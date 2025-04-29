<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="com.edutrack.common.DateSetter, com.edutrack.common.dto.DateParam" %>
<%@include file="../common/header.jsp" %>
<%
	String pCourseId = (String)model.get("pCourseId");
	String pCourseName = (String)model.get("pCourseName");
	String pContentsType = (String)model.get("pContentsType");
	String pFlagNavi = (String)model.get("pFlagNavi");
	String pContentsWidth = (String)model.get("pContentsWidth");
	String pContentsHeight = (String)model.get("pContentsHeight");
	//-- 교안유형 - 일반 : R, 스콤 : S, GVA : G
%>
<!--  파일 리스트 트리 띄우기 start  2007.04.24 sangsang  -->
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/common_util.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/popupbox.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/tree.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/contextmenu.js"></script>

<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ContentsWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/curri_top/contents.js"></script>

<%
	if (pContentsType.equals("S")) {
%>
<iframe width='0' height='0' frameborder='yes' scrolling='yes' src='<%=CONTEXTPATH%>/ScormStudy.cmd?cmd=scormAppletInclude&pCourseId=<%=pCourseId%>' marginwidth='n' marginheight='n' name='wwws'></iframe>
<%
	}
%>

<script language=javascript>
	// 팝업박스 객체 색성
	var popupbox1 = PopupBox("컨텐츠파일리스트", "width=480,height=360,style=normal,bgcolor=white,modal=no,resizable=no,move=yes,titlebar=yes,position=center");

// 컨텐츠관리 컨텍스트메뉴
<% if( CommonUtil.getAuthorCheck(request,  "C") || CommonUtil.getAuthorCheck(request,  "U") || CommonUtil.getAuthorCheck(request,  "D") )/* 권한체크 */  { %>
	var cMenu1 = ContextMenu("left");
<%	}	%>
<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %>
	addContextMenuItem(cMenu1, "목차 추가", "", "manageContents('W','CR')", "");
	addContextMenuItem(cMenu1, "서브목차 추가", "", "manageContents('W','C')", "");
<%	}	%>
<% if(CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  { %>
	addContextMenuItem(cMenu1, "목차 수정", "", "manageContents('E','C')", "");
<%	}	%>
<% if(CommonUtil.getAuthorCheck(request,  "D"))/* 권한체크 */  { %>
	addContextMenuItem(cMenu1, "목차 삭제", "", "registContents('D','C')", "");
<%	}	%>
<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %>
	addContextMenuItem(cMenu1);
	addContextMenuItem(cMenu1, "단원추가", "", "manageContents('W','F')", "");
<%	}	%>

<% if(CommonUtil.getAuthorCheck(request,  "U") || CommonUtil.getAuthorCheck(request,  "D") )/* 권한체크 */  { %>
	var cMenu2 = ContextMenu("left");
<%	}	%>
<% if(CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  { %>
	addContextMenuItem(cMenu2, "단원수정", "", "manageContents('E','F')", "");
<%	}	%>
<% if(CommonUtil.getAuthorCheck(request,  "D"))/* 권한체크 */  { %>
	addContextMenuItem(cMenu2, "단원삭제", "", "registContents('D','F')", "");
<%	}	%>
</script>


										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center" border="0">
<form method="post" name="WCMInput">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td valign="bottom" class="login_on"> <b>과목명 : <%=pCourseName%></b></td>
															<td align=right height=30>
<%
	if (pContentsType.equals("S")) {
%>
<script language=javascript>Button5("LCMS목차추가", "goLCMSList('<%=SYSTEMCODE%>', '', '', '', '<%=pCourseId%>')", "");</script>
<%
	}
%>
<% if(CommonUtil.getAuthorCheck(request,  "C") || CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button5("컨텐츠업로드", "goWebFtp('0')", "");</script><%	}	%>
<% if(CommonUtil.getAuthorCheck(request,  "C") || CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button5("동영상업로드", "goWebFtp('1')", "");</script><%	}	%>
<% if(CommonUtil.getAuthorCheck(request,  "C") || CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button5("자이닉스업로드", "goEszUpload('<%=pCourseId%>')", "");</script><%	}	%>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td colspan="4">
<!-- 목차(장) 입력 박스 시작 -->
<div id="contentsWriteChapter" style="width:100%;display:none">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr class="s_tab05">
															<td colspan="3"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">
																<div id="contentsSubject" style="width:100%;display:block">목 차</div>
																<div id="subContentsSubject" style="width:100%;display:none">서브목차</div>
															</td>
															<td class="s_tab_view02" width="300">
																<input type=text name="pContentsNameChapter" value="" size='60' maxlength="100">
															</td>
															<td valign="middle">
	<div id="chapterRegButton" style="display:block">
		<a href="javascript:registContents('W','C');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_e.gif" border="0" align="absmiddle"></a>&nbsp;<a href="javascript:closeContentsWrite('C');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_d.gif" border="0" align="absmiddle"></a>
		<!-- <script language=javascript>Button3("등록", "registContents('W','C')", "");</script>&nbsp;<script language=javascript>Button3("취소", "closeContentsWrite('C')", "");</script>&nbsp; -->
	</div>
	<div id="chapterModButton" style="display:none">
		<a href="javascript:registContents('E','C');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_c.gif" border="0" align="absmiddle"></a>&nbsp;<a href="javascript:closeContentsWrite('C');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_d.gif" border="0" align="absmiddle"></a>
		<!-- <script language=javascript>Button3("수정", "registContents('E','C')", "");</script>&nbsp;<script language=javascript>Button3("취소", "closeContentsWrite('C')", "");</script>&nbsp; -->
	</div>
															</td>
														</tr>
														<tr class="s_tab05">
															<td colspan="3"></td>
														</tr>
														<tr>
															<td colspan="3" height="10"></td>
														</tr>
													</table>
</div>
<!-- 목차(장) 입력 박스 끝-->

<!-- 목차(단원) 입력 박스 시작-->
<div id="contentsWrite" style="width:100%;display:none">
													<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0">
														<tr class="s_tab01">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">상위목차</td>
															<td class="s_tab_view02" width="200"><div id="parentContentsName" style="width:100%;display:none"></div></td>
															<td class="s_tab_view01" width="90">기준학습시간</td>
															<td class="s_tab_view02"><input type='text' name="pShowTime" value="" size="4" maxlength="3"> 분</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td class="s_tab_view01">목차명</td>
															<td class="s_tab_view02" colspan=3><input type=text name="pContentsName" value="" size='50' maxlength="100"></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td class="s_tab_view01">시작파일</td>
															<td class="s_tab_view02" colspan=3><div id="indexFileName" style="width:100%;display:none"></div></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td class="s_tab_view01">강의동영상</td>
															<td class="s_tab_view02" colspan=3><div id="asfFileName" style="width:100%;display:none"></div></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td class="s_tab_view01">강의교재</td>
															<td class="s_tab_view02" colspan=3><div id="textFileName" style="width:100%;display:none"></div></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td class="s_tab_view01">사이즈적용</td>
															<td class="s_tab_view02">
																<input class=no type=radio value="Y" name=pSizeApp><font class="s_text01">사용</font>&nbsp;&nbsp;&nbsp;
																<input class=no type=radio value="N" name=pSizeApp><font class="s_text02" checked>사용안함</font>
															</td>
															<td class="s_tab_view01">사이즈</td>
															<td class="s_tab_view02">
																폭 : <input type='text' name="pContentsWidth" value="1024" size='4' maxlength="4"> px &nbsp;&nbsp;높이 : <input type='text' name="pContentsHeight" value="768" size='4' maxlength="4"> px
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td class="s_tab_view01">강의타입</td>
															<td class="s_tab_view02" colspan="3">
<%
	CodeParam param1 = new CodeParam();
	param1.setSystemcode(SYSTEMCODE);
	param1.setType("select");
	param1.setName("pLectureGubun");
	param1.setSelected("");
	//param1.setEvent("showSetDate()");
	out.print(CommonUtil.getSoCode(param1,"110"));
%>
															</td>
														</tr>
														
														<tr class="s_tab05">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td class="s_list_btn" colspan="4" height="30" align="right">
	<div id="lessonRegButton" style="display:block">
		<script language=javascript>Button3("등록", "registContents('W','F')", "");</script>&nbsp;<script language=javascript>Button3("취소", "closeContentsWrite('F')", "");</script>
	</div>
	<div id="lessonModButton" style="display:none">
		<script language=javascript>Button3("수정", "registContents('E','F')", "");</script>&nbsp;<script language=javascript>Button3("취소", "closeContentsWrite('F')", "");</script>
	</div>
															</td>
														</tr>
													</table>
</div>
<!-- 목차(장) 입력 박스 끝-->
												</td>
											</tr>

											<tr>
												<td colspan="4">
													<table width="100%">
														<tr class="s_tab01">
															<td colspan="17"></td>
														</tr>
														<tr class="s_tab02">
															<td width="40">번호</td>
															<td class="s_tablien"></td>
															<td width="297">목차명</td>
															<td class="s_tablien"></td>
															<td width="80">강의구분</td>
															<td class="s_tablien"></td>
															<td width="80">미리보기</td>
															<td class="s_tablien"></td>
															<!--<td width="54">퀴즈수</td>
															<td class="s_tablien"></td>-->
															<td width="150">수강기준시간</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="17"></td>
														</tr>
														<tr>
															<td colspan="17">
																<!-- 리스트 -->
															  		<div id="contentsList" style="width:100%;display:no"></div>
															  	<!-- 리스트 -->
															</td>
														</tr>
														<tr class="s_tab05">
															<td colspan="17"></td>
														</tr>
														<tr height="30">
															<td colspan="17" align="right" valign="middle"><script language=javascript>Button3("목록", "goList()", "");</script></td>
														</tr>														
														<tr height="20">
															<td colspan="17" align="center" valign="bottom">* 강의구분은 온라인 강의와 오프라인 강의의 구분입니다 *</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>


</form>


<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pCourseId%>','<%=pCourseName%>','<%=pFlagNavi%>','<%=pContentsWidth%>','<%=pContentsHeight%>');
</script>
<%@include file="../common/footer.jsp" %>