<%@ page  contentType="text/html; charset=GB2312" language="java" %>
<!-- ����jstl����ͨ�õ���ȡ������ -->
<%@include file="common/tag.jsp" %>
<html>
    <head>
        <title>��Ʒ����</title>
        <!--��̬����ͷ��-->
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
                        <%-- ��ʾtimeͼ�� --%>
                        <span class="glyphicon glyphicon-time"></span>
                        <%-- չʾ����ʱ --%>
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
                            <span class="glyphicon glyphicon-phone"> </span>��ɱ�绰:
                        </h3>
                    </div>

                    <div class="modal-body">
                        <div class="row">
                            <div class="col-xs-8 col-xs-offset-2">
                            <input type="text" class="form-control" id="txtPhone" placeholder="�������ֻ���" />
                            </div>
                        </div>
                    </div>

                    <div class="modal-footer">
                         <%--��֤��Ϣ--%>

                             <span id="phoneMsg" class="glyphicon"></span>
                              <button type="button" id="btnPhone" class="btn btn-success">
                                                 <span class="glyphicon glyphicon-phone"></span>
                                                 Submit
                               </button>
                    </div>

                </div>
        </div>



<%-- ��ʾ����дscript���ʱ������"/>"��β����Ȼ���֮�µ�js����������� --%>

<%--jQery�ļ�,�����bootstrap.min.js֮ǰ����--%>
<!-- jQuery�ļ��������bootstrap.min.js ֮ǰ���� -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<!-- ���µ� Bootstrap ���� JavaScript �ļ� -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<%--ʹ��CDN ��ȡ����js http://www.bootcdn.cn/--%>
<%--jQuery Cookie�������--%>
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<%--jQuery countDown����ʱ���--%>
<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>

<script src="/resource/script/seckill.js" type="text/javascript"></script>


<script type="text/javascript">
    $(function () {
        //ʹ��EL���ʽ�������
        seckill.Detail.init({
            seckillId: ${seckill.seckillId},
            startTime: ${seckill.startTime.time},//����
            endTime: ${seckill.endTime.time}
        });
    })

</script>

    </body>
</html>