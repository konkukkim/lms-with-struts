<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.curribbs.dto.CurriBbsContentsDTO"%>
<%@include file="../common/course_header.jsp" %>
<%

	String 	curPage 			=  (String)model.get("curPage");
	String 	pBbsType 			= 	(String)model.get("pBbsType");
	String 	pFileUseYn 			= 	(String)model.get("pFileUseYn");
	int 	pFileCount 			= 	Integer.parseInt((String)model.get("pFileCount"));
	String 	pCommentUseYn 		= 	(String)model.get("pCommentUseYn");
	String 	pEmoticonUseYn 		= 	(String)model.get("pEmoticonUseYn");
	String 	pViewThreadYn 		= 	(String)model.get("pViewThreadYn");
	String 	pViewPrevNextYn 	= 	(String)model.get("pViewPrevNextYn");
	int 	pNewTime 			= 	Integer.parseInt((String)model.get("pNewTime"));
    int	 	pHotChk 			= 	Integer.parseInt((String)model.get("pHotChk"));


	CurriBbsContentsDTO 	contentsDto		= (CurriBbsContentsDTO)model.get("contentsDto");

	String 	regId 				= 	StringUtil.nvl(contentsDto.getRegId());
	String 	editorType 			= 	StringUtil.nvl(contentsDto.getEditorType());
	String 	pCourseId			= 	StringUtil.nvl(contentsDto.getCourseId());


	String 	pLoginChkYn 		= 	"Y";//과정게시물은 모두 로그인 한 사람이 사용할 수 있다.
	String 	chkRegPass 			= 	"F"; // 코멘트도 로그인 후 쓸 수있다.
	int 	pPrevSeqNo 			= 	0;
	String 	pPrevSubject 		= 	"";
	int 	pNextSeqNo 			= 	0;
	String 	pNextSubject 		= 	"";
	if(pViewPrevNextYn.equals("Y")){
		 	pPrevSeqNo 			= 	Integer.parseInt((String)model.get("pPrevSeqNo"));
		 	pPrevSubject 		= 	(String)model.get("pPrevSubject");
		 	pNextSeqNo 			= 	Integer.parseInt((String)model.get("pNextSeqNo"));
		 	pNextSubject 		= 	(String)model.get("pNextSubject");
	}
	int 	listCnt 				= 	1;
	if(pViewThreadYn.equals("Y")){
			listCnt 				= 	Integer.parseInt((String)model.get("listCnt"));
	}
	int 	pChildCnt 			= 	Integer.parseInt((String)model.get("pChildCnt"));

	String 	CourseName 			= 	"";
	if(!pCourseId.equals("")){
			RowSet courseInfo = 	curriSubCoruseDao.getCurriSubCourseInfo(SYSTEMCODE,CURRICODE,CURRIYEAR,CURRITERM,pCourseId);
			courseInfo.next();
			CourseName 			= 	StringUtil.nvl(courseInfo.getString("course_name"));
	}
	String 	bbsHot 				= 	"";
	String 	bbsNew				=	"";

	String 	bbs = "";
	String 	bbsTitleImg	= "ctit3_01.gif";
	if(pBbsType.equals("notice")){
			bbs = "공지사항";
			bbsTitleImg	= "ctit3_01.gif";
	}else	if(pBbsType.equals("bbs")){
			bbs = "게시판";
			bbsTitleImg	= "ctit4_01.gif";
	}else	if(pBbsType.equals("qna")){
			bbs = "Q&A";
			bbsTitleImg	= "ctit6_01.gif";
	}else	if(pBbsType.equals("pds")){
			bbs = "자료실";
			bbsTitleImg	= "ctit5_01.gif";
	}
%>

<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriBbsCommentWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/curri_bbs/curriBbsComment.js"></script>

<script language="javascript">
	// 댓글 삭제
	function deleteComment(commNo,commRegId,chkRegPass){

	   if(chkRegPass == 'F'){//-- 코멘트가 로그인후 등록된 글인경우
	       if(('<%=USERID%>' != commRegId) && ('<%=USERTYPE%>' != 'M')){
				alert('자신이 등록한 답변만 삭제 가능합니다.');
		   }else{
		       if(confirm("댓글을 삭제 하시겠습니까?")){
				   var f = document.Input;
				   var bbsType = f.pBbsType.value;
				   var seqNo = f.pSeqNo.value;

				   CurriBbsCommentWork.curriBbsCommentDelete('<%=pBbsType%>', '<%=contentsDto.getSeqNo()%>', commNo, curriBbsCommentDeleteCallback);
			   }else
			       return;
			   }
	   }else
	       return;
	}

	function goDel(){
		var f = document.Input;
		var commCnt = 0;

		if(f != null) commCnt = f.commentCount.value;

		if(<%=pChildCnt%> > 0 || (commCnt != "" && commCnt > 0)) {
			alert('하위글 또는 코멘트가 있어서 삭제 하실 수 없습니다.');
		} else {
			  <%if(!USERID.equals(regId) && !USERTYPE.equals("M")){ %>
					alert('등록자가 아니면 삭제하실 수 없습니다.');
			  <%}else{%>
					var stat = confirm("게시글을 삭제하시겠습니까?");
					if (stat == true)
						document.location.href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsDelete&pBbsType=<%=pBbsType%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>";
			  <%}%>
		}
	}
	function goEdit(){
  <%if(!USERID.equals(regId) && !USERTYPE.equals("M")){ %>
		alert('등록자가 아니면 수정하실 수 없습니다.');
  <%}else{%>
		document.location.href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsEdit&pBbsType=<%=pBbsType%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>";
  <%}%>
	}
	function goReply(){
		document.location.href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsWrite&pBbsType=<%=pBbsType%>&pSeqNo=<%=contentsDto.getSeqNo()%>&pCourseId=<%=pCourseId%>&curPage=<%=curPage%>";
	}
	function goList(){
		document.location.href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsPagingList&pBbsType=<%=pBbsType%>&curPage=<%=curPage%>";
	}

    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }
</Script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request, bbs)%></b></font></td>
									<!-- // classroom title -->
									<!-- history -->
									<td class="c_stit_history" valign="bottom" align="right" width="327">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (PMODE.equals("Search")) NAVIGATION = "홈 > 통합검색";
	if (NAVIGATION != "") {
		out.println(NAVIGATION) ;
	} // end if
%>
									<!-- // history -->
								</tr>
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">제목</td>
												<td class="s_tab_view02" width="377"><%=StringUtil.getHtmlContents(StringUtil.nvl(contentsDto.getSubject()))%></td>
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
											<tr>
												<td class="s_tab_view01">첨부파일</td>
												<td class="s_tab_view02" colspan="3">
<%
		String rFileName = "";
		String sFileName = "";
	    String FilePath = "";
	    String FileSize = "";

		for (int i=1;i<=pFileCount;i++) {
			if(i == 1 && !StringUtil.nvl(contentsDto.getSfileName1()).equals("") ){
			    rFileName = StringUtil.nvl(contentsDto.getRfileName1());
			    sFileName = StringUtil.nvl(contentsDto.getSfileName1());
			    FilePath = StringUtil.nvl(contentsDto.getFilePath1());
			    FileSize = StringUtil.nvl(contentsDto.getFileSize1());
			    out.print("<img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/ico_file.gif\" align='absmiddle'>&nbsp;");
			    out.print("<a href=\"javascript:fileDownload('"+rFileName+"','"+sFileName+"','"+FilePath+"','"+FileSize+"');\">"+rFileName+"</a>");
		    	out.print("&nbsp;&nbsp;");
			}

			if (i == 2 && !StringUtil.nvl(contentsDto.getSfileName2()).equals("") ){
			    rFileName = StringUtil.nvl(contentsDto.getRfileName2());
			    sFileName = StringUtil.nvl(contentsDto.getSfileName2());
			    FilePath = StringUtil.nvl(contentsDto.getFilePath2());
			    FileSize = StringUtil.nvl(contentsDto.getFileSize2());
			    out.print("<img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/ico_file.gif\" align='absmiddle'>&nbsp;");
			    out.print("<a href=\"javascript:fileDownload('"+rFileName+"','"+sFileName+"','"+FilePath+"','"+FileSize+"');\">"+rFileName+"</a>");
		    	out.print("&nbsp;&nbsp;");
			}

			if (i == 3 && !StringUtil.nvl(contentsDto.getSfileName3()).equals("") ){
			    rFileName = StringUtil.nvl(contentsDto.getRfileName3());
			    sFileName = StringUtil.nvl(contentsDto.getSfileName3());
			    FilePath = StringUtil.nvl(contentsDto.getFilePath3());
			    FileSize = StringUtil.nvl(contentsDto.getFileSize3());
			    out.print("<img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/ico_file.gif\" align='absmiddle'>&nbsp;");
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
											<tr>
												<td class="s_tab_view01">내용</td>
												<td class="s_tab_view03" colspan="3">
<%
	if (editorType.equals("W")) {
%>
													<%=StringUtil.ReplaceAll(StringUtil.nvl(contentsDto.getContents()),"&quot;","\"")%>
<%
	} else if (editorType.equals("H")) {
%>
													<%=StringUtil.getHtmlContents(StringUtil.nvl(contentsDto.getContents()))%>
<%
	}
%>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<script language=javascript>Button3("목록", "goList()", "");</script>
<%
	if (!pBbsType.equals("notice") && StringUtil.nvl(contentsDto.getContentsType()).equals("S")) {
%>
&nbsp;<script language=javascript>Button3("답글쓰기", "goReply()", "");</script>
<%
	}
	if(pBbsType.equals("notice") && (USERTYPE.equals("M") || USERTYPE.equals("P") || USERTYPE.equals("J"))) {
%>
&nbsp;<script language=javascript>Button3("수정", "goEdit()", "");</script>&nbsp;<script language=javascript>Button3("삭제", "goDel()", "");</script>
<%
	} else if(USERID.equals(regId) || USERTYPE.equals("M")){
%>
&nbsp;<script language=javascript>Button3("수정", "goEdit()", "");</script>&nbsp;<script language=javascript>Button3("삭제", "goDel()", "");</script>
<%
	}
 %>
												</td>
											</tr>
<%  //댓글 가능시 시작
	if("Y".equals(pCommentUseYn)){ %>
											<tr>
												<td colspan="4" height="10"></td>
											</tr>
											<!-- 댓글테이블 -->
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td colspan="4">
													<!-- 댓글 쓰기 -->
													<table width="100%" class="reply_form">
<!-- form start -->
 <form name=Input method="post" action="javascript:addComment();">
<input type='hidden' name ='pBbsType' value='<%=pBbsType%>'>
<input type='hidden' name ='pCourseId' value='<%=pCourseId%>'>
<input type='hidden' name ='pBbsType' value='<%=pBbsType%>'>
<input type='hidden' name ='pSeqNo' value='<%=contentsDto.getSeqNo()%>'>
<input type='hidden' name ='curPage' value='<%=curPage%>'>
<input type='hidden' name ='pRegId' value='<%=USERID%>'>
<input type='hidden' name ='pEmoticon' value=''>
<input type='hidden' name ='curPageComment' value=''>
<input type='hidden' name ='commentCount' value=''>
<input type='hidden' name='pRegId' value='<%=USERID%>'>
<input type='hidden' name='pRegPasswd' value=''>
<input type='hidden' name='pRegEmail' value=''>
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
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pBbsType%>','<%=pCourseId%>','<%=contentsDto.getSeqNo()%>');
</script>
<%  //댓글 가능시 끝
	} %>

<%
	// 이전글, 다음글 표시 여부 허용일경우
    if (pViewPrevNextYn.equals("Y")) {
%>
											<tr>
												<td colspan="4" height="10"></td>
											</tr>

<%
		// 이전글
    	if (pPrevSeqNo > 0) {
%>
											<tr class="s_tab_lien01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">이전글</td>
												<td class="s_tab_view02" colspan="3"><a href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsShow&pBbsType=<%=pBbsType%>&pSeqNo=<%=pPrevSeqNo%>&curPage=<%=curPage%>"><%=StringUtil.setMaxLength(StringUtil.getHtmlContents(pPrevSubject),50)%></a></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%
		}
%>
<%
		// 다음 글
		if (pNextSeqNo > 0) {
%>
<%			if (pPrevSeqNo > 0) {	%>
											<tr class="s_tab_lien01">
												<td colspan="4"></td>
											</tr>
<%			} else {	%>

<%			}	%>
											<tr>
												<td class="s_tab_view01">다음글</td>
												<td class="s_tab_view02" colspan="3"><a href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsShow&pBbsType=<%=pBbsType%>&pSeqNo=<%=pNextSeqNo%>&curPage=<%=curPage%>"><%=StringUtil.setMaxLength(StringUtil.getHtmlContents(pNextSubject),50)%></a></td>
											</tr>
											<tr class="s_tab_lien02">
												<td colspan="4"></td>
											</tr>
<%
		}	//end if (pNextSeqNo > 0)
	}	//end if (pViewPrevNextYn.equals("Y"))
%>
										</table>
										<!-- // 게시판 리스트  끝 -->
<%
	if (pViewThreadYn.equals("Y") && listCnt > 1) {
%>
<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<!--tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">관련글 보기</td>
														</tr>
													</table>
												</td>
											</tr-->
											<tr>
												<td colspan="4" height="10"></td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="54"></td>
												<td class="s_tablien"></td>
												<td width="360">제목</td>
												<td class="s_tablien"></td>
												<td width="94">등록자</td>
												<td class="s_tablien"></td>
												<td width="94">등록일</td>
												<td class="s_tablien"></td>
												<td width="65">조회수</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	RowSet list= (RowSet)model.get("listRs");
  	String depthSpace = "";
  	int i	=	0;

	while(list.next()){
		i++;

		if( list.getInt("hit_no") > pHotChk )
			bbsHot	=	"&nbsp;<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_hot.gif' align='absmiddle'>";

		if(Integer.parseInt(StringUtil.nvl(list.getString("reg_date")).substring(0,8)) > Integer.parseInt(DateTimeUtil.getAddDate(-1)))
			bbsNew	=	"&nbsp;<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_new.gif' align='absmiddle'>";

		depthSpace = "";

		if(list.getInt("depth_no") > 0)
			depthSpace = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_re.gif' align='absmiddle'>";

		for(int j=0;j<list.getInt("depth_no");j++)
			depthSpace = "&nbsp;"+depthSpace;
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen">*</td>
												<td></td>
												<td class="s_tab04"><%=depthSpace%><a href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsShow&pBbsType=<%=pBbsType%>&pSeqNo=<%=list.getInt("seq_no")%>&curPage=<%=curPage%>"><%=StringUtil.setMaxLength(StringUtil.getHtmlContents(StringUtil.nvl(list.getString("subject"))),50)%></a><%=bbsNew%> <%=bbsHot%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("reg_name"))%></td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("reg_date")))%></td>
												<td></td>
												<td class="s_tab04_cen"><%=list.getInt("hit_no")%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
			bbsHot	=	"";
			bbsNew	=	"";
		}
%>
										</table>
										<!-- // 게시판 리스트  끝 -->
<%	} %>
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->

<%@include file="../common/course_footer.jsp" %>



