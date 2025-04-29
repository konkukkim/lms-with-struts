<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../../common/popup_header.jsp" %>	
<%@ page import ="com.edutrack.user.action.ParseParam"%> 
<%
    String userType = (String)model.get("userType");
    ArrayList result = (ArrayList)model.get("result");
    String jobCnt = (String)model.get("jobCnt");
    String succCnt = (String)model.get("succCnt");
    String errorCnt = (String)model.get("errorCnt");
    String LineBgColor = "#91C694";
%>
<script language="javascript">
    function Confirm(){
       opener.autoReload();
       window.close();
    }
</script>
	<table width="100%" height="100%" border="0">
		<tr> 
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">일괄등록결과</font></td>
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
								<tr class="s_tab03">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td class="s_tab_view01">총 요청 건수</td>
									<td class="s_tab_view02"><font class="c_text02"><b><%=jobCnt%></b></font></td>
								</tr>
								<tr> 
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">성공 건수</td>
									<td class="s_tab_view02"><font class="c_text02"><b><%=succCnt%></b></font></td>
								</tr>
								<tr> 
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">실패 건수</td>
									<td class="s_tab_view02"><font class="c_text02"><b><%=errorCnt%></b></font></td>
								</tr>
								<tr class="s_tab03">
									<td colspan="2"></td>
								</tr>

							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr class="s_tab01">
									<td colspan="5"></td>
								</tr>
								<tr class="s_tab02">
									<td width="60">라인번호</td>
									<td class="s_tablien"></td>
									<td width="">사용자ID</td>
									<td class="s_tablien"></td>
									<td width="250">오류내용</td>
								</tr>
								<tr class="s_tab03">
									<td colspan="5"></td>
								</tr>
<%	
	if(result.size() > 0){
		ParseParam param = null;
		for(int i =0; i < result.size();i++) {
			param = (ParseParam)result.get(i);
			
			if(i > 0) {
%>
								<tr class="s_tab03">
									<td colspan="5"></td>
								</tr>
<%			}	%>
								<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
									<td class="s_tab04_cen"><%=param.getLineNo()%></td>
									<td></td>
									<td class="s_tab04_cen"><%=param.getUserId()%></td>
									<td></td>
									<td class="s_tab04_cen"><%=param.getErrorType()%></td>
								</tr>
<%		}
	}	%>
								<tr class="s_tab05">
									<td colspan="5"></td>
								</tr>
								<tr>
									<td colspan="5" align="center" valign="top" class="pop_btn">
<script language=javascript>Button3("확인", "Confirm()", "");</script>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"></td>
		</tr>
	</table>

<%@include file="../../common/popup_footer.jsp" %>