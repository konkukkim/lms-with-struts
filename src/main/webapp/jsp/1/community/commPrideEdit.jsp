<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam,com.edutrack.common.dto.CodeParam,com.edutrack.common.dto.EditorParam"%>
<%@ page import ="com.edutrack.community.dto.CommBbsContentsDTO"%>
<%@include file="../common/community_header.jsp" %>
<%  String pMode =  (String)model.get("pMode");
	int pCommId = Integer.parseInt((String)model.get("pCommId"));
	String pBbsType = (String)model.get("pBbsType");
	String pBbsId = (String)model.get("pBbsId");
	String pEditorYn = (String)model.get("pEditorYn");
	String pFileUseYn = (String)model.get("pFileUseYn");
	int pFileCount = 1;
	CommBbsContentsDTO contentsDto = (CommBbsContentsDTO)model.get("contentsDto");

	String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE;
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
		/*  WEAS 삽입 스크립트3(시작) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS 삽입 스크립트3(끝) */
		var f = document.Input;
		if(isEmpty(f.pSubject.value)) {
			alert("제목을 입력하지 않았습니다!\n\n제목은 필수 입력 항목입니다.");
			f.pSubject.focus();
			return false;
		}

		f.submit();
	}

	function goDel(){
		/*
		var f = document.Input;
		f.action = "/BbsContents.cmd?cmd=bbsContentsDelete&pCommId=<%=pCommId%>&pBbsId=<%=pBbsId%>&pSeqNo=<%=contentsDto.getSeqNo()%>";
		f.encoding = "application/x-www-form-urlencoded";
		f.submit();
		*/
		document.location.href="/Community.cmd?cmd=bbsContentsDelete&pMode=<%=pMode%>&pCommId=<%=pCommId%>&pBbsId=<%=pBbsId%>&pSeqNo=<%=contentsDto.getSeqNo()%>";
	}

	function delFile(fileNum){
       	document.location.href="/Community.cmd?cmd=bbsContentsFileDelete&pMode=<%=pMode%>&pCommId=<%=pCommId%>&pBbsId=<%=pBbsId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&pFileNo="+fileNum;
    }

    function goList(){
		document.location.href="/Community.cmd?cmd=commPridebbsPagingList&pMode=<%=pMode%>&pCommId=<%=pCommId%>&pBbsId=<%=pBbsId%>";
	}

	function fileDownload(rfilename, sfilename, filepath, filesize){
       hiddenFrame.document.location = "<%=CONTEXTPATH%>/fileDownServlet?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
    }
</script>
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
<!-- form start   onSubmit="return submitCheck()" -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/Community.cmd?cmd=commSubBoardRegist&pBbsType=<%=pBbsType%>&pRegMode=Edit&pCommId=<%=pCommId%>&pBbsId=<%=pBbsId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&pRegDate=<%=contentsDto.getRegDate()%>" enctype="multipart/form-data">
<input type=hidden name="pMode" value="<%=pMode%>">
<input type=hidden name="pBbsNo" value="<%=contentsDto.getSeqNo()%>">
<input type=hidden name="pDepthNo" value="<%=contentsDto.getDepthNo()%>">
<input type=hidden name="pOrderNo" value="<%=contentsDto.getOrderNo()%>">
<input type=hidden name="pParentNo" value="<%=contentsDto.getParentNo()%>">
<input type="hidden" name="pEditorType" value="<%=contentsDto.getEditorType()%>">
<input type="hidden" name="pOldrFileName[1]" value="<%=contentsDto.getRfileName1()%>">
<input type="hidden" name="pOldsFileName[1]" value="<%=contentsDto.getSfileName1()%>">
<input type="hidden" name="pOldFilePath[1]" value="<%=contentsDto.getFilePath1()%>">
<input type="hidden" name="pOldFileSize[1]" value="<%=contentsDto.getFileSize1()%>">
<input type="hidden" name="pOldrFileName[2]" value="<%=contentsDto.getRfileName2()%>">
<input type="hidden" name="pOldsFileName[2]" value="<%=contentsDto.getSfileName2()%>">
<input type="hidden" name="pOldFilePath[2]" value="<%=contentsDto.getFilePath2()%>">
<input type="hidden" name="pOldFileSize[2]" value="<%=contentsDto.getFileSize2()%>">
<input type="hidden" name="pOldrFileName[3]" value="<%=contentsDto.getRfileName3()%>">
<input type="hidden" name="pOldsFileName[3]" value="<%=contentsDto.getSfileName3()%>">
<input type="hidden" name="pOldFilePath[3]" value="<%=contentsDto.getFilePath3()%>">
<input type="hidden" name="pOldFileSize[3]" value="<%=contentsDto.getFileSize3()%>">
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">제목</td>
												<td class="s_tab_view02"><input name="pSubject" class="input_member" size="70" value="<%=contentsDto.getSubject()%>"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
    if(pFileUseYn.equals("Y")) {
        for(int i=1;i<=pFileCount;i++) {
%>
											<tr>
												<td class="s_tab_view01">첨부파일</td>
												<td class="s_tab_view02">
<%
			if( i == 1 && !contentsDto.getSfileName1().equals("")){
		    	String rFileName = contentsDto.getRfileName1();
		        String sFileName = contentsDto.getSfileName1();
		        String FilePath = contentsDto.getFilePath1();
		        String FileSize = contentsDto.getFileSize1();
		        out.print("<a href=\"javascript:fileDownload('"+rFileName+"','"+sFileName+"','"+FilePath+"','"+FileSize+"');\">"+rFileName+"</a>");
		        out.print("&nbsp;&nbsp;");
		        out.print("<a href=\"#\" onClick=\"javascript:delFile('"+i+"')\">[기존파일삭제]</a>");
		        out.print("<br>");
		    }

		    if( i == 2 && !contentsDto.getSfileName2().equals("")){
		        String rFileName = contentsDto.getRfileName2();
		        String sFileName = contentsDto.getSfileName2();
		        String FilePath = contentsDto.getFilePath2();
		        String FileSize = contentsDto.getFileSize2();
		        out.print("<a href=\"javascript:fileDownload('"+rFileName+"','"+sFileName+"','"+FilePath+"','"+FileSize+"');\">"+rFileName+"</a>");
		        out.print("&nbsp;&nbsp;");
		        out.print("<a href=\"#\" onClick=\"javascript:delFile('"+i+"')\">[기존파일삭제]</a>");
		        out.print("<br>");
		    }

		    if( i == 3 && !contentsDto.getSfileName3().equals("")){
		        String rFileName = contentsDto.getRfileName3();
		        String sFileName = contentsDto.getSfileName3();
		        String FilePath = contentsDto.getFilePath3();
		        String FileSize = contentsDto.getFileSize3();
		        out.print("<a href=\"javascript:fileDownload('"+rFileName+"','"+sFileName+"','"+FilePath+"','"+FileSize+"');\">"+rFileName+"</a>");
		        out.print("&nbsp;&nbsp;");
		        out.print("<a href=\"#\" onClick=\"javascript:delFile('"+i+"')\">[기존파일삭제]</a>");
		        out.print("<br>");
		    }
%>
													<input type=file name="pFile[<%=i%>]" size=70>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
		}
	}
%>

											<tr>
												<td class="s_tab_view01">내용</td>
												<td class="s_tab_view03">
													<textarea name="pContents" cols="90" rows="15" class="input_member "><%=contentsDto.getContents()%></textarea>
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
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("목록", "goList()", "");</script>
&nbsp;<script language=javascript>Button3("수정", "submitCheck()", "");</script>
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
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->

