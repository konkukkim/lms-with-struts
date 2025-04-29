<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/community_header.jsp" %>
<%
	String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE;
%>
<script language="javascript">
	function viewCommunity(commId) {
		document.location = "Community.cmd?cmd=goCommunity&pCommId=" + commId + "&MENUNO=0";
	}

	function joinCommunity(commId) {
		if(confirm('동아리 가입하시겠습니까?\n\n가입시 나의 동아리 메뉴로 이동합니다.')){
	 		document.location = "Community.cmd?cmd=joinCommunity&pCommId=" + commId + "&MENUNO=0";
		} else{
		}
	}
</script>
								<tr valign="top">
									<td colspan="2" height="10"></td>
								</tr>
								<tr valign="top">
									<!-- community title -->
									<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b>신규 동아리</b></font></td>
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
<%	int cnt= 0;
	int k = 0;
	RowSet list= (RowSet)model.get("rs");
    String openYn = "N";

	while(list.next()){
		k++;
	    openYn = StringUtil.nvl(list.getString("open_yn"));	%>
									<!-- 신규 동아리1 -->
										<table width="660" align="center">
											<tr>
												<td width="579"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_newlist_ttop.gif" width="660" height="10"></td>
											</tr>
											<tr>
												<td align=middle background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_newlist_tbg.gif">
													<table width="630" align="center">
														<tr>
															<td height=25>
																<table width="100%">
																	<tr>
																		<td><font class="com_text02"><b><%=list.getString("comm_name")%></b></font></td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_newlist_line.gif"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_newlist_line.gif" width="1" height="3"></td>
														</tr>
														<tr>
															<td align=right></td>
														</tr>
														<tr>
															<td style="padding:10 5 10 5" bgcolor=#ffffff>
															<div id=divcontents style="overflow-x: auto; width: 620px">
															- 시삽 : <%=list.getString("comm_master")%><br>
															- 회원수 : <%=list.getInt("memcount")%> <br>
															- 공개여부 : <%if(openYn.equals("Y")){%>공개<%}else{%>비공개<%}%> <br>
															- 개설일 : <%=DateTimeUtil.getDateType(1,list.getString("reg_date"))%> </div></td>
														</tr>
														<tr>
															<td bgcolor=#d6d6d6></td>
														</tr>
														<tr>
															<td  style="padding-top: 10px">&nbsp;<font class="com_text02"><%=list.getString("comm_synopsis")%></font></td>
														</tr>
														<tr>
															<td  style="padding-top: 10px" align="right">
<%		if (openYn.equals("Y")) {	%>
<a href="javascript:viewCommunity('<%=list.getInt("comm_id")%>')"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/btn_look.gif"></a>
<%		} else {

		}

		if (!StringUtil.nvl(list.getString("comm_master")).equals(USERID)) {	%>
&nbsp;<a href="javascript:joinCommunity('<%=list.getInt("comm_id")%>')"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/btn_join.gif"></a>
<%		} else {	%>
&nbsp;<a href="javascript:alert('동아리의 시샵 입니다.')"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/btn_join.gif"></a>
<%		}	%>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_newlist_tbtm.gif" width="660" height="10"></td>
											</tr>
											<tr>
												<td  height="20"></td>
											</tr>
										</table>
										<!-- // 신규 동아리1 -->
<%		cnt++;
	}
	list.close();

	if (cnt == 0) {	%>
										<!-- 신규 커뮤니티1 -->
										<table width="660" align="center">
											<tr>
												<td width="579"><img src="../img/1/community/com_newlist_ttop.gif" width="660" height="10"></td>
											</tr>
											<tr>
												<td align=middle background="../img/1/community/com_newlist_tbg.gif">
													<table width="630" align="center">
														<tr> 
															<td height=25>
																<table width="100%">
																	<tr> 
																		<td><font class="com_text02"><b></b></font></td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr> 
															<td background="../img/1/community/com_newlist_line.gif"><img src="../img/1/community/com_newlist_line.gif" width="1" height="3"></td>
														</tr>
														<tr> 
															<td align=right></td>
														</tr>
														<tr>
															<td style="padding:10 5 10 5" bgcolor=#ffffff>
																<div id=divcontents style="overflow-x: auto; width: 620px">
																<br>
																<br>
																<br>
																</div>
															</td>
														</tr>
														<tr> 
															<td bgcolor=#d6d6d6></td>
														</tr>
														<tr> 
															<td  style="padding-top: 10px" align="center">개설된 신규 동아리가 없습니다.<font class="com_text02"></font></td>
														</tr>
														<tr> 
															<td  style="padding-top: 10px" align="right"></td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td><img src="../img/1/community/com_newlist_tbtm.gif" width="660" height="10"></td>
											</tr>
											<tr>
												<td  height="20"></td>
											</tr>
										</table>
										<!-- // 신규 커뮤니티1 -->
<%	}	%>
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/community_footer.jsp" %>