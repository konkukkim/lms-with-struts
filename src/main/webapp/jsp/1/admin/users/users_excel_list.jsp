<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map,javax.sql.RowSet, java.util.ArrayList"%> 
<%@ page import ="com.edutrack.common.UserBroker,com.edutrack.framework.util.DateTimeUtil,com.edutrack.framework.util.StringUtil"%>
<%@ page import ="com.edutrack.user.dto.UsersDTO"%> 
<% 
   response.setHeader("Content-Disposition", "attachment; filename=junnodaeMembersList.xls");
   response.setHeader("Content-Description", "JSP Generated Data");
%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0" width="96%">
	<tr>
		<td height="30" align="center"> <font size="3"><b>사용자관리</b></font> </td>
	</tr>
</table>
<table border="1" border_color="#000000" cellpadding="0" cellspacing="1" bgcolor="gray">
	<tr bgcolor="#f3f3f3">
		<td align="center" height="23">No</td>
		<td align="center">이름</td>
		<td align="center">아이디</td>
		<td align="center">비밀번호</td>
		<td align="center">주민번호</td>
		<td align="center">주소</td>
		<td align="center">전화번호</td>
		<td align="center">휴대전화</td>
		<td align="center">회사명</td>
		<td align="center">회사전화번호</td>
		<td align="center">회사주소</td>
		<td align="center">E-MAIL</td>
	</tr>
<%
		Map model = (Map)request.getAttribute("MODEL");
		ArrayList	list		=	(ArrayList)model.get("userList");
        UsersDTO	usersDto	=	new UsersDTO();
        int			No			=	list.size();

        for(int i=0; i < No; i++) {
				usersDto	=	(UsersDTO)list.get(i);
%>
		<tr bgcolor="#ffffff">
			<td align="center" height="20"><%=(No-i)%></td>
			<td align="center"><%=StringUtil.nvl(usersDto.getUserName())%></td>
			<td align="center"><%=StringUtil.nvl(usersDto.getUserId())%></td>
			<td align="center"><%=StringUtil.nvl(usersDto.getPasswd())%></td>
			<td align="center"><%=StringUtil.nvl(usersDto.getJuminNo())%></td>
			<td>(<%=StringUtil.nvl(usersDto.getPostCode())%>) <%=StringUtil.nvl(usersDto.getAddress())%></td>
			<td align="center"><%=StringUtil.nvl(usersDto.getPhoneHome())%></td>
			<td align="center"><%=StringUtil.nvl(usersDto.getPhoneMobile())%></td>
			<td align="center"><%=StringUtil.nvl(usersDto.getCompName())%></td>
			<td align="center"><%=StringUtil.nvl(usersDto.getCompPhone())%></td>
			<td>(<%=StringUtil.nvl(usersDto.getCompPostCode())%>) <%=StringUtil.nvl(usersDto.getCompAddress())%></td>
			<td><%=StringUtil.nvl(usersDto.getEmail())%></td>
		</tr>	
<%
		}
%>
</table>
</body>
</html>
