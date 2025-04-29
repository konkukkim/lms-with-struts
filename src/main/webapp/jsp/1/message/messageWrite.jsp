<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.message.dto.MessageDTO, com.edutrack.common.dto.EditorParam,com.edutrack.common.dto.CodeParam"%>
<%@include file="../common/header.jsp" %>
<%
	String pUtype	 = (String)model.get("pUtype");
	String pRegMode	 = (String)model.get("pRegMode");

	Map data = (Map)request.getAttribute("MODEL");

	String pSendId = "";
	String subject = "";
	String contents = "";

	//답변쓰기일 경우
	if(pRegMode.equals("R")){
		MessageDTO messageShow = (MessageDTO)data.get("messageShow");

		pSendId	 = (String)model.get("pSendId");
		subject = "[답장]"+messageShow.getSubject();
		contents = messageShow.getContents();
	}
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
	if(pUtype.equals("A")) {
%>
		if(isEmpty(f.pId.value))
		{
			alert("받는사람을 입력하세요");
			f.pId.focus();
			return false;
		}

		f.pFILE_NEW1_ori.value = f.pFILE_NEW1.value;
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

		f.submit();
	}

	function goList() {
		document.location = "<%=CONTEXTPATH%>/Message.cmd?cmd=receiveMessagePagingList";
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

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start   onSubmit="return submitCheck()  -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/Message.cmd?cmd=messageRegist" enctype="multipart/form-data"">
<input type="hidden" name="pEditorType" value="W">
<input type="hidden" name="pFILE_NEW1_ori" value="">
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
<%
	if (USERTYPE.equals("M")) {
%>
											<tr>
												<td class="s_tab_view01" width="92">수신대상</td>
												<td class="s_tab_view02">
													<select name='pUtype' onChange="change_target()">
														<option value='A' <%if(pUtype.equals("A") || pUtype.equals("")){%> selected <%}%>>개인</option>
														<option value='S' <%if(pUtype.equals("S")){%> selected <%}%>>학습자</option>
														<option value='P' <%if(pUtype.equals("P")){%> selected <%}%>>교수자</option>
														<option value='M' <%if(pUtype.equals("M")){%> selected <%}%>>관리자</option>
														<option value='C' <%if(pUtype.equals("C")){%> selected <%}%>>과정운영자</option>
													</select>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
	} else {
		out.println("<input type='hidden' name='pUtype' value='A'> ");
	}
%>
<%
	if (pUtype.equals("A")) {
%>
											<tr>
												<td class="s_tab_view01" width="92">수신대상</td>
												<td class="s_tab_view02">
													<input type="text" name="pId" size="20" maxlength=30 value="<%=pSendId%>" readonly>
													<a href="javascript:goUserSearch()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/user_search.gif" width="66" height="17" align="absmiddle" hspace="5" vspace="5" border="0"></a>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
	}
%>
											<tr>
												<td class="s_tab_view01">제목</td>
												<td class="s_tab_view02">
													<input type=text name="pSubject" size="60" maxlength="100" value="<%=subject%>" dispName="제목" notNull>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
	if (pUtype.equals("A") || pUtype.equals("")) {
%>
											<tr>
												<td class="s_tab_view01">첨부파일</td>
												<td class="s_tab_view02">
													<input type=file name="pFILE_NEW1" size=70>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
	}
%>
											<tr>
												<td class="s_tab_view01">내용</td>
												<td class="s_tab_view03">
													<textarea name="pContents" cols="90" rows="5" wrap="VIRTUAL"><%=contents%></textarea>
<%
	EditorParam editerParam = new EditorParam();
	editerParam.setShowFlag("true");
	editerParam.setWidth(550);
	editerParam.setHeight(300);

	if(pUtype.equals("A") || pUtype.equals(""))
		editerParam.setToolBarHidden("attachFile");
	else
		editerParam.setToolBarHidden("attachFile,image,video,audio,flash");

	out.print(CommonUtil.getEditorScript(editerParam));
%>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("목록", "goList()", "");</script>
&nbsp;<script language=javascript>Button3("쪽지보내기", "submitCheck()", "");</script>
												</td>
											</tr>
</form>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						

<%@include file="../common/footer.jsp" %>

<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->