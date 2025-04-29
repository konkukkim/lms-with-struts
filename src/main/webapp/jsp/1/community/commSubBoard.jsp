<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@include file="../common/community_header.jsp" %>
<%
   	String commName	= StringUtil.nvl(model.get("commName"));
    String userLevel = StringUtil.nvl(model.get("userLevel"));
   	String pMode 	= StringUtil.nvl(model.get("pMode"));
   	String pBbsId 	= StringUtil.nvl(model.get("pBbsId"));
   	String pCommId	= StringUtil.nvl(model.get("commId"));
   	String pBbstype  = StringUtil.nvl(model.get("pBbstype"));
   	String bbsName	= StringUtil.nvl(model.get("bbsName"));
   	String pSearchKey= StringUtil.nvl(model.get("pSearchKey"));
   	String pKeyWord	= StringUtil.nvl(model.get("pKeyWord"));
	int	curPage = StringUtil.nvl(model.get("curPage"), 1);

   	//String pVoiceYn = (String)model.get("pVoiceYn");

   	String userId = UserBroker.getUserId(request);
   	String userType = UserBroker.getUserType(request);

   	boolean chkLogin = true;
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CommunitySubBoardWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/community/communitySubBoard.js"></script>

								<tr valign="top">
									<td colspan="2" height="10"></td>
								</tr>
								<tr valign="top">
									<!-- community title -->
									<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=bbsName%></b></font></td>
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
<form name="f" method="post" action="<%=CONTEXTPATH%>/CommSubBoard.cmd?cmd=commSubBoard" >
<input type="hidden" name="curPage" value="1">
<input type="hidden" name="pBbsId" value="<%=pBbsId%>" >
<input type="hidden" name="pBbstype" value="<%=pBbstype%>" >
<input type="hidden" name="pMode" value="<%=pMode%>" >
<input type="hidden" name="userLevel" value="<%=userLevel%>" >

											<tr class="com_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="com_tab02">
												<td width="70">번호</td>
												<td class="com_tablien"></td>
												<td width="358">제목</td>
												<td class="com_tablien"></td>
												<td width="80">등록자</td>
												<td class="com_tablien"></td>
												<td width="80">등록일</td>
												<td class="com_tablien"></td>
												<td width="70">조회수</td>
											</tr>
											<tr class="com_tab03">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11">
													<!-- 리스트 -->
														<div id="bbsList" style="width:100%;display:none"></div>
													<!-- 리스트 -->
												</td>
											</tr>

											<tr class="com_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="right">
<%	if((!userLevel.equals("G") && !pBbstype.equals("N")) || (pBbstype.equals("N") && userLevel.equals("M"))) {	%>
<script language=javascript>Button3("글쓰기", "goAdd()", "");</script>
<%	}	%>
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
																<select name=pSearchKey>
																	<option value="subject">제목</option>
                                                    				<option value="keyword">내용</option>
                                                    				<option value="reg_name">등록자</option>
																</select>
																<input maxlength=30 size=22 name=pKeyWord dispName="검색어" lenMCheck="2" lenCheck="20">
																<a href="javascript:bbsListAutoRelod()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle></a>
															</td>
														</tr>
													</table>
												</td>
											</tr>
</form>
<!-- form end -->
											<!-- // 페이지 리스트, 검색부분 -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<script language="javascript">
	bbsList_init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>', '<%=pCommId%>', '<%=pBbsId%>');
</script>
<%@include file="../common/community_footer.jsp" %>