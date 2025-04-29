<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@include file="../common/community_header.jsp" %>
<script language="javascript">
	function goAdd()
	{
		document.location = "#";
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
<%   ListDTO listObj = (ListDTO)model.get("commCategoryPagingList");	%>
<%= listObj.getPageScript("f", "curPage", "goPage") %>
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
<script language=javascript>Button5("추가", "goAdd()", "");</script>&nbsp;
															</td>
														</tr>
													</table>
												</td>
											</tr>
<form name="f" method="post" action="/CommManage.cmd?cmd=getCommInfoPagingList" >
<input type="hidden" name="curPage" >
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
	int No = listObj.getStartPageNum();

	RowSet list= listObj.getItemList();
	if(listObj.getItemCount() > 0){
		while(list.next()){
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04_cen"><a href="Community.cmd?cmd=commPagingList&pCateCode=<%=list.getString("cate_code")%>&MENUNO=0" ><%=list.getString("cate_name")%></a></td>
												<td></td>
												<td class="s_tab04_cen"><%=list.getInt("cnt")%></td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1,list.getString("reg_date"))%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
			No = No-1;
		}
	} else {
%>
											<tr>
												<td class="s_tab04_cen" colspan="11">등록된 동아리 분류가 없습니다.</td>
											</tr>
<%	}
	list.close();
%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="10" align="right">
												</td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
													<table valign=top height="25">
														<tr>
															<td><%= listObj.getPagging(SYSTEMCODE,"goPage") %></td>
														</tr>
													</table>

												</td>
											</tr>
											<!-- // 페이지 리스트, 검색부분 -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
</form>
<%@include file="../common/community_footer.jsp" %>