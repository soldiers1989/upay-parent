<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include/common.jsp"%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>SI_PAYLOGOSEARCH测试</title>
    <script type="text/javascript">
        function demo001(){
            doAjax({
                trans_code: 'SI_ACC0006'
            }, {
                ROUTE_CODE:'0002'
            })
        }
    </script>
</head>

<body>

    <a href="javascript:void(0);" onclick="demo001();">操作日志查询</a>
</body>
</html>