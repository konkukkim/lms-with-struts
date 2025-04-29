<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/course_header.jsp" %>
<%
    String pResId = (String)model.get("pResId");
    String pContentsResId = (String)model.get("pContentsResId");
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ResearchBankWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/curri_research/researchBankContentsAdd.js"></script>

								<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"설문 출제")%></b></font></td>
									<!-- // classroom title -->
									<!-- history -->
									<td class="c_stit_history" valign="bottom" align="right" width="327">
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
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name="f" method="post" action="<%=CONTEXTPATH%>/ResearchContents.cmd?cmd=researchBankContentsCopy" >
<input type="hidden" name="pGubun"          value="A">
<input type="hidden" name="checkYn"         value="N">
<input type="hidden" name="pContentsResId"  value="<%=pContentsResId%>">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr class="s_tab05">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td width="120" class="s_tab_view01">항목명</td>
															<td height=30 class="s_tab_view02">
																<select name="pResId" onChange="autoReload()"></select>
															</td>
														</tr>
														<tr class="s_tab05">
															<td colspan="4"></td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td colspan="11" height="15"></td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="322">문제내용</td>
												<td class="s_tablien"></td>
												<td width="100">문제유형</td>
												<td class="s_tablien"></td>
												<td width="74">선택</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11">
											  	<!-- 리스트 -->
											  		<div id="researchBankContentsList" style="width:100%;display:none"></div>
											  	<!-- 리스트 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="right">
<script language=javascript>Button3("선택출제", "addContents()", "");</script>
&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
												</td>
											</tr>
</form>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=USERID%>');
</script>
<%@include file="../common/course_footer.jsp" %>