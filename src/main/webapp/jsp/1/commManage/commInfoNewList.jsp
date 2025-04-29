<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/community_header.jsp" %>
<script language="javascript">
	function commAprove(comm_id) {
		var st = confirm("동아리를 승인 하시겠습니까?");
		if(st==true){
	       	document.location="/CommManage.cmd?cmd=approveCommInfo&pCommId="+comm_id;
		}
     }

     // 동아리정보 팝업 윈도우
	function showCommInfo(comm_id) {
		var page = "/CommManage.cmd?cmd=commInfoShow&pCommId="+comm_id;
		show =	window.open(page, "showCommInfo", "toolbar=false,directories=false,status=false,menubar=false,width=420,height=350,scollbar=true,resizable=yes");
	}
</script>
								<tr valign="top">
									<td colspan="2" height="10"></td>
								</tr>
								<tr valign="top">
									<!-- community title -->
									<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b>NEW 동아리</b></font></td>
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
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="50">번호</td>
												<td class="s_tablien"></td>
												<td width="">동아리 명</td>
												<td class="s_tablien"></td>
												<td width="80">시삽</td>
												<td class="s_tablien"></td>
												<td width="150">인증여부</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	RowSet list= (RowSet)model.get("rs");
	int No=0;
	String useYn="N";

	while(list.next()){
		No++;
		useYn=StringUtil.nvl(list.getString("use_yn"),"N");

		if(No > 1) {
%>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%		} %>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04_cen"><a href="javascript:showCommInfo('<%=list.getInt("comm_id")%>')"><%=list.getString("comm_name")%></a></td>
												<td></td>
												<td class="s_tab04_cen"><%=list.getString("user_name")%></td>
												<td></td>
												<td class="s_tab04_cen">
<%
		if(useYn.equals("Y"))
			out.println("예");
		else
			out.println("아니오");
%>
													&nbsp;&nbsp;<a href="javascript:commAprove('<%=list.getInt("comm_id")%>')"><b>[인증]</b></a></td>

											</tr>
<%
	}
	list.close();

	if ( No < 1 ) {
%>
											<tr>
												<td class="s_tab04_cen" colspan="11">NEW 동아리가 없습니다.</td>
											</tr>
<%
	}
%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/community_footer.jsp" %>