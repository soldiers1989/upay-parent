<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include/common.jsp"%>

<html>
  <head>
    <base href="<%=basePath%>">
    <title>SI_PAYLOGOSEARCH测试</title>
	<script type="text/javascript">
		function demo001(){
			doAjax({trans_code:'SI_USR0009',user_id:"UR000000000001",chnl_id:"01"},{TRADE_PWD:"123456789",NEW_TRADE_PWD:"12345678",SURE_TRADE_PWD:"12345678",PWD_FLAG:"2",CHG_TYPE:"01",CERT_TYPE:"00",CERT_NO:"1213131",CERT_NAME:"13111111111",CERT_FLAG:"1"})
		}
	</script>
  </head>
  
  <body onload="">
  	<form>
  		<label style="display:inline-block;width: 200">查询范围:</label><input type="text" id="queryScope" value="01"/>
  		<p>
  		<label style="display:inline-block;width: 200">查询起始日期:</label><input type="text" id="queryStart" value=""/>
  		<label style="display:inline-block;width: 200">查询终止日期:</label><input type="text" id="queryEnd" value=""/>
  		<p>
		<label style="display:inline-block;width: 200">操作渠道:</label><input type="text" id="operChnlId" value="01"/>
	</form>
  	<form>
	   	<a href="javascript:void(0);" onclick="demo001();">操作日志查询</a>
  	</form>
  </body>
</html>