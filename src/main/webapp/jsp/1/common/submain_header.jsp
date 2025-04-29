<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map,java.util.ArrayList"%> 
<%@ page import ="com.edutrack.common.UserBroker,com.edutrack.framework.util.DateTimeUtil,com.edutrack.framework.util.StringUtil"%>
<%@ page import ="com.edutrack.common.CommonUtil,com.edutrack.common.SiteNavigation"%> 
<%@ page import ="com.edutrack.message.dao.MessageDAO,com.edutrack.common.dto.CommMemDTO"%> 
<% String CONTEXTPATH = request.getContextPath(); %>
<%
	Map model = (Map)request.getAttribute("MODEL");
	String PMODE = "";
	String DATEYN = "";
	String MENUNO = "";
	if(model != null) {
		PMODE = (String)model.get("pMode");
		DATEYN = (String)model.get("dateyn");
		MENUNO = (String)model.get("MENUNO");
	}

	String SYSTEMCODE = UserBroker.getSystemCode(request);
	String USERID = UserBroker.getUserId(request);
	String USERTYPE = UserBroker.getUserType(request);
	String BIZEDUAUTH = "B";
	String TopDefaultMap = USERID.equals("") ? "default.gif" : "default_r.gif";

	String 	TitleImg	=	"";
	String	TitleBg		=	"";
	String	TitleBg2	=	"";
	String	LineBgColor	=	"";

	if(PMODE.equals("Home")) {		
		TitleImg = ""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/help/help_title.swf";
		TitleBg	= ""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/help/help_topimg02.gif";
		TitleBg2 = ""+CONTEXTPATH+"/img"+SYSTEMCODE+"/help/help_topimg01.gif"; 
		LineBgColor = "#A9BBD0";
	}else if(PMODE.equals("Info")) {		
		TitleImg = "image_info.gif";
		TitleBg	= "img_bg_info.gif";
		LineBgColor = "#3EB5B7";
	}else if(PMODE.equals("Enroll")){
		TitleImg = ""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/courses/courses.swf";
		TitleBg	= ""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/courses/courses_topimg02.gif";
		TitleBg2 = ""+CONTEXTPATH+"/img"+SYSTEMCODE+"/courses/courses_topimg01.gif"; 
		LineBgColor = "#A5A1D7";
	}else if(PMODE.equals("MyPage")){
		TitleImg = ""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/myclass/myclass_title.swf";
		TitleBg	= ""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/myclass/my_topimg02.gif";
		TitleBg2	=""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/myclass/my_topimg01.gif";
		LineBgColor = "#E7EFF3";
	}else if (PMODE.equals("Community") || PMODE.equals("CommSub")) {
		TitleImg = ""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/community/community.swf";
		TitleBg	= ""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/community/comm_topimg02.gif";
		TitleBg2	=""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/community/comm_topimg01.gif";
		LineBgColor = "#CEC7E3";
	} else if(PMODE.equals("Know")){
		TitleImg = "image_know.gif";
		TitleBg	= "img_bg_know.gif";
		LineBgColor = "#74B54F";
	}else if (PMODE.equals("Member")) { 
		TitleImg = "image_question.gif";
		TitleBg	= "img_bg_question.gif";
		LineBgColor = "#E5E5E5";

	}else{
		TitleImg = "image_question.gif";
		TitleBg	= "img_bg_question.gif";
		LineBgColor = "#85AB30";
	}
	int UnReadCnt = 0;

	String MyAcademyLink = "javascript:alert('로그인 후 접근 하실 수 있습니다.');document.location.href='"+CONTEXTPATH+"/User.cmd?cmd=usersLoginShow&pMode=MEMBER';";

	if (!USERID.equals("")) {
		MessageDAO messageDao = new MessageDAO();		
		UnReadCnt	=	StringUtil.nvl( String.valueOf(messageDao.getUnReadCnt(SYSTEMCODE,USERID)), 0);
	}
%>
<html>
<head>
<TITLE>한국디자인진흥원 교육센터 : 국가 디자인 교육 인프라, e디자인아카데미</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="<%=CONTEXTPATH%>/css/style01.css">
<link href="<%=CONTEXTPATH%>/css/date_select1.css"" rel="stylesheet" type="text/css">
<Script Language="JavaScript" src = "<%=CONTEXTPATH%>/js/common1.js"></script>
<SCRIPT language=javascript src="<%=CONTEXTPATH%>/js/objEmbed.js"></script>
	<script language="javascript">
	var oldMenu1;
	var oldMenu2;
	function toggleMenu(currMenu) {
		if (document.all) {
			thisMenu = eval("document.all." + currMenu + ".style")
			if (thisMenu.display == "block") {
				thisMenu.display = "none";
			} else {
				thisMenu.display = "block";
				if(currMenu.substring(0,4)=='menu') {
					if(oldMenu1!=null && oldMenu1!=thisMenu) 
						oldMenu1.display = "none";
					oldMenu1 = thisMenu;
				} else if(currMenu.substring(0,5)=='smenu') {
					if(oldMenu2!=null && oldMenu2!=thisMenu) 
						oldMenu2.display = "none";
					oldMenu2 = thisMenu;
				}
			}
			return false
		} else {
			return true
		}
	}
	function mouse(){
	    if(event.button==2 || event.button==3) return false;
    } 
	
	<!--
		function mouseover_img(obj, bool, layer)
		{
			if(bool){
				obj.src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/layout/" + obj.id + "_r.gif";
				layer.style.visibility='visible';
			}
			else{
				obj.src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/layout/" + obj.id + ".gif";
				layer.style.visibility='hidden';
			}
		}
		
		function layer_menu_over(layer){
			layer.style.visibility='visible'
		}
		
		function layer_menu_out(layer){
			layer.style.visibility='hidden'
		}
		
		function MM_swapImgRestore() { //v3.0
		  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
		}
		
		function MM_preloadImages() { //v3.0
		  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
		    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
		    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
		}
		
		function MM_findObj(n, d) { //v4.01
		  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
		    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
		  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
		  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
		  if(!x && d.getElementById) x=d.getElementById(n); return x;
		}
		
		function MM_swapImage() { //v3.0
		  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
		   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
		}
		
		function MM_reloadPage(init) {  //reloads the window if Nav4 resized
		  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
		    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
		  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
		}
		MM_reloadPage(true);		
	//-->
</script>
</head>
<body>

<a href="/Main.cmd?cmd=infoShow&pMode=Info">eda소개</a>
&nbsp;
<a href="/CurriSub.cmd?cmd=currentListCate&pMode=Enroll&MENUNO=0">과정안내/신청</a>
&nbsp;
<a href="/CurriSub.cmd?cmd=currentMypageList&MENUNO=0">나의 eda</a>
&nbsp;
<a href="/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=1&pMode=Help&pSec=normal&MENUNO=0">고객지원</a>
&nbsp;
<a href="/Main.cmd?cmd=communityShow&pMode=Community&MENUNO=0">동아리</a>

<!-- 탑시작 -->
<%
// 달력 Layer 

	if (DATEYN != null && (DATEYN.equals("Y") || DATEYN.equals("y"))) {
%>
<div id="CalendarLayer" style="display:none; width:172px; height:176px; ">
    <iframe name="CalendarFrame" src = "<%=CONTEXTPATH%>/html/lib.calendar.js.html" width="172" height="176" border="0" frameborder="0" scrolling="no"></iframe>
</div>
<%
	} // end if
%>
  <!------------top navigation ----------->
<table width="100%" height="106" border="0" cellpadding="0" cellspacing="0" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/top_bg01.gif">
	<tr> 
	  <td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/top_bg02.gif">&nbsp;</td>
	  <td width="972" align="left" valign="top">
	    <table width="972" height="41" border="0" cellpadding="0" cellspacing="0">
<form name="LoginTop" method="post" action="/Main.cmd?cmd=setLogin" onsubmit="return form_check();">
		  <tr> 
			<td width="185" height="41" align="right" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/top_bg02.gif" >&nbsp; 
			</td>
			<td width="787" height="41" align="right" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/top_bg01.gif" style="padding:0 5 0 0"> 



<%
	/**  if(로긴을 했을경우)
	**  else(로긴을 안했을 경우)
	*/
	if (!USERID.equals("")) {
%>          
<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="5" height="5" align="absmiddle"><font style="font-size:8pt ; color:#FB6F12"><%= UserBroker.getUserName(request)%>님 
로그인</font> 
<a href="<%=CONTEXTPATH%>/Main.cmd?cmd=userLogOff"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/top_login04.gif" align="absmiddle" border="0"></a><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="10" height="5" align="absmiddle"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/top_m01.gif" width="40" height="9" align="absmiddle"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/top_m02.gif" width="92" height="9" align="absmiddle">
<%
	}
	else {
%>
<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/top_login01.gif" border="0" align="absmiddle"> 
<input name="userId" type="text" title="아이디" tabindex="1" size="15" style="border-width:1px; border-color:rgb(204,204,204); border-style:solid;">
<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" border="0" align="absmiddle"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/top_login02.gif" border="0" align="absmiddle"> 
<input name="userPw" type="password" title="비밀번호" tabindex="1" size="15" style="border-width:1px; border-color:rgb(204,204,204); border-style:solid;">
<input type="image" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/top_login03.gif" onFocus="this.blur()" border="0" alt="로그인" align="absmiddle" tabindex="3">
<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" border="0" align="absmiddle"><a
href="/User.cmd?cmd=userAgreeShow&pMode=Home"><img
src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/top_m01.gif" border="0" align="absmiddle"></a><a
href="/User.cmd?cmd=searchIdPwShow"><img
src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/top_m02.gif" border="0" align="absmiddle"></a>
<%
	} // end if

	String paymentUrl = null;
	if (USERID.equals("")) {
		paymentUrl = MyAcademyLink;
	}
	else {
		paymentUrl = CONTEXTPATH+"/Payment.cmd?cmd=wishlistShow&pMode=Ebook";
	}
%> 

	  </td>
</form>
		  </tr>
		  <tr> 
			<td width="185" height="65" align="left" valign="top" bgcolor="#FFFFFF" > 
<SCRIPT LANGUAGE="JavaScript">
<!--
KIDP_Flash1 = new setEmbed();
KIDP_Flash1.init("flash" , "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/logo_top.swf", "", "", "185", "65");
KIDP_Flash1.setHeaderAdd('hspace','0'); 
KIDP_Flash1.setHeaderAdd('vspace','0'); 
KIDP_Flash1.setParameter('wmode','transparent'); 
KIDP_Flash1.show(); 
//-->
</SCRIPT>
			</td>
			<td align="left" valign="top" ><table width="787" border="0" cellspacing="0" cellpadding="0">
				<tr> 
				  <td width="7" align="right" valign="top" bgcolor="#FFFFFF"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/top_bg03.gif" width="7" height="65"></td>
				  <td width="780" align="right" valign="top">
<SCRIPT LANGUAGE="JavaScript">
KIDP_Flash1 = new setEmbed();
KIDP_Flash1.init("flash" , "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/menu.swf", "", "", "780", "65");
KIDP_Flash1.setHeaderAdd('hspace','0'); 
KIDP_Flash1.setHeaderAdd('vspace','0'); 
KIDP_Flash1.setParameter('wmode','transparent'); 
KIDP_Flash1.show(); 
</SCRIPT>
				  </td>
				</tr>
			  </table></td>
		  </tr>
		</table></td>
	  <td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/top_bg01.gif">&nbsp;</td>
	</tr>
</table>
<!------------ //top navigation ----------->
<table width="1000" border="0" cellpadding="0" cellspacing="0" align="center">
	<tr> 
		<td width="170"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bar02.gif" width="6" height="36" align="absmiddle"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="3" height="1"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bar.gif" width="4" height="16" align="absmiddle"> 
<%
	if (!USERID.equals("")) {

%> 
<font color="#FD6C02"><%= UserBroker.getUserName(request)%></font> 님 
<a href="<%=CONTEXTPATH%>/Message.cmd?cmd=receiveMessagePagingList&MENUNO=0"> 쪽지<font color="#FD6C02"><%=UnReadCnt%></font> 통</a></font>
<%
	}
	else {
		out.println("손님 반갑습니다.");
	} // end if
%><br>
		</td>
		<td bgcolor="#FEFEF6"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bar.gif" width="4" height="16" align="absmiddle"> 
<!-- 페이지 navigation --> 

<!-- 페이지 navigation 끝 -->
		</td>
	</tr>
</table>
<table width="1000" border="0" cellspacing="0" cellpadding="0" align="center">
	<tr> 
    	<td valign="top" align="center"> 

			<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
				<tr> 
					<!-- left menu 시작 -->
					<%@include file="submain_left.jsp" %>
					<!-- left menu 끝 -->
					<!-- main 시작 -->




