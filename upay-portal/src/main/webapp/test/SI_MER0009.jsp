<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include/common.jsp"%>

<html>
  <head>
    <base href="<%=basePath%>">
    <title>SI_MER0009二级商户费率信息维护</title>
	<script type="text/javascript">
		function demo001(){
			doAjax({trans_code:'SI_MER0009',user_id:"UR000000000015"},{FEE_NAME:'potal测试',ACCT_TYPE:'01',TERMINAL_TYPE:'03',SEC_MER_NO:'4',TXN_CODE:'MER0009',FEE_CODE:'0002',FREE_CYCLE:'1',FREE_COUNT:2,START_DATE:'2016-8-19',END_DATE:'2016-8-20'})
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