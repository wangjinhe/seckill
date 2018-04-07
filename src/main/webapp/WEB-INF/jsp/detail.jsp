<%@ page  contentType="text/html; charset=GB2312" language="java" %>
<!-- 引入jstl（将通用的提取出来） -->
<%@include file="common/tag.jsp" %>
<html>
    <head>
        <title>商品详情</title>
        <!--静态引入头部-->
        <%@include file="common/head.jsp" %>
    </head>
    <body>
        <div class="container">
            <div class="panel panel-default">
                <div class="panel-heading text-center">
                    <h2>${seckill.name}</h2>
                </div>
                <div class="panel-body">
                    <h2 class="text-danger">
                        <%-- 显示time图标 --%>
                        <span class="glyphicon glyphicon-time"></span>
                        <%-- 展示倒计时 --%>
                        <span class="glyphicon" id="seckill-box"></span>
                    </h2>
                </div>
            </div>
        </div>


        <div id="userPhoneModel" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title text-center">
                            <span class="glyphicon glyphicon-phone"> </span>秒杀电话:
                        </h3>
                    </div>

                    <div class="modal-body">
                        <div class="row">
                            <div class="col-xs-8 col-xs-offset-2">
                            <input type="text" class="form-control" id="txtPhone" placeholder="请输入手机号" />
                            </div>
                        </div>
                    </div>

                    <div class="modal-footer">
                         <%--验证信息--%>

                             <span id="phoneMsg" class="glyphicon"></span>
                              <button type="button" id="btnPhone" class="btn btn-success">
                                                 <span class="glyphicon glyphicon-phone"></span>
                                                 Submit
                               </button>
                    </div>

                </div>
        </div>



<%-- 提示：编写script语句时，勿用"/>"结尾，不然语句之下的js浏览器不加载 --%>

<%--jQery文件,务必在bootstrap.min.js之前引入--%>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<%--使用CDN 获取公共js http://www.bootcdn.cn/--%>
<%--jQuery Cookie操作插件--%>
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<%--jQuery countDown倒计时插件--%>
<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>

<script src="/resource/script/seckill.js" type="text/javascript"></script>


<script type="text/javascript">
    $(function () {
        //使用EL表达式传入参数
        seckill.Detail.init({
            seckillId: ${seckill.seckillId},
            startTime: ${seckill.startTime.time},//毫秒
            endTime: ${seckill.endTime.time}
        });
    })

</script>

    </body>
</html>