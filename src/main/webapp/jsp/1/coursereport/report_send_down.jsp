<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map,java.util.ArrayList"%> 
<%@ page import ="com.edutrack.common.UserBroker,com.edutrack.common.CommonUtil"%>
<%@ page import ="com.edutrack.framework.util.StringUtil"%>
<%@ page import ="javax.sql.RowSet"%>
<%
	Map model = (Map)request.getAttribute("MODEL");

 	String pCourseId = (String)model.get("pCourseId");
    String pReportId = (String)model.get("pReportId");
    String pReportSubject = (String)model.get("pReportSubject");
    String pFileName	=	(String)model.get("pFileName");	

   	response.setHeader("Content-Disposition", "attachment; filename="+ pFileName+".xls");
   	response.setContentType("application/vnd.ms-excel");  

%>
<html>
<head>
	<title>과제평가 다운로드</title>
	<meta http-equiv="Content-Type" content="text/html; charset=euc-kr;">
</head>
<body>
<table border=0 cellpadding=0 cellspacing=0 width=96%>
	<tr>
		<td height=30 colspan="5"> <font size=3><b>과제물 평가결과</b></font> </td>
	</tr>
	<tr>
		<td colspan="5">▶과제명:<%=pReportSubject%></td>
	</tr>
	<tr>
		<td height=3 colspan="5"></td>
	</tr>
</table>
<table border=1 cellpadding=0 cellspacing=1 bgcolor="gray" width=96%>
	<tr bgcolor="#f3f3f3" align=center>
		<td height="23" width="20">No</td>
		<td width="30">이름</td>
		<td width="30">아이디</td>
		<td width="20">제출여부</td>
		<td width="20">점수</td>
	</tr>
<%	
	int No = 1;
	String apply = "";
	
	RowSet list = (RowSet)model.get("reportSendList"); 
	
	if (list != null) {  
		while(list.next()){
			if (StringUtil.nvl(list.getString("checkYn")).equals("")) {
				apply = "미제출";	
			} else {
				apply = "제출";
			}	
		
%>
		<tr bgcolor="#ffffff" align='center'>
			<td height=20><%=No++%></td>
			<td><%=StringUtil.nvl(list.getString("user_name"))%></td>
			<td><%=StringUtil.nvl(list.getString("user_id"))%></td>
			<td><%=apply%></td>
			<td><%=list.getInt("score")%>점</td>
		</tr>	
<%		
		}
		list.close();
	}
%>
</table>
</body>
</html>
