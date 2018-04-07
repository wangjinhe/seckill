package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
// 导入spring配置
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {

    @Autowired
    private  SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {

        long seckillId  = 1000L;
        long userPhone = 18711111111L;
        int result = successKilledDao.insertSuccessKilled(seckillId,userPhone);
        System.out.println("插入结果:" + result);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {

        long seckillId  = 1000L;
        long userPhone = 18711111111L;
         SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());

    }

}