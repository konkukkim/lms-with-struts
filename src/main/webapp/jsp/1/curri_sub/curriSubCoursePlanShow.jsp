<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/course_header.jsp" %>
<%
	String pCourseId = (String)model.get("pCourseId");
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriSubCoursePlanWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriSubCourseWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/curri_sub/curriSubCoursePlan.js"></script>

								<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"강의계획서")%></b></font></td>
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
									</td>
									<!-- // history -->
								</tr>
								<tr valign="top">
									<td height="371" colspan="2" class="c_content_top">
									<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center" border=0>

											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" Style="width:125px">과목명</td>
												<td class="s_tab_view02" width="210" Style="width:210px">
<%
	if(COURSELISTSIZE > 1) {
		CodeParam param = new CodeParam();
		param.setSystemcode(SYSTEMCODE); 		// 시스템코드 설정
		param.setType("select"); 				// select, check, radio 유형을 선택한다.
		param.setName("pCourseId"); 				// 객체의 이름을 정한다.
		param.setEvent("autoReload()"); 	// onChange 이벤트가 발생했을 경우에 호출되어질 자바 스크립트 함수 정의
		param.setSelected(pCourseId); 				// 선택되어져야 할 값 선언

		out.print(CommonUtil.getCurriCourse(param, CURRICODE,CURRIYEAR,CURRITERM,PROFID));
	}else {
%>
<div id="courseNameDiv" style="width:100%;display:none"></div>
<%
	}
%>
												</td>
												<td class="s_tab_view01" Style="width:125px">담당교수</td>
												<td class="s_tab_view02" Style="width:210px"><div id="profNameDiv" style="width:100%;display:none"></div>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%	if(classUserType.equals("P")){ %>
											<tr>
												<td class="s_tab_view01" Style="width:125px">강의계획서</td>
												<td class="s_tab_view02" Style="width:210px">

<div id="modButtonDiv" style="display:block">
	<a href="javascript:openCoursePlanEdit();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_c.gif" border="0" align="absmiddle" class="no"></a>
	<a href="javascript:openUploadFilePopup()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_fileup.gif" height="19" border="0" align="absmiddle" class="no"></a>
</div>
<div id="regButtonDiv" style="display:none">
	<a href="javascript:registCoursePlan();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_e.gif" border="0" align="absmiddle" class="no"></a>
	<a href="javascript:showCoursePlan();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_d.gif" align="absmiddle" border="0"></a>
	<a href="javascript:openUploadFilePopup()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_fileup.gif" height="19" border="0" align="absmiddle" class="no"></a>
</div>
												</td>
												<td Style="width:335px" colspan="2">
<div id="attachFileDiv" style="display:none"></div>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%	} else if(classUserType.equals("S")) {  %>
											<tr>
												<td colspan="4" align="left">
<div id="attachFileDiv" style="display:none">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%	}	%>
											<tr>
												<td colspan="4">
<div id="coursePlanInfoDiv" style="width:100%;display:none"></div>
<div id="coursePlanWriteDiv" style="width:100%;display:none">
													<table width="100%" cellpadding="0" cellspacing="0" border="0">
<form name="Input">
														<tr>
															<td class="s_tab_view01" width="125"><input type="text" name="pInfoTitle1" value="" size="10" ></td>
															<td class="s_tab_view03"><textarea name="pInfoText1" rows="6" cols="85"></textarea></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td class="s_tab_view01"><input type="text" name="pInfoTitle2" value="" size="10" ></td>
															<td class="s_tab_view03"><textarea name="pInfoText2" rows="6" cols="85"></textarea></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td class="s_tab_view01"><input type="text" name="pInfoTitle3" value="" size="10" ></td>
															<td class="s_tab_view03"><textarea name="pInfoText3" rows="6" cols="85"></textarea></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td class="s_tab_view01"><input type="text" name="pInfoTitle4" value="" size="10" ></td>
															<td class="s_tab_view03"><textarea name="pInfoText4" rows="6" cols="85"></textarea></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td class="s_tab_view01"><input type="text" name="pInfoTitle5" value="" size="10" ></td>
															<td class="s_tab_view03"><textarea name="pInfoText5" rows="6" cols="85"></textarea></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="4"></td>
														</tr>

</form>
													</table>
</div>
												</td>
											</tr>

											<tr>
												<td class="s_tab_view01">평가비율</td>
												<td class="s_tab_view02" colspan="3">
<div id="courseInfoDiv" style="width:100%;display:none"></div>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01_01" colspan="4">담당교수정보</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view03" colspan="4">
													<!-- 담당교수정보 -->
													<table width="100%" border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td width="25%" valign="top" style="padding:5 5 5 25">
																<table width="121" height="140" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/photo.gif">
																	<tr>
																		<td align="center" valign="middle">
				<div id="profPhotoDiv" style="width:100%;display:none"></div>
																		</td>
																	</tr>
																</table>
															</td>
															<td width="75%" valign="top" style="padding:7 5 5 5">
																<table width="100%">
																	<tr valign="top">
																		<td width="20%" class="c_prof01">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet04.gif" width="11" height="9" align="baseline"> 교수명</td>
																		<td class="c_proflien">:</td>
																		<td width="80%" class="c_prof02">
				<div id="profName2Div" style="width:100%;display:none"></div>
																		</td>
																	</tr>
																	<tr valign="top">
																		<td width="20%" class="c_prof01">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet04.gif" width="11" height="9" align="baseline"> 학력</td>
																		<td class="c_proflien">:</td>
																		<td width="80%" class="c_prof02">
				<div id="profMajorDiv" style="width:100%;display:none"></div>
																		</td>
																	</tr>
																	<tr valign="top">
																		<td width="20%" class="c_prof01">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet04.gif" width="11" height="9" align="baseline"> 저서</td>
																		<td class="c_proflien">:</td>
																		<td width="80%" class="c_prof02">
				<div id="profBookDiv" style="width:100%;display:none"></div>
																		</td>
																	</tr>
																	<tr valign="top">
																		<td width="20%" class="c_prof01">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet04.gif" width="11" height="9" align="baseline"> 경력</td>
																		<td class="c_proflien">:</td>
																		<td width="80%" class="c_prof02">
				<div id="profCareerDiv" style="width:100%;display:none"></div>
																		</td>
																	</tr>
																	<tr valign="top">
																		<td width="20%" class="c_prof01">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet04.gif" width="11" height="9" align="baseline"> 자기소개</td>
																		<td class="c_proflien">:</td>
																		<td width="80%" class="c_prof02">
				<div id="profIntroDiv" style="width:100%;display:none"></div>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
													</table>
													<!-- // 담당교수정보 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<!--tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
												<script language=javascript>Button3("수정", "callFunc('OK')", "");</script></td>
											</tr-->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pCourseId%>','<%=COURSELISTSIZE%>');
</script>
<%@include file="../common/course_footer.jsp" %>
