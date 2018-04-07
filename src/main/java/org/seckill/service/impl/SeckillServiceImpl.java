package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
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
import java.util.List;

/**
 * ����ʵ����
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    //��־����
    private Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);

    // ʹ���Զ�ע��ķ�ʽ����daoע�����
    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    public Seckill queryById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public List<Seckill> getSeckillList(int offset, int count) {
        return seckillDao.queryAll(offset,count);
    }

    /**
     * ���ر�¶�Ľӿ�
     * @param seckillId
     * @return
     */
    public Exposer getExposerUrl(long seckillId) {
      Seckill seckill =  seckillDao.queryById(seckillId);
      if(seckill == null){
          return  new Exposer(seckillId,false);
      }
        Date now =  new Date();
      if( now.before(seckill.getStartTime()) || now.after(seckill.getEndTime()) ){
          return  new Exposer(seckillId, now.getTime(),seckill.getStartTime().getTime(),seckill.getEndTime().getTime(),false,"");

      } else {
          String md5 =  getUrlMd5(seckillId);
          return  new Exposer(seckillId,true,md5);
      }
    }


    //�����õģ�Լ����Լ��
    private  String salt = "fdafdaa!@$#$#$FAdfda%$$%@dadaf$%@%$@&%*";
    private  String getUrlMd5(Long seckillId){
        String temp = seckillId  + salt;
        String md5 =  DigestUtils.md5DigestAsHex(temp.getBytes());
        return  md5;
    }

    @Transactional
    /**
     * ִ����ɱ�����񷽷�
     * ʹ��ע��������񷽷����ŵ㣺
     * 1�������ŶӴ��һ��Լ������ȷ��ע���񷽷��ı�̷��
     * 2����֤���񷽷���ִ��ʱ�価���̣ܶ���Ҫ�����������������RPC/HTTP������߰��뵽���񷽷��ⲿ
     * 3���������еķ�������Ҫ������ֻ��һ���޸Ĳ�����ֻ����������Ҫ�������
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatKillException
     * @throws SeckillCloseException
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {


        if(StringUtils.isEmpty(md5) ||  !getUrlMd5(seckillId).equals(md5)){
            throw  new SeckillException(" data rewrite ");
            // ����쳣��ֱ���׳�ȥ
            //return  new SeckillExecution(seckillId, SeckillStatEnum.DATA_REWRITE.getState(),SeckillStatEnum.DATA_REWRITE.getStateInfo());
        }

        try {
            Date now = new Date();
            int count1 = seckillDao.reduceNumber(seckillId, now);
            if (count1 > 0) {
                int count2 = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (count2 > 0) {
                    // ��ɱ�ɹ�
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                } else {
                    throw new RepeatKillException(SeckillStatEnum.REPEAT_KILL.getStateInfo());
                }
            } else {
                System.out.println("��ɱ�ر�");
                throw  new SeckillCloseException(" seckill close ");
            }

            //Ϊ���ܱ�����ȷ���쳣�������catch�����쳣����catch exception����
        }catch (SeckillCloseException e) {
            throw e;
        }catch (RepeatKillException e){
            throw  e;
        }catch (Exception e) {
            logger.error(e.getMessage());
            throw  new  SeckillException(e.getMessage(),e.getCause());
        }
    }
}
