<%@ page language="java"%>
<%@ page contentType="application/vnd.ms-excel; name='My_Excel', text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*, java.util.Map, javax.sql.RowSet, com.edutrack.framework.util.StringUtil"%>
<%@ page import ="com.edutrack.framework.persist.ListDTO, com.edutrack.common.CommonUtil"%>
<%
	response.setHeader("Content-Disposition", "inline; filename=AllCurriSubInfo_"+CommonUtil.getCurrentDate().substring(0, 8)+".xls");
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
			<td colspan="12">
				<b><%=curriYear%>년 <%=curriTerm%>학기</b>
			</td>
		</tr>
		<tr>
			<td align="center" rowspan="2">개설과정</td>
			<td align="center" rowspan="2">개설학기</td>
			<td align="center" rowspan="2">강의수</td>
			<td align="center" colspan="3">수료인원 (단위:명)</td>
			<td align="center" colspan="3">미수료인원<br>(단위:명)</td>
			<td align="center" colspan="3">총교육인원<br>(단위:명)</td>
		</tr>

		<tr>
			<td align="center">남</td>
			<td align="center">여</td>
			<td align="center">합계</td>
			<td align="center">남</td>
			<td align="center">여</td>
			<td align="center">합계</td>
			<td align="center">남</td>
			<td align="center">여</td>
			<td align="center">합계</td>
		</tr>
<%
	ListDTO listObj = (ListDTO)model.get("StaticList");
	int No = listObj.getStartPageNum();
	int	i = 0;
	RowSet rs= listObj.getItemList();

	if (listObj.getItemCount() > 0) {
		while (rs.next()) {
			i++;
%>
		<tr>
			<td><%=rs.getString("curri_name")%></td>
			<td align="center"><%=rs.getInt("curri_term")%></td>
			<td align="center"><%=rs.getInt("contents_cnt")%>회</td>
			<td align="center"><%=rs.getInt("complete_man_cnt")%></td>
			<td align="center"><%=rs.getInt("complete_women_cnt")%></td>
			<td align="center"><%=rs.getInt("complete_total_cnt")%></td>
			<td align="center"><%=rs.getInt("no_man_cnt")%></td>
			<td align="center"><%=rs.getInt("no_women_cnt")%></td>
			<td align="center"><%=rs.getInt("no_total_cnt")%></td>
			<td align="center"><%=rs.getString("man_total_cnt")%></td>
			<td align="center"><%=rs.getString("women_total_cnt")%></td>
			<td align="center"><%=rs.getString("total_cnt")%></td>
		</tr>
<%
		}
	}
	rs.close();
%>
	</table>
</BODY>
</HTML>
