<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/course_header.jsp" %>
<%
	String courseId = StringUtil.nvl(model.get("courseId"));
	String quizConfigYn = StringUtil.nvl(model.get("quizConfigYn"));
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriContentsWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CourseWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/curri_sub/lecContents.js"></script>

							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"강의목차")%></b></font></td>
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
<%
	if (COURSELISTSIZE > 1 ) {
%>

<form name="f" method="post" action="<%=CONTEXTPATH%>/CurriContents.cmd?cmd=lecContentsList" >
											<tr>
												<td colspan="17" class="c_bbs_top">
												과목 :
<%
		CodeParam param = new CodeParam();
		param.setSystemcode(SYSTEMCODE); 		// 시스템코드 설정
		param.setType("select"); 				// select, check, radio 유형을 선택한다.
		param.setName("pCourseId"); 				// 객체의 이름을 정한다.
//		param.setFirst("-- 과목을 선택 하세요 --"); 	// 리스트 처음에 보여줄 문자를 입력한다.
		param.setEvent("changeCourseInfo()"); 	// onChange 이벤트가 발생했을 경우에 호출되어질 자바 스크립트 함수 정의
		param.setSelected(courseId); 				// 선택되어져야 할 값 선언

		out.print(CommonUtil.getCurriCourse(param, CURRICODE,CURRIYEAR,CURRITERM,PROFID));
%>
												</td>
											</tr>
</form>

<%
	}
%>


											<tr>
												<td colspan="17">
 	<!-- SCORM API -->
 		<div id="scormApi" style="width:100%;display:no"></div>
 	<!-- SCORM API -->
												</td>
											</tr>
											<tr>
												<td colspan="17" align=right valign=middle>
													<font Style="color:#606060;">
													<img src="<%= CONTEXTPATH %>/img/<%= SYSTEMCODE %>/button_img/btn_on.gif"   width=33 height=16 vspace=4 align=absmiddle>온라인강의
													<img src="<%= CONTEXTPATH %>/img/<%= SYSTEMCODE %>/button_img/btn_off.gif"  width=33 height=16 vspace=4 align=absmiddle>오프라인강의
													<img src="<%= CONTEXTPATH %>/img/<%= SYSTEMCODE %>/icon/tree/item.gif"      vspace=4 align=absmiddle>강의교재다운
													<img src="<%= CONTEXTPATH %>/img/<%= SYSTEMCODE %>/icon/tree/fmedia.gif"    vspace=4 align=absmiddle>동영상다운
													</font>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="17"></td>
											</tr>
											<tr class="s_tab02">
												<td width="40">번호</td>
												<td class="s_tablien"></td>
												<td width="220">목차명</td>
												<td class="s_tablien"></td>
												<td width="40">강의<br>구분</td>
												<td class="s_tablien"></td>
												<td width="130">수강기간</td>
												<td class="s_tablien"></td>
												<td width="45">수강<br>여부</td>
												<td class="s_tablien"></td>
												<td width="45">접속<br>회수</td>
												<td class="s_tablien"></td>
												<td width="50">접속<br>시간</td>
												<td class="s_tablien"></td>
												<td width="50">권장<br>시간</td>
												<td class="s_tablien"></td>
												<td width="45">다운<br>로드</td><!--- 퀴즈는 사용하지 않으므로 없앰 -->											</tr>
											<tr class="s_tab03">
												<td colspan="17"></td>
											</tr>
											<tr>
												<td colspan="17">
                    							<!-- 리스트 -->
                    						  		<div id="contentsList" style="width:100%;display:no"></div>
                    						  	<!-- 리스트 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="17"></td>
											</tr>
											<tr>
												<td colspan="17" height="15"></td>
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
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=classUserType%>','<%=courseId%>','<%=quizConfigYn%>');
</script>
<%@include file="../common/course_footer.jsp" %>
