package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * 成功秒杀Dao
 */
public interface SuccessKilledDao {
    /**
     * 插入秒杀成功的商品明细
     * @param seckillId 商品Id
     * @param userPhone 用户手机
     * @return 插入成功返回1，失败返回0
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 查询秒杀成功的商品
     * @param seckillId 商品Id
     * @param userPhone 用户手机号
     * @return 返回秒杀成功的商品
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

}
