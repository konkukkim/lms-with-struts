癤?<%@ page contentType="text/html; charset=utf-8" %>
<%@ page isErrorPage="true" %>
<%@ page import="java.util.ArrayList" %>

<%
	ArrayList errors = new ArrayList();
	errors.add("ERROR: " + exception.getMessage());
	request.setAttribute("errors", errors);
%>

<jsp:include page="/jsp/1/search/error/error_print.jsp" flush="true" />

<!--
<script language="javascript">
	history.go(-1);
</script>
-->