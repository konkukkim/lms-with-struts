<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/community_header.jsp" %>
<%
	String cateName;
	String commName;
	String commSynopsis;
	String commMaster;
	String nickName;
	String makeDate;
	String levelName = CommonUtil.getCodeSoName(SYSTEMCODE, "103", COMMINFO.userLevel);
	String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE;
	String mainImg = img_path+"/community/com_simg.gif";

	int memberCount;

	String commId 	= (String)model.get("commId");
	RowSet list		= (RowSet)model.get("rs");

	list.next();
	cateName 	= list.getString("cate_name");
	commName	= list.getString("comm_name");
	commSynopsis= list.getString("comm_synopsis");
	commMaster	= list.getString("comm_master");
	memberCount = list.getInt("tntmembers");
	nickName 	= list.getString("nick_name");
	makeDate	= DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("reg_date")));
	mainImg		= StringUtil.nvl(list.getString("main_img"));


	if(!("").equals(mainImg)){
		mainImg	= "/data/" + list.getString("main_img");
	} else {
		mainImg = img_path+"/community/com_simg.gif";
	}

	list.close();
%>
<script language="javascript">
	function viewCommunity(commId){
		document.location = "Community.cmd?cmd=goCommunity&pCommId=" + commId + "&MENUNO=0";
	}

	function joinCommunity(commId){
		if(confirm('동아리 가입하시겠습니까?\n\n가입시 나의동아리 메뉴로 이동합니다.')){
			document.location = "Community.cmd?cmd=joinCommunity&pCommId=" + commId + "&MENUNO=0";
		} else {
		}
	}
</script>

						<tr valign="top">
							<td colspan="2" height="10"></td>
						</tr>
						<tr valign="top">
							<!-- community title -->
							<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b>동아리</b></font></td>
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
							<!-- 커뮤니티 소개 --> 
								<table width="660" align="center">
									<tr>
										<td class="com_tabtit"><img src="../img/1/community/icon_title.gif" width="9" height="9" vspace=1 align="absmiddle">&nbsp;우리커뮤니티 소개</td>
									</tr>
									<tr>
										<td>
											<table width="625" align="center">
												<tr>
													<td align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_ttop01.gif" width="625" height="10"></td>
												</tr>
												<tr>
													<td align="center" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_tbg01.gif">
														<table style="table-layout: fixed" cellspacing=0 cellpadding=0 width="625" border=0  align="center" >
															<tr>
																<td align=middle width=230><img height=159 src="<%=mainImg%>" width=201></td>
																<td style="padding-right: 5px; padding-left: 5px; padding-bottom: 5px; padding-top: 5px" valign=top width=395>
																	<table width="100%">
																		<tr>
																			<td height="24" colspan="4"><font class="com_text01"><b><%=commName%></b></font></td>
																		</tr>
																		<tr valign="top">
																			<td height="74" colspan="4"><%=StringUtil.getHtmlContents(commSynopsis)%></td>
																		</tr>
																		<tr>
																			<td width="23%" height="20" align="center" style="padding:0 0 0 35"><b>시삽</b></td>
																			<td width="21%"><%=nickName%></td>
																			<td width="23%" align="center" style="padding:0 0 0 25"><b>등급</b></td>
																			<td width="33%"><%=levelName%></td>
																		</tr>
																		<tr>
																			<td align="center" style="padding:0 0 0 35"><b>분류</b></td>
																			<td><%=cateName%></td>
																			<td align="center" style="padding:0 0 0 25"><b>개설일</b></td>
																			<td><%=makeDate%></td>
																		</tr>
																	</table>
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_tbottom01.gif" width="625" height="10"></td>
												</tr>
												<tr>
													<td height="25"></td>
												</tr>
											</table>
										</td>
										</tr>
									</table>
									<!-- // 동아리 소개 -->
									<!-- 게시판 리스트 시작 -->
									<table width="660" align="center">
										<tr>
											<td class="com_tabtit" colspan="7"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/icon_title.gif" width="9" height="9" vspace=1 align="absmiddle">&nbsp;최근 게시물</td>
										</tr>
										<tr class="com_tab01">
											<td colspan="7"></td>
										</tr>
										<tr>
											<td width="376" class="com_tab02">제목</td>
											<td class="com_tablien"></td>
											<td width="110" class="com_tab02">등록자</td>
											<td class="com_tablien"></td>
											<td width="100" class="com_tab02">둥록일</td>
											<td class="com_tablien"></td>
											<td width="70" class="com_tab02">조회수</td>
										</tr>
										<tr class="com_tab03">
											<td colspan="7"></td>
										</tr>
<%	String bbsId = "";
    String bbsName="";
    int seqNo = 0;
    String subject;
    String regName;
    String regDate;
    int hitNo;
    int cnt=0;
    RowSet listup= (RowSet)model.get("rs3");

    while(listup.next()){
        cnt++;

        bbsId = StringUtil.nvl(listup.getString("bbs_id"));
        bbsName = "[" + StringUtil.nvl(listup.getString("bbs_name")) + "] ";
        seqNo = StringUtil.nvl(listup.getString("seq_no"),0);
        subject = StringUtil.nvl(listup.getString("subject"));
        regName = StringUtil.nvl(listup.getString("reg_name"));
        regDate = StringUtil.nvl(listup.getString("reg_date"));
        hitNo   = StringUtil.nvl(listup.getString("hit_no"),0);
%>
										<tr>
											<td class="com_tab04" colspan="2"><%=bbsName%><a href="<%=CONTEXTPATH%>/CommSubBoard.cmd?cmd=commSubBoardShow&pCommId=<%=commId%>&pBbsId=<%=bbsId%>&pSeqNo=<%=seqNo%>&pMode=<%=PMODE %>"><%=subject%></a></td>
											<td class="com_tab04_cen" colspan="2"><%=regName%></td>
											<td class="com_tab04_cen" colspan="2"><%=DateTimeUtil.getDateType(1,regDate)%></td>
											<td class="com_tab04_cen"><%=hitNo%></td>
										</tr>
										<tr class="com_tab03">
											<td colspan="7"></td>
										</tr>
<%	}

    if (cnt<=0) {	%>
										<tr>
											<td class="s_tab04_cen" colspan="11">등록된 게시글이 없습니다.</td>
										</tr>
<%	}	%>
										<tr class="com_tab05">
											<td colspan="7"></td>
										</tr>
										<tr>
											<td colspan="7" height="25"></td>
										</tr>
									</table>
									<!-- // 게시판 리스트  끝 -->
								</td>
<%@include file="../common/community_footer.jsp" %>