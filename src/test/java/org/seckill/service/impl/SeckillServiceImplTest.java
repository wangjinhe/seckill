package org.seckill.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;



import static org.junit.Assert.*;

/*
    ����spring��junit�����ϣ�����junit����ʱ����springIoc
 */
@RunWith(SpringJUnit4ClassRunner.class)
// ����junit��spring�������ļ�·��  �ڴ�������
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;


    @Test
    public void queryById() throws Exception {
        long seckillId = 1000L;
        Seckill seckill = seckillService.queryById(seckillId);
        System.out.println(seckill);
    }

    @Test
    public void getSeckillList() throws Exception {

       List<Seckill> list =  seckillService.getSeckillList(0,100);
        for (Seckill seckill: list          ) {
            System.out.println(seckill);
        }
    }

    @Test
    public void getExposerUrl() throws Exception {
        long seckillId = 1000L;
        Exposer exposer = getExposer(seckillId);
        System.out.println(exposer);

    }


    private  Exposer getExposer(long seckillId){
        Exposer exposer = seckillService.getExposerUrl(seckillId);
        return  exposer;
    }


    /*
ע�⣺���ɲ��Ը��ǵ��걸�ԣ�
��1����Ʒδ���������ѽ���������
��2����Ʒ��ɱʱ���ѿ���������
��3����Ʒ�ظ���ɱ������

��������Ԫ���Կ�ϲ���һ����ԣ�
����չʾ�ӿ���ɱ��ַ��������ʱִ����ɱ��δ�����򱨳�warn����try catch�����������ظ�ִ�У�����
 */

    @Test
    public void executeSeckill() throws Exception {

            long seckillId = 1001L;
            long userPhone = 18711111114L;
            Exposer exposer = getExposer(seckillId);
            if (exposer.isExposed() == false) {
                logger.info("��ɱ�ر�");
            } else {
                String md5 = exposer.getMd5();
                SeckillExecution seckillExecution = null;
                try {
                    seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
                    if (seckillExecution.getState() == SeckillStatEnum.SUCCESS.getState()) {
                        System.out.println("��ɱ�ɹ�");
                        System.out.println(seckillExecution);
                    }
                } catch (RepeatKillException e) {  // ������֪���쳣
                    System.out.println(e.getMessage());
                } catch (SeckillCloseException e) {
                    System.out.println(e.getMessage());
                }
            }
    }



    @Test
    public void executeSeckillProcedure() throws Exception {

        long seckillId = 1001L;
        long userPhone = 18717181113L;

        Exposer exposer = getExposer(seckillId);
        if (exposer.isExposed() == false) {
            logger.info("��ɱ�ر�");
        } else {
            String md5 = exposer.getMd5();
            SeckillExecution seckillExecution = null;
            try {
                seckillExecution = seckillService.executeSeckillProcedure(seckillId, userPhone, md5);
                if (seckillExecution.getState() == SeckillStatEnum.SUCCESS.getState()) {
                    System.out.println("��ɱ�ɹ�");
                    System.out.println(seckillExecution);
                } else  {
                    System.out.println(seckillExecution);
                }
            } catch (RepeatKillException e) {  // ������֪���쳣
                System.out.println(e.getMessage());
            } catch (SeckillCloseException e) {
                System.out.println(e.getMessage());
            }
        }


    }

}