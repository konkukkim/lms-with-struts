<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map,java.util.ArrayList"%> 
<%
    Map model = (Map)request.getAttribute("MODEL");
%>
<html>
<head>
</head>
<head>
<META HTTP-EQUIV="refresh" Content="0; url=<%=model.get("goUrl")%>">
</head>
</html>

