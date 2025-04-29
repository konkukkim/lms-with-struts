<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.servlet.ServletOutputStream"%>

<%@include file="../common/popup_header.jsp" %>
<Script Language="javascript">
<!--
	function form_chk(f) {
		if(!validate(f)) return;
	}

	function goSearchIDPW() {
		opener.location = "<%=CONTEXTPATH%>/User.cmd?cmd=searchIdPwShow";
		window.close();
	}
//-->
</script>
	<table cellpadding="20" cellspacing="20">
		<tr>
			<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/loginpop_tit.gif" alt="LOGIN"></td>
		</tr>
<!-- form start -->
<form name="Login" method="post" action="<%=CONTEXTPATH%>/Main.cmd?cmd=setLogin&pGubun=popup" OnSubmit="form_chk(Login);">
		<tr>
			<td>
				<table cellpadding="0" cellspacing="0" align="center">
					<tr>
						<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/loginpop_id.gif" width="44" height="11" border=0 alt="">&nbsp;&nbsp;</td>
						<td ><input type="text" class="login" name="userId" style="width:150px;" value="" notNull dispName="아이디" tabindex="1" autocomplete="off"/></td>
						<td rowspan="2">&nbsp;&nbsp;<input type="image" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/loginpop_ok.gif" width="54" height="45" alt="확인" style="border:0" align="absmiddle"></td>
					</tr>
					<tr>
						<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/loginpop_pw.gif" width="44" height="11" border=0 alt="">&nbsp;&nbsp;</td>
						<td><input type="password" class="login" name="userPw" style="width:150px;" notNull dispName="비밀번호" tabindex="2" autocomplete="off"/></td>
					</tr>
					<tr>
						<td colspan="3" style="padding-top:8px;" class="lgpop_text01">* 아이디와 비밀번호를 입력해 주세요.</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/loginpop_line.gif" width="342" height="1"></td>
		</tr>
		<tr>
			<td align="center"><a href="javascript:goSearchIDPW()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/btn_sch_id.gif" width="80" height="21" border=0 alt="아이디찾기"></a>
			<a href="javascript:goSearchIDPW()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/btn_sch_pw.gif" width="89" height="21" border=0 alt="비밀번호찾기" hspace="3"></a>
			<!--a href="<%=CONTEXTPATH %>/User.cmd?cmd=userAgreeShow&pMode=Home"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/btn_join.gif" width="65" height="21" border=0 alt="회원가입하기"></a--></td>
		</tr>
		<tr>
			<td align="right"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/loginpop_close.gif" style="cursor:hand" onClick="window.close()"></td>
		</tr>
</form>
<!-- form end -->
	</table>
<%@include file="../common/popup_footer.jsp" %>
<Script Language="javascript">
<!--
setTimeout('document.Login.userId.focus()',500);
//-->
</script>