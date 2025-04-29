<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.curridate.dto.CurriDateDTO"%>
<%@ page import = "com.edutrack.common.DateSetter, com.edutrack.common.dto.DateParam, com.edutrack.common.dto.CodeParam" %>
<%@include file="../common/header.jsp" %>
<%
	DateParam dateParam = null;
	CurriDateDTO	curriDateDto	=	null;
	String	pGubun		=	StringUtil.nvl((String)model.get("pGubun"));
	int		pCurriYear	=	0;
	int		pHakgiTerm	=	1;
	String	pServiceYn	=	"";

	if(pGubun.equals("W")) {
		pCurriYear		=	Integer.parseInt(CommonUtil.getCurrentDate().substring(0, 4));
	} else {
		curriDateDto	=	(CurriDateDTO)model.get("curriDateDto");
		pCurriYear		=	curriDateDto.getCurriYear();
		pHakgiTerm		=	curriDateDto.getHakgiTerm();
		pServiceYn		=	curriDateDto.getServiceYn();
	}

%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriDateWriteWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/curri_date/curriDateWrite.js"></script>
<script language="javascript">
<!--
	function submitCheck() {
		var f = document.Input;

		var enrollStart = f.pEDate1.value;
		var enrollEnd = f.pEDate2.value;
		var cancelStart = f.pCDate1.value;
		var cancelEnd = f.pCDate2.value;
		var serviceStart = f.pSDate1.value;
		var serviceEnd = f.pSDate2.value;
		var chungEnd = f.pDate71.value;
		var assessmentEnd = f.pDate81.value;
		var completeEnd = f.pDate91.value;

		if(isEmpty(enrollStart)){
			alert('수강신청 시작일을 입력해 주세요');
			return false;
		}
		if(isEmpty(enrollEnd)){
			alert('수강신청 종료일을 입력해 주세요');
			return false;
		}
		if(isEmpty(cancelStart)){
			alert('수강취소 시작일을 입력해 주세요');
			return false;
		}
		if(isEmpty(cancelEnd)){
			alert('수강취소 종료일을 입력해 주세요');
			return false;
		}
		if(isEmpty(serviceStart)){
			alert('수강기간 시작일을 입력해 주세요');
			return false;
		}
		if(isEmpty(serviceEnd)){
			alert('수강기간 종료일을 입력해 주세요');
			return false;
		}
		if(isEmpty(chungEnd)){
			alert('청강종료일을 입력해 주세요');
			return false;
		}
		if(isEmpty(assessmentEnd)){
			alert('평가종료일을 입력해 주세요');
			return false;
		}
		if(isEmpty(completeEnd)){
			alert('수료종료일을 입력해 주세요');
			return false;
		}

		if(parseFloat(cancelStart) < parseFloat(enrollStart)){
			alert('수강취소 시작일은 수강신청일과 같거나 이후여야 합니다.');
			return false;
		}
		//if(parseFloat(serviceStart) <= parseFloat(cancelEnd)){
		//	alert('수강시작일은 수강취소 종료일 이후여야 합니다.');
		//	return false;
		//}
		if(parseFloat(chungEnd) < parseFloat(serviceEnd)){
			alert('청강 종료일은 수강 종료일과 같거나 이후여야 합니다.');
			return false;
		}
		if(parseFloat(assessmentEnd) < parseFloat(serviceEnd)){
			alert('평가 종료일은 수강 종료일과 같거나 이후여야 합니다.');
			return false;
		}
		if(parseFloat(completeEnd) < parseFloat(assessmentEnd)){
			alert('수료처리 종료일은 평가 종료일과 같거나 이후여야 합니다.');
			return false;
		}

		if(!validate(f)) return false;

		f.submit();
	}

	function goList() {
		document.location = "<%=CONTEXTPATH%>/CurriDate.cmd?cmd=curriDateList&MENUNO=0&pMode=MyPage";
	}
//-->
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<form method="post" name="Input" action = "/CurriDate.cmd?cmd=curriDateRegist">
<input type="hidden" name="pGubun" value="<%=pGubun%>">
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">개설년도</td>
												<td class="s_tab_view02" width="200"><INPUT name="pCurriYear" value="<%=pCurriYear%>" dataTye = "number" notnull maxLength=4 size=4></td>
												<td class="s_tab_view01" width="90">개설학기</td>
												<!--td class="s_tab_view02""><INPUT name="pHakgiTerm" value="<%=pHakgiTerm%>" onChange="checkHakgiTerm();" dataTye = "number" notnull maxLength=2 size=2> &nbsp;여름학기(11), 겨울학기(21)</td-->
												<td class="s_tab_view02""><INPUT name="pHakgiTerm" value="<%=pHakgiTerm%>" dataTye = "number" notnull maxLength=2 size=2> &nbsp;여름학기(11), 겨울학기(21)</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">수강신청기간</td>
												<td class="s_tab_view02" colspan="3">
<%
		dateParam = new DateParam();
		dateParam.setCount(2);
		dateParam.setDateType(1);
		dateParam.setForm("Input");
		dateParam.setDate("pEDate");
		dateParam.setYear("pEY");
		dateParam.setMonth("pEM");
		dateParam.setDay("pED");
		dateParam.setHour("pEHH");
		dateParam.setMinute("pEMI");

		out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("enroll_date")));
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">정정(취소)기간</td>
												<td class="s_tab_view02" colspan="3">
<%
		dateParam = new DateParam();
		dateParam.setCount(2);
		dateParam.setDateType(1);
		dateParam.setForm("Input");
		dateParam.setDate("pCDate");
		dateParam.setYear("pCY");
		dateParam.setMonth("pCM");
		dateParam.setDay("pCD");
		dateParam.setHour("pCHH");
		dateParam.setMinute("pCMI");

		out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("cancel_date")));
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">수강기간</td>
												<td class="s_tab_view02" colspan="3">
<%
		dateParam = new DateParam();
		dateParam.setCount(2);
		dateParam.setDateType(1);
		dateParam.setForm("Input");
		dateParam.setDate("pSDate");
		dateParam.setYear("pSY");
		dateParam.setMonth("pSM");
		dateParam.setDay("pSD");
		dateParam.setHour("pSHH");
		dateParam.setMinute("pSMI");

		out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("service_date")));
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">청강종료일</td>
												<td class="s_tab_view02" colspan="3">
<%
		dateParam = new DateParam();
		dateParam.setCount(1);
		dateParam.setDateType(1);
		dateParam.setForm("Input");
		dateParam.setDate("pDate7");
		dateParam.setYear("pCHEY");
		dateParam.setMonth("pCHEM");
		dateParam.setDay("pCHED");
		dateParam.setHour("pHour7");
		dateParam.setMinute("pMinute7");

		out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("chung_end")));
%>&nbsp;&nbsp;<font class="s_text03">* 수강종료일보다 크거나 같게 해 주세요</font>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">평가종료일</td>
												<td class="s_tab_view02" colspan="3">
<%
		dateParam = new DateParam();
		dateParam.setCount(1);
		dateParam.setDateType(1);
		dateParam.setForm("Input");
		dateParam.setDate("pDate8");
		dateParam.setYear("pREY");
		dateParam.setMonth("pREM");
		dateParam.setDay("pRED");
		dateParam.setHour("pHour8");
		dateParam.setMinute("pMinute8");

		out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("assessment_end")));
%>&nbsp;&nbsp;<font class="s_text03">* 수강종료일보다 크거나 같게 해 주세요</font>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">수료처리종료일</td>
												<td class="s_tab_view02" colspan="3">
<%
		dateParam = new DateParam();
		dateParam.setCount(1);
		dateParam.setDateType(1);
		dateParam.setForm("Input");
		dateParam.setDate("pDate9");
		dateParam.setYear("pCOEY");
		dateParam.setMonth("pCOEM");
		dateParam.setDay("pCOED");
		dateParam.setHour("pHour9");
		dateParam.setMinute("pMinute9");

		out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("complete_end")));

%>&nbsp;&nbsp;<font class="s_text03">* 수강종료일보다 크거나 같게 해 주세요</font>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>

											<tr>
												<td class="s_tab_view01">사용여부</td>
												<td class="s_tab_view02" colspan="3">
													<input class=no type=radio value="Y" name=pServiceYn <%if(pServiceYn.equals("") || pServiceYn.equals("Y")) {%> checked<%}%>><font class="s_text01">사용</font>&nbsp;&nbsp;&nbsp;
													<input class=no type=radio value="N" name=pServiceYn <%if(pServiceYn.equals("N")) {%> checked<%}%>><font class="s_text02">사용안함</font>
												</td>
											</tr>
<%	if(pGubun.equals("E")) { %>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">작성자</td>
												<td class="s_tab_view02" width="200"><%=CommonUtil.getUserName(curriDateDto.getSystemCode(), curriDateDto.getRegId())%></td>
												<td class="s_tab_view01" width="90">작성일</td>
												<td class="s_tab_view02"><%=DateTimeUtil.getDateType(1, curriDateDto.getRegDate())%></td>
											</tr>
<%	} %>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
												
<% if( (pGubun.equals("W")&&CommonUtil.getAuthorCheck(request,  "C")) || (pGubun.equals("E")&&CommonUtil.getAuthorCheck(request,  "U")) ) /* 권한체크 */  { %>
<script language=javascript>Button3("확인", "submitCheck()", "");</script>
<%	}	%>
&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
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