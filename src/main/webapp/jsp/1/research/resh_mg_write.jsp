<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/course_header.jsp" %>
<%@ page import ="com.edutrack.curriresearch.dto.CurriResInfoDTO" %>
<%@ page import ="com.edutrack.common.DateSetter" %>
<%@ page import ="com.edutrack.common.dto.DateParam"%>
<%
    CurriResInfoDTO resInfo = (CurriResInfoDTO)model.get("resInfo");
    String pMODE = (String)model.get("pMODE");
    String pResId = (String)model.get("pResId");
    String[] width = new String[]{"190","420"};
%>

<script language="javascript">
<!--
	function SubmitCheck()
	{
		var f = document.Regist;

		if(!validate(f)) return;

		f.submit();
    }

    function goCancel(){
        var f = document.Regist;

        var loc="<%=CONTEXTPATH%>/ResearchInfo.cmd?cmd=researchInfoList";
        document.location = loc;
    }

    function goDelete(){
   		var f = document.Regist;
		var pMODE = f.pMODE.value;
		var pResId = f.pResId.value;
		var str = confirm("설문 정보를 삭제 하시겠습니까? \n설문 응답자료와 설문 내용 정보가 모두 삭제됩니다. ");
		if (str == true) {
		   	document.location.href="/ResearchInfo.cmd?cmd=researchInfoDelete&pResId="+pResId+"&pMODE="+pMODE;
		}
    }
//-->
</script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"설문관리")%></b></font></td>
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
<form name=Regist method="post" action="<%=CONTEXTPATH%>/ResearchInfo.cmd?cmd=researchInfoRegist">
<input type=hidden name="pMODE" value="<%=pMODE%>">
<input type=hidden name="pResId" value="<%=pResId%>">

											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">설문명</td>
												<td class="s_tab_view02"><input type=text name=pSubject size=70 dispName="설문명" notNull lenCheck=50 value="<%=resInfo.getSubject()%>" ></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">설문기간</td>
												<td class="s_tab_view02">
<%	DateParam dateParam = new DateParam();
	dateParam.setCount(2);
	dateParam.setDateType(1);
	dateParam.setForm("f");
	dateParam.setDate("resTerm");
	dateParam.setYear("year1");
	dateParam.setMonth("month1");
	dateParam.setDay("day1");
	dateParam.setHour("hour1");
	dateParam.setMinute("minute1");
    out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("resTerm")));	%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">등록여부</td>
												<td class="s_tab_view02">
													<input type="radio" name="pOpenYn" value="Y" <%=(resInfo.getOpenYn().equals("Y") || resInfo.getOpenYn().equals("") ? "checked" : "" ) %> class="no">완료
           											<input type="radio" name="pOpenYn" value="N" <%=(resInfo.getOpenYn().equals("N") ? "checked" : "" ) %> class="no">대기
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">설문설명</td>
												<td class="s_tab_view03"><textarea name=pContents rows=10 cols=70 wrap="auto" dispName="설문에 대한 간단한 설명"><%=resInfo.getContents()%></textarea></td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("목록", "goCancel()", "");</script>
<%	if (pMODE.equals("write")) {	%>
&nbsp;<script language=javascript>Button3("등록", "SubmitCheck('<%=pMODE%>')", "");</script>
<%	} else {	%>
&nbsp;<script language=javascript>Button3("수정", "SubmitCheck('<%=pMODE%>')", "");</script>
<%	}

	if (!pMODE.equals("write")) {	%>
&nbsp;<script language=javascript>Button3("삭제", "goDelete()", "");</script>
<%	}	%>
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
<script language="javascript">
     document.Regist.pSubject.focus();
</script>

<%@include file="../common/course_footer.jsp" %>