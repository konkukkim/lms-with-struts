<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@include file="../common/header.jsp" %>
<%
	int	pCurriYear	=	StringUtil.nvl(model.get("pCurriYear"), 2007);
	int	pCurriTerm	=	StringUtil.nvl(model.get("pCurriTerm"), 0);
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriSubCourseStaticWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/connstatus/curriSubCourseStatic.js"></script>

<!-- FORM START -->
<form name="f">
<input type="hidden" name="pCurriCode" value="">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
<div id="infoListDiv" style="width:100%;display:block">
										<table width="670" align="center">
											<tr>
												<td colspan="27" class="s_btn01">
													<table width="100%" align="center" border="0">
														<tr>
															<td width="227">
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
															<td width="243" style="padding:0,0,0,0"><div id="curriTermList" style="width:100%;display:none"></div></td>
															<td width="200" align="right"><script language=javascript>Button5("엑셀다운로드", "exelDownload('info')", "");</script></td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="19"></td>
											</tr>
											<tr class="s_tab02">
												<td width="153">개설과정</td>
												<td class="s_tablien"></td>
												<td width="55">교수자</td>
												<td class="s_tablien"></td>
												<td width="55">수강생<br>(명)</td>
												<td class="s_tablien"></td>
												<td width="65">질의응답<br>(건)</td>
												<td class="s_tablien"></td>
												<td width="65">공지사항<br>(건)</td>
												<td class="s_tablien"></td>
												<td width="100">면담<br>(요청건/답변건)</td>
												<td class="s_tablien"></td>
												<td width="40">토론<br>(건)</td>
												<td class="s_tablien"></td>
												<td width="40">과제<br>(건)</td>
												<td class="s_tablien"></td>
												<td width="40">시험<br>(건)</td>
												<td class="s_tablien"></td>
												<td width="50">평가<br>여부</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="19"></td>
											</tr>
											<tr>
												<td colspan="19">
													<!-- 과정정보 -->
													<div id="curriSubCourseList" style="width:100%;display:none"></div>
													<!-- 과정정보 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="19"></td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
</div>
<div id="staticListDiv" style="width:100%;display:none">
										<table width="670" align="center">
											<tr>
												<td colspan="15" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
<div id="curriNameDiv"></div>
															</td>
															<td align=right width="50%" height=30>
<script language=javascript>Button5("목록가기", "goList()", "");</script>
&nbsp;<script language=javascript>Button5("엑셀다운로드", "exelDownload('detail')", "");</script>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="15"></td>
											</tr>
											<tr class="s_tab02">
												<td width="100">수강생</td>
												<td class="s_tablien"></td>
												<td width="83">진도율</td>
												<td class="s_tablien"></td>
												<td width="70">질의응답<br>(건)</td>
												<td class="s_tablien"></td>
												<td width="100">면담<br>(요청건/답변건)</td>
												<td class="s_tablien"></td>
												<td width="80">토론<br>(게시건수)</td>
												<td class="s_tablien"></td>
												<td width="70">과제제출<br>(건)</td>
												<td class="s_tablien"></td>
												<td width="80">시험응시<br>(건)</td>
												<td class="s_tablien"></td>
												<td width="80">수료성적</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="15"></td>
											</tr>
											<tr>
												<td colspan="15">
													<!-- 과정정보 -->
													<div id="courseStudentList" style="width:100%;display:none"></div>
													<!-- 과정정보 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="15"></td>
											</tr>
										</table>
</div>
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pCurriYear%>');
</script>
<%@include file="../common/footer.jsp" %>