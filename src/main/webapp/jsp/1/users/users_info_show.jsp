<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr"    %>
<%@ page import ="com.edutrack.user.dto.UsersDTO"   %>
<%@include file="../common/popup_header.jsp"        %>
<%
    UsersDTO userInfo = (UsersDTO)model.get("userInfo");
%>
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">사용자정보</font></td>
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
									<td colspan="2"></td>
								</tr>
								<tr>
									<td class="s_tab_view01">이름</td>
									<td class="s_tab_view02"><font class="c_text02"><b><%=userInfo.getUserName()%></b></font></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">아이디</td>
									<td class="s_tab_view02"><%=userInfo.getUserId()%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
<%
	if (USERTYPE.equals("M")) {
%>
								<tr>
									<td class="s_tab_view01">계층구분</td>
									<td class="s_tab_view02"><%=userInfo.getDeptSoname()%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
<%
	}
%>
								<tr>
									<td class="s_tab_view01">전화번호</td>
									<td class="s_tab_view02"><%=userInfo.getPhoneHome()%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">휴대전화</td>
									<td class="s_tab_view02"><%=userInfo.getPhoneMobile()%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>

								<tr>
									<td class="s_tab_view01">이메일</td>
									<td class="s_tab_view02"><%=userInfo.getEmail()%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">주소</td>
									<td class="s_tab_view02">
										(<%=userInfo.getPostCode()%>) <br>
										<%=userInfo.getAddress()%>
									</td>
								</tr>
<%
	if (USERTYPE.equals("M")) {
%>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">가입일</td>
									<td class="s_tab_view02"><%=DateTimeUtil.getDateType(1,userInfo.getRegDate())%></td>
								</tr>
<%
	}
%>
								<tr class="s_tab05">
									<td colspan="2"></td>
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





<%@include file="../common/popup_footer.jsp" %>