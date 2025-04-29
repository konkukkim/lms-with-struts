<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam,com.edutrack.common.dto.CodeParam,com.edutrack.common.dto.EditorParam"%> 
<%@ page import ="com.edutrack.courseforum.dto.CourseForumInfoDTO"%> 
<%@ page import = "com.edutrack.courseforum.dao.CourseForumUserDAO"%>
<%@include file="../common/course_header.jsp" %>
<%
   String pCourseId = (String)model.get("pCourseId");
   String pForumId  = (String)model.get("pForumId");
   String pForumType  = (String)model.get("pForumType");
//String sForumType        = pForumType.equals("A")? " 전체토론" : "조별토론";

//   int	pMySubForumId = 0;
///if(USERTYPE.equals("S")){
///     pMySubForumId	= Integer.parseInt((String)model.get("pMySubForumId"));
///}
   
   int 	curPage	= Integer.parseInt((String)model.get("curPage"));
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/common_util.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/popupbox.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/contextmenu.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/ajaxCommon.js"></script>

<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CourseForumTeamWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_forum/courseForumTeam.js"></script>


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
								<tr>
									<td align=right colspan="2" valign=middle>
<span id="addTeamAutoButton" Style="display:none" ><Script Language=javascript>Button5("토론팀자동생성", "divConComment('')", "");</Script></span>
<span id="addTeamButton"     Style="display:"><Script Language=javascript>Button5("토론팀추가"    , "manageInfo('Add')", "");</Script></span>
<span id="manageTeamButton"  Style="display:none" ><Script Language=javascript>Button5("토론팀관리"    , "manageTeam('0')", "");</Script></span>
<Script Language=javascript>Button5("목록"    , "goList()", "");</Script></span>
									</td>
								</tr>
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<div id=addTeamAutoComment Style="display:none">
                                            토론팀 자동생성은 ［강의실>팀관리］ 에서 생성되어 있는 팀 구조 그대로  <br><br>
                                            토론팀을 자동생성후에 팀별 추가/수정/삭제가 가능합니다.<br><br>
                                            계속 진행하시겠습니까?<br><br>
                                            
                                            <Script Language=javascript>Button2("계속진행"    , "registForumTeamAuto()", "");</Script>
                                            <Script Language=javascript>Button2("취소"    , "divConComment('none')", "");</Script>
										</div>
										<table width="670" align="center">
<!-- form start   onSubmit="return submitCheck()"  -->
<form name="Input" method="post" enctype="multipart/form-data">
<input type="hidden" name="pCourseId" value="<%=pCourseId%>">
<input type="hidden" name="pForumId" value="<%=pForumId%>">
<input type="hidden" name="pSeq" value="">
<input type="hidden" name="curPage" value="<%=curPage %>">
											<tr>
											    <td>
<div id="forumInfoWriteDiv" style="display:none">
													<table width="670" align="center">
        											<tr class="s_tab01">
        												<td colspan="2"></td>
        											</tr>
													<tr>
										        		<td class="s_tab_view01" width=150>토론팀명</td>
                                                        <td class="s_tab_view02">
															<input type=text name="pSubTeamName" value="" size=60 maxlength=100 dispName="토론팀명"  notNull  lenCheck="100">
                                                        </td>
													</tr>
        											<tr class="s_tab03">
        												<td colspan="2"></td>
        											</tr>
                                                    <tr>
                                                        <td class="s_tab_view01">주제</td>
                                                        <td class="s_tab_view02">
                                                            <input type=text name="pSubject" value="" size=60 maxlength=100 dispName="주제"  notNull  lenCheck="100">
                                                        </td>
                                                    </tr>
        											<tr class="s_tab03">
        												<td colspan="2"></td>
        											</tr>
                                                    <tr>
                                                        <td class="s_tab_view01">첨부파일</td>
                                                        <td class="s_tab_view02">
                                                            <input type="file" name="pFileName" size="60">
  											            </td>
                                                    </tr>
        											<tr class="s_tab03">
        												<td colspan="2"></td>
        											</tr>
                                                    <tr>
                                                        <td class="s_tab_view01">토론개요</td>
                                                        <td class="s_tab_view03"><textarea name="pContents" rows=5 cols="60" dispName="토론개요" notNull></textarea></td>
													</tr>
        											<tr class="s_tab05">
        												<td colspan="2"></td>
        											</tr>
													<tr>
                                                        <td colspan=2 align=right height="30" valign="middle">
<Script Language=javascript>Button3("등록", "registInfo('Add','')", "");</Script>
<Script Language=javascript>Button3("목록", "closeInfoWrite('')", "");</Script>
								                   	    </td>
													</tr>
        											</table>
</div>
													<br>
											    </td>
											</tr>
											<tr valign=top>
											    <td><div id="forumTeamListDiv" style="display:none"></div></td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						<!-- // 본문 -->
				  
<%@include file="../common/course_footer.jsp" %>



<Script Language="javascript">
    var popupbox1 = new PopupBox("팀관리", "style=normal,width=500,height=500, bgcolor=white, modal=yes,resizable=yes,move=yes,titlebar=yes,position=center,top=100,left=200");
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=CURRICODE%>','<%=CURRIYEAR%>','<%=CURRITERM%>','<%=pCourseId%>','<%=pForumId %>','<%=pForumType %>');
</Script>
