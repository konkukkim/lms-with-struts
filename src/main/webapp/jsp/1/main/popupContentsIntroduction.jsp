<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ include file="../common/popup_header.jsp" %>
<%@ page import="com.edutrack.currisub.dto.CurriContentsDTO" %>
<%
	CurriContentsDTO	contentsDto	=	(CurriContentsDTO)model.get("ContentsDTO");
%>
	<table width="100%" height="100%" border="0">
		<tr> 
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">정규강좌 정보</font></td>
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
									<td class="s_tab_view01" width="100">과 정</td>
									<td class="s_tab_view02"><font class="c_text02"><b><%=contentsDto.getCurriName()%></b></font></td>
								</tr>
								<tr> 
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
<%
	int	contentsCnt	=	StringUtil.nvl(contentsDto.getContentsOrder() , 0);
%>
								<tr>
									<td class="s_tab_view01">강의 주차</td>
									<td class="s_tab_view02"><%=contentsCnt%></td>
								</tr>
								<tr> 
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">강의 교수</td>
									<td class="s_tab_view02"><%=contentsDto.getProfName()%></td>
								</tr>
								<tr> 
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">과정 정보</td>
									<td class="s_tab_view02"><%=contentsDto.getCurriInfo()%></td>
								</tr>
								<tr> 
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
<%
	/*
	String	filePath	=	StringUtil.nvl(contentsDto.getFilePath());
	int		fileIndex01	=	filePath.lastIndexOf(".")-1;
	int		fileIndex02	=	filePath.lastIndexOf("/")+1;
	*/
	String	book		=	"";
	//if(fileIndex01 > 0 && fileIndex02 > 0) filePath.substring(fileIndex01, fileIndex02);
%>
								<tr>
									<td class="s_tab_view01">참고 교재</td>
									<td class="s_tab_view02"><%=book%></td>
								</tr>
								<tr> 
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">학 점</td>
									<td class="s_tab_view02"><%=contentsDto.getCredit()%></td>
								</tr>
								<tr> 
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">출석 일자</td>
									<td class="s_tab_view02"><%=contentsDto.getStartDate()%> ~ <%=contentsDto.getEndDate()%></td>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif" onClick="window.close()" style="cursor:hand"></td>
		</tr>
	</table>
<%@include file="../common/popup_footer.jsp" %>