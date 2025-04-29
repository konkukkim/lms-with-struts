<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/popup_header.jsp" %>
<%
   RowSet userList = (RowSet)model.get("userList");
   String pCommId = (String)model.get("pCommId");
   String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE;
%>
<script language="javascript">
	function goChange(){
	    var f = document.f;

	    if(f.pCommUserId.value == ""){
	       alert("시샵이 될 멤버를 선택해 주세요.");
	       f.pCommUserId.focus();
	       return;
	    }

	    f.submit();
	}
</script>
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">시샵변경</font></td>
			<td class="pop_topright"></td>
		</tr>
		<tr>
			<td class="pop_midleft"></td>
			<td valign="top" class="pop_form">
				<!-- 내용 -->
				<table width="90%" height="100%" align="center">
<form name=f method=post action="<%=CONTEXTPATH%>/CommManage.cmd?cmd=changeMaster">
<input type="hidden" name="pCommId" value="<%=pCommId%>">
					<tr height="97%">
						<td align="center" valign="top">
<br><br>
							<select name="pCommUserId">
								<option value="">------- 멤버선택 -------</option>
<%
	if(userList != null){

		while(userList.next()){
%>
						 		<option value="<%=userList.getString("user_id")%>"><%=userList.getString("user_name")%>(<%=userList.getString("user_id")%>)</option>
<%
		}
	}
%>
					 		</select>
<br><br><br>
						</td>
					</tr>
					<tr>
						<td align="center" valign="top" class="pop_btn">
<script language=javascript>Button3("수정", "goChange()", "");</script>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><a href="javascript:window.close();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif"></a></td>
		</tr>
	</table>
<%@include file="../common/popup_footer.jsp" %>