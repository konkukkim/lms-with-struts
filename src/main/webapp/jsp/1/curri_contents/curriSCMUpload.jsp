<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/popup_header.jsp" %>
<%
	String retVal = (String)model.get("retVal");
%>
<script language=javascript>
    top.document.location = "<%=CONTEXTPATH%>/CurriContents.cmd?cmd=curriSCMComplete&retVal=<%=retVal%>";
</script>