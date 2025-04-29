<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.dto.CodeParam"%>
<%@include file="../../common/popup_header.jsp" %>
<%
	String userType = (String)model.get("userType");
	String strUserType = "관리자";
	String selUserTypeM = " selected";
	String selUserTypeP = "";
	if(userType.equals("P")){
		strUserType = "교수자";
		selUserTypeM = "";
		selUserTypeP = " selected";
	}
%>
<Script Language="javascript">
//top.resizeTo(410,255)
//top.focus();

	function submitCheck()
	{
		var f = document.Input;
		if(f.pUserType.value == "") {
			alert("사용자 유형을 선택하세요!");
			return false;
		}
		if(!validate(f)) return false;
//		else return true;
		f.submit();
	}
	function goUserSearch() {
		window.open("<%=CONTEXTPATH%>/User.cmd?cmd=searchUserList", "usersearch","toolbar=no,location=no, width=426, height=255,top=50, left=50, directories=no,status=no,menubar=no,scrollbars=yes,resizable=no");
	}
</Script>
<!--  onSubmit="return submitCheck()" -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/UserAdmin.cmd?cmd=userTypeAddRegist">
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">사용자 유형(권한) 관리</font></td>
			<td class="pop_topright"></td>
		</tr>
		<tr>
			<td class="pop_midleft"></td>
			<td valign="top" class="pop_form">
				<!-- 내용 -->
				<table width="90%" height="100%" align="center">
					<tr height="97%">
						<td align="center" valign="middle">
							<table width="92%" border="0" cellspacing="0" cellpadding="0">
								<tr class="s_tab01">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td class="s_tab_view01">사용자 유형선택 :</td>
									<td class="s_tab_view02">&nbsp;<select name="pUserType" style="width:110px">
											<option value="S">학습자</option>
											<option value="P" <%=selUserTypeP%>>교수자</option>
											<option value="M" <%=selUserTypeM%>>관리자</option>
										</select>
									</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">사용자 ID :</td>
									<td class="s_tab_view02">&nbsp;<input type="text" name="pId" size="16" maxlenght="15" dispName="사용자ID" notNull readonly>
&nbsp; <a href="javascript:goUserSearch()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_usersearch.gif" border="0" align="absmiddle"></a></td>
								</tr>
								<tr class="s_tab05">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td colspan="2" align="center" valign="top" class="pop_btn">※ 등록한 사용자를 선택하신 사용자 유형으로 등록합니다.</td>
								</tr>
								<tr>
									<td colspan="2" align="center" valign="top" class="pop_btn"><script	language=javascript>Button3("확인", "submitCheck()", "");</script></td>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><a
			href="javascript:self.close()"><img
			src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif"></a></td>
		</tr>
	</table>
</form>
<%@include file="../../common/popup_footer.jsp" %>