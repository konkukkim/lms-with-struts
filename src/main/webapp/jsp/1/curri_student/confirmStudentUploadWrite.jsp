<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/popup_header.jsp" %>
<%
   String pCurriCode = (String)model.get("pCurriCode");
   String pCurriYear = (String)model.get("pCurriYear");
   String pCurriTerm = (String)model.get("pCurriTerm");
%>
<script language=javascript>
window.resizeTo(530,300);
	function check_form() {
		var f = document.Input;

		if (isEmpty(f.pFILE.value)) {
			alert("파일을 첨부해 주세요");
			return false;
		}

		if (file_ext(f.pFILE.value) != "csv" && file_ext(f.pFILE.value) != "txt") {
			alert("확장자가 CSV or TXT 파일만 업로드 하실 수 있습니다.");
			return false;
		}

		//return true;
		f.submit();
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
</script>
<!--  onSubmit="return check_form()" -->
<form name="Input" method=post action="<%=CONTEXTPATH%>/Student.cmd?cmd=confirmStudentUploadRegist&pCurriCode=<%=pCurriCode%>&pCurriYear=<%=pCurriYear%>&pCurriTerm=<%=pCurriTerm%>" enctype="multipart/form-data">
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">수강생관리 - 수강생 일괄 등록</font></td>
			<td class="pop_topright"></td>
		</tr>
		<tr>
			<td class="pop_midleft"></td>
			<td valign="top" class="pop_form">
				<!-- 내용 -->
				<table width="90%" height="100%" align="center">
					<tr height="97%">
						<td align="left" valign="top" align="center">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="s_tab_view02" colspan="2">
										&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet04.gif" align="absmiddle" width="12" height="11" border="0">
										수강생을 일괄 입력할 파일을 입력하세요.
									</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td align="center" colspan="2" class="s_tab_view01"><input type=file name="pFILE" size="40"></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view02">
										&nbsp;&nbsp;* <font color="#FF6600">라인별
										수강생아이디 하나씩만 입력해 주셔야 합니다.</font><br>
										&nbsp;&nbsp;&nbsp;&nbsp;확장자가 CSV or
										TXT 파일만 업로드 하실 수 있습니다.
									</td>
								</tr>

								<tr>
									<td colspan="2" align="center" valign="top" class="pop_btn">
										<script language=javascript>Button3("등록", "check_form()", "");</script>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><a href="javascript:window.close();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif"></a></td>
		</tr>
	</table>
</form>
<%@include file="../common/popup_footer.jsp" %>
