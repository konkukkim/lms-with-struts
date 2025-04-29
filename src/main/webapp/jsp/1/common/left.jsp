<%--
         메뉴 주소 걸 시 주의 사항 : 모든 메뉴에서의 링크에 서브메뉴가 없으면 &MENUNO=0를 붙여 주십시요
         그리고   서브메뉴가 있을 시 상위 메뉴가 menu2라면 &MENUNO=2라고 메뉴 번호를 넣어주시면 감사하겠습니다.
         상위 메뉴는 당연히 &MENUNO=0이겠지요..
         
         MainMenu=Y  -> 로그인 하기 전의 메인메뉴들의 타이틀과 네비게이션을 Header 파일에서 제어하기 위한 변수임.
--%>
<%@ page language="java"%>
<%@ page import = "com.edutrack.progauthor.dao.ProgMenuDAO"%>
<%@ page import ="com.edutrack.progauthor.dto.ProgMenuDTO"%>
<%@ page contentType="text/html; charset=euc-kr" %>

<%
// declare global variable
	ProgMenuDAO MenuDao = new ProgMenuDAO();
    ProgMenuDTO MenuInfo = new ProgMenuDTO();
    ArrayList menuList = new ArrayList();
    
    int subMenuCnt = 0;
    int tmpSubCnt = 0;
    int menuCnt = 0;

	if (PMODE.equals("Home") || PMODE.equals("Info") || PMODE.equals("Enter") || PMODE.equals("News") || PMODE.equals("Help") || PMODE.equals("SiteMap")) {	// 메인에서 링크시
		
		
		menuList = MenuDao.progMenuList(SYSTEMCODE, "Home", USERTYPE, null, null, "Y" );
		
%>
						<table width="153" align="center" cellpadding="0" cellspacing="0">
							<tr> 
								<td>
									<table class="left_menu" align="right">
										<tr> 
											<td height="27"><a href="<%=CONTEXTPATH%><%=myEdaLink%>"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/menu01.gif"></a></td>
										</tr>
										<tr> 
											<td height="27"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/menu02.gif"></td>
										</tr>
<% 
        // 메뉴 코드화....
        for(int i=0; i< menuList.size() ; i++){
            MenuInfo = (ProgMenuDTO)menuList.get(i);
            
            subMenuCnt = MenuInfo.getCntSubMenu() ;
            
            // 서브메뉴가 없을 경우..
            if(subMenuCnt==0){
%>							
										<tr> 
											<td class="left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet.gif" width="11" height="7" align="absmiddle"><a href="<%=CONTEXTPATH%><%=MenuInfo.getMenuUrl() %>&MENUNO=0&pMode=Home&MainMenu=Y" class="l_menu"><%=MenuInfo.getMenuName() %></a></td>
										</tr>
<% 	        }
            // 서브 메뉴가 존재할 시..
            else {
    
            tmpSubCnt = subMenuCnt ;
            menuCnt++;
											
%>
										<tr> 
											<td class="left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon04.gif" width="11" height="7" align="absmiddle"><a href="#" onClick="return toggleMenu('menu<%=menuCnt %>')" class="l_menu"><%=MenuInfo.getMenuName() %></a></td>
										</tr>
										<tr>
											<td class="l_menu_s_pd">
												<!-- 서브메뉴 시작 -->
												<SPAN ID="menu<%=menuCnt %>" style="display:none;">
												<table width="100%">
													<% for(int j=0; j< tmpSubCnt ; j++){
												        i++;
												        MenuInfo = (ProgMenuDTO)menuList.get(i);
												    %>
													<tr>
													    <td width="8%" height="22" align="left">-</td>
														<td width="92%" align="left"><a href="<%=CONTEXTPATH%><%=MenuInfo.getMenuUrl() %>&MENUNO=<%=menuCnt %>&pMode=Home&MainMenu=Y" class="l_menu_s"><%=MenuInfo.getMenuName() %></a>
														</td>
													</tr>
													<%} %>
												</table>
												</SPAN> 
												<!-- 서브메뉴 끝 -->
											</td>
										</tr>

<%            } 
        } 

%>											
										
										<tr valign="top"> 
											<td>
												<table width="100%">
													<tr>
														<td height="10"></td>
													</tr>
													<tr>
														<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/box01_top.gif"></td>
													</tr>
													<tr>
														<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/box01_bg.gif">
															<table width="100%">
																<tr>
																	<td height="2"></td>
																</tr>
																<tr>
																	<td class="login_banner"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon02.gif"> <a href="<%=CONTEXTPATH%>/RecommendSite.cmd?cmd=recommendSiteList&pMode=<%=PMODE %>&MENUNO=0">추천사이트</a></td>
																</tr>
																<tr>
																	<td class="login_banner"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon02.gif"> <a href="http://www.trainingclu.org/" target="_blank">마음수련원</a></td>
																</tr>
																<tr>
																	<td class="login_banner"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon02.gif"> <a href="http://eco.junnodae.org/" target="_blank">문화교육원</a></td>
																</tr>
																<tr>
																	<td class="login_banner"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon02.gif"> <a href="http://dli.nodong.net/" target="_blank">민주노동연구소</a></td>
																</tr>
																<tr>
																	<td height="2"></td>
																</tr>
<form name="urlFrom">
																<tr>
																	<td height="2" class="selectbox01">
																		<select name onChange='if(this.selectedIndex>=0) { this.blur(); window.open(options[selectedIndex].value); }'>
																			<option value="http://www.pressian.com/">프레시안</option>
																			<option value="http://www.nodong.com/">노동의 소리</option>
																			<option value="http://www.samchang.or.kr/">삶이 보이는 창</option>
																			<option value="http://rtv.or.kr/">시민참여방송</option>
																			<option value="http://www.jinbo.net/">진보네트워크</option>
																			<option value="http://migrants.jinbo.net/">이주노동자후원회</option>
																			<option value="http://www.kdlp.org/">민주노동당</option>
																			<option value="http://www.nodong.org/">민주노총</option>
																		</select>
																	</td>
																</tr>
</form>
																<tr>
																	<td height="2"></td>
																</tr>

															</table>
														</td>
													</tr>
													<tr>
														<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/box01_bottom.gif"></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr> 
                                                                                    <!-- <td style="padding:20 0 0 0"><a href="<%=CONTEXTPATH%>/doc/GVA2000_Student.exe"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/down.gif"></a></td> -->
											<td style="padding:20 0 0 0"><a href="http://m.junnodae.org"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/banner_mobile.gif"></a></td>
										</tr>
										<tr> 
											<td style="padding:3 0 0 0"><a href="http://www.junnodae.org/BbsContents.cmd?cmd=bbsContentsShow&pBbsId=17&pMode=News&pSec=&pSeqNo=189&curPage=1" target="_blank"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/banner-st.gif"></a></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>



<%
	}	//end of PMODE.equals("Home")

	else if (PMODE.equals("StudentCenter")) {
%>
									<table class="left_menu" align="right">
										<tr> 
											<td height="27"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_tit04.gif" width="153" height="20"></td>
										</tr>
										<tr> 
											<td class="left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon04.gif" width="11" height="7" align="absmiddle"><a href="#" onClick="return toggleMenu('menu1')" class="l_menu">대학생활안내</a></td>
										</tr>
										<tr>
											<td class="l_menu_s_pd">
												<!-- 서브메뉴 시작 -->
												<SPAN ID="menu1" style="display:none;">
												<table width="100%">
													<tr>
													    <td width="8%" height="22" align="left">-</td>
														<td width="92%" align="left"><a href="<%=CONTEXTPATH%>/Main.cmd?cmd=StudentCenterShow&pMode=StudentCenter&pInfoNum=11&pInfoNum2=1&MENUNO=1"  class="l_menu_s">입학에서 졸업까지</a> 
														</td>
													</tr>
													<tr>
														<td width="8%" height="22" align="left">-</td>
														<td width="92%" align="left"><a href="<%=CONTEXTPATH%>/Main.cmd?cmd=StudentCenterShow&pMode=StudentCenter&pInfoNum2=2&MENUNO=1"  class="l_menu_s">대학생활 및 <br>학습방법</a> 
														</td>
													</tr>
												</table>
												</SPAN> 
												<!-- 서브메뉴 끝 -->
											</td>
										</tr>
										<tr> 
											<td class="left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon04.gif" width="11" height="7" align="absmiddle"><a href="<%=CONTEXTPATH%>/Main.cmd?cmd=StudentCenterShow&pMode=StudentCenter&pInfoNum2=3&pInfoNum3=1&MENUNO=0" class="l_menu">교재안내</a></td>
										</tr>
										<tr> 
											<td class="left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon04.gif" width="11" height="7" align="absmiddle"><a href="<%=CONTEXTPATH%>/Schedule.cmd?cmd=haksaSchedule&pMode=StudentCenter&MENUNO=0" class="l_menu">학사일정</a></td>
										</tr>
										<tr> 
											<td class="left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon04.gif" width="11" height="7" align="absmiddle"><a href="<%=CONTEXTPATH%>/Faq.cmd?cmd=homeFaqList&pMode=StudentCenter&MENUNO=0" class="l_menu">FAQ</a></td>
										</tr>
										<tr> 
											<td height="27"><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=20&MENUNO=0&pMode=<%=PMODE%>"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_tit02.gif" width="153" height="20"></a></td>
										</tr>
										<tr> 
											<td height="27"><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=21&pMode=<%=PMODE%>&MENUNO=0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_tit03.gif" width="153" height="20"></a></td>
										</tr>
										<!-- 동아리 배너 -->
										<tr>
											<td class="left_community"><a href="<%=CONTEXTPATH%>/Main.cmd?cmd=communityShow&pMode=Community&MENUNO=0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/btn_community.gif" width="150" height="52"></a></td>
										</tr>
										<!-- // 동아리 배너 -->
									</table>
<%
	}
	
	if (PMODE.equals("MyPage")) {
	    
	    menuList = MenuDao.progMenuList(SYSTEMCODE, PMODE, USERTYPE, null, null, "Y" );

%>	        
									<table class="left_menu" align="right">
										<tr> 
											<td height="27"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_tit01.gif" width="153" height="20"></td>
										</tr>
										
<% 
        // 메뉴 코드화....
        for(int i=0; i< menuList.size() ; i++){
            MenuInfo = (ProgMenuDTO)menuList.get(i);
            
            subMenuCnt = MenuInfo.getCntSubMenu() ;
            
            // 서브메뉴가 없을 경우..
            if(subMenuCnt==0){
%>							
										<tr> 
											<td class="left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet.gif" width="11" height="7" align="absmiddle"><a href="<%=CONTEXTPATH%><%=MenuInfo.getMenuUrl() %>&MENUNO=0&pMode=<%=PMODE%>" class="l_menu"><%=MenuInfo.getMenuName() %></a></td>
										</tr>
<% 	        }
            // 서브 메뉴가 존재할 시..
            else {
    
            tmpSubCnt = subMenuCnt ;
            menuCnt++;
											
%>
										<tr> 
											<td class="left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon04.gif" width="11" height="7" align="absmiddle"><a href="#" onClick="return toggleMenu('menu<%=menuCnt %>')" class="l_menu"><%=MenuInfo.getMenuName() %></a></td>
										</tr>
										<tr>
											<td class="l_menu_s_pd">
												<!-- 서브메뉴 시작 -->
												<SPAN ID="menu<%=menuCnt %>" style="display:none;">
												<table width="100%">
													<% for(int j=0; j< tmpSubCnt ; j++){
												        i++;
												        MenuInfo = (ProgMenuDTO)menuList.get(i);
												    %>
													<tr> 
														<td width="8%" height="22" align="left">-</td>
														<td width="92%" align="left"><a href="<%=CONTEXTPATH%><%=MenuInfo.getMenuUrl() %>&MENUNO=<%=menuCnt %>&pMode=<%=PMODE%>" class="l_menu_s"><%=MenuInfo.getMenuName() %></a>
														</td>
													</tr>
													<%} %>
												</table>
												</SPAN> 
												<!-- 서브메뉴 끝 -->
											</td>
										</tr>

<%            } 
        } 

%>								
										<tr> 
											<td height="27"><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=20&MENUNO=0&pMode=<%=PMODE%>"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_tit02.gif" width="153" height="20"></a></td>
										</tr>
										<tr> 
											<td height="27"><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=21&pMode=<%=PMODE%>&MENUNO=0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_tit03.gif" width="153" height="20"></a></td>
										</tr>
										<!-- 동아리 배너 -->
										<tr>
											<td class="left_community"><a href="<%=CONTEXTPATH%>/Main.cmd?cmd=communityShow&pMode=Community&MENUNO=0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/btn_community.gif" width="150" height="52"></a></td>
										</tr>
										<!-- // 동아리 배너 -->
									</table>


<%	
	}
	
	
	if(PMODE.equals("Member")) {
	
	} // end of PMODE.equals("Member")
	
	
	else if (PMODE.equals("SiteMap")) {
		
	}
	
	
	
	
	
	// 동아리
	else if(PMODE.equals("Community")){
%>		
							<table width="153" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td class="com_left_form"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_community.gif" width="165" height="34" border="0" alt="동아리 메뉴"></td>
								</tr>
							</table>
							<table width="153" align="center" cellpadding="0" cellspacing="0">
								<tr> 
									<td>
										<table class="com_left_menu" align="right">
											<tr> 
												<td class="com_left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet.gif" width="11" height="7" align="absmiddle"><a href="/Community.cmd?cmd=commInfoList&pMode=Community&MENUNO=0" class="com_l_menu">나의 동아리</a></td>
											</tr>
											<tr>
												<td class="com_left_menulien"></td>
											</tr>
											<tr> 
												<td class="com_left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet.gif" width="11" height="7" align="absmiddle"><a href="/Community.cmd?cmd=commPridebbsPagingList&pBbsId=1&MENUNO=0" class="com_l_menu">동아리 자랑</a></td>
											</tr>
											<tr>
												<td class="com_left_menulien"></td>
											</tr>
											<tr> 
												<td class="com_left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet.gif" width="11" height="7" align="absmiddle"><a href="/Community.cmd?cmd=newCommunity&MENUNO=0" class="com_l_menu">신규 동아리</a></td>
											</tr>
											<tr>
												<td  class="com_left_menulien"></td>
											</tr>
											<tr> 
												<td class="com_left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet.gif" width="11" height="7" align="absmiddle"><a href="/Community.cmd?cmd=recCommunity&MENUNO=0" class="com_l_menu">추천 동아리</a></td>
											</tr>
											<tr>
												<td class="com_left_menulien"></td>
											</tr>
											<tr> 
												<td class="com_left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet.gif" width="11" height="7" align="absmiddle"><a href="/Community.cmd?cmd=makeCommunity&MENUNO=0" class="com_l_menu">동아리 만들기</a></td>
											</tr>
											<tr>
												<td class="com_left_menulien"></td>
											</tr>
<%
		if (USERTYPE.equals("M")) {
%>
											<tr> 
												<td class="com_left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet.gif" width="11" height="7" align="absmiddle"><a href="/CommCategory.cmd?cmd=commCategoryList&pMode=Community&MENUNO=0" class="com_l_menu">동아리 분류관리</a></td>
											</tr>
											<tr>
												<td class="com_left_menulien"></td>
											</tr>
											<tr> 
												<td class="com_left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet.gif" width="11" height="7" align="absmiddle"><a href="/CommManage.cmd?cmd=commInfoPagingList&pMode=Community&MENUNO=0" class="com_l_menu">동아리 관리</a></td>
											</tr>
											<tr>
												<td class="com_left_menulien"></td>
											</tr>
											<tr> 
												<td class="com_left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet.gif" width="11" height="7" align="absmiddle"><a href="/CommManage.cmd?cmd=commInfoNewList&pMode=Community&MENUNO=0" class="com_l_menu">NEW 동아리</a></td>
											</tr>
											<tr>
												<td class="com_left_menulien"></td>
											</tr>
											<tr> 
												<td class="com_left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet.gif" width="11" height="7" align="absmiddle"><a href="/CommManage.cmd?cmd=commInfoClosingList&pMode=Community&pUseYn=C&MENUNO=0" class="com_l_menu">폐쇄신청 동아리</a></td>
											</tr>
											<tr>
												<td class="com_left_menulien"></td>
											</tr>
											<tr> 
												<td class="com_left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet.gif" width="11" height="7" align="absmiddle"><a href="/CommManage.cmd?cmd=commInfoClosingList&pMode=Community&pUseYn=N&MENUNO=0" class="com_l_menu">폐쇄된 동아리</a></td>
											</tr>
											<tr>
												<td class="com_left_menulien"></td>
											</tr>
<%
		}
%>
											<!-- 동아리 나가기 버튼 -->
											<tr>
												<td class="com_left_exit"><a href="<%=myEdaLink%>"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_community_exit.gif" alt="강의실 나가기"></a></td>
											</tr>
											<!-- // 동아리 나가기 버튼 -->
										</table>
									</td>
								</tr>
							</table>
<%		
    }
    // 나의 동아리(sub)
	else if (PMODE.equals("CommSub")) {
		COMMINFO = UserBroker.getCommInfo(request);
		String userLevel = COMMINFO.userLevel;
		ArrayList bbsList = CommonUtil.getCommBbsList(SYSTEMCODE,COMMINFO.commId);
%>
<script language="javascript">
<!--
	function goInviteUser(){
		var winOption = "left="+windowLeftPosition(200)+",top="+windowTopPosition(200)+",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=yes,resizable=no,width=430,height=370";
		var loc = "/CommInvite.cmd?cmd=inviteUserList&MENUNO=0";
		var ShowInfo = window.open(loc,"inviteWindow",winOption);
	}

	function Community_Close(commid){
		if(confirm('동아리가 폐쇄되면 동아리의 모든 정보가 삭제됩니다. \n\n동아리 폐쇄 신청을 하시겠습니까?')){
			hiddenFrame.document.location = "Community.cmd?cmd=commCloseRegist&pCommId="+commid+"&MENUNO=2";
		}
	}
-->
</script>
							<table width="153" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td class="com_left_form"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_community.gif" width="165" height="34" border="0" alt="동아리 메뉴"></td>
								</tr>
							</table>
							<table width="153" align="center" cellpadding="0" cellspacing="0">
								<tr> 
									<td>
										<table class="com_left_menu" align="right">
											<tr> 
												<td class="left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet.gif" width="11" height="7" align="absmiddle"><a href="#" onClick="return toggleMenu('menu1')" class="com_l_menu">
<%	if (MENUNO.equals("1"))
		out.print("<font color=\"#FD6C02\">");	%>
													게시판</a></td>
											</tr>
											<tr>
												<td>
													<!-- 게시판 서브메뉴 시작 -->
													<SPAN ID="menu1" style="display:none;">
													<table class="com_l_menu_s">
<%		String[] bbsInfo = null;

		for(int i=0; i < bbsList.size(); i++){
			bbsInfo = (String[])bbsList.get(i);	%>
														<tr> 
															<td height="22" align="left"><a href="<%=CONTEXTPATH%>/CommSubBoard.cmd?cmd=commSubBoard&pBbsId=<%=bbsInfo[1]%>&MENUNO=1" class="l_menu_s">- <%=bbsInfo[0]%></a>
															</td>
														</tr>
<%		}	%>

													</table>
													</SPAN> 
													<!-- 게시판 서브메뉴 끝 -->
												</td>
											</tr>
											<tr>
												<td class="com_left_menulien"></td>
											</tr>
<%
		if (userLevel.equals("M") || userLevel.equals("A")) {
%>
											<tr> 
												<td class="com_left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet.gif" width="11" height="7" align="absmiddle"><a href="<%=CONTEXTPATH%>/Community.cmd?cmd=commMembersPagingList&MENUNO=0" class="l_menu">회원보기</a></td>
											</tr>
											<tr>
												<td class="com_left_menulien"></td>
											</tr>
											<tr> 
												<td class="com_left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet.gif" width="11" height="7" align="absmiddle"><a href="<%=CONTEXTPATH%>/Community.cmd?cmd=commMemberEdit&MENUNO=0" class="l_menu">개인정보수정</a></td>
											</tr>
											<tr>
												<td  class="com_left_menulien"></td>
											</tr>
<%
		}

		if (userLevel.equals("M")) {
%>
											<tr> 
												<td class="com_left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet.gif" width="11" height="7" align="absmiddle"><a href="javascript:goInviteUser();" class="com_l_menu">초대하기</a></td>
											</tr>
											<tr>
												<td class="com_left_menulien"></td>
											</tr>
											<tr> 
												<td class="com_left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet.gif" width="11" height="7" align="absmiddle"><a href="#" onClick="return toggleMenu('menu2')" class="com_l_menu">
<%			if (MENUNO.equals("2"))
				out.print("<font color=\"#FD6C02\">");	%>
													동아리 관리</a></td>
											</tr>
											<tr>
												<td>
													<!-- 게시판 서브메뉴 시작 -->
													<SPAN ID="menu2" style="display:none;">
													<table class="com_l_menu_s">
														<!-- 동아리관리 서브메뉴 시작 -->
														<tr> 
															<td height="22" align="left"><a href="<%=CONTEXTPATH%>/Community.cmd?cmd=commMembersManagePagingList&MENUNO=2" class="l_menu_s">- 회원관리</a>
															</td>
														</tr>
														<tr> 
															<td height="22" align="left"><a href="<%=CONTEXTPATH%>/Community.cmd?cmd=commBbsManagePagingList&MENUNO=2" class="l_menu_s">- 게시판관리</a>
															</td>
														</tr>
														<tr> 
															<td height="22" align="left"><a href="<%=CONTEXTPATH%>/Community.cmd?cmd=makeCommunity&pWhere=C&pGubun=Edit&pCommId=<%=COMMINFO.commId%>&MENUNO=2" class="l_menu_s">- 동아리 정보관리</a>
															</td>
														</tr>
														<tr> 
															<td height="22" align="left"><a href="javascript:Community_Close('<%=COMMINFO.commId%>');">- 동아리 폐쇄신청</a>
															</td>
														</tr>
													</table>
													</SPAN> 
													<!-- 게시판 서브메뉴 끝 -->
												</td>
											</tr>
											<tr>
												<td class="com_left_menulien"></td>
											</tr>
<%
		}
%>
											<!-- 동아리 나가기 버튼 -->
											<tr>
												<td class="com_left_exit"><a href="Community.cmd?cmd=commInfoList&pMode=Community&MENUNO=0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_community_exit.gif" alt="동아리 나가기"></a></td>
											</tr>
											<!-- // 동아리 나가기 버튼 -->
										</table>
									</td>
								</tr>
							</table>
							<!-- // left 메뉴 -->
<%
	}

	if (!("0").equals(MENUNO) && MENUNO != null && !MENUNO.equals("")) {
%>
<script language="javascript">
<!--
	toggleMenu('menu<%=MENUNO%>');
-->
</script>
<%
	}
%>
