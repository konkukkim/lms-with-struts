<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.util.StringUtil"%> 
<%@ page import ="com.edutrack.courseforum.dto.CourseForumInfoDTO"%> 
<%@ page import ="com.edutrack.courseforum.dto.CourseForumContentsDTO"%> 
<%@ page import ="com.edutrack.courseforum.dto.CourseForumCommentDTO"%> 
<%@include file="../common/course_header.jsp" %>
<%  
	String pCourseId = (String)model.get("pCourseId");
	int pForumId = Integer.parseInt((String)model.get("pForumId"));

  	String ckWrite = "N";
   ckWrite	= (String)model.get("ckWrite");

		
	CourseForumInfoDTO courseForumInfo = (CourseForumInfoDTO)model.get("forumInfo");  		
	CourseForumContentsDTO contentsDto = (CourseForumContentsDTO)model.get("contentsDto");    
	     
	String userId = UserBroker.getUserId(request);
	String userType = UserBroker.getUserType(request);
	String regId = contentsDto.getRegId();
	int 	curPage	= Integer.parseInt((String)model.get("curPage"));
   

   String pVoiceYn			= courseForumInfo.getVoiceYn();
   String pEditorYn			= courseForumInfo.getEditorYn();
   String pCommentUseYn		= courseForumInfo.getCommentUseYn();
   String pEmoticonUseYn	= courseForumInfo.getEmoticonUseYn();
   String pViewThreadYn		= courseForumInfo.getViewThreadYn();
   String pViewPrevNextYn	= courseForumInfo.getViewPrevNextYn();
   
  
   String pStartDate			= courseForumInfo.getStartDate();
   String pEndDate			= courseForumInfo.getEndDate();
   
   int	StartDate			= 	Integer.parseInt(pStartDate.substring(0,8));
   int	EndDate				= 	Integer.parseInt(pEndDate.substring(0,8));
   int	NowDate				=	Integer.parseInt(DateTimeUtil.getDate());   
   
	String editorType = contentsDto.getEditorType();
	
	String pLoginChkYn = "Y";//과정게시물은 모두 로그인 한 사람이 사용할 수 있다.
	String chkRegPass = "F"; // 코멘트도 로그인 후 쓸 수있다.
	
	
	int pPrevSeqNo = 0;
	String pPrevSubject = "";
	int pNextSeqNo = 0; 
	String pNextSubject = "";
	if(pViewPrevNextYn.equals("Y")){
		 pPrevSeqNo = Integer.parseInt((String)model.get("pPrevSeqNo")); 
		 pPrevSubject = (String)model.get("pPrevSubject");
		 pNextSeqNo = Integer.parseInt((String)model.get("pNextSeqNo")); 
		 pNextSubject = (String)model.get("pNextSubject");
	}
	int listCnt = 1;
	if(pViewThreadYn.equals("Y")){
		listCnt = Integer.parseInt((String)model.get("listCnt"));
	}
	
	int pChildCnt = Integer.parseInt((String)model.get("pChildCnt")); 
	
    int pCommentCnt = 0;
    if(pCommentUseYn.equals("Y")){
    	pCommentCnt = Integer.parseInt((String)model.get("pCommentCnt"));
    }
	String CourseName = "";
	if(!pCourseId.equals("")){
		RowSet courseInfo = curriSubCoruseDao.getCurriSubCourseInfo(SYSTEMCODE,CURRICODE,CURRIYEAR,CURRITERM,pCourseId);
		courseInfo.next();
		CourseName = StringUtil.nvl(courseInfo.getString("course_name"));
	}
	
	String 	bbsTitleImg	=	"cl_talk.gif";

%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CourseForumCommentWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_forum/courseForumComment.js"></script>
<Script Language="javascript">
	function isEmpty(data)
	{
		for ( var i = 0 ; i < data.length ; i++ ) {
			if ( data.substring( i, i+1 ) != ' ' )
				return false;
		}
		return true;
	}

	function submitCheck()
	{
		var f = document.Input;
		
	 	if(!validate(f)) 
        	return false;
        else
            return true;     
		
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
							document.location = "<%=CONTEXTPATH%>/CourseForumComment.cmd?cmd=courseForumCommentDelete&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&pCommNo="+commNo;
					}
			<%}%>
		}else{
			var url = "<%=CONTEXTPATH%>/CourseForumComment.cmd?cmd=courseForumCommentPassChkForm&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&pCommNo="+commNo;
			window.open(url,"pass_win","top=100,left=162,toolbar=0,scrollbars=yes,directories=0,status=0,menubar=0,width=300,height=200,resizable=yes");
		}
	}
	function goDel(){
		<%if(pChildCnt>0 || pCommentCnt>0){%>
		alert('하위글 또는 코멘트가 있어서 삭제 하실 수 없습니다.');
		<%}else{%>
			<%if(userType.equals("M") || chkRegPass.equals("F"))
			  { //-- 로그인 필요한 경우
				if(userId.equals(""))
				{%>
					alert('로그인을 먼저 해 주세요');
			  <%}else if(!userId.equals(regId) && !userType.equals("M")){ %>
					alert('등록자가 아니면 삭제하실 수 없습니다.');
			  <%}else{%>
					document.location.href="<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsDelete&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>";
			  <%}%>
		    <%}else{ /* 로그인 필요없는경우 */    %>
					var url = "<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsPassChkForm&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>&pWorkMode=Del";
					window.open(url,"pass_win","top=100,left=162,toolbar=0,scrollbars=yes,directories=0,status=0,menubar=0,width=300,height=200,resizable=yes");
			<%}%>
		<%}%>
	}
	function goEdit(){
	<%if(editorType.equals("V")){%>
		alert('음성 게시글은 수정 할 수 없습니다.');
	<%}else if(pLoginChkYn.equals("Y")){ //-- 로그인 필요한 경우
		if(userId.equals("")){%>
			alert('로그인을 먼저 해 주세요');
	  <%}else if(!userId.equals(regId) && !userType.equals("M")){ %>
			alert('등록자가 아니면 수정하실 수 없습니다.');
	  <%}else{%>
			document.location.href="<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsEdit&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>";
	  <%}%>
    <%}else{ /* 로그인 필요없는경우 */    %>
			var url = "<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsPassChkForm&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>&pWorkMode=Edit";
			window.open(url,"pass_win","top=100,left=162,toolbar=0,scrollbars=yes,directories=0,status=0,menubar=0,width=300,height=200,resizable=yes");
	<%}%>
	}
	function goReply(){
	<%if(userId.equals("")){%>
		alert('로그인을 먼저 해 주세요');
	<%}else{
		if(editorType.equals("V") && !userId.equals("")){%>
		var chkRe = confirm("음성게시물로 답변 하시겠습니까?");
		if(chkRe)
			document.location.href="<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsVoiceWrite&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>";
		else
			document.location.href="<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsWrite&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>";
		<%}else{%>
		document.location.href="<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsWrite&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>";
	<%	}
	  }%>
	}
	function goList(){
		document.location.href="<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsPagingList&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>&curPage=<%=curPage%>";
	}
	
    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;        
       hiddenFrame.document.location = loc;
    }	
</Script>

							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"토론방")%></b></font></td>
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
												<td class="s_tab_view01" width="100">제목</td>
												<td class="s_tab_view02" width="230"><%=StringUtil.getHtmlContents(contentsDto.getSubject())%></td>
												<td class="s_tab_view01" width="100">과목명</td>
												<td class="s_tab_view02" width="230"><%=((COURSELISTSIZE == 1) ? COURSENAME : CourseName) %></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">작성자</td>
												<td class="s_tab_view02"><%=CommonUtil.getUserName(SYSTEMCODE, contentsDto.getRegId())%></td>
												<td class="s_tab_view01">등록일</td>
												<td class="s_tab_view02"><%=DateTimeUtil.getDateType(1,contentsDto.getRegDate())%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">첨부파일</td>
												<td class="s_tab_view02">
                                                <%	if (!contentsDto.getSfileName().equals("")) {
                                                    	String rFileName = contentsDto.getRfileName();
                                                        String sFileName = contentsDto.getSfileName();
                                                        String FilePath = contentsDto.getFilePath();
                                                        String FileSize = contentsDto.getFileSize();
                                                        out.print("<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/ico_file.gif' align='absmiddle'>");
                                                        out.print("<a href=\"javascript:fileDownload('"+rFileName+"','"+sFileName+"','"+FilePath+"','"+FileSize+"');\">"+rFileName+"</a>");
                                                        out.print("&nbsp;&nbsp;");
                                                	}	%>
												</td>
												<td class="s_tab_view01">조회수</td>
												<td class="s_tab_view02"><%=contentsDto.getHitNo()%></td>											
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">내용</td>
												<td class="s_tab_view03" colspan="3">
                                                <%
                                                	String VoicesFileName = "";
                                                	String VoiceFilePath = "";
                                                	
                                                	if (editorType.equals("W")) {	
                                                	    out.println(StringUtil.ReplaceAll(contentsDto.getContents(),"&quot;","\""));
                                                    } else if (editorType.equals("V")) {
                                                		VoicesFileName = contentsDto.getSfileName();
                                                		VoiceFilePath = "/data/"+contentsDto.getFilePath();	
                                                %>
                                                		<object classid="clsid:7B688F23-3382-46D1-98B7-CE2C15EED4F0"        	
                                                		codebase="/VoiceBbs/AxEduTrackViewer.ocx#version=0,0,0,9" width="500" height="400">
                                                		<param name="FileSrc" value="<%=VoiceFilePath + VoicesFileName%>">
                                                		<param name="ViewMode" value="0">
                                                		</object>
                                                <%	} else {	
                                                    out.println(StringUtil.getHtmlContents(contentsDto.getContents()));
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
 //댓글 가능시 시작
	if("Y".equals(pCommentUseYn)){
		if (USERTYPE.equals("M") ||  USERTYPE.equals("J") || USERTYPE.equals("P") || ( ckWrite.equals("Y") && USERTYPE.equals("S") &&  StartDate <= NowDate && NowDate <= EndDate )) {	%>
											<!-- 댓글테이블 -->
											<tr>
												<td colspan="4">
													<!-- 댓글 쓰기 -->
													<table width="100%" class="reply_form">
<!-- form start --> 
<form name=Input method="post" action="<%=CONTEXTPATH%>/CourseForumComment.cmd?cmd=courseForumCommentRegist" onSubmit="return submitCheck()">
<input type='hidden' name ='pCourseId' value='<%=pCourseId%>'>
<input type='hidden' name ='pForumId'   value='<%=pForumId%>'>
<input type='hidden' name ='pSeqNo'     value='<%=contentsDto.getSeqNo()%>'>
<input type='hidden' name ='curPage'    value='<%=curPage%>'>
<input type='hidden' name ='pEmoticon'  value=''>
<input type='hidden' name ='curPageComment' value=''>
														<tr>
															<td class="reply_w_icon">
																<table>
																	<tr>
																		<td width=32><a href="javascript:selectEmoticon('1')" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/emoticon/1.gif"></a></td>
																		<td width=32><a href="javascript:selectEmoticon('2')" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/emoticon/2.gif"></a></td>
																		<td width=32><a href="javascript:selectEmoticon('3')" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/emoticon/3.gif"></a></td>
																		<td width=32><a href="javascript:selectEmoticon('4')" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/emoticon/4.gif"></a></td>
																		<td width=32><a href="javascript:selectEmoticon('5')" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/emoticon/5.gif"></a></td>
																		<td width=32><a href="javascript:selectEmoticon('6')" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/emoticon/6.gif"></a></td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td class="reply_w_text01">
<!-- 선택된 표정 끝 -->
<input type='hidden' name='pRegId' value='<%=USERID%>'>
<input type='hidden' name='pRegPasswd' value=''>
<input type='hidden' name='pRegEmail' value=''>
<input type='hidden' name="pRegName"  value='<%=UserBroker.getUserName(request)%>' size="12">
<!-- 선택된 표정 시작 -->
<div id="selectedEmoticon" style="width:100%;display:block"></div>
<textarea rows=2 wrap=virtual cols=90 name="pContents" dispName="답변내용" lenCheck="2000" notnull></textarea>
<a href="javascript:addComment()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_record.gif" align="absmiddle" border="0" class="no"></a>
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
<%    }		%>													
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
<% 

} //댓글 가능시 끝
%>

											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
                                                    <script language=javascript>Button3("목록", "goList()", "");</script>&nbsp;
                                                    <script language=javascript>Button3("답글쓰기", "goReply()", "");</script>&nbsp;
                                                    <% if( USERTYPE.equals("M") || USERTYPE.equals("C") || USERTYPE.equals("P") || ( ckWrite.equals("Y") && USERTYPE.equals("S") &&  StartDate <= NowDate && NowDate <= EndDate ) ){%>
                                                    <script language=javascript>Button3("수정", "goEdit()", "");</script>&nbsp;
                                                    <script language=javascript>Button3("삭제", "goDel()", "");</script>&nbsp;
                                                    <%	}	%>
												</td>
											</tr>
                                            <%
                                            // 이전글, 다음글 표시 여부 허용일경우
                                            if (pViewPrevNextYn.equals("Y")) {
                                            
                                                // 이전글
                                                if (pPrevSeqNo > 0) {
                                            %>
											<tr class="s_tab_lien01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">이전글</td>
												<td class="s_tab_view02" colspan="3"><a href="<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsShow&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>&pSeqNo=<%=pPrevSeqNo%>&curPage=<%=curPage%>"><%=StringUtil.setMaxLength(StringUtil.getHtmlContents(pPrevSubject),50)%></a></td>
											</tr>
                                        <%
                                                }
                                                // only 이전글
                                                if(pNextSeqNo <= 0){
                                        %>
                                        	<tr class="s_tab_lien02">
												<td colspan="4"></td>
											</tr>

                                        <%      }
                                                // 다음 글
                                                if (pNextSeqNo > 0) {
                                        %>
                                        <%	    if (pPrevSeqNo > 0) {	%>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
                                        <%	    } else {	%>
											<tr class="s_tab_lien01">
												<td colspan="4"></td>
											</tr>
                                        <%			}	%>
											<tr>
												<td class="s_tab_view01">다음글</td>
												<td class="s_tab_view02" colspan="3"><a href="<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsShow&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>&pSeqNo=<%=pNextSeqNo%>&curPage=<%=curPage%>"><%=StringUtil.setMaxLength(StringUtil.getHtmlContents(pNextSubject),50)%></td>
											</tr>
											<tr class="s_tab_lien02">
												<td colspan="4"></td>
											</tr>
                                        <%
                                        		}	//end if (pNextSeqNo > 0)
                                        	}	//end if (이전글/다음글)
                                        %>
										</table>
										<!-- // 게시판 리스트  끝 -->
                                        <%  
                                            // 관련글...
                                        	if (pViewThreadYn.equals("Y") && listCnt > 1) {
                                        %>
                                        <br>
                                        <!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%"><b>[관련글 보기]</b></td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="54">*</td>
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
                                    		ArrayList list= (ArrayList)model.get("contentsList");
                                    		CourseForumContentsDTO ContentsListDto = null;	
                                    		String depthSpace = "";		
                                    		
                                    		for (int fc = 0; fc < list.size(); fc++) {
                                    			ContentsListDto	= (CourseForumContentsDTO)list.get(fc);
                                    			depthSpace = "";
                                    			
                                    			if (ContentsListDto.getDepthNo() > 0)
                                    				depthSpace = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button/icon_re.gif' width='26' height='11'>";
                                    			
                                    			for (int j=0;j<ContentsListDto.getDepthNo(); j++)
                                    				depthSpace = "&nbsp;"+depthSpace;
                                            %>  
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen">*</td>
												<td></td>
												<td class="s_tab04"><%=depthSpace %><a href="<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsShow&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>&pSeqNo=<%=ContentsListDto.getSeqNo()%>&curPage=<%=curPage%>"><%=StringUtil.setMaxLength(StringUtil.getHtmlContents(ContentsListDto.getSubject()),50)%></a></td>
												<td></td>
												<td class="s_tab04_cen"><%=CommonUtil.getUserName(SYSTEMCODE, ContentsListDto.getRegId())%></td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1,ContentsListDto.getRegDate())%></td>
												<td></td>
												<td class="s_tab04_cen"><%=ContentsListDto.getHitNo()%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
                                            <%		}	%>	
										</table>
										<!-- // 게시판 리스트  끝 -->
                                        <%	}	%>
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
						

<%@include file="../common/course_footer.jsp" %>
    
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pCourseId%>','<%=pForumId%>','<%=contentsDto.getSeqNo()%>','<%=USERID %>','<%=USERTYPE %>','<%=pCommentUseYn %>');
</script>