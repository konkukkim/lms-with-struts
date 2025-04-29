<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@ page import ="com.edutrack.onlinequiz.dto.OnlineQuizDTO"%>
<%@ page import ="com.edutrack.common.CommonUtil"%>
<%@include file="../common/header.jsp" %>
<%
	OnlineQuizDTO onlineQuizDTO = new OnlineQuizDTO();
	onlineQuizDTO = (OnlineQuizDTO)model.get("onlineQuizDto");
%>
<script language="javascript">
<!--
	function goHome() {
		document.location = "<%=CONTEXTPATH%><%=myEdaLink%>";
	}
//-->
</script>
							<table width="685" height="100%" align="right">
								<tr valign="top">
									<!-- sub title -->
									<td height="34" class="stit_blet"><div class="stit_title"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/title/mtit15_01.gif" alt="타이틀" width="247" height="34"></div></td>
									<!-- // sub title -->
									<!-- history -->
									<td class="stit_history" valign="bottom" align="right" width="368">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (PMODE.equals("Search")) NAVIGATION = "홈 > 통합검색";
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
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">제목</td>
												<td class="s_tab_view02"><%= onlineQuizDTO.getSubject()%>&nbsp;&nbsp;<font color='CB622E'><b>[<%=(request.getParameter("pNO")).equals(  onlineQuizDTO.getAnswer() ) ? "정답입니다." :"오답입니다" %>]</b></font></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">설명</td>
												<td class="s_tab_view03"><%= onlineQuizDTO.getQuizComment()%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">결과</td>
												<td class="s_tab_view02">
<%
	// 선택형
	if( ("S").equals(onlineQuizDTO.getQuizType() ) )
	{
%>
													<% if( !("").equals(StringUtil.nvl(onlineQuizDTO.getExample1()) ) ) { %><input type="radio"  name='pNO' value="1" <%=( ("1").equals(onlineQuizDTO.getAnswer())? "checked" : ""  ) %> disabled><%=onlineQuizDTO.getExample1()%><br><% } %>
													<% if( !("").equals(StringUtil.nvl(onlineQuizDTO.getExample2()) ) ) { %><input type="radio"  name='pNO' value="2" <%=( ("2").equals(onlineQuizDTO.getAnswer())? "checked" : ""  ) %> disabled><%=onlineQuizDTO.getExample2()%><br><% } %>
													<% if( !("").equals(StringUtil.nvl(onlineQuizDTO.getExample3()) ) ) { %><input type="radio"  name='pNO' value="3" <%=( ("3").equals(onlineQuizDTO.getAnswer())? "checked" : ""  ) %> disabled><%=onlineQuizDTO.getExample3()%><br><% } %>
													<% if( !("").equals(StringUtil.nvl(onlineQuizDTO.getExample4()) ) ) { %><input type="radio"  name='pNO' value="4" <%=( ("4").equals(onlineQuizDTO.getAnswer())? "checked" : ""  ) %> disabled><%=onlineQuizDTO.getExample4()%><br><% } %>
													<% if( !("").equals(StringUtil.nvl(onlineQuizDTO.getExample5()) ) ) { %><input type="radio"  name='pNO' value="5" <%=( ("5").equals(onlineQuizDTO.getAnswer())? "checked" : ""  ) %> disabled><%=onlineQuizDTO.getExample5()%><br><% } %>
<%
	}
	// O,X 형
	else
	{
%>
													<input type="radio"  name='pNO' value="O" <%=( ("O").equals(onlineQuizDTO.getAnswer())? "checked" : ""  ) %> disabled>O(맞다)<br>
													<input type="radio"  name='pNO' value="X" <%=( ("X").equals(onlineQuizDTO.getAnswer())? "checked" : ""  ) %> disabled>X(틀리다)<br>
<%
	} // end if
%>
												</td>
											</tr>
											<!--tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01"></td>
												<td class="s_tab_view02">
<%
	String sLinkUrl = "";
	String sQuizCurriUrl = StringUtil.nvl(onlineQuizDTO.getQuizCurriUrl(), "");
	String sQuizLinkUrl  = StringUtil.nvl(onlineQuizDTO.getQuizLinkUrl() , "");


	if(!sQuizLinkUrl.equals("") &&  !sQuizLinkUrl.equals("0")){
		sQuizLinkUrl = "<b><a href=\""+sQuizLinkUrl+"\">[ 관련 웹페이지 ]</a></b>";
	}else{
		sQuizLinkUrl = "";
	}
	if(!sQuizCurriUrl.equals("")){
		String[] tmpVal = sQuizCurriUrl.split("!");
		sLinkUrl  = CONTEXTPATH + "/CurriSub.cmd?cmd=enrollInfo&pProperty1=Cyber";
		sLinkUrl += "&pCurriCode=" + tmpVal[1];
		sLinkUrl += "&pCurriYear=" + tmpVal[2];
		sLinkUrl += "&pCurriTerm=" + tmpVal[3];
		sLinkUrl = "<b><a href=\""+sLinkUrl+"\">[ 연결강좌 ]</a></b>";
	}

	if(!sQuizCurriUrl.equals("") || !sLinkUrl.equals(""))
	{
%>
													<%=sLinkUrl%>
                                  					<%=sQuizLinkUrl%>
<%
	}
%>
												</td>
											</tr-->
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("돌아가기", "goHome()", "");</script>
												</td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/footer.jsp" %>