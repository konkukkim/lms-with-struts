<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@ page import ="com.edutrack.framework.util.StringUtil"%>
<%@include file="../common/community_header.jsp" %>
<%
   String pMode =  (String)model.get("pMode");
   String pBbsId = (String)model.get("pBbsId");
   int pCommId = 1;
   String pBbsType = (String)model.get("pBbsType");

   String userId = UserBroker.getUserId(request);
   String userType = UserBroker.getUserType(request);

   boolean chkLogin = true;
   String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE;
%>
<script language="javascript">
	function goAdd() {
		document.location = "<%=CONTEXTPATH%>/Community.cmd?cmd=commPrideWrite&pBbsId=<%=pBbsId%>&pMode=<%=pMode%>";
	}

	function goSearch() {
		document.f.submit();
	}
</script>
								<tr valign="top">
									<td colspan="2" height="10"></td>
								</tr>
								<tr valign="top">
									<!-- community title -->
									<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b>동아리 자랑</b></font></td>
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
<%
	ListDTO listObj = (ListDTO)model.get("contentsList");
	int iTotCnt = listObj.getTotalItemCount();
	int iCurPage = listObj.getCurrentPage();
	int iDispLine = listObj.getListScale();
%>
<%= listObj.getPageScript("f", "curPage", "goPage") %>
								<tr valign="top">
									<td colspan="2" class="com_content_top">
									<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name="f" method="post" action="<%=CONTEXTPATH%>/Community.cmd?cmd=commPridebbsPagingList&pBbsId=<%=pBbsId%>&MENUNO=0" >
<input type="hidden" name="curPageNew" value="1">
											<tr class="com_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="com_tab02">
												<td width="70">번호</td>
												<td class="com_tablien"></td>
												<td width="">제목</td>
												<td class="com_tablien"></td>
												<td width="80">작성자</td>
												<td class="com_tablien"></td>
												<td width="80">등록일</td>
												<td class="com_tablien"></td>
												<td width="70">조회수</td>
											</tr>
											<tr class="com_tab03">
												<td colspan="11"></td>
											</tr>
<%
	int No = listObj.getStartPageNum();
	RowSet list = listObj.getItemList();
	String depthSpace = "";
	if(listObj.getItemCount() > 0){
		while(list.next()){
			depthSpace = "";

			if( list.getInt("depth_no") > 0 )
				depthSpace = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/icon_re.gif'>";
			for(int j=0;j<list.getInt("depth_no");j++)
				depthSpace = "&nbsp;"+depthSpace;
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="com_tab04_cen"><%=No%></td>
												<td></td>
												<td class="com_tab04"><%=depthSpace%><a href="<%=CONTEXTPATH%>/Community.cmd?cmd=commPrideContentsShow&pMode=<%=pMode%>&pCommId=<%=list.getString("comm_id")%>&pBbsId=<%=pBbsId%>&pSeqNo=<%=list.getInt("seq_no")%>&curPage=<%=iCurPage%>"><b>[<%=StringUtil.nvl(list.getString("comm_name"))%>]</b> <%=StringUtil.nvl(list.getString("subject"))%></a></td>
												<td></td>
												<td class="com_tab04_cen"><%=StringUtil.nvl(list.getString("reg_name"))%></td>
												<td></td>
												<td class="com_tab04_cen"><%=DateTimeUtil.getDateType(1,list.getString("reg_date"))%></td>
												<td></td>
												<td class="com_tab04_cen"><%=list.getInt("hit_no")%></td>
											</tr>
<%			if(No > 1) { %>
											<tr class="com_tab03">
												<td colspan="11"></td>
											</tr>
<%
			}
			No = No-1;
	   }
	   list.close();
	}else{
%>

											<tr>
												<td class="com_tab04_cen" colspan="11">등록된 게시글이 없습니다.</td>
											</tr>
<%
	}
%>
											<tr class="com_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="right">
<script language=javascript>Button3("글쓰기", "goAdd()", "");</script>
												</td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
<%
	if (listObj.getItemCount() > 0) {
%>
													<table valign=top height="25">
														<tr>
															<td><%= listObj.getPagging(SYSTEMCODE,"goPage") %></td>
														</tr>
													</table>
<%
	}
%>
													<table>
														<tr>
															<td align=middle height=30>
																<select name=pSearchKey>
																	<option value=subject selected>제목</option>
																	<option value=keyword>내용</option>
																	<option value=reg_name>등록자</option>
																</select>
																<input maxlength=30 size=22 name=pKeyWord>
																<a href="goSearch()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle></a>
															</td>
														</tr>
													</table>
												</td>
											</tr>
</form>
											<!-- // 페이지 리스트, 검색부분 -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->

<%@include file="../common/community_footer.jsp" %>