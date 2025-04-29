<%@ page language="java"%>
<%@ page contentType="application/vnd.ms-excel; name='My_Excel', text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*, java.util.Map, javax.sql.RowSet, com.edutrack.framework.util.StringUtil"%>
<%@ page import ="com.edutrack.common.CommonUtil"%>
<%
	response.setHeader("Content-Disposition", "inline; filename=CurriSubCourse_"+CommonUtil.getCurrentDate().substring(0, 8)+".xls");
	response.setHeader("Content-Description", "JSP Generated Data");
	response.setHeader("Cache-Control", "public, max-age=86400");
	Map model = (Map)request.getAttribute("MODEL");
	int	curriYear = StringUtil.nvl(model.get("pCurriYear"), 2007);
	int	curriTerm = StringUtil.nvl(model.get("pCurriTerm"), 1);
%>
<HTML>
<HEAD>
<TITLE> New Document </TITLE>

</HEAD>

<BODY>
	<table width="670" align="center" border="1" bordercolor="black">
		<tr>
			<td colspan="10">
				<b><%=curriYear%>년 <%=curriTerm%>학기</b>
			</td>
		</tr>
		<tr>
			<td align="center">개설과정</td>
			<td align="center">교수자</td>
			<td align="center">수강생<br>(명)</td>
			<td align="center">질의응답<br>(건)</td>
			<td align="center">공지사항<br>(건)</td>
			<td align="center">면담<br>(요청건/답변건)</td>
			<td align="center">토론<br>(건)</td>
			<td align="center">과제<br>(건)</td>
			<td align="center">시험<br>(건)</td>
			<td align="center">평가여부</td>
		</tr>
<%
	RowSet	rs	=	(RowSet)model.get("StaticList");
	int		i	=	0;
	while(rs.next()) {
		i++;
%>
		<tr>
			<td><%=rs.getString("curri_name")%></td>
			<td align="center"><%=rs.getString("prof_name")%></td>
			<td align="center"><%=rs.getInt("student_cnt")%></td>
			<td align="center"><%=rs.getInt("qna_cnt")%></td>
			<td align="center"><%=rs.getInt("notice_cnt")%></td>
			<td align="center"><%=rs.getInt("req_cnt")%> / <%=rs.getInt("res_cnt")%></td>
			<td align="center"><%=rs.getInt("forum_cnt")%> / <%=rs.getInt("forum_contents_cnt")%></td>
			<td align="center"><%=rs.getInt("report_cnt")%></td>
			<td align="center"><%=rs.getInt("exam_cnt")%></td>
			<td align="center"><%=rs.getString("complete_yn")%></td>
		</tr>
<%
	}
	rs.close();
%>
	</table>
</BODY>
</HTML>
