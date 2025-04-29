<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.coursereport.dto.ReportSubInfoDTO" %>
<%@ page import ="com.edutrack.common.dto.UserMemDTO, com.edutrack.common.dto.CurriMemDTO" %>
<%@ page import ="com.edutrack.common.dto.EditorParam"%>
<%@include file="../common/popup_header.jsp" %>
<%
    ReportSubInfoDTO reportSubInfo = (ReportSubInfoDTO)model.get("reportSubInfo");
    String pMODE        = (String)model.get("pMODE");
    String pCourseId    = (String)model.get("pCourseId");
    String pReportId    = (String)model.get("pReportId");
    String pSubReportId = (String)model.get("pSubReportId");
    String[] width      = new String[]{"150","407"};

    UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	CurriMemDTO curriMemDto = userMemDto.curriInfo;
	String CURRIYEAR = curriMemDto.curriYear;
	String CURRITERM = curriMemDto.curriTerm;
	String CURRINAME = curriMemDto.curriName;
	String USERNAME = userMemDto.userName;

%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/scriptaculous/lib/prototype.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/scriptaculous/src/effects.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/scriptaculous/src/scriptaculous.js"></script>

<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/util.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ReportBankWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ReportSubInfoWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_report/reportInfo.js"></script>

	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">과제문제관리</font></td>
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
<!-- form start   action="javascript:" -->
<form name="Input" method="post" enctype="multipart/form-data">
<input type=hidden name="pMODE"  value="<%=pMODE%>">
<input type=hidden name="pCourseId" value="<%=pCourseId%>">
<input type=hidden name="pReportId" value="<%=pReportId%>">
<input type=hidden name="pSubReportId" value="<%=pSubReportId%>">
<input type="hidden" name="pOldRfile"  value="<%=reportSubInfo.getRfileName()%>">
<input type="hidden" name="pOldSfile"  value="<%=reportSubInfo.getSfileName()%>">
<input type="hidden" name="pOldFilePath" value="<%=reportSubInfo.getFilePath()%>">
<input type="hidden" name="pOldFileSize" value="<%=reportSubInfo.getFileSize()%>">
								<tr class="s_tab01">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td class="s_tab_view01" width="20%">제목</td>
									<td class="s_tab_view02" width="80%"><input type="text" name="pSubject" size="70" value="<%=StringUtil.nvl(reportSubInfo.getSubReportSubject())%>"></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">문제내용</td>
									<td class="s_tab_view03">
<textarea name="pContents" rows="9" cols="70" wrap="VIRTUAL"><%=StringUtil.nvl(reportSubInfo.getSubReportContents())%></textarea>
<%
	EditorParam editerParam = new EditorParam();
	editerParam.setShowFlag("true");
	editerParam.setWidth(450);
	editerParam.setHeight(350);
	editerParam.setToolBarHidden("attachFile");
	out.print(CommonUtil.getEditorScript(editerParam));
%>
									</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">첨부파일</td>
									<td class="s_tab_view02">
<%
	if (!StringUtil.nvl(reportSubInfo.getSfileName()).equals("")) {
		out.print("<div id='fileDiv' style='display:block'><a href=\"javascript:fileDownload('"+StringUtil.nvl(reportSubInfo.getRfileName())+"','"+StringUtil.nvl(reportSubInfo.getSfileName())+"','"+StringUtil.nvl(reportSubInfo.getFilePath())+"','"+StringUtil.nvl(reportSubInfo.getFileSize())+"');\">"+StringUtil.nvl(reportSubInfo.getRfileName())+"</a>");
		out.print("&nbsp;&nbsp;");
		out.print("<a href='javascript:delFile()'>[기존파일삭제]</a>");
		out.print("</div>");
	}
%>
		<input type="file" name="pFile" size="55">
									</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
<% if(pMODE.equals("ADD")) { %>
								<tr>
									<td class="s_tab_view01">문제은행등록</td>
									<td class="s_tab_view02">
										&nbsp;해당 문제를 문제은행에 자동 등록 하시겠습니까?
										<input type="radio" name="pBankInfoYn" value="Y" style=border=0 class="solid0" onclick="javascript:pBankView()">예&nbsp;&nbsp;
           				  				<input type="radio" name="pBankInfoYn" value="N" style=border=0 class="solid0" checked onclick="javascript:pBankView()">아니오
									</td>
								</tr>
<% } %>
								<tr>
									<td colspan="2" height="1">
<div id="reportBankAutoYn" style="width:100%;display:none">
										<table width="100%" cellpadding="0" cellspacing="0" border="0">
											<tr>
												<td height="1" colspan="2" class="c_test_tablien01"> </td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="20%" height="30">항목명</td>
												<td width="80%">
													<table width="100%" cellpadding="0" cellspacing="0" border="0">
														<tr>
															<td class="s_tab_view02" height="30">
	<div id="reportBankInfoSelectDiv" style="width:100%;display:block">
		<select name="pBankId"></select>
		<a href="javascript:manageReportBankInfo('ADD');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_item01.gif" align="absmiddle" vspace="2" hspace="5" border="0"></a>
		<a href="javascript:manageReportBankInfo('EDIT');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_item02.gif" align="absmiddle" vspace="2" hspace="5"></a>
		<a href="javascript:manageReportBankInfo('DEL')"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_item03.gif" align="absmiddle" vspace="2" hspace="5"></a>
	</div>
	<div id="reportBankWriteDiv" style="width:100%;display:none">
		<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="42%">
					<input type=text name="pBankName" value="" size="40" maxlength="100" >
				</td>
				<td align="left">
					<div id="regButtonDiv" style="display:block">
						<a href="javascript:registReportBankInfo('ADD');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_e.gif" border="0" class="solid0" align="absmiddle"></a>
						<a href="javascript:closeReportBankInfoWrite();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_d.gif" border="0" align="absmiddle"></a>
					</div>
					<div id="modButtonDiv" style="display:none">
						<a href="javascript:registReportBankInfo('EDIT');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_c.gif" border="0" class="solid0" align="absmiddle"></a>
						<a href="javascript:closeReportBankInfoWrite();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_d.gif" border="0" align="absmiddle"></a>
					</div>
				</td>
			</tr>
		</table>
	</div>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
</div>
									</td>
								</tr>
								<tr class="s_tab05">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td colspan="2" align="right" valign="top" class="pop_btn">
<% if(pMODE.equals("ADD")) { %>
<script language=javascript>Button3("등록", "goSubmit('<%=pMODE%>')", "");</script>
<% } else {  %>
<script language=javascript>Button3("수정", "goSubmit('<%=pMODE%>')", "");</script>
&nbsp;<script language=javascript>Button3("삭제", "goSubDelete('<%=CONTEXTPATH%>')", "");</script>
<% } %>
								</tr>
							</table>
						</td>
					</tr>
</form>
<!-- form -->
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><a href="javascript:window.close();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif"></a></td>
		</tr>
	</table>
<% if(pMODE.equals("ADD")) { %>
<script language="javascript">
	init('<%=CONTEXTPATH%>','<%=SYSTEMCODE%>','<%=pCourseId%>','<%=pReportId%>','write');
</script>
<% } %>
<%@include file="../common/popup_footer.jsp" %>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->
