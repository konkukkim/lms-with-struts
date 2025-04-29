<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.curribbs.dto.CurriBbsContentsDTO,com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam,com.edutrack.common.dto.CodeParam,com.edutrack.common.dto.EditorParam"%>
<%@include file="../common/course_header.jsp" %>
<%
	String 	pMode 		=  (String)model.get("pMode");
	String 	pBbsType 	= 	(String)model.get("pBbsType");
	String 	pFileUseYn 	= 	(String)model.get("pFileUseYn");
	int 	pFileCount	= 	Integer.parseInt((String)model.get("pFileCount"));

	CurriBbsContentsDTO 	contentsDto		= (CurriBbsContentsDTO)model.get("contentsDto");

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
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriBbsContentsWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/imageView.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/curri_bbs/curriBbsContents.js"></script>

<form name="bbsEditForm" method="post">
	<input type="hidden" name="CurriCode" value="<%=CURRICODE%>">
	<input type="hidden" name="CurriYear" value="<%=CURRIYEAR%>">
	<input type="hidden" name="CurriTerm" value="<%=CURRITERM%>">
	<input type="hidden" name="COURSELISTSIZE" value="<%=COURSELISTSIZE%>">
	<input type="hidden" name="pMode" value="<%=pMode%>">
</form>
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
<!-- form start  -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsRegist&pRegMode=Edit&pBbsType=<%=pBbsType%>&pSeqNo=<%=contentsDto.getSeqNo()%>&pRegDate=<%=StringUtil.nvl(contentsDto.getRegDate())%>" enctype="multipart/form-data">
<input type=hidden name="pMode"             value="<%=pMode%>">
<input type=hidden name="pBbsNo"            value="<%=contentsDto.getSeqNo()%>">
<input type=hidden name="pDepthNo"          value="<%=contentsDto.getDepthNo()%>">
<input type=hidden name="pOrderNo"          value="<%=contentsDto.getOrderNo()%>">
<input type=hidden name="pParNo"            value="<%=contentsDto.getParNo()%>">
<input type=hidden name="pEditorType"       value="<%=StringUtil.nvl(contentsDto.getEditorType())%>">
<input type=hidden name="pOldrFileName[1]"  value="<%=StringUtil.nvl(contentsDto.getRfileName1())%>">
<input type=hidden name="pOldsFileName[1]"  value="<%=StringUtil.nvl(contentsDto.getSfileName1())%>">
<input type=hidden name="pOldFilePath[1]"   value="<%=StringUtil.nvl(contentsDto.getFilePath1())%>">
<input type=hidden name="pOldFileSize[1]"   value="<%=StringUtil.nvl(contentsDto.getFileSize1())%>">
<input type=hidden name="pOldrFileName[2]"  value="<%=StringUtil.nvl(contentsDto.getRfileName2())%>">
<input type=hidden name="pOldsFileName[2]"  value="<%=StringUtil.nvl(contentsDto.getSfileName2())%>">
<input type=hidden name="pOldFilePath[2]"   value="<%=StringUtil.nvl(contentsDto.getFilePath2())%>">
<input type=hidden name="pOldFileSize[2]"   value="<%=StringUtil.nvl(contentsDto.getFileSize2())%>">
<input type=hidden name="pOldrFileName[3]"  value="<%=StringUtil.nvl(contentsDto.getRfileName3())%>">
<input type=hidden name="pOldsFileName[3]"  value="<%=StringUtil.nvl(contentsDto.getSfileName3())%>">
<input type=hidden name="pOldFilePath[3]"   value="<%=StringUtil.nvl(contentsDto.getFilePath3())%>">
<input type=hidden name="pOldFileSize[3]"   value="<%=StringUtil.nvl(contentsDto.getFileSize3())%>">
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">제목</td>
												<td class="s_tab_view02"><input name="pSubject" type="text" size="70" value="<%=StringUtil.nvl(contentsDto.getSubject())%>" notNull  dispName="제목" dataType="text" lenCheck="180"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
	if( !pBbsType.equals("notice") && ( USERTYPE.equals("M") || USERTYPE.equals("C") || USERTYPE.equals("P") ) ){
%>
											<tr>
												<td class="s_tab_view01">게시글타입</td>
												<td class="s_tab_view02">
													<input type=radio name="pContentsType" value="N" class="no" <% if(StringUtil.nvl(contentsDto.getContentsType()).equals("N")) out.print(" checked");%> style="border:0"> 공지
                                					<input type=radio name="pContentsType"  class="no" <% if(StringUtil.nvl(contentsDto.getContentsType()).equals("S")) out.print(" checked");%>  value="S" style="border:0" > 일반
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
	} else {
%>
                      					<input type="hidden" name="pContentsType" value="S">
<%	}	%>
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
	param.setSystemcode(SYSTEMCODE); 								// 시스템코드 설정
	param.setType("select"); 										// select, check, radio 유형을 선택한다.
	param.setName("pCourseId"); 									// 객체의 이름을 정한다.
	param.setFirst("-- 과목을 선택 하세요 --"); 							// 리스트 처음에 보여줄 문자를 입력한다.
	// param.setEvent("Change_Code(this)"); 						// onChange 이벤트가 발생했을 경우에 호출되어질 자바 스크립트 함수 정의
	// 아직 이벤트가 필요 없음
	param.setSelected(StringUtil.nvl(contentsDto.getCourseId()));	// 선택되어져야 할 값 선언

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
    	String rFileName = "";
    	String sFileName = "";
    	String FilePath  = "";
    	String FileSize  = "";

    for (int i=1;i<=pFileCount;i++) {
%>
											<tr>
												<td class="s_tab_view01">첨부 <%=i%></td>
												<td class="s_tab_view03">
<% out.print("<div id=\"pFile"+i+"\" style=\"display:none\"></div>"); %>
													<input name="pFile[<%=i%>]" type="file" size="70" id="pFile[<%=i%>]">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
		}
%>
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=COURSEID%>','<%=pBbsType%>','<%=contentsDto.getSeqNo()%>');
</script>
<%
	}
%>
											<tr>
												<td class="s_tab_view01">내용</td>
												<td class="s_tab_view03">
													<textarea name="pContents" rows=15 cols=70 wrap="VIRTUAL"><%=StringUtil.nvl(contentsDto.getContents())%></textarea>
<%
	EditorParam editerParam = new EditorParam();
	editerParam.setShowFlag("true");
	editerParam.setWidth(520);
	editerParam.setHeight(300);
	editerParam.setToolBarHidden("attachFile");

	out.print(CommonUtil.getEditorScript(editerParam));
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
&nbsp;<script language=javascript>Button3("삭제", "goDel()", "");</script>
												</td>
											</tr>
</form>
<!-- form -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/course_footer.jsp" %>

<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->