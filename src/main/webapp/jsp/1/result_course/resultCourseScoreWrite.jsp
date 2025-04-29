<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.resultcourse.dto.ResultScoreDTO,java.text.DecimalFormat,com.edutrack.currisub.dto.CurriContentsDTO"%>
<%@include file="../common/popup_header.jsp" %>
<%
	//---- 소수점 형태 선언
	DecimalFormat decimal = new DecimalFormat("#.##");

	String pCourseId = (String)model.get("pCourseId");
	String pStudentId = (String)model.get("pStudentId");

	int idLength = Integer.parseInt((String)model.get("idLength"));
	String[] aStudentIds = null;
	if(idLength > 0){
		aStudentIds = (String[])model.get("aStudentIds");
		idLength = aStudentIds.length;
	}

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
	String pStudentName = (String)model.get("pStudentName");
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

<style type='text/css'>
	input {text-align: right;}
</style>
<script language=javascript>
	function win_close()
	{
		//opener.document.location.reload();
		opener.autoReload();
		self.close();
	}

	function number_format( number , decimals ) {
		var number = number.toString();
		var arr = number.split(".");
		var str='', temp='', z=0,  int_len=0 , interval=0;
		var idx = number.indexOf(".");
		//정수와 소수부분 분리
		integer = arr[0];
		float = arr[1];
		//alert(idx);
		int_len = integer.length;
		for( z=0 ; z < int_len ; z++ ) {
			if(((int_len - z) % 3) == 0 && (int_len - z != int_len)) str = str + ",";
			str = str + integer.charAt(z);
		}

		//끊기를 원하는 자리수(decimals) 가 있고, 실제 소수부분이 있는 경우
		if ( decimals > 0 && float != "" && idx >= 0) {

			//끊기를 원하는 수가 소수부분보다 길면...
			if ( decimals > float.length ) {
				//길이의 차이를 구한다
				interval = decimals - float.length;

				//길이의 차이만큼 0 을 더해준다.
				for( z=0 ; z < interval ; z++ ) {
					float = float + '0';
				}
			}

			//끊기를 원하는 수가 소수부분보다 짧으면...
			else if ( decimals < float.length ) {
				//원하는 소수점개수보다 1자리 많게 끊어서 반올림하여 구한다. eval 은 문자를 숫자로 변환해주는 function
				float = Math.round ( float.substr ( 0, eval(decimals) + 1 ) / 10 );
			}

			str = str+"."+float;
		}

		return str;
	}

	function go_next()
	{
		var f = document.Result;
		f.action="<%=CONTEXTPATH%>/ResultCourse.cmd?cmd=resultCourseScoreRegist";
		f.submit();
	}
	function score_change(obj){
		if(obj.value != "" && isNaN(obj.value))
		{
			alert('숫자를 입력해 주세요');
			obj.value="";
			obj.focus();
		}else{
			//obj.value = number_format(obj.value,2);
			f=obj.form;
			//var TotalValue = parseFloat(number_format(f.pScoreExam.value,2)) + parseFloat(number_format(f.pScoreReport.value,2)) + parseFloat(number_format(f.pScoreAttend.value,2)) + parseFloat(number_format(f.pScoreForum.value,2)) + parseFloat(number_format(f.pScoreEtc1.value,2)) + parseFloat(number_format(f.pScoreEtc2.value,2)) ;
			var TotalValue = parseFloat(number_format(f.pScoreReport.value,2)) + parseFloat(number_format(f.pScoreAttend.value,2)) + parseFloat(number_format(f.pScoreForum.value,2)) + parseFloat(number_format(f.pScoreEtc1.value,2)) + parseFloat(number_format(f.pScoreEtc2.value,2)) ;

			f.pScoreTotal.value = number_format(TotalValue,2);
		}
	}
	function isEmpty( data )
	{
		for ( var i = 0 ; i < data.length ; i++ )
		{
			if ( data.substring( i, i+1 ) != ' ' )	return false;
		}
		return true;
	}
	function chkForm()
	{
		var f = document.Result;

		//if(isEmpty(f.pScoreExam.value)){
		//	alert('시험성적을 입력하세요.');
		//	f.pScoreExam.focus();
		//	return false;
		//}
		if(isEmpty(f.pScoreReport.value)){
			alert('과제성적을 입력하세요.');
			f.pScoreReport.focus();
			return false;
		}
		if(isEmpty(f.pScoreAttend.value)){
			alert('출석성적을 입력하세요.');
			f.pScoreAttend.focus();
			return false;
		}
		if(isEmpty(f.pScoreForum.value)){
			alert('토론성적을 입력하세요.');
			f.pScoreForum.focus();
			return false;
		}
		if(isEmpty(f.pScoreEtc1.value)){
			alert('그룹공부 성적을 입력하세요.');
			f.pScoreEtc1.focus();
			return false;
		}
		if(isEmpty(f.pScoreEtc2.value)){
			alert('기타 성적을 입력하세요.');
			f.pScoreEtc2.focus();
			return false;
		}
		if(f.pScoreTotal.value > 100){
			alert('점수 합계가 100을 초과 하였습니다. \n\n 확인 해 주세요');
			return false;
		}

		f.submit();

	}
	function go_auto_score(){
		var chk = confirm('시험,과제,출석,토론,점수에 대해 자동으로 계산하여 점수를 부여합니다. \n\n점수부여후 다시 개별 점수를 수정하실 수 있습니다. \n\n자동채점을 하시겠습니까?');
		if(chk){
			var f=document.Result;
			f.action="<%=CONTEXTPATH%>/ResultCourse.cmd?cmd=resultCourseScoreAutoRegist";
			f.submit();
		}
	}
</script>
	<table width="100%" height="100%" border="0">
<!--  onSubmit="return chkForm(this)" -->
<form name=Result method=post action="<%=CONTEXTPATH%>/ResultCourse.cmd?cmd=resultCourseScoreRegist">
<input type=hidden name=pCourseId value="<%=pCourseId%>">
<input type=hidden name=pStudentId value="<%=pStudentId%>">
<input type=hidden name=idLength value="<%=idLength%>">
<input type=hidden name=pSelect value="SEL">

<%	if (idLength > 0) {

		for (int i=0;i<aStudentIds.length;i++){%>
			<input type=hidden name="pStudentIds" value="<%=aStudentIds[i]%>">
<%      }
	}	%>
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">수강생점수입력</font></td>
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
									<td colspan="11" class="s_btn01">
										<table width="100%" align="center">
											<tr>
												<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet02.gif" align="absmiddle"> <%=pStudentName%> (<%=pStudentId%>) 님의 성적평가
												</td>
												<td align=right  height=30>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr class="s_tab01">
									<td colspan="11"></td>
								</tr>
								<tr class="s_tab02">
									<td width="">성적요소</td>
									<td class="s_tablien"></td>
									<td width="100">구분</td>
									<td class="s_tablien"></td>
									<td width="">원점수</strong><br>취득/배점</td>
									<td class="s_tablien"></td>
									<td width="">환산점수</strong><br>취득/배점</td>
								</tr>
								<tr class="s_tab03">
									<td colspan="11"></td>
								</tr>
								<!-- 시험시작 -->
								<!--tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
									<td class="s_tab04_cen" rowspan="<%=(examScoreList.size()*2+1)%>">시험 (<%=examBase%>%)</td>
									<td rowspan="<%=(examScoreList.size()*2+1)%>" bgcolor="#D5CCC3"></td>
<%	double calExamBaseScore = 0;
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

	if (examCnt > 0)
		out.println("<tr class='s_tab02'>");	%>

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
								</tr-->




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
								<tr>
									<td colspan="11" align="center" valign="top" class="pop_btn">
                                        <Script Language=javascript>Button3("등 록", "chkForm()", "");</script>
                                        <%	if (idLength > 0) {	%>
                                        &nbsp;<Script Language=javascript>Button3("다 음", "go_next()", "");</script>
                                        <%	}	%>
									</td>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><a href="javascript:win_close()" onMouseOver="window.status='작업을 중단하고 창을 닫습니다';return true" onMouseOut="window.status='';return true"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif"></a></td>
		</tr>
</form>
<!-- form end -->
	</table>

<%@include file="../common/popup_footer.jsp" %>