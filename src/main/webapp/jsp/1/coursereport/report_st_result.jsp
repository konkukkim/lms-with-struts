<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.coursereport.dto.ReportInfoDTO,com.edutrack.coursereport.dto.ReportAnswerDTO,com.edutrack.coursereport.dto.ReportContentsDTO"%>
<%@include file="../common/popup_header.jsp" %>
<%
    String pCourseId		=	(String)model.get("pCourseId");
    String pReportId		=	(String)model.get("pReportId");
    String pUserId			=	(String)model.get("pUserId");
    ArrayList contentsList	=	(ArrayList)model.get("contentsList");
    ReportAnswerDTO answerInfo	=	(ReportAnswerDTO)model.get("answerInfo");
    ReportInfoDTO reportInfo	=	(ReportInfoDTO)model.get("reportInfo");
    String[] answer			=	StringUtil.split(answerInfo.getAnswer(),CommonUtil.DELI);
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
    String[] width			=	new String[]{"200","400"};
    String LineBgColor		=	"#40B389";
%>
<script language="javascript">
    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }
</script>
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="../img/1/common/blet05.gif" align="absmiddle"><font class="pop_tit">시험정보</font></td>
			<td class="pop_topright"></td>
		</tr>
		<tr>
			<td class="pop_midleft"></td>
			<td valign="top" class="pop_form">
				<!-- 내용 -->
				<table width="90%" height="100%" align="center">
					<tr height="97%">
						<td align="left" valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr class="s_tab01">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td class="s_tab_view02" align="center" colspan="2">과제명 : <font class="c_text02"><b>[<%=CommonUtil.getCourseName(SYSTEMCODE,pCourseId)%>]<%=reportInfo.getSubject()%></b></font></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="s_tab05"> </td>
								</tr>
<%
	for(int i =0; i < contentsList.size(); i++) {
		cI = (ReportContentsDTO)contentsList.get(i);
		pReportType = cI.getReportType();
%>
								<tr>
									<td class="s_tab_view01" width="20%"><%=cI.getReportOrder()%>번 문제</td>
									<td class="s_tab_view02" width="80%">(<%=CommonUtil.getContentsTypeName(cI.getReportType())%>)&nbsp;<%=cI.getScore()%>점</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view03" colspan="2">&nbsp;<%=StringUtil.ReplaceAll(cI.getContents(),"&quot;","\"")%><br>
<%		if (!cI.getRfileName().equals("")) {	%>
<a href="javascript:fileDownload('<%=cI.getRfileName()%>', '<%=cI.getSfileName()%>', '<%=cI.getFilePath()%>', '<%=cI.getFileSize()%>');">관련 첨부파일 Download</a>
<%		}	%>
									</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">정답</td>
									<td class="s_tab_view02"><%=cI.getAnswer()%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
<%
		if (pReportType.equals("F")) {
%>
								<tr>
									<td class="s_tab_view01">본인이제출한파일</td>
									<td class="s_tab_view02"><%=answer[i]%>
<%			if (!rfileName1[i].equals("")) {	%>
<a href="javascript:fileDownload('<%=rfileName1[i]%>', '<%=sfileName1[i]%>', '<%=filePath1[i]%>', '<%=fileSize1[i]%>');"><u><%=rfileName1[i]%></u></a>
<%			}
			if (!rfileName2[i].equals("")) {	%>
&nbsp;<a href="javascript:fileDownload('<%=rfileName2[i]%>', '<%=sfileName2[i]%>', '<%=filePath2[i]%>', '<%=fileSize2[i]%>');"><u><%=rfileName2[i]%></u></a>
<%			}	%>
									</td>
								</tr>
<%		}
		else {	%>
								<tr>
									<td class="s_tab_view01">본인이 풀이한 답</td>
									<td class="s_tab_view02"><%=answer[i]%></td>
								</tr>
<%		}	%>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">정답 풀이</td>
									<td class="s_tab_view02"><%=cI.getComment()%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">배점</td>
									<td class="s_tab_view02"><%=inputAnswer[i]%>&nbsp;/&nbsp;<%=cI.getScore()%>점중</td>
								</tr>
								<tr class="s_tab05">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td height="8" colspan="2"></td>
								</tr>
								<tr class="s_tab05">
									<td colspan="2"></td>
								</tr>
<%	}//end for	%>
								<input type="hidden" name="pMaxOrder" value="<%=cI.getReportOrder()%>">
								<tr>
									<td class="s_tab_view01">채점 결과</td>
									<td class="s_tab_view03">총 <%=answerInfo.getTotalScore()%> 점</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">피드백 내용</td>
									<td class="s_tab_view03"><%=pFeedback %></td>
								</tr>
								<tr class="s_tab05">
									<td colspan="2"></td>
								</tr>

							</table>
						</td>
					</tr>
				</table>
				<!-- // 내용 -->
			</td>
			<td class="pop_midright"></td>
		</tr>
		<tr height="6">
			<td class="pop_bottomleft"></td>
			<td class="pop_bottombg"></td>
			<td class="pop_bottomright"></td>
		</tr>
		<tr>
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><a
			href="javascript:window.close();"><img
			src="../img/1/button_img/btn_popclose.gif"></a></td>
		</tr>
	</table>
</form>
<!--===== Form End =========================================================-->

<%@include file="../common/popup_footer.jsp" %>