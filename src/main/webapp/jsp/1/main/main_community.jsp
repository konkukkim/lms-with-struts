<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO,java.util.* "%>
<%@include file="../common/community_header.jsp" %>
<%
	String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE;
%>
<script language="javascript">
<!--
	function commSearch(){
		var f = document.f;
		if(!validate(f)) return;

		f.submit();
	}

	function showInviteMsg(){
		var winOption = "left="+windowLeftPosition(420)+",top="+windowTopPosition(350)+",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=yes,resizable=no,width=420,height=350";
		var loc = "/CommInvite.cmd?cmd=inviteMsgList&MENUNO=0";
		var ShowInfo = window.open(loc,"inviteWindow",winOption);
	}

	function goCommunity(commId){
		document.location = "Community.cmd?cmd=goCommunity&pCommId=" + commId + "&MENUNO=0";
	}
//-->
</script>
								<tr valign="top">
									<td colspan="2" class="com_content_top">
									<!-- 내용 -->
										<!-- 동아리 시작-->
										<table width="100%" align="center">
											<tr>
												<td class="com_search01"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_iconsearch.gif" align="absmiddle"> <b>동아리검색</b>&nbsp;</td>
<!-- form start -->
<form name="f" method="post" action="<%=CONTEXTPATH%>/Community.cmd?cmd=commPagingList&MENUNO=0" >
												<td class="com_search02" align=left height=30>
													<select name="pFields">
														<option value="comm_name" selected>동아리 명</option>
														<option value="user_name">시샵명</option>
														<option value="keyword">키워드</option>
													</select>
													&nbsp;<input maxlength=58 size=67 name=pValue id="pValue" value="" dispName="검색어" lenMCheck="2" lenCheck="20">
													<a href="javascript:commSearch();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_comsearch.gif" width=40 height=20 align=absmiddle></a>
												</td>
</form>
<!-- form end -->
											</tr>
										</table>
										<table cellspacing=0 cellpadding=0 width="100%" border=0>
											<tr>
												<td class="com_titform" valign=top align=left width=450><img height=21 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_tit01.gif" width=106> </td>
												<td class="com_titform" valign=top align=left><a href="<%=CONTEXTPATH%>/Community.cmd?cmd=commPridebbsPagingList&pBbsId=1&MENUNO=0"><img 
													src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_tit02.gif" width="198" height="24" border=0 align=absmiddle></td>
											</tr>
											<tr>
												<!-- 동아리분류 -->
												<td valign=top align=left width=485>
													<table cellspacing=0 cellpadding=0 width=450 background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_mbg.gif" border=0>
														<tr>
															<td colspan=2 height=14><img height=14 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_mtop.gif" width=450 align=absmiddle></td>
														</tr>
														<tr>
															<td valign=top align=right width=100><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_mimg.gif" width="75" height="65" align=absmiddle> </td>
															<td style="padding-right: 0px; padding-left: 10px; padding-bottom: 0px; padding-top: 0px" valign=top align=middle width=365>
																<table cellspacing=0 cellpadding=0 width="100%">
<%	String pCateCode  = (String)model.get("pCateCode");
    int cnt4 = 0;
    RowSet list4= (RowSet)model.get("rs4");

    while (list4.next()) {

        if ((cnt4+1) % 3 == 1) {
        	out.println("<tr> ");
        }
%>
																		<td align=left width="33%" height=23><a class=b_title02 href="<%=CONTEXTPATH%>/Community.cmd?cmd=commPagingList&MENUNO=0&pCateCode=<%=list4.getString("cate_code")%>"><%=list4.getString("cate_name")%></a></td>
<%

		if((cnt4+1) % 3 == 0) {
			out.println("</tr> ");
			out.println("<tr> ") ;
		}
		cnt4 = cnt4 + 1;
	}
%>
																</table>
															</td>
														</tr>
														<tr>
															<td colspan=2 height=14><img height=14 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_mbottom.gif" width=450></td>
														</tr>
													</table>
												</td>
												<!-- 동아리 자랑 -->
												<td valign=top align=middle>
													<table cellspacing=0 cellpadding=0 width="95%" border=0>
<%	int cnt2 = 0;
	RowSet list2= (RowSet)model.get("rs2");
	String	pMainImg	=	"";

	if (list2!=null) {
		while (list2.next()) {
			if(!StringUtil.nvl(list2.getString("main_img")).equals("")) {
				pMainImg = list2.getString("main_img");
			} else {
				pMainImg = CONTEXTPATH+"/img/"+SYSTEMCODE+"/community/com_inpic01.gif";
			}
%>
														<tr height=87 valign="top">
															<td width="90" align="left">
																<table height=77 width=82 background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/center_bg2.gif">
																	<tr>
																		<td align=center><img height=67 src="<%=pMainImg%>" width=72></td>
																	</tr>
																</table>
															</td>
															<td width="130" align="left">
																<table>
																	<tr height=24 valign="top">
																		<td><font color="#ff6600"><a href="<%=CONTEXTPATH%>/Community.cmd?cmd=commPrideContentsShow&pMode=<%=PMODE%>&pCommId=<%=list2.getInt("comm_id")%>&pBbsId=<%=list2.getInt("bbs_id")%>&pSeqNo=<%=list2.getInt("seq_no")%>"><%=StringUtil.setMaxLength(list2.getString("subject"), 25)%></a></font></td>
																	</tr>
																	<tr>
																		<td height="22"><a
																			href="<%=CONTEXTPATH%>/Community.cmd?cmd=commPrideContentsShow&pMode=<%=PMODE%>&pCommId=<%=list2.getInt("comm_id")%>&pBbsId=<%=list2.getInt("bbs_id")%>&pSeqNo=<%=list2.getInt("seq_no")%>"><img
																			src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/btn_quick.gif" width="74" height="16"></a>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
<%			cnt2++;
		}

		list2.close();
	}

	for (int i =cnt2; i < 4; i++) {
		if (i==0 && cnt2==0) {	%>
														<tr height=87 valign="top">
															<td align="left" colspan="2">
																<table width="100%" align="center">
																	<tr>
																		<td align="center" valign="middle">등록된 내용이 없습니다.</td>
																	</tr>
																</table>
															</td>
														</tr>
<%		} else {

		}
	}	%>
													</table>
												</td>
											</tr>
											<tr>
												<td height="25"></td>
											</tr>
											<tr>
												<td class="com_titform" valign="top" align="left"><img height=19 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_tit03.gif" width=104></td>
												<td class="com_titform" valign="top" align="left"><a href="<%=CONTEXTPATH%>/Community.cmd?cmd=newCommunity&MENUNO=0"><img 
													src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_tit04.gif" width="198" height="24" border=0 align=absmiddle></a></td>
											</tr>
											<tr>
												<!-- 나의 동아리 -->
												<td valign=top align=left>
													<table cellspacing=0 cellpadding=0 width=450 border=0>
														<tr class="com_lien01">
															<td colspan="2"></td>
														</tr>
<%	int cnt3 = 0;
	RowSet list3= (RowSet)model.get("rs3");
	String	pCommMainImg	=	"";
	String	pCommMainImgUrl	=	"";

	if (list3!= null) {
		while (list3.next()) {
			pCommMainImg	=	StringUtil.nvl(list3.getString("main_img"));
			if(!pCommMainImg.equals("")) {
				pCommMainImgUrl	=	CONTEXTPATH+"/data/"+StringUtil.nvl(list3.getString("main_img"));
			} else {
				pCommMainImgUrl	=	CONTEXTPATH+"/img/"+SYSTEMCODE+"/community/pic01.gif";
			}
		
%>
														<tr>
															<td align=middle width="17%" height=50><img height=36 src="<%=pCommMainImgUrl%>" width=55></td>
															<td height=50><a href="<%=CONTEXTPATH%>/Community.cmd?cmd=goCommunity&pCommId=<%=list3.getInt("comm_id")%>&MENUNO=0"><%=list3.getString("comm_name")%></a></td>
														</tr>
														<tr>
															<td class="com_lien02" colspan="2"></td>
														</tr>
<%			cnt3++;
		}
		list3.close();
	}

	for (int i =cnt3; i < 4; i++) {
		if (i ==0 && cnt3 == 0) {	%>
														<tr>
															<td height="50" colspan="2">가입한 동아리가 없습니다.</td>
														</tr>
<%		}
	}	%>
													</table>
												</td>
												<!-- 신규동아리 -->
												<td width="220" align=middle valign="top">
													<table cellspacing=0 cellpadding=0 width="90%" border=0>
<%	int cnt = 0;
	RowSet list= (RowSet)model.get("rs");

	while (list.next() && cnt < 4) {
		cnt++;	%>
														<tr>
															<td valign=center align=left width="8%" height=22><img height=14 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet03.gif" width=11 align="absmiddle"></td>
															<td height=22><a href="Community.cmd?cmd=goCommunity&pCommId=<%=list.getInt("comm_id")%>"><%=list.getString("comm_name")%></a></td>
														</tr>
														<tr>
															<td class="com_lien02" colspan="2"></td>
														</tr>
<%	}
	list.close();

	if(cnt==0) {	%>
														<tr>
															<td height="22" colspan="2">신규 동아리가 없습니다.</td>
														</tr>
<%	}	%>
													</table>
												</td>
											</tr>
										</table>
										<!-- // 커뮤니티 끝--> 
									<!-- // 내용 -->
									</td>
<%
	int inviteMsgCnt = Integer.parseInt((String)model.get("inviteMsgCnt"));
	if(inviteMsgCnt > 0) {
%>
<script language="javascript">
	showInviteMsg();
</script>
<%	}	%>
<%@include file="../common/community_footer.jsp" %>
