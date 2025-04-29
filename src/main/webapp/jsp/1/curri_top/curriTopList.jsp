<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO,com.edutrack.curritop.dto.CurriTopDTO"%>
<%@include file="../common/header.jsp" %>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriTopWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/curri_top/curriTopList.js"></script>
<%
	String	pProperty1	=	StringUtil.nvl(model.get("pProperty1"));
	String	pProperty2	=	StringUtil.nvl(model.get("pProperty2"));
	String	pMode		=	StringUtil.nvl(model.get("pMode"));
%>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<form name="f" method="post" onSubmit="return false;">
<input type="hidden" name="curPage" value="">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%"></td>
															<td align=right width="50%" height=30>
<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %><script language=javascript>Button5("과정생성", "goAdd()", "");</script><%	}	%>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="40">번호</td>
												<td class="s_tablien"></td>
												<td width="100">과정코드</td>
												<td class="s_tablien"></td>
												<td width="267">과정명</td>
												<td class="s_tablien"></td>
												<td width="120">과정분류</td>
												<td class="s_tablien"></td>
												<td width="70">과정구분</td>
												<td class="s_tablien"></td>
												<td width="70">연결과목</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11">
													<!-- 리스트 -->
														<div id="curriTopDiv" style="width:100%;display:none"></div>
													<!-- 리스트 -->
												</td>
											</tr>

											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td class="s_tab04_cen" colspan="11" height="30" align="right">
													<font color="#9A8B7C">*&nbsp;연결과목 항목을 클릭하면 과목을 선택할 수 있는 페이지로 연결됩니다.</font>
												</td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
													<table valign=top height="25">
														<tr>
															<td><!-- 페이징 -->
																<div id="getPagging" style="width:100%;display:none"></div></td>
														</tr>
													</table>
													<table>
														<tr>
															<td align=middle height=30>
																<select name=pSTarget>
																	<option value=curri_name selected>과정명</option>
																	<option value=curri_code>과정코드</option>
																</select>
																<input maxlength=30 size=22 name=pSWord  value="">
																<a href="javascript:searchList()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle></a>
															</td>
														</tr>
</form>
													</table>
												</td>
											</tr>
											<!-- // 페이지 리스트, 검색부분 -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
					
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pProperty1%>','<%=pProperty2%>');
</script>
<%@include file="../common/footer.jsp" %>