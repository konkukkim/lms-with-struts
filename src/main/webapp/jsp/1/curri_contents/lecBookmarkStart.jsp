<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.util.Map"%> 
<%
    Map model = (Map)request.getAttribute("MODEL");
%>
<html>
<head>
<script language="javascript">

</script>
</head>
<body onUnload="javascript:parent.opener.bookmarkEnd('<%=model.get("pCourseId")%>','<%=model.get("pContentsId")%>','<%=model.get("pStartTime")%>')">
</body>
</html>

