<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.coursereport.dto.ReportSubInfoDTO" %>
<%@include file="../common/popup_header.jsp" %>
<%
     ReportSubInfoDTO reportSubInfo = (ReportSubInfoDTO)model.get("reportSubInfo");

    String 			pMODE			=	(String)model.get("pMODE");
    String 			pCourseId 		=	(String)model.get("pCourseId");
    String 			pReportId 		= 	(String)model.get("pReportId");
    String 			pSubReportId 		= 	(String)model.get("pSubReportId");
    String			rfileName		=	StringUtil.nvl(reportSubInfo.getRfileName());
    String			sfileName		=	StringUtil.nvl(reportSubInfo.getSfileName());
    String			filePath		=	StringUtil.nvl(reportSubInfo.getFilePath());
    String			fileSize		=	StringUtil.nvl(reportSubInfo.getFileSize());

%>

<script language="javascript">
<!--

	//파일 다운로드
	 function fileDownload(rfilename, sfilename, filepath, filesize){
		var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
		hiddenFrame.document.location = loc;
	 }

//-->
</script>
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit"><%=CommonUtil.getPageTitle(request,"과제상세정보")%></font></td>
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
									<td class="s_tab_view01">과제명</td>
									<td class="s_tab_view03"><font class="c_text02"><b><%= reportSubInfo.getSubReportSubject() %></b></font></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">과제설명</td>
									<td class="s_tab_view03"><% out.println( StringUtil.ReplaceAll(StringUtil.nvl(reportSubInfo.getSubReportContents()),"&quot;","\"")  ); %></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">첨부파일</td>
									<td class="s_tab_view02">
<%	if(!rfileName.equals("") && !rfileName.equals("null")) { %>
<a href="javascript:fileDownload('<%=rfileName%>','<%=sfileName%>','<%=filePath%>','<%=fileSize%>');"><%=rfileName%></a>
<%	} %>
									</td>
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
				src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif"></a></td>
		</tr>
	</table>
<%@include file="../common/popup_footer.jsp" %>

