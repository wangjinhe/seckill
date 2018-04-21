package org.seckill.dao;


import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

//@Component 这个注解是不需要的
public interface SeckillDao {

    /**
     * 根据商品Id,查询秒杀商品
     * @param seckillId 商品id
     * @return 查询到的秒杀商品
     */
    Seckill queryById(long seckillId);

    /**
     *  查询批量的秒杀商品
     * @param offset 偏移量
     * @param count 个数
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset ,@Param("count") int count);

    /**
     * 减库存
     * @param seckillId 商品Id
     * @param killTime 参加秒杀时间
     * @return 返回修改的数据条数
     //注意：@Param表示给参数命名，若是不加，按照java没有保存形参的习性，传值至junit测试会报错
    //原因：对应的xml文件中sql语句要接受两个参数，若要正确传参，则需要给参数命名
     */
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime")  Date killTime);


    /**
     * 通过存储过程执行秒杀
     * 通过map传递参数
     * @param paramMap
     */
    void killByProcedure(Map<String,Object> paramMap);



}
