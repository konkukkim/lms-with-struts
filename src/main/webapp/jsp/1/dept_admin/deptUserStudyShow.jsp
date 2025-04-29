<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.resultcourse.dto.ResultScoreDTO,java.text.DecimalFormat,com.edutrack.currisub.dto.CurriContentsDTO"%>
<%@ page import ="com.edutrack.currisub.dao.CurriSubCourseDAO,com.edutrack.currisub.dto.CurriCourseListDTO,com.edutrack.message.dao.MessageDAO"%>
<%@ page import ="com.edutrack.common.dto.CodeParam"%>
<%@include file="../common/popup_header.jsp" %>
<%
	String CURRICODE = StringUtil.nvl( (String)model.get("pCurriCode"));
	int CURRIYEAR = StringUtil.nvl( (String)model.get("pCurriYear") ,0);
	int CURRITERM = StringUtil.nvl( (String)model.get("pCurriTerm") ,0);


	//-- 과정에 속한 과목의 리스트를 가져온다.
	CurriSubCourseDAO curriSubCoruseDao = new CurriSubCourseDAO();
	ArrayList COURSELIST = new ArrayList();
	CurriCourseListDTO CourseListDto = null;
	COURSELIST = curriSubCoruseDao.getCurriCourseList(SYSTEMCODE,CURRICODE,CURRIYEAR,CURRITERM,"");

	//-- 과목 갯수는 COURSELIST.size()
	int COURSELISTSIZE = COURSELIST.size();
	String COURSEID = "";
	String COURSENAME = "";

	if(COURSELISTSIZE == 1){
		CourseListDto = (CurriCourseListDTO)COURSELIST.get(0);
		COURSEID = CourseListDto.getCourseId();
		COURSENAME = CourseListDto.getCourseName();
	}


	//---- 소수점 형태 선언
	DecimalFormat decimal = new DecimalFormat("#.##");

	String pCourseId = (String)model.get("pCourseId");
	String pCourseName = (String)model.get("pCourseName");

	//-- 과목 배점 배율 정보
	RowSet courseInfo = (RowSet)model.get("curriCourseInfo");
	courseInfo.next();
	pCourseName = courseInfo.getString("course_name");
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
<Script Language=javascript>
    function cancel_page() {
		top.window.close();
    }

	function change_code(courseId)
	{
		var selId = courseId.value;
		document.location = "<%=CONTEXTPATH%>/DeptAdmin.cmd?cmd=deptUserStudyShow&pUserId=<%=request.getParameter("pUserId") %>&pCurriCode=<%=CURRICODE %>&pCurriYear=<%=CURRIYEAR %>&pCurriTerm=<%=CURRITERM %>&pCourseId="+selId;
	}


</Script>
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">평가조회</font></td>
			<td class="pop_topright"></td>
		</tr>
		<tr>
			<td class="pop_midleft"></td>
			<td valign="top" class="pop_form">
				<!-- 내용 -->
				<table width="90%" height="100%" align="center">
					<tr height="97%">
						<td align="left" valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td align="center" valign="top">
										<table width="" border="0" cellspacing="0" cellpadding="0">
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="30%">과정명</td>
												<td class="s_tab_view02" width="70%"><font class="c_text02"><b><%=pCourseName %></b></font></td>
											</tr>
											<tr>
												<td height="1" colspan="2" class="c_test_tablien01"> </td>
											</tr>
											<tr>
												<td class="s_tab_view01">과정구분</td>
												<td class="s_tab_view02">정규과정</td>
											</tr>
											<tr>
												<td height="1" colspan="2" class="c_test_tablien01"> </td>
											</tr>
											<tr>
												<td class="s_tab_view01">수강기간</td>
												<td class="s_tab_view02"><%=DateTimeUtil.getDateType(1, StringUtil.nvl( String.valueOf(model.get("pServiceStart")) )) %>  ~  <%=DateTimeUtil.getDateType(1, StringUtil.nvl( String.valueOf(model.get("pServiceEnd")) )) %></td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view03" colspan="2">* 평가조회를 원하는 과정을 선택하시면 해당 과정의 성적을 조회하실 수 있습니다.</td>
											</tr>
<%
	if( COURSELISTSIZE > 1 ) {
%>
											<tr>
												<td colspan="2" height="60" align="center" valign="middle">
													<b>과 목 명</b>&nbsp;:&nbsp;
<%
	   CodeParam param = new CodeParam();
	   param.setSystemcode(SYSTEMCODE); 			// 시스템코드 설정
	   param.setType("select"); 					// select, check, radio 유형을 선택한다.
	   param.setName("pCourseId"); 					// 객체의 이름을 정한다.
	   param.setFirst("-- 과목을 선택 하세요 --"); 	// 리스트 처음에 보여줄 문자를 입력한다.
	   param.setEvent("change_code(this)"); 		// onChange 이벤트가 발생했을 경우에 호출되어질 자바 스크립트 함수 정의
	   param.setSelected(pCourseId); 				// 선택되어져야 할 값 선언
	   out.print(CommonUtil.getCurriCourse(param, CURRICODE,CURRIYEAR,CURRITERM,""));
%>
												</td>
											</tr>
<%	} else {	%>
											<tr>
												<td colspan="2" height="30" align="left" valign="middle"></td>
											</tr>
<%	}	%>
										</table>
									</td>
								</tr>
								<tr>
									<td align="center" valign="top">
										<!-- 본문 시작 -->
      									<table width="100%" border="0" cellspacing="0" cellpadding="0">
      										<tr>
      											<td colspan="11" class="s_btn01"><b><%= model.get("pStudentName") %>(<%=request.getParameter("pUserId") %>)님</b> 의 성적 현황</td>
      										</tr>
      										<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">성적요소</td>
												<td class="s_tablien"></td>
												<td width="130">구분</td>
												<td class="s_tablien"></td>
												<td width="140">원점수<br>취득/배점(30)</td>
												<td class="s_tablien"></td>
												<td width="140">환산점수<br>취득/배점(30)</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
											<!-- 시험시작 -->
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen" rowspan="<%=(examScoreList.size()*2+1)%>">시험 (<%=examBase%>%)</td>
												<td rowspan="<%=(examScoreList.size()*2+1)%>" bgcolor="#D5CCC3"></td>
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

		if(i>0) out.println("<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">");
		out.println("<td class=\"s_tab04_cen\">"+strTitle+"</td>");
		out.println("<td></td>");
		out.println("<td class=\"s_tab04_cen\">");
		out.println("	<TABLE border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">");
		out.println("		<TR>");
		out.println("			<TD class=\"s_tab04\" width=\"49%\" align=\"right\">"+scoreDto.getResultScore()+"</TD>");
		out.println("			<TD class=\"s_tab04_cen\" width=\"1%\">/</TD>");
		out.println("			<TD class=\"s_tab04\" width=\"50%\" align=\"left\">"+scoreDto.getBaseScore()+"</TD>");
		out.println("		</TR>");
		out.println("	</TABLE>");
		out.println("</td>");
		out.println("<td></td>");
		out.println("<td class=\"s_tab04_cen\">");
		out.println("	<TABLE border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">");
		out.println("		<TR>");
		out.println("			<TD class=\"s_tab04\" width=\"49%\" align=\"right\">"+decimal.format(calExamResultScore)+"</TD>");
		out.println("			<TD class=\"s_tab04_cen\" width=\"1%\">/</TD>");
		out.println("			<TD class=\"s_tab04\" width=\"50%\" align=\"left\">"+decimal.format(calExamBaseScore)+"</TD>");
		out.println("		</TR>");
		out.println("	</TABLE>");
		out.println("</td></tr>");
		out.println("<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>");

	}
	if(examCnt > 0)
		out.println("<tr class='s_tab02'>");
%>
												<td class="s_tab04_cen">소계</td>
												<td></td>
												<td class="s_tab04_cen">
													<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
														<TR>
															<TD class="s_tab04" width="49%" align="right"><b><%=ExamResultScore%></b></TD>
															<TD class="s_tab04_cen" width="1%"><b>/</b></TD>
															<TD class="s_tab04" width="50%" align="left"><b><%=ExamBaseScore%></b></TD>
														</TR>
													</TABLE>
												</td>
												<td></td>
												<td class="s_tab04_cen">
													<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
														<TR>
															<TD class="s_tab04" width="49%" align="right"><input type='text' name='pScoreExam' value='<%=pScoreExam%>' style='align:right;' size='5' maxlength='5' onKeyup='javascript:score_change(this);'></TD>
															<TD class="s_tab04_cen" width="1%"><b>/</b></TD>
															<TD class="s_tab04" width="50%" align="left"><b><%=examBase%></b></TD>
														</TR>
													</TABLE>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>


											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen" rowspan="<%=(reportScoreList.size()*2+1)%>">과제 (<%=reportBase%>%)</td>
												<td rowspan="<%=(reportScoreList.size()*2+1)%>" bgcolor="D5CCC3"></td>
<%	double calReportBaseScore = 0;
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

		if (i > 0)
			out.println("<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">");

		out.println("<td class=\"s_tab04_cen\">"+strTitle+"</td>");
		out.println("<td></td>");
		out.println("<td class=\"s_tab04_cen\">");
		out.println("	<TABLE border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">");
		out.println("		<TR>");
		out.println("			<TD class=\"s_tab04\" width=\"49%\" align=\"right\">"+scoreDto.getResultScore()+"</TD>");
		out.println("			<TD class=\"s_tab04_cen\" width=\"1%\">/</TD>");
		out.println("			<TD class=\"s_tab04\" width=\"50%\" align=\"left\">"+scoreDto.getBaseScore()+"</TD>");
		out.println("		</TR>");
		out.println("	</TABLE>");
		out.println("</td><td></td>");
		out.println("<td class=\"s_tab04_cen\">");
		out.println("	<TABLE border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">");
		out.println("		<TR>");
		out.println("			<TD class=\"s_tab04\" width=\"49%\" align=\"right\">"+decimal.format(calReportResultScore)+"</TD>");
		out.println("			<TD class=\"s_tab04_cen\" width=\"1%\">/</TD>");
		out.println("			<TD class=\"s_tab04\" width=\"50%\" align=\"left\">"+decimal.format(calReportBaseScore)+"</TD>");
		out.println("		</TR>");
		out.println("	</TABLE>");
		out.println("</td></tr><tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>");
	}

	if (reportCnt > 0)
		out.println("<tr class='s_tab02'>");	%>
												<td class="s_tab04_cen">소계</td>
												<td></td>
												<td class="s_tab04_cen">
													<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
														<TR>
															<TD class="s_tab04" width="49%" align="right">0</TD>
															<TD class="s_tab04_cen" width="1%">/</TD>
															<TD class="s_tab04" width="50%" align="left">0</TD>
														</TR>
													</TABLE>
												</td>
												<td></td>
												<td class="s_tab04_cen">
													<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
														<TR>
															<TD class="s_tab04" width="49%" align="right"><input type='text' name='pScoreReport' value='<%=pScoreReport%>' style='align:right;' size='5' maxlength='5' onKeyup='javascript:score_change(this);'></TD>
															<TD class="s_tab04_cen" width="1%"><b>/</b></TD>
															<TD class="s_tab04" width="50%" align="left"><b><%=reportBase%></b></TD>
														</TR>
													</TABLE>
												</td>
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
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td rowspan="<%=iRowSpan %>" class="s_tab04_cen">출석 (<%=attendBase%>%)</td>
												<td rowspan="<%=iRowSpan %>" bgcolor="D5CCC3"></td>
												<td class="s_tab04_cen">목차학습수</td>
												<td></td>
												<td class="s_tab04_cen">
													<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
														<TR>
															<TD class="s_tab04" width="49%" align="right"><%=attend.getContentsCnt()%> 개</TD>
															<TD class="s_tab04_cen" width="1%">/</TD>
															<TD class="s_tab04" width="50%" align="left"><%=attend.getAllContentsCnt()%> 개</TD>
														</TR>
													</TABLE>
												</td>
												<td></td>
												<td class="s_tab04_cen"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen">목차 학습 시간</td>
												<td></td>
												<td class="s_tab04_cen"><%=attend.getTotalTime()%> 분</td>
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
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><b>오프라인(<%=all_cnt %>)<b></td>
												<td></td>
												<td class="s_tab04_cen" colspan="3">
													<table width="90%" border="0">
														<tr>
															<td class="s_tab04_cen" width="30%"><b>출석</b> : <b><%=t_cnt %></b> 회</td>
															<td class="s_tab04_cen" width="30%"><b>결석</b> : <b><%=f_cnt %></b> 회</td>
															<td class="s_tab04_cen" width="30%"><b>지각</b> : <b><%=m_cnt %></b> 회</td>
														</tr>
													</table>
												</td>
												<!--
												<td></td>
												<td class="s_tab04_cen"></td>
												-->
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	} // end if
	//오프라인 끝
%>
											<tr class='s_tab02'>
												<td class="s_tab04_cen">소계</td>
												<td></td>
												<td class="s_tab04_cen"></td>
												<td></td>
												<td class="s_tab04_cen">
													<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
														<TR>
															<TD class="s_tab04" width="49%" align="right"><input type='text' name='pScoreAttend' value='<%=pScoreAttend%>' style='align:right;' size='5' maxlength='5' onKeyup='javascript:score_change(this);'></TD>
															<TD class="s_tab04_cen" width="1%"><b>/</b></TD>
															<TD class="s_tab04" width="50%" align="left"><b><%=attendBase%></b></TD>
														</TR>
													</TABLE>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>




											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen" rowspan="<%=(forumScoreList.size()*2+1)%>">토론 (<%=forumBase%>%)</td>
												<td rowspan="<%=(forumScoreList.size()*2+1)%>" bgcolor="#D5CCC3"></td>
<%	double calForumBaseScore = 0;
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

		if(i>0)
			out.println("<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">");

		out.println("<td class=\"s_tab04_cen\">"+strTitle+"</td>");
		out.println("<td></td>");
		out.println("<td class=\"s_tab04_cen\">");
		out.println("	<TABLE border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">");
		out.println("		<TR>");
		out.println("			<TD class=\"s_tab04\" width=\"49%\" align=\"right\">"+scoreDto.getResultScore()+"</TD>");
		out.println("			<TD class=\"s_tab04_cen\" width=\"1%\">/</TD>");
		out.println("			<TD class=\"s_tab04\" width=\"50%\" align=\"left\">"+scoreDto.getBaseScore()+"</TD>");
		out.println("		</TR>");
		out.println("	</TABLE>");
		out.println("</td><td></td>");
		out.println("<td class=\"s_tab04_cen\">");
		out.println("	<TABLE border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">");
		out.println("		<TR>");
		out.println("			<TD class=\"s_tab04\" width=\"49%\" align=\"right\">"+decimal.format(calForumResultScore)+"</TD>");
		out.println("			<TD class=\"s_tab04_cen\" width=\"1%\">/</TD>");
		out.println("			<TD class=\"s_tab04\" width=\"50%\" align=\"left\">"+decimal.format(calForumBaseScore)+"</TD>");
		out.println("		</TR>");
		out.println("	</TABLE>");
		out.println("</td></tr><tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>");
	}

	if (forumCnt > 0)
		out.println("<tr class='s_tab02'>");	%>
												<td class="s_tab04_cen">소계</td>
												<td></td>
												<td class="s_tab04_cen">
													<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
														<TR>
															<TD class="s_tab04" width="49%" align="right"><b><%=ForumResultScore%></b></TD>
															<TD class="s_tab04_cen" width="1%"><b>/</b></TD>
															<TD class="s_tab04" width="50%" align="left"><b><%=ForumBaseScore%></b></TD>
														</TR>
													</TABLE>
												</td>
												<td></td>
												<td class="s_tab04_cen">
													<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
														<TR>
															<TD class="s_tab04" width="49%" align="right"><input type='text' name='pScoreForum' value="<%=pScoreForum%>" style='align:right;' size='5' maxlength='5' onKeyup='javascript:score_change(this);'></TD>
															<TD class="s_tab04_cen" width="1%"><b>/</b></TD>
															<TD class="s_tab04" width="50%" align="left"><b><%=forumBase%></b></TD>
														</TR>
													</TABLE>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>



											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen">그룹공부 (0%)</td>
												<td bgcolor="D5CCC3"></td>
												<td class="s_tab04_cen">소계</td>
												<td></td>
												<td class="s_tab04_cen">
													<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
														<TR>
															<TD class="s_tab04" width="49%" align="right">0</TD>
															<TD class="s_tab04_cen" width="1%">/</TD>
															<TD class="s_tab04" width="50%" align="left">0</TD>
														</TR>
													</TABLE>
												</td>
												<td></td>
												<td class="s_tab04_cen">
													<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
														<TR>
															<TD class="s_tab04" width="49%" align="right"><b><input type='text' name='pScoreEtc1' value='<%=pScoreEtc1%>' style='align:right;' size='5' maxlength='5' onKeyup='javascript:score_change(this);'></b></TD>
															<TD class="s_tab04_cen" width="1%"><b>/</b></TD>
															<TD class="s_tab04" width="50%" align="left"><b><%=etc1Base%></b></TD>
														</TR>
													</TABLE>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>



											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen">기타 (0%)</td>
												<td bgcolor="D5CCC3"></td>
												<td class="s_tab04_cen">소계</td>
												<td></td>
												<td class="s_tab04_cen">
													<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
														<TR>
															<TD class="s_tab04" width="49%" align="right">0</TD>
															<TD class="s_tab04_cen" width="1%">/</TD>
															<TD class="s_tab04" width="50%" align="left">0</TD>
														</TR>
													</TABLE>
												</td>
												<td></td>
												<td class="s_tab04_cen">
													<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
														<TR>
															<TD class="s_tab04" width="49%" align="right"><b><input type="text" name="pScoreEtc2" value="<%=pScoreEtc2%>" style="align:right;" size="5" maxlength="5" onKeyup='javascript:score_change(this);'></b></TD>
															<TD class="s_tab04_cen" width="1%"><b>/</b></TD>
															<TD class="s_tab04" width="50%" align="left"><b><%=etc2Base%></b></TD>
														</TR>
													</TABLE>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>



											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen">총점</td>
												<td bgcolor="D5CCC3"></td>
												<td class="s_tab04_cen"></td>
												<td></td>
												<td class="s_tab04_cen"></td>
												<td></td>
												<td class="s_tab04_cen">
													<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
														<TR>
															<TD class="s_tab04" width="49%" align="right"><input type="text" name="pScoreTotal" value="<%=pScoreTotal%>" style="align:right;" size="5" maxlength="5" readonly></TD>
															<TD class="s_tab04_cen" width="1%"><b>/</b></TD>
															<TD class="s_tab04" width="50%" align="left"><b>100</b></TD>
														</TR>
													</TABLE>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
      									</table>
									</td>
								</tr>

								<tr>
									<td colspan="2" align="left" valign="top" class="pop_btn">※ 강의 종료 후 채점 완료 전까지는 가채점 상태이므로 성적 관리에 참고 자료로만 보시기 바랍니다.</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<!-- // 내용 -->
			</td>
			<td class="pop_midright"></td>
		</tr>
		<tr height="6">
			<td class="pop_bottomleft"></td>
			<td class="pop_bottombg"></td>
			<td class="pop_bottomright"></td>
		</tr>
		<tr>
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif" onClick="self.close()" style="cursor:hand"></td>
		</tr>
	</table>


<%@include file="../common/popup_footer.jsp" %>