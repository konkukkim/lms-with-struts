<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/course_header.jsp" %>
<%
    String pCourseId = (String)model.get("pCourseId");
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ExamAdminWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_exam/examAdmin.js"></script>
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
<!-- form start   -->
<form name="f"  method="post" action="<%=CONTEXTPATH%>/ExamAdmin.cmd?cmd=examWrite">
<input type=hidden name="pMODE" value="">
<input type=hidden name="pExamId" value="">

											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">과목 :
<%
	if (COURSELISTSIZE > 1) {
		CodeParam param = new CodeParam();
	   	param.setSystemcode(SYSTEMCODE);
	   	param.setType("select");
	   	param.setName("pCourseId");
	   	//param.setFirst("-- 과목 선택 --");
	   	param.setEvent("autoReloadProf()");
	   	param.setSelected(pCourseId);
	   	out.print(CommonUtil.getCurriCourse(param, CURRICODE,CURRIYEAR,CURRITERM,PROFID));

	} else {
%>
              													<%=CommonUtil.getCourseName(SYSTEMCODE,pCourseId)%><input type=hidden name=pCourseId value="<%=pCourseId%>">
<%
	}
%>
															</td>
															<td align=right width="50%" height=30>
<script language=javascript>Button5("시험추가", "Create_Exam()", "");</script>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="40">번호</td>
												<td class="s_tablien"></td>
												<td width="243">시험명 (문제출제)</td>
												<td class="s_tablien"></td>
												<td width="150">시험기간</td>
												<td class="s_tablien"></td>
												<td width="80">시간제한</td>
												<td class="s_tablien"></td>
												<td width="80">등록여부</td>
												<td class="s_tablien"></td>
												<td width="74">수정/삭제</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11">
													<!-- 리스트 -->
													<div id="examListDiv" style="width:100%;display:none"></div>
													<!-- 리스트 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="11"></td>
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
	init('<%=CONTEXTPATH%>','<%=COURSELISTSIZE%>','<%=pCourseId%>','PROF');
</script>
<%@include file="../common/course_footer.jsp" %>


