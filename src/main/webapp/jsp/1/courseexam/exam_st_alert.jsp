<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map,java.util.ArrayList"%>
<%@include file="../common/popup_header.jsp" %>
<%
     String remainTime = (String)model.get("remainTime");
%>
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">시험정보</font></td>
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
								<tr>
									<td class="s_tab_view03">
										남은 시간 확인 바랍니다.<br>
										시험 종료 시간이 <font color=red size=2><%=remainTime%></font>분 남았습니다.<br>
										답안 작성을 마무리하고, 답안지를 제출하시기 바랍니다.<br>
										※주의 : 시험 종료 시간이 지나면 현재 푼 문제까지만 저장됩니다.<br>
										※주의 : 현재 화면(팝업창)과 관계없이 시간은 계속 체크됩니다.<br>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><img
				src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif" onClick="window.close()" style="cursor:hand;"></td>
		</tr>
	</table>