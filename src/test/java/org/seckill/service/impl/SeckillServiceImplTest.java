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
    配置spring和junit的整合，告诉junit启动时加载springIoc
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit，spring的配置文件路径  在大括号内
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
注意：集成测试覆盖的完备性：
（1）商品未开启或者已结束，测试
（2）商品秒杀时间已开启，测试
（3）商品重复秒杀，测试

将两个单元测试块合并到一起测试：
首先展示接口秒杀地址，当开启时执行秒杀，未开启则报出warn。且try catch包裹后允许重复执行！！！
 */

    @Test
    public void executeSeckill() throws Exception {

            long seckillId = 1001L;
            long userPhone = 18711111114L;
            Exposer exposer = getExposer(seckillId);
            if (exposer.isExposed() == false) {
                logger.info("秒杀关闭");
            } else {
                String md5 = exposer.getMd5();
                SeckillExecution seckillExecution = null;
                try {
                    seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
                    if (seckillExecution.getState() == SeckillStatEnum.SUCCESS.getState()) {
                        System.out.println("秒杀成功");
                        System.out.println(seckillExecution);
                    }
                } catch (RepeatKillException e) {  // 捕获已知的异常
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
            logger.info("秒杀关闭");
        } else {
            String md5 = exposer.getMd5();
            SeckillExecution seckillExecution = null;
            try {
                seckillExecution = seckillService.executeSeckillProcedure(seckillId, userPhone, md5);
                if (seckillExecution.getState() == SeckillStatEnum.SUCCESS.getState()) {
                    System.out.println("秒杀成功");
                    System.out.println(seckillExecution);
                } else  {
                    System.out.println(seckillExecution);
                }
            } catch (RepeatKillException e) {  // 捕获已知的异常
                System.out.println(e.getMessage());
            } catch (SeckillCloseException e) {
                System.out.println(e.getMessage());
            }
        }


    }

}