<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@include file="../common/course_header.jsp" %>
<%
   String 	pCourseId 	= 	(String)model.get("pCourseId");
   if(COURSELISTSIZE == 1 && pCourseId.equals(""))  pCourseId = COURSEID;
%>


<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/popupbox.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/contextmenu.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CourseForumWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_forum/courseForumList.js"></script>


								<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"토론방")%></b></font></td>
									<!-- // classroom title -->
									<!-- history -->
									<td class="c_stit_history" valign="bottom" align="right" width="327">
									<!-- // history -->
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
										<!--- form ---------------------->
                						<form name="f">
                						<input type="hidden" name="curPage" value="">
                						<tr>
                						    <td colspan="20">
												<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%" height="28">
													<tr>
<%	if (!USERTYPE.equals("S") && COURSELISTSIZE > 1 ) { %>
														<td widht="50%" align="left">과 목 명
<%		CodeParam param = new CodeParam();
		param.setSystemcode(SYSTEMCODE); 				// 시스템코드 설정
		param.setType("select"); 						// select, check, radio 유형을 선택한다.
		param.setName("pCourseId"); 					// 객체의 이름을 정한다.
		//param.setFirst("-- 과목을 선택 하세요 --"); 	// 리스트 처음에 보여줄 문자를 입력한다.
		param.setEvent("autoReload('')"); 			    // onChange 이벤트가 발생했을 경우에 호출되어질 자바 스크립트 함수 정의
		param.setSelected(pCourseId); 					// 선택되어져야 할 값 선언
		out.print(CommonUtil.getCurriCourse(param, CURRICODE,CURRIYEAR,CURRITERM,PROFID));	%>
														</td>
<%	} else {	%>
														<td><input type="hidden" name="pCourseId"   value="<%=pCourseId%>"></td>
<%	}

	if ( ( USERTYPE.equals("M") || USERTYPE.equals("C") ||  USERTYPE.equals("P"))) {	%>
														<td width="50%" align="right">
															<Script Language=Javascript>Button5('결과보기','goResult()','')</Script>
															<Script Language=Javascript>Button5('토론방개설','goAdd()','')</Script>
														</td>
<%	}	%>
													</tr>
												</table>
											</td>
										</tr>
										<tr class="s_tab01">
											<td colspan="20"></td>
										</tr>
										<tr class="s_tab02">
<%		if (!USERTYPE.equals("S")) { 	%>
											<td width="50">번호</td>
											<td class="s_tablien"></td>
											<td width="210" >제목</td>
											<td class="s_tablien"></td>
											<td width="150" >토론기간</td>
											<td class="s_tablien"></td>
											<td width="70" >운영방식</td>
											<td class="s_tablien"></td>
											<td width="70" >공개여부</td>
											<td class="s_tablien"></td>
											<td width="70" >등록대기</td>
											<td class="s_tablien"></td>
											<td width="50" >입장</td>
<%		} else {  //-- 학생일 때  %>

											<td width="50" >번호</td>
											<td class="s_tablien"></td>
											<% if(COURSELISTSIZE>1){ %>
											<td width="100" >과목</td>
											<td class="s_tablien"></td>
											<td width="210" >제목</td>
											<td class="s_tablien"></td>
											<% } else { %>
											<td width="310" >제목</td>
											<td class="s_tablien"></td>
											<% } %>
											<td width="150" >토론기간</td>
											<td class="s_tablien"></td>
											<td width="80" >운영방식</td>
											<td class="s_tablien"></td>
											<td width="80" >입장</td>
<% 	}	%>
										</tr>
										<tr class="s_tab03">
											<td colspan="20"></td>
										</tr>
									    <!-- 리스트 -->
									    <tr>
											<td colspan="20"><div id="courseForumInfoList" style="width:100%;display:none"></div></td>
										</tr>
                        			  	<!-- 리스트 -->
										<tr class="s_tab05">
											<td colspan="20"></td>
										</tr>
<%	if ( ( USERTYPE.equals("M") || USERTYPE.equals("C") ||  USERTYPE.equals("P"))) {	%>

										<tr>
											<td colspan="20" height=25>
											    <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/b_ico01.gif" align="absmiddle" width="11" height="17" border="0">
											    &nbsp;<b>운영방식(팀별)</b>을 클릭하시면 팀과 멤버를 구성할 수 있습니다.
											</td>
										</tr>
<%  } %>
                        			  	<!-- 페이지 리스트 -->
										<tr>
											<td colspan="11" align=center>
												<table valign=top height="25">
													<tr>
														<td>
															<div id="getPagging" style="width:100%;display:none"></div>
														</td>
													</tr>
												</table>

											</td>
										</tr>
                                        </form>
										<!-- // 페이지 리스트 -->
									</table>
									<!-- // 게시판 리스트  끝 -->
									<!-- // 내용 -->
								</td>
							</tr>
						</table>
					</td>
					<!-- // 본문 -->


<Script Language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=CURRICODE%>','<%=CURRIYEAR%>','<%=CURRITERM%>','<%=pCourseId%>','','<%=PROFID%>', '<%=USERTYPE %>','<%=COURSELISTSIZE %>');
</Script>

<%@include file="../common/course_footer.jsp" %>

