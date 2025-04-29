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
	String 			pSubReportEditYn =	(String)model.get("pSubReportEditYn");
    String[] 		width			=	new String[]{"190","647"};
	String			reportSubject	=	StringUtil.nvl(reportInfo.getReportSubject(), "");
	String			reportContents	=	StringUtil.nvl(reportInfo.getReportContents(), "");

	String 			reportType2		=	StringUtil.nvl(reportInfo.getReportType2());

	String 			pReportSendDelYn = (String)model.get("pReportSendDelYn");

	String reportStartDate  =	StringUtil.nvl(reportInfo.getReportStartDate(), "");
	String reportEndDate  =	StringUtil.nvl(reportInfo.getReportEndDate(), "");
	String reportExtendDate  =	StringUtil.nvl(reportInfo.getReportExtendDate(), "");

	String reportScoreYn	=	StringUtil.nvl(reportInfo.getReportScoreYn(), "");
	String reportOpenYn		=	StringUtil.nvl(reportInfo.getReportOpenYn(), "");
	String reportRegistYn	=	StringUtil.nvl(reportInfo.getReportRegistYn(), "");

%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ReportBankWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ReportSubInfoWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_report/reportInfo.js"></script>
<script language="javascript">
<!--

	//리포트문제제출
	function Show_ReportContents(reportid) {
       var f = document.Regist;
       var pCourseId =   "<%=pCourseId%>";
       var pParentReportId	=	<%=pReportId%>;
       document.location = "/ReportContents.cmd?cmd=reportContentsList&pCourseId="+pCourseId+"&pReportId="+reportid+"&pTeamMode=A&pParentReportId="+pParentReportId;
    }

//-->
</script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"과제출제")%></b></font></td>
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
<input type="hidden" name="pSubReportEditYn" value="<%=pSubReportEditYn%>">
<input type="hidden" name="pReportRegistYn" value="<%=reportRegistYn%>">
<input type="hidden" name="pReportType1" value="A">
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과제출제방식</td>
												<td class="s_tab_view02" width="215">
<% 	if(reportType2.equals("R")) {	%>
													랜덤출제

<%	} else if(reportType2.equals("C")) {	%>
													과제선택
<%	}%>
												</td>
												<td class="s_tab_view01" width="120">성적적용여부</td>
												<td class="s_tab_view02">
<% 	if(reportScoreYn.equals("Y")) {	%>
													적용

<%	} else if(reportScoreYn.equals("N")) {	%>
													미적용
<%	}%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>

											<tr>
												<td class="s_tab_view01" width="120">과제명</td>
												<td class="s_tab_view02" colspan="3">
													<%=reportSubject%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>

											<tr>
												<td class="s_tab_view01" width="120">과제기간</td>
												<td class="s_tab_view02" colspan="3">
													<%=DateTimeUtil.getDateType(1,StringUtil.nvl(reportStartDate))+" ~ "+DateTimeUtil.getDateType(1,StringUtil.nvl(reportEndDate))%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>

											<tr>
												<td class="s_tab_view01" width="120">제출연장일</td>
												<td class="s_tab_view02" colspan="3">
													<%=DateTimeUtil.getDateType(1,StringUtil.nvl(reportExtendDate))%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>

											<tr>
												<td class="s_tab_view01" width="120">과제공개여부</td>
												<td class="s_tab_view02">
<% 	if(reportOpenYn.equals("Y")) {	%>
													공개

<%	} else if(reportOpenYn.equals("N")) {	%>
													미공개
<%	}%>
												</td>
												<td class="s_tab_view01" width="120">과제등록여부</td>
												<td class="s_tab_view02">
<% 	if(reportRegistYn.equals("Y")) {	%>
													등록

<%	} else if(reportRegistYn.equals("N")) {	%>
													미등록
<%	}%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>

											<tr>
												<td class="s_tab_view01" width="120">과제설명</td>
												<td class="s_tab_view03" colspan="3">
													<% out.println( StringUtil.ReplaceAll(StringUtil.nvl(reportInfo.getReportContents()),"&quot;","\"")  ); %>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<script language=javascript>Button3("목록", "goCancel();", "");</script>
												</td>
											</tr>
										</table>

										<table width="670" align="center">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td >
															</td>
															<td align=right height=30>
<script language=javascript>Button5("문제추가", "addSubReport()", "");</script>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="489">제목</td>
												<td class="s_tablien"></td>
												<td width="110">첨부파일</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11">
											  	<!-- 리스트 -->
											  		<div id="reportSubInfoList" style="width:100%;display:none"></div>
											  	<!-- 리스트 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
										</table>

										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<script language="javascript">
	init('<%=CONTEXTPATH%>','<%=SYSTEMCODE%>','<%=pCourseId%>','<%=pReportId%>','show');
</script>
<%@include file="../common/course_footer.jsp" %>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->
