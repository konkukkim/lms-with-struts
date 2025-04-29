<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map,java.util.ArrayList"%>
<%@ page import ="com.edutrack.common.UserBroker,com.edutrack.framework.util.StringUtil"%>
<%
    Map model = (Map)request.getAttribute("MODEL");
    String SYSTEMCODE = UserBroker.getSystemCode(request);
    String CONTEXTPATH = request.getContextPath();
    String pCourseId = (String)model.get("pCourseId");
    String pExamId = (String)model.get("pExamId");
    int pConCnt = Integer.parseInt((String)model.get("pConCnt"));
    pConCnt++;
    String[] remainInfo = StringUtil.split((String)model.get("remainTime"),"/");
%>
	<html>
	<head>
	<title>시험시간체크</title>
	<%if(!remainInfo[1].equals("0")){%>
	  <meta http-equiv="refresh" content="60; url=/ExamAnswer.cmd?cmd=examAnswerTimeEdit&pCourseId=<%=pCourseId%>&pExamId=<%=pExamId%>&pConCnt=<%=pConCnt%>">
	<% } %>
	<script language="javascript">
		    function Exam_Alert(time){
				var	loc = "<%=CONTEXTPATH%>/ExamAnswer.cmd?cmd=examAlertShow&remainTime="+time;
				ShowInfo = window.open(loc,"info","left=162,top=100,toolbar=0,scrollbars=no,directories=0,status=0,menubar=0,width=470,height=220,resizable=no");
			}
    </script>
	</head>
	<body>
     <%
		if(remainInfo[0].equals("Y")){
			if (remainInfo[1].equals("10"))
			{
	%>
			<script>Exam_Alert("10");</script>
	<%
			}
			else if (remainInfo[1].equals("5"))
			{
	%>
			<script>Exam_Alert("5");</script>
	<%
			}
			//else if (remainInfo[1].equals("1"))
			//{
	%>
			<!-- script>Exam_Alert("1");</script -->
	<%
			//}
		}
     %>

	</body>
	</html>