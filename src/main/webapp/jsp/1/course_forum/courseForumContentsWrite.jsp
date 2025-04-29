<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam,com.edutrack.common.dto.CodeParam,com.edutrack.common.dto.EditorParam"%> 
<%@ page import ="com.edutrack.courseforum.dto.CourseForumInfoDTO"%> 
<%@include file="../common/course_header.jsp" %>
<%  
	
	String pRegMode = (String)model.get("pRegMode");
	String pCourseId = (String)model.get("pCourseId");
	int pForumId = Integer.parseInt((String)model.get("pForumId"));
	
	CourseForumInfoDTO courseForumInfo = (CourseForumInfoDTO)model.get("forumInfo"); 
   String pVoiceYn			= courseForumInfo.getVoiceYn();
   String pEditorYn			= courseForumInfo.getEditorYn();
   String pCommentUseYn		= courseForumInfo.getCommentUseYn();
   String pEmoticonUseYn	= courseForumInfo.getEmoticonUseYn();
   String pViewThreadYn		= courseForumInfo.getViewThreadYn();
   String pViewPrevNextYn	= courseForumInfo.getViewPrevNextYn();
	
	String pBbsNo  = "";
	String pDepthNo  = "";
	String pLevelNo  = "";
	String pParentNo  = "";
	
	if(pRegMode.equals("Reply"))
	{
		pBbsNo  =(String)model.get("pBbsNo");
		pDepthNo  =(String)model.get("pDepthNo");
		pLevelNo  =(String)model.get("pLevelNo");
		pParentNo  =(String)model.get("pParentNo");
	}
	String 	bbsTitleImg	=	"cl_talk.gif";
%>
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
		<%if(pEditorYn.equals("Y")){%>
		/*  WEAS 삽입 스크립트3(시작) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS 삽입 스크립트3(끝) */
		<%}%>
		var f = document.Input;	
		
		if(validate(f)) f.submit();	
	}
	
	function goList(){
		var f = document.Input;
		var pCourseId = f.pCourseId.value;
		var pForumId	= f.pForumId.value;
		document.location.href="<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsPagingList&pCourseId="+pCourseId+"&pForumId="+pForumId;
	}
</Script>

								<tr valign="top"> 
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"토론방")%></b></font></td>
									<!-- // classroom title -->
									<!-- history -->
									<td class="c_stit_history" valign="bottom" align="right" width="327"> 
									<!-- // history -->
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (PMODE.equals("Search")) NAVIGATION = "홈 > 통합검색";
	if (NAVIGATION != "") {
		out.println(NAVIGATION) ;
	} // end if
%>
									</td>
									<!-- // history -->
								</tr>
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start ----------------->
<form name=Input method="post" action="<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsRegist&pRegMode=<%=pRegMode%>&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>" enctype="multipart/form-data" onSubmit="return submitCheck()">
 <input type="hidden" name="pCourseId"   value="<%=pCourseId%>">
 <input type="hidden" name="pForumId"    value="<%=pForumId%>">
 <input type="hidden" name="pEditorType" value="W">
 <input type="hidden" name="pBbsNo"      value="<%=pBbsNo%>">
 <input type="hidden" name="pDepthNo"    value="<%=pDepthNo%>">
 <input type="hidden" name="pLevelNo"    value="<%=pLevelNo%>">
 <input type="hidden" name="pParentNo"   value="<%=pParentNo%>">	
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="110">제목</td>
												<td class="s_tab_view02" colspan=3><input type=text name="pSubject" value="" size=70 maxlength=100 dispName="제목"  notNull dataType="text" lenCheck="180"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%	if (USERTYPE.equals("M") || USERTYPE.equals("C") || USERTYPE.equals("P") ) {	%>
											<tr>
												<td class="s_tab_view01" width="110">게시글타입</td>
												<td class="s_tab_view02" colspan=3>
    												<input type=radio name="pContentsType" value="N" style="border:0" class="no">공지
    												<input type=radio name="pContentsType" value="S" style="border:0" class="no" checked>일반
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
													<textarea name="pContents" rows=10 cols=70  wrap="VIRTUAL" dispName="내용" notNull></textarea>
<%
	if (pEditorYn.equals("Y")) {
		EditorParam editerParam = new EditorParam();
        editerParam.setShowFlag("true");
        //editerParam.setWidth(500);
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
												    <input name="pFile" type="file" size="60">
											    </td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>											<tr>
												<td class="s_list_btn" height="30" align="right" colspan=4>
                                                    <Script Language=javascript>Button3("목록", "goList()", "");</Script>&nbsp;
                                                    <Script Language=javascript>Button3("등록", "submitCheck()", "");</Script>&nbsp;
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
						
<%@include file="../common/course_footer.jsp" %>
<%if(pEditorYn.equals("Y")){%>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->
<%}%>		


