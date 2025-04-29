<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map"%> 
<%@ page import ="com.edutrack.common.CommonUtil"%> 
<%
    Map model = (Map)request.getAttribute("MODEL");
    String pCourseId = (String) model.get("pCourseId");
    String pSetTarget = (String) model.get("pSetTarget");
%>
<html>
<head>
    <title>Score Upload</title>
    <meta http-equiv="expires" content="0">
</head>
<frameset rows="350,*" frameborder=0 border=0 framespacing=0>
	<frame name="score_write" src = "<%=CONTEXTPATH%>/html/Uploading.html"
		marginwidth="0" marginheight="0" scrolling="auto">
	<frame name="score_insert" src = "<%=CONTEXTPATH%>/ResultCourse.cmd?cmd=resultCourseScoreAutoSet&pCourseId=<%=pCourseId%>&pSetTarget=<%=pSetTarget%>"  scrolling="auto">
</frameset>
</html>