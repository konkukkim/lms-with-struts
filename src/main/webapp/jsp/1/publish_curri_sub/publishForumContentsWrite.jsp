<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam,com.edutrack.common.dto.CodeParam,com.edutrack.common.dto.EditorParam"%> 
<%@ page import ="com.edutrack.courseforum.dto.CourseForumInfoDTO"%> 
<%@include file="../common/header.jsp" %>
<%
	String 	pRegMode 		= 	StringUtil.nvl(model.get("pRegMode"));
    String	pCurriCode		=	StringUtil.nvl(model.get("pCurriCode"));
    int		pCurriYear		=	StringUtil.nvl(model.get("pCurriYear"), 0);
    int		pCurriTerm		=	StringUtil.nvl(model.get("pCurriTerm"), 0);
	String 	pCourseId 		= 	StringUtil.nvl(model.get("pCourseId"));
	int 	pForumId 		= 	StringUtil.nvl(model.get("pForumId"), 0);
	String	pGubun	 		= 	StringUtil.nvl(model.get("pGubun"));
	
	CourseForumInfoDTO courseForumInfo = (CourseForumInfoDTO)model.get("forumInfo"); 
	String	pEditorYn		=	StringUtil.nvl(courseForumInfo.getEditorYn());
	String 	pCommentUseYn	= 	StringUtil.nvl(courseForumInfo.getCommentUseYn());
	String 	pEmoticonUseYn	= 	StringUtil.nvl(courseForumInfo.getEmoticonUseYn());
	
	String 	pBbsNo  		= "";
	String 	pDepthNo  		= "";
	String 	pLevelNo  		= "";
	String 	pParentNo  		= "";
	
	if(pRegMode.equals("Reply"))
	{
		pBbsNo  		=	StringUtil.nvl(model.get("pBbsNo"));
		pDepthNo  		=	StringUtil.nvl(model.get("pDepthNo"));
		pLevelNo  		=	StringUtil.nvl(model.get("pLevelNo"));
		pParentNo  		=	StringUtil.nvl(model.get("pParentNo"));
	}
	String	param		=	"&pCurriCode="+pCurriCode+"&pCurriYear="+pCurriYear+"&pCurriTerm="+pCurriTerm+"&pMode=Home&pGubun="+pGubun;
	
	String[]	randomStr	=	(String[])model.get("RandomStr");
	String		inputStr	=	"";
	for(int i=0; i<randomStr.length; i++) {
		inputStr += randomStr[i];
	}
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
		var inputStr = f.inputStr.value;
		
	   	if(inputStr != f.needBasicStr.value) {
			alert('도배방지 텍스트를 정확하게 입력해 주십시오.');
			f.needBasicStr.focus();
			return false;
		}
		if(validate(f)) f.submit();	
	}
	
	function goList(){
		var param1 = "&pCurriCode=<%=pCurriCode%>&pCurriYear=<%=pCurriYear%>&pCurriTerm=<%=pCurriTerm%>";
		var param2 = "&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>&pGubun=<%=pGubun%>";

		document.location.href="<%=CONTEXTPATH%>/PublishCurriSub.cmd?cmd=publishForumContentsPagingList&MENUNO=1&pMode=Home"+param1+param2;
	}
</Script>

										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<form name=Input method="post" action="<%=CONTEXTPATH%>/PublishCurriSub.cmd?cmd=publishForumContentsRegist&pRegMode=<%=pRegMode%>&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%><%=param%>" enctype="multipart/form-data" onSubmit="return submitCheck()">
<input type="hidden" name="curPage"     value="">
<input type="hidden" name="pEditorType" value="W">
<input type="hidden" name="pBbsNo"      value="<%=pBbsNo%>">
<input type="hidden" name="pDepthNo"    value="<%=pDepthNo%>">
<input type="hidden" name="pLevelNo"    value="<%=pLevelNo%>">
<input type="hidden" name="pParentNo"   value="<%=pParentNo%>">	
											<!-- tr>
												<td colspan="11" class="s_btn01" align="right">
<script language=javascript>Button5("과정리스트", "goCurriList()", "");</script>
&nbsp;<script language=javascript>Button5("강의목록", "goContents()", "");</script>
												</td>
											</tr-->
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="110">제목</td>
												<td class="s_tab_view02" colspan=3><input type=text name="pSubject" value="<%if(pRegMode.equals("Reply")){ %>[RE] <%=StringUtil.nvl(model.get("pSubject"))%><%}%>" size=70 maxlength=100 dispName="제목"  notNull dataType="text" lenCheck="180"></td>
											</tr>											
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%	if(USERID.equals("null") || USERID.equals("")) {	%>
											<tr>
												<td class="s_tab_view01" width="110">이름</td>
												<td class="s_tab_view02"><input type=text name="pRegName" value="" size=12 maxlength=40 dispName="이름"  notNull dataType="text" lenCheck="40"></td>
												<td class="s_tab_view01" width="110">비밀번호</td>
												<td class="s_tab_view02"><input type="password" name="pRegPasswd" value="" size=12 maxlength=40 dispName="비밀번호"  notNull dataType="text" lenCheck="40"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%	}
	if (USERTYPE.equals("M") || USERTYPE.equals("C") || USERTYPE.equals("P") ) {	%>
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
												<td class="s_tab_view03" colspan=3><textarea name="pContents" rows=10 cols=70  wrap="VIRTUAL" dispName="내용" notNull></textarea>
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
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td align="center"><input type="text" name="inputStr" value="<%=inputStr%>" size="6" style="border:0px;font-weight:bold;" readOnly></td>
												<td class="s_tab_view02"  colspan="3"><input type="text" name="needBasicStr" value="" notNull  dispName="도배방지 텍스트" dataType="text" size="15" lenCheck="5" maxlength='5'>
													* 도배방지를 위한 텍스트 코드를 입력해 주세요. </td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" height="30" align="right" colspan=4>
<Script Language=javascript>Button3("등록", "submitCheck()", "");</Script>
&nbsp;<Script Language=javascript>Button3("목록", "goList()", "");</Script>
												</td>
											</tr>
</form>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>

<%@include file="../common/footer.jsp" %>
<%if(pEditorYn.equals("Y")){%>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->
<%}%>