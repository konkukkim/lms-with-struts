<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.popupnotice.dto.PopupNoticeDTO, com.edutrack.common.dto.EditorParam"%>
<%@include file="../common/header.jsp" %>
<%
	Map data = (Map)request.getAttribute("MODEL");
	String pRegMode = (String)model.get("pRegMode");
	String pSeqNo   = (String)model.get("pSeqNo");
	String curPage	= (String)model.get("curPage");

	String subject  = "";
	String contents = "";
	int win_width = 300;
	int win_height = 300;
	int win_xposition = 0;
	int win_yposition = 0;
	String use_yn = "Y";
	String target="L";

	//수정모드일 경우
	if(pRegMode.equals("E")) {
		PopupNoticeDTO popupNoticeView = (PopupNoticeDTO)data.get("popupnoticeShow");
		subject = popupNoticeView.getSubject();
		contents = popupNoticeView.getContents();
		win_width = popupNoticeView.getWinWidth();
		win_height = popupNoticeView.getWinHeight();
		win_xposition = popupNoticeView.getWinXposition();
		win_yposition = popupNoticeView.getWinYposition();
		use_yn = popupNoticeView.getUseYn();
		target = popupNoticeView.getPopupTarget();
	}
%>

<Script Language="javascript">
	function submitCheck()
	{
		var f = document.Input;

		if(window.VBN_prepareSubmit != null){
			if(!VBN_prepareSubmit())
				return false;
		}

		if(isEmpty(f.pSubject.value))
		{
			alert("제목을 입력하세요");
			f.pSubject.focus();
			return false;
		}

		if(isEmpty(f.pWidth.value) || !Check_Num3(f.pWidth.value))
		{
			alert("팝업창 넓이를 숫자로 입력하세요");
			f.pWidth.focus();
			return false;
		}

		if(isEmpty(f.pHeight.value) || !Check_Num3(f.pHeight.value))
		{
			alert("팝업창 높이를 숫자로 입력하세요");
			f.pHeight.focus();
			return false;
		}

		if(isEmpty(f.pXposition.value) || !Check_Num3(f.pXposition.value))
		{
			alert("팝업창 X위치를 숫자로 입력하세요");
			f.pXposition.focus();
			return false;
		}

		if(isEmpty(f.pYposition.value) || !Check_Num3(f.pYposition.value))
		{
			alert("팝업창 Y위치를 숫자로 입력하세요");
			f.pYposition.focus();
			return false;
		}


		if(isEmpty(f.pContents.value))
		{
			alert("내용을 입력하세요");
			//f.pContents.focus();
			return false;
		}
        //f.encoding ="multipart/form-data";
        //f.action="<%=CONTEXTPATH%>/PopupNotice.cmd?cmd=popupNoticeRegist";

//		return true;
		f.submit();

	}

	function goList(cate_id) {
		document.location = "<%=CONTEXTPATH%>/PopupNotice.cmd?cmd=popupNoticePagingList&pMode=<%=PMODE %>";
	}

	function goDel() {

		if ( confirm("게시물을 삭제하시겠습니까?") == false) return;

		var	f = document.Input;
		f.encoding = "application/x-www-form-urlencoded";
		f.action="<%=CONTEXTPATH%>/PopupNotice.cmd?cmd=popupNoticeDelete&pMode=<%=PMODE %>";
		f.submit();

	}
</Script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start   onSubmit="return submitCheck()"  -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/PopupNotice.cmd?cmd=popupNoticeRegist&pMode=<%=PMODE %>" enctype="multipart/form-data">
<input type='hidden' name="pRegMode" value="<%=pRegMode%>">
<input type='hidden' name="pSeqNo"   value="<%=pSeqNo%>">
<input type='hidden' name="curPage"  value="<%=curPage%>">
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">제목</td>
												<td class="s_tab_view02" width="200" colspan="3"><input type="text" name="pSubject" size="60" value="<%=subject%>" maxlength="100"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">사용여부</td>
												<td class="s_tab_view02" colspan="3"">
													<input type="radio" name="pUseYn" value="Y" <%=(use_yn.equals("Y")? "checked" : "")%> class="no"> 사용
			                                        <input type="radio" name="pUseYn" value="N" <%=(use_yn.equals("N")? "checked" : "")%> class="no"> 사용안함
			                                        <input type=hidden name="pTarget" value="L">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">팝업창넓이</td>
												<td class="s_tab_view02" width="200"><input type="text" name="pWidth" value="<%=win_width%>" size="5"></td>
												<td class="s_tab_view01" width="90">팝업창높이</td>
												<td class="s_tab_view02""><input type="text" name="pHeight" value="<%=win_height%>" size="5"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">팝업창 X위치</td>
												<td class="s_tab_view02" width="200"><input type="text" name="pXposition" value="<%=win_xposition%>" size="5"></td>
												<td class="s_tab_view01" width="90">팝업창 Y위치</td>
												<td class="s_tab_view02""><input type="text" name="pYposition" value="<%=win_yposition%>" size="5"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">내용</td>
												<td class="s_tab_view03" width="200" colspan="3">
													<textarea name="pContents" class="" cols="80" rows="5"><%=contents%></textarea>
<%
	EditorParam editerParam = new EditorParam();
	editerParam.setShowFlag("true");
	editerParam.setWidth(550);
	editerParam.setHeight(300);
	editerParam.setToolBarHidden("attachFile");
	out.print(CommonUtil.getEditorScript(editerParam));
%>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<script language=javascript>Button3("목록", "goList()", "");</script>
<% if(!pRegMode.equals("E") && CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button3("등록", "submitCheck()", "");</script><%	}	%>
<% if( pRegMode.equals("E") && CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button3("수정", "submitCheck()", "");</script><%	}	%>
<% if( pRegMode.equals("E") && CommonUtil.getAuthorCheck(request,  "D"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button3("삭제", "goDel()", "");</script><%	}	%>

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
