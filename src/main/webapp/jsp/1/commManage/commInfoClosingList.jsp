<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@include file="../common/community_header.jsp" %>
<% String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE;%>
<script language="javascript">
	function changeClosing(use_yn,comm_id) {
		var msg;
		if(use_yn=='N')
			msg = "동아리를 폐쇄를 취소 하시겠습니까?";
		else if(use_yn=='Y')
			msg = "동아리를 폐쇄 하시겠습니까?";

		var st = confirm(msg);
		if(st==true){
		if(use_yn=='N')
	       	document.location="<%=CONTEXTPATH%>/Community.cmd?cmd=commCloseRegist&pCloseYn=N&pCommId="+comm_id;
	    else
	       	document.location="<%=CONTEXTPATH%>/Community.cmd?cmd=commInfoClosingChange&pUseYn=N&pCommId="+comm_id;
		}
     }

     // 동아리정보 팝업 윈도우
	function showCommInfo(comm_id) {
		var page = "<%=CONTEXTPATH%>/CommManage.cmd?cmd=commInfoShow&pCommId="+comm_id;
		show =	window.open(page, "showCommInfo", "toolbar=false,directories=false,status=false,menubar=false,width=450,height=350,scollbar=true,resizable=yes");
	}

</script>
								<tr valign="top">
									<td colspan="2" height="10"></td>
								</tr>
								<tr valign="top">
									<!-- community title -->
									<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b>폐쇄신청 동아리</b></font></td>
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
<%	ListDTO listObj = (ListDTO)model.get("commInfoClosingList");	%>
<%= listObj.getPageScript("f", "curPage", "goPage") %>
								<tr valign="top">
									<td colspan="2" class="com_content_top">
									<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name="f" method="post" action="<%=CONTEXTPATH%>/CommManage.cmd?cmd=getCommInfoPagingList" >
<input type="hidden" name="curPage" >

											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="">동아리명</td>
												<td class="s_tablien"></td>
												<td width="80">시샵</td>
												<td class="s_tablien"></td>
												<td width="100">폐쇄여부</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	int No = listObj.getStartPageNum();
	RowSet list= listObj.getItemList();

	if(listObj.getItemCount() > 0){

		while(list.next()){
			String useYn=StringUtil.nvl(list.getString("use_yn"),"N");
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04_cen"><a href="javascript:showCommInfo('<%=list.getInt("comm_id")%>')">&nbsp;<%=list.getString("comm_name")%></a></td>
												<td></td>
												<td class="s_tab04_cen"><%=list.getString("user_name")%></td>
												<td></td>
												<td class="s_tab04_cen">
													<a href="javascript:changeClosing('Y','<%=list.getInt("comm_id")%>')"><b>[폐 쇄]</b></a>
													&nbsp;<a href="javascript:changeClosing('N','<%=list.getInt("comm_id")%>')"><b>[취 소]</b></a>
												</td>
											</tr>
<%			if(No > 1) { %>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
			}
			No = No - 1;
		}
		list.close();

	} else {
%>
											<tr>
												<td class="s_tab04_cen" colspan="11">폐쇄신청 동아리가 없습니다.</td>
											</tr>
<%
	}
%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="10" align="right">

												</td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
<%
	if(listObj.getTotalItemCount() > 0){
%>
													<table valign=top height="25">
														<tr>
															<td><%= listObj.getPagging(SYSTEMCODE,"goPage") %></td>
														</tr>
													</table>
<%
	}
%>
												</td>
											</tr>
											<!-- // 페이지 리스트, 검색부분 -->
</form>
<!-- form end -->

										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/community_footer.jsp" %>
