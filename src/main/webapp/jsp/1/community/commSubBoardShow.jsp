<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.util.StringUtil"%>
<%@ page import ="com.edutrack.community.dto.CommBbsContentsDTO,com.edutrack.framework.util.FileUtil"%>
<%@include file="../common/community_header.jsp" %>
<%
   	String	commName		=	StringUtil.nvl(model.get("commName"));
	String	pMode 			=	StringUtil.nvl(model.get("pMode"));
	String	curPage			=	StringUtil.nvl(model.get("curPage"));
	String	pBbstype 		=	StringUtil.nvl(model.get("pBbsType"));
	String	bbsName			=	StringUtil.nvl(model.get("bbsName"));
	String	pCommId			=	StringUtil.nvl(model.get("pCommId"));
	String	pBbsId 			=	StringUtil.nvl(model.get("pBbsId"));
	String	pFileUseYn 		=	StringUtil.nvl(model.get("pFileUseYn"));
	String	pCommentUseYn	=	StringUtil.nvl(model.get("pCommentUseYn"));
	String	pUserLevel		=	StringUtil.nvl(model.get("userLevel"));		//커뮤니티 내에서의 등급

	CommBbsContentsDTO contentsDto = (CommBbsContentsDTO)model.get("contentsDto");

	String	regId 			=	contentsDto.getRegId();
	String	editorType 		=	contentsDto.getEditorType();
	String	regPasswd 		=	contentsDto.getRegPasswd();
	String	chkRegPass		=	"T";
	if(regPasswd.equals("")) chkRegPass= "F";

	int		pPrevSeqNo		= 	0;
	String	pPrevSubject	= 	"";
	int		pNextSeqNo 		= 	0;
	String	pNextSubject 	= 	"";
	int		listCnt 		= 	1;
	int		pChildCnt 		= 	StringUtil.nvl(model.get("pChildCnt"), 0);

	String	img_path		=	CONTEXTPATH + "/img/" + SYSTEMCODE;
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CommBbsCommentWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/community/commBbsComment.js"></script>
<script language="javascript">
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
        else
            return true;
	}

	function goCommDel(commNo,commRegId,chkRegPass) {
		if(chkRegPass == 'F'){//-- 코멘트가 로그인후 등록된 글인경우
			<%if(USERID.equals("")) {//비로그인인경우%>
					alert('로그인 후 삭제해 주세요');
			<%}else{%>
					if(('<%=USERID%>' != commRegId) && ('<%=USERTYPE%>' != 'M')){
						alert('자신이 등록한 답변만 삭제 가능합니다.');
					}else{
						var stat = confirm("답변을 삭제하시겠습니까?");
						if (stat == true)
							document.location  = "/CommPrideComment.cmd?cmd=commSubCommentDelete&pMode=<%=pMode%>&pCommId=<%=pCommId%>&pBbsId=<%=pBbsId%>&pBbstype=<%=pBbstype%>&pSeqNo=<%=contentsDto.getSeqNo()%>&pCommNo="+commNo;
					}
			<%}%>
		}else{
			var url = "<%=CONTEXTPATH%>/BbsComment.cmd?cmd=bbsCommentPassChkForm&pMode=<%=pMode%>&pBbsId=<%=pBbsId%>&pBbstype=<%=pBbstype%>&pSeqNo=<%=contentsDto.getSeqNo()%>&pCommNo="+commNo;
			window.open(url,"pass_win","top=100,left=162,toolbar=0,scrollbars=yes,directories=0,status=0,menubar=0,width=300,height=200,resizable=yes");
		}
	}

	function goDel(){
		var f = document.Input;
		pCommCnt = f.pCommCnt.value;

		if(<%=pChildCnt%> > 0 || (pCommCnt != "" && pCommCnt > 0)) {
		alert('하위글 또는 코멘트가 있어서 삭제 하실 수 없습니다.');
		} else {
			<%if(USERTYPE.equals("M") || chkRegPass.equals("F"))
			  { //-- 로그인 필요한 경우
				if(USERID.equals(""))
				{%>
				alert('로그인을 먼저 해 주세요');
			  <%}else if(!USERID.equals(regId) && !USERTYPE.equals("M")){ %>
				alert('등록자가 아니면 삭제하실 수 없습니다.');
			  <%}else{%>
			  		if(confirm("정말로 게시글을 삭제하시겠습니까? ") == true)
						document.location.href="<%=CONTEXTPATH%>/CommSubBoard.cmd?cmd=commSubBoardDelete&pMode=<%=pMode%>&pCommId=<%=pCommId%>&pBbsId=<%=pBbsId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>&pBbstype=<%=pBbstype%>";
			  <%}%>
		    <%}else{ /* 로그인 필요없는경우 */    %>
					var url = "<%=CONTEXTPATH%>/CommSubBoard.cmd?cmd=bbsContentsPassChkForm&pMode=<%=pMode%>&pCommId=<%=pCommId%>&pBbsId=<%=pBbsId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>&pWorkMode=Del&pBbstype=<%=pBbstype%>";
					window.open(url,"pass_win","top=100,left=162,toolbar=0,scrollbars=yes,directories=0,status=0,menubar=0,width=300,height=200,resizable=yes");
			<%}%>
		}
	}

	function goEdit(){
	<% if(USERID.equals("")){ %>
			alert('로그인을 먼저 해 주세요');
	  <%}else if(!USERID.equals(regId) && !USERTYPE.equals("M")){ %>
			alert('등록자가 아니면 수정하실 수 없습니다.');
	  <%}else{%>
			document.location.href="<%=CONTEXTPATH%>/CommSubBoard.cmd?cmd=commSubBoardContentsEdit&pMode=<%=pMode%>&pCommId=<%=pCommId%>&pBbsId=<%=pBbsId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>&pBbstype=<%=pBbstype%>";
	  <%}%>
	}

	function goReply(){
	<%if(USERID.equals("")){%>
		alert('로그인을 먼저 해 주세요');
	<%}else{%>
		document.location.href="<%=CONTEXTPATH%>/CommSubBoard.cmd?cmd=commSubBoardWrite&pMode=<%=pMode%>&pCommId=<%=pCommId%>&pBbsId=<%=pBbsId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>&pBbstype=<%=pBbstype%>";
	<%}%>
	}

	function goList(){
		document.location.href="<%=CONTEXTPATH%>/CommSubBoard.cmd?cmd=commSubBoard&MENUNO=0&pBbsId=<%=pBbsId%>";
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
											<tr class="com_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="com_tab02" width="92">제목</td>
												<td class="s_tab_view02" width="377"><%=StringUtil.getHtmlContents(StringUtil.nvl(contentsDto.getSubject()))%></td>
												<td class="com_tab02" width="80">조회수</td>
												<td class="s_tab_view02" width="121"><%=contentsDto.getHitNo()%></td>
											</tr>
											<tr class="com_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="com_tab02">작성자</td>
												<td class="s_tab_view02"><%=StringUtil.nvl(contentsDto.getRegName())%></td>
												<td class="com_tab02">등록일</td>
												<td class="s_tab_view02"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(contentsDto.getRegDate()))%></td>
											</tr>
											<tr class="com_tab03">
												<td colspan="4"></td>
											</tr>

<%	if (pFileUseYn.equals("Y")) {	%>
											<tr>
												<td class="com_tab02">첨부파일</td>
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
	            out.print("<img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/ico_file.gif\" border=\"0\" align=\"absmiddle\">&nbsp;");
	            out.print("<a href=\"javascript:fileDownload('"+rFileName+"','"+sFileName+"','"+FilePath+"','"+FileSize+"');\">"+rFileName+"</a>");
	            out.print("&nbsp;&nbsp;");
	        }
	    }
%>
												</td>
											</tr>
											<tr class="com_tab03">
												<td colspan="4"></td>
											</tr>
<%	}	%>
											<tr>
												<td class="com_tab02">내용</td>
												<td class="s_tab_view03" colspan="3">
<%	if(editorType.equals("W")) {
        out.println( StringUtil.ReplaceAll(StringUtil.nvl(contentsDto.getContents()),"&quot;","\"") );

    } else if(editorType.equals("H")) {
        out.println( StringUtil.getHtmlContents(StringUtil.nvl(contentsDto.getContents())) );
    }	%>
												</td>
											</tr>
											<tr class="com_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<script language=javascript>Button3("목록", "goList()", "");</script>
<%	if(!pBbstype.equals("N")) { %>&nbsp;<script language=javascript>Button3("답글", "goReply()", "");</script><%	} %>
<%	if((pBbstype.equals("N") && (USERTYPE.equals("M") || pUserLevel.equals("M"))) || (!pBbstype.equals("N") && USERID.equals(regId))) { %>
&nbsp;<script language=javascript>Button3("수정", "goEdit()", "");</script>&nbsp;<script language=javascript>Button3("삭제", "goDel()", "");</script>
<%	} %>
												</td>
											</tr>
<%
	if(pCommentUseYn.equals("Y")) {
%>
											<tr>
												<td colspan="4" height="10"></td>
											</tr>
											<!-- 댓글테이블 -->
											<tr class="com_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td colspan="4">
													<!-- 댓글 쓰기 -->


													<table width="100%" class="reply_form">
<!-- form start   onSubmit="return submitCheck()"   -->
<!-- action="<%=CONTEXTPATH%>/CommPrideComment.cmd?cmd=boardCommentRegist" -->
<form name=Input method="post">
<input type="hidden" name="pMode"   value="<%=pMode%>">
<input type="hidden" name="pCommId" value="<%=pCommId%>">
<input type="hidden" name="pBbsId"  value="<%=pBbsId%>">
<input type='hidden' name ="pBbstype" value='<%=pBbstype%>'>
<input type="hidden" name="pSeqNo"  value="<%=contentsDto.getSeqNo()%>">
<input type="hidden" name="curPage" value="<%=curPage%>">
<input type="hidden" name="pRegId"  value="<%=USERID%>">
<input type="hidden" name="pRegPasswd" value="">
<input type="hidden" name="pRegEmail" value="">
<input type='hidden' name ='curPageComment' value=''>
<input type='hidden' name ='pEmoticon' value=''>

<input type="hidden" name="userId"  value="<%=USERID%>">
<input type="hidden" name="userType"  value="<%=USERTYPE%>">
<input type="hidden" name="regId"  value="<%=regId%>">
<input type="hidden" name="childCnt"  value="<%=pChildCnt%>">
<input type="hidden" name="pCommCnt"  value="">

														<tr>
															<td class="reply_w_icon">
																<table>
																	<tr>
	<td width=32><a href="javascript:selectEmoticon('1')" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_01.gif"></a></td>
	<td width=32><a href="javascript:selectEmoticon('2')" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_02.gif"></a></td>
	<td width=32><a href="javascript:selectEmoticon('3')" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_03.gif"></a></td>
	<td width=32><a href="javascript:selectEmoticon('4')" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_04.gif"></a></td>
	<td width=32><a href="javascript:selectEmoticon('5')" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_05.gif"></a></td>
	<td width=32><a href="javascript:selectEmoticon('6')" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_06.gif"></a></td>
																	</tr>
																</table>
															</td>
														</tr>

														<tr>
<td class="reply_w_text01">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" align="right">
		<tr>
			<td width="50"><input name="pRegName" size="6" type="text" value='<%=UserBroker.getUserName(request)%>' readOnly></td>
			<td rowspan="2" width="620"><textarea name="pContents" wrap=virtual dispName="답변내용" lenCheck="4000" notNull style="width:555px;height:48px"></textarea>
	<!--등록버튼-->&nbsp;<a href="javascript:addComment();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_record.gif" align="absmiddle"></a>
			</td>
		</tr>
		<tr>
			<td width="50" height="25"><!-- 선택된 표정 시작 --><div id="selectedEmoticon" style="width:100%;display:none"></div></td>
		</tr>
	</table>
</td>
														</tr>
</form>
<!-- form end -->
													</table>
													<table>
														<tr>
															<td height="5"=></td>
														<tr>
													</table>
													<!-- 댓글 내용 -->
<!-- 리스트 -->
	<div id="commentList" style="width:100%;display:none"></div>
<!-- 리스트 -->
													<table width="97%" border=0>
														<tr>
															<td align="right" class="reply_list">
<!-- 페이징 -->
	<div id="getPagging" style="width:100%;display:none"></div>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!-- // 댓글테이블 끝 -->
											<tr class="com_tab05">
												<td colspan="4"></td>
											</tr>
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pCommId%>','<%=pBbsId%>','<%=contentsDto.getSeqNo()%>');
</script>
<%
	}
%>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->

<%@include file="../common/community_footer.jsp" %>