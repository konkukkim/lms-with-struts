<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<!-- AHN20120806  1630 -->
<%@include file="../common/header.jsp" %>
<%
	int		curriNo		=	0;
	String	pCurriCode	=	"";
	String	pServerPath	=	"";
	int		pCurriYear	=	0;
	int		pCurriTerm	=	0;
	String	pCourseId	=	"";
	String	pContentsId	=	"";
	
%>
<script>
<!--
	function curriContentsIntroduction(curricode, curriyear, curriterm, courseid, contentsid) {
		var param	=	"&pCurriCode="+curricode+"&pCurriYear="+curriyear+"&pCurriTerm="+curriterm+"&pCourseId="+courseid+"&pContentsId="+contentsid;
		var strUrl	=	"<%=CONTEXTPATH%>/CurriContents.cmd?cmd=getContentsIntroduction"+param;
		window.open(strUrl, "contentsIntroduce", "width=600, height=400,top=0, left=0, directories=no,status=no,menubar=no,scrollbars=yes,resizable=no");
	}
	
	function contentsList(pareocode1, parecode2) {
		var param = "&pPareCode1="+pareocode1+"&pPareCode2="+parecode2;
		document.location.href = "<%=CONTEXTPATH%>/CurriSub.cmd?cmd=currentCurriContentsList&pMode=Home&MENUNO=0"+param;	
	}
	
	function goPublishCourse() {
		document.location.href = "<%=CONTEXTPATH%>/PublishCurriSub.cmd?cmd=publishCurriSubPageList&pPareCode1=99999&pPareCode2=00001&pGubun=1&MENUNO=1&pMode=Home&MainMenu=Y";	
	}
	
	function goPublishContents(pCode, pYear, pTerm, gubun) {
		var param	=	"&pCurriCode="+pCode+"&curriYear="+pYear+"&curriTerm="+pTerm+"&pGubun="+gubun;
		document.location.href	=	"<%=CONTEXTPATH%>/CurriContents.cmd?cmd=lecContentsList&MENUNO=1&pMode=Home"+param;
	}
//-->
</script>
						<!-- 정규강좌, 공개강좌 -->
						<table width="670" align="center" cellpadding="0" cellspacing="0">
							<tr valign="top">
								<td colspan="2"height="23"></td>
							</tr>
							<tr valign="top">
								<!-- 정규강좌 -->
								<td width="370">
									<table width="335">
										<tr>
											<td width="335" height="12" colspan="4" class="login_bbs_tit01"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tit01.gif"><img 
											src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_more01.gif" 
											onClick="contentsList('00001', '')" style="cursor:hand;"></td>
										</tr>
										<tr>
											<td width="82" height="22"><a href="javascript:contentsList('00001', '1');"><img 
												src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tab01_on.gif" border="0" name=sMENU1 onmouseover="chng('sMENU1','sMenu1B');chng('sMENU2','sMenu2A');chng('sMENU3','sMenu3A');MM_showHideLayers('clist01','','show','clist02','','hide','clist03','','hide')"></a></td>
											<td width="82" height="22"><a href="javascript:contentsList('00001', '2');"><img 
												src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tab02.gif" border="0" name=sMENU2 onmouseover="chng('sMENU2','sMenu2B');chng('sMENU1','sMenu1A');chng('sMENU3','sMenu3A');MM_showHideLayers('clist02','','show','clist01','','hide','clist03','','hide')"></a></td>
											<td width="82" height="22"><a href="javascript:contentsList('00001', '3');"><img 
												src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tab03.gif" border="0" name=sMENU3 onmouseover="chng('sMENU3','sMenu3B');chng('sMENU1','sMenu1A');chng('sMENU2','sMenu2A');MM_showHideLayers('clist03','','show','clist01','','hide','clist02','','hide')"></a></td>
											<td width="89" height="22" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tablien01.gif"></td>
										</tr>
										<tr>
											<td colspan=4 valign=top style="padding:7 0 0 0">
<!-- 1학년 -->
<DIV id=clist01 style="Z-INDEX: 1; VISIBILITY: visible; WIDTH: 335px; POSITION: absolute">
												<table width="100%">
<%
	RowSet	curriList01	=	(RowSet)model.get("CurriList01");
	
	while(curriList01.next()) {
		curriNo++;
		pCurriCode	=	StringUtil.nvl(curriList01.getString("curri_code"));
		pCurriYear	=	curriList01.getInt("curri_year");
		pCurriTerm	=	curriList01.getInt("curri_term");
		pCourseId	=	StringUtil.nvl(curriList01.getString("course_id"));
		pContentsId	=	StringUtil.nvl(curriList01.getString("contents_id"));
%>
													<tr>
														<td width="14" align="center" class="login_tab01"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon03.gif" width="10" height="7" align="absmiddle"></td>
														<td width="309" style="padding:0 5 0 0" class="login_tab02"><a 
															href="javascript:curriContentsIntroduction('<%=pCurriCode%>','<%=pCurriYear%>','<%=pCurriTerm%>','<%=pCourseId%>', '<%=pContentsId%>')"><font 
															class="login_font01">[<%=StringUtil.cropByte(StringUtil.nvl(curriList01.getString("contents_name")), 27)%>]</font></a></td>
													</tr>
<%
	}
	curriList01.close();
	for(int c = 0; c < 4-curriNo; c++) {
%>
													<tr>
														<td width="14" align="center" class="login_tab01"><!--img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon03.gif" width="10" height="7" align="absmiddle"--></td>
														<td width="309" style="padding:0 5 0 0" class="login_tab02">&nbsp;</td>
													</tr>
<%
	}
%>													
													<tr>
														<td width="335" height="1" colspan="2" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_bottom01.gif"></td>
													</tr>
												</table>
</DIV>
<!-- 2학년 -->
<DIV id=clist02 style="Z-INDEX: 1; VISIBILITY: hidden; WIDTH: 335px; POSITION: absolute">
												<table width="100%">
<%
	RowSet	curriList02	=	(RowSet)model.get("CurriList02");
	curriNo		=	0;
	pCurriCode	=	"";
	pServerPath	=	"";
	pCurriYear	=	0;
	pCurriTerm	=	0;
	pCourseId	=	"";
	pContentsId	=	"";
		
	while(curriList02.next()) {
		curriNo++;
		pCurriCode	=	StringUtil.nvl(curriList02.getString("curri_code"));
		pCurriYear	=	curriList02.getInt("curri_year");
		pCurriTerm	=	curriList02.getInt("curri_term");
		pCourseId	=	StringUtil.nvl(curriList02.getString("course_id"));
		pContentsId	=	StringUtil.nvl(curriList02.getString("contents_id"));
%>
													<tr>
														<td width="14" align="center" class="login_tab01"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon03.gif" width="10" height="7" align="absmiddle"></td>
														<td width="309" style="padding:0 5 0 0" class="login_tab02"><a 
															href="javascript:curriContentsIntroduction('<%=pCurriCode%>','<%=pCurriYear%>','<%=pCurriTerm%>','<%=pCourseId%>', '<%=pContentsId%>')"><font 
															class="login_font01"><%=StringUtil.cropByte(StringUtil.nvl(curriList02.getString("contents_name")), 27)%></font></a></td>
													</tr>
<%
	}
	curriList02.close();
	for(int c = 0; c < 4-curriNo; c++) {
%>
													<tr>
														<td width="14" align="center" class="login_tab01"><!--img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon03.gif" width="10" height="7" align="absmiddle"--></td>
														<td width="309" style="padding:0 5 0 0" class="login_tab02">&nbsp;</td>
													</tr>
<%
	}
%>
													<tr>
														<td width="335" height="1" colspan="2" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_bottom01.gif"></td>
													</tr>
												</table>
												</DIV>
												
												<!-- 3학년 -->
												<DIV id=clist03 style="Z-INDEX: 1; VISIBILITY: hidden; WIDTH: 335px; POSITION: absolute">
												<table width="100%">
<%
	RowSet	curriList03	=	(RowSet)model.get("CurriList03");
	curriNo		=	0;
	pCurriCode	=	"";
	pServerPath	=	"";
	pCurriYear	=	0;
	pCurriTerm	=	0;
	pCourseId	=	"";
	pContentsId	=	"";
		
	while(curriList03.next()) {
		curriNo++;
		pCurriCode	=	StringUtil.nvl(curriList03.getString("curri_code"));
		pCurriYear	=	curriList03.getInt("curri_year");
		pCurriTerm	=	curriList03.getInt("curri_term");
		pCourseId	=	StringUtil.nvl(curriList03.getString("course_id"));
		pContentsId	=	StringUtil.nvl(curriList03.getString("contents_id"));
%>
													<tr>
														<td width="14" align="center" class="login_tab01"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon03.gif" width="10" height="7" align="absmiddle"></td>
														<td width="309" style="padding:0 5 0 0" class="login_tab02"><a 
															href="javascript:curriContentsIntroduction('<%=pCurriCode%>','<%=pCurriYear%>','<%=pCurriTerm%>','<%=pCourseId%>', '<%=pContentsId%>')"><font 
															class="login_font01"><%=StringUtil.cropByte(StringUtil.nvl(curriList03.getString("contents_name")), 27)%></font></a></td>
													</tr>
<%
	}
	curriList03.close();
	for(int c = 0; c < 4-curriNo; c++) {
%>
													<tr>
														<td width="14" align="center" class="login_tab01"><!--img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon03.gif" width="10" height="7" align="absmiddle"--></td>
														<td width="309" style="padding:0 5 0 0" class="login_tab02">&nbsp;</td>
													</tr>
<%
	}
%>
													<tr>
														<td width="335" height="1" colspan="2" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_bottom01.gif"></td>
													</tr>
												</table>
												</DIV>
											</td>
										</tr>
									</table>
								</td>
								<!-- 공개강좌 -->
								<td width="300">
									<table width="300" align="right">
										<tr valign="top">
											<td colspan="2" class="login_bbs_tit01"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tit02.gif"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_more01.gif" 
											onClick="goPublishCourse()" style="cursor:hand;"></td>
										</tr>
<%
	RowSet	curriList	=	(RowSet)model.get("CurriList");
	curriNo		=	0;
	pCurriCode	=	"";
	pServerPath	=	"";
	pCurriYear	=	0;
	pCurriTerm	=	0;
	String	pareCode2	=	"";
	String	gubun		=	"";
	
	while(curriList.next()) {
		curriNo++;
		pCurriCode	=	StringUtil.nvl(curriList.getString("curri_code"));
		pCurriYear	=	curriList.getInt("curri_year");
		pCurriTerm	=	curriList.getInt("curri_term");
		pareCode2	=	StringUtil.nvl(curriList.getString("pare_code2"));
		if(pareCode2.equals("00001")) {
			gubun	=	"1";
		} else {
			gubun	=	"2";
		}
%>
										<tr>
											<td width="14" align="center" class="login_tab01"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon03.gif" width="10" height="7" align="absmiddle"></td>
											<td width="309" style="padding:0 5 0 0" class="login_tab03"><a 
href="javascript:goPublishContents('<%=pCurriCode%>','<%=pCurriYear%>','<%=pCurriTerm%>', '<%=gubun%>')" class="lg_notice01"><%=StringUtil.cropByte(StringUtil.nvl(curriList.getString("curri_name")), 18)%></a></td>
										</tr>
<%
		gubun	=	"";
	}
	curriList.close();
	for(int c = 0; c < 5-curriNo; c++) {
%>
										<tr>
											<td width="14" align="center" class="login_tab01"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon03.gif" width="10" height="7" align="absmiddle"></td>
											<td width="309" style="padding:0 5 0 0" class="login_tab03" height="20">&nbsp;</td>
										</tr>
<%
	}
%>
										<tr>
											<td width="335" height="2" colspan="2"></td>
										</tr>
										<tr>
											<td width="335" height="1" colspan="2" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_bottom01.gif"></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<!-- 학교소식 -->
						<table width="670" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td class="bbs_pd"></td>
							</tr>
							<tr valign="top">
								<td width="472">
									<table width="472">
										<tr valign="top">
<td width="82" height="22"><A onmouseover="chng11('s1MENU1','s1Menu1B');chng11('s1MENU2','s1Menu2A');chng11('s1MENU3','s1Menu3A');MM_showHideLayers('clist11','','show','clist22','','hide','clist33','','hide')" href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=17&pMode=News&MENUNO=0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tab11_on.gif" border="0" name=s1MENU1></a></td>
<td width="82" height="22"><A onmouseover="chng11('s1MENU2','s1Menu2B');chng11('s1MENU1','s1Menu1A');chng11('s1MENU3','s1Menu3A');MM_showHideLayers('clist22','','show','clist11','','hide','clist33','','hide')" href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=18&pMode=News&MENUNO=0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tab12.gif" border="0" name=s1MENU2></a></td>
<td width="82" height="22"><A onmouseover="chng11('s1MENU3','s1Menu3B');chng11('s1MENU1','s1Menu1A');chng11('s1MENU2','s1Menu2A');MM_showHideLayers('clist33','','show','clist11','','hide','clist22','','hide')" href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=19&pMode=News&MENUNO=0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tab13.gif" border="0" name=s1MENU3></a></td>
<td width="180" height="22" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tablien02.gif"></td>
<td width="46" height="22"><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=17&pMode=News&MENUNO=0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_more02.gif" border="0"></a></td>
										</tr>
										<tr>
											<td colspan=5 valign="top">
												<!-- 학사공지 -->
<DIV id=clist11 style="Z-INDEX: 1; VISIBILITY: visible; WIDTH: 472px; POSITION: absolute">
												<table width="100%" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tabbg.gif">
													<tr>
														<td colspan="3"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tabtop.gif"></td>
													</tr>
													<tr>
														<td colspan="3" height="8"></td>
													</tr>
<!-- 학사공지 시작 -->
<%
	ListDTO listObj = (ListDTO)model.get("Haksa_NoticeList");
	RowSet HaksaList= listObj.getItemList();


	int haksaNum = 0;
	while (HaksaList.next()) {
		haksaNum++;
%>
													<tr>
														<td width="14" align="center" class="login_tab04"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon05.gif" width="10" height="7" align="absmiddle"></td>
														<td width="388" style="padding:0 5 0 0" class="login_tab05"><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsShow&pMode=Help&pBbsId=<%=HaksaList.getInt("bbs_id")%>&pSeqNo=<%=HaksaList.getInt("seq_no")%>&curPage=1"><%=StringUtil.cropByte(StringUtil.nvl(HaksaList.getString("subject")),35)%></a><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/new.gif"></td>
														<td width="70" style="padding:0 5 0 0" class="login_tab05"><%=DateTimeUtil.getDateType(1, StringUtil.nvl(HaksaList.getString("reg_date")))%></td>
													</tr>
<%

	}
	HaksaList.close();
	for(int i=1; i <= (4-haksaNum); i++) {
%>
													<tr>
														<td width="14" align="center" class="login_tab04"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon05.gif" width="10" height="7" align="absmiddle"></td>
														<td width="388" style="padding:0 5 0 0" class="login_tab05">&nbsp;</td>
														<td width="70" style="padding:0 5 0 0" class="login_tab05">&nbsp;</td>
													</tr>
<%
	}
%>
<!-- 학사공지 끝 -->
													
													<tr>
														<td width="472" colspan="3" height="3"></td>
													</tr>
													<tr>
														<td width="472" colspan="3"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tabbottom.gif"></td>
													</tr>
												</table>
</DIV>
<!-- 일반공지 -->
<DIV id=clist22 style="Z-INDEX: 1; VISIBILITY: hidden; WIDTH: 472px; POSITION: absolute">
												<table width="100%" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tabbg.gif">
													<tr>
														<td colspan="3"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tabtop.gif"></td>
													</tr>
													<tr>
														<td colspan="3" height="8"></td>
													</tr>
<!-- 공지사항 시작 -->
<%
	ListDTO NoticelistObj = (ListDTO)model.get("NoticeList");
	RowSet noticeList= NoticelistObj.getItemList();


	int noticeNum = 0;
	while (noticeList.next()) {
		noticeNum++;
%>
													<tr>
														<td width="14" align="center" class="login_tab04"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon05.gif" width="10" height="7" align="absmiddle"></td>
														<td width="388" style="padding:0 5 0 0" class="login_tab05"><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsShow&pMode=Help&pBbsId=<%=noticeList.getInt("bbs_id")%>&pSeqNo=<%=noticeList.getInt("seq_no")%>&curPage=1"><%=StringUtil.cropByte(StringUtil.nvl(noticeList.getString("subject")),35)%></a></td>
														<td width="70" style="padding:0 5 0 0" class="login_tab05"><%=DateTimeUtil.getDateType(1, StringUtil.nvl(noticeList.getString("reg_date")))%></td>
													</tr>
<%

	}
	noticeList.close();
	for(int i=1; i <= (4-noticeNum); i++) {
%>
													<tr>
														<td width="14" align="center" class="login_tab04"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon05.gif" width="10" height="7" align="absmiddle"></td>
														<td width="388" style="padding:0 5 0 0" class="login_tab05">&nbsp;</td>
														<td width="70" style="padding:0 5 0 0" class="login_tab05">&nbsp;</td>
													</tr>
<%
	}
%>
<!-- 공지사항 끝 -->

													<tr>
														<td width="472" colspan="3" height="3"></td>
													</tr>
													<tr>
														<td width="472" colspan="3"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tabbottom.gif"></td>
													</tr>
												</table>
</DIV>

<!-- 소식 -->
<DIV id=clist33 style="Z-INDEX: 1; VISIBILITY: hidden; WIDTH: 472px; POSITION: absolute">
												<table width="100%" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tabbg.gif">
													<tr>
														<td colspan="3"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tabtop.gif"></td>
													</tr>
													<tr>
														<td colspan="3" height="8"></td>
													</tr>
<!-- 소식 시작 -->
<%
	ListDTO NewslistObj = (ListDTO)model.get("NewsList");
	RowSet newsList = NewslistObj.getItemList();


	int newsNum = 0;
	while (newsList.next()) {
		newsNum++;
%>
													<tr>
														<td width="14" align="center" class="login_tab04"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon05.gif" width="10" height="7" align="absmiddle"></td>
														<td width="388" style="padding:0 5 0 0" class="login_tab05"><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsShow&pMode=Help&pBbsId=<%=newsList.getInt("bbs_id")%>&pSeqNo=<%=newsList.getInt("seq_no")%>&curPage=1"><%=StringUtil.cropByte(StringUtil.nvl(newsList.getString("subject")),35)%></a></td>
														<td width="70" style="padding:0 5 0 0" class="login_tab05"><%=DateTimeUtil.getDateType(1, StringUtil.nvl(newsList.getString("reg_date")))%></td>
													</tr>
<%

	}
	newsList.close();
	for(int i=1; i <= (4-newsNum); i++) {
%>
													<tr>
														<td width="14" align="center" class="login_tab04"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon05.gif" width="10" height="7" align="absmiddle"></td>
														<td width="388" style="padding:0 5 0 0" class="login_tab05">&nbsp;</td>
														<td width="70" style="padding:0 5 0 0" class="login_tab05">&nbsp;</td>
													</tr>
<%
	}
%>
<!-- 소식 끝 -->

													<tr>
														<td width="472" colspan="3" height="3"></td>
													</tr>
													<tr>
														<td width="472" colspan="3"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tabbottom.gif"></td>
													</tr>
												</table>
</DIV>
											</td>
										</tr>
									</table>
								</td>
								<td width="198" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_img.gif"></td>
							</tr>
						</table>
<%@include file="../common/footer.jsp" %>
