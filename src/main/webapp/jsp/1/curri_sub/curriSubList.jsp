<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp" %>

<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriSubWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/curri_sub/curriSubList.js"></script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
<form name="Input" ID="Form1" method="post" action="<%=CONTEXTPATH%>/CurriSub.cmd?cmd=curriSubList">
														<tr>
															<td>
<select id='pCateCode' name='pCateCode' onChange='changeCategory()'></select>&nbsp;
<select id="pCurriCode" name="pCurriCode" onChange='autoReload()'></select>&nbsp;
<input type="text" name=pCurriYear value="<%=model.get("curriYear")%>" size="4" maxlenght="4">
															</td>
															<td align=right height=30>
<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %><script language=javascript>Button5("개설과정추가", "goAdd()", "");</script><%	}	%>
															</td>
														</tr>
													</table>
												</td>
											</tr>
</form>
											<tr class="s_tab01">
												<td colspan="9"></td>
											</tr>
											<tr class="s_tab02">
												<td width="40">번호</td>
												<td class="s_tablien"></td>
												<td width="258">과정명</td>
												<td class="s_tablien"></td>
												<td width="150">신청기간</td>
												<td class="s_tablien"></td>
												<td width="150">수강기간</td>
												<td class="s_tablien"></td>
												<td width="70">연결과목</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="9"></td>
											</tr>
											<tr>
												<td colspan="9">
													<!-- 리스트 -->
												  	<div id="curriSubList" style="width:100%;display:none"></div>
													<!-- 리스트 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="9"></td>
											</tr>
											<tr>
												<td colspan="9" height="30"></td>
											</tr>
										</table>
										<!-- // 정규과정 끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=model.get("cateCode")%>');
</script>
<%@include file="../common/footer.jsp" %>
