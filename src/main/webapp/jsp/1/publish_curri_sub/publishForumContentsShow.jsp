<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.util.StringUtil"%> 
<%@ page import ="com.edutrack.courseforum.dto.CourseForumInfoDTO"%> 
<%@ page import ="com.edutrack.courseforum.dto.CourseForumContentsDTO"%> 
<%@ page import ="com.edutrack.courseforum.dto.CourseForumCommentDTO"%> 
<%@include file="../common/header.jsp" %>
<%  
	String	pCurriCode		=	StringUtil.nvl(model.get("pCurriCode"));
	int		pCurriYear		=	StringUtil.nvl(model.get("pCurriYear"), 0);
	int		pCurriTerm		=	StringUtil.nvl(model.get("pCurriTerm"), 0);
	String 	pCourseId 		= 	StringUtil.nvl(model.get("pCourseId"));
	int 	pForumId 		= 	StringUtil.nvl(model.get("pForumId"), 0);
	String	pGubun	 		= 	StringUtil.nvl(model.get("pGubun"));
	
	CourseForumInfoDTO 		courseForumInfo	= 	(CourseForumInfoDTO)model.get("forumInfo");  		
	CourseForumContentsDTO	contentsDto 	= 	(CourseForumContentsDTO)model.get("contentsDto");
	
	int 	curPage			= 	StringUtil.nvl(model.get("curPage"), 1);
	String 	pEditorYn		= 	courseForumInfo.getEditorYn();
	String 	pCommentUseYn	= 	courseForumInfo.getCommentUseYn();
	String 	pEmoticonUseYn	= 	courseForumInfo.getEmoticonUseYn();
	String 	editorType 		= 	contentsDto.getEditorType();
	
	int 	pChildCnt 		= 	StringUtil.nvl(model.get("pChildCnt"), 0);
    int 	pCommentCnt 	= 	0;
    if(pCommentUseYn.equals("Y")){
    	pCommentCnt		=	StringUtil.nvl(model.get("pCommentCnt"), 0);
    }
	String	pConRegName	=	StringUtil.nvl(contentsDto.getRegName());
	String	pConRegId	=	StringUtil.nvl(contentsDto.getRegId());
	if(pConRegName.equals("") && !pConRegId.equals("")) {
		pConRegName		=	CommonUtil.getUserName(SYSTEMCODE, pConRegId);
	}
    
    String	param		=	"&pCurriCode="+pCurriCode+"&pCurriYear="+pCurriYear+"&pCurriTerm="+pCurriTerm+"&pMode=Home&pGubun="+pGubun;
%>

<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/PublishCurriSubWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CourseForumContentsWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CourseForumCommentWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/publish_curri_sub/publishForumComment.js"></script>
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
	}

    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;        
       hiddenFrame.document.location = loc;
    }	
</Script>

										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<form name="f">
	<input type="hidden" name="pCurriCode"	value="<%=pCurriCode%>">
	<input type="hidden" name="pCurriYear" 	value="<%=pCurriYear%>">
	<input type="hidden" name="pCurriTerm" 	value="<%=pCurriTerm%>">
	<input type="hidden" name="pGubun"    	value="<%=pGubun%>">
	<input type="hidden" name ="curPage"    value='<%=curPage%>'>
	<input type="hidden" name ="pChildCnt"  value='<%=pChildCnt%>'>
	<input type="hidden" name ="pCommentCnt" value='<%=pCommentCnt%>'>
	<input type="hidden" name ="USERID" value='<%=USERID%>'>
</form>
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="100">제목</td>
												<td class="s_tab_view02" colspan="3" width="570"><%=StringUtil.getHtmlContents(contentsDto.getSubject())%></td>
												
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="100">작성자</td>
												<td class="s_tab_view02" width="235"><%=pConRegName%></td>
												<td class="s_tab_view01" width="100">등록일</td>
												<td class="s_tab_view02" width="235"><%=DateTimeUtil.getDateType(1,contentsDto.getRegDate())%></td>
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
<%=StringUtil.ReplaceAll(StringUtil.nvl(contentsDto.getContents()), "550px", "500px")%>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td colspan="4" height="35" align="right" valign="middle">
<script language=javascript>Button3("답글", "goReply()", "");</script>
<%	if((!USERID.equals("null") || !USERID.equals("")) && USERID.equals(pConRegId)) {	%>
&nbsp;<script language=javascript>Button3("수정", "goContentsEdit('Edit')", "");</script>
&nbsp;<script language=javascript>Button3("삭제", "goContentsEdit('Del')", "");</script>
<%	} else {	%>
&nbsp;<script language=javascript>Button3("수정", "goTaken('Edit')", "");</script>
&nbsp;<script language=javascript>Button3("삭제", "goTaken('Del')", "");</script>
<%	}	%>
&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
												</td>
											</tr>
<form name="f1">
<input type="hidden" name="pGoMode" value="">
											<tr>
												<td colspan="4" align="right" valign="middle">
<div id="regPassChkDiv" style="width:100%;display:none"><b>* 글 등록시 입력하신 비밀번호를 입력해 주세요.</b> <input type="password" name="regPass" size="12" notNull>
&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/bttn_confirm02.gif" align="absmiddle" onClick="goChkPass()" style="cursor:hand;"></div>
												</td>
											</tr>
</form>
											<tr>
												<td colspan="4" height="10"></td>
											</tr>

<%
 //댓글 가능시 시작
	if("Y".equals(pCommentUseYn)){ %>
											<!-- 댓글테이블 -->
											<tr>
												<td colspan="4">
													<!-- 댓글 쓰기 -->
													<table width="100%" border="0" class="reply_form">
<!-- form start --> 
<form name=Input method="post" action="<%=CONTEXTPATH%>/CourseForumComment.cmd?cmd=courseForumCommentRegist&pMode=Home" onSubmit="return submitCheck()">
<input type='hidden' name ='pCourseId' value='<%=contentsDto.getCourseId()%>'>
<input type='hidden' name ='pForumId'   value='<%=contentsDto.getForumId()%>'>
<input type='hidden' name ='pSeqNo'     value='<%=contentsDto.getSeqNo()%>'>
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
																		<td class="" width="478" align="right">
<%	if(USERID.equals("null") || USERID.equals("")) {	%>
이름 : <input type='text' name="pRegName" size="12" maxlength=40 dispName="이름"  notNull dataType="text" lenCheck="40" value=''>
&nbsp;비밀번호 : <input type='password' name='pRegPasswd' value='' size="12"  maxlength=40 dispName="비밀번호"  notNull dataType="text" lenCheck="40">
<%	} else {	%>
이름 : <input type='text' name="pRegName" size="12" value='<%=CommonUtil.getUserName(SYSTEMCODE, USERID)%>' readOnly>
<%	}	%>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td class="reply_w_text01" width="100%">
																<table width="100%" border="0" cellpadding="0" cellspacing="0">
																	<tr>
<td width="40" height="25"><div id="selectedEmoticon" style="width:100%;display:block"></div></td>
<td><textarea rows=2 wrap=virtual cols=90 name="pContents" dispName="답변내용" lenCheck="2000" notnull></textarea>
<%	if(USERID.equals("null") || USERID.equals("")) {	%>
&nbsp;<a href="javascript:addComment('<%=USERID%>')">
<%	} else {	%>
&nbsp;<a href="javascript:addComment('<%=USERID%>')">
<%	}	%>
<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_record.gif" align="absmiddle" border="0" class="no"></a></td>
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
<%
	} //댓글 가능시 끝
%>


										</table>
									</td>
								</tr>
							</table>
<%@include file="../common/footer.jsp" %>
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pCourseId%>','<%=pForumId%>','<%=contentsDto.getSeqNo()%>','<%=pCommentUseYn %>', '<%=USERTYPE%>');
</script>