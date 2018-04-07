package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


/*
    ����spring��junit�����ϣ�����junit����ʱ����springIoc
 */
@RunWith(SpringJUnit4ClassRunner.class)
// ����junit��spring�������ļ�·��  �ڴ�������
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    // �Զ�ע��SeckillDao
    @Autowired
    private  SeckillDao seckillDao;

    @Test
    public void queryById() throws Exception {
        long seckillId = 1000L;
        Seckill seckill =  seckillDao.queryById(seckillId);
        System.out.println(seckill);
    }

    @Test
    public void queryAll() throws Exception {

        List<Seckill>  list = seckillDao.queryAll(0,100);
        for (Seckill seckill: list ) {
            System.out.println(seckill);
        }
    }

    @Test
    public void reduceNumber() throws Exception {
        long seckillId  = 1000L;
        int result =  seckillDao.reduceNumber(seckillId,new Date());
        System.out.println("��ɱ���" + result);
    }

}