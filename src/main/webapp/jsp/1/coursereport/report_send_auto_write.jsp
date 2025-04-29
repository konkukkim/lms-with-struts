<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.courseteam.dto.CourseTeamInfoDTO,com.edutrack.courseteam.dto.CourseTeamMemberDTO,com.edutrack.coursereport.dto.ReportInfoDTO,com.edutrack.coursereport.dto.ReportSendDTO "%>
<%@include file="../common/course_header.jsp" %>
<%
   String 	pCourseId		= 	(String)model.get("pCourseId");
   String 	pReportId 		= 	(String)model.get("pReportId");
   String 	pParReportId 	= 	(String)model.get("pParReportId");
   String	pReportType 	= 	(String)model.get("pReportType");
   String 	pTeamNo 		= 	(String)model.get("pTeamNo");

	ReportInfoDTO reportinfoView = (ReportInfoDTO)model.get("reportinfoView");
%>
<!--<%=pCourseId%>//<%=pReportId%>//--<%=pParReportId%>--//--<%=pReportType%>--//--<%=pTeamNo%>--//
--<%=reportinfoView.getCourseId()%>--//--<%=reportinfoView.getSubject()%>-->
<script language="javascript">
   function Change_Code(){
      var f = document.f1;

      f.submit();
   }

	function goParentForm() {
		var f = document.f1;
		var pCourseId =  f.pCourseId.value;
		var pReportId =  f.pParReportId.value;
		document.location = "/ReportAdmin.cmd?cmd=reportWrite&pCourseId="+pCourseId+"&pMODE=edit&pReportId="+pReportId;
	}

   function goMemInsert(){
		var f2 = document.f2;

		if(f2.pTcnt.value == 0) {
			alert("선택한 팀에는 현재 등록된 팀원이 없습니다.");
			return;
		}else{
			var stat = confirm("선택한 팀구성원을 현재 과제의 구성원으로 등록하시겠습니까?\n\n이미 다른팀에 등록된 학생인 경우 현재 구성할 팀으로 변경됩니다.");
			if (stat == true) {
				f2.action="/ReportSend.cmd?cmd=reportSendAutoRegist";
				f2.submit();
			}
		}
	}

</script>
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
<form name="f1" method="post" action="<%=CONTEXTPATH%>/ReportSend.cmd?cmd=reportSendAutoWirte">
<input type="hidden" name="pCourseId" value="<%=pCourseId%>">
<input type="hidden" name="pReportId" value="<%=pReportId%>">
<input type="hidden" name="pReportType" value="<%=pReportType%>">
<input type="hidden" name="pParReportId" value="<%=pParReportId%>">

											<tr class="s_tab05">
												<td colspan="3"></td>
											</tr>
											<tr>
												<td colspan="3" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td class="s_tab_view01" width="20%"><b>과제명<b> </td>
															<td class="s_tab_view02" width="30%"><%=reportinfoView.getSubject()%></td>
															<td class="s_tab_view01" width="20%" height=30>구성팀</td>
															<td class="s_tab_view02" width="30%">
																<select name="pTeamNo" onChange="Change_Code(this)">
																	<option value=''>---팀선택---</option>
<%
	String teamno = "";
	ArrayList teamlist = (ArrayList)model.get("courseteamList");
	CourseTeamInfoDTO teaminfo = null;
	for(int i=0; i < teamlist.size(); i++){
		teaminfo = (CourseTeamInfoDTO)teamlist.get(i);
		teamno = Integer.toString(teaminfo.getTeamNo());
%>
																	<option value="<%=teamno%>" <%if(teamno.equals(pTeamNo)){%>selected<%}%>><%=teaminfo.getTeamName()%></option>
<%
	}
%>
																</select>
															</td>
														</tr>
</form>
													</table>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="3"></td>
											</tr>
											<tr>
												<td height="30" colspan="3"></td>
											</tr>
											<tr>
												<td class="s_btn01" colspan="2"><b>* 과제구성원</b></td>
												<td class="s_btn01" height=30><b>* 팀구성원</b></td>
											</tr>
											<tr>
												<td width="315" align="center" valign="top">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr class="s_tab01">
															<td colspan="3"></td>
														</tr>
														<tr class="s_tab02">
															<td width="30%">번호</td>
															<td class="s_tablien"></td>
															<td width="70%">이름</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="3"></td>
														</tr>
<%
	int num2 = 0;
	ArrayList list2 = (ArrayList)model.get("reportsendList");
	ReportSendDTO reportsend = null;
	if(list2.size() > 0) {
		for(int i=0; i < list2.size(); i++){
			reportsend = (ReportSendDTO)list2.get(i);
			num2 = num2 + 1;
			
			if(num2 > 1) {
%>
														<tr class="s_tab03">
															<td colspan="11"></td>
														</tr>
<%			}	%>
														<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
															<input type='hidden' name='pUserId' value='<%=reportsend.getUserId()%>'>
															<td class="s_tab04_cen"><%=num2%></td>
															<td></td>
															<td class="s_tab04_cen"><%=reportsend.getUserName()%>(<%=reportsend.getUserId()%>)</td>
														</tr>

<%
	   }//end for
	 }else{
%>														<tr>
															<td class="s_tab04_cen" colspan="3">과제 구성원이 없습니다.</td>
														</tr>
<%
	}//end if
%>
														<tr class="s_tab05">
															<td colspan="11"></td>
														</tr>
													</table>
												</td>
												<td align=center valign=middle width=40>
													<table border=0 cellspacing='0' cellpadding="0" width="100%">
														<tr>
															<td align=center><br><br>
																<a href="javascript:goMemInsert()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_prev01.gif" hspace="2" align="absmiddle"></a>
															</td>
														</tr>
													</table>
												</td>
												<td width="315" align="center" valign="top">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
<form name="f2" method="post">
<input type="hidden" name="pCourseId" value="<%=pCourseId%>">
<input type="hidden" name="pReportId" value="<%=pReportId%>">
<input type="hidden" name="pReportType" value="<%=pReportType%>">
<input type="hidden" name="pParReportId" value="<%=pParReportId%>">

														<tr class="s_tab01">
															<td colspan="3"></td>
														</tr>
														<tr class="s_tab02">
															<td width="30%">번호</td>
															<td class="s_tablien"></td>
															<td width="70%">이름</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="3"></td>
														</tr>
<%
		int tcnt = 0;
		//팀을 선택한 경우
		if(!pTeamNo.equals("")) {
			int num = 0;
			ArrayList list = (ArrayList)model.get("courseteamMemberList");
			CourseTeamMemberDTO teammember = null;
			tcnt = list.size();
			if(list.size() > 0) {
				for(int i=0; i < list.size(); i++){
					teammember = (CourseTeamMemberDTO)list.get(i);
					num = num + 1;

					if(num > 1) {
%>
														<tr class="s_tab03">
															<td colspan="11"></td>
														</tr>
<%					}	%>
														<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
															<input type='hidden' name='pUserId' value='<%=teammember.getUserId()%>'>
															<td class="s_tab04_cen"><%=num%></td>
															<td></td>
															<td class="s_tab04_cen"><%=teammember.getUserName()%>(<%=teammember.getUserId()%>)</td>
														</tr>

<%
			   }//end for
			}else{
				out.println("<tr><td height='20' colspan='3' align='center'>팀구성원이 없습니다.</td></tr>");
			}
		 }else{
%>														<tr>
															<td class="s_tab04_cen" colspan="3">지정할 팀을 선택하세요.</td>
														</tr>
<%
	}//end if
%>

														<tr class="s_tab05">
															<td colspan="3"></td>
														</tr>
<input type='hidden' name='pTcnt' value='<%=tcnt%>'>
</form>
													</table>
												</td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="right">
<script language=javascript>Button3("이전", "goParentForm()", "");</script>
												</td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/course_footer.jsp" %>


