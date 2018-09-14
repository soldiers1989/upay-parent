<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include/common.jsp"%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>SI_PAYLOGOSEARCH测试</title>
	<script type="text/javascript">
		function demo001(){
			doAjax(
				{"syshead": {trans_code:'SI_ACC1001',
				user_id:"UR000000000008",
				chnl_id:"01"},
				"body":{CERT_WEAK_WAY:"02",
				USER_CERT_LEVEL:"2",
				MOBILE:"13551324852",
				CERT_NO:"510623198608108436",
				CERT_NAME:"刘兵",
				CERT_TYPE:"1",
				BIND_ACCT_TYPE:"11",
				E_BIND_ACCT_NO:"621483123654785",
				SMS_CODE:"123456",
				THIRD_AUTH_CHNL:"1"}});
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