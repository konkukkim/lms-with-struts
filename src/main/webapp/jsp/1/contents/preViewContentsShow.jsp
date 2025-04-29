<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map"%> 
<%
    Map model = (Map)request.getAttribute("MODEL");
%>
<% String CONTEXTPATH = request.getContextPath(); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<script language="javascript">
	//alert("<%=CONTEXTPATH%><%=model.get("ContentsUrl")%>");
	document.location.href="<%=CONTEXTPATH%><%=model.get("ContentsUrl")%>";
</script>
</head>
<body>
</body>
</html>

