<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp" %>
<%@ page import ="javax.servlet.ServletOutputStream"%>
<%
String pGUBUN   =request.getParameter("pGUBUN");
String msg      =(String)model.get("MSG");
%>
<script language="javascript">
<!--
	function SubmitCheck(where)
	{
		var f = document.SearchId;
		f.pGUBUN.value=where;

		if(where == "pass") {
			f = document.SearchPass;
			f.pGUBUN.value=where;

			if(!dataCheck(f.pUserId.value)){
			    alert("사용자 아이디에 특수문자를 사용할 수 없습니다.");
			    f.pUserId.focus();
			    return;
			}
		}

		if(!validate(f)) return;

		f.submit();
    }

    function goCancel(){
	    var loc="<%=CONTEXTPATH%>/Main.cmd?cmd=mainShow&pMode=Home";
		document.location = loc;
	}

    function goLogin(){
	    var loc="<%=CONTEXTPATH%>/User.cmd?cmd=usersLoginShow&pMode=MEMBER";
		document.location = loc;
	}

//-->
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
											<TABLE height=120 cellSpacing=0 cellPadding=0 width="100%" border=0><!-- 아이디 찾기 시작 -->
<FORM name=SearchId action=/User.cmd?cmd=searchIdPw method=post>
<INPUT type=hidden name=pGUBUN>
												<TBODY>
													<TR>
														<TD style="PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; PADDING-TOP: 45px" vAlign=top align=middle width="37%" height=300>
<%	//-- 아이디를 찾았을 경우
	//-- 아이디를 화면에 보여준다.

	if (("id").equals(pGUBUN) && !("").equals(msg)) {	%>
															<TABLE height=141 cellSpacing=0 cellPadding=0 width=512 background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/main/ld_search022.gif" border=0>
																<TBODY>
																	<TR>
																		<TD width="40%" height=50>&nbsp;</TD>
																		<TD width="30%" height=50>&nbsp;</TD>
																		<TD height=50>&nbsp;</TD>
																		<TD height=50>&nbsp;</TD>
																	</TR>
																	<TR>
																		<TD width="40%" height=30>&nbsp;</TD>
																		<TD width="60%" height=30 colspan="2"><%=msg%><br><br></TD>
																		<TD height=30>&nbsp;</TD>
																	</TR>
																	<TR>
																		<TD width="40%" height=30>&nbsp;</TD>
																		<TD width="60%" height=30 colspan="2" style="padding:0,0,0,65">
																			<A href="<%=CONTEXTPATH%>/User.cmd?cmd=searchIdPwShow"><IMG height=18 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/main/ld_search01.gif" width=34></A>
																			<A href="javascript:goLogin()"><IMG height=18 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/main/logIn.gif" width=34></A>
																		</TD>
																		<TD height=30>&nbsp;</TD>
																	</TR>
																	<TR>
																		<TD width="40%">&nbsp;</TD>
																		<TD width="30%">&nbsp;</TD>
																		<TD>&nbsp;</TD>
																		<TD>&nbsp;</TD>
																	</TR>
																</TBODY>
															</table>
<%	} else {	%>
															<TABLE height=141 cellSpacing=0 cellPadding=0 width=512 background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/main/ld_search021.gif" border=0>
																<TBODY>
																	<TR>
																		<TD width="40%" height=50>&nbsp;</TD>
																		<TD width="30%" height=50>&nbsp;</TD>
																		<TD height=50>&nbsp;</TD>
																		<TD height=50>&nbsp;</TD>
																	</TR>
																	<TR>
																		<TD width="40%" height=30>&nbsp;</TD>
																		<TD width="30%" height=30><INPUT size=15 name=pUserName dispName="이름" notNull> </TD>
																		<TD height=30>&nbsp;</TD>
																		<TD height=30>&nbsp;</TD>
																	</TR>
																	<TR>
																		<TD width="40%" height=30>&nbsp;</TD>
																			<TD width="30%" height=30><INPUT maxLength=6 size=8 name=pResidentId1 dispName="주민번호 앞자리" notNull> - <INPUT maxLength=7 size=8 name=pResidentId2 dispName="주민번호 앞자리" notNull> </TD>
																			<TD height=30>
																				<A href="javascript:SubmitCheck('id')"><IMG height=18 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/main/ld_search01.gif" width=34></A>
																				<A href="javascript:goCancel()"><IMG height=18 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/main/cancel.gif" width=34></A>
																			</TD>
																		<TD height=30>&nbsp;</TD>
																	</TR>
																	<TR>
																		<TD width="40%">&nbsp;</TD>
																		<TD width="30%">&nbsp;</TD>
																		<TD>&nbsp;</TD>
																		<TD>&nbsp;</TD>
																	</TR>
																</TBODY>
															</TABLE>
<%	}	%>
</FORM><!-- 아이디 찾기 끝 --><BR><BR>
<FORM name=SearchPass action=/User.cmd?cmd=searchIdPw method=post>
<INPUT type=hidden name=pGUBUN>
<%	//-- 비밀번호를 찾았을 경우
	//-- 비밀번호를 화면에 보여준다.

	if (("pass").equals(pGUBUN) && !("").equals(msg)) {	%>
															<TABLE height=141 cellSpacing=0 cellPadding=0 width=512 background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/main/ld_search032.gif" border=0>
																<TBODY>
																	<TR>
																		<TD width="40%" height=50>&nbsp;</TD>
																		<TD width="30%" height=50>&nbsp;</TD>
																		<TD height=50>&nbsp;</TD>
																		<TD height=50>&nbsp;</TD>
																	</TR>
																	<TR>
																		<TD width="40%" height=30>&nbsp;</TD>
																		<TD width="60%" height=30 colspan="2"><%=msg%></TD>
																		<TD height=30>&nbsp;</TD>
																	</TR>
																	<TR>
																		<TD width="40%" height=30>&nbsp;</TD>
																		<TD width="60%" height=30 colspan="2" style="padding:0,0,0,65">
																			<a href="<%=CONTEXTPATH%>/User.cmd?cmd=searchIdPwShow"><IMG height=18 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/main/ld_search01.gif" width=34></A>
																			<a href="javascript:goLogin()"><IMG height=18 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/main/logIn.gif" width=34></A>
																		</TD>
																		<TD height=30>&nbsp;</TD>
																	</TR>
																	<TR>
																		<TD width="40%">&nbsp;</TD>
																		<TD width="30%">&nbsp;</TD>
																		<TD>&nbsp;</TD>
																		<TD>&nbsp;</TD>
																	</TR>
																</TBODY>
															</TABLE>
<!-- 가입된 정보가 있는 경우 비밀번호 찾기 끝 -->
<%	} else {	%>
															<TABLE height=141 cellSpacing=0 cellPadding=0 width=512 background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/main/ld_search031.gif" border=0>
																<TBODY>
																	<TR>
																		<TD width="40%" height=50>&nbsp;</TD>
																		<TD width="30%" height=50>&nbsp;</TD>
																		<TD height=50>&nbsp;</TD>
																		<TD height=50>&nbsp;</TD>
																	</TR>
																	<TR>
																		<TD width="40%" height=30>&nbsp;</TD>
																		<TD width="30%" height=30><INPUT size=15 name=pUserId dispName="아이디" notNull> </TD>
																		<TD height=30>&nbsp;</TD>
																		<TD height=30>&nbsp;</TD>
																	</TR>
																	<TR>
																		<TD width="40%" height=30>&nbsp;</TD>
																		<TD width="30%" height=30><INPUT maxLength=6 size=8 name=pResidentId1 dispName="주민번호 앞자리" notNull> - <INPUT maxLength=7 size=8 name=pResidentId2 dispName="주민번호 뒷자리" notNull> </TD>
																		<TD height=30>
																			<A href="javascript:SubmitCheck('pass')"><IMG height=18 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/main/ld_search01.gif" width=34></A>
																			<A href="javascript:goCancel()"><IMG height=18 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/main/cancel.gif" width=34></A>
																		</TD>
																		<TD height=30>&nbsp;</TD>
																	</TR>
																	<TR>
																		<TD width="40%">&nbsp;</TD>
																		<TD width="30%">&nbsp;</TD>
																		<TD>&nbsp;</TD>
																		<TD>&nbsp;</TD>
																	</TR>
																</TBODY>
															</TABLE>
<%	}	%>
</FORM>
														</TD>
													</TR>
												</TBODY>
											</TABLE>
										<!-- // 정규과정 끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<%@include file="../common/footer.jsp" %>