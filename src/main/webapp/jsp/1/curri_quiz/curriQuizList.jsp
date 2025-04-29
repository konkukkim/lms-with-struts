<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/header.jsp" %>
<% 	String pCurriCode = (String)model.get("pCurriCode");
	String pCurriYear = (String)model.get("pCurriYear");
	String pCurriTerm = (String)model.get("pCurriTerm");
	String pCourseId = (String)model.get("pCourseId");
	String pContentsId = (String)model.get("pContentsId");

%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriQuizWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/curri_sub/curriQuizList.js"></script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<form name='Input'>
<input type="hidden" name="pCurriCode" value="<%=pCurriCode%>">
<input type="hidden" name="pCurriYear" value="<%=pCurriYear%>">
<input type="hidden" name="pCurriTerm" value="<%=pCurriTerm%>">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%"></td>
															<td align=right width="50%" height=30>
<script language=javascript>Button5("목록", "goContentsList()", "");</script>&nbsp;<script language=javascript>Button5("문제추가", "goAdd()", "");</script>&nbsp;<script language=javascript>Button5("평가설정", "writeConfig()", "");</script>&nbsp;
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!-- 평가설정 박스 시작-->
											<tr>
												<td colspan="5">
													<div id="inputBox" style="width:100%;display:none">
													<table width="100%" align="center" cellpadding="0" cellspacing="0">
														<tr class="s_tab05">
															<td colspan="5"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">표시문제수</td>
															<td class="s_tab_view02" width="150"><input type=text name="pQuizCount" value="" size="3" maxlength="3" style="text-align:right;"> 문제</td>
															<td class="s_tab_view01" width="120">통과정답수</td>
															<td class="s_tab_view02" width="150"><input type=text name="pQuizPoint" value="" size="3" maxlength="3" style="text-align:right;"> 문제</td>
															<td class="s_tab_view02" align="right"><a href="javascript:registConfig();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_e.gif" border="0" align="absmiddle"></a>&nbsp;<a href="javascript:closeConfig();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_d.gif" border="0" align="absmiddle"></a></td>
														</tr>
														<tr class="s_tab05">
															<td colspan="5"></td>
														</tr>
														<tr><td colspan="5" height="10">&nbsp;</td></tr>
													</table>
													</div>
												</td>
											</tr>
											<!-- 평가설정 박스 끝-->
											<tr class="s_tab01">
												<td colspan="5"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="456">문제내용</td>
												<td class="s_tablien"></td>
												<td width="140">문제유형</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="5"></td>
											</tr>
<%
	int No = 0;
	RowSet list= (RowSet)model.get("rs");
	String strQuizType = "";
	while (list.next()) {
		No++;
		if(StringUtil.nvl(list.getString("quiz_type")).equals("K"))
			strQuizType = "선택형";
		if(StringUtil.nvl(list.getString("quiz_type")).equals("D"))
			strQuizType = "단답형";
		if(StringUtil.nvl(list.getString("quiz_type")).equals("S"))
			strQuizType = "OX형";

		if(No > 1) {
%>
											<tr class="s_tab03">
												<td colspan="5"></td>
											</tr>
<%		} %>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04"><a href="<%=CONTEXTPATH%>/CurriQuiz.cmd?cmd=curriQuizEdit&pCurriCode=<%=pCurriCode%>&pCurriYear=<%=pCurriYear%>&pCurriTerm=<%=pCurriTerm%>&pCourseId=<%=pCourseId%>&pContentsId=<%=pContentsId%>&pQuizOrder=<%=list.getInt("quiz_order")%>"><%=StringUtil.setMaxLength(StringUtil.nvl(list.getString("contents_text")),60)%></a></td>
												<td></td>
												<td class="s_tab04_cen"><%=strQuizType%></td>
											</tr>
<%
	}
	list.close();
	if (No == 0) {
%>
											<tr>
												<td class="s_tab04_cen" colspan="5">등록된 단원평가가 없습니다.</td>
											</tr>
<%
	}
%>
<input type='hidden' name='pCnt' value='<%=No%>'>
											<tr class="s_tab05">
												<td colspan="5"></td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pCourseId%>','<%=pContentsId%>');
</script>
<%@include file="../common/footer.jsp" %>