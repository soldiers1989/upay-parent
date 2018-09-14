<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include/common.jsp"%>

<html>
  <head>
    <base href="<%=basePath%>">
    <title>SI_PAYLOGOSEARCH测试</title>
	<script type="text/javascript">
		function demo001(){
			doPayAjax("pay",
						{
							transCode:"pay",
							serviceVersion:"1.0",
							charset:"UTF-8",
							signType:"MD5",
							sign:"lfkdjljfsjflkdsjfls",
							merNo:'13968111',
							secMerNo:"10000201",
							outerOrderNo:"2016090119561123888",
							userId:"usr001",
							vAcctNo:"",
							transAmt:"0.01",
							CURR:"156",
							spbillCreateIp:"192,168.1.89",
							outerOrderStartDate:"20160907094934",
							outerOrderEndDate:"20160907104934",
							otherTranAmt:"",
							productAmt:"",
							transComments:"",
							notifyUrl:"",
							returnUrl:"",
							payServicType:"0002",
							chnlId:"pc",
							tokenId:"",

						}
					);
		}
	</script>
  </head>
  <form name='paySubmit' method='post'  action='http://localhost:8086/upay-portal/static_app/Pay/pay_fail.html' >
	  <input type='hidden' name='rspCode' value='2404'>
	  <input type='hidden' name='rspMsg' value='sign参数为空错误'>
  </form>
  <script>
//	  document.paySubmit.submit();
  </script>

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