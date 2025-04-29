<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.resultcourse.dto.ResultScoreDTO,java.text.DecimalFormat,com.edutrack.currisub.dto.CurriContentsDTO"%>
<%@include file="../common/course_header.jsp" %>
<%
	//---- 소수점 형태 선언
	DecimalFormat decimal = new DecimalFormat("#.##");

	String pCourseId = (String)model.get("pCourseId");
	String pCourseName = (String)model.get("pCourseName");

	//-- 과목 배점 배율 정보
	RowSet courseInfo = (RowSet)model.get("curriCourseInfo");
	courseInfo.next();
	int examBase = courseInfo.getInt("exam_base");
	int reportBase = courseInfo.getInt("report_base");
	int attendBase = courseInfo.getInt("attend_base");
	int forumBase = courseInfo.getInt("forum_base");
	int etc1Base = courseInfo.getInt("etc1_base");
	int etc2Base = courseInfo.getInt("etc2_base");
	courseInfo.close();

	//-- 성적정보
	double pScoreExam = Double.parseDouble((String)model.get("pScoreExam"));
	double pScoreReport = Double.parseDouble((String)model.get("pScoreReport"));
	double pScoreAttend = Double.parseDouble((String)model.get("pScoreAttend"));
	double pScoreForum = Double.parseDouble((String)model.get("pScoreForum"));
	double pScoreEtc1 = Double.parseDouble((String)model.get("pScoreEtc1"));
	double pScoreEtc2 = Double.parseDouble((String)model.get("pScoreEtc2"));

	ResultScoreDTO scoreDto = null;
	//-- 시험 점수 정보 가져오기
	int ExamBaseScore = 0;
	int ExamResultScore = 0;
	ArrayList examScoreList = (ArrayList)model.get("examScoreList");
	for(int i=0;i<examScoreList.size();i++){
		scoreDto = (ResultScoreDTO)examScoreList.get(i);
		ExamBaseScore += scoreDto.getBaseScore();
		ExamResultScore += scoreDto.getResultScore();
	}
	if(pScoreExam <= 0){
		if(ExamBaseScore > 0)
			pScoreExam = ExamResultScore*examBase/Double.parseDouble(String.valueOf(ExamBaseScore));
		else
			pScoreExam = 0;
	}
	//-- 시험 끝
	//-- 과제 점수 정보 가져오기
	int ReportBaseScore = 0;
	int ReportResultScore = 0;
	ArrayList reportScoreList = (ArrayList)model.get("reportScoreList");
	for(int i=0;i<reportScoreList.size();i++){
		scoreDto = (ResultScoreDTO)reportScoreList.get(i);
		ReportBaseScore += scoreDto.getBaseScore();
		ReportResultScore += scoreDto.getResultScore();
	}
	if(pScoreReport <= 0){
		if(ReportBaseScore > 0 )
			pScoreReport = ReportResultScore*reportBase/Double.parseDouble(String.valueOf(ReportBaseScore));
		else
			pScoreReport = 0;
	}
	//-- 과제 끝
	//--  포럼 점수 정보 가져오기
	int ForumBaseScore = 0;
	int ForumResultScore = 0;
	ArrayList forumScoreList = (ArrayList)model.get("forumScoreList");
	for(int i=0;i<forumScoreList.size();i++){
		scoreDto = (ResultScoreDTO)forumScoreList.get(i);
		ForumBaseScore += scoreDto.getBaseScore();
		ForumResultScore += scoreDto.getResultScore();
	}
	if(pScoreForum <=0){
		if(ForumBaseScore > 0)
			pScoreForum = ForumResultScore*forumBase/Double.parseDouble(String.valueOf(ForumBaseScore));
		else
			pScoreForum = 0;
	}
	//-- 포럼 끝

	double pScoreTotal = pScoreExam+pScoreReport+pScoreAttend+pScoreForum+pScoreEtc1+pScoreEtc2;
	String strTitle = "";
	//출석정보
	CurriContentsDTO attend = (CurriContentsDTO)model.get("attendView");
%>
<script language="javascript">
	function change_code(courseId)
	{
		var selId = courseId.value;
		document.location = "<%=CONTEXTPATH%>/ResultCourse.cmd?cmd=resultCourseStShow&pCourseId="+selId;
	}
</script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"성적조회")%></b></font></td>
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
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet02.gif" align="absmiddle"><b> <%=USERNAME%> (<%=USERID%>) </b>님의 성적 현황</td>
															<td align=right width="50%" height=30>
<%
	if( COURSELISTSIZE > 1 ) {
%>
<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet02.gif" align="absmiddle"> <b>과 목 명</b>&nbsp;
<%
	  CodeParam param = new CodeParam();
	  param.setSystemcode(SYSTEMCODE); 		// 시스템코드 설정
	  param.setType("select"); 				// select, check, radio 유형을 선택한다.
	  param.setName("pCourseId"); 				// 객체의 이름을 정한다.
	  param.setFirst("-- 과목을 선택 하세요 --"); 	// 리스트 처음에 보여줄 문자를 입력한다.
	  param.setEvent("change_code(this)"); 	// onChange 이벤트가 발생했을 경우에 호출되어질 자바 스크립트 함수 정의
	  param.setSelected(pCourseId); 				// 선택되어져야 할 값 선언
	  out.print(CommonUtil.getCurriCourse(param, CURRICODE,CURRIYEAR,CURRITERM,""));
%>
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
											<tr class="s_tab02" height="50">
												<td width="180">성적요소</td>
												<td class="s_tablien"></td>
												<td width="150">구분</td>
												<td class="s_tablien"></td>
												<td width="170">원점수<br>취득/배점</td>
												<td class="s_tablien"></td>
												<td width="170">환산점수<br>취득/배점(30)</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>

											<!-- 시험시작 -->
											<tr>
												<td class="s_tab04_cen" rowspan="<%=(examScoreList.size()*2+1)%>"><b>시험 (<%=examBase%>%)</b></td>
												<td rowspan="<%=(examScoreList.size()*2+1)%>" bgcolor="D5CCC3"></td>
<%
	double calExamBaseScore = 0;
	double calExamResultScore = 0;
	int examCnt = 0;
	for(int i=0;i<examScoreList.size();i++){
		examCnt++;
		scoreDto = (ResultScoreDTO)examScoreList.get(i);
		//-- 배점 환산 점수 구하기 (배점 / 배점합 * 점수비율)
		calExamBaseScore = (scoreDto.getBaseScore()*examBase)/Double.parseDouble(String.valueOf(ExamBaseScore));
		//-- 취득 환산점수 구하기 (취득점수 / 배점점수 * 배점환산점수)
		calExamResultScore = (scoreDto.getResultScore()*calExamBaseScore)/scoreDto.getBaseScore();
		strTitle = "<a href='#' title='"+scoreDto.getSubject()+"\n"+DateTimeUtil.getDateType(1,scoreDto.getStartDate())+" ~ "+DateTimeUtil.getDateType(1,scoreDto.getEndDate())+"'>"+examCnt+"회</a>";
		if(i>0) out.println("<tr>");
		out.println("<td class=\"s_tab04_cen\">"+strTitle+"</td>");
		out.println("<td></td>");
		out.println("<td class=\"s_tab04_cen\">"+scoreDto.getResultScore()+" / "+scoreDto.getBaseScore()+"</td>");
		out.println("<td></td>");
		out.println("<td class=\"s_tab04_cen\">"+decimal.format(calExamResultScore)+" / "+decimal.format(calExamBaseScore)+"</td></tr>");
		out.println("<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>");

	}
	if(examCnt > 0)
		out.println("<tr class='s_tab02'>");
%>
												<td class="s_tab04_cen">소계</td>
												<td></td>
												<td class="s_tab04_cen"><%=ExamResultScore%> / <%=ExamBaseScore%></td>
												<td></td>
												<td class="s_tab04_cen"><%=pScoreExam%> / <%=examBase%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>


											<!-- 과제 시작 -->
											<tr>
												<td class="s_tab04_cen" rowspan="<%=(reportScoreList.size()*2+1)%>"><b>과제 (<%=reportBase%>%)</b></td>
												<td rowspan="<%=(reportScoreList.size()*2+1)%>" bgcolor="D5CCC3"></td>
<%
	double calReportBaseScore = 0;
	double calReportResultScore = 0;
	int reportCnt = 0;

	for(int i=0;i<reportScoreList.size();i++){
		reportCnt++;
		scoreDto = (ResultScoreDTO)reportScoreList.get(i);
		//-- 배점 환산 점수 구하기 (배점 / 배점합 * 점수비율)
		calReportBaseScore = (scoreDto.getBaseScore()*reportBase)/Double.parseDouble(String.valueOf(ReportBaseScore));
		//-- 취득 환산점수 구하기 (취득점수 / 배점점수 * 배점환산점수)
		calReportResultScore = (scoreDto.getResultScore()*calReportBaseScore)/scoreDto.getBaseScore();
		strTitle = "<a href='#' title='"+scoreDto.getSubject()+"\n"+DateTimeUtil.getDateType(1,scoreDto.getStartDate())+" ~ "+DateTimeUtil.getDateType(1,scoreDto.getEndDate())+"'>"+reportCnt+"회</a>";
		if(i>0) out.println("<tr>");
		out.println("<td class=\"s_tab04_cen\">"+strTitle+"</td>");
		out.println("<td></td>");
		out.println("<td class=\"s_tab04_cen\">"+scoreDto.getResultScore()+" / "+scoreDto.getBaseScore()+"</td>");
		out.println("<td></td>");
		out.println("<td class=\"s_tab04_cen\">"+decimal.format(calReportResultScore)+" / "+decimal.format(calReportBaseScore)+"</td></tr>");
		out.println("<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>");

	}
	if(reportCnt > 0)
		out.println("<tr class='s_tab02'>");
%>
												<td class="s_tab04_cen">소계</td>
												<td></td>
												<td class="s_tab04_cen"><%=ReportResultScore%> / <%=ReportBaseScore%></td>
												<td></td>
												<td class="s_tab04_cen"><%=pScoreReport%> / <%=reportBase%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	RowSet offAtList = (RowSet)model.get("offAtList");
	int all_cnt = 0;
	int t_cnt   = 0;   // 출석
	int f_cnt   = 0;   // 결석
	int m_cnt   = 0;   // 지각
	int iRowSpan = 5;
	if(offAtList!=null){
		if(offAtList.next()){

			all_cnt = offAtList.getInt("all_cnt");
			t_cnt   = offAtList.getInt("t_cnt");    // 출석
			f_cnt   = offAtList.getInt("f_cnt");    // 결석
			m_cnt   = offAtList.getInt("m_cnt");    // 지각
			iRowSpan += 2;

			offAtList.close();

		} // end if
	}
%>

											<!-- 출석 시작 -->
											<tr>
												<td rowspan="<%if(all_cnt>0){%>7<%}else{%>5<%}%>" class="s_tab04_cen"><b>출석 (<%=attendBase%>%)</b></td>
												<td rowspan="<%if(all_cnt>0){%>7<%}else{%>5<%}%>" bgcolor="D5CCC3"></td>
												<td class="s_tab04_cen">목차 학습 수</td>
												<td></td>
												<td class="s_tab04_cen"><%=attend.getContentsCnt()%> 개 / <%=attend.getAllContentsCnt()%> 개</td>
												<td></td>
												<td class="s_tab04_cen"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td class="s_tab04_cen">목차 학습 시간</td>
												<td></td>
												<td class="s_tab04_cen"><%=attend.getTotalTime()%>분</td>
												<td></td>
												<td class="s_tab04_cen"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	// 오프라인이 있을경우
	if(all_cnt>0){
%>
											<tr>
												<td colspan="11">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td width="25%" class="s_tab04_cen"><b>오프라인(총 <%=all_cnt %> 회)</b></td>
															<td width="25%" class="s_tab04_cen"><b>출석 : <%=t_cnt%></b>회</td>
															<td width="25%" class="s_tab04_cen"><b>결석 : <%=f_cnt%></b>회</td>
															<td width="25%" class="s_tab04_cen"><b>지각 : <%=m_cnt%></b>회</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%	}	%>
											<tr class='s_tab02'>
												<td class="s_tab04_cen">소계</td>
												<td></td>
												<td class="s_tab04_cen"></td>
												<td></td>
												<td class="s_tab04_cen"><%=pScoreAttend%> / <%=attendBase%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>


											<!-- 토론 시작 -->
											<tr>
												<td class="s_tab04_cen" rowspan="<%=(forumScoreList.size()*2+1)%>"><b>토론 (<%=forumBase%>%)</b></td>
												<td rowspan="<%=(forumScoreList.size()*2+1)%>" bgcolor="D5CCC3"></td>
<%
	double calForumBaseScore = 0;
	double calForumResultScore = 0;
	int forumCnt = 0;

	for(int i=0;i<forumScoreList.size();i++){
		forumCnt++;
		scoreDto = (ResultScoreDTO)forumScoreList.get(i);
		//-- 배점 환산 점수 구하기 (배점 / 배점합 * 점수비율)
		calForumBaseScore =  scoreDto.getBaseScore()*forumBase/ForumBaseScore;
		//-- 취득 환산점수 구하기 (취득점수 / 배점점수 * 배점환산점수)
		calForumResultScore = scoreDto.getResultScore()*calForumBaseScore/scoreDto.getBaseScore();
		strTitle = "<a href='#' title='"+scoreDto.getSubject()+"\n"+DateTimeUtil.getDateType(1,scoreDto.getStartDate())+" ~ "+DateTimeUtil.getDateType(1,scoreDto.getEndDate())+"'>"+forumCnt+"회</a>";
		if(i>0) out.println("<tr>");
		out.println("<td class=\"s_tab04_cen\">"+strTitle+"</td>");
		out.println("<td></td>");
		out.println("<td class=\"s_tab04_cen\">"+scoreDto.getResultScore()+" / "+scoreDto.getBaseScore()+"</td>");
		out.println("<td></td>");
		out.println("<td class=\"s_tab04_cen\">"+decimal.format(calForumResultScore)+" / "+decimal.format(calForumBaseScore)+"</td></tr>");
		out.println("<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>");

	}
	if(forumCnt > 0)
		out.println("<tr class=\"s_tab02\">");
%>

												<td class="s_tab04_cen"><a href="">소계</a></td>
												<td></td>
												<td class="s_tab04_cen"><%=ForumResultScore%> / <%=ForumBaseScore%></td>
												<td></td>
												<td class="s_tab04_cen"><%=pScoreForum%> / <%=forumBase%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>


											<!-- 그룹공부  시작 -->
											<tr>
												<td class="s_tab04_cen">그룹공부 (<%=etc1Base%>%)</td>
												<td bgcolor="D5CCC3"></td>
												<td class="s_tab04_cen">소계</td>
												<td></td>
												<td class="s_tab04_cen"></td>
												<td></td>
												<td class="s_tab04_cen"><%=pScoreEtc1%> / <%=etc1Base%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>



											<!-- 기타2  시작 -->
											<tr>
												<td class="s_tab04_cen">기타 (<%=etc2Base%>%)</td>
												<td bgcolor="D5CCC3"></td>
												<td class="s_tab04_cen">소계</td>
												<td></td>
												<td class="s_tab04_cen"></td>
												<td></td>
												<td class="s_tab04_cen"><%=pScoreEtc2%> / <%=etc2Base%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>


											<!-- 총점 시작 -->
											<tr>
												<td class="s_tab04_cen">총점</td>
												<td bgcolor="D5CCC3"></td>
												<td class="s_tab04_cen"></td>
												<td></td>
												<td class="s_tab04_cen"></td>
												<td></td>
<%
	String	totalScoreStr	=	String.valueOf(pScoreTotal);
	int		markerNo			=	0;
	markerNo	=	totalScoreStr.indexOf(".");

	if(totalScoreStr.length() > 3 && markerNo > 0) {
		totalScoreStr	=	totalScoreStr.substring(0, markerNo+3);
	}
%>


												<td class="s_tab04_cen"><%=totalScoreStr%> / 100</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11" class="s_tab04_cen" colspan="50"> ※ 강의 종료 후 채점 완료 전까지는 가채점 상태이므로 성적 관리에 참고 자료로만 보시기 바랍니다. </td>
											</tr>

										</table>
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/course_footer.jsp" %>