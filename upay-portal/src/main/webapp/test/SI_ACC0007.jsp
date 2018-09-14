<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include/common.jsp"%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>SI_PAYLOGOSEARCH测试</title>
	<script type="text/javascript">
	function demo001(){
		$("#tips").html('交易处理中,请稍后...');
		var SYSHEAD=  {
			trans_code:'SI_ACC0007',
			user_id:"UR000000000181",
			chnl_id:"01"};
		var BODY = {};
		var message = new Message2(SYSHEAD,BODY);
		alert(message);
		console.log(message);
		$.post("http://localhost:8081/upay-portal/JSONServer/dwonloadExcel.do?user_id=UR000000000181&username=刘兵",
				packMessage(new Message2(SYSHEAD,BODY)),
				function(data) {
					doSuc(data);
				}).fail(function() {
			doFail();
		});
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