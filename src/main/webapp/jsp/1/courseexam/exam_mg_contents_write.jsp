<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.courseexam.dto.ExamContentsDTO,com.edutrack.common.dto.EditorParam"%>
<%@include file="../common/course_header.jsp" %>
<%
    ExamContentsDTO contentsInfo = (ExamContentsDTO)model.get("contentsInfo");
    String pMODE = (String)model.get("pMODE");
    String pCourseId = (String)model.get("pCourseId");
    String pExamId = (String)model.get("pExamId");
    String pExamType = (String)model.get("pExamType");
    String pExamOrder = (String)model.get("pExamOrder");
    String pExamNo = (String)model.get("pExamNo");
    String answerUserCnt = (String)model.get("answerUserCnt");
    String answer = contentsInfo.getAnswer();
    String multiAnswer = contentsInfo.getMultiAnswer();
    String[] width = new String[]{"150","407"};

	String selJ = " selected";
	String selK ="";
	String selD ="";
	String selS ="";
	if(pExamType.equals("J")) selJ = " selected";
	if(pExamType.equals("K")) selK = " selected";
	if(pExamType.equals("D")) selD = " selected";
	if(pExamType.equals("S")) selS = " selected";
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ExamBankWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ExamContentsWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_exam/examMgContents.js"></script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"시험출제")%></b></font></td>
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
<form name="Input" method="post" enctype="multipart/form-data" action="javascript:goSubmit();">
<input type="hidden" name="pMODE"         value="<%=pMODE%>">
<input type="hidden" name="pCourseId"     value="<%=pCourseId%>">
<input type="hidden" name="pExamId"       value="<%=pExamId%>">
<input type="hidden" name="pExamNo"       value="<%=pExamNo%>">
<input type="hidden" name="answerUserCnt" value="<%=answerUserCnt%>">
<input type="hidden" name="pOldRfile" value="<%=contentsInfo.getRfileName()%>">
<input type="hidden" name="pOldSfile" value="<%=contentsInfo.getSfileName()%>">
<input type="hidden" name="pOldFilePath" value="<%=contentsInfo.getFilePath()%>">
<input type="hidden" name="pOldFileSize" value="<%=contentsInfo.getFileSize()%>">							
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
<%	//-- 입력일 경우
	if (pMODE.equals("ADD")) {	%>	
											<tr>
												<td class="s_tab_view01" width="125">문제유형</td>
												<td class="s_tab_view02">
													<select name='pExamType'onchange="changeExamType(this.value)">
														<option value='J' <%=selJ%>>서술형</option>
														<option value='K' <%=selK%>>선택형</option>
														<option value='D' <%=selD%>>단답형</option>
														<option value='S' <%=selS%>>OX형</option>
													</select>
												</td>
												<td class="s_tab_view01" width="125">문제번호</td>
												<td class="s_tab_view02">
													<input type="text" name="pExamOrder" size="3" value="<%=contentsInfo.getExamOrder()%>">
												</td>
											</tr>
<%	//-- 수정일 경우
	} else {	%>
											<tr>
												<td class="s_tab_view01" width="125">문제유형</td>
												<td class="s_tab_view02">
													<%=CommonUtil.getContentsTypeName(pExamType)%>
													<input type="hidden" name="pExamOrder" value="<%=contentsInfo.getExamOrder()%>">
													<input type="hidden" name="pExamType" value="<%=pExamType%>">
												</td>
												<td class="s_tab_view01" width="125">문제번호</td>
												<td class="s_tab_view02">
													<b><%=contentsInfo.getExamOrder()%></b>
												</td>
											</tr>
<%	}	%> 
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td colspan="4" height="20"></td>
											</tr>
										</table>
										<table width="670" align="center">
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">문제</td>
												<td class="s_tab_view03">
													<textarea name="pContents" cols="70" rows="10" wrap="VIRTUAL"><%=contentsInfo.getContents()%></textarea>
<%	EditorParam editerParam = new EditorParam();
	editerParam.setShowFlag("true");
	editerParam.setWidth(520);
	editerParam.setHeight(300);
	editerParam.setToolBarHidden("attachFile");
	out.print(CommonUtil.getEditorScript(editerParam));	%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">첨부파일</td>
												<td class="s_tab_view02">
<%
	if (!StringUtil.nvl(contentsInfo.getSfileName()).equals("")) {
		out.print("<div id='fileDiv' style='display:block'><a href=\"javascript:fileDownload('"+StringUtil.nvl(contentsInfo.getRfileName())+"','"+StringUtil.nvl(contentsInfo.getSfileName())+"','"+StringUtil.nvl(contentsInfo.getFilePath())+"','"+StringUtil.nvl(contentsInfo.getFileSize())+"');\">"+StringUtil.nvl(contentsInfo.getRfileName())+"</a>");
		out.print("&nbsp;&nbsp;");
		out.print("<a href='javascript:delFile()'>[기존파일삭제]</a>");
		out.print("</div>");
	}
%>
													<input type="file"   name="pFile" size="70">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td colspan="2">
<!-- 객관식 -->
<div id="examWriteK" style="display:none">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td width="105" class="s_tab_view01">보기 1</td>
															<td class="s_tab_view03">
																<textarea name="pExample1" cols="82" wrap="VIRTUAL"><%=StringUtil.nvl(contentsInfo.getExample1())%></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 2</td>
															<td class="s_tab_view03">
																<textarea name="pExample2" cols="82" wrap="VIRTUAL"><%=StringUtil.nvl(contentsInfo.getExample2())%></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 3</td>
															<td class="s_tab_view03">
																<textarea name="pExample3" cols="82" wrap="VIRTUAL"><%=StringUtil.nvl(contentsInfo.getExample3())%></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 4</td>
															<td class="s_tab_view03">
																<textarea name="pExample4" cols="82" wrap="VIRTUAL"><%=StringUtil.nvl(contentsInfo.getExample4())%></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">보기 5</td>
															<td class="s_tab_view03">
																<textarea name="pExample5" cols="82" wrap="VIRTUAL"><%=StringUtil.nvl(contentsInfo.getExample5())%></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td width="105" class="s_tab_view01">정답</td>
															<td class="s_tab_view03">
																<select name="pAnswerK">
								                                    <option>정답 수</option>
								                                    <option value=1 <%=(answer.equals("1") ? "selected" : "" ) %>>1개</option>
								                                    <option value=2 <%=(answer.equals("2") ? "selected" : "" ) %>>2개</option>
								                                    <option value=3 <%=(answer.equals("3") ? "selected" : "" ) %>>3개</option>
								                                    <option value=4 <%=(answer.equals("4") ? "selected" : "" ) %>>4개</option>
								                                    <option value=5 <%=(answer.equals("5") ? "selected" : "" ) %>>5개</option>
								                                </select>
								                                1. <input type='checkbox' name="pAnswers" value=1 class="solid0" <%=( (multiAnswer.indexOf("1") > -1)? "checked" : "" )%>>
								                                2. <input type='checkbox' name="pAnswers" value=2 class="solid0" <%=( (multiAnswer.indexOf("2") > -1)? "checked" : "" )%>>
								                                3. <input type='checkbox' name="pAnswers" value=3 class="solid0" <%=( (multiAnswer.indexOf("3") > -1)? "checked" : "" )%>>
								                                4. <input type='checkbox' name="pAnswers" value=4 class="solid0" <%=( (multiAnswer.indexOf("4") > -1)? "checked" : "" )%>>
								                                5. <input type='checkbox' name="pAnswers" value=5 class="solid0" <%=( (multiAnswer.indexOf("5") > -1)? "checked" : "" )%>>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
													</table>
</div>

<!-- 서술형 -->
<div id="examWriteJ" style="display:none">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td width="105" class="s_tab_view01">정답</td>
															<td class="s_tab_view03">
																<textarea name="pAnswerJ" rows="3" cols="82"><%=StringUtil.nvl(contentsInfo.getAnswer())%></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
													</table>
</div>
											
<!-- 단답형 -->
<div id="examWriteD" style="display:none">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td width="105" class="s_tab_view01">정답</td>
															<td class="s_tab_view03">
																<textarea name="pAnswerD" rows="3" cols="82"><%=StringUtil.nvl(contentsInfo.getAnswer())%></textarea>
																<br>&nbsp;☞ 단답형의 중복답안 처리에 대한 답안 구분은 파이프('|' Shift+￦)로 해 주세요
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
													</table>
</div>

<!-- OX형 -->
<div id="examWriteS" style="display:none">		
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td width="105" class="s_tab_view01">정답</td>
															<td class="s_tab_view03">
																맞음:<input type="radio" name="pAnswerS" value="O" style=border=0 <%if(StringUtil.nvl(contentsInfo.getAnswer()).equals("O")) out.print(" checked");%>>&nbsp;&nbsp;
																틀림:<input type="radio" name="pAnswerS" value="X" style=border=0 <%if(StringUtil.nvl(contentsInfo.getAnswer()).equals("X")) out.print(" checked");%>>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
													</table>
</div>
												</td>
											</tr>
											<tr>
												<td class="s_tab_view01">문제에 대한 해설</td>
												<td class="s_tab_view03"><textarea name="pComment" cols="82" rows=5 wrap="VIRTUAL"><%=contentsInfo.getComment()%></textarea></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
	if (pMODE.equals("ADD")) {
%>
											<tr>
												<td class="s_tab_view01">문제은행</td>
												<td class="s_tab_view02">
													* 등록하시는 문제를 문제은행에 자동 등록 하시겠습니까? 
													<input type="radio" name="pAutoBankRegist" value="Y" style=border=0 onClick="showBank('block')"> 예 
													<input type="radio" name="pAutoBankRegist" value="N" style=border=0 checked onClick="showBank('none')">아니오
<div id="examBankInfoDiv" style="width:100%;display:none">
<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<div id="examBankInfoSelectDiv" style="width:100%;display:block">
				<select name="pBankCode"></select>
				<a href="javascript:manageExamBankInfo();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/list_add.gif" width="53" height="17" align="absmiddle" vspace="2" hspace="5" border="0"></a> 
				&nbsp;<font color='blue'>* 항목선택 또는 항목을 추가하세요.</font>
			</div>
			<div id="examBankWriteDiv" style="width:100%;display:none">
				<table height="30" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<input type=text name="pBankName" value="" size="40" maxlength="100" >&nbsp;&nbsp;
						</td>
						<td>
							<a href="javascript:registExamBankInfo('ADD');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_e.gif" border="0" class="solid0" align="absmiddle"></a>
							<a href="javascript:closeExamBankInfoWrite();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_d.gif" border="0" align="absmiddle"></a>                   		                           		    	
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
</div>												
												</td>
											</tr>
<%
	}
%>								
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<div id="regButtonDiv" style="display:block">
<script language=javascript>Button3("등록", "goSubmit()", "");</script>
&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script></div>	
<div id="modButtonDiv" style="display:none">
<script language=javascript>Button3("수정", "goSubmit()", "");</script>
&nbsp;<script language=javascript>Button3("삭제", "goDelete()", "");</script>
&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
</div>
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
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pCourseId%>');
</script>
<%@include file="../common/course_footer.jsp" %>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->