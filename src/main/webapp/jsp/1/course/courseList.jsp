<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.CommonUtil"%>
<%@include file="../common/header.jsp" %>

<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CourseWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course/course.js"></script>


										<!-- 내용 -->
<!-- 레이어 선언 -->
<div id="courseListDiv" style="display:block">
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center" border="0">
<form name="f" method="post" action="javascript:goSearch();" >
<input type="hidden" name="curPage"  value="">
											<tr>
												<td colspan="13" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%"></td>
															<td align=right width="50%" height=30>
<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %><script language=javascript>Button5("과목생성", "goAdd()", "");</script>&nbsp;<%	}	%>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="13"></td>
											</tr>
											<tr class="s_tab02">
												<td width="60">번호</td>
												<td class="s_tablien"></td>
												<td width="100">과목코드</td>
												<td class="s_tablien"></td>
												<td width="220">과목명</td>
												<td class="s_tablien"></td>
												<td width="70">교수자</td>
												<td class="s_tablien"></td>
												<td width="50">상태</td>
												<td class="s_tablien"></td>
												<td width="80">온/오프</td>
												<td class="s_tablien"></td>
												<td width="80">교재관리</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="13"></td>
											</tr>
											<tr>
												<td colspan="13">
													<!-- 리스트 -->
														<div id="courseList" style="width:100%;display:none"></div>
													<!-- 리스트 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="13"></td>
											</tr>
											<tr>
												<td height="10"> </td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="13" align=center>
													<table valign=top height="25">
														<tr>
															<td><!-- 페이징 -->
																<div id="getPagging" style="width:100%;display:none"></div>
															</td>
														</tr>
													</table>
													<table>
														<tr>
															<td align=middle height=30>
																<select name=pSTarget>
																	<option value=course_name selected>과목명</option>
																	<option value=course_id>과목ID</option>
																</select>
																<input maxlength=30 size=22 name=pSWord>
																<a href="javascript:autoReload();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle></a>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!-- // 페이지 리스트, 검색부분 -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->

</form>
</div>
<div id="courseWriteDiv" style="display:none">
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<form name="Input" method="post" action="<%=CONTEXTPATH%>/Course.cmd?cmd=courseRegist" enctype="multipart/form-data" onSubmit="return submitCheck()">
<input type="hidden" name="pRegMode" value="">
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과목코드</td>
												<td class="s_tab_view02" colspan="3" width="200"><input type="text" name="pCourseId" value="" onChange="checkCourseId();" size="24" maxlength="12" dispName="과목코드" notNull></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과목명</td>
												<td class="s_tab_view02" colspan="3"><input type="text" name="pCourseName" value="" maxlength="100" dispName="과목명" notNull size="50"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">교수자</td>
												<td class="s_tab_view02" colspan="3">
<%
	String profId = (String)model.get("profId");
	CodeParam param1 = new CodeParam();
	param1.setSystemcode(SYSTEMCODE);
	param1.setType("select");
	param1.setName("pProfId");
	param1.setFirst("-- 교수자선택--");
	//param1.setEvent("Change_Code()");
	param1.setSelected(profId);
	out.print(CommonUtil.getUserList(param1,"P"));
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">교안넓이+높이</td>
												<td class="s_tab_view02" colspan="3"">
													<input type="text" name="pContentsWidth" value="" size="4" maxlength="4" value="1024" dispName="교안넓이" notNull dataType="number">
													X
													<input type="text" name="pContentsHeight" value="" size="4" maxlength="4" value="768" dispName="교안높이" notNull dataType="number">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>											
											<tr>
												<td class="s_tab_view01" width="120">설정사이즈</td>
												<td class="s_tab_view02" colspan="3"">
													<input type="radio" name="pFlagNavi" value="Y" class="no" checked>
													사용
													<input type="radio" name="pFlagNavi" value="N" class="no">
													사용안함
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">교안유형</td>
												<td class="s_tab_view02" width="200">
													<input type="radio" name="pContentsType" value="R" class="no" checked>
													일반
													<input type="radio" name="pContentsType" value="S" class="no">
													스콤
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">온라인/오프라인<br>구분</td>
												<td class="s_tab_view02" width="200">
													<input type="radio" name="pOnlineYn" value="Y" class="no" checked>
													온라인
													<input type="radio" name="pOnlineYn" value="N" class="no">
													오프라인
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과목사용</td>
												<td class="s_tab_view02" width="200">
													<input type="radio" name="pFlagUse" value="Y" class="no" checked>
													사용
													<input type="radio" name="pFlagUse" value="N" class="no">
													사용안함
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>											
											<tr>
												<td class="s_tab_view01" width="120">과목이미지1</td>
												<td class="s_tab_view02" colspan="3"">
													<input type=file name="pTitleImg[1]" size="50"><div id="pTitleImg1Div" style="display:none">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과목이미지2</td>
												<td class="s_tab_view02" colspan="3"">
													<input type=file name="pTitleImg[2]" size="50"><div id="pTitleImg2Div" style="display:none">
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<div id="regButtonDiv" style="display:block">
	<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %><script language=javascript>Button3("등록", "registCourse('Add')", "");</script><%	}	%><% if(CommonUtil.getAuthorCheck(request,  "R"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button3("목록", "goList()", "목록");</script><%	}	%>
</div>
<div id="modButtonDiv" style="display:none">
	<% if(CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  { %><script language=javascript>Button3("수정", "registCourse('Edit')", "");</script><%	}	%><% if(CommonUtil.getAuthorCheck(request,  "D"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button3("삭제", "deleteCourse()", "");</script><%	}	%><% if(CommonUtil.getAuthorCheck(request,  "R"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button3("목록", "goList()", "목록");</script><%	}	%>
</div>
												</td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
</form>
</div>

									</td>
								</tr>
							</table>
						
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>');
</script>
<%@include file="../common/footer.jsp" %>