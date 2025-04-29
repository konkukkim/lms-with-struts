<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map"%> 
<%@ page import ="com.edutrack.common.CommonUtil"%> 
<% String CONTEXTPATH = request.getContextPath(); %>
<%
    Map model = (Map)request.getAttribute("MODEL");
    String pCourseId = (String) model.get("pCourseId");
%>
<html>
<head>
    <title>Scorm Contents Manager</title>
    <meta http-equiv="expires" content="0">
</head>
<frameset rows="445,*" frameborder=0 border=0 framespacing=0>
	<frame name="scorm_contents_write" src = "<%=CONTEXTPATH%>/Contents.cmd?cmd=SCMUploadForm&pCourseId=<%=pCourseId%>"
		marginwidth="0" marginheight="0" scrolling="auto">
	<frame name="scorm_contents_insert" src = "<%=CONTEXTPATH%>/html/blank.html"  scrolling="no">
</frameset>
</html>