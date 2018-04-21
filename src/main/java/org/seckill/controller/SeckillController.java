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

// ��SeckillControllerע�뵽�����У�
// ����ӳ��·��
@Controller
@RequestMapping(value = "/seckill")
public class SeckillController {

    private Logger loger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    // ������ɱ��Ʒ�б�ҳ
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model){
        List<Seckill> list = seckillService.getSeckillList(0,100);
        model.addAttribute("list",list);
        // list.jsp + model = ModelAndView
        return  "list";
    }


    // ��ɱ����ҳ
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


    // ��ɱ�ӿڵ�ַ������json����
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


    // ִ����ɱ,ע�⣺spring mvc��cookie�����⣬���cookie�����ڣ��ͻᱨ���������Ϊfalse
    @ResponseBody
    @RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    public  SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                    @PathVariable("md5") String md5,
                                                    @CookieValue(value = "userPhone" , required = false) Long userPhone){
        SeckillResult<SeckillExecution> seckillResult = null;
        SeckillExecution seckillExecution = null;
        if(userPhone == null) {
            seckillResult = new SeckillResult<SeckillExecution>(false,"δע��");
            return  seckillResult;
        }
        try {
            // ����ʽ����ִ����ɱ
            //seckillExecution = seckillService.executeSeckill(seckillId,userPhone,md5);
            // ͨ���洢����ִ����ɱ
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



    // ��ȡϵͳ��ǰʱ��
    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public  SeckillResult<Long> getNow(){
        Date date = new Date();
        return  new SeckillResult<Long>(true,date.getTime());
     }


}
