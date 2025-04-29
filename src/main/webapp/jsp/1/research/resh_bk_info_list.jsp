<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/course_header.jsp" %>
<%
    String pResId = (String)model.get("pResId");
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ResearchBankWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/curri_research/researchBank.js"></script>

								<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"설문문제은행관리")%></b></font></td>
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
<form name="f" method="post" >
<input type="hidden" name="pMODE" value="">
<input type="hidden" name="pGubun" value="M">
<input type="hidden" name="pResNo" value="">

											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">항목명</td>
												<td class="s_tab_view02" colspan="3">
													<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td>
<div id="researchBankInfoSelectDiv" style="width:100%;display:block">
	<select name="pResId" onChange="autoReload()"></select>
	<a href="javascript:manageResearchBankInfo('write');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_item01.gif" align="absmiddle" vspace="2" hspace="5" border="0"></a>
	<a href="javascript:manageResearchBankInfo('edit');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_item02.gif" align="absmiddle" vspace="2" hspace="5"></a>
	<a href="javascript:manageResearchBankInfo('delete')"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_item03.gif" align="absmiddle" vspace="2" hspace="5"></a>
</div>
<div id="researchBankWriteDiv" style="width:100%;display:none">
<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<input type=text name="pSubject" value="" size="40" maxlength="100" >
			<input type="radio" name="pShareYn" value="Y" class="solid0" checked >공유함&nbsp;&nbsp;
			<input type="radio" name="pShareYn" value="N" class="solid0" >공유안함
		</td>
		<td align="left">
			<div id="regButtonDiv" style="display:block">
				<a href="javascript:registResearchBankInfo('write');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_e.gif" border="0" class="solid0" align="absmiddle"></a>
				<a href="javascript:closeResearchBankInfoWrite();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_d.gif" border="0" align="absmiddle"></a>
			</div>
			<div id="modButtonDiv" style="display:none">
				<a href="javascript:registResearchBankInfo('edit');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_c.gif" border="0" class="solid0" align="absmiddle"></a>
				<a href="javascript:closeResearchBankInfoWrite();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_d.gif" border="0" align="absmiddle"></a>
			</div>
		</td>
	</tr>
</table>
</div>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td height="15" colspan="4"></td>
											</tr>
										</table>
<div id="researchBankContentsWriteDIV" style="width:100%;display:none">
										<table width="670" align="center">
											<tr>
												<td align="right" valign="top" height="25"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/text_b01.gif" width="84" height="16" border="0">
													<select name='pContentsType' onchange="changeContentsType(this.value)">
														<option value='J' >서술형</option>
														<option value='K' >선택형</option>
														<option value='S' >OX형</option>
													</select>
												</td>
											</tr>
										</table>
										<table width="670" align="center">
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">문제</td>
												<td class="s_tab_view03">
													<textarea name="pContents" cols="70" rows="6" wrap="VIRTUAL"></textarea>
												</td>
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
																<textarea name="pExample1" cols="70" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 2</td>
															<td class="s_tab_view03">
																<textarea name="pExample2" cols="70" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 3</td>
															<td class="s_tab_view03">
																<textarea name="pExample3" cols="70" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 4</td>
															<td class="s_tab_view03">
																<textarea name="pExample4" cols="70" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 5</td>
															<td class="s_tab_view03">
																<textarea name="pExample5" cols="70" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>

														<tr>
															<td width="105" class="s_tab_view01">보기 6</td>
															<td class="s_tab_view03">
																<textarea name="pExample6" cols="70" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 7</td>
															<td class="s_tab_view03">
																<textarea name="pExample7" cols="70" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 8</td>
															<td class="s_tab_view03">
																<textarea name="pExample8" cols="70" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 9</td>
															<td class="s_tab_view03">
																<textarea name="pExample9" cols="70" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 10</td>
															<td class="s_tab_view03">
																<textarea name="pExample10" cols="70" wrap="VIRTUAL"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">정답</td>
															<td class="s_tab_view03">
																<input type="radio" name="pExampleNum" value="O" style=border=0 class="solid0" checked>한개&nbsp;&nbsp;
																<input type="radio" name="pExampleNum" value="X" style=border=0 class="solid0" >한개이상
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
												<td class="s_list_btn" colspan="11" height="30" align="right">
	<div id="regButtonContentsDiv" style="display:block">
<script language=javascript>Button3("등록", "registResearchBkContents('write')", "");</script>
&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
	</div>
	<div id="modButtonContentsDiv" style="display:none">
<script language=javascript>Button3("수정", "registResearchBkContents('edit')", "");</script>
&nbsp;<script language=javascript>Button3("삭제", "deleteResearchBkContents()", "");</script>
&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
		</div>
												</td>
											</tr>
										</table>
</div>

<div id="researchBankContentsListDIV" style="width:100%;display:block">
										<table width="670" align="center">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
															</td>
															<td align=right width="50%" height=30>
																<script language=javascript>Button5("문제추가", "goAdd()", "");</script>&nbsp;
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
												<td width="498">문제내용</td>
												<td class="s_tablien"></td>
												<td width="100">문제유형</td>
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
										</table>
</div>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=USERID%>','<%=pResId%>');
</script>
<%@include file="../common/course_footer.jsp" %>






























