package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * 商品秒杀的服务类

 * 业务接口：站在"使用者"角度设计接口
 *
 * 三个方面：
 * 1.方法定义粒度，方法定义的要非常清楚
 * 2.参数，要越简练越好
 * 3.返回类型(return 类型一定要友好/或者return异常，我们允许的异常)
 *
 */
public interface SeckillService {

    //查询一个秒杀商品
    Seckill queryById(long seckillId);

    // 查询秒杀商品列表
    List<Seckill> getSeckillList(int offset,int count);

    /**
     * 返回秒杀地址
     * 当秒杀开始时返回秒杀地址
     * 当秒杀没有开始时，返回系统时间和秒杀时间
     * @param seckillId
     * @return
     */
    Exposer getExposerUrl(long seckillId);

    /**
     * 执行秒杀，有可能失败，因此把异常抛出来
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(long seckillId,long userPhone,String md5)
    throws SeckillException,RepeatKillException,SeckillCloseException;


    /**
     * 执行秒杀，有可能失败，因此把异常抛出来
     * 通过存储过程调用
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckillProcedure(long seckillId,long userPhone,String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;
}
