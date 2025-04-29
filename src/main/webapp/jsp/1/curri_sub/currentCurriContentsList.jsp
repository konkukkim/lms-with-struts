<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp" %>
<%
	String	pPareCode1	=	StringUtil.nvl(model.get("pPareCode1"));
	String	pPareCode2	=	StringUtil.nvl(model.get("pPareCode2"));
	int		dispLine	=	StringUtil.nvl(model.get("dispLine"), 10);
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriSubWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/curri_sub/currentCurriContents.js"></script>
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<form name="f">
<input type="hidden" name="curPage" value="">
<input type="hidden" name="dispLine" value="<%=dispLine%>">
											<tr>
												<td colspan="11" class="s_btn01" align="right">
													<select name='pPareCode2' onChange='selectSchoolYear()'>
														<option value="1" <%if(pPareCode2.equals("1")){%>selected<%}%>>1학년</option>
														<option value="2" <%if(pPareCode2.equals("2")){%>selected<%}%>>2학년</option>
														<option value="3" <%if(pPareCode2.equals("3")){%>selected<%}%>>3학년</option>
													</select>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="40">번호</td>
												<td class="s_tablien"></td>
												<td width="150">과정</td>
												<td class="s_tablien"></td>
												<td width="287">강의명</td>
												<td class="s_tablien"></td>
												<td width="60">교수</td>
												<td class="s_tablien"></td>
												<td width="130">출석기간</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11">
												<!-- 리스트 DIV -->
													<div id="contentsListDiv" style="width:100%;display:none;"></div>
												<!-- 리스트 DIV-->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
</form>
																						<tr>
												<td colspan="9" align=center>
													<table valign=top height="25">
														<tr>
															<td><!-- 페이징 -->
																<div id="getPagging" style="width:100%;display:none"></div></td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
										<!-- // 정규과정 끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pPareCode1%>');
</script>  
<%@include file="../common/footer.jsp" %>