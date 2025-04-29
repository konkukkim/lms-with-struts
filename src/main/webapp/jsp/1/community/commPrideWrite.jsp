<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam,com.edutrack.common.dto.CodeParam,com.edutrack.common.dto.EditorParam"%>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/community_header.jsp" %>
<%
	String pMode =  (String)model.get("pMode");
	String pRegMode = (String)model.get("pRegMode");
	String pBbsType = (String)model.get("pBbsType");
	String pBbsId = (String)model.get("pBbsId");
	String pFileUseYn = (String)model.get("pFileUseYn");
	String pEditorYn = (String)model.get("pEditorYn");
	String pBbsNo  = "";
	String pDepthNo  = "";
	String pOrderNo  = "";
	String pParentNo  = "";
	if(pRegMode.equals("Reply")) {
		pBbsNo  =(String)model.get("pBbsNo");
		pDepthNo  =(String)model.get("pDepthNo");
		pOrderNo  =(String)model.get("pOrderNo");
		pParentNo  =(String)model.get("pParentNo");
	}

	String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE;
%>
<center>
<Script Language="javascript">
	function submitCheck() {
		<%if(pEditorYn.equals("Y")){%>
		/*  WEAS 삽입 스크립트3(시작) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS 삽입 스크립트3(끝) */
		<%}%>
		var f = document.Input;
        var commId = f.pCommId[f.pCommId.selectedIndex].value;

        f.action="<%=CONTEXTPATH%>/Community.cmd?cmd=commPrideRegist&pRegMode=<%=pRegMode%>&pBbsId=<%=pBbsId%>&pCommId="+commId		;

        if(commId==""){
            alert("동아리를 선택하세요.");
            f.pCommId.focus();
            return false;
        }

		if(!validate(f)) return false;
//		else return true;
		f.submit();
	}

	function goList(){
		document.location.href="/Community.cmd?cmd=commPridebbsPagingList&pMode=<%=pMode%>&pBbsId=<%=pBbsId%>";
	}
</Script>
								<tr valign="top">
									<td colspan="2" height="10"></td>
								</tr>
								<tr valign="top">
									<!-- community title -->
									<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b>동아리 자랑</b></font></td>
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
<form name=Input method="post" action="<%=CONTEXTPATH%>/Community.cmd?cmd=commSubBoardRegist&pRegMode=<%=pRegMode%>&pBbsId=<%=pBbsId%>&pBbsType=<%=pBbsType%>" enctype="multipart/form-data">
<input type="hidden" name="pEditorType" value="W">
<input type="hidden" name="pMode" value="<%=pMode%>">
<input type="hidden" name="pBbsNo" value="<%=pBbsNo%>">
<input type="hidden" name="pDepthNo" value="<%=pDepthNo%>">
<input type="hidden" name="pOrderNo" value="<%=pOrderNo%>">
<input type="hidden" name="pParentNo" value="<%=pParentNo%>">

											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">동아리</td>
												<td class="s_tab_view02">
													<select name="pCommId">
														<option value="">-- 선택하세요 --</option>
<%
	RowSet list= (RowSet)model.get("rs");

// 내가 속한 커뮤니티를 가져온다.
	while(list.next()){
		out.println("<option value=" + list.getInt("comm_id") +">" + list.getString("comm_name") +"</option>");
	}//end while
%>
													</select>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">제목</td>
												<td class="s_tab_view02"><input name="pSubject" size="70"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">게시만료일</td>
												<td class="s_tab_view02"><input size=4 name=pyear1>년 <input size=2 name=pmonth1>월 <input size=2 name=pday1>일 <input type=hidden size=2 name=phour1><input type=hidden size=2 name=pminute1><input type=hidden name=pdate1></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
	if(pFileUseYn.equals("Y")) {
		int i = 1;
%>
											<tr>
												<td class="s_tab_view01">첨부파일</td>
												<td class="s_tab_view02">
													<input type=file name="pFile[<%=i%>]" size=70>
													<input type="hidden" name="pTarget" value="A">
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
													<textarea name="pContents" rows=15 wrap="VIRTUAL" cols="90"></textarea>
<%
	if(pEditorYn.equals("Y")){
    	EditorParam editerParam = new EditorParam();
        editerParam.setShowFlag("true");
        editerParam.setWidth(516);
        editerParam.setHeight(300);
        editerParam.setToolBarHidden("attachFile");
        out.print(CommonUtil.getEditorScript(editerParam));
    }
%>
												</td>
											</tr>
											<tr class="s_tab05">
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
