<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/course_header.jsp" %>
<%@ page import ="javax.sql.RowSet"%>
<%@ page import ="com.edutrack.coursereport.dto.ReportSubInfoDTO, com.edutrack.common.DateSetter, com.edutrack.common.dto.DateParam"%>
<%@ page import ="com.edutrack.coursereport.dto.ReportSendDTO"%>
<%@ page import ="com.edutrack.common.dto.EditorParam"%>
<%

    String 			pMODE			=	(String)model.get("pMODE");
    String 			pCourseId 		=	(String)model.get("pCourseId");
    String 			pReportId 		= 	(String)model.get("pReportId");
    String			pUserId			=	(String)model.get("pUserId");
    String			pUserName		=	(String)model.get("pUserName");
    String			pRegional		=	(String)model.get("pRegional");
    String 			pReportSubject 	= 	(String)model.get("pReportSubject");

%>

<!-- 서브과제 정보 -->
<%
    ReportSubInfoDTO reportSubInfo = (ReportSubInfoDTO)model.get("reportSubInfo");

    String			subRfileName	=	StringUtil.nvl(reportSubInfo.getRfileName());
    String			subSfileName	=	StringUtil.nvl(reportSubInfo.getSfileName());
    String			subFilePath		=	StringUtil.nvl(reportSubInfo.getFilePath());
    String			subFileSize		=	StringUtil.nvl(reportSubInfo.getFileSize());

%>

<!-- 과제 제출 정보 -->
<%
    ReportSendDTO reportSendDto  	=   (ReportSendDTO)model.get("reportSendDto");


	String			userId			=	StringUtil.nvl(reportSendDto.getUserId());
	int				subReportId		=	reportSendDto.getSubReportId();
    String			rfileName1		=	StringUtil.nvl(reportSendDto.getRfileName1());
    String			sfileName1		=	StringUtil.nvl(reportSendDto.getSfileName1());
    String			filePath1		=	StringUtil.nvl(reportSendDto.getFilePath1());
    String			fileSize1		=	StringUtil.nvl(reportSendDto.getFileSize1());
    String			rfileName2		=	StringUtil.nvl(reportSendDto.getRfileName2());
    String			sfileName2		=	StringUtil.nvl(reportSendDto.getSfileName2());
    String			filePath2		=	StringUtil.nvl(reportSendDto.getFilePath2());
    String			fileSize2		=	StringUtil.nvl(reportSendDto.getFileSize2());

    //에디터유형
    String			editorType		=	StringUtil.nvl(reportSendDto.getEditorType());

%>

<!-- 과제 평가 정보 -->
<%
	String			profComment		=	StringUtil.nvl(reportSendDto.getProfComment());
	int				score			=	reportSendDto.getScore();
	String			rfileName3		=	StringUtil.nvl(reportSendDto.getRfileName3());
    String			sfileName3		=	StringUtil.nvl(reportSendDto.getSfileName3());
    String			filePath3		=	StringUtil.nvl(reportSendDto.getFilePath3());
    String			fileSize3		=	StringUtil.nvl(reportSendDto.getFileSize3());
%>

<script language="javascript">
<!--

	//파일 다운로드
	 function fileDownload(rfilename, sfilename, filepath, filesize){
		var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
		hiddenFrame.document.location = loc;
	 }

	 //상위 리포트 정보(팝업)
   	function showSubReportInfo(courseId, reportId, subReportId){
	   var loc="/ReportAdmin.cmd?cmd=reportStSubShow&pCourseId="+courseId+"&pReportId="+reportId+"&pSubReportId="+subReportId;
	   report = window.open(loc,"report","left=0,top=0,toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=auto,resizable=no,width=720,height=300");
    }

     // 폼체크
	function chkForm()
	{
		/*  WEAS 삽입 스크립트3(시작) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS 삽입 스크립트3(끝) */

		var f = document.Input;
		var score = f.pScore.value;

		if (isNaN(f.pScore.value) || f.pScore.value == "")
        {
        	alert("숫자만 입력하세요.");
            f.pScore.value = 0;
            return false;
        }


		if(score > 100) {
			alert('100점 이내로 입력해 주세요');
			return false;
		}

		return true;
	}

    // 서브밋
	function goSubmit(){
		if(chkForm()){
			var f = document.Input;
			f.action = "<%=CONTEXTPATH%>/ReportResult.cmd?cmd=reportProfSendRegist";
			f.method = "post";
			f.submit();
		}else{
			return;
		}
	}

	//취소
    function goCancel(){
        var f = document.Input;

        var courseid = f.pCourseId.value;
		var reportid = f.pReportId.value;
		var reportsubject = f.pReportSubject.value;

        var loc="<%=CONTEXTPATH%>/ReportResult.cmd?cmd=reportUserResultList&pCourseId="+courseid+"&pReportId="+reportid+"&pReportSubject="+reportsubject;
        document.location = loc;
    }


//-->
</script>

<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ReportSendWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_report/reportSend.js"></script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"과제평가")%></b></font></td>
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
<form name="Input" method="post" enctype="multipart/form-data" action="<%=CONTEXTPATH%>/ReportAdmin.cmd?cmd=reportRegist">
<input type="hidden" name="pMODE" value="<%=pMODE%>">
<input type="hidden" name="pCourseId" value="<%=pCourseId%>">
<input type="hidden" name="pReportId" value="<%=pReportId%>">
<input type="hidden"  name="pReportSubject"   value="<%=pReportSubject%>">
<input type="hidden" name="pUserId" value="<%=userId%>">
<input type="hidden" name="pOldRfile3"  value="<%=rfileName3%>">
<input type="hidden" name="pOldSfile3"  value="<%=sfileName3%>">
<input type="hidden" name="pOldFilePath3" value="<%=filePath3%>">
<input type="hidden" name="pOldFileSize3" value="<%=fileSize3%>">
											<tr>
												<td colspan="4" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td height=30><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" align="absmiddle" border="0"> <b>과제정보</b></td>
															<td align=right></td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과제명</td>
												<td class="s_tab_view02" colspan="3">
													<%= StringUtil.nvl(reportSubInfo.getSubReportSubject()) %>
												</td>

											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과제설명</td>
												<td class="s_tab_view03" colspan="3">
													<% out.println( StringUtil.ReplaceAll(StringUtil.nvl(reportSubInfo.getSubReportContents()),"&quot;","\"")  ); %>
												</td>

											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">첨부파일</td>
												<td class="s_tab_view03" colspan="3">
<%	if(!subRfileName.equals("") && !subRfileName.equals("null")) {%>
<a href="javascript:fileDownload('<%=subRfileName%>','<%=subSfileName%>','<%=subFilePath%>','<%=subFileSize%>');"><img
src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/ico_file.gif" border="0" align="absmiddle"> <%=subRfileName%></a>
<%	}	%>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
										</table>
										<!-- 과제제출정보 시작 -->
										<table width="670" align="center">
											<tr>
												<td height="20" colspan="4"></td>
											</tr>
											<tr>
												<td colspan="4" class="s_btn01"><img
													src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" align="absmiddle" border="0"> <b>과제제출정보</b></td>
											</tr>
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">성명(ID)</td>
												<td class="s_tab_view02" width="550" colspan="3"><%=pUserId%>(<%=pUserName%>)</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">제출여부</td>
												<td class="s_tab_view02" width="550" colspan="3">
													<% if(pRegional.equals("Y")) { %>
														<font color="blue">정상제출</font>
													<% } else if(pRegional.equals("N")) { %>
														<font color="green">지연제출</font>
													<% } else { %>
														<font color="red">미제출</font>
													<% } %>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">제목</td>
												<td class="s_tab_view02" width="550" colspan="3"><%=StringUtil.nvl(reportSendDto.getReportSendSubject())%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">문제내용</td>
												<td class="s_tab_view03" width="550" colspan="3">
<% if (editorType.equals("W")) {
		out.println( StringUtil.ReplaceAll(StringUtil.nvl(reportSendDto.getReportSendContents()),"&quot;","\"")  );
	} else if (editorType.equals("H")) {
		out.println( StringUtil.getHtmlContents(StringUtil.nvl(reportSendDto.getReportSendContents())));
	}
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">첨부파일</td>
												<td class="s_tab_view03" colspan="3">
<%	if(!rfileName1.equals("") && !rfileName1.equals("null")) { %>
	<a href="javascript:fileDownload('<%=rfileName1%>','<%=sfileName1%>','<%=filePath1%>','<%=fileSize1%>');"><img
	src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/ico_file.gif" border="0" align="absmiddle"> <%=rfileName1%></a>
<%	}
	if(!rfileName2.equals("") && !rfileName2.equals("null")) { %>
	&nbsp;<a href="javascript:fileDownload('<%=rfileName2%>','<%=sfileName2%>','<%=filePath2%>','<%=fileSize2%>');"><img
	src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/ico_file.gif" border="0" align="absmiddle"> <%=rfileName2%></a>
<%	}	%>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
										</table>
										<!-- 평가 폼 시작-->
										<table width="670" align="center">
											<tr>
												<td height="20" colspan="4"></td>
											</tr>
											<tr>
												<td colspan="4" class="s_btn01"><img
													src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" align="absmiddle" border="0"> <b>평가정보</b></td>
											</tr>
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">평가점수</td>
												<td class="s_tab_view02" width="550" colspan="3"><input type="text" name="pScore" size="5" style="text-align:right"  maxLength=3 value="<%=score%>">점</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">문제내용</td>
												<td class="s_tab_view03" width="550" colspan="3">
<textarea name="pContents" rows="9" cols="72" wrap="VIRTUAL"><%=profComment%></textarea>
<%
	EditorParam editerParam = new EditorParam();
	editerParam.setShowFlag("true");
	editerParam.setWidth(530);
	editerParam.setHeight(200);
	editerParam.setToolBarHidden("attachFile");
	out.print(CommonUtil.getEditorScript(editerParam));
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">피드백파일</td>
												<td class="s_tab_view03" width="550" colspan="3">
<%
	if (!sfileName3.equals("") && !sfileName3.equals("null")) {
		out.print("<div id='fileDiv3' style='display:block'><a href=\"javascript:fileDownload('"+rfileName3+"','"+sfileName3+"','"+filePath3+"','"+fileSize3+"');\">");
		out.print("<img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/ico_file.gif\" border=\"0\"  align=\"absmiddle\"> "+rfileName3+"</a>");
		out.print("&nbsp;&nbsp;");
		out.print("<a href='javascript:delFile(\"3\")'>[기존파일삭제]</a>");
		out.print("</div>");
	}
%>
<input type="file" name="pFile3" size="70">
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
										</table>
										<table width="670" align="center">
											<tr>
												<td colspan="4" class="s_list_btn" align="right">
<%	if (pMODE.equals("ADD")) {	%>
<script language=javascript>Button3("등 록", "goSubmit()", "");</script>
<%	} else {	%>
<script language=javascript>Button3("수 정", "goSubmit()", "");</script>
<%	}  %>
&nbsp;<script language=javascript>Button3("목 록", "goCancel()", "");</script>
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