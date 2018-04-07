//�����ɱ��ص�ǰ���ж�js
// jsģ�黯�����嵽������
var seckill = {

    /*�����ɱ�ӿڵ�ַ�Ķ���*/
    Url : {
        getNow: function(){  return "/seckill/time/now";     },
        getExposer : function(seckillId) {
            return "/seckill/" + seckillId + "/exposer";
        },
        getKillUrl: function(seckillId,md5) {
            return "/seckill/" + seckillId + "/" + md5 + "/execution";
        }
    },

    // ��ɱ����ҳ����Ҫ�߼�
    Detail : {
        // ����ҳ��ʼ��
        init: function(param) {
            //(1) �ж�cookie�Ƿ����
            var userPhone = $.cookie("userPhone");
            //(2)�����ھ͵�����¼��
            if(!seckill.isValidatePhone(userPhone)) {
                var userPhoneModal = $("#userPhoneModel");
                userPhoneModal.modal({
                    show : true, // ��ʾ�Ի���
                    backdrop : 'static' , // ��ֹ������������رյ�����
                    keyboard : false  // �رռ����¼�����ʽesc�رյ�����
                });
                // �󶨵绰�ύ��ť
                $("#btnPhone").click(function() {
                    var phone = $("#txtPhone").val();
                    console.log(phone); // TODO
                    if(!seckill.isValidatePhone(phone)) { // ��ʾ������Ϣ
                       //������domʱ�������أ�������html,Ȼ������ʾ���и���̬Ч��
                        $("#phoneMsg").hide().html('<label class="label  label-danger">�ֻ��Ŵ���!</label> ').show(300);

                    } else {  // д��cookie
                      //�绰д��cookie(7�����)��pathֻ��"/seckill"·��֮�·���Ч
                        $.cookie('userPhone', phone, {expires: 7, path: '/seckill'});
                        window.location.reload(); // ˢ��ҳ��
                    }
                })
                return;// ������֮�󣬾ͷ��أ���ֹ����ִ��countDown
            }

            //(3)���ھͿ�ʼ��ʱ
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
    }, // ��Ҫ�����������

    // �ж��ֻ����Ƿ���Ч
    isValidatePhone: function(phone) {
       // ֱ���ж϶���ῴ�����Ƿ�Ϊ��,�վ���undefine����false; isNaN �����ַ���true
        if(phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    }, // ��Ҫ�����������

    // ����ʱ
    countDown(seckillId,nowTime,startTime,endTime) {

         //��ȡǰ��ҳ��չʾ����ʱ�����
         var seckillBox = $('#seckill-box');

        if(nowTime > endTime) {
             seckillBox.hide().html("��ɱ����").show(300);
        } else if(nowTime < startTime) { // ����ʱ
             //��ɱδ��ʼ,��ʱ�¼���
             var time  = new Date(startTime);
             seckillBox.countdown(time,function(event) {
                var format = event.strftime("��ɱ����ʱ %D��%Hʱ%M��%S�� ");
                seckillBox.html(format);
             }).on("finish.countdown",function () {  // �󶨼�ʱ��ɵ��¼�
                seckill.executeSeckill(seckillId,seckillBox);
             })
        } else {  // ��ɱ��ʼ
                seckill.executeSeckill(seckillId,seckillBox);
        }
    }, // ��Ҫ�����������

    // ִ����ɱ
    executeSeckill : function(seckillId,node) {
        // �����ɱ��ť
        node.hide().html('<input type="button" id="btnKill" class="btn btn-primary btn-lg"  value="��ʼ��ɱ" /> ');
        // (1)��ȡ��ɱ��ַ
        $.post(seckill.Url.getExposer(seckillId),function(result){
            if(result && result["success"]){
                // ������ɱ�ӿ�
                var exposer = result["data"];
                if(exposer["exposed"]) {
                    var md5  = exposer["md5"];
                    var killUrl = seckill.Url.getKillUrl(seckillId,md5);

                     //��һ�ε���¼�
                    //Ԥ���û��������������������ɲ���Ҫ��ѹ��
                    $("#btnKill").one("click",function(){
                        $(this).addClass("disabled"); //
                        $.post(killUrl,{},function(result) { // ִ����ɱ

                            if(result && result["success"]) {
                               var stateInfo = result["data"]["stateInfo"];
                                node.html('<span class="label label-success">' + stateInfo + "</span>");
                            } else { // ��ɱʧ��
                                node.html('<span class="label label-success">' + result["error"] + "</span>");
                            }
                        })
                    })
                    node.show();

                } else {  // ʵ��û�п�ʼ��ɱ
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