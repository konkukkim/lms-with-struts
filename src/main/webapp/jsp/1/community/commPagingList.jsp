<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@include file="../common/community_header.jsp" %>
<%
	String img_path  = CONTEXTPATH + "/img/" + SYSTEMCODE;
%>
<script language="javascript">
	function goAdd() {
		document.location = "#";
	}

	function goList(){
		history.back();
	}

	function goCommunity(openyn, commid){
	    if(openyn == "N"){
	        alert("비공개 동아리이므로 접근하실 수 없습니다.");
	        return;
	    }

        document.location = "<%=CONTEXTPATH%>/Community.cmd?cmd=goCommunity&pCommId="+commid;
    }

 	function viewCommunity(commId){
		document.location = "<%=CONTEXTPATH%>/Community.cmd?cmd=goCommunity&pCommId=" + commId + "&MENUNO=0";
	}

	function joinCommunity(commId){
		if(confirm('동아리에 가입하시겠습니까?\n\n가입시 나의 동아리 메뉴로 이동합니다.')){
			document.location = "<%=CONTEXTPATH%>/Community.cmd?cmd=joinCommunity&pCommId=" + commId + "&MENUNO=0";
		}
	}
</script>

<%	ListDTO listObj = (ListDTO)model.get("commPagingList"); %>
<%= listObj.getPageScript("f", "curPage", "goPage") %>
								<tr valign="top">
									<td colspan="2" height="10"></td>
								</tr>
								<tr valign="top">
									<!-- community title -->
									<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b>운영 동아리</b></font></td>
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
									<!-- 동아리 리스트 -->
										<table width="650" height=69 border=0 align="center" class="com_tbg">
<form name="f">
											<tr>
												<td colspan="2" class="com_ttop"></td>
											</tr>
											<tr>
												<td width="130" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_timg.gif"></td>
												<td width="520" align="center">
													<table width="490" height="49" border=0 cellpadding=3 cellspacing=0>
														<tr>
<%
	String	pCateCode  = (String)model.get("pCateCode");
	String	catename	=	"";
	int cnt = 0;
	RowSet list4= (RowSet)model.get("rs4");
	while(list4.next()){
		if((cnt+1) % 5 == 1) {
				out.println("<tr height=\"20\"> ");
		}
		if (pCateCode.equals(list4.getString("cate_code"))) {
			catename	=	list4.getString("cate_name");
%>
															<td width="155"><img height=19 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet11.gif" width=13 align="absmiddle"><a href="<%=CONTEXTPATH%>/Community.cmd?cmd=commPagingList&MENUNO=0&pCateCode=<%=list4.getString("cate_code")%>" class="b_title02"><b><%=list4.getString("cate_name")%></b></a></td>
<%		} else { 	%>
															<td width="155"><img height=19 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet11.gif" width=13 align="absmiddle"><a href="<%=CONTEXTPATH%>/Community.cmd?cmd=commPagingList&MENUNO=0&pCateCode=<%=list4.getString("cate_code")%>" class="b_title02"><%=list4.getString("cate_name")%></a></td>
<%		}

		if((cnt+1) % 3 == 0) {
			out.println("</tr> ");
			out.println("<tr height=\"20\"> ");
		}
		cnt = cnt + 1;
	}
 %>
													</table>
												</td>
											</tr>
											<tr>
												<td colspan="2" class="com_tbottom"></td>
											</tr>
										</table>
										<table>
											<tr>
												<td height="20"></td>
											</tr>
										</table>
										<!-- 동아리 리스트 -->
<%
	int No = listObj.getStartPageNum();
	int	chkcnt	=	0;

	RowSet list= listObj.getItemList();
	String openYn = "N";
	String bestYn = "N";
	String	bgimg1	=	"05_cen05.gif";
	String	bgimg2	=	"05_cen06.gif";
	String	bgimg3	=	"05_cen07.gif";
	String	bgcolor	=	"#DCEDF1";
	if(listObj.getItemCount() > 0){
		while(list.next()){
			bestYn = StringUtil.nvl(list.getString("best_yn"),"N");
			openYn = StringUtil.nvl(list.getString("open_yn"),"N");

			if ((chkcnt) % 2 == 0) {
				bgimg1	=	"05_cen05.gif";
				bgimg2	=	"05_cen06.gif";
				bgimg3	=	"05_cen07.gif";
				bgcolor	=	"#DCEDF1";
			}
			else {
				bgimg1	=	"05_cen12.gif";
				bgimg2	=	"05_cen10.gif";
				bgimg3	=	"05_cen11.gif";
				bgcolor	=	"#F4FDFF";
			}
			chkcnt++;
%>
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
																		<td><a href="javascript:goCommunity('<%=openYn%>','<%=list.getInt("comm_id")%>')"><font color="#1B5972"><font class="com_text02"><b>[<%=list.getString("comm_name")%>]</b></font></a></td>
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
															- 시삽 : <%=list.getString("user_name")%><br>
															- 회원수 : <%=list.getInt("cnt")%> <br>
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
															<%	if(openYn.equals("Y")) {	%>
																<a href="javascript:viewCommunity('<%=list.getInt("comm_id")%>')"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/btn_look.gif"></a>
															<%	}	%>&nbsp;
															<%	if(!StringUtil.nvl(list.getString("comm_master")).equals(USERID)) {	%>
																<a href="javascript:joinCommunity('<%=list.getInt("comm_id")%>')"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/btn_join.gif"></a>
															<%	}else{	%>
																<a href="javascript:alert('동아리의 시샵 입니다.')"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/btn_join.gif"></a>
															<%	}	%>
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
<%
			No = No-1;
		}
	}else{
%>

<%
	}
	list.close();
%>
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td height="50" align="center" valign="botoom"><%= listObj.getPagging(SYSTEMCODE,"goPage") %></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/community_footer.jsp" %>