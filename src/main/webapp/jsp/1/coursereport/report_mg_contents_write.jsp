<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.coursereport.dto.ReportContentsDTO,com.edutrack.common.dto.EditorParam"%>
<%@ page import = "com.edutrack.coursereport.dto.ReportInfoDTO"%>
<%@include file="../common/course_header.jsp" %>
<%
    ReportContentsDTO	contentsInfo	=	(ReportContentsDTO)model.get("contentsInfo");
    String	pMODE			=	(String)model.get("pMODE");
    String	pCourseId		=	(String)model.get("pCourseId");
    String	pReportId		= 	(String)model.get("pReportId");
    String	pReportType		=	(String)model.get("pReportType");
    String	pReportOrder 	=	(String)model.get("pReportOrder");
    String	pReportNo		=	(String)model.get("pReportNo");
    String	answerUserCnt	=	(String)model.get("answerUserCnt");
    String	pTeamMode		=	(String)model.get("pTeamMode");
	String	pParentReportId =	(String)model.get("pParentReportId");
    String	answer			=	contentsInfo.getAnswer();
    String	multiAnswer		=	contentsInfo.getMultiAnswer();
    String[]	width 		=	new String[]{"150","407"};

	String selJ = " selected";
	String selF ="";
	if(pReportType.equals("J")) selJ = " selected";
	if(pReportType.equals("F")) selF = " selected";
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ReportBankWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ReportContentsWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_report/reportMgContents.js"></script>

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
<!-- form start    action="javascript:goSubmit();" -->
<form name="Input" method="post" enctype="multipart/form-data">
<input type="hidden" name="pMODE" value="<%=pMODE%>">
<input type="hidden" name="pCourseId" value="<%=pCourseId%>">
<input type="hidden" name="pReportId" value="<%=pReportId%>">
<input type="hidden" name="pReportNo" value="<%=pReportNo%>">
<input type="hidden" name="answerUserCnt" value="<%=answerUserCnt%>">
<input type="hidden" name="pTeamMode" value="<%=pTeamMode%>">
<input type="hidden" name="pParentReportId" value="<%=pParentReportId%>">
<input type="hidden" name="pOldRfile" value="<%=contentsInfo.getRfileName()%>">
<input type="hidden" name="pOldSfile" value="<%=contentsInfo.getSfileName()%>">
<input type="hidden" name="pOldFilePath" value="<%=contentsInfo.getFilePath()%>">
<input type="hidden" name="pOldFileSize" value="<%=contentsInfo.getFileSize()%>">

											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
<%	//-- 입력일 경우
	if (pMODE.equals("ADD")) {	%>
											<tr>
												<td class="s_tab_view01" width="120">문제유형</td>
												<td class="s_tab_view02" width="200">
													<select name='pReportType'onchange="changeReportType(this.value)">
														<option value='J' <%=selJ%>>서술형</option>
														<option value='F' <%=selF%>>파일형</option>
													</select>
												</td>
												<td class="s_tab_view01" width="90">소속분류</td>
												<td class="s_tab_view02"">
													<input type="text" name="pReportOrder" size="3" value="<%=contentsInfo.getReportOrder()%>">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%	//-- 수정일 경우
	} else {	%>
											<tr>
												<td class="s_tab_view01" width="120">문제유형</td>
												<td class="s_tab_view02" width="200">
													<%=CommonUtil.getContentsTypeName(pReportType)%>
				                                    <input type="hidden" name="pReportOrder" value="<%=contentsInfo.getReportOrder()%>">
				                                    <input type="hidden" name="pReportType" value="<%=pReportType%>">
												</td>
												<td class="s_tab_view01" width="90">문제번호</td>
												<td class="s_tab_view02"">
													<b><%=contentsInfo.getReportOrder()%></b>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%	}	%>
											<tr>
												<td width="15" colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">문제</td>
												<td class="s_tab_view03" colspan="3"">
													<textarea name="pContents" cols="75" rows="10" wrap="VIRTUAL"><%=contentsInfo.getContents()%></textarea>
<%	EditorParam editerParam = new EditorParam();
	editerParam.setShowFlag("true");
	editerParam.setWidth(520);
	editerParam.setHeight(300);
	editerParam.setToolBarHidden("attachFile");
	out.print(CommonUtil.getEditorScript(editerParam));	%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">첨부파일</td>
												<td class="s_tab_view02" colspan="3">
<%
	if (!StringUtil.nvl(contentsInfo.getSfileName()).equals("")) {
		out.print("<div id='fileDiv' style='display:block'><a href=\"javascript:fileDownload('"+StringUtil.nvl(contentsInfo.getRfileName())+"','"+StringUtil.nvl(contentsInfo.getSfileName())+"','"+StringUtil.nvl(contentsInfo.getFilePath())+"','"+StringUtil.nvl(contentsInfo.getFileSize())+"');\">"+StringUtil.nvl(contentsInfo.getRfileName())+"</a>");
		out.print("&nbsp;&nbsp;");
		out.print("<a href='javascript:delFile()'>[기존파일삭제]</a>");
		out.print("</div>");
	}
%>
													<input type="file" name="pFile" size="70">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>

											<tr>
												<td colspan="4">
<!-- 서술형 정답 시작-->
<div id="reportWriteJ" style="display:block">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td class="s_tab_view01" width="120">정답</td>
															<td class="s_tab_view03" colspan="3"><textarea name="pAnswer" rows="3" cols="75"><%=contentsInfo.getAnswer()%></textarea></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="4"></td>
														</tr>
													</table>
</div>
<!-- 서술형 끝-->
												</td>
											</tr>

											<tr>
												<td class="s_tab_view01" width="120">문제에 대한 해설</td>
												<td class="s_tab_view03" colspan="3"><textarea name="pComment" cols="75" rows=5 wrap="VIRTUAL"><%=contentsInfo.getComment()%></textarea></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%
	if (pMODE.equals("ADD")) {
%>
											<tr>
												<td class="s_tab_view01" width="120">문제은행</td>
												<td class="s_tab_view02" colspan="3">
													* 등록하시는 문제를 문제은행에 자동 등록 하시겠습니까?
													<input type="radio" name="pAutoBankRegist" value="Y" style=border=0 onClick="showBank('block')"> 예
													<input type="radio" name="pAutoBankRegist" value="N" style=border=0 checked onClick="showBank('none')">아니오
<div id="reportBankInfoDiv" style="width:100%;display:none">
													<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td>
	<div id="reportBankInfoSelectDiv" style="width:100%;display:block">
																<select name="pBankCode"></select>
											                 	<a href="javascript:manageReportBankInfo();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/list_add.gif" width="53" height="17" align="absmiddle" vspace="2" hspace="5" border="0"></a>
											                 	&nbsp;<font color='blue'>* 항목선택 또는 항목을 추가하세요.</font>
	</div>
	<div id="reportBankWriteDiv" style="width:100%;display:none">
																<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
																	<tr>
																		<td width="42%">
																			<input type=text name="pBankName" value="" size="40" maxlength="100" >&nbsp;&nbsp;
																		</td>
																		<td align="left">
											                   		    	<a href="javascript:registReportBankInfo('ADD');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_e.gif" border="0" class="solid0" align="absmiddle"></a>
																			<a href="javascript:closeReportBankInfoWrite();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_d.gif" border="0" align="absmiddle"></a>
																		</td>
																	</tr>
																</table>
	</div>
															</td>
														</tr>
													</table>
</div>
												</td>
											</tr>
<%
	}
%>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<div id="regButtonDiv" style="display:block">
<script language=javascript>Button3("등록", "goSubmit()", "");</script>
&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
</div>
<div id="modButtonDiv" style="display:none">
<script language=javascript>Button3("수정", "goSubmit()", "");</script>
&nbsp;<script language=javascript>Button3("삭제", "goDelete()", "");</script>
&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
</div>
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
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pCourseId%>');
</script>
<%@include file="../common/course_footer.jsp" %>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->