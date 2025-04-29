<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.popupnotice.dto.PopupNoticeDTO,com.edutrack.common.dto.EditorParam"%>
<%@include file="../common/popup_header.jsp" %>
<%
	Map data = (Map)request.getAttribute("MODEL");
	PopupNoticeDTO popupNoticeView = (PopupNoticeDTO)data.get("popupnoticeShow");

	int seqNo = popupNoticeView.getSeqNo();
	String subject = popupNoticeView.getSubject();
	String contents = popupNoticeView.getContents();
%>
<script language="JavaScript">
	function closeWin()
	{ 
		var todayDate = new Date();
		todayDate.setDate( todayDate.getDate() + 1 );
		document.cookie = "popup=noshow; path=/; expires="+todayDate.toGMTString()+";";
		self.close();
	}
</script>
	<table width="100%" height="100%" border="0">
		<form>
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">공지사항</font></td>
			<td class="pop_topright"></td>
		</tr>
		<tr>
			<td class="pop_midleft"></td>
			<td valign="top" class="pop_form">
				<!-- 내용 -->
				<table width="90%" height="100%" align="center">
					<tr height="10%">
						<td align="center" valign="middle">
							<b>제 목 : <%=subject%></b>
						</td>
					</tr>
					<tr height="70%">
						<td align="left" valign="middle">
							<%=StringUtil.ReplaceAll(contents,"&quot;","\"")%>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><input type="checkbox" name="Notice" OnClick="javascript:closeWin();"  style="border:0">오늘 하루 이 창 닫기 </td>
		</tr>
	</table>
</form>
<%@include file="../common/popup_footer.jsp" %>

