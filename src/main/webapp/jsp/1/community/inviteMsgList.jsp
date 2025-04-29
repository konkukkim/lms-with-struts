<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.community.dto.CommInviteDTO"%>
<%@include file="../common/popup_header.jsp" %>
<%
	ArrayList msgList = (ArrayList)model.get("inviteMsgList");
	String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE;
%>
<script language="javascript">
	function goCommunity(commid){
		var doc = "Community.cmd?cmd=goCommunity&pCommId="+commid+"&MENUNO=0";
		opener.document.location= doc;
	}

    function delInviteMsg(seqno){
        var doc = "CommInvite.cmd?cmd=inviteMsgDelete&pSeqNo="+seqno+"&MENUNO=0";
        document.location= doc;
    }

    function delInviteAllMsg(){
        var doc = "CommInvite.cmd?cmd=inviteMsgDelete&MENUNO=0";
        document.location= doc;
    }
</script>
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="../img/1/common/blet05.gif" align="absmiddle"><font class="pop_tit">동아리 초대장</font></td>
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
<form name="search" method="post">
<input type="hidden" name="checkYn" value="N">

								<tr class="s_tab01">
									<td colspan="2"></td>
								</tr>
<%
	CommInviteDTO msgInfo = null;
	for(int i =0; i < msgList.size(); i++) {
		msgInfo = (CommInviteDTO)msgList.get(i);
%>

								<tr>
									<td colspan="2" class="s_tab_view03"><%=msgInfo.getSubject()%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td colspan="2" class="s_tab_view03"><%=StringUtil.getHtmlContents(msgInfo.getContents())%></td>
								</tr>

								<tr class="s_tab05">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td colspan="2" align="right" valign="top" class="pop_btn">
<script language=javascript>Button3("둘러보기", "goCommunity('<%=msgInfo.getCommId()%>')", "");</script>
&nbsp;<script language=javascript>Button3("삭제", "delInviteMsg('<%=msgInfo.getSeqNo()%>')", "");</script>
									</td>
								</tr>
<%	}	%>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center" valign="top" class="pop_btn">
<script language=javascript>Button3("전체삭제", "delInviteAllMsg()", "");</script>
						</td>
					</tr>
</form>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><a href="javascript:window.close();"><img src="../img/1/button_img/btn_popclose.gif"></a></td>
		</tr>
	</table>
<%@include file="../common/popup_footer.jsp" %>