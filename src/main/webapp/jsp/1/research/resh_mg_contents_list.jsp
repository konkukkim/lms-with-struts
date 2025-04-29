<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/course_header.jsp" %>
<%
    String pResId = (String)model.get("pResId");
    String answerUserCnt = (String)model.get("answerUserCnt");
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ResearchContentsWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/curri_research/researchMgContents.js"></script>

							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"설문관리")%></b></font></td>
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
<!-- form start -->
<form name="f" method="post" >
<input type=hidden name="pMODE"         value="">
<input type=hidden name="pGubun"        value="A">
<input type=hidden name="pResId"        value="<%=pResId%>">
<input type=hidden name="pResNo"        value="">
<input type=hidden name="pAnswerUserCnt" value="<%=answerUserCnt%>">
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
<!-- 문제 입력/수정 시 -->
<div id="researchContentsWriteDIV" style="width:100%;display:none">
										<table width="670" align="center">
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<!-- 유형선택 -->
											<tr>
												<td class="s_tab_view01" width="105">문제유형</td>
												<td class="s_tab_view02">
													<select name='pContentsType' onchange="changeContentsType(this.value)">
														<option value='J' >서술형</option>
														<option value='K' >선택형</option>
														<option value='S' >OX형</option>
													</select>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">문제</td>
												<td class="s_tab_view03"><textarea name="pContents" cols="75" rows="6" wrap="VIRTUAL"></textarea></td>
											</tr>
											<tr>
												<td colspan="2">
	<!-- 선택형 -->
	<div id="researchWriteK" style="display:none">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 1</td>
															<td class="s_tab_view03">
																<textarea name="pExample1" cols="75" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 2</td>
															<td class="s_tab_view03">
																<textarea name="pExample2" cols="75" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 3</td>
															<td class="s_tab_view03">
																<textarea name="pExample3" cols="75" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 4</td>
															<td class="s_tab_view03">
																<textarea name="pExample4" cols="75" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 5</td>
															<td class="s_tab_view03">
																<textarea name="pExample5" cols="75" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>

														<tr>
															<td width="105" class="s_tab_view01">보기 6</td>
															<td class="s_tab_view03">
																<textarea name="pExample6" cols="75" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 7</td>
															<td class="s_tab_view03">
																<textarea name="pExample7" cols="75" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 8</td>
															<td class="s_tab_view03">
																<textarea name="pExample8" cols="75" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 9</td>
															<td class="s_tab_view03">
																<textarea name="pExample9" cols="75" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 10</td>
															<td class="s_tab_view03">
																<textarea name="pExample10" cols="75" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">정답</td>
															<td class="s_tab_view03">
																<input type="radio" name="pExampleNum" value="O" style=border=0 class="no" checked>한개&nbsp;&nbsp;
																<input type="radio" name="pExampleNum" value="X" style=border=0 class="no" >한개이상
															</td>
														</tr>
													</table>
	</div>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" align="right">
	<div id="regButtonContentsDiv" style="display:block">
<script language=javascript>Button3("등록", "registResearchContents('write')", "");</script>
&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
	</div>
	<div id="modButtonContentsDiv" style="display:none">
<script language=javascript>Button3("수정", "registResearchContents('edit')", "");</script>
&nbsp;<script language=javascript>Button3("삭제", "deleteResearchContents()", "");</script>
&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
	</div>
												</td>
											</tr>
										</table>
</div>
<!-- 문제 입력/수정 끝 -->
<!-- 문제 리스트 시작 -->
<div id="researchContentsListDIV" style="width:100%;display:block">
										<table width="670" align="center">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
															</td>
															<td align=right width="50%" height=30>
	<script language=javascript>Button5("문제은행보기", "addBankContents()", "");</script>&nbsp;<script language=javascript>Button5("문제추가", "goAdd()", "");</script>
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
												<td width="80">문제번호</td>
												<td class="s_tablien"></td>
												<td width="417">문제</td>
												<td class="s_tablien"></td>
												<td width="80">문제유형</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="7"></td>
											</tr>
											<tr class="">
												<td colspan="7">
												<!-- 리스트 -->
											  		<div id="researchContentsList" style="width:100%;display:none"></div>
											  	<!-- 리스트 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="right">
<script language=javascript>Button3("미리보기", "contentsPreview()", "");</script>
&nbsp;<script language=javascript>Button3("설문등록", "confirmResearchContents()", "");</script>
&nbsp;<script language=javascript>Button3("목록", "goResInfoList()", "");</script>
												</td>
											</tr>
										</table>
</div>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
</form>
							</table>
						</td>
						<!-- // 본문 -->
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pResId%>');
</script>
<%@include file="../common/course_footer.jsp" %>

























