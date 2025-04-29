<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/course_header.jsp" %>
<%@ page import ="javax.sql.RowSet"%>
<%@ page import ="com.edutrack.coursereport.dto.ReportInfoDTO"%>
<%@ page import ="com.edutrack.coursereport.dto.ReportSubInfoDTO, com.edutrack.common.DateSetter, com.edutrack.common.dto.DateParam"%>
<%@ page import ="com.edutrack.coursereport.dto.ReportSendDTO"%>
<%@ page import ="com.edutrack.common.dto.EditorParam"%> 
<%

    String 			pCourseId 		=	(String)model.get("pCourseId");
    String 			pReportId 		= 	(String)model.get("pReportId");
    String			pUserId			=	(String)model.get("pUserId");
    String			pUserName		=	(String)model.get("pUserName");
    String 			pReportSubject 	= 	(String)model.get("pReportSubject");
   	
%>

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

<script language="javascript">
<!--
   
	//파일 다운로드
	 function fileDownload(rfilename, sfilename, filepath, filesize){
		var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;        
		hiddenFrame.document.location = loc;
	 }
	 

	//취소
    function goCancel(){
         var f = document.Input;
        
         f.action = "<%=CONTEXTPATH%>/ReportResult.cmd?cmd=reportOtherUserList";
	 	 f.method = "post";
	 	 f.submit();;
    }
    
 
//-->
</script>

                </tr>
                <!-- main 시작 -->
                <tr> 
					<td height="410" align="left" valign="top" style="padding:0 10 0 15">
						<table width="100%" height="41" border="0" cellpadding="0" cellspacing="0" bgcolor="#71A076">
              				<tr> 
                				<td width="21"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle_bg1.gif" width="21" height="41"></td>
                				<td class="class_title"><%=COURSENAME%> [<%=CURRIYEAR%>년 <%=CURRITERM%>기]<br></td>
                				<td width="300" align="right"><font color="#CCFF00"><%=USERNAME%>님</font>
									<a href="<%=CONTEXTPATH%>/Message.cmd?cmd=receiveMessagePagingList&MENUNO=0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_msg.gif" border="0" align="absmiddle"><font color="#CCFF00"><%=UnReadCnt%></font></a>&nbsp; 
                				</td>
                				<td width="17"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle_bg2.gif" width="17" height="41"></td>
              				</tr>
            			</table>            			
										
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<!-- form start -->
						<form name="Input" method="post" action="">
					   	<input type="hidden" name="pCourseId" value="<%=pCourseId%>">
					   	<input type="hidden" name="pReportId" value="<%=pReportId%>">
					   	<input type="hidden"  name="pReportSubject"   value="<%=pReportSubject%>">
					   	<input type="hidden" name="pUserId" value="<%=userId%>">		
							<tr align="left" valign="middle"> 
			                	<td width="100%" height="45">
			                		<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움체' size="2"><b><%=CommonUtil.getPageTitle(request,"과제평가")%></b></font>
			                	</td>
			              	</tr>
							<tr>
								<td align="center" valign="top">
									
									<!-- 과제 기본 정보 시작 -->								
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td align="left">
												<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/b_ico01.gif" align="absmiddle" width="11" height="17" border="0">&nbsp;<font color="blue"><b>과제기본정보</b></font></b>
											</td>
										</tr>
										<tr>
											<td height="2" class="b_td01"> </td>
										</tr>
										<tr>
											<td height="30" > 
												<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td width="80" align="center" class="b_td02">과제명</td>
														<td width="4" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti1.gif"></td>
														<td style="padding:0 10 0 10">
															<%=reportSubject%>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="1" class="b_td03"></td>
										</tr>

										<tr>
											<td height="30" > 
												<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td width="80" align="center" class="b_td02">과제기간</td>
														<td width="4" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti1.gif"></td>
														<td style="padding:0 10 0 10">
															<%=DateTimeUtil.getDateType(1,StringUtil.nvl(reportStartDate))+" ~ "+DateTimeUtil.getDateType(1,StringUtil.nvl(reportEndDate))%>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="1" class="b_td03"> </td>
										</tr>
										<tr>
											<td height="30" >
												<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td width="80" align="center" class="b_td02">제출연장일</td>
														<td width="4" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti1.gif"></td>
														<td style="padding:0 10 0 10">
															<%=DateTimeUtil.getDateType(1,StringUtil.nvl(reportExtendDate))%>	
														</td>
														<td width="80" align="center" class="b_td02">성적적용여부</td>
														<td width="4" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti1.gif"></td>
														<td style="padding:0 10 0 10">
<% 	if(reportScoreYn.equals("Y")) {	%>
															적용
															
<%	} else if(reportScoreYn.equals("N")) {	%>
															미적용
<%	}%>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="1" class="b_td03"> </td>
										</tr>
										
										<!-- //상위과제 상세 내용 필요시  주석제거  <tr>
											<td height="30" > 
												<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td width="80" align="center" class="b_td02">과제설명</td>
														<td width="4" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti1.gif"></td>
														<td style="padding:0 10 0 0">
															<% out.println( StringUtil.ReplaceAll(StringUtil.nvl(reportInfo.getReportContents()),"&quot;","\"")  ); %>
															
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="1" class="b_td03"> </td>
										</tr>
									</table>-->
									
									
									<table width="100%" height="20" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td></td>
										</tr>
									</table>
								<!-- 과제 기본 정보 끝 -->	
								
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td align="left">
												<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/b_ico01.gif" align="absmiddle" width="11" height="17" border="0">&nbsp;<font color="blue"><b>과제상세정보</b></font></b>
											</td>
										</tr>
										<tr>
											<td height="2" class="b_td01"> </td>
										</tr>  
						              	<tr>
											<td height="30" > 
												<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td width="80" align="center" class="b_td02">과제명</td>
														<td width="4" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti1.gif"></td>
														<td style="padding:0 10 0 10">
															<%= StringUtil.nvl(reportSubInfo.getSubReportSubject()) %>
														</td>
													</tr>
												</table>
											</td>
										</tr>							
										<tr>
											<td height="1" class="b_td03"></td>
										</tr>
										<tr>
											<td height="30" > 
												<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td width="80" align="center" class="b_td02">과제설명</td>
														<td width="4" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti1.gif"></td>
														<td style="padding:0 10 0 0">
															<% out.println( StringUtil.ReplaceAll(StringUtil.nvl(reportSubInfo.getSubReportContents()),"&quot;","\"")  ); %>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="1" class="b_td03"> </td>
										</tr>
										
										<tr>
											<td height="30" > 
												<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td width="80" align="center" class="b_td02">첨부파일</td>
														<td width="4" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti1.gif"></td>
														<td style="padding:0 10 0 10">
															<a href="javascript:fileDownload('<%=subRfileName%>','<%=subSfileName%>','<%=subFilePath%>','<%=subFileSize%>');"><%=subRfileName%></a>
														</td>
													</tr>
												</table>
											</td>
										</tr>		
										<tr>
											<td height="1" class="b_td03"> </td>
										</tr>
									</table>
									
									<table width="100%" height="20" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td></td>
										</tr>
									</table>
									
									<!-- 과제제출정보 시작 -->
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td align="left">
												<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/b_ico01.gif" align="absmiddle" width="11" height="17" border="0">&nbsp;<font color="blue"><b>과제제출정보</b></font></b>
											</td>
										</tr>
										<tr> 
											<td height="2" class="b_td01"></td>
										</tr>
										<tr> 
											<td height="30" >
												<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
													<tr> 
														<td width="80" align="center" class="b_td02">성명(ID)</td>
														<td width="4"  background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti1.gif"></td>
														<td style="padding:0 10 0 10">
															<%=pUserId%>(<%=pUserName%>)
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr> 
											<td height="1" class="b_td03" ></td>
										</tr>
										<tr> 
											<td height="30" >
												<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
													<tr> 
														<td width="80" align="center" class="b_td02">제목</td>
														<td width="4"  background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti1.gif"></td>
														<td style="padding:0 10 0 10">
															<%=StringUtil.nvl(reportSendDto.getReportSendSubject())%>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr> 
											<td height="1" class="b_td03" ></td>
										</tr>
										<tr> 
											<td height="30" >
												<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
													<tr> 
														<td width="80" align="center" class="b_td02">문제내용</td>
														<td width="4"  background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti1.gif"></td>
														<td style="padding:0 10 0 0">
															<% if (editorType.equals("W")) {
															    	out.println( StringUtil.ReplaceAll(StringUtil.nvl(reportSendDto.getReportSendContents()),"&quot;","\"")  );
															    } else if (editorType.equals("H")) {
															    	out.println( StringUtil.getHtmlContents(StringUtil.nvl(reportSendDto.getReportSendContents())));
															    }
															%>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr> 
											<td height="1" class="b_td03" ></td>
										</tr>
										<tr> 
											<td height="30" >
												<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
													<tr> 
														<td width="80" align="center" class="b_td02">첨부파일</td>
														<td width="4"  background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti1.gif"></td>
														<td style="padding:0 10 0 10">
															<a href="javascript:fileDownload('<%=rfileName1%>','<%=sfileName1%>','<%=filePath1%>','<%=fileSize1%>');"><%=rfileName1%></a>
															<%if(!rfileName1.equals("")) { %>
															&nbsp;
															<% } %>
															<a href="javascript:fileDownload('<%=rfileName2%>','<%=sfileName2%>','<%=filePath2%>','<%=fileSize2%>');"><%=rfileName2%></a>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr> 
											<td height="1" class="b_td03" ></td>
										</tr>
									</table>
									<!-- 과제제출정보 끝 -->
									
									
									
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="30" align="right">

												<a href="javascript:goCancel();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/bttn_list.gif" border="0"></a>
												
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</form>
						<!-- form -->
						</table>
					</td>
				</tr>

<%@include file="../common/course_footer.jsp" %> 
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->