<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.dto.EditorParam"%>
<%@include file="../common/course_header.jsp" %>
<%
    String pCourseId = (String)model.get("pCourseId");
    String pBankId = (String)model.get("pBankId");
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ReportBankWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_report/reportBank.js"></script>
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
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과목</td>
												<td class="s_tab_view02" colspan="3">
<%	if (COURSELISTSIZE > 1) {
		CodeParam param = new CodeParam();
		param.setSystemcode(SYSTEMCODE);
		param.setType("select");
		param.setName("pCourseId");
		//param.setFirst("-- 과목 선택 --");
		param.setEvent("javascript:reportBankInfoSelectList('N')");
		param.setSelected(pCourseId);
		out.print(CommonUtil.getCurriCourse(param, CURRICODE,CURRIYEAR,CURRITERM,PROFID));
	} else {
%>
													<%=CommonUtil.getCourseName(SYSTEMCODE,pCourseId)%><input type=hidden name=pCourseId value="<%=pCourseId%>">
<%	}	%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">항목명</td>
												<td class="s_tab_view02" colspan="3">
<div id="reportBankInfoSelectDiv" style="width:100%;display:block">
	<select name="pBankId" onChange="autoReload()"></select>
	<a href="javascript:manageReportBankInfo('ADD');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_item01.gif" align="absmiddle" vspace="2" hspace="5" border="0"></a>
	<a href="javascript:manageReportBankInfo('EDIT');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_item02.gif" align="absmiddle" vspace="2" hspace="5"></a>
	<a href="javascript:manageReportBankInfo('DEL')"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_item03.gif" align="absmiddle" vspace="2" hspace="5"></a>
</div>
<div id="reportBankWriteDiv" style="width:100%;display:none">
	<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="42%">
				<input type=text name="pBankName" value="" size="40" maxlength="100" >&nbsp;&nbsp;
			</td>
			<td align="left">
				<div id="regButtonDiv" style="display:block">
					<a href="javascript:registReportBankInfo('ADD');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_e.gif" border="0" class="no" align="absmiddle"></a>
					<a href="javascript:closeReportBankInfoWrite();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_d.gif" border="0" align="absmiddle"></a>
				</div>
				<div id="modButtonDiv" style="display:none">
					<a href="javascript:registReportBankInfo('EDIT');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_c.gif" border="0" class="no" align="absmiddle"></a>
					<a href="javascript:closeReportBankInfoWrite();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_d.gif" border="0" align="absmiddle"></a>
				</div>
			</td>
		</tr>
	</table>
</div>
												</td>
											</tr>
<!--
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">문제추가</td>
												<td class="s_tab_view02" colspan="3">
<a href="javascript:addContents('J');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_add01.gif" align="absmiddle" border="0"></a>
<a href="javascript:addContents('F');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_add02.gif" align="absmiddle" border="0"></a>
												</td>
											</tr>
-->

											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td height="15" colspan="4"></td>
											</tr>
										</table>

										<table width="670" align="center">
											<tr>
												<td colspan="11" class="s_btn01" align="right">
<script language=javascript>Button5("문제추가", "addContents('J')", "");</script>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="499">문제내용</td>
												<td class="s_tablien"></td>
												<td width="100">첨부파일</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11">
													<!-- 리스트 -->
												  		<div id="reportBankContentsList" style="width:100%;display:none"></div>
												  	<!-- 리스트 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
										</table>


										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pCourseId%>','<%=pBankId%>');
</script>
<%@include file="../common/course_footer.jsp" %>

