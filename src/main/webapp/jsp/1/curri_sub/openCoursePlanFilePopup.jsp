<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.dto.CodeParam"%>
<%@include file="../common/popup_header.jsp" %>
<%
	String	pCourseId = StringUtil.nvl(model.get("pCourseId"));
%>
<script language="javascript">
	function submitCheck() {
		document.inputFile.target = "hiddenFrame";
		document.inputFile.submit();
	}

	function returnOpener() {
		opener.init();
		self.close();
	}
</script>
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">강의계획서 파일 첨부</font></td>
			<td class="pop_topright"></td>
		</tr>
		<tr>
			<td class="pop_midleft"></td>
			<td valign="top" class="pop_form">
				<!-- 내용 -->
				<table width="90%" height="100%" align="center">
<form name="inputFile" method="post" action="/CurriSubCoursePlan.cmd?cmd=coursePlanFileRegist&pCourseId=<%=pCourseId%>"  enctype = "multipart/form-data">
<input type="hidden" name="callBackMethod" value="parent.returnOpener()"><!-- opener.callbackAfterRegist  -->


					<tr height="97%">
						<td align="left" valign="top">
							<table width="92%" border="0" cellspacing="0" cellpadding="0">
								<tr class="s_tab01">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td colspan="2" class="s_tab_view01">첨부파일</td>
								</tr>
<!--
<tr>
	<td height="1" colspan="2" class="c_test_tablien01"> </td>
</tr>
 -->
								<tr>
									<td colspan="2" class="s_tab_view02"><input type="file" name="pFile[1]" size="40" value=""></td>
								</tr>


								<tr class="s_tab05">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td colspan="2" align="center" valign="top" class="pop_btn">
<script language=javascript>Button3("등록", "submitCheck()", "");</script>
									</td>
								</tr>
							</table>
						</td>
					</tr>
</form>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><a href="javascript:window.close()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif"></a></td>
		</tr>
	</table>
<%@include file="../common/popup_footer.jsp" %>