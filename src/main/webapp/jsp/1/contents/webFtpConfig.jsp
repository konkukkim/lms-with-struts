<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map"%> 
<%
    Map model = (Map)request.getAttribute("MODEL");
    String ConfigValue = (String)model.get("ConfigValue");
%>
<html><%=ConfigValue%></html>