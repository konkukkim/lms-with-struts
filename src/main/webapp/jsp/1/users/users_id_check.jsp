<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/popup_header.jsp" %>
<%	String pNewId = (String)model.get("pNEW_ID");
    String pGubun = (String)model.get("pGUBUN");
    String errorYn = "Ignore";
    int idCnt = Integer.parseInt((String)model.get("idCnt"));
    String LineBgColor = "#40B389";	%>

<Script Language=javascript>
//--	사용자본인이 회원가입(1), 관리자 회원가입(2)
	function goOk(str) {
		var f			=	document.forms[0];
		var	gubn		=	f.pGUBUN.value;
		if (str == 'Yes') {

			opener.document.Regist.pUserId.value	=	"<%=pNewId%>";
			opener.document.Regist.pUserPass1.focus();

			self.close();
		}else {
			var yes = confirm("아이디 중복 검사를 완료하지 못했습니다\n\n창을 닫으시겠습니까?");
			if (yes == true)
				self.close();
			else false;
		}
	}

	function formCheck(){
	  var form = document.Search;


	  if(!dataCheck(form.pNEW_ID.value)){
		    alert("사용자 아이디에 특수문자를 사용할 수 없습니다.");
		    form.pNEW_ID.focus();
		    form.pNEW_ID.select();
		    return;
		}

	  if(!validate(form)) return;

	  form.submit();
	}

	function checkNum(val){
	 for (var i = 0; i < val.length; i ++)
	 {
	  if (val.charAt(i) >= 0 && val.charAt(i) <= 9) continue;
	  else  return false;
	 }

	 return true;
	}
</Script>

<table width="100%" height="100%" border="0">
<!-- form start -->
<form name=Search method=post action="<%=CONTEXTPATH%>/User.cmd?cmd=userIdCheck">
<input type=hidden name="pGUBUN" value="<%=pGubun%>">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="../img/1/common/blet05.gif" align="absmiddle"><font class="pop_tit">ID중복검색</font></td>
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
									<td class="s_tab_view03" colspan="2">
										<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/pop_idcheck02.gif" width="3" height="10" align="absmiddle">&nbsp;사용한 아이디(ID)는 영문과 숫자의 조합으로 4자 이상 15자 이하로 만들 수 있습니다.<br>
										<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/pop_idcheck02.gif" width="3" height="10" align="absmiddle">&nbsp;아이디는 특수문자를 사용할 수 없습니다.<br>
										<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/pop_idcheck02.gif" width="3" height="10" align="absmiddle">&nbsp;숫자만으로도 아이디를 만들 수 있습니다.<br>
									</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
<%	if (!pNewId.equals("")) {
		if (idCnt > 0) {
			errorYn = "No";	%>
								<tr>
									<td class="s_tab_view03" colspan="2" align="center">
										<strong><font color="#333399">[<%=pNewId%>]</font></strong><font color="#666699">
										<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/pop_idcheck02.gif" width="3" height="10" align="absmiddle">&nbsp;이미 사용하는 아이디 입니다.<br>
									</td>

								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
<%		} else {
			errorYn = "Yes";	%>
								<tr>
									<td class="s_tab_view03" colspan="2" align="center">
										<strong><font color="#333399">[<%=pNewId%>]</font></strong><font color="#666699">는
                    					<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/pop_idcheck02.gif" width="3" height="10" align="absmiddle">&nbsp;사용할 수 있는 아이디 입니다.<br>
									</td>

								</tr>
								<tr>
									<td class="s_tab_view03" colspan="2">
                    					<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/pop_idcheck02.gif" width="3" height="10" align="absmiddle">&nbsp;'확 인' 버튼을 눌러 아이디 중복 검사를 완료 하십시요!
									</td>

								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
<%		}
	} else {
		errorYn = "Ignore";
	}	%>
								<tr>
									<td class="s_tab_view02" colspan="2" align="center">
										<input type="text" name=pNEW_ID size=20 dispName="아이디" notNull lenMCheck=4 lenCheck=15  class="solid1">
                   						<input type="image" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/pop_idcheck03.gif" width="60" height="17" align="absmiddle" class="solid0">
                   						<br>
									</td>

								</tr>

								<tr class="s_tab05">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td colspan="2" align="center" valign="top" class="pop_btn">
<script language=javascript>Button3("확 인", "goOk('<%=errorYn%>')", "");</script>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><a
			href="javascript:window.close()" onMouseOver="window.status='창을 닫습니다.';return true" onMouseOut="window.status='';return true"><img
			src="<%=CONTEXTPATH%>/img/1/button_img/btn_popclose.gif"></td>
		</tr>


</form>
<!-- form end -->
</table>

<Script Language="JavaScript">
document.Search.pNEW_ID.focus();
</script>
<%@include file="../common/popup_footer.jsp" %>