<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@include file="../common/community_header.jsp" %>
<% String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE;%>
<script language="javascript">
	function changeBestYn(best_yn,comm_id) {
		var st = confirm("추천동아리 여부를 변경하시겠습니까?");
		if ( st == true ) {
			document.location.href="<%=CONTEXTPATH%>/CommManage.cmd?cmd=commInfoBestYnChange&pBestYn="+best_yn+"&pCommId="+comm_id+"&pCurPos=LIST";
		}
	}

    // 동아리정보 팝업 윈도우
	function showCommInfo(comm_id) {
		var page = "<%=CONTEXTPATH%>/CommManage.cmd?cmd=commInfoShow&pCommId="+comm_id;
		show = window.open(page, "showCommInfo", "toolbar=false,directories=false,status=false,menubar=false,width=600,height=450,scollbar=true,resizable=yes");
	}

	// 동아리 가기
	function goCommunity(commid){
        document.location = "<%=CONTEXTPATH%>/Community.cmd?cmd=goCommunity&pCommId="+commid+"&MENUNO=0";
    }

	// 시샵 변경하기
	function changeMaster(commid){
		var winOption = "left="+windowLeftPosition(400)+",top="+windowTopPosition(200)+",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=yes,resizable=no,width=400,height=200";
		var loc = "<%=CONTEXTPATH%>/CommManage.cmd?cmd=changeMemberList&pCommId="+commid;
		var ShowInfo = window.open(loc,"commChangeMaster",winOption);
	}
</script>
								<tr valign="top">
									<td colspan="2" height="10"></td>
								</tr>
								<tr valign="top">
									<!-- community title -->
									<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b>동아리 관리</b></font></td>
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
<%
	ListDTO listObj = (ListDTO)model.get("commInfoPagingList");
%>
<%= listObj.getPageScript("f", "curPage", "goPage") %>
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td>
&nbsp;<img src="<%=img_path%>/common/blet02.gif" align="absmiddle" border="0">시삽 이름을 누르시면 시삽을 바꿀 수 있습니다.<br>
&nbsp;<img src="<%=img_path%>/common/blet02.gif" align="absmiddle" border="0">추천동아리 설정 시 추천동아리로 등록이 됩니다.<br>
&nbsp;<img src="<%=img_path%>/common/blet02.gif" align="absmiddle" border="0">동아리명 선택시 동아리 상세 정보를 볼 수 있습니다.
															</td>
															<td align=right height=30>

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
												<td width="">동아리명</td>
												<td class="s_tablien"></td>
												<td width="80">회원수</td>
												<td class="s_tablien"></td>
												<td width="70">시샵</td>
												<td class="s_tablien"></td>
												<td width="70">수정</td>
												<td class="s_tablien"></td>
												<td width="100">추천동아리</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	int No = listObj.getStartPageNum();
	RowSet list= listObj.getItemList();

	if(listObj.getItemCount() > 0) {

		while(list.next()) {
			int commId = list.getInt("comm_id");
			String commName = StringUtil.nvl(list.getString("comm_name"));
			String bestYn=StringUtil.nvl(list.getString("best_yn"),"N");
			String masterName = StringUtil.nvl(list.getString("user_name"));
			int cnt = list.getInt("cnt");
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04"><a href="javascript:goCommunity('<%=commId%>')"><%=commName%></a></td>
												<td></td>
												<td class="s_tab04_cen"><%=cnt%> 명</td>
												<td></td>
												<td class="s_tab04_cen"><a href="javascript:changeMaster('<%=commId%>');"><%=masterName%></a></td>
												<td></td>
												<td class="s_tab04_cen"><a href="/Community.cmd?cmd=makeCommunity&pWhere=M&pGubun=Edit&pCommId=<%=commId%>"><b>[수정]</b></a></td>
												<td></td>
												<td class="s_tab04_cen">
<%
			if(bestYn.equals("Y"))
				out.println("예");
			else
				out.println("아니오");
%>
													<a href="javascript:changeBestYn('<%=bestYn%>','<%=commId%>')"><b>[변경]</b></a>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
			No = No-1;
		}
	}else{
%>

											<tr>
												<td class="s_tab04_cen" colspan="11">동아리가 없습니다.</td>
											</tr>
<%
	}
	list.close();
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
	if(listObj.getItemCount() > 0) {
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
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/community_footer.jsp" %>