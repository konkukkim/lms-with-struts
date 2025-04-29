<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/course_header.jsp" %>
<%
    String pCourseId = (String)model.get("pCourseId");
%>
<script language="javascript">
    function Create_Report(){
       var f = document.f;

       if(<%=COURSELISTSIZE%> > 1 && f.pCourseId.value == ""){
         alert("먼저 과목을 선택해 주십시요.");
         return;
       }

       f.pMODE.value = "write";
       f.submit();
    }

    function Show_ReportContents(reportid, reportType, parentid){
       var f = document.f;

       f.pReportId.value = reportid;
       f.pReportType.value = reportType;
       f.pParentReportId.value = parentid;
       f.action="<%=CONTEXTPATH%>/ReportResult.cmd?cmd=reportUserList";
       f.submit();
    }

    function Show_ReportInfo(reportid){
	   var f = document.f;
	   var courseid = f.pCourseId.value;
	   var loc="<%=CONTEXTPATH%>/ReportAdmin.cmd?cmd=reportStShow&pCourseId="+courseid+"&pReportId="+reportid;
	   ShowInfo = window.open(loc,"test","left=0,top=0,toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=auto,resizable=yes,width=600,height=400");
    }

    function Change_Course(){
       var f = document.f;
       f.action="<%=CONTEXTPATH%>/ReportResult.cmd?cmd=reportResultList";
       f.submit();
    }
</script>
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
<!-- form start -->
<form name="f" method="post" action="<%=CONTEXTPATH%>/ReportAdmin.cmd?cmd=reportWrite" >
<input type=hidden name="pMODE" value="">
<input type=hidden name="pReportId" value="">
<input type=hidden name="pReportType" value="">
<input type=hidden name="pParentReportId" value="">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td>
<%
	if (COURSELISTSIZE > 1) {
%>
과목 : <%
		CodeParam param = new CodeParam();
		param.setSystemcode(SYSTEMCODE);
		param.setType("select");
		param.setName("pCourseId");
		param.setFirst("-- 과목 선택 --");
		param.setEvent("Change_Course()");
		param.setSelected(pCourseId);
		out.print(CommonUtil.getCurriCourse(param, CURRICODE,CURRIYEAR,CURRITERM,PROFID));
	} else {
%>
																<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" width="18" height="16" align="absmiddle">
																<%=CommonUtil.getCourseName(SYSTEMCODE,pCourseId)%><input type=hidden name=pCourseId value="<%=pCourseId%>">
<%
	}
%>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="">과제명 (평가하기)</td>
												<td class="s_tablien"></td>
												<td width="150">과제기간</td>
												<td class="s_tablien"></td>
												<td width="80">과제정보</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="7"></td>
											</tr>
<%	int No = 0;
	RowSet list = (RowSet)model.get("reportList");

	if (list != null) {
		while (list.next()) {
			No++;

			if(No > 1) {
%>
											<tr class="s_tab03">
												<td colspan="7"></td>
											</tr>
<%			} %>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04"><a href="javascript:Show_ReportContents('<%=list.getInt("report_id")%>', '<%=StringUtil.nvl(list.getString("report_type"))%>', '<%=list.getInt("parent_report_id")%>');"><%=StringUtil.nvl(list.getString("subject"))%></a></td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("start_date")))+" ~ "+DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("end_date")))%></td>
												<td></td>
												<td class="s_tab04_cen"><a href="javascript:Show_ReportInfo('<%=list.getInt("report_id")%>');"><b>[보기]</b></a></td>
											</tr>
<%		}

		list.close();
	}

	if (No < 1) {	%>

											<tr>
												<td class="s_tab04_cen" colspan="7">과제 리스트가 존재하지 않습니다.</td>
											</tr>
<%	}	%>
											<tr class="s_tab05">
												<td colspan="11"></td>
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