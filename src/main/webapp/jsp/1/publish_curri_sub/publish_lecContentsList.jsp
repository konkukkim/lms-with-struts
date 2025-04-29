<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.currisub.dao.CurriSubCourseDAO, com.edutrack.currisub.dto.CurriCourseListDTO"%>
<%@include file="../common/header.jsp" %>
<%
	String 	classUserType	=	USERTYPE;
	String	CURRICODE	=	StringUtil.nvl(model.get("pCurriCode"));
	int		CURRIYEAR	=	StringUtil.nvl(model.get("curriYear"), 0);
	int		CURRITERM	=	StringUtil.nvl(model.get("curriTerm"), 0);
	
	//-- 과정에 속한 과목의 리스트를 가져온다.
	CurriSubCourseDAO curriSubCoruseDao = new CurriSubCourseDAO();
	ArrayList COURSELIST = new ArrayList();
	CurriCourseListDTO CourseListDto = null;
	String PROFID = "";

	if(USERTYPE.equals("P")) PROFID = USERID;
		COURSELIST = curriSubCoruseDao.getCurriCourseList(SYSTEMCODE,CURRICODE,CURRIYEAR,CURRITERM,PROFID);

	//-- 과목 갯수는 COURSELIST.size()
	int COURSELISTSIZE = COURSELIST.size();
	String COURSEID = "";
	String COURSENAME = "";

	if(COURSELISTSIZE == 1){
		CourseListDto = (CurriCourseListDTO)COURSELIST.get(0);
		COURSEID = CourseListDto.getCourseId();
		COURSENAME = CourseListDto.getCourseName();
	}

	String 	courseId		= 	StringUtil.nvl(model.get("courseId"));
	String 	quizConfigYn	= 	StringUtil.nvl(model.get("quizConfigYn"));
	String	pGubun			=	StringUtil.nvl(model.get("pGubun"));
	int		forumId	=	StringUtil.nvl(model.get("forumId"), 0);

	CurriTopDTO	curriTopDto	=	(CurriTopDTO)model.get("CurriTopDTO");	
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/PublishCurriSubWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CourseWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/publish_curri_sub/lecContents.js"></script>

										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<form name="f" method="post" action="<%=CONTEXTPATH%>/CurriContents.cmd?cmd=lecContentsList" >
<input type="hidden" name="pCurriCode" value="<%=CURRICODE%>">
<input type="hidden" name="pCurriYear" value="<%=CURRIYEAR%>">
<input type="hidden" name="pCurriTerm" value="<%=CURRITERM%>">
<input type="hidden" name="classUserType" value="<%=classUserType%>">
<input type="hidden" name="courseId" value="<%=courseId%>">
<input type="hidden" name="quizConfigYn" value="<%=quizConfigYn%>">
<input type="hidden" name="pGubun" value="<%=pGubun%>">
<%
	if(pGubun.equals("2")) {
%>
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td class="s_btn01">
																<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" align="absmiddle">&nbsp;<b><%=COURSENAME%></b>
															</td>
															<td class="s_btn01" align="right">
<script language=javascript>Button5("과정리스트", "goCurriList()", "");</script>
<% if(forumId > 0) { %>&nbsp;<script language=javascript>Button5("토론방", "goForum('<%=forumId%>')", "");</script><% } %>
															</td>
														</tr>
													</table>
												</td>
											</tr>
<%
	}
	if (COURSELISTSIZE > 1 ) {
%>
											<tr>
												<td colspan="11" class="c_bbs_top">
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
<%
	}
%>
											<tr>
												<td colspan="11">
 	<!-- SCORM API -->
 		<div id="scormApi" style="width:100%;display:no"></div>
 	<!-- SCORM API -->
												</td>
											</tr>
											<tr>
												<td colspan="11" height="30" class="s_tab04_cen">

													<%=curriTopDto.getCurriInfo()%>
												</td>
											</tr>											
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="40">번호</td>
												<td class="s_tablien"></td>
												<td width="337">강의제목</td>
												<td class="s_tablien"></td>
												<td width="60">교수</td>
												<td class="s_tablien"></td>
												<td width="80">등록일</td>
												<td class="s_tablien"></td>
												<td width="60">첨부</td>
												<td class="s_tablien"></td>
												<td width="90">다운로드</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11">
                    							<!-- 리스트 -->
                    						  		<div id="contentsList" style="width:100%;display:no"></div>
                    						  	<!-- 리스트 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
											<tr>
    											<td colspan="11" height="35" align="center" valign="bottom">* 강의구분은 온라인 강의와 오프라인 강의의 구분입니다 *</td>
    										</tr>
											<tr>
												<td colspan="11" height="15"></td>
											</tr>
</form>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>

<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>');
</script>
<%@include file="../common/footer.jsp" %>