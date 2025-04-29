<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.DateSetter, com.edutrack.common.dto.DateParam"%>
<%@include file="../common/header.jsp" %>
<%
	String pCurriCode = (String)model.get("pCurriCode");
	String pCurriYear = (String)model.get("pCurriYear");
	String pCurriTerm = (String)model.get("pCurriTerm");
	String pCourseId = (String)model.get("pCourseId");
	String pCourseName = (String)model.get("pCourseName");
	String pContentsType = (String)model.get("pContentsType");
	String pFlagNavi = (String)model.get("pFlagNavi");
	String pContentsWidth = (String)model.get("pContentsWidth");
	String pContentsHeight = (String)model.get("pContentsHeight");
	int pQuizCnt = Integer.parseInt((String)model.get("pQuizCnt"));
	int pContentsCnt = Integer.parseInt((String)model.get("pContentsCnt"));
	//-- 교안유형 - 일반 : R, 스콤 : S, GVA : G
%>
<!--  파일 리스트 트리 띄우기 start  2007.04.24 sangsang  -->
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/common_util.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/popupbox.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/tree.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/contextmenu.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriContentsWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriQuizWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/curri_sub/curriContents.js"></script>

<%
	if (pContentsType.equals("S")) {
%>
<iframe width='0' height='0' frameborder='yes' scrolling='yes' src='<%=CONTEXTPATH%>/ScormStudy.cmd?cmd=scormAppletInclude&pCourseId=<%=pCourseId%>' marginwidth='n' marginheight='n' name='wwws'></iframe>
<%
	}
%>


<Script Language=javascript>
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
	
</Script>
							
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center" border="0">
<form method="post" name="WCMInput">
<input type="hidden" name="pCurriCode" value="<%=pCurriCode%>">
<input type="hidden" name="pCurriYear" value="<%=pCurriYear%>">
<input type="hidden" name="pCurriTerm" value="<%=pCurriTerm%>">
<input type="hidden" name="pQuizCnt" value="<%=pQuizCnt%>">
<input type="hidden" name="pContentsCnt" value="<%=pContentsCnt%>">
											<tr>
												<td colspan="17" class="s_btn01">
													<table width="100%" align="center" border="0">
														<tr>
															<td valign="bottom" class="login_on"> <b>과목명 : <%=pCourseName%></b></td>
															<td align="right">
<% if(CommonUtil.getAuthorCheck(request,  "C") || CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button5("컨텐츠업로드", "goWebFtp('0')", "");</script>&nbsp;<script language=javascript>Button5("동영상업로드", "goWebFtp('1')", "");</script>&nbsp;<script language=javascript>Button5("자이닉스업로드", "goEszUpload('<%=pCourseId%>')", "");</script><%	}	%>
															</td>
															<td id="addContentsTd" align="right" width="0">
<div id="addContentsAutoDiv" style="width:100%;display:none">
<% if(CommonUtil.getAuthorCheck(request,  "C") || CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button5("컨텐츠이월", "addContentsAuto()", "");</script><%	}	%>
</div>
															</td>
															<!--td id="addQuizTd" align="right" width="0">
<div id="addQuizAutoDiv" style="width:0%;display:none">
<% if(CommonUtil.getAuthorCheck(request,  "C") || CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button5("단원평가이월", "addQuizAuto()", "");</script><%	}	%>
</div>
															</td-->
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
															<td class="s_tab_view01" width="100">상위목차</td>
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
	param1.setEvent("showSetDate()");
	out.print(CommonUtil.getSoCode(param1,"110"));
%>
															</td>
<!--td class="s_tab_view01">Offline 날짜</td>
<td class="s_tab_view02"><input maxLength=4 size=4> 년 <input maxLength=2 size=2> 월 <input maxLength=2 size=2> 일 <img src="../img/1/button_img/btn_date.gif" width="45" height="17" align="absmiddle"></td-->
														</tr>
														<tr class="s_tab03">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">기간설정</td>
															<td class="s_tab_view02" width="550" colspan="3">
	<div id="offlineDateSetDiv" style="display:none">
<%
	DateParam dateParam = new DateParam();
	dateParam.setCount(2);
	dateParam.setDateType(0);
	dateParam.setDate("pOfflineDate");
	dateParam.setYear("pOffYY");
	dateParam.setMonth("pOffMM");
	dateParam.setDay("pOffDD");
	dateParam.setHour("pOffHour");
	dateParam.setMinute("pOffMinute");
	out.print(CommonUtil.getCalendar(dateParam, new DateSetter() ));
%>
	</div>
	<div id="onlineDateSetDiv" style="display:block">
<%
	DateParam dateParam1 = new DateParam();
	dateParam1.setCount(2);
	dateParam1.setDateType(1);
	dateParam1.setDate("pOnlineDate");
	dateParam1.setYear("pYY");
	dateParam1.setMonth("pMM");
	dateParam1.setDay("pDD");
	out.print(CommonUtil.getCalendar(dateParam1, new DateSetter() ));
%>
	</div>
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
													<table>
														<tr class="s_tab01">
															<td colspan="17"></td>
														</tr>
														<tr class="s_tab02">
															<td width="40">번호</td>
															<td class="s_tablien"></td>
															<td width="307">목차명</td>
															<td class="s_tablien"></td>
															<td width="70">강의구분</td>
															<td class="s_tablien"></td>
															<td width="70">미리보기</td>
															<td class="s_tablien"></td>
															<td width="139">강의기간</td>
															<td class="s_tablien"></td>
															<td width="70">수강기준<br>시간</td>
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
														<tr>
															<td colspan="17" align="right" valign="middle" height="35"><script language=javascript>Button3("과목목록", "goCourseList()", "");</script></td>
														</tr>
														<tr height="50">
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

