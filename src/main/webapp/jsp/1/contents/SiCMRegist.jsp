<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/popup_header.jsp" %>
<%
	String selectedItem = (String)model.get("selectedItem");
%>
<%=selectedItem%>
<script language=javascript>
parent.opener.location.reload();
parent.close();
</script>
<body>
</html>