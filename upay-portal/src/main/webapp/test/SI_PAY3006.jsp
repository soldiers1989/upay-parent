<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="include/common.jsp" %>

<html>
<head>
    <base href="<%=basePath%>">
    <title>SI_PAY3006测试 申请二维码（主扫）</title>
    <script type="text/javascript">
        function demo001() {
            doAjax({
                trans_code: $("#trans_code").val(),
                user_id: $("#user_id").val(),
                chnl_id: $("#chnl_id").val(),
            }, {
                MER_NO: $("#MER_NO").val(),
                ORDER_NO: $("#ORDER_NO").val()
            })
        }
    </script>
</head>

<body onload="">
SI_PAY3006测试 申请二维码（主扫）
<form>


    <label style="display:inline-block;width: 200">商户号:</label><input type="text" id="MER_NO"
                                                                      value="MER2017000119"/><br/>
    <label style="display:inline-block;width: 200">订单号:</label><input type="text" id="ORDER_NO"
                                                                      value="UPAY201712040009447981"/><br/>
    <label style="display:inline-block;width: 200">用户id:</label><input type="text" id="user_id"
                                                                       value="UR000000000477"/><br/>
    <label style="display:inline-block;width: 200">操作渠道:</label><input type="text" id="chnl_id" value="02"/><br/>


    <label style="display:inline-block;width: 200">流程模板:</label><input type="text" id="trans_code"
                                                                       value="SI_PAY3006"/>
</form>
<form>
    <a href="javascript:void(0);" onclick="demo001();">操作日志查询</a>
</form>
</body>
</html>