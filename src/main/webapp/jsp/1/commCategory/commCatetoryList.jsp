<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/community_header.jsp" %>
<%
	String	pMode	=	StringUtil.nvl(model.get("pMode"));
%>
<script language="javascript">
	function goAdd() {
		document.location = "<%=CONTEXTPATH%>/CommCategory.cmd?cmd=commCategoryWrite&pMode=<%=pMode%>&MENUNO=0";
	}
</script>
								<tr valign="top">
									<td colspan="2" height="10"></td>
								</tr>
								<tr valign="top">
									<!-- community title -->
									<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b>동아리 분류관리</b></font></td>
									<!-- // community title -->
									<!-- history -->
									<td class="com_stit_history" valign="bottom" align="right" width="327">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (PMODE.equals("Search")) NAVIGATION = "홈 > 통합검색";
	if (NAVIGATION != "") {
		out.println(NAVIGATION) ;
	} // end if
%>
									</td>
								</tr>
								<tr valign="top">
									<td colspan="2" class="com_content_top">
									<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
															</td>
															<td align=right width="50%" height=30>
<script language=javascript>Button5("추가", "goAdd()", "");</script>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="150">동아리 분류코드</td>
												<td class="s_tablien"></td>
												<td width="">동아리 분류명</td>
												<td class="s_tablien"></td>
												<td width="100">수정/삭제</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	int No = 0;
	RowSet list= (RowSet)model.get("rs");

	while(list.next()){
		No = No+1;
		if(No > 1) {
%>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%		} %>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04_cen"><%=list.getString("cate_code")%></td>
												<td></td>
												<td class="s_tab04_cen"><%=list.getString("cate_name")%></td>
												<td></td>
												<td class="s_tab04_cen"><a href="/CommCategory.cmd?cmd=commCategoryEdit&pCateCode=<%=list.getString("cate_code")%>"><b>[수정/삭제]</b></a></td>
											</tr>
<%
	}
	list.close();

	if ( No < 1 ) {
%>
											<tr>
												<td class="s_tab04_cen" colspan="11">등록된 분류가 없습니다.</td>
											</tr>
<%
	}
%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/community_footer.jsp" %>