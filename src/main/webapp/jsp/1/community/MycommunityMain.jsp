<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@ include file="../common/community_header.jsp" %>
<%
	String img_path = CONTEXTPATH +	"/img/" + SYSTEMCODE;
%>
<script language="javascript">
<!--
	function sayGoodbye(commid,val){
		if(val == 'M'){
			alert('동아리의 시샵은 탈퇴를 할 수 없습니다. \n\n시샵 권한을 회원에게 이양을 하시고 탈퇴를 하십시요.');
		}else{
			if(confirm('동아리를 탈퇴하려고 합니다..\n\n탈퇴하시겠습니까?')){
				window.location = "<%=CONTEXTPATH%>/Community.cmd?cmd=delUserCommunity&pCommId="+commid;
			}
			else{
			}
		}
	}

	function goCommunity(commuseyn, commid){
	     if(commuseyn == "Y"){
		 	 var loc = "<%=CONTEXTPATH%>/Community.cmd?cmd=goCommunity&pCommId="+commid+"&MENUNO=0";
			 document.location = loc;
		 }else{
		     alert("동아리 인증이 되지 않았습니다.\n\n관리자에게 문의하여 주십시요.");
		     return;
		 }
	}

	function showCommInfo(comm_id) {
		var page = "<%=CONTEXTPATH%>/CommManage.cmd?cmd=commInfoShow&pGubun=N&pCommId="+comm_id;
		show =	window.open(page, "showCommInfo", "toolbar=false,directories=false,status=false,menubar=false,width=450,height=350,scollbar=true,resizable=yes");
	}
//-->
</script>
								<tr valign="top">
									<td colspan="2" height="10"></td>
								</tr>
								<tr valign="top">
									<!-- community title -->
									<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b>나의 동아리</b></font></td>
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

											<tr class="com_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="com_tab02">
												<td width="70">구분</td>
												<td class="com_tablien"></td>
												<td width="">동아리명</td>
												<td class="com_tablien"></td>
												<td width="80">시샵</td>
												<td class="com_tablien"></td>
												<td width="90">회원등급</td>
												<td class="com_tablien"></td>
												<td width="90">탈퇴관리</td>
												<td class="com_tablien"></td>
												<td width="90">정보관리</td>
											</tr>
											<tr class="com_tab03">
												<td colspan="11"></td>
											</tr>
<%
	int cnt = 0;
	String useYn = "";
	String goUrl = "";
	String useMsg = "";
	String useNo = "";
	String commUseYn = "N";
	int commId			= 0;
	String cate_code	= "";
	String userLevel	= "";
	String levelName    = "";
	RowSet list= (RowSet)model.get("rs");
	while(list.next()){
		cnt++;
		commId		= list.getInt("comm_id");
		cate_code	= list.getString("cate_code");
		userLevel	= list.getString("user_level");
		useYn 		= list.getString("use_yn");
		commUseYn 	= list.getString("comm_use_yn");
		levelName 	= list.getString("level_name");

		if(cnt > 1) {
%>
											<tr class="com_tab03">
												<td colspan="11"></td>
											</tr>
<%		} %>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="com_tab04_cen"><%=list.getString("cate_name")%></td>
												<td></td>
												<td class="com_tab04"><a href="javascript:goCommunity('<%=commUseYn%>','<%=commId%>')"><%=list.getString("comm_name")%></a></td>
												<td></td>
												<td class="com_tab04_cen"><%=list.getString("comm_master")%></td>
												<td></td>
												<td class="com_tab04_cen"><%=levelName%></td>
												<td></td>
												<td class="com_tab04_cen">
<%
		if (useYn.equals("Y")) {
			if(commUseYn.equals("Y")) {
%>
													<a href="javascript:sayGoodbye('<%=commId%>','<%=userLevel%>')"><img src="<%=img_path%>/button/sbt_exit.gif" align="absmiddle" border="0"></a>
<%
			} else {
				out.println("인증필요");
			}
		} else {
			out.println("인증필요");
		}
%>
												</td>
												<td></td>
												<td class="com_tab04_cen">
<%
		if (userLevel.equals("M")) {
%>
													<a href="<%=CONTEXTPATH%>/Community.cmd?cmd=makeCommunity&pGubun=Edit&pCommId=<%=commId%>"><img src="<%=img_path%>/button/sbt_mody.gif" align="absmiddle" border="0"></a>
<%
		} else {
%>
													<a href="javascript:showCommInfo('<%=commId%>');"><img src="<%=img_path%>/button/bttn_view.gif" align="absmiddle" border="0"></a>
<%
		}
%>
												</td>
											</tr>
<%
	}
	list.close();

	if(cnt == 0) {
%>

											<tr>
												<td class="com_tab04_cen" colspan="11">가입한 동아리가 없습니다.</td>
											</tr>
<%
	}
%>
											<tr class="com_tab05">
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