<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.community.dto.CommMembersDTO"%>
<%@include file="../common/community_header.jsp" %>
<%
    CommMembersDTO memberInfo = (CommMembersDTO)model.get("memberInfo");
    String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE;
%>

<script language="javascript">
	function submitCheck() {
		var f = document.Input;
		if(!validate(f)) return false;
//		else return true;
		f.submit();
	}

	function goList() {
		document.location.href = "<%=CONTEXTPATH%>/Community.cmd?cmd=goCommunity&pCommId=<%=model.get("commId")%>";
	}
</script>
								<tr valign="top">
									<td colspan="2" height="10"></td>
								</tr>
								<tr valign="top">
									<!-- community title -->
									<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b>개인정보수정</b></font></td>
									<!-- // community title -->
									<!-- history -->
									<td class="com_stit_history" valign="bottom" align="right" width="327">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (PMODE.equals("Search")) NAVIGATION = "홈 > 통합검색";
	if (NAVIGATION != "") {
		out.println(NAVIGATION) ;
	} // end if
%>
									</td>
								</tr>
								<tr valign="top">
									<td colspan="2" class="com_content_top">
									<!-- 내용 -->
									<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start   onSubmit="return submitCheck()" -->
<form name="Input" method="post" action="<%=CONTEXTPATH%>/Community.cmd?cmd=commMemberRegist&pRegMode=Edit">
											<tr class="com_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="com_tab02" width="92">닉네임</td>
												<td class="s_tab_view02"><input name="pUserNick" size="70" dispName="닉네임" value="<%=StringUtil.nvl(memberInfo.getUserNick())%>"></td>
											</tr>
											<tr class="com_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="com_tab02">자기소개</td>
												<td class="s_tab_view03"><textarea name="pUserIntro" cols="90" rows="6" class="input_member "><%=StringUtil.nvl(memberInfo.getUserIntro())%></textarea></td>
											</tr>

											<tr class="com_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("수정", "submitCheck()", "");</script>
&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
												</td>
											</tr>
</form>
<!-- form -->

										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<script language="javascript">
   document.Input.pUserNick.focus();
</script>
<%@include file="../common/community_footer.jsp" %>