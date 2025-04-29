<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.curritop.dto.QuizDTO,com.edutrack.common.dto.EditorParam"%>
<%@include file="../common/header.jsp" %>
<% 	String pCourseId = (String)model.get("pCourseId");
	String pContentsId = (String)model.get("pContentsId");
	String pQuizOrder = (String)model.get("pQuizOrder");
	String pQuizType = (String)model.get("pQuizType");
	String[] width = new String[]{"150","407"};
	QuizDTO quizDto= (QuizDTO)model.get("quizDto");

	if(pQuizType.equals("")) pQuizType = StringUtil.nvl(quizDto.getQuizType());

	String selK = ""; String selD =""; String selS = "";
	if(pQuizType.equals("K")) selK = " selected";
	if(pQuizType.equals("D")) selD = " selected";
	if(pQuizType.equals("S")) selS = " selected";
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/QuizWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course/quizEdit.js"></script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- FROM STATRT    action = "javascript:goSubmit();" -->
<form method="post" name="Input" enctype="multipart/form-data" >
<input type='hidden' name='pCourseId' value='<%=pCourseId%>'>
<input type='hidden' name='pContentsId' value='<%=pContentsId%>'>
<input type='hidden' name='pQuizOrder' value='<%=pQuizOrder%>'>
<input type="hidden" name="pOldrFileName" value="<%=StringUtil.nvl(quizDto.getRfileName())%>">
<input type="hidden" name="pOldsFileName" value="<%=StringUtil.nvl(quizDto.getSfileName())%>">
<input type="hidden" name="pOldFilePath" value="<%=StringUtil.nvl(quizDto.getFilePath())%>">
<input type="hidden" name="pOldFileSize" value="<%=StringUtil.nvl(quizDto.getFileSize())%>">
											<tr>
												<td colspan="2" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%"></td>
															<td align=right width="50%" height=30>
<select name='pQuizType'onchange="change_type(this.value)">
	<option value='K' <%=selK%>>선택형</option>
	<option value='D' <%=selD%>>단답형</option>
	<option value='S' <%=selS%>>OX형</option>
</select>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">문제</td>
												<td class="s_tab_view03" width="200"><textarea name="pContents" rows=9 cols=77><%=StringUtil.nvl(quizDto.getContents())%></textarea>
<%
	EditorParam editerParam = new EditorParam();
	editerParam.setShowFlag("true");
	editerParam.setWidth(520);
	editerParam.setHeight(200);
	editerParam.setToolBarHidden("attachFile");
	out.print(CommonUtil.getEditorScript(editerParam));
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">첨부화일</td>
												<td class="s_tab_view03">
<%
	if (!StringUtil.nvl(quizDto.getSfileName()).equals("")) {
		out.print("<div id='fileDiv' style='display:block'><a href=\"javascript:fileDownload('"+StringUtil.nvl(quizDto.getRfileName())+"','"+StringUtil.nvl(quizDto.getSfileName())+"','"+StringUtil.nvl(quizDto.getFilePath())+"','"+StringUtil.nvl(quizDto.getFileSize())+"');\">"+StringUtil.nvl(quizDto.getRfileName())+"</a>");
		out.print("&nbsp;&nbsp;");
		out.print("<a href='javascript:delFile()'>[기존파일삭제]</a>");
		out.print("</div>");
	}
%>
													<input type="file" name="pFile" size="60"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>

											<tr>
												<td colspan="2">
<!-- 객관식 -->
<div id="quizWriteK" style="display:block">
													<table width="100%" cellpadding="0" cellspacing="0" border="0">
														<tr>
															<td class="s_tab_view01" width="120">보기1</td>
															<td class="s_tab_view03"><textarea name="pExample1" cols="77" wrap="VIRTUAL"><%=StringUtil.nvl(quizDto.getExample1())%></textarea></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">보기2</td>
															<td class="s_tab_view03"><textarea name="pExample2" cols="77" wrap="VIRTUAL"><%=StringUtil.nvl(quizDto.getExample2())%></textarea></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">보기3</td>
															<td class="s_tab_view03"><textarea name="pExample3" cols="77" wrap="VIRTUAL"><%=StringUtil.nvl(quizDto.getExample3())%></textarea></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">보기4</td>
															<td class="s_tab_view03"><textarea name="pExample4" cols="77" wrap="VIRTUAL"><%=StringUtil.nvl(quizDto.getExample4())%></textarea></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">보기5</td>
															<td class="s_tab_view03"><textarea name="pExample5" cols="77" wrap="VIRTUAL"><%=StringUtil.nvl(quizDto.getExample5())%></textarea></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">정답</td>
															<td class="s_tab_view02">
																1.<input type='radio' name="pAnswerK" style=border:0 value='1' <%if(StringUtil.nvl(quizDto.getAnswer()).equals("1")) out.print(" checked");%>>
																2.<input type='radio' name="pAnswerK" style=border:0 value='2' <%if(StringUtil.nvl(quizDto.getAnswer()).equals("2")) out.print(" checked");%>>
																3.<input type='radio' name="pAnswerK" style=border:0 value='3' <%if(StringUtil.nvl(quizDto.getAnswer()).equals("3")) out.print(" checked");%>>
																4.<input type='radio' name="pAnswerK" style=border:0 value='4' <%if(StringUtil.nvl(quizDto.getAnswer()).equals("4")) out.print(" checked");%>>
																5.<input type='radio' name="pAnswerK" style=border:0 value='5' <%if(StringUtil.nvl(quizDto.getAnswer()).equals("5")) out.print(" checked");%>>
															</td>
														</tr>
													</table>
</div>
<!-- 단답형 -->
<div id="quizWriteD" style="display:none">
													<table width="100%" cellpadding="0" cellspacing="0" border="0">
														<tr>
															<td class="s_tab_view01" width="120">정답</td>
															<td class="s_tab_view03"><textarea name="pAnswerD" rows="3" cols="77"><%=StringUtil.nvl(quizDto.getAnswer())%></textarea></td>
														</tr>
													</table>
</div>
<!-- OX형 -->
<div id="quizWriteS" style="display:none">
													<table width="100%" cellpadding="0" cellspacing="0" border="0">
														<tr>
															<td class="s_tab_view01" width="120">정답</td>
															<td class="s_tab_view02">
																맞음:<input type="radio" name="pAnswerS" value="O" style=border=0 <%if(StringUtil.nvl(quizDto.getAnswer()).equals("O")) out.print(" checked");%>>&nbsp;&nbsp;
																틀림:<input type="radio" name="pAnswerS" value="X" style=border=0 <%if(StringUtil.nvl(quizDto.getAnswer()).equals("X")) out.print(" checked");%>>
															</td>
														</tr>
													</table>
</div>
												</td>
											</tr>

											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">문제에 대한 해설</td>
												<td class="s_tab_view03"><textarea name="pComment" rows="4" cols="77"><%=StringUtil.nvl(quizDto.getComment())%></textarea></td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("수정", "goSubmit()", "");</script>&nbsp;<script language=javascript>Button3("삭제", "goDelete()", "");</script>&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
												</td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<%@include file="../common/footer.jsp" %>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->