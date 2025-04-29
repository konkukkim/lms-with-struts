<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam,com.edutrack.common.dto.CodeParam,com.edutrack.common.dto.EditorParam"%>
<%@include file="../common/course_header.jsp" %>
<%
	String 	pRegMode 		= 	(String)model.get("pRegMode");
	String 	pBbsType 		= 	(String)model.get("pBbsType");
	String 	pFileUseYn 		= 	(String)model.get("pFileUseYn");
	int 		pFileCount 		= 	Integer.parseInt((String)model.get("pFileCount"));
	String 	pEditorYn 		= 	(String)model.get("pEditorYn");
	String 	pBadWordUseYn 	= 	(String)model.get("pBadWordUseYn");
	String 	pBadWord 		= 	(String)model.get("pBadWord");
	String 	pBbsNo  			= 	"";
	String 	pDepthNo  		= 	"";
	String 	pOrderNo  		= 	"";
	String 	pParNo  			= 	"";
	String 	pCourseId 		= 	"";
	String 	bbsTitleImg	    =   "ctit3_01.gif";
	String	pContentsType	=	"S";
	if(pRegMode.equals("Reply"))
	{
		pBbsNo  =(String)model.get("pBbsNo");
		pDepthNo  =(String)model.get("pDepthNo");
		pOrderNo  =(String)model.get("pOrderNo");
		pParNo  =(String)model.get("pParNo");
		pCourseId = request.getParameter("pCourseId");
		pContentsType	=	(String)model.get("pContentsType");
	}

	String 	bbs = "";
	if(pBbsType.equals("notice")){
			bbs = "공지사항";
			bbsTitleImg	    =   "ctit3_01.gif";
	}else	if(pBbsType.equals("bbs")){
			bbs = "게시판";
			bbsTitleImg	    =   "ctit4_01.gif";
	}else	if(pBbsType.equals("qna")){
			bbs = "Q&A";
			bbsTitleImg	    =   "ctit6_01.gif";
	}else	if(pBbsType.equals("pds")){
			bbs = "자료실";
			bbsTitleImg	    =   "ctit5_01.gif";
	}

	String sContentsType = pContentsType.equals("S") ? "일반" : "공지";
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
	<%if(pBadWordUseYn.equals("Y") && !pBadWord.equals("")){%>
	//유해어 체크
    function CheckBkWord(SearchStr, field)
    {
      	var bklist = "<%=pBadWord%>";
      	var currentstring = "";

      	for( var i = 0; i < bklist.length; i++ ) {
          thischar = bklist.charAt( i );
          if( thischar == ',' ){
          	index = SearchStr.indexOf( currentstring,0 );
    		    if( index > -1 ) {
    		        alert( field+"에 유해어 '"+ currentstring +"'가 포함되어 있습니다. \r\n" +
    		               "고운말을 써 주세요.");
    		        return  false ;
    		    }
    		    currentstring = "";
          }else{
          	currentstring += thischar;
          }
        }

        return  true ;
    }
	<%}%>
	function submitCheck()
	{
		<%if(pEditorYn.equals("Y")){%>
		/*  WEAS 삽입 스크립트3(시작) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS 삽입 스크립트3(끝) */
		<%}%>
		var f = document.Input;
	<%//	if(COURSELISTSIZE > 1 ) { %>
/*
		if(f.pCourseId[f.pCourseId.selectedIndex].value == '') {
			alert('과목을 선택하세요');
			f.pCourseId.focus();
			return false;
		}
*/
	<%//	} %>
	<%if(pBadWordUseYn.equals("Y") && !pBadWord.equals("")){%>
		 if (CheckBkWord(f.pSubject.value,'제목') == false){
        	f.pSubject.focus();
        	return false;
       }
       if (CheckBkWord(f.pContents.value,'내용') == false){
        	return false;
       }
	<%}%>

		if(!validate(f))
	   	   return false;
//	   	else
//	   		return true;
		f.submit();
	}
	function goList(){
		document.location.href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsPagingList&pBbsType=<%=pBbsType%>";
	}
</Script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,bbs)%></b></font></td>
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
<!-- form start   onSubmit="return submitCheck()"  -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsRegist&pRegMode=<%=pRegMode%>&pBbsType=<%=pBbsType%>" enctype="multipart/form-data">
<input type="hidden" name="pEditorType" value="W">
<input type="hidden" name="pBbsNo" value="<%=pBbsNo%>">
<input type="hidden" name="pDepthNo" value="<%=pDepthNo%>">
<input type="hidden" name="pOrderNo" value="<%=pOrderNo%>">
<input type="hidden" name="pParNo" value="<%=pParNo%>">

											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">제목</td>
												<td class="s_tab_view02"><input name="pSubject" type="text" size="70" notNull  dispName="제목" dataType="text" lenCheck="180"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
	if(!pBbsType.equals("notice") && ( USERTYPE.equals("M") || USERTYPE.equals("C") || USERTYPE.equals("P") ) ){
%>
											<tr>
												<td class="s_tab_view01">게시글타입</td>
												<td class="s_tab_view02">
<%
		if(pRegMode.equals("Reply")){
%>
               										<%=sContentsType%>
                 									<input type="hidden" name="pContentsType" value="<%=pContentsType%>">
<%
		} else {
%>
                      								<input type=radio name="pContentsType" value="N" class="no" style="border:0"> 공지 &nbsp;<input type=radio name="pContentsType" value="S" class="solid0" style="border:0" checked> 일반
<%
		}
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
	} else {
%>
											<tr>
												<td colspan="2"><input type="hidden" name="pContentsType" value="S"></td>
											</tr>
<%
	}
%>
<%
	if(COURSELISTSIZE == 1 ){
		out.print("<input type='hidden' name='pCourseId' value='" + COURSEID + "'>");

	} else {
		out.print("<input type='hidden' name='pCourseId' value=''>");
	}
%>
<!--
											<tr>
												<td class="s_tab_view01">과목명</td>
												<td class="s_tab_view02">
<%
		CodeParam param = new CodeParam();
		param.setSystemcode(SYSTEMCODE); 				// 시스템코드 설정
		param.setType("select"); 						// select, check, radio 유형을 선택한다.
		param.setName("pCourseId"); 					// 객체의 이름을 정한다.
		param.setFirst("-- 과목을 선택 하세요 --"); 			// 리스트 처음에 보여줄 문자를 입력한다.
		// param.setEvent("Change_Code(this)"); 		// onChange 이벤트가 발생했을 경우에 호출되어질 자바 스크립트 함수 정의
		// 아직 이벤트가 필요 없음

		if(pRegMode.equals("Reply")){
		   param.setSelected(pCourseId); 				// 선택되어져야 할 값 선언
		}
		out.print(CommonUtil.getCurriCourse(param, CURRICODE,CURRIYEAR,CURRITERM,PROFID));
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
-->
<%
	if (pFileUseYn.equals("Y")) {
    	for (int i=1;i<=pFileCount;i++) {
%>
											<tr>
												<td class="s_tab_view01">첨부 <%=i%></td>
												<td class="s_tab_view02"><input name="pFile[<%=i%>]" type="file" size="70" id="pFile[<%=i%>]"></td>
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
													<textarea name="pContents" rows=15 cols=70  wrap="VIRTUAL"></textarea>
<%
	if(pEditorYn.equals("Y")){
		EditorParam editerParam = new EditorParam();
		editerParam.setShowFlag("true");
		editerParam.setWidth(520);
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
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/course_footer.jsp" %>

<%if(pEditorYn.equals("Y")){%>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->
<%}%>













