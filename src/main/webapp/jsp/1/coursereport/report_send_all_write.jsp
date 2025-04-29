<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.coursereport.dto.ReportSendDTO,com.edutrack.coursereport.dto.ReportInfoDTO"%> 
<%@include file="../common/course_header.jsp" %>
<%
   String pCourseId = (String)model.get("pCourseId");
   String pReportId = (String)model.get("pReportId");
   String pReportType = (String)model.get("pReportType");
%>
<!--<%="pCourseId=>"+pCourseId%>--//-<%="pReportId=>"+pReportId%>-//-<%="pReportType=>"+pReportType%>-//-->
<script language="javascript">
	function goList() {
		var f = document.Input;
		var pCourseId =  f.pCourseId.value;
		var pReportId =  f.pReportId.value;
		document.location = "/ReportAdmin.cmd?cmd=reportWrite&pCourseId="+pCourseId+"&pMODE=edit&pReportId="+pReportId;
	}

	function setTeamPerson() {
		document.Input.submit();
	}
</Script>
							<tr valign="top"> 
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"과제출제")%></b></font></td>
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
<form name="Input" method="post" action="<%=CONTEXTPATH%>/ReportSend.cmd?cmd=reportSendAllRegist">
<input type='hidden' name="pReportId"   value="<%=pReportId%>">
<input type="hidden" name="pCourseId"   value="<%=pCourseId%>">
<input type="hidden" name="pReportType" value="<%=pReportType%>">
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="140">학습자아이디</td>
												<td class="s_tablien"></td>
												<td width="">이름</td>
												<td class="s_tablien"></td>
												<td width="160">지정팀과제</td>
												<td class="s_tablien"></td>
												<td width="110">팀선택</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	int num = 0;
	ArrayList list = (ArrayList)model.get("reportsendList");
	ReportSendDTO reportsend = null;
	if(list.size() > 0) {
		for(int i=0; i < list.size(); i++){
			reportsend = (ReportSendDTO)list.get(i);
			num = num + 1;
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
<input type='hidden' name='pUserId' value='<%=reportsend.getUserId()%>'>
												<td class="s_tab04_cen"><%=num%></td>
												<td></td>
												<td class="s_tab04_cen"><%=reportsend.getUserId()%></td>
												<td></td>
												<td class="s_tab04_cen"><%=reportsend.getUserName()%></td>
												<td></td>
												<td class="s_tab04_cen">
<%	
			if(!reportsend.getSubject().equals("")) { 
				out.println(reportsend.getSubject());
			} else {
				out.println("<font color='red'>미지정</font>");
			}
%>
												</td>
												<td></td>
												<td class="s_tab04_cen">
													<select name='pSubReportId'>
<%
			//팀과제일 경우
			if(pReportType.equals("T")) {	
				ArrayList list2 = (ArrayList)model.get("reportteamList");
				ReportInfoDTO reportinfo = null;
				for(int j=0; j < list2.size(); j++){
					reportinfo = (ReportInfoDTO)list2.get(j);
%>
    				        							<option value='<%=reportinfo.getReportId()%>' <%if(reportsend.getSubReportId() == reportinfo.getReportId()){%>selected <%}%>><%=reportinfo.getSubject()%></option>
<%
				}	//end for
			} else {
%>
    				       								<option value='0'>전체</option>
<%
			}
%>
													</select>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
		}//end for
	} else {
%>
											<tr>
												<td class="s_tab04_cen" colspan="11">강의가 종료된 과정이 없습니다.</td>
											</tr>

											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
<%
	}//end if
%>
											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="right">
<script language=javascript>Button3("인원지정", "setTeamPerson()", "");</script>
&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
												</td>
											</tr>
</form>
<!-- form end -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/course_footer.jsp" %>