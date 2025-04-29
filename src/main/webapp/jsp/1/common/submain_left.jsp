<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%-- 
         메뉴 주소 걸 시 주의 사항 : 모든 메뉴에서의 링크에 서브메뉴가 없으면 &MENUNO=0를 붙여 주십시요 
         그리고   서브메뉴가 있을 시 상위 메뉴가 menu2라면 &MENUNO=2라고 메뉴 번호를 넣어주시면 감사하겠습니다.
         상위 메뉴는 당연히 &MENUNO=0이겠지요..
--%>

<% if(PMODE.equals("Home")){%>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td height="25"><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=5&pMode=Home&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu00','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/help/help_leftmenu00_on.gif',1)"><img name="leftmenu00" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/help/help_leftmenu00.gif" width="155" height="25"></a></td>
      </tr>
      <tr> 
        <td height="25"><a href="<%=CONTEXTPATH%>/WebSite.cmd?cmd=getWebSiteList&pSiteType=20&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu01','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/help/help_leftmenu01_on.gif',1)"><img name="leftmenu01" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/help/help_leftmenu01.gif" width="155" height="25"></a></td>
      </tr>
	  <tr> 
        <td height="25"><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=1&pMode=Home&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu08','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/help/help_leftmenu08_on.gif',1)"><img name="leftmenu08" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/help/help_leftmenu08.gif" width="155" height="25"></a></td>
      </tr>
      <tr> 
        <td height="25"><a href="<%=CONTEXTPATH%>/Faq.cmd?cmd=homeFaqList&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu02','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/help/help_leftmenu02_on.gif',1)"><img name="leftmenu02" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/help/help_leftmenu02.gif" width="155" height="25"></a></td>
      </tr>
      <tr> 
        <td height="25"><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=3&pMode=Home&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu03','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/help/help_leftmenu03_on.gif',1)"><img name="leftmenu03" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/help/help_leftmenu03.gif" width="155" height="25"></a></td>
      </tr>
      <tr> 
        <td height="25"><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=2&pMode=Home&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu04','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/help/help_leftmenu04_on.gif',1)"><img name="leftmenu04" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/help/help_leftmenu04.gif" width="155" height="25"></a></td>
      </tr>
      <tr> 
        <td height="25"><a href="<%=CONTEXTPATH%>/Poll.cmd?cmd=pollList&pMode=<%=PMODE%>&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu05','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/help/help_leftmenu05_on.gif',1)"><img name="leftmenu05" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/help/help_leftmenu05.gif" width="155" height="25"></a></td>
      </tr>
      <!--<tr> 
        <td height="25"><a href="mailto:test@test.com" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu06','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/help/help_leftmenu06_on.gif',1)"><img name="leftmenu06" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/help/help_leftmenu06.gif" width="155" height="25"></a></td>
      </tr>-->
      <tr> 
        <td height="25"><a href="<%=CONTEXTPATH%>/Common.cmd?cmd=goSiteMap&MENU=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu07','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/help/help_leftmenu07_on.gif',1)"><img name="leftmenu07" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/help/help_leftmenu07.gif" width="155" height="25"></a></td>
      </tr>
    </table>

<%}%>

<% if(PMODE.equals("MyPage") && USERTYPE.equals("S")){%>

                    <!-- left menu 시작 -->
                    <td width="159" valign="top" rowspan="2"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/title/academy.gif" width="147" height="133"><br>
                        <a href="03수강현황.htm" onMouseOver="MM_swapImage('leftmenu01','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/03_leftmenu01_on.gif',1)" onMouseOut=MM_swapImgRestore()><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/03_leftmenu01_off.gif" width="147" height="24" border=0 name=leftmenu01></a><br>
                        <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/left_green02.gif" width="147" height="1"><br>
                        <a href="03결제현황.htm" onMouseOver="MM_swapImage('leftmenu02','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/03_leftmenu02_on.gif',2)" onMouseOut=MM_swapImgRestore()><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/03_leftmenu02_off.gif" width="147" height="24" border=0 name=leftmenu02></a><br>
                        <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/left_green02.gif" width="147" height="1"><br>
                        <!-- 개인 정보  -->   
                        <a href="<%=CONTEXTPATH%>/User.cmd?cmd=userWrite&pGUBUN=edit&MENUNO=0" onMouseOver="MM_swapImage('leftmenu03','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/03_leftmenu03_on.gif',3)" onMouseOut=MM_swapImgRestore()><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/03_leftmenu03_off.gif" width="147" height="24" border=0 name=leftmenu03></a><br>
                        <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/left_green02.gif" width="147" height="1"><br>
                        <!-- 개인 면담  -->   
                        <a href="<%=CONTEXTPATH%>/CurriWorkReqRes.cmd?cmd=workReqResList&pWhere=MyPage&MENUNO=0" onMouseOver="MM_swapImage('leftmenu04','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/03_leftmenu04_on.gif',4)" onMouseOut=MM_swapImgRestore()><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/03_leftmenu04_off.gif" width="147" height="24" border=0 name=leftmenu04></a><br>
                        <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/left_green02.gif" width="147" height="1"><br>
                        <!-- 쪽지함  -->   
                        <a href="<%=CONTEXTPATH%>/Message.cmd?cmd=receiveMessagePagingList&MENUNO=0" onMouseOver="MM_swapImage('leftmenu05','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/03_leftmenu05_on.gif',5)" onMouseOut=MM_swapImgRestore()><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/03_leftmenu05_off.gif" width="147" height="24" border=0 name=leftmenu05></a><br>
                        <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/left_green02.gif" width="147" height="1"><br>
                        <!-- 일정 관리  -->
                        <a href="<%=CONTEXTPATH%>/Schedule.cmd?cmd=scheduleView&MENUNO=0" onMouseOver="MM_swapImage('leftmenu6','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/03_leftmenu06_on.gif',6)" onMouseOut=MM_swapImgRestore()><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/03_leftmenu06_off.gif" width="147" height="24" border=0 name=leftmenu06></a><br>
                        <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/left_green02.gif" width="147" height="1"><br>
                    </td>
                    <!-- left menu 끝 -->

<!--leftmenu start -->
<!--table id='tableMain' width="100%" border="0" cellspacing="0" cellpadding="0">
<tr> 
<td height="25"><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=1&pMode=MyPage&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu01','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu01_on.gif',1)"><img name="leftmenu01" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu01.gif" width="155" height="25"></a></td>
</tr>
<tr> 
<td height="25"><a href="<%=CONTEXTPATH%>/Main.cmd?cmd=stuCurriBeforeList&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu02','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu02_on.gif',1)"><img name="leftmenu02" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu02.gif" width="155" height="25"></a></td>
</tr>
<tr> 
<td height="25"><a href="<%=CONTEXTPATH%>/Main.cmd?cmd=stuCurriList&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu03','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu03_on.gif',1)"><img name="leftmenu03" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu03.gif" width="155" height="25"></a></td>
</tr>
<tr> 
<td height="25"><a href="<%=CONTEXTPATH%>/Main.cmd?cmd=stuCurriHistoryList&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu04','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu04_on.gif',1)"><img name="leftmenu04" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu04.gif" width="155" height="25"></a></td>
</tr-->

   
<%}%>

<%if(PMODE.equals("MyPage") && USERTYPE.equals("C")){%>
	<table id='tableMain' width="176" border="0" cellspacing="0" cellpadding="0">
	    <tr> 
	      <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/top.gif" width="176" height="17"></td>
	    </tr>
	    <tr>
	      <td><a href="<%=CONTEXTPATH%>/User.cmd?cmd=userWrite&pGUBUN=edit&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image54','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/mypage_01_r.gif',1)"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/mypage_01.gif" name="Image54" width="176" height="30" border="0"></a></td>
	    </tr>
	    <tr> 
	      <td><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=1&pMode=MyPage&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image541','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/mypage_02_r.gif',1)"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/mypage_02.gif" name="Image541" width="176" height="30" border="0" id="Image541"></a></td>
	    </tr>
	    <tr> 
	      <td><a href="<%=CONTEXTPATH%>/Schedule.cmd?cmd=scheduleView&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image542','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/mypage_03_r.gif',1)"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/mypage_03.gif" name="Image542" width="176" height="30" border="0" id="Image542"></a></td>
	    </tr>
	    <tr> 
	      <td><a href="<%=CONTEXTPATH%>/Message.cmd?cmd=receiveMessagePagingList&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image543','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/mypage_04_r.gif',1)"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/mypage_04.gif" name="Image543" width="176" height="30" border="0" id="Image543"></a></td>
	    </tr>	    
	    <tr> 
	      <td><a href="<%=CONTEXTPATH%>/Main.cmd?cmd=profmangerCurriList&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image544','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/mypage_10_r.gif',1)"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/mypage_10.gif" name="Image544" width="176" height="30" border="0" id="Image544"></a></td>
	    </tr>
	     <tr> 
	      <td><a href="<%=CONTEXTPATH%>/SelfTestInfo.cmd?cmd=selfTestInfoStList&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image546','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/mypage_07_r.gif',1)"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/mypage_07.gif" name="Image546" width="176" height="30" border="0" id="Image546"></a></td>
	    </tr>
	    <tr> 
	      <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/bottom.gif" width="176" height="80"></td>
	    </tr>
	  </table>

<%}%>

<%if(PMODE.equals("MyPage") && USERTYPE.equals("P")){%>

 <!--leftmenu start -->
     <table id='tableMain' width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td height="25"><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=1&pMode=MyPage&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu01','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu01_on.gif',1)"><img name="leftmenu01" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu01.gif" width="155" height="25"></a></td>
      </tr>
      <tr> 
        <td height="25"><a href="<%=CONTEXTPATH%>/UserAdmin.cmd?cmd=profWrite&pGUBUN=edit&userId=<%=USERID%>&userType=P&pWorker=P&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu08','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu08_on.gif',1)"><img name="leftmenu08" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu08.gif" width="155" height="25"></a></td>
      </tr>
      <tr> 
        <td height="25"><a href="<%=CONTEXTPATH%>/Main.cmd?cmd=profmangerCurriList&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu03','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu10_on.gif',1)"><img name="leftmenu03" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu10.gif" width="155" height="25"></a></td>
      </tr>
      <!-- 개인 면담  -->   
      <tr> 
      	<td><a href="<%=CONTEXTPATH%>/CurriWorkReqRes.cmd?cmd=workReqResList&pWhere=MyPage&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image547','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu26_on.gif',1)"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu26.gif" name="Image547" width="155" height="25" border="0" id="Image547"></a></td>
      </tr>
      <!--<tr> 
        <td height="25"><a href="<%=CONTEXTPATH%>/Community.cmd?cmd=commInfoList&pMode=MyPage&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu07','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu07_on.gif',1)"><img name="leftmenu07" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu07.gif" width="155" height="25"></a></td>
      </tr>
      <tr> 
        <td height="25"><a href="<%=CONTEXTPATH%>/User.cmd?cmd=userWrite&pGUBUN=edit&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu08','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu08_on.gif',1)"><img name="leftmenu08" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu08.gif" width="155" height="25"></a></td>
      </tr>-->
      <tr> 
        <td height="25"><a href="<%=CONTEXTPATH%>/Message.cmd?cmd=receiveMessagePagingList&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu09','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu09_on.gif',1)"><img name="leftmenu09" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu09.gif" width="155" height="25"></a></td>
      </tr>
      <!-- 일정 관리  -->               
         <tr> 
	      <td><a href="<%=CONTEXTPATH%>/Schedule.cmd?cmd=scheduleView&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image542','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/mypage_03_r.gif',1)"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/mypage_03.gif" name="Image542"  width="155" height="25" border="0" id="Image542"></a></td>
	    </tr>
    </table>
  <!--leftmenu end --> 

<%}%>

<% if(PMODE.equals("MyPage") && USERTYPE.equals("M")){%>

           <table id='tableMain' width="100%" border="0" cellspacing="0" cellpadding="0">
		   <!-- 공지사항 --> 
              <tr> 
                <td height="25"><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=1&pMode=MyPage&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu01','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu01_on.gif',1)"><img name="leftmenu01" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu01.gif" width="155" height="25"></a></td>
              </tr>
			<!-- 진행중인 과정 --> 
			  <tr> 
                <td height="25"><a href="<%=CONTEXTPATH%>/CurriSub.cmd?cmd=currentMypageList&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu02','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu12_on.gif',1)"><img name="leftmenu02" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu12.gif" width="155" height="25"></a></td>
              </tr>
			  <!-- 강의종료 과정 --> 
			  <tr> 
                <td height="25"><a href="<%=CONTEXTPATH%>/CurriSub.cmd?cmd=completeMypageList&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu03','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu13_on.gif',1)"><img name="leftmenu03" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu13.gif" width="155" height="25"></a></td>
              </tr>
			  <!-- 사용자 관리 -->
              <tr> 
                <td height="12" align="left" valign="top"><a href="#" onClick="return toggleMenu('menu1')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu04','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu14_on.gif',1)"><img name="leftmenu04" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu14.gif" width="155" height="25"></a></td>
              </tr>
              <tr><td>
               <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#F7F2ED">
               <tr><td>
              <SPAN ID="menu1" style="display:none;">
              <table width=100% border=0 cellspacing=0 cellpadding=0> 
                 <tr><td height=6></td></tr>
                 <tr>
                 <td height=17><a href="<%=CONTEXTPATH%>/UserAdmin.cmd?cmd=userPagingList&userType=S&MENUNO=1" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('menu04_1','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu14_s01_on.gif',1)"><img name="menu04_1" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu14_s01.gif" width="120" height="15"></a></td>
                 </tr>
                 <tr>
                 <td height=17><a href="<%=CONTEXTPATH%>/UserAdmin.cmd?cmd=userPagingList&userType=P&MENUNO=1" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('menu04_2','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu14_s02_on.gif',1)"><img name="menu04_2" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu14_s02.gif" width="120" height="15"></a></td>
                 </tr>
                 <tr>
                 <td height=17><a href="<%=CONTEXTPATH%>/UserAdmin.cmd?cmd=userPagingList&userType=M&MENUNO=1" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('menu04_3','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu14_s03_on.gif',1)"><img name="menu04_3" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu14_s03.gif" width="120" height="15"></a></td>
                 </tr>
                 <tr><td height=6></td></tr>
                </table>
               </span>
              </td></tr></table></td></tr>      
			<!-- 과정/과목 관리  --> 
              <tr> 
                <td height="12" align="left" valign="top"><a href="#" onClick="return toggleMenu('menu2')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu05','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu15_on.gif',1)"><img name="leftmenu05" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu15.gif" width="155" height="25"></a></td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#F7F2ED">
					<tr><td>
					<span id="menu2" style="display:none;">
					<table width=100% border=0 cellspacing=0 cellpadding=0> 
                  		<tr><td height=6></td></tr>
                  		<tr>
                  		<td height=17><a href="<%=CONTEXTPATH%>/CurriCategory.cmd?cmd=curriCategoryList&MENUNO=2" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('menu05_1','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu15_s01_on.gif',1)"><img name="menu05_1" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu15_s01.gif" width="120" height="15"></a></td>
                  		</tr>
                  		<tr>
                  		<td height=17><a href="<%=CONTEXTPATH%>/Course.cmd?cmd=courseList&MENUNO=2" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('menu05_2','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu15_s02_on.gif',1)"><img name="menu05_2" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu15_s02.gif" width="120" height="15"></a></td>
                  		</tr>
                  		<tr>
                  		<td height=17><a href="<%=CONTEXTPATH%>/CurriTop.cmd?cmd=curriTopList&pProperty1=Cyber&MENUNO=2" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('menu05_3','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu15_s03_on.gif',1)"><img name="menu05_3" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu15_s03.gif" width="120" height="15"></a></td>
                  		</tr>
                  		<tr>
                  		<td height=17><a href="<%=CONTEXTPATH%>/CurriSub.cmd?cmd=curriSubList&MENUNO=2" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('menu05_4','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu15_s04_on.gif',1)"><img name="menu05_4" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu15_s04.gif" width="120" height="15"></a></td>
                  		</tr>
                  		<tr><td height=6></td></tr>
                  	</table>
                    </span>
                    </td>
                    </tr>
                  </table>
                </td>
              </tr>
			 <!-- 수강/수료 관리 --> 
              <tr> 
                <td height="12" align="left" valign="top"><a href="#" onClick="return toggleMenu('menu3')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu06','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu16_on.gif',1)"><img name="leftmenu06" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu16.gif" width="155" height="25"></a></td>
              </tr>
              <tr> 
                <td > 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#F7F2ED">
                    <tr><td> 
                    <span id="menu3" style="display:none;"> 
                    <table width=100% border=0 cellspacing=0 cellpadding=0> 
                  		<tr><td height=6></td></tr>
                  		<tr>
                  		<td height=17><a href="<%=CONTEXTPATH%>/Student.cmd?cmd=enrollStudentList&MENUNO=3" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('menu06_1','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu16_s01_on.gif',1)"><img name="menu06_1" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu16_s01.gif" width="120" height="15"></a></td>
                  		</tr>
                  		<tr>
                  		<td height=17><a href="<%=CONTEXTPATH%>/Student.cmd?cmd=confirmStudentList&MENUNO=3" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('menu06_2','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu16_s02_on.gif',1)"><img name="menu06_2" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu16_s02.gif" width="120" height="15"></a></td>
                  		</tr>
                  		<tr>
                  		<td height=17><a href="<%=CONTEXTPATH%>/Student.cmd?cmd=completeCurriList&MENUNO=3" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('menu06_3','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu16_s03_on.gif',1)"><img name="menu06_3" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu16_s03.gif" width="120" height="15"></a></td>
                  		</tr>
                  		<tr><td height=6></td></tr>
                  	</table>
                    </span> 
                    </td>
                    </tr>
                  </table>
                </td>
              </tr>
			 <!-- 시스템 관리 --> 
              <tr> 
                <td height="12" align="left" valign="top"><a href="#" onClick="return toggleMenu('menu4')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu07','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu17_on.gif',1)"><img name="leftmenu07" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu17.gif" width="155" height="25"></a></td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#F7F2ED">
                    <tr> 
                      <td>
						<span id="menu4" style="display:none;">
						<table width=100% border=0 cellspacing=0 cellpadding=0> 
                  			<tr><td height=6></td></tr>
                  			<tr>
                  			<td height=17><a href="<%=CONTEXTPATH%>/DeptDae.cmd?cmd=deptDaeList&MENUNO=4" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('menu07_1','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu17_s01_on.gif',1)"><img name="menu07_1" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu17_s01.gif" width="120" height="15"></a></td>
                  			</tr>
                  			<tr>
                  			<td height=17><a href="<%=CONTEXTPATH%>/CodeDae.cmd?cmd=codeDaeList&MENUNO=4" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('menu07_2','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu17_s02_on.gif',1)"><img name="menu07_2" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu17_s02.gif" width="120" height="15"></a></td>
                  			</tr>
                  			<tr>
                  			<td height=17><a href="<%=CONTEXTPATH%>/ConfigSub.cmd?cmd=configSubList&MENUNO=4" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('menu07_3','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu17_s03_on.gif',1)"><img name="menu07_3" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu17_s03.gif" width="120" height="15"></a></td>
                  			</tr>
                  			<tr><td height=6></td></tr>
                  		</table>
                    	</span> 
                    </td>
                    </tr>
                  </table>
                </td>
              </tr>
			 <!-- 통계 관리 --> 
              <tr> 
                <td height="12" align="left" valign="top"><a href="#" onClick="return toggleMenu('menu5')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu08','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu18_on.gif',1)"><img name="leftmenu08" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu18.gif" width="155" height="25"></a></td>
              </tr>
              <tr><td height=10></td></tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#F7F2ED">
                    <tr> 
                      <td>
						<span id="menu5" style="display:none;"> 
						<table width=100% border=0 cellspacing=0 cellpadding=0> 
                  			<tr><td height=6></td></tr>
                  			<tr>
                  			<td height=17><a href="<%=CONTEXTPATH%>/ConnStatus.cmd?cmd=systemConnAction&selectGb=1&MENUNO=5" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('menu08_1','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu18_s01_on.gif',1)"><img name="menu08_1" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu18_s01.gif" width="120" height="15"></a></td>
                  			</tr>
                  			<tr>
                  			<td height=17><a href="<%=CONTEXTPATH%>/ConnStatus.cmd?cmd=curriConnAction&selectGb=1&MENUNO=5" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('menu08_2','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu18_s02_on.gif',1)"><img name="menu08_2" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu18_s02.gif" width="120" height="15"></a></td>
                  			</tr>
                  			<tr>
                  			<td height=17><a href="<%=CONTEXTPATH%>/ConnStatus.cmd?cmd=courseConnAction&selectGb=1&MENUNO=5" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('menu08_3','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu18_s03_on.gif',1)"><img name="menu08_3" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu18_s03.gif" width="120" height="15"></a></td>
                  			</tr>
                  			<tr>
                  			<td height=17><a href="<%=CONTEXTPATH%>/ConnStatus.cmd?cmd=studentStatusAction&selectGb=1&MENUNO=5" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('menu08_4','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu18_s04_on.gif',1)"><img name="menu08_4" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu18_s04.gif" width="120" height="15"></a></td>
                  			</tr>
                  			<tr><td height=6></td></tr>
                  		</table>
	                    </span>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            <!-- 개인면담 관리 -->
	           <tr> 
			      <td><A HREF="#" onClick="return toggleMenu('menu15')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image544','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu25_on.gif',1)"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu25.gif" name="Image544" width="155" height="25" border="0" id="Image544"></a></td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#F7F2ED">
                    <tr> 
                      <td>
						<SPAN ID="menu15" style="display:none;">
						<table width=100% border=0 cellspacing=0 cellpadding=0> 
                  			<tr><td height=6></td></tr>
                  			<tr>
                  			<td height=17><a href="<%=CONTEXTPATH%>/CurriWorkReqRes.cmd?cmd=workReqResList&pWhere=MyPage&MENUNO=15" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image545','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu25_s01_on.gif',1)"><img name="Image545" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu25_s01.gif" width="120" height="15"></a></td>
                  			</tr>
                  			<tr>
                  			<td height=17><a href="<%=CONTEXTPATH%>/CurriWorkReqRes.cmd?cmd=workReqResStatList&pWhere=MyPage&MENUNO=15" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image546','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu25_s02_on.gif',1)"><img name="Image546" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu25_s02.gif" width="120" height="15"></a></td>
                  			</tr>
                  			<tr><td height=6></td></tr>
                  		</table>
						</SPAN>
			       	  </td>
                    </tr>
                  </table>
                </td>
              </tr>
		   <!-- 게시판 관리  --> 
              <tr> 
                <td height="25"><a href="<%=CONTEXTPATH%>/BbsInfo.cmd?cmd=bbsInfoPagingList&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu09','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu19_on.gif',1)"><img name="leftmenu09" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu19.gif" width="155" height="25"></a></td>
              </tr>
		   <!-- 과정 게시판 관리  --> 
              <tr> 
                <td height="25"><a href="<%=CONTEXTPATH%>/CurriBbsInfo.cmd?cmd=curriBbsInfoPagingList&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu10','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu20_on.gif',1)"><img name="leftmenu10" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu20.gif" width="155" height="25"></a></td>
              </tr>
		   <!-- FAQ  --> 
              <tr> 
                <td height="25"><a href="<%=CONTEXTPATH%>/Faq.cmd?cmd=faqCategoryList&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu11','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu21_on.gif',1)"><img name="leftmenu11" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu21.gif" width="155" height="25"></a></td>
              </tr>
		   <!-- 팝업공지관리  --> 
              <tr> 
                <td height="25"><a href="<%=CONTEXTPATH%>/PopupNotice.cmd?cmd=popupNoticePagingList&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu12','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu22_on.gif',1)"><img name="leftmenu12" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu22.gif" width="155" height="25"></a></td>
              </tr>
            <!-- 온라인투표 관리  --> 
              <tr> 
			      	<td><A href="<%=CONTEXTPATH%>/Poll.cmd?cmd=pollList&pMode=<%=PMODE%>&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image573','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/admin_mypage_13_r.gif',1)"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/admin_mypage_13.gif" name="Image573"  width="155" height="25" border="0" id="Image573"></a></td>
			  </tr>
			<!-- 관련사이트관리  --> 
			  <tr> 
			      	<td><A href="<%=CONTEXTPATH%>/WebSite.cmd?cmd=getWebSiteList&pWorkGb=M&pSiteType=10&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image577','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/ma_leftmenu_02_r.gif',1)"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/ma_leftmenu_02.gif" name="Image577"  width="155" height="25" border="0" id="Image577"></a></td>
			  </tr>
			<!-- What's Up 관리  --> 
			  <tr> 
			      	<td><A href="<%=CONTEXTPATH%>/WebSite.cmd?cmd=getWebSiteList&pWorkGb=M&pSiteType=20&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image578','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/ma_leftmenu_03_r.gif',1)"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/ma_leftmenu_03.gif" name="Image578"  width="155" height="25" border="0" id="Image578"></a></td>
			  </tr>             
		   <!-- 프로그램 권한 관리  --> 
              <tr> 
                <td height="25"><a href="<%=CONTEXTPATH%>/ProgAuthor.cmd?cmd=getProgAuthorList&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu13','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu23_on.gif',1)"><img name="leftmenu13" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu23.gif" width="155" height="25"></a></td>
              </tr>
		   <!-- 개인정보 관리  --> 
              <!--<tr> 
                <td height="25"><a href="<%=CONTEXTPATH%>/User.cmd?cmd=userWrite&pGUBUN=edit&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu14','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu08_on.gif',1)"><img name="leftmenu14" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu08.gif" width="155" height="25"></a></td>
              </tr>-->
		   <!-- 쪽지함   --> 
              <tr> 
                <td height="25"><a href="<%=CONTEXTPATH%>/Message.cmd?cmd=receiveMessagePagingList&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu15','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu09_on.gif',1)"><img name="leftmenu15" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myclass/my_leftmenu09.gif" width="155" height="25"></a></td>
              </tr>
           <!-- 일정 관리  -->               
             <tr> 
		      <td><a href="<%=CONTEXTPATH%>/Schedule.cmd?cmd=scheduleView&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image542','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/mypage_03_r.gif',1)"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/leftmenu/mypage_03.gif" name="Image542"  width="155" height="25" border="0" id="Image542"></a></td>
		    </tr>

         	
            </table>
            <br>
            <br>



<%}%>
<% if(PMODE.equals("Enroll")){%>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td height="25"><a href="<%=CONTEXTPATH%>/Common.cmd?cmd=goLectureInfo&MENU=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu01','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/courses_leftmenu01_on.gif',1)"><img name="leftmenu01" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/courses_leftmenu01.gif" width="155" height="25"></a></td>
      </tr>
      <tr> 
        <td height="25"><a href="<%=CONTEXTPATH%>/CurriSub.cmd?cmd=currentList&pMode=Enroll&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu02','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/courses_leftmenu02_on.gif',1)"><img name="leftmenu02" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/courses_leftmenu02.gif" width="155" height="25"></a></td>
      </tr>
      <tr> 
        <td height="25"><a href="<%=CONTEXTPATH%>/WebSite.cmd?cmd=getWebSiteList&pSiteType=10&MENU=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu03','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/courses_leftmenu03_on.gif',1)"><img name="leftmenu03" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/courses_leftmenu03.gif" width="155" height="25"></a></td>
      </tr>
      <tr> 
        <td height="25"><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=4&pMode=Enroll&MENUNO=0" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu04','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/courses_leftmenu04_on.gif',1)"><img name="leftmenu04" border="0" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/courses_leftmenu04.gif" width="155" height="25"></a></td>
      </tr>
    </table>
    <br>
    <br>

<%}%>

<% if(PMODE.equals("Info")) { %>
    <table id='tableMain' width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td height="25"><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu01','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/courses_leftmenu01_on.gif',1)">주요업무</a></td>
      </tr>
      <tr> 
        <td height="25"><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu02','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/courses_leftmenu02_on.gif',1)">목적과 역할</a></td>
      </tr>
      <tr> 
        <td height="25"><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('leftmenu03','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/courses_leftmenu03_on.gif',1)">찾아오시는 길</a></td>
      </tr>
    </table>
    <br>
    <br>

<%	}  %>
 
<% if(MENUNO != null && !MENUNO.equals("")){ %>
	<script language="javascript">
	toggleMenu('menu<%=MENUNO%>');
	</script>
<% } %>

<!--
<script language=javascript>
	function qsearchSubmit(form) {
		form.keyWord2.value = form.keyWord1.value;
	}
</script> 	
	<form name="qsearchForm" method="post" action="<%=CONTEXTPATH%>/CurriSub.cmd?cmd=currentSearchList" onSubmit="return qsearchSubmit(this)">
			<input type="hidden" name="key1" value="findWord">
			<input type="hidden" name="key2" value="title">
			<input type="hidden" name="keyWord2">
			<input type="hidden" name="keyFlag1" value="and">
			<input type="hidden" name="keyFlag2" value="or"> 
	<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/main/main_cen_search01.gif" width="103" height="22">
	<table width="100%" border="0" cellspacing="1" bgcolor="#DCD7C4" cellpadding="0">
		<tr>	
			<td bgcolor="#F6F3E8">
				<table width="100%" border="0" cellspacing="1" cellpadding="0">
					<tr><td height=6></td></tr>
					<tr>
						<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/main/main_cen_search02.gif" width="49" height="14" hspace="5"><input type="text" name="keyWord1" size="13"></td>
					</tr>
					<tr>
						<td height=3></td>
					</tr>
					<tr>
						<td align=center><input type="image" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/main/main_cen_search03.gif" width="129" height="19" hspace="3" border=0 class='no'></td>
					</tr>
					<tr><td height=6></td></tr>
				</table>
			</td>
		</tr>
	</table>
	</form>
-->