<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/course_header.jsp" %>
<%@ page import ="com.edutrack.coursereport.dto.ReportInfoDTO,com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam"%>
<%
    ReportInfoDTO	reportInfo	=	(ReportInfoDTO)model.get("reportInfo");
    String			pMODE		=	(String)model.get("pMODE");
    String			pCourseId	=	(String)model.get("pCourseId");
    String			pReportId	=	(String)model.get("pReportId");
	String			pAnswerUserCnt	=	(String)model.get("pAnswerUserCnt");
	String			pParentReportId	=	(String)model.get("pParentReportId");

    String[] width = new String[]{"190","647"};

	String	subject			=	"";
	String	reportComment	=	"";
	if(pMODE.equals("edit")){
		subject			=	StringUtil.nvl(reportInfo.getSubject(), "");
		reportComment	=	StringUtil.nvl(reportInfo.getReportComment(), "");
	}
%>
<script language="javascript">
<!--
	function SubmitCheck()
	{
		var f = document.Regist;

		if(!validate(f)) return;

		f.submit();
    }

    function goCancel(){
        var f = document.Regist;
        var courseid = f.pCourseId.value;
        var reportid = f.pParentReportId.value;
        var loc="/ReportAdmin.cmd?cmd=reportWrite&pCourseId="+courseid+"&pMODE=edit&pReportId="+reportid;
        document.location = loc;
    }


    function goDelete() {
    	var f = document.Regist;
    	var pMODE = f.pMODE.value;
    	var pCourseId = f.pCourseId.value;
    	var pReportId = f.pReportId.value;
    	var pAnswerUserCnt = f.pAnswerUserCnt.value;
    	var pParentReportId = f.pParentReportId.value;

    	if(parseInt(pAnswerUserCnt) > 0 ){
		   var str = confirm("현재 응시자가 " + pAnswerUserCnt +"명이 있습니다.\n삭제하시겠습니까? (삭제시 시험 응시 정보도 삭제됩니다.)");
		   if (str == true ) {
			   	var loc = "/ReportAdmin.cmd?cmd=reportDelete&pMODE="+pMODE+"&pCourseId="+pCourseId+"&pReportId="+pReportId;
		     	document.location.href = loc;
		   }
		} else {
	    	var loc = "/ReportAdmin.cmd?cmd=reportDelete&pMODE="+pMODE+"&pCourseId="+pCourseId+"&pReportId="+pReportId+"&pTeamMode=T&pParentReportId="+pParentReportId;
	    	document.location.href = loc;

	    }
    }
//-->
</script>
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
<form name=Regist method="post" action="<%=CONTEXTPATH%>/ReportAdmin.cmd?cmd=reportRegist">
<input type="hidden" name="pReportType" value="<%=reportInfo.getReportType()%>">
<input type="hidden" name="reportTerm1" value="<%=reportInfo.getStartDate()%>">
<input type="hidden" name="reportTerm2" value="<%=reportInfo.getEndDate()%>">
<input type="hidden" name="reportResult1" value="<%=reportInfo.getResultDate()%>">
<input type="hidden" name="pViewStyle" value="<%=reportInfo.getViewStyle()%>">
<input type="hidden" name="pFlagTime" value="<%=reportInfo.getFlagTime()%>">
<input type="hidden" name="pRegistYn" value="<%=reportInfo.getRegistYn()%>">
<%	if(pMODE.equals("write")){ %>
	<input type="hidden" name="pParentReportId" value="<%=pReportId%>">
<%	} else {	%>
	<input type="hidden" name="pParentReportId" value="<%=pParentReportId%>">
<%	}	%>
<input type=hidden name="pMODE" value="<%=pMODE%>">
<input type=hidden name="pCourseId" value="<%=pCourseId%>">
<input type=hidden name="pReportId" value="<%=pReportId%>">
<input type=hidden name="pAnswerUserCnt" value="<%=pAnswerUserCnt%>">
<input type=hidden name="pTeamMode" value="T">
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">팀과제명</td>
												<td class="s_tab_view02"><input type=text name=pSubject size=70 dispName="팀과제명" notNull lenCheck=50 value="<%=subject%>"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">팀과제설명</td>
												<td class="s_tab_view03"><textarea name=pComment rows=10 cols=75 wrap="auto" dispName="시험에 대한 간단한 설명"><%=reportComment%></textarea></td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<%	if (pMODE.equals("write")) {	%>
<script language=javascript>Button3("등록", "SubmitCheck('<%=pMODE%>')", "");</script>
<%	} else {	%>
&nbsp;<script language=javascript>Button3("수정", "SubmitCheck('<%=pMODE%>')", "");</script>
<%	}

	if (!pMODE.equals("write")) {	%>
&nbsp;<script language=javascript>Button3("삭제", "goDelete()", "");</script>
<%	}	%>
&nbsp;<script language=javascript>Button3("취소", "goCancel()", "");</script>
												</td>
											</tr>
</form>
<!-- form -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/course_footer.jsp" %>