//存放秒杀相关的前端判断js
// js模块化：定义到对象中
var seckill = {

    /*存放秒杀接口地址的对象*/
    Url : {
        getNow: function(){  return "/seckill/time/now";     },
        getExposer : function(seckillId) {
            return "/seckill/" + seckillId + "/exposer";
        },
        getKillUrl: function(seckillId,md5) {
            return "/seckill/" + seckillId + "/" + md5 + "/execution";
        }
    },

    // 秒杀详情页的主要逻辑
    Detail : {
        // 详情页初始化
        init: function(param) {
            //(1) 判断cookie是否存在
            var userPhone = $.cookie("userPhone");
            //(2)不存在就弹窗登录框
            if(!seckill.isValidatePhone(userPhone)) {
                var userPhoneModal = $("#userPhoneModel");
                userPhoneModal.modal({
                    show : true, // 显示对话框
                    backdrop : 'static' , // 防止单击其他区域关闭弹出层
                    keyboard : false  // 关闭键盘事件，方式esc关闭弹出层
                });
                // 绑定电话提交按钮
                $("#btnPhone").click(function() {
                    var phone = $("#txtPhone").val();
                    console.log(phone); // TODO
                    if(!seckill.isValidatePhone(phone)) { // 提示错误信息
                       //当操作dom时，先隐藏，再设置html,然后再显示，有个动态效果
                        $("#phoneMsg").hide().html('<label class="label  label-danger">手机号错误!</label> ').show(300);

                    } else {  // 写入cookie
                      //电话写入cookie(7天过期)，path只在"/seckill"路径之下方有效
                        $.cookie('userPhone', phone, {expires: 7, path: '/seckill'});
                        window.location.reload(); // 刷新页面
                    }
                })
                return;// 弹出框之后，就返回，防止继续执行countDown
            }

            //(3)存在就开始计时
            var startTime  = param["startTime"];
            var endTime  = param["endTime"];
            var seckillId = param["seckillId"];

            $.get(seckill.Url.getNow(),function(result){
                if(result && result["success"]) {
                    var now = result["data"];
                    seckill.countDown(seckillId,now,startTime,endTime);
                } else {
                    console.error(result);
                    alert(result);
                }
            })

        }
    }, // 不要忘记这个逗号

    // 判断手机号是否有效
    isValidatePhone: function(phone) {
       // 直接判断对象会看对象是否为空,空就是undefine就是false; isNaN 非数字返回true
        if(phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    }, // 不要忘记这个逗号

    // 倒计时
    countDown(seckillId,nowTime,startTime,endTime) {

         //获取前端页面展示倒计时的组件
         var seckillBox = $('#seckill-box');

        if(nowTime > endTime) {
             seckillBox.hide().html("秒杀结束").show(300);
        } else if(nowTime < startTime) { // 倒计时
             //秒杀未开始,计时事件绑定
             var time  = new Date(startTime);
             seckillBox.countdown(time,function(event) {
                var format = event.strftime("秒杀倒计时 %D天%H时%M分%S秒 ");
                seckillBox.html(format);
             }).on("finish.countdown",function () {  // 绑定计时完成的事件
                seckill.executeSeckill(seckillId,seckillBox);
             })
        } else {  // 秒杀开始
                seckill.executeSeckill(seckillId,seckillBox);
        }
    }, // 不要忘记这个逗号

    // 执行秒杀
    executeSeckill : function(seckillId,node) {
        // 添加秒杀按钮
        node.hide().html('<input type="button" id="btnKill" class="btn btn-primary btn-lg"  value="开始秒杀" /> ');
        // (1)获取秒杀地址
        $.post(seckill.Url.getExposer(seckillId),function(result){
            if(result && result["success"]){
                // 返回秒杀接口
                var exposer = result["data"];
                if(exposer["exposed"]) {
                    var md5  = exposer["md5"];
                    var killUrl = seckill.Url.getKillUrl(seckillId,md5);

                     //绑定一次点击事件
                    //预防用户连续点击，给服务器造成不必要的压力
                    $("#btnKill").one("click",function(){
                        $(this).addClass("disabled"); //
                        $.post(killUrl,{},function(result) { // 执行秒杀

                            if(result && result["success"]) {
                               var stateInfo = result["data"]["stateInfo"];
                                node.html('<span class="label label-success">' + stateInfo + "</span>");
                            } else { // 秒杀失败
                                node.html('<span class="label label-success">' + result["error"] + "</span>");
                            }
                        })
                    })
                    node.show();

                } else {  // 实际没有开始秒杀
                        var nowTime = exposer["now"];
                        var startTime = exposer["start"];
                        var endTime = exposer["end"];
                        seckill.countDown(seckillId,nowTime,startTime,endTime);
                }
            } else {
                node.html('<span class="label label-error">' + result["error"] + "</span>");
                node.show();
            }
        })

    }




}