<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.currisub.dto.CurriSubCourseDTO,com.edutrack.common.dto.CodeParam"%>
<%@include file="../common/popup_header.jsp" %>
<%	String	pCurriType	=	StringUtil.nvl(model.get("pCurriType"));	%>
<script language="javascript">
	function submitCheck()
	{
		var f = document.Input;
		if(f.pProfId.value == ''){
			alert("교수자를 선택 해 주셔야합니다.");
			return false;
		}

		var report_base = parseInt(f.pReportBase.value);
		var attend_base = parseInt(f.pAttendBase.value);
		var forum_base = parseInt(f.pForumBase.value);
		var etc1_base = parseInt(f.pEtc1Base.value);
		var etc2_base = parseInt(f.pEtc2Base.value);
		var total = report_base+attend_base+forum_base+etc1_base+etc2_base;
		
<%	if(pCurriType.equals("R")) {	%>		
		//---- 총합이 100 인지
		if(total != 100) {
			alert("기준비율의 총합이 100 이 아닙니다.\n\n정규과정의 기준비율은 총합을 100으로 맞춰주셔야 합니다.");
			return false;
		}
<%	}	%>
		if(!validate(f)) return false;
		f.submit();
	}
	function cancel_page() {
		top.window.close();
    }
</script>
	<table width="100%" height="100%" border="0">
<!--  onSubmit="return submitCheck()" -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/CurriSubCourse.cmd?cmd=curriSubCourseRegist&pRegMode=Edit">
<input type="hidden" name="pCurriCode" value="<%=model.get("pCurriCode")%>">
<input type="hidden" name="pCurriYear" value="<%=model.get("pCurriYear")%>">
<input type="hidden" name="pCurriTerm" value="<%=model.get("pCurriTerm")%>">
<input type="hidden" name="pCourseId" value="<%=model.get("pCourseId")%>">
<input type="hidden" name="pCourseType" value="<%=model.get("pCourseType")%>">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="../img/1/common/blet05.gif" align="absmiddle"><font class="pop_tit">과목연결정보입력</font></td>
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
								<tr class="s_tab01">
									<td colspan="4"></td>
								</tr>
								<tr>
									<td class="s_tab_view01">과정명</td>
									<td class="s_tab_view02" colspan="3"><font class="c_text02"><b><%=model.get("pCurriName")%></b></font></td>
								</tr>
								<tr>
									<td height="1" colspan="4" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">과목명</td>
									<td class="s_tab_view02" colspan="3"><%=model.get("pCourseName")%></td>
								</tr>
								<tr>
									<td height="1" colspan="4" class="c_test_tablien01"> </td>
								</tr>
<%
	String pMode = (String)model.get("pMode");
	if (pMode.equals("Lecture")) {
%>
	<input type=hidden name=pProfId value="<%=model.get("pProfId")%>">
<%
	}
	else {
%>
								<tr>
									<td class="s_tab_view01">교수자</td>
									<td class="s_tab_view02" colspan="3">
<%
		String profId = (String)model.get("pProfId");
		CodeParam param1 = new CodeParam();
		param1.setSystemcode(SYSTEMCODE);
		param1.setType("select");
		param1.setName("pProfId");
		param1.setFirst("-- 교수자선택--");
		//param1.setEvent("Change_Code()");
		param1.setSelected(profId);
		out.print(CommonUtil.getUserList(param1,"P"));
%>
									</td>
								</tr>
								<tr>
									<td height="1" colspan="4" class="c_test_tablien01"> </td>
								</tr>
<%
	}
%>
								<tr>
									<!--td class="s_tab_view01">시험기준비율</td>
									<td class="s_tab_view02"><input type="text" name=pExamBase size=3 maxlength=3 value="<%=model.get("pExamBase")%>"></td-->
									<td class="s_tab_view01">출석기준비율</td>
									<td class="s_tab_view02" colspan="3"><input type="text" name=pAttendBase size=3 maxlength=3 value="<%=model.get("pAttendBase")%>"></td>
								</tr>
								<tr>
									<td height="1" colspan="4" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">과제기준비율</td>
									<td class="s_tab_view02"><input type="text" name=pReportBase size=3 maxlength=3 value="<%=model.get("pReportBase")%>"></td>
									<td class="s_tab_view01">포럼기준비율</td>
									<td class="s_tab_view02"><input type="text" name=pForumBase size=3 maxlength=3 value="<%=model.get("pForumBase")%>"></td>
								</tr>
								<tr>
									<td height="1" colspan="4" class="c_test_tablien01"> </td>
								</tr>


								<tr>
									<td class="s_tab_view01">그룹공부기준비율</td>
									<td class="s_tab_view02"><input type="text" name=pEtc1Base size=3 maxlength=3 value="<%=model.get("pEtc1Base")%>"></td>
									<td class="s_tab_view01">기타기준비율</td>
									<td class="s_tab_view02"><input type="text"name=pEtc2Base size=3 maxlength=3 value="<%=model.get("pEtc2Base")%>"></td>
								</tr>

								<tr class="s_tab05">
									<td colspan="4"></td>
								</tr>
								<tr>
									<td colspan="4" align="center" valign="top" class="pop_btn">
<script language=javascript>Button3("확인", "submitCheck()", "");</script>
&nbsp;<script language=javascript>Button3("취소", "cancel_page()", "");</script>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><img src="../img/1/button_img/btn_popclose.gif"></td>
		</tr>
	</table>



<%@include file="../common/popup_footer.jsp" %>