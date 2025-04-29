<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.user.dto.UsersDTO, com.edutrack.common.dto.EditorParam,com.edutrack.common.dto.CodeParam"%>
<%@include file="../common/popup_header.jsp" %>
<%
	String pMessageKb = (String)model.get("pMessageKb");
	String pMessageWhere = (String)model.get("pMessageWhere");
	String pMessageWhereSub = (String)model.get("pMessageWhereSub");
	String pResultCode1 = (String)model.get("pResultCode1");	//	시험,,과제..토론일경우...해당 키값..
	String pResultCode2 = (String)model.get("pResultCode2");	//	시험,,과제..토론일경우...해당 키값..
	String pResultCode3 = (String)model.get("pResultCode3");	//	시험,,과제..토론일경우...해당 키값..
	String pKb = (String)model.get("pKb");

	String 	pTotCnts = "";
	int		pTotCnt	 = 0;
	ArrayList chkList	=	null;

	if(pMessageKb.equals("S")){

		chkList = (ArrayList)model.get("pCHK");
		pTotCnt = chkList.size();
	}else{

		pTotCnts = (String)model.get("totCnt");
		if(!pTotCnts.equals("")){
			pTotCnt = Integer.parseInt(pTotCnts);
		}

		if(pMessageWhere.equals("Lecture")){
			if(!pMessageWhereSub.equals("Student")){	// 수강생 조회가 아닌경우..

			}
		}
	}
	UsersDTO usersDto = null;

%>
<script language="javascript">
	function submitCheck()
	{
		var f = document.Input;

		if(window.VBN_prepareSubmit != null){
			if(!VBN_prepareSubmit())
				return false;
		}
<%
	if(pTotCnt == 1) {
%>
		var file = f.pFILE_NEW1.value;

		f.pFILE_NEW1_ori.value = f.pFILE_NEW1.value;
		while (file.indexOf("\\") != -1) {
			file = file.slice(file.indexOf("\\") + 1);
		}

		if(file.indexOf(".") != -1){
			file = file.substring(0, file.lastIndexOf("."));
		}
		f.action += "&pRealFileName="+file;
<%
	}
%>

		if(isEmpty(f.pSubject.value))
		{
			alert("제목을 입력하세요");
			f.pSubject.focus();
			return false;
		}

		if(isEmpty(f.pContents.value))
		{
			alert("내용을 입력하세요");
			//f.pContents.focus();
			return false;
		}


		//return true;
		f.submit();

	}


	function goUserSearch() {
		window.open("<%=CONTEXTPATH%>/User.cmd?cmd=searchUserList", "usersearch","toolbar=no,location=no, width=500, height=300,top=50, left=50, directories=no,status=no,menubar=no,scrollbars=yes,resizable=no");
	}

	function change_target() {

		var f				=	document.Input;
		var	pUtype			=	f.pUtype.options[f.pUtype.selectedIndex].value;
		document.location.href="<%=CONTEXTPATH%>/Message.cmd?cmd=messageWrite&pUtype="+pUtype;
	}
</script>
<!--  onSubmit="return submitCheck()" -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/Mail.cmd?cmd=mailSend" enctype="multipart/form-data">
<%
	if(pMessageKb.equals("S")){
		for(int i=0;i < pTotCnt;i++){
			usersDto = (UsersDTO)chkList.get(i);
%>
			<input type="hidden" name="pCHK" value="<%=usersDto.getUserId()+"^"+usersDto.getUserName()+"^"+usersDto.getEmail()%>">
<%
		}
	}
%>
<input type="hidden" name="pEditorType"         value="W">
<input type="hidden" name="pFILE_NEW1_ori"      value="">

<input type="hidden" name="pKb"                 value="<%=pKb           %>">
<input type="hidden" name="pMessageKb"          value="<%=pMessageKb    %>">
<input type="hidden" name="pMessageWhere"       value="<%=pMessageWhere %>">
<input type="hidden" name="pMessageWhereSub"    value="<%=pMessageWhereSub  %>">
<input type="hidden" name="pResultCode1"        value="<%=pResultCode1  %>">
<input type="hidden" name="pResultCode2"        value="<%=pResultCode2  %>">
<input type="hidden" name="pResultCode3"        value="<%=pResultCode3  %>">
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">메일쓰기</font></td>
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
									<td class="s_tab_view01" width="20%">제목</td>
									<td class="s_tab_view02"><input type="text" name="pSubject" size="50" maxlength="100" value="" dispName="제목" notNull></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>

								<tr>
									<td colspan="2" class="s_tab_view03">
<textarea name="pContents" cols="60" rows="8" dispName="내용" notNull></textarea>
<%
	EditorParam editerParam = new EditorParam();
	editerParam.setShowFlag("true");
	editerParam.setWidth(500);
	editerParam.setHeight(300);
	editerParam.setEmailMode("true");
	//if(pTotCnt == 1)//-- 개인에게 발송시만 파일 첨부 가능하게
	//	editerParam.setToolBarHidden("attachFile");
	//else
	editerParam.setToolBarHidden("attachFile,image,video,audio,flash");
	out.print(CommonUtil.getEditorScript(editerParam));
%>
									</td>
								</tr>
<%
	if (pTotCnt == 1) { //-- 개인에게 발송시
%>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">첨부파일</td>
									<td class="s_tab_view02"><input type="file" name="pFILE_NEW1" size="35"></td>
								</tr>
<%
	}
%>
								<tr class="s_tab05">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td colspan="2" align="center" valign="top" class="pop_btn">
<script language=javascript>Button3("확인", "submitCheck()", "");</script>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose">
				<a href="javascript:self.close();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif"></a>
			</td>
		</tr>
	</table>







</form>
<%@include file="../common/popup_footer.jsp" %>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->
<%@include file="../common/popup_footer.jsp" %>