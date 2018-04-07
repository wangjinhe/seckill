<%@ page  contentType="text/html; charset=GB2312" language="java" %>
<!-- ����jstl����ͨ�õ���ȡ������ -->
<%@include file="common/tag.jsp" %>
<html>
    <head>
        <title>��ɱ��Ʒ�б�</title>
        <!--��̬����ͷ��-->
        <%@include file="common/head.jsp" %>
    </head>
    <body>

    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading text-center">
                <h2>��ɱ�б�</h2>
            </div>
            <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                        <th>����</th>
                        <th>���</th>
                        <th>��ʼʱ��</th>
                        <th>����ʱ��</th>
                        <th>����ʱ��</th>
                        <th>����ҳ</th>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="sk">
                        <tr>
                            <td>${sk.name}</td>
                            <td>${sk.number}</td>

                             <!-- ����jstl���ʽ��Ӧ��fmt���ʽ��������ʾ��ʽ -->
                            <td>
                                <fmt:formatDate value="${sk.startTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                            <td>
                                <fmt:formatDate value="${sk.endTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                            <td>
                                <fmt:formatDate value="${sk.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                            <td>
                                <a class="btn btn-info" href="/seckill/${sk.seckillId}/detail" target="_blank">
                                ����</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

<!-- jQuery�ļ��������bootstrap.min.js ֮ǰ���� -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>

<!-- ���µ� Bootstrap ���� JavaScript �ļ� -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


</body>
</html>