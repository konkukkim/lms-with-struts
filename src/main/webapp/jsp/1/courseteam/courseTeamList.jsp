<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/course_header.jsp" %>
<%
   String pCourseId = (String)model.get("pCourseId");
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CourseTeamWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/popupbox.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_team/courseTeam.js"></script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"팀관리")%></b></font></td>
									<!-- // classroom title -->
									<!-- history -->
									<td class="c_stit_history" valign="bottom" align="right" width="327">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
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
<form name="f">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
<%
	if(COURSELISTSIZE > 1 ) {
		out.println("과목 : ");

		CodeParam param = new CodeParam();
	   	param.setSystemcode(SYSTEMCODE); 			// 시스템코드 설정
	   	param.setType("select"); 					// select, check, radio 유형을 선택한다.
	   	param.setName("pCourseId"); 				// 객체의 이름을 정한다.
	   	param.setFirst("--과목선택--"); 	// 리스트 처음에 보여줄 문자를 입력한다.
	   	param.setEvent("autoReload()"); 			// onChange 이벤트가 발생했을 경우에 호출되어질 자바 스크립트 함수 정의
	   	param.setSelected(pCourseId); 				// 선택되어져야 할 값 선언

	   	out.print(CommonUtil.getCurriCourse(param, CURRICODE,CURRIYEAR,CURRITERM,PROFID));
	}	%>
															</td>
															<td align=right width="50%" height=30>
<script language=javascript>Button5("팀생성", "writeTeam()", "");</script>
															</td>
														</tr>
													</table>
												</td>
											</tr>
</form>
<!-- form end -->
<!-- form start -->
<form name='teamNameForm'>
											<tr>
												<td colspan="11">
<!-- 팀입력 박스 시작-->
<div id="inputBox" style="width:100%;display:none">
													<table width="100%" align="center">
														<tr class="s_tab05">
															<td colspan="3"></td>
														</tr>
														<tr>
															<td width="90" class="s_tab_view01">팀이름</td>
															<td class="s_tab_view02"><input type=text name="pTeamName" value="" size="60" maxlength="100" dispName="팀이름" notNull></td>
															<td class="s_tab_view02" align="right">
<a href="javascript:registTeam('ADD','');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_e.gif" border="0" align="absmiddle"></a>
<a href="javascript:closeTeamWrite();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_d.gif" border="0" align="absmiddle"></a>
															</td>
														</tr>
														<tr class="s_tab05">
															<td colspan="3"></td>
														</tr>
														<tr>
															<td colspan="11" height="15"></td>
														</tr>
													</table>
</div>
<!-- 팀입력 박스 끝-->
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="310">팀이름</td>
												<td class="s_tablien"></td>
												<td width="080">팀원수</td>
												<td class="s_tablien"></td>
												<td width="100">팀원관리</td>
												<td class="s_tablien"></td>
												<td width="110">수정/삭제</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11">
										  			<!-- 리스트 -->
										  				<div id="teamList" style="width:100%;display:none"></div>
										  			<!-- 리스트 -->
												</td>
											</tr>
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

<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pCourseId%>');
	var popupbox1 = PopupBox("팀원목록", "width=515,height=460,style=normal,bgcolor=white,modal=no,resizable=no,move=yes,titlebar=no,position=center");
</script>