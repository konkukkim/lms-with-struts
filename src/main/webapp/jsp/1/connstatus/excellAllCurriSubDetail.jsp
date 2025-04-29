<%@ page language="java"%>
<%@ page contentType="application/vnd.ms-excel; name='My_Excel', text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*, java.util.Map, javax.sql.RowSet, com.edutrack.framework.util.StringUtil"%>
<%@ page import ="com.edutrack.common.CommonUtil"%>
<%
	response.setHeader("Content-Disposition", "inline; filename=AllCurriSubDetail_"+CommonUtil.getCurrentDate().substring(0, 8)+".xls");
	response.setHeader("Content-Description", "JSP Generated Data");
	response.setHeader("Cache-Control", "public, max-age=86400");
	Map model = (Map)request.getAttribute("MODEL");
	int	curriYear = StringUtil.nvl(model.get("pCurriYear"), 2007);
	int	curriTerm = StringUtil.nvl(model.get("pCurriTerm"), 1);
	String curriName = StringUtil.nvl(model.get("pCurriName"));
	String ageYn = StringUtil.nvl(model.get("pAgeYn"));
%>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<TITLE> New Document </TITLE>

</HEAD>

<BODY>
	<table width="670" align="center" border="1" bordercolor="black">
		<tr>
			<td colspan="10">
				<b><%=curriName%>&nbsp;&nbsp;<%=curriYear%>년 <%=curriTerm%>학기</b>
			</td>
		</tr>
		<tr>
			<td align="center" rowspan="2"><%if(ageYn.equals("Y")){%>연령별<%}else{%>지역별<%}%></td>
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
	RowSet	rs	=	(RowSet)model.get("StaticList");
	int		i	=	0;
	while(rs.next()) {
		i++;
%>
		<tr>
			<td align="center"><%=rs.getString("so_name")%></td>
			<td align="center"><%=rs.getInt("pass_man")%></td>
			<td align="center"><%=rs.getInt("pass_women")%></td>
			<td align="center"><%=rs.getInt("pass_total")%></td>
			<td align="center"><%=rs.getInt("np_man")%></td>
			<td align="center"><%=rs.getInt("np_women")%></td>
			<td align="center"><%=rs.getInt("np_total")%></td>
			<td align="center"><%=rs.getString("total_man")%></td>
			<td align="center"><%=rs.getString("total_women")%></td>
			<td align="center"><%=rs.getString("total_cnt")%></td>
		</tr>
<%
	}
	rs.close();
%>
	</table>
</BODY>
</HTML>
