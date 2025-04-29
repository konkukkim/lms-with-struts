<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.currisub.dto.CurriSubDTO"%>
<%@include file="../common/header.jsp" %>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriSubStaticInfoWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CompanyMasterCourseWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/companymaster/companyMasterCurriList.js"></script>
<%	int	pCurriYear	=	StringUtil.nvl(model.get("pCurriYear"), 0);	%>
							<table width="685" height="100%" align="right">
								<tr valign="top">
									<!-- sub title -->
									<td height="34" class="stit_blet"><div class="stit_title"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/title/mtit8_04.gif" alt="타이틀" width="247" height="34"></div></td>
									<!-- // sub title -->
									<!-- history -->
									<td class="stit_history" valign="bottom" align="right" width="368">
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
										<!-- 정규과정 시작 -->
										<!--table width="670" align="center">
											<tr>
												<td colspan="13" class="s_tabtit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" width="18" height="16" align="absmiddle">과정 현황</td>
											</tr>
										</table-->
										<table width="670" align="center">
											<tr>
												<td colspan="13" class="s_btn01">
													<table width="100%" align="center" border="0">
														<tr>
															<td width="100"><select onchange="changeYear()" name="pCurriYear">
																	<option value="">-- 선 택 --</option>
<%	int	pYear	=	Integer.parseInt(CommonUtil.getCurrentDate().substring(0, 4));
	for(int i=2007; i <= pYear; i++) {	%>
																	<option value="<%=i%>" <%if(pCurriYear == i){%>selected<%}%>><%=i%></option>
<%	} %>
																</select>&nbsp;&nbsp;
															</td>
															<td width="30" style="padding:0,0,0,0"><div id="curriTermList" style="width:100%;display:none"></div></td>
															<td width="" align="right">&nbsp;&nbsp;<select name="pCurriCode" onChange="changeCurriCode()"></select>&nbsp;</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
<div id="companyCurriDiv" style="width:100%;display:block">
										<table width="670" align="center">
											<tr class="s_tab01">
												<td colspan="13"></td>
											</tr>
											<tr class="s_tab02">
												<td width="297">과정명</td>
												<td class="s_tablien"></td>
												<td width="70">수강중인<br>학생</td>
												<td class="s_tablien"></td>
												<td width="70">수료생</td>
												<td class="s_tablien"></td>
												<td width="80">미수료생</td>
												<td class="s_tablien"></td>
												<td width="80">총수강생</td>
												<td class="s_tablien"></td>
												<td width="70">상태</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="13"></td>
											</tr>
											<tr>
												<td colspan="13">
													<!-- 진행중인 과정 리스트 -->
                      								<div id="masterCurriList" style="width:100%;display:none"></div>
                      								<!-- 진행중인 과정 리스트 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="13"></td>
											</tr>
										</table>
</div>
<div id="studentCurriDiv" style="width:100%;display:none">
										<table width="670" align="center">
											
											<tr class="s_tab01">
												<td colspan="13"></td>
											</tr>
											<tr class="s_tab02">
												<td width="247">학생명</td>
												<td class="s_tablien"></td>
												<td width="70">시험</td>
												<td class="s_tablien"></td>
												<td width="70">과제</td>
												<td class="s_tablien"></td>
												<td width="70">출석</td>
												<td class="s_tablien"></td>
												<td width="70">토론</td>
												<td class="s_tablien"></td>
												<td width="70">총점</td>
												<td class="s_tablien"></td>
												<td width="70">수료사항</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="13"></td>
											</tr>
											<tr>
												<td colspan="13">
													<!-- 진행중인 과정 리스트 -->
                      								<div id="studentCurriList" style="width:100%;display:none"></div>
                      								<!-- 진행중인 과정 리스트 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="13"></td>
											</tr>
											<tr>
												<td colspan="13" height="50">
													<table width="100%" align="center">
														<tr>
															<td align="right" valign="bottom">
<script language=javascript>Button3("목록", "goList()", "");</script>&nbsp;
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
</div>
										<!-- // 정규과정 끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>');
</script>
<%@include file="../common/footer.jsp" %>