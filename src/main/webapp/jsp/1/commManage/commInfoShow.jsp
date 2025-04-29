<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.community.dto.CommInfoDTO"%>
<%@include file="../common/popup_header.jsp" %>
<script language="javascript">
	function closeWin(){
		window.close();
	}
</script>

<%  String pGubun = (String)model.get("pGubun"); %>
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">동아리 정보보기</font></td>
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
									<td class="s_tab_view01">동아리 이름</td>
									<td class="s_tab_view02"><font class="c_text02"><b><%=StringUtil.nvl((String)model.get("comm_name"))%></b></font></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">동아리 분류</td>
									<td class="s_tab_view02"><%=StringUtil.nvl((String)model.get("cate_name"))%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">동아리 소개</td>
									<td class="s_tab_view03"><%=StringUtil.getHtmlContents((String)model.get("comm_synopsis"))%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">동아리 검색어</td>
									<td class="s_tab_view02"><%=StringUtil.nvl((String)model.get("keyword"))%></td>
								</tr>
<%  if(pGubun.equals("")){%>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">동아리<br>
                                        가입처리 방법</td>
									<td class="s_tab_view02"><%
                                        if(StringUtil.nvl((String)model.get("regist_type")).equals("1"))
                                            out.println("&nbsp;자동승인");
                                        else if(StringUtil.nvl((String)model.get("regist_type")).equals("2"))
                                            out.println("&nbsp;관리자승인");
                                        else
                                            out.println("&nbsp;알수없음");
                                     %></td>
								</tr>
<%  }%>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><a href="javascript:window.close()"><img
				src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif"></a></td>
		</tr>
	</table>
<%@include file="../common/popup_footer.jsp" %>
