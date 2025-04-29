<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/course_header.jsp" %>
<%
    String pCourseId = (String)model.get("pCourseId");
    long curDate = Long.parseLong(CommonUtil.getCurrentDate());
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ReportAdminWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_report/reportAdmin.js"></script>

							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"과제")%></b></font></td>
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
<input type=hidden name="curDate" value="<%=curDate%>">
											<tr>
												<td colspan="11" class="s_btn01">
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
		param.setEvent("autoReloadStu()");
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
												<td width="268">과제명</td>
												<td class="s_tablien"></td>
												<td width="70">출제방식</td>
												<td class="s_tablien"></td>
												<td width="140">과제기간</td>
												<td class="s_tablien"></td>
												<td width="80">연장일자</td>
												<td class="s_tablien"></td>
												<td width="70">채점결과</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11">
													<!-- 리스트 -->
													<div id="reportStList" style="width:100%;display:none"></div>
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
	init('<%=CONTEXTPATH%>','<%=COURSELISTSIZE%>','<%=pCourseId%>','STU');
</script>
<%@include file="../common/course_footer.jsp" %>
