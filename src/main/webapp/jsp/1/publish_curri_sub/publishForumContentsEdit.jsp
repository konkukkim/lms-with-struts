<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam,com.edutrack.common.dto.CodeParam,com.edutrack.common.dto.EditorParam"%> 
<%@ page import ="com.edutrack.courseforum.dto.CourseForumInfoDTO"%> 
<%@ page import ="com.edutrack.courseforum.dto.CourseForumContentsDTO"%> 
<%@include file="../common/header.jsp" %>
<%  
	String	pCurriCode		=	StringUtil.nvl(model.get("pCurriCode"));
	int		pCurriYear		=	StringUtil.nvl(model.get("pCurriYear"), 0);
	int		pCurriTerm		=	StringUtil.nvl(model.get("pCurriTerm"), 0);
	String 	pCourseId 		= 	StringUtil.nvl(model.get("pCourseId"));
	int 	pForumId 		= 	StringUtil.nvl(model.get("pForumId"), 0);
	String	pGubun	 		= 	StringUtil.nvl(model.get("pGubun"));
		
	CourseForumInfoDTO courseForumInfo = (CourseForumInfoDTO)model.get("forumInfo");  		
	CourseForumContentsDTO contentsDto = (CourseForumContentsDTO)model.get("contentsDto");    
  	
	String pEditorYn			= courseForumInfo.getEditorYn();
	String pCommentUseYn		= courseForumInfo.getCommentUseYn();
	String pEmoticonUseYn	= courseForumInfo.getEmoticonUseYn();
	
	String	param		=	"&pCurriCode="+pCurriCode+"&pCurriYear="+pCurriYear+"&pCurriTerm="+pCurriTerm+"&pMode=Home&pGubun="+pGubun;
%>
<script language="javascript">
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
		/*  WEAS 삽입 스크립트3(시작) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS 삽입 스크립트3(끝) */
		var f = document.Input;
		if(validate(f)) f.submit();
		else return false;
	}
	function goDel(){
		document.location.href="<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsDelete&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>&pSeqNo=<%=contentsDto.getSeqNo()%>";
	}
	function delFile(){
       	document.location.href="<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsFileDelete&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>&pSeqNo=<%=contentsDto.getSeqNo()%>";
    }
	function goList(){
		document.location.href="<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsPagingList&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>";
	}
	
    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;        
       hiddenFrame.document.location = loc;
    }	
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start ----------->
<form name=Input method="post" action="<%=CONTEXTPATH%>/PublishCurriSub.cmd?cmd=publishForumContentsRegist&pRegMode=Edit&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>&pSeqNo=<%=contentsDto.getSeqNo()%>&pRegDate=<%=contentsDto.getRegDate()%><%=param%>" enctype="multipart/form-data" onSubmit="return submitCheck()">
<input type=hidden name="pBbsNo"        value='<%=contentsDto.getSeqNo()     %>'>
<input type=hidden name="pDepthNo"      value='<%=contentsDto.getDepthNo()   %>'>
<input type=hidden name="pLevelNo"      value='<%=contentsDto.getLevelNo()   %>'>
<input type=hidden name="pParentNo"     value='<%=contentsDto.getParentNo()  %>'>
<input type=hidden name="pEditorType"   value="<%=contentsDto.getEditorType()%>">
<input type=hidden name="pOldrFileName" value="<%=contentsDto.getRfileName() %>">
<input type=hidden name="pOldsFileName" value="<%=contentsDto.getSfileName() %>">
<input type=hidden name="pOldFilePath"  value="<%=contentsDto.getFilePath()  %>">
<input type=hidden name="pOldFileSize"  value="<%=contentsDto.getFileSize()  %>">
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="110">제목</td>
												<td class="s_tab_view02" colspan=3><input type=text name="pSubject" value="<%=contentsDto.getSubject()%>" size=70 maxlength=100 dispName="제목"  notNull dataType="text" lenCheck="180"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%	if (USERTYPE.equals("M") || USERTYPE.equals("C") || USERTYPE.equals("P") ) {	%>
											<tr>
												<td class="s_tab_view01" width="110">게시글타입</td>
												<td class="s_tab_view02" colspan=3>
    												<input type=radio name="pContentsType" value="N" style="border:0" class="no" <%=(contentsDto.getContentsType().equals("N")? " checked" : "")%>>공지
    												<input type=radio name="pContentsType" value="S" style="border:0" class="no" <%=(contentsDto.getContentsType().equals("N")? " checked" : "")%>>일반
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%	} else {	%>
										        <input type="hidden" name="pContentsType" value="S">
<%	}	%>
											<tr>
												<td class="s_tab_view01">내용</td>
												<td class="s_tab_view02" colspan=3>
													<textarea name="pContents" rows=10 cols=70  wrap="VIRTUAL" dispName="내용" notNull><%=contentsDto.getContents()%></textarea>
<%
	if (pEditorYn.equals("Y")) {
    	EditorParam editerParam = new EditorParam();
     	editerParam.setShowFlag("true");
		//editerParam.setWidth(650);
		editerParam.setToolBarHidden("attachFile");
        out.print(CommonUtil.getEditorScript(editerParam)); 
    }	
%> 
											    </td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">첨부파일</td>
												<td class="s_tab_view03" colspan=3>
<%
	if (!contentsDto.getSfileName().equals("")) {
		String rFileName = contentsDto.getRfileName();
		String sFileName = contentsDto.getSfileName();
		String FilePath 	= contentsDto.getFilePath();
		String FileSize 	= contentsDto.getFileSize();
		out.print("<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/ico_file.gif' align='absmiddle'>");
		out.print("<a href=\"javascript:fileDownload('"+rFileName+"','"+sFileName+"','"+FilePath+"','"+FileSize+"');\">"+rFileName+"</a>");
		out.print("&nbsp;&nbsp;");
		out.print("<a href=\"#\" onClick=\"javascript:delFile()\">[기존파일삭제]</a>");
		out.print("<br>");
	}
%>												
												    <input name="pFile" type="file" size="60">
											    </td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>											<tr>
												<td class="s_list_btn" height="30" align="right" colspan=4>
<Script Language=javascript>Button3("목록", "goList()", "");</Script>&nbsp;
<Script Language=javascript>Button3("수정", "submitCheck()", "");</Script>&nbsp;
												</td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
</form>
						</td>
						<!-- // 본문 -->

<%@include file="../common/footer.jsp" %>
<%if(pEditorYn.equals("Y")){%>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->
<%}%>

