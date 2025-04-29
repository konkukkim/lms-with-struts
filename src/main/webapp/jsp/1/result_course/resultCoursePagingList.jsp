<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@include file="../common/course_header.jsp" %>
<%
	String pCourseId = (String)model.get("pCourseId");
	
	String pScoreGubun = (String)model.get("pScoreGubun");
	String pEvalGubun = (String)model.get("pEvalGubun");

%>

<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/popupbox.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/contextmenu.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ResultCourseWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/result_course/resultCourseList.js"></script>

							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"평가관리")%></b></font></td>
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
<form name="f" method="post" onSubmit="return false">
<input type="hidden" name="pCurPage">
<input type="hidden" name="idLength">
<input type="hidden" name="pSelect">
<input type="hidden" name="checkYn" value="N">
<input type="hidden" name="pOrderColm" value="">
<%
	if (COURSELISTSIZE > 1) {
%>
											<tr>
												<td colspan="30" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td>과목명 :
<%
			CodeParam param = new CodeParam();
			param.setSystemcode(SYSTEMCODE); 			// 시스템코드 설정
			param.setType("select"); 					// select, check, radio 유형을 선택한다.
			param.setName("pCourseId"); 				// 객체의 이름을 정한다.
			//param.setFirst("-- 과목을 선택 하세요 --"); 		// 리스트 처음에 보여줄 문자를 입력한다.
			param.setEvent("changeCourseId(this)"); 		// onChange 이벤트가 발생했을 경우에 호출되어질 자바 스크립트 함수 정의
			param.setSelected(pCourseId); 				// 선택되어져야 할 값 선언
			out.print(CommonUtil.getCurriCourse(param, CURRICODE,CURRIYEAR,CURRITERM,PROFID));
%>
															</td>
															<td align=right height=30>
															</td>
														</tr>
													</table>
												</td>
											</tr>
<%
	}
	else
	{
%>
<input type="hidden" name="pCourseId" value="<%=pCourseId%>" >
<%  
    }


%>
											<tr class="s_tab01">
												<td colspan="30"></td>
											</tr>
											<tr>
												<td align="center" valign="top" style="padding:5 0 0 0" colspan="30">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td height="30" align="left" style="padding:0 0 0 10">
															    <div id="scoreRateDiv" style="width:100%;display:none"><!-- 설정된 비율 보여주기 --></div>
															</td>
															<td height="30" align="right" style="padding:0 2 2 0"><div id="buttDiv1"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_grade_element.gif" width="89" height="19" style="cursor:hand" onClick='javascript:ratio_win()'></div></td>
														</tr>
														<tr class="c_test_tablien01">
															<td height="1" colspan="2"> </td>
														</tr>
														<tr>
															<td width="80%" align="left" style="padding:10 0 5 10" class="c_text02">
															    <div id="cntScoreDiv" style="width:100%;display:none"><!-- 시험, 과제, 토론 건수 보여주기 --></div>
															</td>
															<td height="22" align="right" valign="middle"  style="padding:0 2 0 0"><div id="buttDiv2"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_grade_finish.gif" width="89" height="19" style="cursor:hand" onClick='javascript:CompleteScore()'></div></td>
														</tr>
														<tr class="c_test_tablien01">
															<td height="1" colspan="2"> </td>
														</tr>
														<tr>
															<td class="c_tabbox" align="left" valign="middle" colspan="2">- 최종적으로 저장한 점수가 결과로 남게 되어있습니다.<br>
															- 수강이 종료된 시점에서 한번은 꼭 저장을 해주셔야 합니다.<br>
															- 아래의 점수는 총점 100점을 기준으로 환산된 점수 입니다.<br>
															- 모든 학생의 성적 처리가 끝나면 반드시 우측상단의 <font color="red"> [성적처리완료]</font> 버튼을 클릭해 주세요.<br>
															<% if(("1").equals(pScoreGubun)){ %>
															- 성적 처리 완료를 하신 후에 <font color="red">등급과 평점</font>이 보여집니다.<br>
															<% } else { %>
															- 성적 처리 완료를 하신 후에 수료처리가 가능합니다.<br>
															- <font color="red">[수강/수료관리 > 수료관리]</font> 에서 수료처리를 하실 수 있습니다.<br>
															<% } %>
															- 총점은 <font color="red">소수점 두번째</font>에서 반올림 됩니다.<br>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="30"></td>
											</tr>
											<tr>
												<td colspan="30" height="20"></td>
											</tr>
											<tr>
												<td colspan="30" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td align=right width="100%" valign="bottom">
															<div id="buttDiv3">
                                                                <a href="javascript:result_all()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_grade01.gif"></a>
                                                                &nbsp;<a href="javascript:result_check()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_grade02.gif"></a>
                                                                &nbsp;<a href="javascript:scoreUpload()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_grade03.gif"></a>
                                                                &nbsp;<a href="javascript:excelDown()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_grade04.gif"></a>
                                                                &nbsp;<a href="javascript:goAllList()"><img id="changBut" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_grade05.gif"></a>
                                                            </div>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="30"></td>
											</tr>
											<%
											int pWidth = 0;
											
											if(("1").equals(pScoreGubun))
											     pWidth  = (int)460/8;
											else pWidth  = (int)460/6;
											%>
											<tr class="s_tab02">
												<td width="40">번호</td>
												<td class="s_tablien"></td>
												<td width="130"><Span id="user_name_id" onClick="doOrder('user_name','user_name_id')" Style="cursor:hand">성명(아이디)▲</Span></td>
												<td class="s_tablien"></td>
<!--
												<td width="<%=pWidth %>">시험</td>
												<td class="s_tablien"></td>
-->
												<td width="<%=pWidth %>">과제</td>
												<td class="s_tablien"></td>
												<td width="<%=pWidth %>">출석</td>
												<td class="s_tablien"></td>
												<td width="<%=pWidth %>">토론</td>
												<td class="s_tablien"></td>
												<td width="<%=pWidth %>">그룹공부</td>
												<td class="s_tablien"></td>
												<td width="<%=pWidth %>">기타</td>
												<td class="s_tablien"></td>
												<td width="<%=pWidth %>"><Span id="score_total_id" onClick="doOrder('score_total','score_total_id')" Style="cursor:hand">총점▲</Span></td>
												<td class="s_tablien"></td>
												<% if(("1").equals(pScoreGubun)){ %>
												<td width="<%=pWidth %>"><Span id="grade_id" onClick="doOrder('grade','grade_id')" Style="cursor:hand">등급▲</Span></td>
												<td class="s_tablien"></td>
												<td width="<%=pWidth %>"><Span id="get_credit_id" onClick="doOrder('get_credit','get_credit_id')" Style="cursor:hand">평점▲</Span></td>
												<td class="s_tablien"></td>
												<% } %>
												<td width="40"><a href="javascript:setAllToken(f.pStudentIds)">선택</a></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="30"></td>
											</tr>
											<tr>
											    <td colspan="30"><div id="listDiv" style="width:100%;display:none"></div></td>
											</tr>

											<tr class="s_tab05">
												<td colspan="30"></td>
											</tr>
											<tr>
												<td colspan="30" height="15"></td>
											</tr>
											<tr>
											<td colspan="30" align=center>
												<table valign=top height="25">
													<tr>
														<td>
															<div id="getPagging" style="width:100%;display:none"></div>
														</td>
													</tr>
												</table>
												<table id="searchDiv">
													<tr>
														<td align=middle height=30>
															<select name=pOP>
																<option value="u.user_name" >이름</option>
																<option value="s.user_id"   >아이디</option>
															</select>
															<input maxlength=30 size=22 name="pSEARCH" value="">
															<a href="javascript:goSearch();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle></a>
														</td>
													</tr>
												</table>
												
											</td>
										    </tr>
											<!-- // 페이지 리스트, 검색부분 -->
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

<Script Language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=CURRICODE%>','<%=CURRIYEAR%>','<%=CURRITERM%>','<%=COURSELISTSIZE %>','<%=pScoreGubun %>','<%=pWidth %>');
</Script>