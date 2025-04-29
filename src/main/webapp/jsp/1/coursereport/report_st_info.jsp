<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/popup_header.jsp" %>
<%@ page import ="com.edutrack.coursereport.dto.ReportInfoDTO,com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam"%>
<%
    ReportInfoDTO reportInfo = (ReportInfoDTO)model.get("reportInfo");
    String pCourseId = (String)model.get("pCourseId");
    String pReportId = (String)model.get("pReportId");
    String[] width = new String[]{"100","300"};
    String LineBgColor = "#40B389";
%>
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">과제정보</font></td>
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
									<td class="s_tab_view01">과제명</td>
									<td class="s_tab_view02"><font class="c_text02"><b><%=reportInfo.getSubject()%></b></font></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">과제기간</td>
									<td class="s_tab_view02"><%=DateTimeUtil.getDateType(0,reportInfo.getStartDate())%> ~ <%=DateTimeUtil.getDateType(0,reportInfo.getEndDate())%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>

								<tr>
									<td class="s_tab_view01">결과확인일자</td>
									<td class="s_tab_view02"><%=DateTimeUtil.getDateType(0,reportInfo.getResultDate())%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">문제지유형</td>
									<td class="s_tab_view02"><%=(reportInfo.getViewStyle().equals("L") ? "전체문제" : "한문제씩" ) %></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">성적적용여부</td>
									<td class="s_tab_view02"><%=(reportInfo.getReportType().equals("N") ? "미적용" : "적용" ) %></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>

								<tr>
									<td class="s_tab_view01">과제설명</td>
									<td class="s_tab_view03"><textarea name="comment" style="width:100%;height:50;" ReadOnly><%=reportInfo.getReportComment()%></textarea></td>
								</tr>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><a
			href="javascript:window.close();"><img
			src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif"></a></td>
		</tr>
	</table>
<%@include file="../common/popup_footer.jsp" %>