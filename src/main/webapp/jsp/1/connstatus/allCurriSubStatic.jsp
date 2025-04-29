<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@include file="../common/header.jsp" %>
<%
	int	pCurriYear	=	StringUtil.nvl(model.get("pCurriYear"), 2007);
	int	pCurriTerm	=	StringUtil.nvl(model.get("pCurriTerm"), 0);
	int	DispLine	=	StringUtil.nvl(model.get("DispLine"), 10);
	int	curPage		=	StringUtil.nvl(model.get("curPage"), 1);
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriSubStaticInfoWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/connstatus/curriSubStaticInfo.js"></script>

<!-- FORM START -->
<form name="f">
<input type="hidden" name="curPage" value="<%=curPage%>">
<input type="hidden" name="pCurriCode" value="">
<input type="hidden" name="curriTerm" value="">
<input type="hidden" name="pAgeYn" value="">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
<div id="infoListDiv" style="width:100%;display:block">
										<table width="670" align="center">
											<tr>
												<td colspan="27" class="s_btn01">
													<table width="100%" align="center" border="0">
														<tr>
															<td width="225">
																개설년도 :
															<select onchange="changeYear()" name="pCurriYear">
																<option value="">-- 선 택 --</option>
<%
	int	pYear	=	Integer.parseInt(CommonUtil.getCurrentDate().substring(0, 4));
	for(int i=2007; i <= pYear; i++) {
%>
																<option value="<%=i%>" <%if(pCurriYear == i){%>selected<%}%>><%=i%></option>
<%	} %>
															</select>&nbsp;&nbsp;개설학기 :
															</td>
															<td width="30" style="padding:0,0,0,0"><div id="curriTermList" style="width:100%;display:none"></div></td>
															<td>&nbsp;&nbsp;교육과정분야 :
															<select name='pPareCode1' onChange='changeCategory()'></select>&nbsp;
															</td>
														</tr>
														<tr>
															<td colspan="3">
																<table width="100%" cellpadding="0" cellspacing="0">
																	<tr>
																		<td align="right" style="padding:0,0,0,0" height=30>
페이지별 강좌수 :
<select name="DispLine" onChange="autoReload()">
	<option value="">-- 선 택 --</option>
	<option value="10">10</option>
	<option value="20">20</option>
	<option value="30">30</option>
</select>
																		</td>
																		<td align="right" width="105" height=30>
<script language=javascript>Button5("엑셀다운로드", "downExcel('info')", "");</script>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="27"></td>
											</tr>
											<tr class="s_tab02">
												<td width="125" rowspan="3">개설과정</td>
												<td class="" rowspan="3"></td>
												<td width="70" rowspan="3">개설학기</td>
												<td class="" rowspan="3"></td>
												<td width="45" rowspan="3">컨텐츠<br>수</td>
												<td class="" rowspan="3"></td>
												<td width="122" colspan="5">수료인원 (단위:명)</td>
												<td class="s_tablien"></td>
												<td width="122" colspan="5">미수료인원<br>(단위:명)</td>
												<td class="s_tablien"></td>
												<td width="122" colspan="5">총교육인원<br>(단위:명)</td>
												<td class="" rowspan="3"></td>
												<td width="60" rowspan="3">지역<br>/연령</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="17"></td>
											</tr>
											<tr class="s_tab02">
												<td width="40">남</td>
												<td class="s_tablien"></td>
												<td width="40">여</td>
												<td class="s_tablien"></td>
												<td width="40">합계</td>
												<td class="s_tablien"></td>
												<td width="40">남</td>
												<td class="s_tablien"></td>
												<td width="40">여</td>
												<td class="s_tablien"></td>
												<td width="40">합계</td>
												<td class="s_tablien"></td>
												<td width="40">남</td>
												<td class="s_tablien"></td>
												<td width="40">여</td>
												<td class="s_tablien"></td>
												<td width="40">합계</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="27"></td>
											</tr>
											<tr>
												<td colspan="27">
													<!-- 과정정보 -->
													<div id="curriSubStaticInfo" style="width:100%;display:none"></div>
													<!-- 과정정보 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="27"></td>
											</tr>
										</table>
</div>
<div id="staticListDiv" style="width:100%;display:none">
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr>
												<td colspan="21" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
<script language=javascript>Button5("목록", "goList()", "");</script>
															</td>
															<td align=right width="50%" height=30>
<script language=javascript>Button5("엑셀다운로드", "downExcel('detail')", "");</script>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="21"></td>
											</tr>
											<tr class="s_tab02">
												<td width="132" rowspan="3"><div id="curriName" style="width:100%;display:none"></div></td>
												<td rowspan="3"></td>
												<td width="167" colspan="5">수료인원 (단위:명)</td>
												<td class="s_tablien"></td>
												<td width="167" colspan="5">미수료인원<br>(단위:명)</td>
												<td class="s_tablien"></td>
												<td width="167" colspan="5">총교육인원<br>(단위:명)</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="17"></td>
											</tr>
											<tr class="s_tab02">
												<td width="55">남</td>
												<td class="s_tablien"></td>
												<td width="55">여</td>
												<td class="s_tablien"></td>
												<td width="55">합계</td>
												<td class="s_tablien"></td>
												<td width="55">남</td>
												<td class="s_tablien"></td>
												<td width="55">여</td>
												<td class="s_tablien"></td>
												<td width="55">합계</td>
												<td class="s_tablien"></td>
												<td width="55">남</td>
												<td class="s_tablien"></td>
												<td width="55">여</td>
												<td class="s_tablien"></td>
												<td width="55">합계</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="27"></td>
											</tr>
											<tr>
												<td colspan="27">
													<!-- 과정정보 -->
													<div id="curriSubStatic" style="width:100%;display:none"></div>
													<!-- 과정정보 -->
												</td>
											</tr>

											<tr class="s_tab05">
												<td colspan="21"></td>
											</tr>
										</table>
</div>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						


<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pCurriYear%>');
</script>
<%@include file="../common/footer.jsp" %>