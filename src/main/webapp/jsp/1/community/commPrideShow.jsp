<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.util.StringUtil"%>
<%@ page import ="com.edutrack.community.dto.CommBbsContentsDTO"%>
<%@ page import ="com.edutrack.framework.util.FileUtil"%>
<%@include file="../common/community_header.jsp" %>
<%
	String pMode =  (String)model.get("pMode");
	String curPage =  (String)model.get("curPage");
	String pBbsType = (String)model.get("pBbsType");
	String pCommId = (String)model.get("pCommId");
	String pBbsId = (String)model.get("pBbsId");
	String pFileUseYn = (String)model.get("pFileUseYn");
	String pCommentUseYn = (String)model.get("pCommentUseYn");

	String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE;

	CommBbsContentsDTO contentsDto = (CommBbsContentsDTO)model.get("contentsDto");

	String userId = UserBroker.getUserId(request);
	String userType = UserBroker.getUserType(request);
	String regId = contentsDto.getRegId();
	String editorType = contentsDto.getEditorType();
	String regPasswd = contentsDto.getRegPasswd();

	String chkRegPass = "T";
	if(regPasswd.equals("")) chkRegPass= "F";

	int pPrevSeqNo = 0;
	String pPrevSubject = "";
	int pNextSeqNo = 0;
	String pNextSubject = "";
	int listCnt = 1;

	int pChildCnt = Integer.parseInt((String)model.get("pChildCnt"));

	int pCommentCnt = 0;
	if(pCommentUseYn.equals("Y")){
		pCommentCnt = Integer.parseInt((String)model.get("pCommentCnt"));
	}
%>
<script type="text/javascript" language="javascript">
	function isEmpty(data) {
		for ( var i = 0 ; i < data.length ; i++ ) {
			if ( data.substring( i, i+1 ) != ' ' )
				return false;
		}
		return true;
	}

	function submitCheck() {
		var f = document.Input;

	 	if(!validate(f))
        	return false;
//        else
//            return true;
		f.submit();
	}

	function goCommDel(commNo,commRegId,chkRegPass) {
		if(chkRegPass == 'F'){//-- 코멘트가 로그인후 등록된 글인경우
			<%if(userId.equals("")) {//비로그인인경우%>
			alert('로그인 후 삭제해 주세요');
			<%}else{%>
			if(('<%=userId%>' != commRegId) && ('<%=userType%>' != 'M')){
				alert('자신이 등록한 답변만 삭제 가능합니다.');
			}else{
				var stat = confirm("답변을 삭제하시겠습니까?");
				if (stat == true)
					document.location  = "<%=CONTEXTPATH%>/CommPrideComment.cmd?cmd=bbsCommentDelete&pMode=<%=pMode%>&pCommId=<%=pCommId%>&pBbsId=<%=pBbsId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&pCommNo="+commNo;
			}
			<%}%>
		}else{
			var url = "<%=CONTEXTPATH%>/BbsComment.cmd?cmd=bbsCommentPassChkForm&pMode=<%=pMode%>&pBbsId=<%=pBbsId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&pCommNo="+commNo;
			window.open(url,"pass_win","top=100,left=162,toolbar=0,scrollbars=yes,directories=0,status=0,menubar=0,width=300,height=200,resizable=yes");
		}
	}

	function goDel(){

	    if(confirm("정말로 게시글을 삭제하시겠습니까? ")==false) return;

	<%	if(pChildCnt>0 || pCommentCnt>0){%>
		alert('하위글 또는 코멘트가 있어서 삭제 하실 수 없습니다.');
	<%	}else{
			if(userType.equals("M") || chkRegPass.equals("F")) { //-- 로그인 필요한 경우
				if(userId.equals("")) {%>
				alert('로그인을 먼저 해 주세요');
			<%	}else if(!userId.equals(regId) && !userType.equals("M")){ %>
				alert('등록자가 아니면 삭제하실 수 없습니다.');
			<%	}else{%>
				document.location.href="<%=CONTEXTPATH%>/Community.cmd?cmd=bbsContentsDelete&pMode=<%=pMode%>&pCommId=<%=pCommId%>&pBbsId=<%=pBbsId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>";
			<%	}
			}else{ /* 로그인 필요없는경우 */    %>
				var url = "<%=CONTEXTPATH%>/Community.cmd?cmd=bbsContentsPassChkForm&pMode=<%=pMode%>&pCommId=<%=pCommId%>&pBbsId=<%=pBbsId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>&pWorkMode=Del";
				window.open(url,"pass_win","top=100,left=162,toolbar=0,scrollbars=yes,directories=0,status=0,menubar=0,width=300,height=200,resizable=yes");
		<%	}
		}%>
	}

	function goEdit(){
	<%	if(userId.equals("")){%>
		alert('로그인을 먼저 해 주세요');
	<%	}else if(!userId.equals(regId) && !userType.equals("M")){ %>
		alert('등록자가 아니면 수정하실 수 없습니다.');
	<%	}else{%>
		document.location.href="<%=CONTEXTPATH%>/Community.cmd?cmd=bbsContentsEdit&pMode=<%=pMode%>&pCommId=<%=pCommId%>&pBbsId=<%=pBbsId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>";
	<%	}%>
	}

	function goReply(){
	<%if(userId.equals("")){%>
		alert('로그인을 먼저 해 주세요');
	<%}else{%>
		document.location.href="<%=CONTEXTPATH%>/Community.cmd?cmd=commPrideWrite&pMode=<%=pMode%>&pCommId=<%=pCommId%>&pBbsId=<%=pBbsId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>";
	<%}%>
	}

	function goList(){
		document.location.href="<%=CONTEXTPATH%>/Community.cmd?cmd=commPridebbsPagingList&pMode=<%=pMode%>&pCommId=<%=pCommId%>&pBbsId=<%=pBbsId%>&curPage=<%=curPage%>";
	}

	function fileDownload(rfilename, sfilename, filepath, filesize){
       //hiddenFrame.document.location = "<%=CONTEXTPATH%>/fileDownServlet?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;

       hiddenFrame.document.location = loc ;
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
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">제목</td>
												<td class="s_tab_view02" width="377"><%=contentsDto.getSubject()%></td>
												<td class="s_tab_view01" width="80">조회수</td>
												<td class="s_tab_view02" width="121"><%=contentsDto.getHitNo()%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">작성자</td>
												<td class="s_tab_view02"><%=StringUtil.nvl(contentsDto.getRegName())%></td>
												<td class="s_tab_view01">등록일</td>
												<td class="s_tab_view02"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(contentsDto.getRegDate()))%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%	if (pFileUseYn.equals("Y")) {	%>
											<tr>
												<td class="s_tab_view01">첨부파일</td>
												<td class="s_tab_view02" colspan="3">
<%
	    String rFileName = "";
	    String sFileName = "";
	    String FilePath = "";
	    String FileSize = "";
	    String FileImg = "";
	    int pFileCount = 1;

	    for(int i=1;i<=pFileCount;i++) {
	        if(i == 1 && !StringUtil.nvl(contentsDto.getSfileName1()).equals("") ) {
	            rFileName = StringUtil.nvl(contentsDto.getRfileName1());
	            sFileName = StringUtil.nvl(contentsDto.getSfileName1());
	            FilePath = StringUtil.nvl(contentsDto.getFilePath1());
	            FileSize = StringUtil.nvl(contentsDto.getFileSize1());
	            FileImg = FileUtil.fn_file_img(rFileName, img_path);
	            out.print("<a href=\"javascript:fileDownload('"+rFileName+"','"+sFileName+"','"+FilePath+"','"+FileSize+"');\">"+rFileName+"</a>");
	            out.print("&nbsp;&nbsp;");
	        }
	    }
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%	}	%>
											<tr>
												<td class="s_tab_view01">내용</td>
												<td class="s_tab_view03" colspan="3">
<%	if(editorType.equals("W")) {
        out.println( StringUtil.ReplaceAll(StringUtil.nvl(contentsDto.getContents()),"&quot;","\"") );

    } else if(editorType.equals("H")) {
        out.println( StringUtil.getHtmlContents(StringUtil.nvl(contentsDto.getContents())) );
    }	%>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td colspan="4" height="10"></td>
											</tr>
<%
	if(pCommentUseYn.equals("Y")) {
%>
											<!-- 댓글테이블 -->
											<tr>
												<td colspan="4">
													<!-- 댓글 쓰기 -->
													<table width="100%" class="reply_form">
<!-- form start   onSubmit="return submitCheck()"  -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/CommPrideComment.cmd?cmd=bbsCommentRegist">
<input type="hidden" name="pMode"   value="<%=pMode%>">
<input type="hidden" name="pCommId" value="<%=pCommId%>">
<input type="hidden" name="pBbsId"  value="<%=pBbsId%>">
<input type='hidden' name ='pBbsType' value='<%=pBbsType%>'>
<input type="hidden" name="pSeqNo"  value="<%=contentsDto.getSeqNo()%>">
<input type="hidden" name="curPage" value="<%=curPage%>">
<input type="hidden" name="pRegId"  value="<%=userId%>">
<input type="hidden" name="pRegPasswd" value="">
<input type="hidden" name="pRegEmail" value="">

														<tr>
															<td class="reply_w_icon">
																<table>
																	<tr>
<%
		// 이모티콘
		for (int i=1; i<=6; i++) {
%>
																		<td><input type=radio value="0<%=i%>" name="pEmoticon" <%if(i==1){%>checked<%}%>></td>
																		<td width=32><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_0<%=i%>.gif"></td>
<%
		}
%>
																	</tr>
																</table>
															</td>
														</tr>
														<input name="pRegName" type="hidden" value='<%=UserBroker.getUserName(request)%>' size="12" dispName="이름" notnull lenCheck="20">
														<tr>
															<td class="reply_w_text01"><textarea name="pContents" rows=2 wrap=virtual cols=96 dispName="답변내용" lenCheck="2000" notnull></textarea>
															<!--등록버튼-->&nbsp;<a href="javascript:submitCheck()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_record.gif" align="absmiddle"></a></td>
														</tr>
</form>
<!-- form end -->
													</table>
													<table>
														<tr>
															<td height="5"=></td>
														<tr>
													</table>
<%
	}
%>
<%
	if (pCommentCnt > 0) {
%>
<%		RowSet commentList= (RowSet)model.get("commentList");

        while (commentList.next()) {
        	String chkCommRegPass = "T";
            if (StringUtil.nvl(commentList.getString("reg_passwd")).equals(""))
            	chkCommRegPass = "F";	%>
													<!-- 댓글 내용 -->
													<table width="100%" align="center">
														<tr>
															<td class="reply_r_textform">
																<table width="650" align="center">
																	<tr>
																		<td valign="top" width="30"><%if(!StringUtil.nvl(commentList.getString("emoticon_num")).equals("")){%><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_<%=StringUtil.nvl(commentList.getString("emoticon_num"))%>.gif"><%}%></td>
																		<td width="100"><font color=#9a7441><%=StringUtil.nvl(commentList.getString("reg_name"))%></font></td>
																		<td><%=StringUtil.getHtmlContents(StringUtil.nvl(commentList.getString("contents")))%>&nbsp;&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_reply.gif" align="absmiddle">&nbsp;</td>
																		<td width="100" align="right" style="padding-right:6px"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(commentList.getString("reg_date")))%></td>
																		<td width="18" align="right" valign="top"><a href="javascript:goCommDel('<%=commentList.getInt("comm_no")%>','<%=StringUtil.nvl(commentList.getString("reg_id"))%>','<%=chkCommRegPass%>');"><img src="<%=img_path%>/common/re_del.gif" alt="리플삭제하기" width="9" height="9" align="absmiddle" border="0"></a></td>
																	</tr>
																</table>
																<table width="640" align="center">
																	<tr>
																		<td class="s_tab03" height=1></td>
																	</tr>
																</table>
															</td>
														</tr>
													</table>
<%
		}
		commentList.close();
%>
												</td>
											</tr>
											<!-- // 댓글테이블 끝 -->
<%
	}
%>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<script language=javascript>Button3("목록", "goList()", "");</script>
&nbsp;<script language=javascript>Button3("답글", "goReply()", "");</script>
&nbsp;<script language=javascript>Button3("수정", "goEdit()", "");</script>
&nbsp;<script language=javascript>Button3("삭제", "goDel()", "");</script>
												</td>
											</tr>

										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->

<%@include file="../common/community_footer.jsp" %>
