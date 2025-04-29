<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam,com.edutrack.schedule.dto.ScheduleDTO"%>
<%@include file="../common/header.jsp" %>
<%
	String pVMode = StringUtil.nvl((String)model.get("pVMode"), "");
	if (pVMode==null) pVMode="";

	Map data = (Map)request.getAttribute("MODEL");
	String pRegMode = (String)model.get("pRegMode");
	String pSchId  = StringUtil.nvl((String)model.get("pSchId"), "");;

	String sch_type = "";
	String contents = "";

	if(USERID.equals("")) {
%>
<script>
alert('로그인 하신후 이용하세요.');
history.back();
</script>
<%
		return;
	}

	if (pVMode.equals("1") && !USERTYPE.equals("M")) {
%>
<script>
alert('관리자 ID로 로그인 하신후 이용하세요.');
history.back();
</script>
<%
		return;
	}

	//수정모드일 경우
	if(pRegMode.equals("E")) {
		ScheduleDTO scheduleView = (ScheduleDTO)data.get("scheduleView");
		sch_type = scheduleView.getSchType();
		contents = scheduleView.getContents();
	}
%>

<script language="javascript">
	function submitCheck()
	{
		var f = document.Input;
		if(isEmpty(f.pContents.value))
		{
			alert("내용을 입력하세요");
			f.pContents.focus();
			return false;
		}
//		return true;
		f.submit();
	}

	function goList() {
			document.location = "<%=CONTEXTPATH%>/Schedule.cmd?cmd=scheduleView&pVMode=<%=pVMode%>";
	}

	function goDel() {
		var stat = confirm("일정을 삭제하시겠습니까?");
		if (stat == true) {
			var	f = document.Input;
			f.action="<%=CONTEXTPATH%>/Schedule.cmd?cmd=scheduleDelete";
			f.submit();
		}
	}
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start   onSubmit="return submitCheck()"  -->
<form name="Input" method="post" action="<%=CONTEXTPATH%>/Schedule.cmd?cmd=scheduleRegist">
<input type="hidden" name="pRegMode" value="<%=pRegMode%>">
<input type="hidden" name="pSchId" value="<%=pSchId%>">
<input type="hidden" name="pVMode" value="<%=pVMode%>">
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">일정기간</td>
												<td class="s_tab_view02">
<%	DateParam dateParam = new DateParam();
	dateParam.setCount(2);
	dateParam.setDateType(1);
	dateParam.setForm("Input");
	dateParam.setDate("pDate");
	dateParam.setYear("pYear");
	dateParam.setMonth("pMonth");
	dateParam.setDay("pDay");
	out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("ds")));	%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">일정분류</td>
												<td class="s_tab_view02">
													<select name='pSchType'>
														<option value='1' <%if(sch_type.equals("1") || sch_type.equals("")){%> selected <%}%>>업무일정</option>
<%	if(USERTYPE.equals("M")) {	%>
														<option value='2' <%if(sch_type.equals("2")){%> selected <%}%>>학습일정</option>
														<option value='99' <%if(sch_type.equals("99")){%> selected <%}%>>학사일정</option>
														<option value='4' <%if(sch_type.equals("4")){%> selected <%}%>>학습관일정</option>
<%	}	%>
														<option value='3' <%if(sch_type.equals("3")){%> selected <%}%>>기념일</option>
														
													</select>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											
											<tr>
												<td class="s_tab_view01">내용</td>
												<td class="s_tab_view03">
													<textarea name="pContents" cols="70" rows="10" wrap="VIRTUAL"  dispName="내용" notNull><%=contents%></textarea>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("목록", "goList()", "");</script>
<%	if (pRegMode.equals("E")) {	%>
&nbsp;<script language=javascript>Button3("수정", "submitCheck()", "");</script>
<%	} else {	%>
&nbsp;<script language=javascript>Button3("등록", "submitCheck()", "");</script>
<%	}
	if (pRegMode.equals("E")) {	%>
&nbsp;<script language=javascript>Button3("삭제", "goDel()", "");</script>
<%	}	%>
												</td>
											</tr>
</form>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						

<%@include file="../common/footer.jsp" %>