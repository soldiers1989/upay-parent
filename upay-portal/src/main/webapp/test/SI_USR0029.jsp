<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include/common.jsp"%>

<html>
  <head>
    <base href="<%=basePath%>">
    <title>SI_MER0011商户资金变动查询</title>
	<script type="text/javascript">
		function demo001(){
			doAjax({trans_code:'SI_USR0029'},{MOBILE:"18012367023"})
		}
	</script>
  </head>
  
  <body onload="">
  	<form>
	   	<a href="javascript:void(0);" onclick="demo001();">操作日志查询</a>
  	</form>
  </body>
</html>