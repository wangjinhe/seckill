package org.seckill.service.impl;

import org.apache.commons.collections.MapUtils;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务实现类
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    //日志对象
    private Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);

    // 使用自动注入的方式，把dao注入进来
    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    public Seckill queryById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public List<Seckill> getSeckillList(int offset, int count) {
        return seckillDao.queryAll(offset, count);
    }

    /**
     * 返回暴露的接口
     *
     * @param seckillId
     * @return
     */
    public Exposer getExposerUrl(long seckillId) {
        //  先查询redis中是否存在
        Seckill seckill = null;
        seckill = redisDao.getSeckill(seckillId);
        if (seckill == null) {
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null) {
                return new Exposer(seckillId, false);
            } else {
                redisDao.putSeckill(seckill);
            }
        }

        Date now = new Date();
        if (now.before(seckill.getStartTime()) || now.after(seckill.getEndTime())) {
            return new Exposer(seckillId, now.getTime(), seckill.getStartTime().getTime(), seckill.getEndTime().getTime(), false, "");

        } else {
            String md5 = getUrlMd5(seckillId);
            return new Exposer(seckillId, true, md5);
        }
    }

    //加密用的，约复杂约好
    private String salt = "fdafdaa!@$#$#$FAdfda%$$%@dadaf$%@%$@&%*";

    private String getUrlMd5(Long seckillId) {
        String temp = seckillId + salt;
        String md5 = DigestUtils.md5DigestAsHex(temp.getBytes());
        return md5;
    }

    @Transactional
    /**
     * 执行秒杀，事务方法
     * 使用注解控制事务方法的优点：
     * 1：开发团队达成一致约定，明确标注事务方法的编程风格
     * 2：保证事务方法的执行时间尽可能短，不要穿插其它网络操作，RPC/HTTP请求或者剥离到事务方法外部
     * 3：不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatKillException
     * @throws SeckillCloseException
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {

        if (StringUtils.isEmpty(md5) || !getUrlMd5(seckillId).equals(md5)) {
            throw new SeckillException(" data rewrite ");
        }

        try {
            // 先插入明细
            int count2 = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (count2 > 0) {   // 明细插入成功成功

                // 再减库存
                Date now = new Date();
                int count1 = seckillDao.reduceNumber(seckillId, now);
                if (count1 > 0) {  // 减库存成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                } else {
                    System.out.println("秒杀关闭");
                    throw new SeckillCloseException(" seckill close ");
                }
            } else {  // 重复秒杀
                throw new RepeatKillException(SeckillStatEnum.REPEAT_KILL.getStateInfo());
            }

            //为了能报出精确的异常，因此先catch其它异常，再catch exception结束
        } catch (SeckillCloseException e) {
            throw e;
        } catch (RepeatKillException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new SeckillException(e.getMessage(), e.getCause());
        }
    }




    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (StringUtils.isEmpty(md5) || !getUrlMd5(seckillId).equals(md5)) {
            return  new SeckillExecution(seckillId,SeckillStatEnum.DATA_REWRITE);
        }
        Date killTime  = new Date();
        // 通过map传递参数
        Map<String,Object> paramMap = new HashMap<String,Object>() ;
        paramMap.put("seckillId",seckillId);
        paramMap.put("userPhone",userPhone);
        paramMap.put("killTime",killTime);
        paramMap.put("result",null);

        try {
            seckillDao.killByProcedure(paramMap);
            int result  = MapUtils.getIntValue(paramMap,"result",-2);
            if(result == 1) {
                SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                return  new SeckillExecution(seckillId,SeckillStatEnum.SUCCESS,successKilled);
            } else {
                return  new SeckillExecution(seckillId,SeckillStatEnum.stateOf(result));
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return  new SeckillExecution(seckillId,SeckillStatEnum.INNER_ERROR);
        }
    }
}
