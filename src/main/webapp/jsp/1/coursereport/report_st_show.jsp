<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.coursereport.dto.ReportInfoDTO" %> 
<%@include file="../common/popup_header.jsp" %>
<%
    ReportInfoDTO	reportInfo		=	(ReportInfoDTO)model.get("reportInfo");
   
    String[] 		width			=	new String[]{"190","647"};	
	String			reportSubject	=	StringUtil.nvl(reportInfo.getReportSubject(), "");
	String 			reportType2		=	StringUtil.nvl(reportInfo.getReportType2());
	
	String reportStartDate  =	StringUtil.nvl(reportInfo.getReportStartDate(), "");
	String reportEndDate  =	StringUtil.nvl(reportInfo.getReportEndDate(), "");
	String reportExtendDate  =	StringUtil.nvl(reportInfo.getReportExtendDate(), "");
	
	String reportScoreYn	=	StringUtil.nvl(reportInfo.getReportScoreYn(), "");
	String reportOpenYn		=	StringUtil.nvl(reportInfo.getReportOpenYn(), "");	
	String reportRegistYn	=	StringUtil.nvl(reportInfo.getReportRegistYn(), "");	
	
%>	

                </tr>
                <!-- main 시작 -->
                <tr> 
					<td height="410" align="left" valign="top" style="padding:0 10 0 15">
							
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<!-- form start -->
							<tr align="left" valign="middle"> 
			                	<td width="100%" height="45">
			                		<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움체' size="2"><b><%=CommonUtil.getPageTitle(request,"과제상세정보")%></b></font>
			                	</td>
			              	</tr>
							<tr>
								<td align="center" valign="top">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="2" class="b_td01"> </td>
										</tr>
										<tr>
											<td height="30" > 
												<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td width="80" align="center" class="b_td02">과제출제방식</td>
														<td width="4" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti1.gif"></td>
														<td style="padding:0 10 0 10" width="250">

<% 	if(reportType2.equals("R")) {	%>
															랜덤출제
															
<%	} else if(reportType2.equals("C")) {	%>
															과제선택
<%	}%>
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
											<td height="1" class="b_td03"></td>
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
														<td width="80" align="center" class="b_td02">과제공개여부</td>
														<td width="4" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti1.gif"></td>
														<td style="padding:0 10 0 10" width="250">
<% 	if(reportOpenYn.equals("Y")) {	%>
															공개
															
<%	} else if(reportOpenYn.equals("N")) {	%>
															미공개
<%	}%>
														</td>
														<td width="80" align="center" class="b_td02">과제등록여부</td>
														<td width="4" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti1.gif"></td>
														<td style="padding:0 10 0 10">
<% 	if(reportRegistYn.equals("Y")) {	%>
															등록
															
<%	} else if(reportRegistYn.equals("N")) {	%>
															미등록
<%	}%>
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
									</table>
												<!-- 버튼 -->
												<table width="100%" border="0" cellspacing="0" cellpadding="0">
													<tr> 
														<td height="30" align="right">
															<a href="javascript:window.close();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/bttn_close.gif" border="0"></a>												
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>								

								</td>
							</tr>
						</table>
					</td>
				</tr>
<%@include file="../common/popup_footer.jsp" %>

