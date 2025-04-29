<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.util.Map, com.edutrack.common.UserBroker, com.edutrack.common.dto.UserMemDTO "%>
<% String CONTEXTPATH = request.getContextPath(); %>
<%
	Map model = (Map)request.getAttribute("MODEL");
	UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	String SYSTEMCODE = userMemDto.systemCode;


	String pCourseId = (String)model.get("pCourseId");
	String pTeamNo = (String)model.get("pTeamNo");
%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=euc-kr">
	<title></title>
	<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/form.css">
	<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/date_select1.css">
	<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/form_classroom.css">

	<Script Language="JavaScript" src = "<%=CONTEXTPATH%>/js/common1.js"></script>
	<script language=javascript src="<%=CONTEXTPATH%>/js/common_util.js"></script>
	<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/select_box_multi.js"></script>

	<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/engine.js"></script>
	<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/util.js"></script>
	<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CourseTeamWork.js"></script>

	<script type="text/javascript" src="<%=CONTEXTPATH%>/js/ajaxCommon.js"></script>
	<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_team/courseTeamMember.js"></script>
</head>


<body>
<table width="100%" align="center">

	<tr valign="top">
		<td>
			<!-- 내용 -->
			<!-- 게시판 리스트 시작 -->
			<table width="410" align="center">
				<tr valign="top">
					<!-- classroom title -->
					<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b>팀원관리</b></font></td>
					<!-- // classroom title -->
					<!-- history -->
					<td class="c_stit_history" valign="bottom" align="right" width="327">
					</td>
					<!-- // history -->
				</tr>
				<tr>
					<td colspan="2" class="content_top" valign="top"  align="center">
						<table width="100%" align="center">
<form name="Input">
							<tr class="s_tab05">
								<td colspan="3"></td>
							</tr>
							<tr>
								<td class="s_tab_view01" width="90">
									팀명
								</td>
								<td class="s_tab_view02">
									<div id="teamName"></div>
								</td>
								<td class="s_tab_view02" align="right">
									<select name="pTeamSelectList" ID="courseTeamSelect" onChange="changeCourseTeamList();"></select>
								</td>
							</tr>
							<tr class="s_tab05">
								<td colspan="3"></td>
							</tr>
						</table>
					</td>
				</tr>
</form>
				<tr>
					<td colspan="11" height="15"></td>
				</tr>
				<tr>
					<td colspan="11">
						<!-- 라운드 박스 -->
						<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
							<tr>
								<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_01.gif" width="7" height="6"></td>
								<td width="100%" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_05.gif"></td>
								<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_03.gif" width="7" height="6"></td>
							</tr>
							<tr>
							<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_07.gif"></td>
								<td align="center" valign="top"  style="padding:5 5 5 5">
<form name="AddForm" >
									<table width="440" border="0" cellspacing="0" cellpadding="0">
										<tr align="left" valign="top">
											<td width="200" height="20"><strong>- 대기구성원</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:courseStudentList('name','a')">이름▲</font></a>&nbsp;<a href="javascript:courseStudentList('id','a')">아이디▲</a></td>
											<td width="40" height="20">&nbsp;</td>
											<td width="200" height="20"><strong>- 현재구성원</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:teamMemberList('name','a')">이름▲</a>&nbsp;<a href="javascript:teamMemberList('id','a')">아이디▲</a></td>
										</tr>
										<tr>
											<td width="200">
												<table width="100%" border="0" cellspacing="3" cellpadding="5" class="b_td01">

													<tr>
														<td bgcolor="#FFFFFF">
															<table width="100%" height="217" border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td>
																		<!-- 대기학생 리스트 시작-->
																		<div id="studentList" style="width:100%;display:no"></div>
																		<!-- 대기학생 리스트 끝-->
																	</td>
																</tr>
															</table>
														</td>
													</tr>

												</table>
											</td>
											<td width="40" height="100%">
												<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td align="center" valign="top">
															<a href="javascript:addItem()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_next01.gif" vspace="8" alt="저장"></a><br>
															<a href="javascript:delItem()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_prev01.gif" vspace="0" alt="저장"></a><br>
															<a href="javascript:saveItem()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_save.gif" vspace="8" alt="저장"></a><br>
														</td>
													</tr>
												</table>
											</td>
											<td width="200">
												<table width="100%" border="0" cellspacing="3" cellpadding="5" class="b_td01">

													<tr>
														<td bgcolor="#FFFFFF">
															<table width="100%" height="217" border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td>
																		<!-- 팀멤버 리스트 시작-->
																		<div id="teamMemberList" style="width:100%;display:no"></div>
																		<!-- 팀멤버 리스트 끝-->
																	</td>
																</tr>
															</table>
														</td>
													</tr>

												</table>
											</td>
										</tr>
									</table>
</form>
<!-- add form end -->
								</td>
								<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_08.gif"></td>
							</tr>
							<tr>
								<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_02.gif" width="7" height="6"></td>
								<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_06.gif"></td>
								<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_04.gif" width="7" height="6"></td>
							</tr>
							<tr>
								<td height="10" colspan="3"></td>
							</tr>
							<tr>
								<td align="right" colspan="3"><a href="javascript:closeWin()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/b_co_close.gif"></a></td>
							</tr>
						</table>
						<!-- 라운드 박스 -->

					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pCourseId%>','<%=pTeamNo%>');
</script>
</body>
</html>


















