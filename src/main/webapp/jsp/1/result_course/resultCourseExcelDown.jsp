<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map,javax.sql.RowSet"%> 
<%@ page import ="com.edutrack.common.UserBroker,com.edutrack.framework.util.StringUtil"%>
<% 
   response.setHeader("Content-Disposition", "attachment; filename=resultCourse.xls");
   response.setHeader("Content-Description", "JSP Generated Data");
   
    Map model = (Map)request.getAttribute("MODEL");
    
	RowSet courseInfo = (RowSet)model.get("curriCourseInfo");
	courseInfo.next();
	int examBase = courseInfo.getInt("exam_base");
	int reportBase = courseInfo.getInt("report_base");
	int attendBase = courseInfo.getInt("attend_base");
	int forumBase = courseInfo.getInt("forum_base");
	int etc1Base = courseInfo.getInt("etc1_base");
	int etc2Base = courseInfo.getInt("etc2_base");
	courseInfo.close();
 	RowSet list = (RowSet)model.get("resultCourseList"); 
%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body>
<table border=0 cellpadding=0 cellspacing=0 width=96%>
	<tr>
		<td height=30 align="center"> <font size=3><b>과목 평가 현황</b></font> </td>
	</tr>
</table>
<table border=1 cellpadding=0 cellspacing=0 bordercolor="gray" width=96%>
	<tr bgcolor="#f3f3f3">
		<td align=center height=23>No</td>
		<td align=center>수강생</td>
		<td align=center>시험(<%=examBase%>)</td>
		<td align=center>과제(<%=reportBase%>)</td>
		<td align=center>출석(<%=attendBase%>)</td>
		<td align=center>토론(<%=forumBase%>)</td>
		<td align=center>그룹공부(<%=etc1Base%>)</td>
		<td align=center>기타(<%=etc2Base%>)</td>
		<td align=center>총점(100)</td>
	</tr>
<%
int studentCnt = Integer.parseInt((String)model.get("studentCnt"))+1;
int No = studentCnt;
while(list.next()){
		No--;
	%>
	<tr bgcolor="#ffffff">
		<td align=center height=20><%=No%></td>
		<td align=center><%=StringUtil.nvl(list.getString("user_name"))%>(<%=StringUtil.nvl(list.getString("user_id"))%>)</td>
		<td align=center><%=list.getDouble("score_exam")%></td>
		<td align=center><%=list.getDouble("score_report")%></td>
		<td align=center><%=list.getDouble("score_attend")%></td>
		<td align=center><%=list.getDouble("score_forum")%></td>
		<td align=center><%=list.getDouble("score_etc1")%></td>
		<td align=center><%=list.getDouble("score_etc2")%></td>
		<td align=center><%=list.getDouble("score_total")%></td>
	</tr>	
	<%

}
list.close();
if(studentCnt == 0)
{
%>		
	<TR>
		<TD colspan="8" align=center bgcolor="#FFFFFF" height="25"> 등록된 수강생이 없습니다.</TD>
	</TR>
<%}%>
</table>
</body>
</html>
