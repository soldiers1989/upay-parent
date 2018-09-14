<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include/common.jsp"%>

<html>
<head>
<base href="<%=basePath%>">
<title>SI_PAYLOGOSEARCH测试</title>
<script type="text/javascript">
	function demo001() {
		doAjax({
			trans_code : 'SI_PAY3007'
		}, {
			MER_NO: 'MER2017000119',
			TRANS_AMT: '300',
			USER_ID: 'UR000000000068',
			OUTER_ORDER_NO: '2017120425394111111',
			PAY_TIME: '20171204153940240553',
			SPBILL_CREATE_IP: '127.0.0.1',
			PAY_SERVIC_TYPE: '0002',
			CHNL_ID: '02',
			QR_NO: '6225796752391402924',
			ORDER_START_DATE: '20171204000000',
			ORDER_END_DATE: '20171204235757',
			CURR: 'CNY'
		})
	}
</script>
</head>

<body onload="">
	<form>
		<label style="display: inline-block; width: 200">查询范围:</label><input
			type="text" id="queryScope" value="01" />
		<p>
			<label style="display: inline-block; width: 200">查询起始日期:</label><input
				type="text" id="queryStart" value="" /> <label
				style="display: inline-block; width: 200">查询终止日期:</label><input
				type="text" id="queryEnd" value="" />
		<p>
			<label style="display: inline-block; width: 200">操作渠道:</label><input
				type="text" id="operChnlId" value="01" />
	</form>
	<form>
		<a href="javascript:void(0);" onclick="demo001();">操作日志查询</a>
	</form>
</body>
</html>