<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<html>
  <head>
   <title></title>
   
  </head>
  
  <body onload="">
  <br>
  <br>
  <br>
  <br>
  	富农汇止付完成回调页面
  	<%
  		String a=request.getParameter("orderStat");
  		request.getParameter("age");
  	%>
  	<br>
  	订单状态：<%=a %>
  </body>
</html>