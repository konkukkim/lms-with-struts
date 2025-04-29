<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map,javax.sql.RowSet, java.util.ArrayList"%> 
<%@ page import ="com.edutrack.common.UserBroker,com.edutrack.framework.util.DateTimeUtil,com.edutrack.framework.util.StringUtil"%>
<%@ page import ="com.edutrack.curristudent.dto.StudentDTO"%> 
<% 
   response.setHeader("Content-Disposition", "attachment; filename=junnodaeStudentList.xls");
   response.setHeader("Content-Description", "JSP Generated Data");
%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0" width="96%">
	<tr>
		<td height="30" align="center"> <font size="3"><b>수강생관리</b></font> </td>
	</tr>
</table>
<table border="1" border_color="#000000" cellpadding="0" cellspacing="1" bgcolor="gray">
	<tr bgcolor="#f3f3f3">
		<td align="center" height="23">No</td>
		<td align="center">이름</td>
		<td align="center">아이디</td>
		<td align="center">수강과정</td>
		<td align="center">수강년도</td>
		<td align="center">수강학기</td>
		<td align="center">수강신청일</td>
	</tr>
<%
		Map model = (Map)request.getAttribute("MODEL");
		ArrayList	list		=	(ArrayList)model.get("dataList");
        StudentDTO studentDto   =	new StudentDTO();
        
        for(int i=0; i < list.size(); i++) {
				studentDto	=	(StudentDTO)list.get(i);
%>
		<tr bgcolor="#ffffff">
			<td align="center" height="20"><%=i+1%></td>
			<td align="center"><%=StringUtil.nvl(studentDto.getUserName())%></td>
			<td align="center"><%=StringUtil.nvl(studentDto.getUserId())%></td>
			<td align="center"><%=StringUtil.nvl(studentDto.getCurriName())%></td>
			<td align="center"><%=studentDto.getCurriYear()%></td>
			<td align="center"><%=studentDto.getCurriTerm()%></td>
			<td align="center"><%=StringUtil.nvl(studentDto.getRegDate())%></td>
		</tr>	
<%
		}
%>
</table>
</body>
</html>
