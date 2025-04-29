<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@ page import ="com.edutrack.common.dto.CodeParam, com.edutrack.currisub.dto.CurriContentsDTO"%>
<%@ page import ="com.edutrack.common.dao.CommonDAO, com.edutrack.common.dto.CodeDTO"%>
<%@include file="../common/course_header.jsp" %>
<%
   String   pCourseId   =   (String)model.get("pCourseId");
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/contextmenu.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/OfflineAttendWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/offline_attend/offlineAttendList.js"></script>


							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"출결관리(오프라인)")%></b></font></td>
									<!-- // classroom title -->
									<!-- history -->
									<td class="c_stit_history" valign="bottom" align="right" width="327">

									</td>
									<!-- // history -->
								</tr>
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
								    <!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
										<tr>
											<td colspan="30" class="s_btn01">
<%
if ( COURSELISTSIZE > 1 ) {
%>
과목 :

<%		CodeParam param = new CodeParam();
		param.setSystemcode(SYSTEMCODE); 		// 시스템코드 설정
		param.setType("select"); 				// select, check, radio 유형을 선택한다.
		param.setName("pCourseId"); 				// 객체의 이름을 정한다.
		//param.setFirst("-- 과목을 선택 하세요 --"); 	// 리스트 처음에 보여줄 문자를 입력한다.
		param.setEvent("chgCourseId()"); 	// onChange 이벤트가 발생했을 경우에 호출되어질 자바 스크립트 함수 정의
		param.setSelected(pCourseId); 				// 선택되어져야 할 값 선언

		out.print(CommonUtil.getCurriCourse(param, CURRICODE,CURRIYEAR,CURRITERM,PROFID));

	} else {	%>
														<input type="hidden" name="pCourseId"   value="<%=pCourseId%>">
<%	}  %>
												</td>
											</tr>
                    
                                            <!-- main 시작   action="javascript:goSearch();"  -->
                                            <form name="f" method="post">
                                            <input type="hidden" name="pageNo" value="">
											</table>
											
										    <!-- // 게시판 리스트 시작 -->
											<div id="attendList" Style="display:none"></div>
										    <!-- // 게시판 리스트  끝 -->
										    
										    <table width="670" align="center" border=0>
											<tr>
												<td colspan="30" height="30" align="right" >
												    <span ID=prevBut Style="display:"><Script Language=Javascript>Button3("이전", "goNext('P')", "");</Script></span>
												    <span ID=nextBut Style="display:"><Script Language=Javascript>Button3("다음", "goNext('N')", "");</Script></span>
												</td>
											</tr>
 							                </form>
										    </table>											
									    
									</td>
								</tr>
							</table>
						</td>
						

						
<%@include file="../common/course_footer.jsp" %>
						
<script language="javascript">

	// 상단의 출석일자를 클릭시 보여지는 메뉴
	var cMenu0 = ContextMenu("left");
	addContextMenuItem(cMenu0, "모두출석으로", "", "replaceAttend('O','all')", "");
	addContextMenuItem(cMenu0, "모두결석으로", "", "replaceAttend('X','all')", "");
	addContextMenuItem(cMenu0, "저장", "", "saveAttend()", "");


	var cMenu1 = ContextMenu("left");
	addContextMenuItem(cMenu1, "출석", "", "replaceAttend('O','')", "");
	addContextMenuItem(cMenu1, "지각", "", "replaceAttend('△','')", "");
	addContextMenuItem(cMenu1, "결석", "", "replaceAttend('X','')", "");

    
    init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>');

</script>

