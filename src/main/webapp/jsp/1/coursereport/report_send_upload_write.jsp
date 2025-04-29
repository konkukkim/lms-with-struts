<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/popup_header.jsp" %>
<%
   String pCourseId = (String)model.get("pCourseId");
   String pReportId = (String)model.get("pReportId");
%>
<script language=javascript>
	function check_form() {
		var f = document.Input;

		if (isEmpty(f.pFILE.value)) {
			alert("파일을 첨부해 주세요");
			return false;
		}

		if ((file_ext(f.pFILE.value) != "txt")) {
			alert("확장자가 TXT 파일만 업로드 하실 수 있습니다.");
			return false;
		}

		return true;
	}

	function file_ext(f_name) {
		var ext = "";
		if (f_name != "") {
			var lst_in = f_name.lastIndexOf('.');
			ext = f_name.substring(lst_in+1);
			ext = ext.toLowerCase();
		}
		return ext;
	}

	function closePop() {
		window.close();
	}
</script>
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">과제점수 업로드</font></td>
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
<!-- FORM 시작    onSubmit="return check_form()"  -->
<form name="Input" method=post action="<%=CONTEXTPATH%>/ReportResult.cmd?cmd=reportSendUpLoadRegist" enctype="multipart/form-data">
<input type=hidden name="pCourseId" value="<%=pCourseId%>">
<input type=hidden name="pReportId"   value="<%=pReportId%>">
								<tr class="s_tab01">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td class="s_tab_view01" colspan="2">과제물 점수를 입력할 파일을 선택하세요.</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view03" colspan="2">
										<input type="file" name="pFILE" size="50" class="brown"><br>
										<font color="F58220">* 수강생 아이디,점수</font> 형태로 구성되어 있어야 합니다.
									</td>
								</tr>
								<tr class="s_tab05">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td colspan="2" align="center" valign="top" class="pop_btn">
<script language=javascript>Button3("등록", "check_form()", "");</script>
&nbsp;<script language=javascript>Button3("취소", "closePop()", "");</script></td>
								</tr>
</form>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><a href="javascript:closePop();"><img
			src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif"></a></td>
		</tr>
	</table>
<%@include file="../common/popup_footer.jsp" %>