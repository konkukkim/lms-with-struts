<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.user.dto.UsersDTO"%>
<%@include file="../common/popup_header.jsp" %>
<%
	String pOp  = (String)model.get("pOp");
	String pSearch  = (String)model.get("pSearch");
	String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE;
%>
<script language="javascript">
	function goSearch() {
		var f =	document.search;
		if(!validate(f))			//폼체크 후 이상 있으면
			return;

        f.action = "<%=CONTEXTPATH%>/CommInvite.cmd?cmd=inviteUserList";
		f.submit();
	}

	function goInvite(){
  		var f = document.search;
		if(parseInt(f.checkNum.value) < 1) {
	    	alert("초대할 사용자가 없습니다.");
	    	return;
		}

  		var checkVal = getChkBoxByValue(f, "inviteId", "");
		if(checkVal == "") {
	    	alert("초대할 사용자를 선택해 주세요.");
	    	return;
		}

		f.action = "<%=CONTEXTPATH%>/CommInvite.cmd?cmd=inviteUserRegist";
		f.submit();
	}

	function checkAll(){
    	var f = document.search;
    	if(f.checkYn.value == "Y") {
        	f.checkYn.value = "N";
    		setChkboxAll(f, "inviteId", false);
    	}else{
        	f.checkYn.value = "Y";
    		setChkboxAll(f, "inviteId", true);
    	}
	}
</script>
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">동아리 초대하기</font></td>
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
								<tr>
									<td height="25" align="center" colspan="5"><font class="c_text02">※ 검색어를 입력한 후 검색버튼을 클릭하세요.</td>
								</tr>
								<tr class="s_tab01">
									<td colspan="5"></td>
								</tr>
								<tr>
									<td class="s_tab_view01" colspan="5">
										<select name="pOp" class="input_member ">
											<option value="user_name" <%if(pOp.equals("") || pOp.equals("user_name")){ %> selected<%}%>>이름</option>
											<option value="user_id" <%if(pOp.equals("user_id")){ %> selected<%}%>>사용자ID</option>
										</select>
										<input name="pSearch" class="input_member" size="30" value="<%=pSearch%>">
										<a href="javascript:goSearch();"><img src="<%=img_path%>/button_img/btn_search.gif" align="absmiddle" border="0"></a>
									</td>
								</tr>
								<tr class="s_tab01">
									<td colspan="5"></td>
								</tr>
								<tr>
									<td height="20" colspan="5"></td>
								</tr>
<%
	int totCnt = 0;
	if(!pOp.equals("") && !pSearch.equals("")){

		ArrayList list = (ArrayList)model.get("userList");
		UsersDTO users = null;
		totCnt = list.size();
%>
								<tr class="s_tab01">
									<td colspan="5"></td>
								</tr>
								<tr class="s_tab02">
									<td width="40%">사용자 아이디</td>
									<td width="1" class="s_tablien"></td>
									<td width="39%">이름</td>
									<td width="1" class="s_tablien"></td>
									<td width="20%">선택</td>
								</tr>
								<tr class="s_tab03">
									<td colspan="5"></td>
								</tr>
<%
		String userType = "";
		if(totCnt > 0) {
			for(int i=0; i < totCnt; i++) {
				users = (UsersDTO)list.get(i);
				if(i >= 1) {
%>
								<tr class="s_tab03">
									<td colspan="5"></td>
								</tr>
<%				} %>
								<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
									<td class="s_tab04_cen"><%=users.getUserId()%></td>
									<td></td>
									<td class="s_tab04_cen"><%=users.getUserName()%></td>
									<td></td>
									<td class="s_tab04_cen"><input type="checkbox" name="inviteId" value="<%=users.getUserId()%>/<%=users.getUserName()%>" class="solid0"></td>
								</tr>
<%
			 } //end for
		} else {
%>
								<tr>
									<td class="s_tab04_cen" colspan="5">검색된 사용자가 없습니다.</td>
								</tr>
<%
		} //end if
%>
								<tr class="s_tab05">
									<td colspan="5"></td>
								</tr>
								<tr>
									<td height="20" colspan="5"></td>
								</tr>
<%
	}
%>
<input type="hidden" name="checkNum" value="<%=totCnt%>">
								<tr class="s_tab01">
									<td colspan="5"></td>
								</tr>
								<tr>
									<td colspan="5" align="center" class="s_tab_view01"><B>초대메시지</B></td>
								</tr>
								<tr>
									<td colspan="5" height="1" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td colspan="5" class="s_tab_view03"><textarea name="pContents" style="width:100%;height:50;"></textarea></td>
								</tr>
								<tr class="s_tab05">
									<td colspan="5"></td>
								</tr>
								<tr>
									<td colspan="5" align="center" valign="top" class="pop_btn">
										<script language=javascript>Button3("보내기", "goInvite()", "");</script>
									</td>
								</tr>
							</table>
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

