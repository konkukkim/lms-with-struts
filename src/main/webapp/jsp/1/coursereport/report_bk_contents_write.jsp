<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.coursereport.dto.ReportBankContentsDTO" %>
<%@ page import ="com.edutrack.common.dto.EditorParam"%>
<%@include file="../common/course_header.jsp" %>
<%
    ReportBankContentsDTO contentsInfo = (ReportBankContentsDTO)model.get("contentsInfo");
    String pMODE        = (String)model.get("pMODE");
    String pGubun       = (String)model.get("pGubun");
    String pCourseId    = (String)model.get("pCourseId");
    String pBankId    = (String)model.get("pBankId");
    String pReportId      = (String)model.get("pReportId");

    String[] width      = new String[]{"150","407"};

%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ReportBankWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_report/reportBankContents.js"></script>

							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"과제문제은행관리")%></b></font></td>
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
<!-- form start  action="javascript:goSubmit();"  -->
<form name="Input" method="post" enctype="multipart/form-data">
<input type=hidden name="pMODE"  value="<%=pMODE%>">
<input type=hidden name="pGubun"  value="<%=pGubun%>">
<input type=hidden name="pCourseId" value="<%=pCourseId%>">
<input type=hidden name="pBankId" value="<%=pBankId%>">
<input type=hidden name="pReportId"  value="<%=pReportId%>">
<input type="hidden" name="pOldRfile"  value="<%=contentsInfo.getRfileName()%>">
<input type="hidden" name="pOldSfile"  value="<%=contentsInfo.getSfileName()%>">
<input type="hidden" name="pOldFilePath" value="<%=contentsInfo.getFilePath()%>">
<input type="hidden" name="pOldFileSize" value="<%=contentsInfo.getFileSize()%>">
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">제목</td>
												<td class="s_tab_view02"><input type="text" name="pSubject" size="70" value="<%=contentsInfo.getReportSubject()%>"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">내용</td>
												<td class="s_tab_view03"><textarea name="pContents" rows=15 cols=82><%=contentsInfo.getReportContents()%></textarea>
<%
	EditorParam editerParam = new EditorParam();
	editerParam.setShowFlag("true");
	editerParam.setWidth(520);
	editerParam.setHeight(300);
	editerParam.setToolBarHidden("attachFile");
	out.print(CommonUtil.getEditorScript(editerParam));
%>

												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">첨부파일</td>
												<td class="s_tab_view03">
<%
	if (!StringUtil.nvl(contentsInfo.getSfileName()).equals("")) {
		out.print("<div id='fileDiv' style='display:block'><a href=\"javascript:fileDownload('"+StringUtil.nvl(contentsInfo.getRfileName())+"','"+StringUtil.nvl(contentsInfo.getSfileName())+"','"+StringUtil.nvl(contentsInfo.getFilePath())+"','"+StringUtil.nvl(contentsInfo.getFileSize())+"');\">"+StringUtil.nvl(contentsInfo.getRfileName())+"</a>");
		out.print("&nbsp;&nbsp;");
		out.print("<a href='javascript:delFile()'>[기존파일삭제]</a>");
		out.print("</div>");
	}
%>
<input type="file" name="pFile" size="60">
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
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
<!-- 페이지초기화 -->
<script language="javascript">init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>');</script>

<%@include file="../common/course_footer.jsp" %>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->

