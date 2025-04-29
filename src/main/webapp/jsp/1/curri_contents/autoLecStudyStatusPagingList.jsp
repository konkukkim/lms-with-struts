<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/course_header.jsp" %>
<%
	String 	pViewType 	=	StringUtil.nvl(model.get("pViewType"));
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriContentsWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/curri_sub/lecStudyStatus.js"></script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"수강생 진도현황")%></b></font></td>
									<!-- // classroom title -->
									<!-- history -->
									<td class="c_stit_history" valign="bottom" align="right" width="327">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
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
<!-- form start    -->
<form name="f" method="post" action="javascript:autoReload()">
<input type="hidden" name="curPage" value="">
<input type="hidden" name="pViewType" value="<%=pViewType%>" >

											<tr>
												<td colspan="13" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
															</td>
															<td align=right width="50%" height=30>
<!--script language=javascript>Button5("전체학생보기", "goAllStuList()", "");</script-->
목록수 :
<select name="pDispLine" onChange="autoReload()">
	<option value="10">10</option>
	<option value="20">20</option>
	<option value="30">30</option>
	<option value="50">50</option>
	<option value="100">100</option>
</select>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="13"></td>
											</tr>
											<tr class="s_tab02">
												<td width="35">번호</td>
												<td class="s_tablien"></td>
												<td width="120">이름</td>
												<td class="s_tablien"></td>
												<td width="301">진도율</td>
												<td class="s_tablien"></td>
												<td width="70">과제<br>제출/출제</td>
												<td class="s_tablien"></td>
												<!--td width="70">시험<br>응시/출제</td>
												<td class="s_tablien"></td-->
												<td width="70">토론<br>참여/개설</td>
												<td class="s_tablien"></td>
												<td width="70">오프라인<br>출석/전체</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="13"></td>
											</tr>
											<tr>
												<td colspan="13">
													<!-- 리스트 -->
														<div id="lecStudyStatusListDiv" style="width:100%;display:none"></div>
													<!-- 리스트 -->
												</td>
											</tr>

											<tr class="s_tab05">
												<td colspan="13"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="13" height="10" align="right">

												</td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="13" align=center>
													<table valign=top height="25">
														<tr>
															<td><!-- 페이징 -->
																<div id="getPagging" style="width:100%;display:none"></div></td>
														</tr>
													</table>
													<table>
														<tr>
															<td align=middle height=30>
																<select name=pSearchKey>
																	<option value="user_name">이름</option>
					      											<option value="user_id">아이디</option>
																</select>
																<input maxlength=30 size=22 name="pKeyWord">
																<a href="javascript:autoReload()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle></a>
															</td>
														</tr>
													</table>
												</td>
											</tr>
</form>
<!-- form end -->
											<!-- // 페이지 리스트, 검색부분 -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>');
</script>
<%@include file="../common/course_footer.jsp" %>