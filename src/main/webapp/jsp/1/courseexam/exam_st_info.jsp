<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/popup_header.jsp" %>
<%@ page import ="com.edutrack.courseexam.dto.ExamInfoDTO,com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam"%>
<%
    ExamInfoDTO examInfo = (ExamInfoDTO)model.get("examInfo");
    String pCourseId = (String)model.get("pCourseId");
    String pExamId = (String)model.get("pExamId");
    String[] width = new String[]{"100","300"};
    String LineBgColor = "#40B389";
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
								<tr class="s_tab01">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td class="s_tab_view01">시험명</td>
									<td class="s_tab_view02"><font class="c_text02"><b><%=examInfo.getSubject()%></b></font></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">시험기간</td>
									<td class="s_tab_view02"><%=DateTimeUtil.getDateType(0,examInfo.getStartDate())%> ~ <%=DateTimeUtil.getDateType(0,examInfo.getEndDate())%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">시험</td>
									<td class="s_tab_view02"><%=examInfo.getRunTime()%>분</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">결과확인절차</td>
									<td class="s_tab_view02"><%=DateTimeUtil.getDateType(0,examInfo.getResultDate())%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">문제지유형</td>
									<td class="s_tab_view02"><%=(examInfo.getViewStyle().equals("L") ? "전체문제" : "한문제씩" ) %></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">성적적용여부</td>
									<td class="s_tab_view02"><%=(examInfo.getExamType().equals("N") ? "미적용" : "적용" ) %></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">시간체크여부</td>
									<td class="s_tab_view02"><%=(examInfo.getFlagTime().equals("N")? "시간체크안함" : "시간체크" ) %></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">시험설명</td>
									<td class="s_tab_view03"><textarea name="comment" style="width:100%;height:50;" ReadOnly ><%=examInfo.getComment()%></textarea></td>
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