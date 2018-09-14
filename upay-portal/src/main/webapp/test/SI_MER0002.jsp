<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include/common.jsp"%>

<html>
  <head>
    <base href="<%=basePath%>">
    <title>SI_MER0002二级商户维护</title>
	<script type="text/javascript">
		function demo001(){
			doAjax({trans_code:'SI_MER0002',user_id:"12332112332111"},{MER_NAME:"接口测试2",CONTACT:"testa",CONTACT_TEL:"80000000",CONTACT_MOBILE:"13111111111",CONTACT_EMAIL:"1213131@163.com",MER_TEL:"13111111111",MER_FAX:"123321",MER_ADDR:"测试地址",MER_POSTAL_CODE:"000000",WEBSITE_CODE:"1234560",WEBSITE_NAME:"网站名字",WEBSITE_DOMAIN:"www.321.com",WEBSITE_SCOP:"1qq",COMPANY_NAME:"公司",EGAL_PERSON_NAME:"哈哈",EGAL_PERSON_ID_TYPE:"01",EGAL_PERSON_ID_NO:"58432154111321",COMPANY_ID_TYPE:"22",COMPANY_ID_NO:"51255555",ORG_DEPT_NO:"WE33333",BUSI_LICENSE_ID:"ddddd"})
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