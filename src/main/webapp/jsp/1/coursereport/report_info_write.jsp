<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/course_header.jsp" %>
<%@ page import ="com.edutrack.coursereport.dto.ReportInfoDTO, com.edutrack.common.DateSetter, com.edutrack.common.dto.DateParam"%>
<%@ page import ="com.edutrack.common.dto.EditorParam"%>
<%
    ReportInfoDTO	reportInfo		=	(ReportInfoDTO)model.get("reportInfo");
    String 			pMODE			=	(String)model.get("pMODE");
    String 			pCourseId 		=	(String)model.get("pCourseId");
    String 			pReportId 		= 	(String)model.get("pReportId");
    String 			pBankId 		= 	(String)model.get("pBankId");
	String 			pSubReprotCnt	=	(String)model.get("pSubReprotCnt");
    String[] 		width			=	new String[]{"190","647"};
	String			reportSubject	=	StringUtil.nvl(reportInfo.getReportSubject(), "");
	String			reportContents	=	StringUtil.nvl(reportInfo.getReportContents(), "");

	String 			reportType2		=	StringUtil.nvl(reportInfo.getReportType2());

	String 			pReportSendDelYn = (String)model.get("pReportSendDelYn");

%>

<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ReportBankWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_report/reportInfo.js"></script>

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
<form name="Regist" method="post" enctype="multipart/form-data" action="<%=CONTEXTPATH%>/ReportAdmin.cmd?cmd=reportRegist">
<input type="hidden" name="pMODE" value="<%=pMODE%>">
<input type="hidden" name="pCourseId" value="<%=pCourseId%>">
<input type="hidden" name="pReportId" value="<%=pReportId%>">
<input type="hidden" name="pReportType1" value="A">

											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>

											<tr>
												<td class="s_tab_view01" width="120">과제출제방식</td>
												<td class="s_tab_view02" colspan="3">
<%	if(pMODE.equals("write")){	%>
													<input type=radio name="pReportType2" value="R" checked class=no>랜덤출제	&nbsp;
													<input type=radio name="pReportType2" value="C" class=no>과제선택
<%	} else { %>
													<input type=radio name="pReportType2" value="R" <%=(reportType2.equals("R")? "checked" : "") %> class=no>랜덤출제&nbsp;
													<input type=radio name="pReportType2" value="C" <%=(reportType2.equals("C")? "checked" : "") %> class=no>과제선택
<%	}	%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>

											<tr>
												<td class="s_tab_view01" width="120">과제명</td>
												<td class="s_tab_view02" colspan="3">
													<input type=text name=pSubject size=70 dispName="과제명" notNull lenCheck=50 value="<%=reportSubject%>">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>

											<tr>
												<td class="s_tab_view01" width="120">과제기간</td>
												<td class="s_tab_view02" colspan="3">
<%	DateParam dateParam = new DateParam();
	dateParam.setCount(2);
	dateParam.setDateType(0);
	dateParam.setForm("f");
	dateParam.setDate("reportTerm");
	dateParam.setYear("year1");
	dateParam.setMonth("month1");
	dateParam.setDay("day1");
	dateParam.setHour("hour1");
	dateParam.setMinute("minute1");
	out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("reportTerm")));	%><br>
															[시간은 24시간제로 직접 입력 ex) 23:11]
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>

											<tr>
												<td class="s_tab_view01" width="120">제출연장일</td>
												<td class="s_tab_view02" colspan="3">
<%	dateParam = new DateParam();
	dateParam.setCount(1);
	dateParam.setDateType(0);
	dateParam.setForm("f");
	dateParam.setDate("reportResult");
	dateParam.setYear("year2");
	dateParam.setMonth("month2");
	dateParam.setDay("day2");
	dateParam.setHour("hour2");
	dateParam.setMinute("minute2");
	out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("reportResult")));

	String	reportScoreYn	=	"";
	String	reportOpenYn	=	"";
	String  reportRegistYn	=	"";
	reportScoreYn	=	StringUtil.nvl(reportInfo.getReportScoreYn(), "");
	reportOpenYn	=	StringUtil.nvl(reportInfo.getReportOpenYn(), "");
	reportRegistYn	=	StringUtil.nvl(reportInfo.getReportRegistYn(), "");

%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>

											<tr>
												<td class="s_tab_view01" width="120">성적적용여부</td>
												<td class="s_tab_view02" colspan="3">
													<input type="radio" name="pScoreYn" value="Y" class="solid0" <%if (reportScoreYn.equals("Y") || reportScoreYn.equals("")) out.print(" checked");%> style="border:0">적용
                  									<input type="radio" name="pScoreYn" value="N" class="solid0" <%if (reportScoreYn.equals("N")) out.print(" checked"); %> style="border:0">미적용
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>

											<tr>
												<td class="s_tab_view01" width="120">과제공개여부</td>
												<td class="s_tab_view02" colspan="3">
													<input type="radio" name="pOpenYn" value="Y" class="solid0" <%if (reportOpenYn.equals("Y")) out.print(" checked"); %> style="border:0">공개
                  									<input type="radio" name="pOpenYn" value="N" class="solid0" <%if (reportOpenYn.equals("N") || reportOpenYn.equals("")) out.print(" checked"); %> style="border:0">미공개
                  									&nbsp;&nbsp;<font color="#816554">● 공개시 다른 학생의 과제 제출정보를 볼 수 있습니다. </font>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>

											<tr>
												<td class="s_tab_view01" width="120">과제등록여부</td>
												<td class="s_tab_view02" colspan="3">
													<input type="radio" name="pRegistYn" value="Y" class="solid0" <%if (reportRegistYn.equals("Y")) out.print(" checked"); %> style="border:0" <%if(pSubReprotCnt.equals("0")) out.print(" disabled"); %>>등록
			               							<input type="radio" name="pRegistYn" value="N" class="solid0" <%if (reportRegistYn.equals("N") || reportRegistYn.equals("")) out.print(" checked"); %> style="border:0">미등록
			               							&nbsp;&nbsp;<font color="#816554">● 과제는 과제문제를 제출하신 이후에 등록이 가능합니다. </font>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%	if(pMODE.equals("edit") && pReportSendDelYn.equals("Y") && reportRegistYn.equals("Y")){	%>
											<tr>
												<td class="s_tab_view01" width="120">제출정보삭제</td>
												<td class="s_tab_view02" colspan="3">
													<input type="radio" name="pSendDelYn" value="Y" class="solid0" style="border:0">예
			               							<input type="radio" name="pSendDelYn" value="N" class="solid0" style="border:0" checked>아니오
			               							&nbsp;&nbsp;<font color="#816554">● '예' 선택시 해당 과제의 제출 정보가 삭제됩니다. </font>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<% } if(pMODE.equals("write")){	%>
											<tr>
												<td class="s_tab_view01" width="120">자동등록여부</td>
												<td class="s_tab_view02">
													<select name="pAutoBankYn" onChange="autoBankYn()">
														<option value="N">문제수동등록</option>
														<option value="Y">문제자동등록</option>
													</select>
												</td>
												<td class="s_tab_view02" colspan="2">
													<div id="autoBankDiv" style="width:100%;display:none">
														<select name="pBankId"></select>
													</div>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<% } %>
											<tr>
												<td class="s_tab_view01" width="120">과제설명</td>
												<td class="s_tab_view03" colspan="3">
<textarea name="pContents" rows="9" cols="70" wrap="VIRTUAL"><%=reportContents%></textarea>
<%
	EditorParam editerParam = new EditorParam();
	editerParam.setShowFlag("true");
	editerParam.setWidth(530);
	editerParam.setHeight(300);
	editerParam.setToolBarHidden("attachFile");
	out.print(CommonUtil.getEditorScript(editerParam));
%>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<%	if (pMODE.equals("write")) {	%>
<script language=javascript>Button3("등록", "SubmitCheck('<%=pMODE%>')", "");</script>
<%	} else {	%>
&nbsp;<script language=javascript>Button3("수정", "SubmitCheck('<%=pMODE%>')", "");</script>
<%	}

	if (!pMODE.equals("write")) {	%>
&nbsp;<script language=javascript>Button3("삭제", "goDelete('<%=CONTEXTPATH%>')", "");</script>
<%
	}
%>
&nbsp;<script language=javascript>Button3("목록", "goCancel()", "");</script>
												</td>
											</tr>
</form>
<!-- FROM END -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/course_footer.jsp" %>

<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->