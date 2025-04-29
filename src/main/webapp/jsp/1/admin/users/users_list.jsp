<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr"            %>
<%@include file="../../common/header.jsp" %>
<%
    String userType = (String)model.get("userType");
	String deptDaeCode = (String)model.get("deptDaeCode");
	String tmp_img	= "mtit3_01.gif";     // 학습자 관리 타이틀 이미지
	if (userType.equals("P")) {             // 교수자 관리 타이틀 이미지
		tmp_img	= "mtit3_02.gif";
	} else if (userType.equals("M")) {      // 관리자 관리 타이틀 이미지
		tmp_img	= "mtit3_03.gif";
	}
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/UsersWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/user/adminUserList.js"></script>


										<!-- 내용 -->
<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center" border="0">
<!--===== Form Start =========================================================-->
<form name="f" method="post" onSubmit="return false;">
<input type="hidden" name="pMessageKb"          value="S">
<input type="hidden" name="pMessageWhere"       value="Users">
<input type="hidden" name="pMessageWhereSub"    value="">
<input type="hidden" name="pResultCode1"        value="<%=userType%>">
<input type="hidden" name="pResultCode2"        value="">
<input type="hidden" name="pResultCode3"        value="">

<input type="hidden" name="curPage"  value="">
<input type="hidden" name="userType" value="<%=userType%>">
<input type="hidden" name="checkYn"  value="N">
<input type="hidden" name="service"  value="">
<input type="hidden" name="subject"  value="">
<input type="hidden" name="contents" value="">
														<tr>
															<td width="">
																<select id='deptDaeCode' name='deptDaeCode' onChange='changeDeptDaeCode()'></select>&nbsp;
																<select id='deptSoCode'  name="deptSoCode" onChange='changeDeptSoCode()'></select>
															</td>
<!-- 상단버튼 -->
															<td align=right width="" height=30>
<% if(CommonUtil.getAuthorCheck(request,  "R"))/* 권한체크 */  { %><script language=javascript>Button5("ExcelDown", "Users_Service('excel')", "");</script><%	}	%>
<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %>
&nbsp;<script language=javascript>Button5("사용자일괄등록", "Batch_AddUser('<%=userType%>')", "");</script>
&nbsp;<script language=javascript>Button5("사용자등록", "UserAdd('write')", "");</script>
<%	}	%>
<% if(CommonUtil.getAuthorCheck(request,  "U") && !userType.equals("S") ){	%>&nbsp;<script language=javascript>Button5("사용자변경", "UserTypeAdd()", "");</script><%	}	%>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="130">이름</td>
												<td class="s_tablien"></td>
												<td width="140">아이디</td>
												<td class="s_tablien"></td>
												<td width="140">소속구분</td>
												<td class="s_tablien"></td>
												<td width="110">가입일</td>
												<td class="s_tablien"></td>
												<td width="74"><a href="javascript:setAllToken(f.pCHK)">선택</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td colspan="11">
													<!-- 리스트 -->
													<div id="userList" style="width:100%;display:none"></div>
													<!-- 리스트 -->
												</td>
											</tr>

											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="right">
<script language=javascript>Button3("개별메일", "mailSend('S')", "");</script>
&nbsp;<script language=javascript>Button3("개별쪽지", "messageSend('S')", "");</script>
&nbsp;<script language=javascript>Button3("전체메일", "mailSend('A')", "");</script>
&nbsp;<script language=javascript>Button3("전체쪽지", "messageSend('A')", "");</script></td>
											</tr>
										<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
													<table valign=top height="25">
														<tr>
															<td>
																<!-- 페이징 -->
																<div id="getPagging" style="width:100%;display:no"></div>
															</td>
														</tr>
													</table>
													<table>
														<tr>
															<td align=middle height=30>
																<select name=pFields>
																	<option value=user_name selected>이름</option>
																	<option value=user_id>사용자ID</option>
																	<option value=jumin_no>주민번호</option>
																</select>
																<input maxlength=20 size=22 name=pValue value="" dispName="검색어" lenCheck="20">
																<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle onClick="goSearch()" style="cursor:hand">
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
							</table>
						
</form>
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=userType%>','<%=PMODE %>');
</script>
<%@include file="../../common/footer.jsp" %>
