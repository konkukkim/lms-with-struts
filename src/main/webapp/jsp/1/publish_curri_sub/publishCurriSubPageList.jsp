<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO,com.edutrack.curritop.dto.CurriTopDTO"%>
<%@include file="../common/header.jsp" %>
<%
	String	pPareCode1	=	StringUtil.nvl(model.get("pPareCode1"));
	String	pPareCode2	=	StringUtil.nvl(model.get("pPareCode2"));
	String	pMode		=	StringUtil.nvl(model.get("pMode"));
	int		dispLine	=	StringUtil.nvl(model.get("dispLine"), 10);
	int		dispPage	=	StringUtil.nvl(model.get("dispPage"), 10);
	String	pGubun		=	StringUtil.nvl(model.get("pGubun"));
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/PublishCurriSubWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/publish_curri_sub/publishCurriSubList.js"></script>

										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<form name="f">
<input type="hidden" name="curPage" value="">
<input type="hidden" name="dispLine" value="<%=dispLine%>">
<input type="hidden" name="dispPage" value="<%=dispPage%>">
<input type="hidden" name="pMode" value="<%=pMode%>">
											<tr class="s_tab01">
												<td colspan="9"></td>
											</tr>
											<tr class="s_tab02">
												<td width="40">번호</td>
												<td class="s_tablien"></td>
												<td width="197">과정</td>
												<td class="s_tablien"></td>
<%	if(pGubun.equals("2") && USERTYPE.equals("M")) {		%>
												<td width="270">주제</td>
												<td class="s_tablien"></td>
												<td width="80">교수</td>
												<td class="s_tablien"></td>
												<td width="80">토론방개설</td>
<%	} else {	%>
												<td width="350">주제</td>
												<td class="s_tablien"></td>
												<td width="80">교수</td>
<%	}	%>
												
											</tr>
											<tr class="s_tab03">
												<td colspan="9"></td>
											</tr>
											
											<tr>
												<td colspan="9">
													<!-- 리스트 -->
														<div id="publishCurriSubList" style="width:100%;display:none"></div>
													<!-- 리스트 -->
												</td>
											</tr>
											
											<tr class="s_tab05">
												<td colspan="9"></td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="9" align=center>
													<table valign=top height="30">
														<tr>
															<td>
																<!-- 페이징 -->
																<div id="getPagging" style="width:100%;display:none"></div>
																<!-- 페이징 -->
															</td>
														</tr>
													</table>
													<table>
														<tr>
															<td align=middle height=30>
																<select name=pSearchKey>
																	<option value="cs.curri_name" selected>과정명</option>
																	<option value="ct.curri_code">과정코드</option>
																</select>
																<input maxlength=30 size=22 name=pKeyWord  value="">
																<a href="javascript:autoReload()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle></a>
															</td>
														</tr>
													</table>
												</td>
											</tr>
</form>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
										
										
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>', '<%=pPareCode1%>','<%=pPareCode2%>','<%=pGubun%>', '<%=USERTYPE%>');
</script>
<%@include file="../common/footer.jsp" %>