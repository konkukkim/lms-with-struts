<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/course_header.jsp" %>
<%
    String pCourseId = (String)model.get("pCourseId");
    String pBankCode = (String)model.get("pBankCode");
    String pReportId = (String)model.get("pReportId");
   	String pTeamMode = (String)model.get("pTeamMode");
	String pParentReportId = (String)model.get("pParentReportId");
%>
<!-- <%=pTeamMode%>--//--<%=pParentReportId%> -->
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ReportBankWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_report/reportBankContentsAdd.js"></script>

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
<form name="f" method="post" action="<%=CONTEXTPATH%>/ReportContents.cmd?cmd=reportBankContentsCopy" >
<input type="hidden" name="pGubun"      value="A">
<input type="hidden" name="checkYn"     value="N">
<input type="hidden" name="pCourseId"   value="<%=pCourseId%>">
<input type="hidden" name="pReportId"   value="<%=pReportId%>">
<input type="hidden" name="pReportType" value="">
<input type="hidden" name="pReportNo"   value="">
<input type="hidden" name="pTeamMode" value="<%=pTeamMode%>">
<input type="hidden" name="pParentReportId" value="<%=pParentReportId%>">

											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr class="s_tab05">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td width="120" class="s_tab_view01">항목명</td>
															<td height=30 class="s_tab_view02">
																<select name="pBankCode" onChange="autoReload()"></select>
															</td>
															<td width="120" class="s_tab_view01">문제번호</td>
															<td height=30 class="s_tab_view02">
																<input type="text" name="pReportOrder" size="5" value="0" >
															</td>
														</tr>
														<tr class="s_tab05">
															<td colspan="4"></td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td colspan="11" height="15"></td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="322">문제내용</td>
												<td class="s_tablien"></td>
												<td width="100">문제유형</td>
												<td class="s_tablien"></td>
												<td width="100">첨부파일</td>
												<td class="s_tablien"></td>
												<td width="74">선택</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11">
											  	<!-- 리스트 -->
											  		<div id="reportBankContentsList" style="width:100%;display:none"></div>
											  	<!-- 리스트 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="right">
<script language=javascript>Button3("선택출제", "addContents()", "");</script>
&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
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

