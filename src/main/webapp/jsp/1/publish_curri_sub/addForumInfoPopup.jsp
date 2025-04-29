<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.courseforum.dao.CourseForumInfoDAO, com.edutrack.courseforum.dto.CourseForumInfoDTO"%>
<%@include file="../common/popup_header.jsp" %>
<%
	String	pCurriCode		=	StringUtil.nvl(model.get("pCurriCode"));
	int		pCurriYear		=	StringUtil.nvl(model.get("pCurriYear"), 0);
	int		pCurriTerm		=	StringUtil.nvl(model.get("pCurriTerm"), 0);
	String	pCourseId		=	StringUtil.nvl(model.get("pCourseId"));
	int		pForumId		=	StringUtil.nvl(model.get("pForumId"), 0);
	String	pRegMode		=	StringUtil.nvl(model.get("pRegMode"));
	
	CourseForumInfoDTO	forumDto	=	(CourseForumInfoDTO)model.get("CourseForumInfoDTO");
	//String	pOpenYn			=	StringUtil.nvl(forumDto.getOpenYn());
	String	pOpenYn		=	"";
%>
<script language="javascript">
<!--
	function SubmitCheck() {
		var f = document.f;
		
		f.submit();
	}
	
	function goCancel() {
		window.close();
	}
//-->
</script>
	<table width="100%" height="100%" border="0">
		<tr> 
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">토론방 개설</font></td>
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
<form name="f" method="post" action="<%=CONTEXTPATH%>/PublishCurriSub.cmd?cmd=ForumInfoRegist">
<input type="hidden" name="pCurriCode" value="<%=pCurriCode%>">
<input type="hidden" name="pCurriYear" value="<%=pCurriYear%>">
<input type="hidden" name="pCurriTerm" value="<%=pCurriTerm%>">
<input type="hidden" name="pCourseId" value="<%=pCourseId%>">
<input type="hidden" name="pForumId" value="<%=pForumId%>">
<input type="hidden" name="pRegMode" value="<%=pRegMode%>">
								<tr class="s_tab01">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td class="s_tab_view01" width="30%">토론 제목</td>
									<td class="s_tab_view02"><input type="text" name="pSubject" value="<%=StringUtil.nvl(forumDto.getSubject())%>" isNull="N" dispName="토론 제목" lenCheck="15" style="width:100%;"></td>
								</tr>
								<tr> 
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">토론 설명</td>
									<td class="s_tab_view03"><textarea name="pContents" isNull="Y" dispName="토론 설명" style="width:100%;height:150;"><%=StringUtil.nvl(forumDto.getContents())%></textarea></td>
								</tr>
								<tr> 
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">토론방 공개여부</td>
									<td class="s_tab_view02">
										공개 <input type="radio" name="pOpenYn" value="Y" <%if(pOpenYn.equals("") || pOpenYn.equals("Y")) {%>checked<%}%>>
										비공개 <input type="radio" name="pOpenYn" value="N" <%if(pOpenYn.equals("N")) {%>checked<%}%>>
									</td>
								</tr>
								<tr class="s_tab05">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td colspan="2" align="center" valign="top" class="pop_btn">
<script language=javascript>Button3("등록", "SubmitCheck()", "");</script>
&nbsp;<script language=javascript>Button3("취소", "goCancel()", "");</script>
									</td>
								</tr>
</form>
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
				src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif" onClick="window.close()" style="cursor:hand;">
			</td>
		</tr>
	</table>

<%@include file="../common/popup_footer.jsp" %>