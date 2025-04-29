<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.dto.CodeParam"%>
<%@include file="../common/header.jsp" %>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/StudentWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/student/confirmStudent.js"></script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
<!-- main 시작   action="javascript:goSearch();"  -->
<form name="f" method="post">
<input type="hidden" name="curPage" value="">
<input type="hidden" name="pCurriCode" value="">
<input type="hidden" name="pCurriYear" value="">
<input type="hidden" name="pCurriTerm" value="">
														<tr>
															<td width="320">
<%
	CodeParam param1 = new CodeParam();
	param1.setSystemcode(SYSTEMCODE);
	param1.setType("select");
	param1.setName("pCurriYearTerm");
	param1.setFirst("-- 개설 과정 선택--");
	param1.setEvent("changeCurriSub()");
	param1.setSelected("");
	out.print(CommonUtil.getCurrentCurriList(param1,"",""));
%>
															</td>
															<td align=right width="350" height=30>
<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %><script language=javascript>Button5("수강생등록", "goAdd()", "");</script><%	}	%>
<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button5("일괄등록", "goAddFile()", "");</script><%	}	%>
<% if(CommonUtil.getAuthorCheck(request,  "D"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button5("선택삭제", "goSelDelete()", "");</script><%	}	%>
&nbsp;<script language=javascript>Button5("ExcelDown", "doExcelDown()", "");</script>&nbsp;
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td colspan="11" align="center">
<!--  박스 시작 -->
<div id="studentWrite" style="width:100%;display:none">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr class="s_tab05">
															<td colspan="3"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">학습자 ID</td>
															<td class="s_tab_view02" width="300">
																<input type=text name="pId" value="" size='70' maxlength="100">
															</td>
															<td align="right">
	<div id="chapterRegButton" style="display:block">
		<a href="javascript:registStudents();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_e.gif" border="0" class="no" align='absmiddle'></a>
        <a href="javascript:closeStudentWrite()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_d.gif" border="0" align='absmiddle'></a>
    </div>
															</td>
														</tr>
														<tr class="s_tab05">
															<td colspan="3"></td>
														</tr>
														<tr>
															<td colspan="3" height="25" valign="bottom">
																&nbsp;&nbsp;* 현재 진행중인 개설 과정이나 수강신청 중인 개설 과정에만 수강신청을 하실 수 있습니다.
															</td>
														</tr>
														<tr>
															<td colspan="3" height="30"></td>
														</tr>
													</table>
</div>
<!--  입력 박스 끝-->
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="100">이름(ID)</td>
												<td class="s_tablien"></td>
												<td width="280">수강과정</td>
												<td class="s_tablien"></td>
												<td width="140">수강신청일</td>
												<td class="s_tablien"></td>
												<td width="74"><a href="javascript:setAllToken(f.pChk)">[선택]</a></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11">
													<!-- 리스트 -->
														<div id="studentList" style="width:100%;display:no"></div>
													<!-- 리스트 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11" height="10" align="right">
												</td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
													<table valign=top height="25">
														<tr>
															<td>
																<!-- 페이징 -->
																<div id="getPagging" style="width:100%;display:no"></div>
															</td>
														</tr>
													</table>
													<table>
														<tr>
															<td align=middle height=30>
																<select name=pSTarget>
																	<option value=a.user_id selected>ID</option>
																	<option value=b.user_name>이름</option>
																	<option value=c.curri_name>과정명</option>
																</select>
																<input maxlength=30 size=22 name=pSWord>
																<a href="javascript:goSearch();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle></a>
															</td>
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
						
</form>
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>');
</script>
<%@include file="../common/footer.jsp" %>



