<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include/common.jsp"%>
<html>
<head>
<base href="<%=basePath%>">
<title>SI_PAYLOGOSEARCH测试</title>
<script type="text/javascript">
	function demo001() {
		$("#tips").html('交易处理中,请稍后...');
		var SYSHEAD = {
			trans_code : 'SI_ACC0007',
			user_id : "UR000000000181",
			chnl_id : "01"
		};
		var BODY = {};
		var message = new Message2(SYSHEAD, BODY);
		alert(message);
		console.log(message);
		$.post("http://localhost:8090/upay-portal/JSONServer/upload.do",
				packMessage(new Message2(SYSHEAD, BODY)), function(data) {
					doSuc(data);
				}).fail(function() {
			doFail();
		});
	}
</script>
</head>

<body onload="">
	<form action="http://localhost:8090/upay-portal/JSONServer/upload.do"
		method="post" enctype="multipart/form-data">
		<input type="text" name="type" id="type">
		选择文件:<input type="file" name="file"> <input type="submit"
			value="提交">
	</form>
</body>
</html>