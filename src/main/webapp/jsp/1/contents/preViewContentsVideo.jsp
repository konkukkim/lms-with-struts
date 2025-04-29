<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map"%>
<%
    String pFileUrl = request.getParameter("pFileUrl");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
	<script language="javascript">
//		alert('<%=pFileUrl%>');
	</script>
</head>
<body>
<table width="100%" height="100%" border="0">
	<tr>
		<td align="center" valign="middle">
			<object width="425" height="350">
			<embed src="<%=pFileUrl%>" type="application/x-shockwave-flash" wmode="transparent" width="425" height="350"></embed>
			</object>
		</td>
	</tr>
</table>
</body>
</html>