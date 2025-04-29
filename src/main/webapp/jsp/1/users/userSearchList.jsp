<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.user.dto.UsersDTO"%>
<%@include file="../common/popup_header.jsp" %>
<%
	String pOp		=	StringUtil.nvl((String)model.get("pOp"), "");
	String pSearch	=	StringUtil.nvl((String)model.get("pSearch"), "");
	String goUrl	=	StringUtil.nvl(request.getParameter("goUrl"), "");

	goUrl  = StringUtil.nvl(goUrl, CONTEXTPATH + "/User.cmd?cmd=searchUserList");

%>

<Script Language="javascript">
top.resizeTo(530,360)
top.focus();
	function goSearch() {
		var f =	document.search;
		if(!validate(f))			//폼체크 후 이상 있으면
			return;

		f.action="<%=goUrl %>";
		f.submit();
	}

	function goInsertId(id){
		opener.document.Input.pId.value	= id;
		self.close();
	}

</Script>
<form name="search" method="post" action="<%=goUrl %>">
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">사용자검색</font></td>
			<td class="pop_topright"></td>
		</tr>
		<tr>
			<td class="pop_midleft"></td>
			<td valign="top" class="pop_form">
				<!-- 내용 -->
				<table width="90%" height="100%" align="center">
					<tr height="97%">
						<td align="center" valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr class="s_tab01">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td class="s_tab_view03" align="center">* 검색어를 입력한 후 검색버튼을 클릭하세요.</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">
										<select name="pOp">
											<option value="user_name">이름</option>
											<option value="user_id">사용자 ID</option>
										</select>
										<input type="text" name="pSearch" size="14" lenMCheck="2" dispName="검색어" notNull  align="absmiddle">
										<a href="javascript:goSearch();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" border="0" align="absmiddle"></a>
									</td>

								</tr>
								<tr class="s_tab05">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td height="10"></td>
								</tr>
							</table>
<%
	if(!pOp.equals("") && !pSearch.equals("")){

		ArrayList list = (ArrayList)model.get("userSearchList");
		UsersDTO users = null;

%>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr class="s_tab01">
									<td colspan="5"></td>
								</tr>
								<tr class="s_tab02">
									<td width="40%">사용자ID</td>
									<td class="s_tablien"></td>
									<td width="29%">이름</td>
									<td class="s_tablien"></td>
									<td width="30%">사용자구분</td>

								</tr>
								<tr class="s_tab03">
									<td colspan="5"></td>
								</tr>
<%
		String userType = "";
		if(list.size() > 0) {
			for(int i=0; i < list.size(); i++){
				users = (UsersDTO)list.get(i);

				if(users.getUserType().equals("M")) userType = "관리자";
				else if(users.getUserType().equals("C")) userType = "과정운영자";
				else if(users.getUserType().equals("P")) userType = "교수자";
				else if(users.getUserType().equals("S")) userType = "학습자";

			if(i >= 1) {
%>
								<tr class="s_tab03">
									<td colspan="5"></td>
								</tr>
<%			} %>
								<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
									<td class="s_tab04_cen"><a href="javascript:goInsertId('<%=users.getUserId()%>')"><%=users.getUserId()%></a></td>
									<td></td>
									<td class="s_tab04_cen"><%=users.getUserName()%></td>
									<td></td>
									<td class="s_tab04_cen"><%=userType%></td>
								</tr>
<%
			 }//end for
		}else{
%>
								<tr>
									<td class="s_tab04_cen" colspan="5">검색된 사용자가 없습니다.</td>
								</tr>
<%
		}//end if
%>
								<tr class="s_tab05">
									<td colspan="5"></td>
								</tr>
							</table>
<%
	}
%>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose">
				<a href="javascript:window.close();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif"></a></td>
		</tr>
	</table>

<%-- 시스템관리>소속코드관리>사용자검색 에서 팝업 오픈시 단체별루 조회할 경우 필요한 파라미터.. --%>
<input type=hidden name=pDeptDaeCode value="<%=request.getParameter("pDeptDaeCode") %>">
<input type=hidden name=pDeptSoCode  value="<%=request.getParameter("pDeptSoCode") %>">
<input type=hidden name=pUserType    value="<%=request.getParameter("pUserType") %>">
<input type=hidden name=goUrl        value="<%=goUrl%>">
</form>

<%@include file="../common/popup_footer.jsp" %>