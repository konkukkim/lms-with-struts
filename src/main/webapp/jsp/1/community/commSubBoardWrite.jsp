<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam,com.edutrack.common.dto.CodeParam,com.edutrack.common.dto.EditorParam"%>
<%@include file="../common/community_header.jsp" %>
<%
	String pMode		= (String)model.get("pMode");
	String pRegMode 	= (String)model.get("pRegMode");
	String pBbsType 	= (String)model.get("pBbsType");
	String pBbsId 		= (String)model.get("pBbsId");
	String pFileUseYn 	= (String)model.get("pFileUseYn");
	String pEditorYn 	= (String)model.get("pEditorYn");
	String pCommId 		= (String)model.get("commId");
	String commName 	= (String)model.get("commName");
	String userLevel 	= (String)model.get("userLevel");
	String bbsName 		= (String)model.get("bbsName");

	String pBbsNo		= "";
	String pDepthNo  	= "";
	String pOrderNo  	= "";
	String pParentNo  	= "";

	if(pRegMode.equals("Reply")) {
		pBbsNo  =(String)model.get("pBbsNo");
		pDepthNo  =(String)model.get("pDepthNo");
		pOrderNo  =(String)model.get("pOrderNo");
		pParentNo  =(String)model.get("pParentNo");
	}
%>
<center>
<script language="javascript">
	function isEmpty(data) {
		for ( var i = 0 ; i < data.length ; i++ ) {
			if ( data.substring( i, i+1 ) != ' ' )
				return false;
		}
		return true;
	}

	function submitCheck() {
		<%if(pEditorYn.equals("Y")){%>
		/*  WEAS 삽입 스크립트3(시작) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS 삽입 스크립트3(끝) */
		<%}%>
		var f = document.Input;

		if(isEmpty(f.pSubject.value)) {
			alert("제목을 입력하지 않았습니다!\n\n제목은 필수 입력 항목입니다.");
			f.pSubject.focus();
			return false;
		}
//		else{
//			return true;
//		}
		f.submit();
	}

	function goList(){
		document.location.href = "<%=CONTEXTPATH%>/CommSubBoard.cmd?cmd=commSubBoard&MENUNO=0&pBbsId=<%=pBbsId%>";
	}
</script>
								<tr valign="top">
									<td colspan="2" height="10"></td>
								</tr>
								<tr valign="top">
									<!-- community title -->
									<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=bbsName%></b></font></td>
									<!-- // community title -->
									<!-- history -->
									<td class="com_stit_history" valign="bottom" align="right" width="327">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (PMODE.equals("Search")) NAVIGATION = "홈 > 통합검색";
	if (NAVIGATION != "") {
		out.println(NAVIGATION) ;
	} // end if
%>
									</td>
								</tr>
								<tr valign="top">
									<td colspan="2" class="com_content_top">
									<!-- 내용 -->
									<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start   onSubmit="return submitCheck()"  -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/CommSubBoard.cmd?cmd=commSubBoardRegist&pRegMode=<%=pRegMode%>&pBbsId=<%=pBbsId%>&pBbsType=<%=pBbsType%>" enctype="multipart/form-data">
<input type="hidden" name="pEditorType" value="W">
<input type="hidden" name="pMode" value="<%=pMode%>">
<input type="hidden" name="pBbsNo" value="<%=pBbsNo%>">
<input type="hidden" name="pDepthNo" value="<%=pDepthNo%>">
<input type="hidden" name="pOrderNo" value="<%=pOrderNo%>">
<input type="hidden" name="pParentNo" value="<%=pParentNo%>">

											<tr class="com_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="com_tab02" width="92">제목</td>
												<td class="s_tab_view02"><input name="pSubject" size="70"></td>
											</tr>
											<tr class="com_tab03">
												<td colspan="2"></td>
											</tr>
<%
	if(pFileUseYn.equals("Y")) {
		int i = 1;
%>
											<tr>
												<td class="com_tab02">첨부 1</td>
												<td class="s_tab_view02">
													<input type=file name="pFile[<%=i%>]" size=70>
													<input type="hidden" name="pTarget" value="A">
												</td>
											</tr>
											<tr class="com_tab03">
												<td colspan="2"></td>
											</tr>
<%
	}
%>
											<tr>
												<td class="com_tab02">내용</td>
												<td class="s_tab_view03">
													<textarea name="pContents" cols="90" rows="15"></textarea>
<%
	if(pEditorYn.equals("Y")){
    	EditorParam editerParam = new EditorParam();
        editerParam.setShowFlag("true");
        editerParam.setWidth(550);
        editerParam.setHeight(300);
        editerParam.setToolBarHidden("attachFile");
        out.print(CommonUtil.getEditorScript(editerParam));
    }
%>
												</td>
											</tr>
											<tr class="com_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("목록", "goList()", "");</script>
&nbsp;<script language=javascript>Button3("등록", "submitCheck()", "");</script>
												</td>
											</tr>
</form>
<!-- form end -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->





<%@include file="../common/community_footer.jsp" %>
<%if(pEditorYn.equals("Y")){%>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->
<%}%>