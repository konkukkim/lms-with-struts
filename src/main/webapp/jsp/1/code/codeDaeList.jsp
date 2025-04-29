<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp" %>

<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CodeDaeWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/code/codeDae.js"></script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name="f">
<input type="hidden" name="curPage" value="">
<input type="hidden" name="pCodeSoCnt" value="">
<input type="hidden" name="pCol" value="">
<input type="hidden" name="pOrder" value="">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
															</td>
															<td align=right width="50%" height=30>
<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %><script language=javascript>Button5("대코드추가", "codeDaeWrite('block')", "");</script>&nbsp;<%	}	%>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td colspan="11">
<!-- 대코드 입력창 start -->
<div id="codeDaeWrite" style="width:100%;display:none">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr class="s_tab05">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">대코드</td>
															<td class="s_tab_view02" colspan="3">
																<input type=text name="pCodeDae" onChange="checkCodeDae();" value="" maxlength="10" dispName="대코드" notNull >
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">대코드명</td>
															<td class="s_tab_view02" colspan="3">
																<input type=text name="pDaeName" size="50" maxlength="40" dispName="대코드" notNull >
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">대코드설명</td>
															<td class="s_tab_view02" colspan="3">
																<textarea name="pComment" cols="80" rows="5"></textarea>
															</td>
														</tr>
														<tr class="s_tab03">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">사용여부</td>
															<td class="s_tab_view02" colspan="3">
																<input type=radio name="pUseYn" value="Y" class="no" checked>사용
														        <input type=radio name="pUseYn" value="N" class="no" >사용안함
															</td>
														</tr>
														<tr class="s_tab05">
															<td colspan="4"></td>
														</tr>
														<tr>
															<td class="s_list_btn" colspan="4" height="30" align="right">
	<div id="regButton" style="display:block">
		<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %><script language=javascript>Button3("등록", "manageCodeDae('Add')", "");</script><%	}	%>&nbsp;<script language=javascript>Button3("취소", "codeDaeWrite('none')", "");</script>
	</div>
	<div id="modButton" style="display:none">
		<% if(CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  { %><script language=javascript>Button3("수정", "manageCodeDae('Edit')", "");</script><%	}	%><% if(CommonUtil.getAuthorCheck(request,  "D"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button3("삭제", "manageCodeDae('Delete')", "");</script><%	}	%>&nbsp;<script language=javascript>Button3("취소", "codeDaeWrite('none')", "");</script>
	</div>
															</td>
														</tr>
													</table>
</div>
<!-- 대코드 입력창 end -->
									<!-- 리스트 시작 -->
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="40">번호</td>
												<td class="s_tablien"></td>
												<td width="110">
													<table border="0" cellpadding="0" cellspacing="0">
														<tr class="s_tab02">
															<!-- <td align="center"><a href="javascript:sorting(1,'code_dae','asc')" onMouseOver="window.status='정렬';return true" onMouseOut="window.status='';return true"><div id="sort_1" class="sort" style="width:20;cursor:hand;display:block">▲</div></a></td> -->
															<td align="center">대코드</td>
															<!-- <td align="center"><a href="javascript:sorting(2,'code_dae','desc')" onMouseOver="window.status='정렬';return true" onMouseOut="window.status='';return true"><div id="sort_2" class="sort" style="width:20;cursor:hand;display:block">▽</div></a></td> -->
														</tr>
													</table>
												</td>
												<td class="s_tablien"></td>
												<td width="215">
													<table border="0" cellpadding="0" cellspacing="0">
														<tr class="s_tab02">
															<!-- <td align="center"><a href="javascript:sorting(3,'dae_name','asc')" onMouseOver="window.status='정렬';return true" onMouseOut="window.status='';return true"><div id="sort_3" class="sort" style="width:20;cursor:hand;display:block">△</div></a></td> -->
															<td align="center">코드명</td>
															<!-- <td align="center"><a href="javascript:sorting(4,'dae_name','desc')" onMouseOver="window.status='정렬';return true" onMouseOut="window.status='';return true"><div id="sort_4" class="sort" style="width:20;cursor:hand;display:block">▽</div></a></td> -->
														</tr>
													</table>
												</td>
												<td class="s_tablien"></td>
												<td width="110">
													<table border="0" cellpadding="0" cellspacing="0">
														<tr class="s_tab02">
															<!-- <td align="center"><a href="javascript:sorting(5,'use_name','asc')" onMouseOver="window.status='정렬';return true" onMouseOut="window.status='';return true"><div id="sort_5" class="sort" style="width:20;cursor:hand;display:block">△</div></a></td> -->
															<td align="center">상태</td>
															<!-- <td align="center"><a href="javascript:sorting(6,'use_name','desc')" onMouseOver="window.status='정렬';return true" onMouseOut="window.status='';return true"><div id="sort_6" class="sort" style="width:20;cursor:hand;display:block">▽</div></a></td> -->
														</tr>
													</table>
												</td>
												<td class="s_tablien"></td>
												<td width="110">
													<table border="0" cellpadding="0" cellspacing="0">
														<tr class="s_tab02">
															<!-- <td align="center"><a href="javascript:sorting(7,'reg_date','asc')" onMouseOver="window.status='정렬';return true" onMouseOut="window.status='';return true"><div id="sort_7" class="sort" style="width:20;cursor:hand;display:block">△</div></a></td> -->
															<td align="center">등록일</td>
															<!-- <td align="center"><a href="javascript:sorting(8,'reg_date','desc')" onMouseOver="window.status='정렬';return true" onMouseOut="window.status='';return true"><div id="sort_8" class="sort" style="width:20;cursor:hand;display:block">▽</div></a></td> -->
														</tr>
													</table>
												</td>
												<td class="s_tablien"></td>
												<td width="80">수정/삭제</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11">
													<!-- 리스트 -->
														<div id="codeDaeList" style="width:100%;display:no"></div>
													<!-- 리스트 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="11" height="10" align="">
												</td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
													<table valign=top height="25">
														<tr>
															<td><!-- 페이징 -->
																<div id="getPagging" style="width:100%;display:no"></div>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!-- // 페이지 리스트, 검색부분 -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
								</form>
							</table>
						
<%@include file="../common/footer.jsp" %>
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>');
</script>