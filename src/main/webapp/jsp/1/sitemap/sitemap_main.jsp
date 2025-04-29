<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp" %>

<%

%>
									<table width="660" align="center">
<%	//로그인 전의 사이트 맵
	if (USERID.equals("") || USERID.equals("null")) {	%>
										<!-- 컨텐츠 내용 -->
										<tr valign="top" align="center">
											<!-- 열린마당 -->
											<td width="170">
												<table width="170" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td height="35" valign="top"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/sitmap_tit08.gif"></td>
													</tr>
												</table>
												<table width="170" border="0" cellspacing="0" cellpadding="0">
<%
		menuList = MenuDao.progMenuList(SYSTEMCODE, "Home", USERTYPE, null, null, "Y" );
 
        // 메뉴 코드화....
        for(int i=0; i< menuList.size() ; i++){
            MenuInfo = (ProgMenuDTO)menuList.get(i);
            
            subMenuCnt = MenuInfo.getCntSubMenu() ;
            
            // 서브메뉴가 없을 경우..
            if(subMenuCnt==0){
%>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td width="150" style="padding-top:7px; padding-bottom:4px"><a href="<%=CONTEXTPATH%><%=MenuInfo.getMenuUrl() %>&MENUNO=0&pMode=Home&MainMenu=Y"><b><%=MenuInfo.getMenuName() %></b></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
<% 	        }
            // 서브 메뉴가 존재할 시..
            else {
    
	            tmpSubCnt = subMenuCnt ;
	            menuCnt++;
%>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon02.gif" style="margin-top:11px; margin-left:8px">&nbsp;&nbsp;</td>
														<td style="padding-top:7px; padding-bottom:4px">&nbsp;<b><%=MenuInfo.getMenuName() %></b></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
<% 
				for(int j=0; j< tmpSubCnt ; j++){
			        i++;
			        MenuInfo = (ProgMenuDTO)menuList.get(i);
%>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="<%=CONTEXTPATH%><%=MenuInfo.getMenuUrl() %>&MENUNO=<%=menuCnt %>&pMode=Home&MainMenu=Y">- <%=MenuInfo.getMenuName() %></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
<%				} //end for
			} // end of
		} //end for
%>
												</table>
											</td>
											<td width="30"></td>
											<!-- 대학소개 -->
											<td width="170">
												<table width="170" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td height="35" valign="top"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/sitmap_tit01.gif"></td>
													</tr>
												</table>
												<table width="170" border="0" cellspacing="0" cellpadding="0">
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td width="150" style="padding-top:7px; padding-bottom:4px"><a href="/Main.cmd?cmd=infoShow&pMode=Info&pInfoNum=1">인사말</a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="/Main.cmd?cmd=infoShow&pMode=Info&pInfoNum=2">설립취지 &middot; 연혁</a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="/Main.cmd?cmd=infoShow&pMode=Info&pInfoNum=3">교육내용 &middot; 과정</a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="/Main.cmd?cmd=infoShow&pMode=Info&pInfoNum=4">교수진 소개</a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="/Main.cmd?cmd=infoShow&pMode=Info&pInfoNum=5">대학의 조직과 기구</a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="/Main.cmd?cmd=infoShow&pMode=Info&pInfoNum=6">찾아오는 길</a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr><td colspan="2" height="30"></td></tr>
													
													<!-- 학교소식 -->
													<tr>
														<td colspan="2" height="35" valign="top"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/sitmap_tit02.gif"></td>
													</tr>												
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="/Main.cmd?cmd=enterShow&pMode=Enter&pInfoNum=1">입학안내</a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="/Main.cmd?cmd=enterShow&pMode=Enter&pInfoNum=2">수강안내</a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="/Schedule.cmd?cmd=haksaSchedule&MENUNO=0&pMode=Enter">학사일정</a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>												
													<tr><td colspan="2" height="30"></td></tr>
													
													<!-- 입 학 -->
													<tr>
														<td colspan="2" height="35" valign="top"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/sitmap_tit03.gif"></td>
													</tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=17&pMode=News&MENUNO=0&pInfoNum=1">학사공지</a></td>
													</tr>		
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=18&pMode=News&MENUNO=0&pInfoNum=1">일반공지</a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=19&pMode=News&MENUNO=0&pInfoNum=1">소식</a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>	
												</table>
											</td>
											<td width="30"></td>
											<td width="170">
												<!-- 이용안내 -->
												<table width="170" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="35" valign="top"><a href="/Main.cmd?cmd=infoShow&pMode=Help&pInfoNum=0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/sitmap_tit04.gif"></a></td>
												</tr>
												</table>
											</td>
											<td width="20"></td>
										</tr>
										<!-- // 컨텐츠 내용 -->
<%	}
	//관리자일 경우 사이트 맵
	else if(!USERID.equals("")) {
	    menuList = MenuDao.progMenuList(SYSTEMCODE, "MyPage", USERTYPE, null, null, "Y" );
%>
										<!-- 컨텐츠 내용 -->
										<tr valign="top" align="center">
											<!-- 나의학습실 -->
											<td width="170">
												<table width="170" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td height="35" valign="top"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/sitmap_tit06.gif"></td>
													</tr>
												</table>
												<table width="170" border="0" cellspacing="0" cellpadding="0">
<% 
        // 메뉴 코드화....
        for(int i=0; i< menuList.size() ; i++){
            MenuInfo = (ProgMenuDTO)menuList.get(i);
            
            subMenuCnt = MenuInfo.getCntSubMenu() ;
            
            // 서브메뉴가 없을 경우..
            if(subMenuCnt==0){
%>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td width="150" style="padding-top:7px; padding-bottom:4px"><a href="<%=CONTEXTPATH%><%=MenuInfo.getMenuUrl() %>&MENUNO=0&pMode=MyPage"><b><%=MenuInfo.getMenuName() %></b></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
<% 	        }
            // 서브 메뉴가 존재할 시..
            else {
    
	            tmpSubCnt = subMenuCnt ;
	            menuCnt++;
%>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon02.gif" style="margin-top:11px; margin-left:8px">&nbsp;&nbsp;</td>
														<td style="padding-top:7px; padding-bottom:4px"><b><%=MenuInfo.getMenuName() %></b></td>
													</tr>
<% 
				for(int j=0; j< tmpSubCnt ; j++){
   					i++;
    				MenuInfo = (ProgMenuDTO)menuList.get(i);
%>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="<%=CONTEXTPATH%><%=MenuInfo.getMenuUrl() %>&MENUNO=<%=menuCnt %>&pMode=MyPage">- <%=MenuInfo.getMenuName() %></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
<%				} //end for
			} // end of
		} //end for
%>
												</table>
											</td>
											<td width="30"></td>
											<td width="170">
												<!-- 학생지원센터 -->
												<table width="170" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td height="35" valign="top"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/sitmap_tit05.gif"></td>
													</tr>
												</table>
												<table width="170" border="0" cellspacing="0" cellpadding="0">
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon02.gif" style="margin-top:11px; margin-left:8px">&nbsp;&nbsp;</td>
														<td width="150" style="padding-top:7px; padding-bottom:4px"><b>대학생활안내</b></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="<%=CONTEXTPATH%>/Main.cmd?cmd=StudentCenterShow&pMode=StudentCenter&pInfoNum=11&pInfoNum2=1&MENUNO=1">- 입학에서 졸업까지</a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="<%=CONTEXTPATH%>/Main.cmd?cmd=StudentCenterShow&pMode=StudentCenter&pInfoNum2=2&MENUNO=1">- 대학생활 및 학습방법</a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="<%=CONTEXTPATH%>/Main.cmd?cmd=StudentCenterShow&pMode=StudentCenter&pInfoNum2=3&pInfoNum3=1&MENUNO=0"><b>교재안내</b></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="<%=CONTEXTPATH%>/Schedule.cmd?cmd=haksaSchedule&pMode=StudentCenter&MENUNO=0"><b>학사일정</b></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="<%=CONTEXTPATH%>/Faq.cmd?cmd=homeFaqList&pMode=StudentCenter&MENUNO=0"><b>FAQ</b></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr><td colspan="2" height="30"></td></tr>
											<!-- 동아리 -->
													<tr>
														<td colspan="2" height="35" valign="top"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/sitmap_tit07.gif"></td>
													</tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td width="150" style="padding-top:7px; padding-bottom:4px"><a href="/Community.cmd?cmd=commInfoList&pMode=Community&MENUNO=0"><b>나의 동아리</b></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td width="150" style="padding-top:7px; padding-bottom:4px"><a href="/Community.cmd?cmd=commPridebbsPagingList&pBbsId=1&MENUNO="><b>동아리 자랑</b></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td width="150" style="padding-top:7px; padding-bottom:4px"><a href="/Community.cmd?cmd=newCommunity&MENUNO=0"><b>신규 동아리</b></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td width="150" style="padding-top:7px; padding-bottom:4px"><a href="/Community.cmd?cmd=recCommunity&MENUNO=0"><b>추천 동아리</b></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td width="150" style="padding-top:7px; padding-bottom:4px"><a href="/Community.cmd?cmd=makeCommunity&MENUNO=0"><b>동아리 만들기</b></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
<%
		if (USERTYPE.equals("M")) {
%>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td width="150" style="padding-top:7px; padding-bottom:4px"><a href="/CommCategory.cmd?cmd=commCategoryList&pMode=Community&MENUNO="><b>동아리 분류관리</b></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td width="150" style="padding-top:7px; padding-bottom:4px"><a href="/CommManage.cmd?cmd=commInfoPagingList&pMode=Community&MENUNO=0"><b>동아리 관리</b></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td width="150" style="padding-top:7px; padding-bottom:4px"><a href="/CommManage.cmd?cmd=commInfoNewList&pMode=Community&MENUNO=0"><b>NEW 동아리</b></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td width="150" style="padding-top:7px; padding-bottom:4px"><a href="/CommManage.cmd?cmd=commInfoClosingList&pMode=Community&pUseYn=C&MENUNO=0"><b>폐쇄신청 동아리</b></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td width="150" style="padding-top:7px; padding-bottom:4px"><a href="/CommManage.cmd?cmd=commInfoClosingList&pMode=Community&pUseYn=N&MENUNO=0"><b>폐쇄된 동아리</b></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
<%
		}
%>
												</table>	
											</td>
											<td width="30"></td>
											<td width="170">
												<table width="170" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td height="35" valign="top"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/sitmap_tit08.gif"></td>
													</tr>
												</table>
												<table width="170" border="0" cellspacing="0" cellpadding="0">
<%
		menuList = MenuDao.progMenuList(SYSTEMCODE, "Home", USERTYPE, null, null, "Y" );
 
        // 메뉴 코드화....
        for(int i=0; i< menuList.size() ; i++){
            MenuInfo = (ProgMenuDTO)menuList.get(i);
            
            subMenuCnt = MenuInfo.getCntSubMenu() ;
            
            // 서브메뉴가 없을 경우..
            if(subMenuCnt==0){
%>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td width="150" style="padding-top:7px; padding-bottom:4px"><a href="<%=CONTEXTPATH%><%=MenuInfo.getMenuUrl() %>&MENUNO=0&pMode=Home&MainMenu=Y"><b><%=MenuInfo.getMenuName() %></b></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
<% 	        }
            // 서브 메뉴가 존재할 시..
            else {
    
	            tmpSubCnt = subMenuCnt ;
	            menuCnt++;
%>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon02.gif" style="margin-top:11px; margin-left:8px">&nbsp;&nbsp;</td>
														<td style="padding-top:7px; padding-bottom:4px">&nbsp;<b><%=MenuInfo.getMenuName() %></b></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
<% 
				for(int j=0; j< tmpSubCnt ; j++){
			        i++;
			        MenuInfo = (ProgMenuDTO)menuList.get(i);
%>
													<tr valign="top">
														<td width="18"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" style="margin-top:11px; margin-left:8px"></td>
														<td style="padding-top:7px; padding-bottom:4px"><a href="<%=CONTEXTPATH%><%=MenuInfo.getMenuUrl() %>&MENUNO=<%=menuCnt %>&pMode=Home&MainMenu=Y">- <%=MenuInfo.getMenuName() %></a></td>
													</tr>
													<tr><td colspan="2" height="1" bgcolor="#DDDDDD"></td></tr>
<%				} //end for
			} // end of
		} //end for
%>
												</table>
											</td>
											</td>
											<td width="30"></td>
<%	}	%>
									</table>
								</td>
							</tr>
						</table>
						<!-- 내용 끝 -->



<%@include file="../common/footer.jsp" %>