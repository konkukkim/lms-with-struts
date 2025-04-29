<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/course_header.jsp" %>
<%
    String pCourseId = (String)model.get("pCourseId");
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ReportAdminWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_report/reportAdmin.js"></script>

							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"과제관리")%></b></font></td>
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
<form name="f" method="post" action="<%=CONTEXTPATH%>/ReportAdmin.cmd?cmd=reportWrite" >
<input type=hidden name="pMODE" value="">

<input type=hidden name="pReportId" value="">
<input type=hidden name="pReportSubject" value="">
<input type="hidden" name="pReportType1" value="A">

											<tr>
												<td colspan="13" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
<%
	if (COURSELISTSIZE > 1) {
%>
과목 :
<%
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
															<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" align="absmiddle" border="0">
															<%=CommonUtil.getCourseName(SYSTEMCODE,pCourseId)%><input type=hidden name=pCourseId value="<%=pCourseId%>">
<%
	}
%>
															</td>
															<td align=right width="50%" height=30>
<script language=javascript>Button5("과제추가", "Create_Report()", "");</script>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="13"></td>
											</tr>
											<tr class="s_tab02">
												<td width="40">번호</td>
												<td class="s_tablien"></td>
												<td width="257">과제명</td>
												<td class="s_tablien"></td>
												<td width="60">성적적용</td>
												<td class="s_tablien"></td>
												<td width="140">과제기간</td>
												<td class="s_tablien"></td>
												<td width="60">등록여부</td>
												<td class="s_tablien"></td>
												<td width="60">하위과제</td>
												<td class="s_tablien"></td>
												<td width="60">과제평가</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="13"></td>
											</tr>
											<tr>
												<td colspan="13">
													<!-- 리스트 -->
													<div id="reportList" style="width:100%;display:none"></div>
													<!-- 리스트 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="13"></td>
											</tr>
</form>
<!-- form end -->
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
