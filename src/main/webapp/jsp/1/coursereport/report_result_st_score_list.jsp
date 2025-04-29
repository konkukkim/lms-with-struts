<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.coursereport.dto.ReportInfoDTO" %>
<%@ page import ="com.edutrack.coursereport.dto.ReportAnswerDTO" %>
<%@ page import ="com.edutrack.coursereport.dto.ReportContentsDTO"%>
<%@include file="../common/course_header.jsp" %>
<script language="javascript">
    function Score_Submit(){
       var f = document.Input;

	   if(!validate(f)) return;

       f.submit();
    }

    function goCancel(){
        var f = document.Input;

        var courseid = f.pCourseId.value;
        var reportid = f.pReportId.value;
        var teamtype = f.pTeamType.value;

        var loc = "<%=CONTEXTPATH%>/ReportResult.cmd?cmd=reportUserList&pCourseId="+courseid+"&pReportId="+reportid+"&pReportType="+teamtype;
        document.location = loc;
    }

    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }
</script>
<%
    String pCourseId		=	(String)model.get("pCourseId");
    String pReportId		=	(String)model.get("pReportId");
    String pUserId			=	(String)model.get("pUserId");
    String pTeamType		=	(String)model.get("pReportType");

    ArrayList contentsList	=	(ArrayList)model.get("contentsList");
    ReportAnswerDTO answerInfo	=	(ReportAnswerDTO)model.get("answerInfo");
    ReportInfoDTO reportInfo	=	(ReportInfoDTO)model.get("reportInfo");
    String[] answer			=	StringUtil.split(answerInfo.getAnswer(), CommonUtil.DELI);
	String[] rfileName1		=	StringUtil.split(answerInfo.getRfileName1(), CommonUtil.DELI);
	String[] sfileName1		=	StringUtil.split(answerInfo.getSfileName1(), CommonUtil.DELI);
	String[] filePath1		=	StringUtil.split(answerInfo.getFilePath1(), CommonUtil.DELI);
	String[] fileSize1		=	StringUtil.split(answerInfo.getFileSize1(), CommonUtil.DELI);
	String[] rfileName2		=	StringUtil.split(answerInfo.getRfileName2(), CommonUtil.DELI);
	String[] sfileName2		=	StringUtil.split(answerInfo.getSfileName2(), CommonUtil.DELI);
	String[] filePath2		=	StringUtil.split(answerInfo.getFilePath2(), CommonUtil.DELI);
	String[] fileSize2		=	StringUtil.split(answerInfo.getFileSize2(), CommonUtil.DELI);

    String[] inputAnswer	=	StringUtil.split(answerInfo.getAnswerScore(),CommonUtil.DELI);
    ReportContentsDTO cI	=	null;
    String pReportType		=	"";
    String pFeedback		=	StringUtil.nvl(answerInfo.getFeedBack());
    String[] width			=	new String[]{"200","537"};
%>
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
<!--===== Form Start =========================================================-->
<form name="Input" method="post" action="<%=CONTEXTPATH%>/ReportResult.cmd?cmd=reportResultRegist" >
<input type="hidden" name="pCourseId" value="<%=pCourseId%>">
<input type="hidden" name="pReportId"   value="<%=pReportId%>">
<input type="hidden" name="pUserId"   value="<%=pUserId%>">
<input type="hidden" name="pTeamType" value="<%=pTeamType%>">

											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01_01" colspan="2">과제명 : [<%=CommonUtil.getCourseName(SYSTEMCODE,pCourseId)%>]<%=reportInfo.getSubject()%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
	for (int i =0; i < contentsList.size(); i++) {
		cI = (ReportContentsDTO)contentsList.get(i);
		pReportType = cI.getReportType();
		inputAnswer[i]	=	StringUtil.nvl(inputAnswer[i], "0");
%>
											<tr>
												<td class="s_tab_view01" width="135"><%=cI.getReportOrder()%>번 문제</td>
												<td class="s_tab_view02">(<%=CommonUtil.getContentsTypeName(cI.getReportType())%>) <%=cI.getScore()%>점</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view03" colspan="2">
													<%=StringUtil.ReplaceAll(cI.getContents(),"&quot;","\"")%><br><br>
<%
		if (!cI.getRfileName().equals("")) {
%>
                                    				<a href="javascript:fileDownload('<%=cI.getRfileName()%>', '<%=cI.getSfileName()%>', '<%=cI.getFilePath()%>', '<%=cI.getFileSize()%>');">관련 첨부파일 Download</a>
<%
		}
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
		if (pReportType.equals("F")) {
%>
											<tr>
												<td class="s_tab_view01">본인이 제출한 파일</td>
												<td class="s_tab_view02">
													<%=answer[i]%>
<%
			if (!rfileName1[i].equals("")) {
%>
													<a href="javascript:fileDownload('<%=rfileName1[i]%>', '<%=sfileName1[i]%>', '<%=filePath1[i]%>', '<%=fileSize1[i]%>');"><u><%=rfileName1[i]%></u></a>
<%
			}
			if (!rfileName2[i].equals("")) {
%>
													&nbsp;<a href="javascript:fileDownload('<%=rfileName2[i]%>', '<%=sfileName2[i]%>', '<%=filePath2[i]%>', '<%=fileSize2[i]%>');"><u><%=rfileName2[i]%></u></a>
<%
			}
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
		}
		else {
%>
											<tr>
												<td class="s_tab_view01" width="135">본인이 풀이한 답</td>
												<td class="s_tab_view02"><%=answer[i]%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
		}
%>
											<tr>
												<td class="s_tab_view01" width="135">정답 풀이</td>
												<td class="s_tab_view02"><%=cI.getComment()%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="135">배점</td>
												<td class="s_tab_view02">
													<input type="text" name="pScore<%=cI.getReportOrder()%>" value="<%=inputAnswer[i]%>" notNull dataType="number" minValue="0" maxValue="<%=cI.getScore()%>" dispName="<%=cI.getReportOrder()%>번 문제 배점"> / <%=cI.getScore()%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
	}//end for
%>
<input type="hidden" name="pMaxOrder" value="<%=cI.getReportOrder()%>">
											<tr>
												<td class="s_tab_view01" width="135">피드백내용</td>
												<td class="s_tab_view03">
													<textarea name="pFeedback" cols="80" rows="7"><%=pFeedback %></textarea>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("채점", "Score_Submit()", "");</script>
&nbsp;<script language=javascript>Button3("목록", "goCancel()", "");</script>
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
<%@include file="../common/course_footer.jsp" %>