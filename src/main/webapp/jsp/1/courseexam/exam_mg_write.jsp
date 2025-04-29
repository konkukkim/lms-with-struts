<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/course_header.jsp" %>
<%@ page import ="com.edutrack.courseexam.dto.ExamInfoDTO,com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam"%>
<%
    ExamInfoDTO examInfo = (ExamInfoDTO)model.get("examInfo");
    String pMODE = (String)model.get("pMODE");
    String pCourseId = (String)model.get("pCourseId");
    String pExamId = (String)model.get("pExamId");
    String pAnswerUserCnt = (String)model.get("pAnswerUserCnt");

    String[] width = new String[]{"190","647"};
%>

<script language="javascript">
<!--
	function SubmitCheck()
	{
		var f = document.Regist;

		if(!validate(f)) return;

	    var st_examterm = f.year11.value + f.month11.value + f.day11.value +
	                      f.hour11.value + f.minute11.value + "00";
        f.examTerm1.value = st_examterm;

	    var ed_examterm = f.year12.value + f.month12.value + f.day12.value +
	                      f.hour12.value + f.minute12.value + "00";
        f.examTerm2.value = ed_examterm;

	    var st_examresult = f.year21.value + f.month21.value + f.day21.value +
	                      f.hour21.value + f.minute21.value + "00";
        f.examResult1.value = st_examresult;

	     // 결과확인일자의 유효성 체크
        if(parseFloat(ed_examterm) > parseFloat(st_examresult)){
           alert("결과확인일자는 시험종료일자 이후에 설정이되어야 합니다.");
           return;
        }

		f.submit();
    }

    function goCancel(){
        var f = document.Regist;

        var courseid = f.pCourseId.value;

        var loc="<%=CONTEXTPATH%>/ExamAdmin.cmd?cmd=examList&pCourseId="+courseid;
        document.location = loc;
    }

    function goDelete() {
    	var f = document.Regist;
    	var pMODE = f.pMODE.value;
    	var pCourseId = f.pCourseId.value;
    	var pExamId = f.pExamId.value;
    	var pAnswerUserCnt = f.pAnswerUserCnt.value;

    	if(parseInt(pAnswerUserCnt) > 0 ){
		   var str = confirm("현재 응시자가 " + pAnswerUserCnt +"명이 있습니다.\n삭제하시겠습니까? (삭제시 시험 응시 정보도 삭제됩니다.)");
		   if (str == true ) {
			   	var loc = "/ExamAdmin.cmd?cmd=examDelete&pMODE="+pMODE+"&pCourseId="+pCourseId+"&pExamId="+pExamId;
		     	document.location.href = loc;
		   }
		} else {
	    	var loc = "/ExamAdmin.cmd?cmd=examDelete&pMODE="+pMODE+"&pCourseId="+pCourseId+"&pExamId="+pExamId;
	    	document.location.href = loc;

	    }
    }
//-->
</script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"시험출제")%></b></font></td>
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
<!-- form start -->
<form name=Regist method="post" action="<%=CONTEXTPATH%>/ExamAdmin.cmd?cmd=examRegist">
<input type=hidden name="pMODE" value="<%=pMODE%>">
<input type=hidden name="pCourseId" value="<%=pCourseId%>">
<input type=hidden name="pExamId" value="<%=pExamId%>">
<input type=hidden name="pAnswerUserCnt" value="<%=pAnswerUserCnt%>">

											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">시험명</td>
												<td class="s_tab_view02"><input type=text name=pSubject size=70 dispName="시험명" notNull lenCheck=50 value="<%=examInfo.getSubject()%>"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">시험기간</td>
												<td class="s_tab_view02">
<%	DateParam dateParam = new DateParam();
	dateParam.setCount(2);
	dateParam.setDateType(0);
	dateParam.setForm("f");
	dateParam.setDate("examTerm");
	dateParam.setYear("year1");
	dateParam.setMonth("month1");
	dateParam.setDay("day1");
	dateParam.setHour("hour1");
	dateParam.setMinute("minute1");
    out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("examTerm")));	%>
													<br>[시간은 24시간제로 직접 입력 ex) 23:11]
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">시간</td>
												<td class="s_tab_view02">
													<input type=text name=pRumTime size=10 dispName="시간" minValue="1" minValue="24" notNull dataType="number" lenCheck=4 value="<%=examInfo.getRunTime()%>" >분 동안
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">결과확인일자</td>
												<td class="s_tab_view02">
<%	dateParam = new DateParam();
	dateParam.setCount(1);
	dateParam.setDateType(0);
	dateParam.setForm("f");
	dateParam.setDate("examResult");
	dateParam.setYear("year2");
	dateParam.setMonth("month2");
	dateParam.setDay("day2");
	dateParam.setHour("hour2");
	dateParam.setMinute("minute2");
    out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("examResult")));	%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">문제지유형</td>
												<td class="s_tab_view02">
													<input type="radio" name="pViewStyle" value="S" class="solid0" <%=(examInfo.getViewStyle().equals("S") || examInfo.getViewStyle().equals("") ? "checked" : "" ) %>>한문제씩
           											<input type="radio" name="pViewStyle" value="L" class="solid0" <%=(examInfo.getViewStyle().equals("L")? "checked" : "" ) %>>전체문제
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">성적적용여부</td>
												<td class="s_tab_view02">
													<input type="radio" name="pExamType" value="Y" class="solid0" <%if(examInfo.getExamType().equals("Y") || examInfo.getExamType().equals("")){%>checked<%}%> style="border:0">적용
          											<input type="radio" name="pExamType" value="N" class="solid0" <%if(examInfo.getExamType().equals("N")){%>checked<%}%> style="border:0">미적용
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">시간체크여부</td>
												<td class="s_tab_view02">
													<input type="radio" name="pFlagTime" value="Y" class="solid0" <%if(examInfo.getFlagTime().equals("Y") || examInfo.getFlagTime().equals("")){%>checked<%}%> style="border:0">시간체크
          											<input type="radio" name="pFlagTime" value="N" class="solid0" <%if(examInfo.getFlagTime().equals("N")){%>checked<%}%> style="border:0">시간체크안함
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">시험설명</td>
												<td class="s_tab_view03">
													<textarea name=pComment rows=10 cols=70 wrap="auto" dispName="시험에 대한 간단한 설명"><%=examInfo.getComment()%></textarea>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<%	if (pMODE.equals("write")) {	%>
<script language=javascript>Button3("등록", "SubmitCheck('<%=pMODE%>')", "");</script>
<%	} else {	%>
&nbsp;<script language=javascript>Button3("수정", "SubmitCheck('<%=pMODE%>')", "");</script>
<%	}	%>
<%	if (!pMODE.equals("write")) {	%>
&nbsp;<script language=javascript>Button3("삭제", "goDelete()", "");</script>
<%	}	%>
&nbsp;<script language=javascript>Button3("취소", "goCancel()", "");</script>
												</td>
											</tr>
</form>
<!-- form -->

										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/course_footer.jsp" %>



































