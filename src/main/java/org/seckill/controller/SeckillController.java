package org.seckill.controller;


import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;

// 将SeckillController注入到容器中；
// 配置映射路径
@Controller
@RequestMapping(value = "/seckill")
public class SeckillController {

    private Logger loger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    // 返回秒杀商品列表页
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model){
        List<Seckill> list = seckillService.getSeckillList(0,100);
        model.addAttribute("list",list);
        // list.jsp + model = ModelAndView
        return  "list";
    }


    // 秒杀详情页
    @RequestMapping(value = "/{id}/detail" , method = RequestMethod.GET)
    public String detail(@PathVariable("id") Long id, Model model){
        if(id == null) {
            return  "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.queryById(id);
        if(seckill == null){
            return  "forward:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return  "detail";
    }


    // 秒杀接口地址，返回json数据
    @ResponseBody()
    @RequestMapping(value = "/{seckillId}/exposer" , method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public SeckillResult<Exposer> exposer( @PathVariable("seckillId") Long seckillId){
        SeckillResult<Exposer> seckillResult = null;
        try {
           Exposer exposer =  seckillService.getExposerUrl(seckillId);
           seckillResult = new SeckillResult<Exposer>(true,exposer);

        } catch (Exception ex){
            loger.error(ex.getMessage());
            seckillResult = new SeckillResult<Exposer>(false,ex.getMessage());
        }
        return  seckillResult;
    }


    // 执行秒杀,注意：spring mvc的cookie有问题，如果cookie不存在，就会报错，因此设置为false
    @ResponseBody
    @RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    public  SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                    @PathVariable("md5") String md5,
                                                    @CookieValue(value = "userPhone" , required = false) Long userPhone){
        SeckillResult<SeckillExecution> seckillResult = null;
        SeckillExecution seckillExecution = null;
        if(userPhone == null) {
            seckillResult = new SeckillResult<SeckillExecution>(false,"未注册");
            return  seckillResult;
        }
        try {
            // 声明式事务执行秒杀
            //seckillExecution = seckillService.executeSeckill(seckillId,userPhone,md5);
            // 通过存储过程执行秒杀
            seckillExecution = seckillService.executeSeckillProcedure(seckillId,userPhone,md5);
            if(seckillExecution.getState() == SeckillStatEnum.SUCCESS.getState()) {
                seckillResult = new SeckillResult<SeckillExecution>(true, seckillExecution);
            } else  {
                seckillResult = new SeckillResult<SeckillExecution>(false,seckillExecution.getStateInfo());
            }
            return  seckillResult;
        }catch (RepeatKillException ex){
            seckillResult = new SeckillResult<SeckillExecution>(false, SeckillStatEnum.REPEAT_KILL.getStateInfo());
            return  seckillResult;
        } catch (SeckillCloseException ex) {
            seckillResult = new SeckillResult<SeckillExecution>(false,SeckillStatEnum.END.getStateInfo());
            return  seckillResult;
        } catch (Exception ex) {
            loger.error(ex.getMessage());
            seckillResult = new SeckillResult<SeckillExecution>(false,ex.getMessage());
            return  seckillResult;
        }
    }



    // 获取系统当前时间
    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public  SeckillResult<Long> getNow(){
        Date date = new Date();
        return  new SeckillResult<Long>(true,date.getTime());
     }


}
