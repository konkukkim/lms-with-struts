<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp" %>
							<table width="685" border="0" height="100%" align="right">
								<tr valign="top">
									<!-- sub title -->
									<td height="34" class="stit_blet"><div class="stit_title"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/title/mtit5_01.gif" alt="타이틀" width="247" height="34"></div></td>
									<!-- // sub title -->
									<!-- history -->
									<td class="stit_history" valign="bottom" align="right" width="368">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (PMODE.equals("Search")) NAVIGATION = "홈 > 통합검색";
	if (NAVIGATION != "") {
		out.println(NAVIGATION) ;
	} // end if
%>
									</td>
									<!-- // history -->
								</tr>
<!-- 카테고리 모음 -->
								<tr valign="middle" height="50">
									<td colspan="2" valign="middle">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr valign="top">
		<td align="left"><a href="<%=CONTEXTPATH%>/CurriCategory.cmd?cmd=curriCategoryList&pStep=1&MENUNO=2"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/tle_manage11.gif" border="0"></a></td>
		<td align="center"><a href="<%=CONTEXTPATH%>/CurriCategory.cmd?cmd=curriCategoryList&pStep=2&MENUNO=2"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/tle_manage21.gif" border="0"></a></td>
		<td align="right"><a href="<%=CONTEXTPATH%>/CurriCategory.cmd?cmd=curriCategoryList&pStep=3&MENUNO=2"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/tle_manage31.gif" border="0"></a></td>
	</tr>
</table>
									</td>
								</tr>
								<!-- //  카테고리 모음 -->
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
									</td>
								</tr>

							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/footer.jsp" %>