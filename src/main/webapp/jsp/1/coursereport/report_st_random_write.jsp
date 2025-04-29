<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/course_header.jsp" %>
<%@ page import ="com.edutrack.coursereport.dto.ReportInfoDTO" %>
<%@ page import ="com.edutrack.coursereport.dto.ReportSubInfoDTO, com.edutrack.common.DateSetter, com.edutrack.common.dto.DateParam"%>
<%@ page import ="com.edutrack.coursereport.dto.ReportSendDTO"%>
<%@ page import ="com.edutrack.common.dto.EditorParam"%>
<!-- 상위 과제 정보 -->
<%
    ReportInfoDTO	reportInfo		=	(ReportInfoDTO)model.get("reportInfo");

	String			reportSubject	=	StringUtil.nvl(reportInfo.getReportSubject(), "");
	String 			reportType2		=	StringUtil.nvl(reportInfo.getReportType2());

	String reportStartDate  =	StringUtil.nvl(reportInfo.getReportStartDate(), "");
	String reportEndDate  =	StringUtil.nvl(reportInfo.getReportEndDate(), "");
	String reportExtendDate  =	StringUtil.nvl(reportInfo.getReportExtendDate(), "");

	String reportScoreYn	=	StringUtil.nvl(reportInfo.getReportScoreYn(), "");
	String reportOpenYn		=	StringUtil.nvl(reportInfo.getReportOpenYn(), "");
	String reportRegistYn	=	StringUtil.nvl(reportInfo.getReportRegistYn(), "");

%>

<!-- 서브과제정보 -->
<%
    ReportSubInfoDTO reportSubInfo  =   (ReportSubInfoDTO)model.get("reportSubInfo");
    String 			pMODE			=	(String)model.get("pMODE");
    String 			pCourseId 		=	(String)model.get("pCourseId");
    String 			pReportId 		= 	(String)model.get("pReportId");
    String 			pInsertYn		=	(String)model.get("pInsertYn");
    String 			pEndYn			=	(String)model.get("pEndYn");		//종료여부(Y:진행중, N:종료)
    String 			pMarkCheckYn	=	(String)model.get("pMarkCheckYn");	//평가여부
    String 			pSendCheckYn	=	(String)model.get("pSendCheckYn");	//제출여부
    String			rfileName		=	StringUtil.nvl(reportSubInfo.getRfileName());
    String			sfileName		=	StringUtil.nvl(reportSubInfo.getSfileName());
    String			filePath		=	StringUtil.nvl(reportSubInfo.getFilePath());
    String			fileSize		=	StringUtil.nvl(reportSubInfo.getFileSize());

    String[] 		width			=	new String[]{"190","647"};

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


    //평가파일
    String			rfileName3		=	StringUtil.nvl(reportSendDto.getRfileName3());
    String			sfileName3		=	StringUtil.nvl(reportSendDto.getSfileName3());
    String			filePath3		=	StringUtil.nvl(reportSendDto.getFilePath3());
    String			fileSize3		=	StringUtil.nvl(reportSendDto.getFileSize3());

    //에디터유형
    String			editorType		=	StringUtil.nvl(reportSendDto.getEditorType());
%>

<script language="javascript">
<!--

	//파일 다운로드
	 function fileDownload(rfilename, sfilename, filepath, filesize){
		var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
		hiddenFrame.document.location = loc;
	 }

	 // 폼체크
	function chkForm()
	{
		/*  WEAS 삽입 스크립트3(시작) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS 삽입 스크립트3(끝) */

		var f = document.Input;

		if(isEmpty(f.pSubject.value)) {
			alert('제목을 입력하세요');
			f.pSubject.focus();
			return false;
		}

		if (getLength(f.pSubject.value) > 200) {
			alert('제목의 길이가 너무 길어 입력이 불가능합니다.(200자이내)');
			f.pSubject.focus();
			return false;
		}

		if(isEmpty(f.pContents.value)) {
			alert('문제를 입력하세요');
			return false;
		}
		return true;
	}

    // 서브밋
	function goSubmit(){
		if(chkForm()){
			var f = document.Input;
			f.action = "<%=CONTEXTPATH%>/ReportResult.cmd?cmd=reportSendRegist";
			f.method = "post";
			f.submit();
		}else{
			return;
		}
	}

	//다른 학습 사용자 보기
	function goOtherReoprtList() {
		var f = document.Input;

        var courseid = f.pCourseId.value;
		var reportid = f.pReportId.value;
		var reportsubject = "<%=reportSubject%>";
		var reportopenyn = "<%=reportOpenYn%>";
		if(reportopenyn  == "N") {
			alert("미공개 이므로 다른 학생 과제를 볼수 없습니다.");
			return;
		}
        var loc="/ReportResult.cmd?cmd=reportOtherUserList&pCourseId="+courseid+"&pReportId="+reportid+"&pReportSubject="+reportsubject;
        document.location = loc;
	}

	//취소
    function goCancel(){
        var f = document.Input;

        var courseid = f.pCourseId.value;

        var loc="/ReportAdmin.cmd?cmd=reportStList&pCourseId="+courseid;
        document.location = loc;
    }

//-->
</script>

<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ReportSendWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_report/reportSend.js"></script>


							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"과제제출")%></b></font></td>
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
<!-- form start -->
<form name="Input" method="post" enctype="multipart/form-data" action="">
<input type="hidden" name="pMODE" value="<%=pMODE%>">
<input type="hidden" name="pInsertYn" value="<%=pInsertYn%>">
<input type="hidden" name="pCourseId" value="<%=pCourseId%>">
<input type="hidden" name="pReportId" value="<%=pReportId%>">
<input type="hidden" name="pSubReportId" value="<%=subReportId%>">
<input type="hidden" name="pUserId" value="<%=userId%>">
<input type="hidden" name="pOldRfile1"  value="<%=rfileName1%>">
<input type="hidden" name="pOldSfile1"  value="<%=sfileName1%>">
<input type="hidden" name="pOldFilePath1" value="<%=filePath1%>">
<input type="hidden" name="pOldFileSize1" value="<%=fileSize1%>">
<input type="hidden" name="pOldRfile2"  value="<%=rfileName2%>">
<input type="hidden" name="pOldSfile2"  value="<%=sfileName2%>">
<input type="hidden" name="pOldFilePath2" value="<%=filePath2%>">
<input type="hidden" name="pOldFileSize2" value="<%=fileSize2%>">
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr>
												<td colspan="4" class="s_btn01"><img
													src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" align="absmiddle" border="0"> <b>과제기본정보</b></td>
											</tr>
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과제명</td>
												<td class="s_tab_view02" width="550" colspan="3"><%=reportSubject%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과제기간</td>
												<td class="s_tab_view02" colspan="3"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(reportStartDate))+" ~ "+DateTimeUtil.getDateType(1,StringUtil.nvl(reportEndDate))%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">제출연장일</td>
												<td class="s_tab_view02" width="215"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(reportExtendDate))%></td>
												<td class="s_tab_view01" width="120">성적적용여부</td>
												<td class="s_tab_view02" width="215">
<% 	if(reportScoreYn.equals("Y")) {	%>
													적용

<%	} else if(reportScoreYn.equals("N")) {	%>
													미적용
<%	}%>
												</td>
											</tr>
<!--
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과제설명</td>
												<td class="s_tab_view03" colspan="3"><% out.println( StringUtil.ReplaceAll(StringUtil.nvl(reportInfo.getReportContents()),"&quot;","\"")  ); %></td>
											</tr>
-->
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
										</table>
<% if(!pSendCheckYn.equals("") || pEndYn.equals("Y")) {%>
										<table width="670" align="center">
											<tr>
												<td height="20" colspan="4"></td>
											</tr>
											<tr>
												<td colspan="4" class="s_btn01"><img
													src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" align="absmiddle" border="0"> <b>과제상세정보</b></td>
											</tr>
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과제명</td>
												<td class="s_tab_view02" colspan="3"><%=StringUtil.nvl(reportSubInfo.getSubReportSubject())%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과제설명</td>
												<td class="s_tab_view03" colspan="3"><% out.println( StringUtil.ReplaceAll(StringUtil.nvl(reportSubInfo.getSubReportContents()),"&quot;","\"")  ); %></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">첨부파일</td>
												<td class="s_tab_view03" colspan="3">
<%	if(!rfileName.equals("") && !rfileName.equals("null")) {	%>
<a href="javascript:fileDownload('<%=rfileName%>','<%=sfileName%>','<%=filePath%>','<%=fileSize%>');"><img
src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/ico_file.gif" border="0" align="absmiddle"> <%=rfileName%></a>
<%	}	%>

												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
										</table>
<% } %>
<%
	if(pEndYn.equals("Y") && pMarkCheckYn.equals("")) {
%>
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
												<td class="s_tab_view01" width="120">제목</td>
												<td class="s_tab_view02" width="550" colspan="3"><input type="text" name="pSubject" size="70" value="<%=StringUtil.nvl(reportSendDto.getReportSendSubject())%>"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">문제내용</td>
												<td class="s_tab_view03" colspan="3">
<textarea name="pContents" rows="9" cols="102" wrap="VIRTUAL"><%=StringUtil.nvl(reportSendDto.getReportSendContents())%></textarea>
<%
	EditorParam editerParam = new EditorParam();
	editerParam.setShowFlag("true");
	editerParam.setWidth(525);
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
												<td class="s_tab_view01" width="120">첨부파일1</td>
												<td class="s_tab_view03" colspan="3">
<%
	if (!sfileName1.equals("")) {
		out.print("<div id='fileDiv1' style='display:block'><a href=\"javascript:fileDownload('"+rfileName1+"','"+sfileName1+"','"+filePath1+"','"+fileSize1+"');\"><img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/ico_file.gif\" border=\"0\"  align=\"absmiddle\"> "+rfileName1+"</a>");
		out.print("&nbsp;&nbsp;");
		out.print("<a href='javascript:delFile(\"1\")'>[기존파일삭제]</a>");
		out.print("</div>");
	}
%>
<input type="file" name="pFile1" size="60">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">첨부파일2</td>
												<td class="s_tab_view03" colspan="3">
<%
	if (!sfileName2.equals("")) {
		out.print("<div id='fileDiv2' style='display:block'><a href=\"javascript:fileDownload('"+rfileName2+"','"+sfileName2+"','"+filePath2+"','"+fileSize2+"');\"><img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/ico_file.gif\" border=\"0\"  align=\"absmiddle\"> "+rfileName2+"</a>");
		out.print("&nbsp;&nbsp;");
		out.print("<a href='javascript:delFile(\"2\")'>[기존파일삭제]</a>");
		out.print("</div>");
	}
%>
<input type="file" name="pFile2" size="60">
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
										</table>
<!-- 과제제출정보 끝 -->
<% } else if(!pSendCheckYn.equals("")) {%>
										<table width="670" align="center">
											<tr>
												<td height="20" colspan="4"></td>
											</tr>
											<tr>
												<td colspan="4" class="s_btn01"><img
													src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" align="absmiddle" border="0"> <b>제출정보</b></td>
											</tr>
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">제목</td>
												<td class="s_tab_view02" colspan="3"><%=StringUtil.nvl(reportSendDto.getReportSendSubject())%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과제설명</td>
												<td class="s_tab_view03" colspan="3">
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
													<a href="javascript:fileDownload('<%=rfileName1%>','<%=sfileName1%>','<%=filePath1%>','<%=fileSize1%>');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/ico_file.gif" border="0" align="absmiddle"> <%=rfileName1%></a>
													<% if(!rfileName2.equals("")) {%>
														&nbsp;
													<% } %>
													<a href="javascript:fileDownload('<%=rfileName2%>','<%=sfileName2%>','<%=filePath2%>','<%=fileSize2%>');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/ico_file.gif" border="0" align="absmiddle"> <%=rfileName2%></a>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
										</table>
<% } %>

<%
	if(!pMarkCheckYn.equals("") && !pSendCheckYn.equals("")) {
%>
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
												<td class="s_tab_view01" width="120">점수</td>
												<td class="s_tab_view02" colspan="3"><%=reportSendDto.getScore()%>점</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">피드백내용</td>
												<td class="s_tab_view03" colspan="3"><% out.println( StringUtil.ReplaceAll(StringUtil.nvl(reportSendDto.getProfComment()),"&quot;","\"")  ); %></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">첨부파일</td>
												<td class="s_tab_view03" colspan="3"><a href="javascript:fileDownload('<%=rfileName3%>','<%=sfileName3%>','<%=filePath3%>','<%=fileSize3%>');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/ico_file.gif" border="0" align="absmiddle"> <%=rfileName3%></a></td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
										</table>
<% } %>
										<table width="670" align="center">
											<tr>
												<td colspan="4" class="s_list_btn" align="right">
<%	if (pMODE.equals("ADD") && pEndYn.equals("Y") && pMarkCheckYn.equals("") && pSendCheckYn.equals("")) {	%>
<script language=javascript>Button3("제 출", "goSubmit()", "");</script>
<%	} else if (pMODE.equals("EDIT") && pEndYn.equals("Y") && pMarkCheckYn.equals("")) {		%>
<script language=javascript>Button3("제 출", "goSubmit()", "");</script>
<%	}  %>
<%	if (reportOpenYn.equals("Y")) { %>
&nbsp;<script language=javascript>Button3("전체제출정보 보기", "goOtherReoprtList()", "");</script>
<%	} %>
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