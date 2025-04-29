<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam,com.edutrack.common.dto.CodeParam,com.edutrack.common.dto.EditorParam"%> 
<%@ page import ="com.edutrack.courseforum.dto.CourseForumInfoDTO"%> 
<%@include file="../common/course_header.jsp" %>
<%
    String pRegMode   = (String)model.get("pRegMode");
    String pCourseId  = (String)model.get("pCourseId");
    String pForumId   = (String)model.get("pForumId");
    int curri_cnt	= 0;
    int forum_scnt	= 0;
    curri_cnt = Integer.parseInt((String)model.get("curri_cnt"));
    forum_scnt = Integer.parseInt((String)model.get("forum_scnt"));

	    int pParentForumId = 0; 
    pParentForumId = Integer.parseInt((String)model.get("pParentForumId"));
   
    CourseForumInfoDTO courseForumInfo = (CourseForumInfoDTO)model.get("courseForumInfo"); 

    if(("Add").equals(pRegMode)) courseForumInfo = new CourseForumInfoDTO();
   
%>
<script language="javascript">
	function submitCheck()
	{
		var f = document.Input;
		
		f.encoding ="multipart/form-data";
		f.action = "<%=CONTEXTPATH%>/CourseForumInfo.cmd?cmd=courseForumInfoRegist&pCourseId=<%=pCourseId%>&pRegMode=<%=pRegMode %>&pForumId=<%=pForumId %>";
		
	 	if(validate(f)) 
	 	    f.submit();
	}
	
	function cklForumType(type){
		var obj = document.getElementById('pRegistYn');
		
		if(type == "T"){
			obj.disabled = true;
		}
		if(type == "A"){
			obj.disabled = false;
		}		
	}


    // 이전목록으로 이동..
	function goListPage(){
		document.location="<%=CONTEXTPATH%>/CourseForumInfo.cmd?cmd=courseForumInfoPagingList&pCourseId=<%=pCourseId%>";
	}
	
	function goDel(){
		if(confirm("토론방을 삭제시 관련 회원정보와 토론게시판 \n\n그리고 코멘트 등 모두가 삭제됩니다. \n\n그래도 삭제하시겠습니끼?"))
		document.location = "<%=CONTEXTPATH%>/CourseForumInfo.cmd?cmd=courseForumInfoDelete&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>&pParentForumId=<%=courseForumInfo.getParentForumId()%>&pForumType=<%=courseForumInfo.getForumType()%>";
	}
	function delFile(){
       	document.location.href="<%=CONTEXTPATH%>/CourseForumInfo.cmd?cmd=courseForumInfoFileDelete&pCourseId=<%=pCourseId%>&pForumId=<%=pForumId%>";
    }
    

    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;        
       hiddenFrame.document.location = loc;
    }
    
	//분임토론이고 미지정수강생이 있을 경우
	function regist_check(){
		var f = document.Input;
		if( f.pForumType[1].checked == true && <%=curri_cnt-forum_scnt%> > 0 ){
			alert("미지정된 수강생이 있습니다.\n\n 참여자 지정후 등록해주세요.");
			var f = document.Input;
			f.pRegistYn[0].checked = false;
			f.pRegistYn[1].checked = true;
		}
	}
	
	

</script>


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
                                        <form name=Input method="post" enctype="multipart/form-data" onSubmit="return submitCheck()">          
						                <input type=hidden name=pParentForumId  value="<%=pParentForumId    %>">
                                        <input type=hidden name=pStartDate      value="<%=StringUtil.nvl(courseForumInfo.getStartDate() )%>">
                                        <input type=hidden name=pEndDate        value="<%=StringUtil.nvl(courseForumInfo.getEndDate()   )%>">
                                        <input type=hidden name=pVoiceYn        value="<%=StringUtil.nvl(courseForumInfo.getVoiceYn()   )%>">
                                        <input type=hidden name=pOldForumType   value="<%=StringUtil.nvl(courseForumInfo.getForumType() )%>">
                                        <input type=hidden name=pOldRegistYn    value="<%=StringUtil.nvl(courseForumInfo.getRegistYn()  )%>">
                                        <input type=hidden name=pOldRfileName   value="<%=StringUtil.nvl(courseForumInfo.getRfileName() )%>">
                                        <input type=hidden name=pOldSfileName   value="<%=StringUtil.nvl(courseForumInfo.getSfileName() )%>">
                                        <input type=hidden name=pOldFileSize    value="<%=StringUtil.nvl(courseForumInfo.getFileSize()  )%>">
                                        <input type=hidden name=pOldFilePath    value="<%=StringUtil.nvl(courseForumInfo.getFilePath()  )%>">
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="110">주제</td>
												<td class="s_tab_view02" colspan=3><input type=text name="pSubject" value="<%=StringUtil.nvl(courseForumInfo.getSubject()) %>" size=70 maxlength=100 dispName="주제"  notNull  lenCheck="100"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">배점</td>
												<td class="s_tab_view02">
                                                    <input type="text" name="pForumScore" value="<%=courseForumInfo.getForumScore() %>" size="5" maxlength=3 dispName="배점" notNull datatype=number maxValue='100'>
												</td>
												<td class="s_tab_view01">등록여부</td>
												<td class="s_tab_view02">
												    <%	if (("Y").equals(StringUtil.nvl(courseForumInfo.getRegistYn()))) {
                                                    	out.print(" 등록 ");
                                                    	out.print("<input type=hidden name='pRegistYn' value='Y'>");
                                                    
                                                    } else {	%>
													<input type="radio" name="pRegistYn"  id="pRegistYn" value="Y" class="no" <%=( ("Y").equals(StringUtil.nvl(courseForumInfo.getRegistYn())) ? "checked" :"") %> onclick="regist_check()">등록 
									                <input type="radio" name="pRegistYn"  id="pRegistYn" value="N" class="no" <%=(!("Y").equals(StringUtil.nvl(courseForumInfo.getRegistYn())) ? "checked" :"") %>>대기
									                <% }	%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">토론종류</td>
												<td class="s_tab_view02" colspan=3>
												    <table>
												        <tr>
												            <td height=30>
            												    <%	if (("Y").equals(StringUtil.nvl(courseForumInfo.getRegistYn()))) {
                                                                    	if (courseForumInfo.getForumType().equals("A")) {
                                                                    		out.print(" 전체토론");
                                                                    	
                                                                    	} else {
                                                                    		out.print(" 조별토론");
                                                                    	}	%>
                                                                <input type=hidden name="pForumType" value="<%=courseForumInfo.getForumType()%>">
                                                                <% } else {	%>
                                                                <input type="radio" name="pForumType" value="A" onclick="javascript:cklForumType('A')" class="no" <%=(!("T").equals(StringUtil.nvl(courseForumInfo.getForumType())) ? "checked" :"") %>>전체토론 
                                                                <input type="radio" name="pForumType" value="T" onclick="javascript:cklForumType('T')" class="no" <%=( ("T").equals(StringUtil.nvl(courseForumInfo.getForumType())) ? "checked" :"") %>>팀별토론
                                                                <% }	%>
                                                            </td>
                                                         </tr>
                                                         <tr>
                                                            <td>
                                                            <% if(("Edit").equals(pRegMode) && ("T").equals(StringUtil.nvl(courseForumInfo.getForumType())) ){ %>
                                                            <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/b_ico01.gif" align="absmiddle" width="11" height="17" border="0">
                                                                지정 <font color='blue'><%=forum_scnt%></font>명 / 미지정  <font color='red'><%=curri_cnt-forum_scnt%></font>명 / 전체  <font color='green'><%=curri_cnt%></font>명<Br>
                                                            <% }
                                                            if(!("Y").equals(StringUtil.nvl(courseForumInfo.getRegistYn())) ){ %>
                                                            <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/b_ico01.gif" align="absmiddle" width="11" height="17" border="0">
                                                                팀별 토론은 팀멤버를 모두 구성한 이후에 등록 완료를 할 수 있습니다. <br>
                                                                <span Style="line-height:20px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;☞&nbsp;<b>팀구성 :</b> 토론방 목록에서 <b>운영방식</b>을 클릭하면 팀과 멤버를 구성할수 있습니다.</span>
                                                            <% }	%>
                                                            </td>
                                                         </tr>
                                                     </table>   
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">토론기간</td>
												<td class="s_tab_view02" colspan="3">
                                                <%
                                                	DateParam dateParam = new DateParam();
                                                	dateParam.setCount(2);
                                                	dateParam.setDateType(1);
                                                	dateParam.setForm("Input");
                                                	dateParam.setDate("pDate");
                                                	dateParam.setYear("pYear");
                                                	dateParam.setMonth("pMonth");
                                                	dateParam.setDay("pDay");
                                                	dateParam.setHour("pHour");
                                                	dateParam.setMinute("pMinute");
                                                    out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("ds")));	
                                                %>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">웹에디터</td>
												<td class="s_tab_view02">
    												<input type=radio name="pEditorYn" value="Y" class="no" style="border:0" <%=( ("Y").equals(courseForumInfo.getEditorYn()) ? "checked" :"") %>> 사용
    												<input type=radio name="pEditorYn" value="N" class="no" style="border:0" <%=(!("Y").equals(courseForumInfo.getEditorYn()) ? "checked" :"") %>> 미사용 
												</td>
												<td class="s_tab_view01">공개여부</td>
												<td class="s_tab_view02">
													<input type="radio" name="pOpenYn" value="Y" class="no" <%=( ("Y").equals(courseForumInfo.getOpenYn()) ? "checked" :"") %>>공개 
									                <input type="radio" name="pOpenYn" value="N" class="no" <%=(!("Y").equals(courseForumInfo.getOpenYn()) ? "checked" :"") %>>비공개
												</td>											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>																						
											<tr>
												<td class="s_tab_view01">댓글기능</td>
												<td class="s_tab_view02">
    												<input type=radio name="pCommentUseYn" value="Y" class="no" style="border:0" <%=( ("Y").equals(courseForumInfo.getCommentUseYn()) ? "checked" :"") %>> 사용
    												<input type=radio name="pCommentUseYn" value="N" class="no" style="border:0" <%=(!("Y").equals(courseForumInfo.getCommentUseYn()) ? "checked" :"") %>> 미사용 
												</td>
												<td class="s_tab_view01">댓글이모티콘</td>
												<td class="s_tab_view02">
    												<input type=radio name="pEmoticonUseYn" value="Y" class="no" style="border:0" <%=( ("Y").equals(courseForumInfo.getEmoticonUseYn()) ? "checked" :"") %>> 사용
    												<input type=radio name="pEmoticonUseYn" value="N" class="no" style="border:0" <%=(!("Y").equals(courseForumInfo.getEmoticonUseYn()) ? "checked" :"") %>> 미사용 
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>																						
											<tr>
												<td class="s_tab_view01">관련글보기</td>
												<td class="s_tab_view02" colspan=3>
    												<input type=radio name="pViewThreadYn" value="Y" class="no" style="border:0" <%=( ("Y").equals(courseForumInfo.getViewThreadYn()) ? "checked" :"") %>> 보임
    												<input type=radio name="pViewThreadYn" value="N" class="no" style="border:0" <%=(!("Y").equals(courseForumInfo.getViewThreadYn()) ? "checked" :"") %>> 안보임 <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/b_ico01.gif" align="absmiddle" width="11" height="17" border="0">보기화면에서 관련 글 목록 표시
												</td>
											</tr>											
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>																						
											<tr>
												<td class="s_tab_view01">이전글<br>다음글</td>
												<td class="s_tab_view02" colspan=3>
    												<input type=radio name="pViewPrevNextYn" value="Y" class="no" style="border:0" <%=( ("Y").equals(courseForumInfo.getViewPrevNextYn()) ? "checked" :"") %>> 보임
    												<input type=radio name="pViewPrevNextYn" value="N" class="no" style="border:0" <%=(!("Y").equals(courseForumInfo.getViewPrevNextYn()) ? "checked" :"") %>> 안보임 <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/b_ico01.gif" align="absmiddle" width="11" height="17" border="0">보기화면에서 이전글, 다음글 표시
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>																						
											<tr>
												<td class="s_tab_view01">첨부파일</td>
												<td class="s_tab_view02" colspan=3>
<%	if (!StringUtil.nvl(courseForumInfo.getSfileName()).equals("")) {
		String rFileName = courseForumInfo.getRfileName();
	    String sFileName = courseForumInfo.getSfileName();
	    String FilePath = courseForumInfo.getFilePath();
	    String FileSize = courseForumInfo.getFileSize();
	    out.print("<a href=\"javascript:fileDownload('"+rFileName+"','"+sFileName+"','"+FilePath+"','"+FileSize+"');\">"+rFileName+"</a>"); 
	    out.print("&nbsp;&nbsp;");
	    out.print("<a href='#' onClick='javascript:delFile()'>[기존파일삭제]</a>");
		out.print("<br>");
	}	%>   												
												<input name="pFileName" type="file" size="60">
											    </td>
												
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>

											<tr>
												<td class="s_tab_view01">토론개요</td>
												<td class="s_tab_view03" colspan=3>
													<textarea name="pContents" rows=10 cols=70  wrap="VIRTUAL" dispName="토론개요" notNull><%=StringUtil.nvl(courseForumInfo.getContents())%></textarea>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" height="30" align="right" colspan=4>
                                                    <Script Language=javascript>Button3("목록", "goListPage()", "");</Script>&nbsp;
                                                    <% if(("Add").equals(pRegMode)){ %>
                                                    <Script Language=javascript>Button3("등록", "submitCheck()", "");</Script>&nbsp;
                                                    <% } else { %>
                                                    <Script Language=javascript>Button3("수정", "submitCheck()", "");</Script>&nbsp;
                                                    <Script Language=javascript>Button3("삭제", "goDel()", "");</Script>&nbsp;
                                                    <% } %>
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