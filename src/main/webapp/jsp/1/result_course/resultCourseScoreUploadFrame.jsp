<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map"%>
<%@ page import ="com.edutrack.common.CommonUtil"%>
<%
    Map model = (Map)request.getAttribute("MODEL");
    String pCourseId = (String) model.get("pCourseId");
%>
<html>
<head>
    <title>Score Upload</title>
    <meta http-equiv="expires" content="0">
</head>
<frameset rows="380,*" frameborder=0 border=0 framespacing=0>
	<frame name="score_write" src = "/ResultCourse.cmd?cmd=resultCourseScoreUploadForm&pCourseId=<%=pCourseId%>"
		marginwidth="0" marginheight="0" scrolling="auto">
	<frame name="score_insert" src = "/html/blank.html"  scrolling="auto">
</frameset>
</html>