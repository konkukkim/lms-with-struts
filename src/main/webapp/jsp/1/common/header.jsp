<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
body {
  font-family: Arial, Helvetica, sans-serif;
}

.navbar {
  overflow: hidden;
  background-color: #AC001C;
  border-radius: 15px;
  /*align-items: center;*/
  text-align: center;
  /* justify-content: center; */
}

.navbar a {
  float: left;
  font-size: 16px;
  color: white;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}

.dropdown {
  float: left;
  overflow: hidden;
}

.dropdown .dropbtn {
  font-size: 16px;  
  border: none;
  outline: none;
  color: white;
  padding: 14px 16px;
  /* background-color: inherit; */
  background-color: #AC001C;
  font-family: inherit;
  margin: 0;
}

.navbar a:hover, .dropdown:hover .dropbtn {
  background-color: red;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
}

.dropdown-content a {
  float: none;
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
  text-align: left;
}

.dropdown-content a:hover {
  background-color: #ddd;
}

.dropdown:hover .dropdown-content {
  display: block;
}

a.onemenu:link {color:white; text-decoration:none;} 
a.onemenu:active {color:white; text-decoration:none;} 
a.onemenu:visited {color:white; text-decoration:none;} 
a.onemenu:hover {color:#white; /*text-decoration:underline;*/}
</style>
<%@ page import ="com.edutrack.onlinequiz.dto.OnlineQuizDTO,com.edutrack.poll.dto.InternetPollDTO, com.edutrack.popupnotice.dto.PopupNoticeDTO"  %>
<%@ page import ="java.lang.*,java.util.Map,java.util.ArrayList"%>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@ page import ="com.edutrack.common.UserBroker,com.edutrack.framework.util.DateTimeUtil,com.edutrack.framework.util.StringUtil"%>
<%@ page import ="com.edutrack.common.CommonUtil,com.edutrack.common.SiteNavigation,com.edutrack.message.dao.MessageDAO,com.edutrack.common.dto.CommMemDTO"%>
<%@ page import ="com.edutrack.curritop.dto.CurriTopDTO,com.edutrack.common.dto.CodeParam,com.edutrack.common.dto.EditorParam"%>
<%@ page import ="com.edutrack.currisub.dao.CurriSubDAO, com.edutrack.currisub.dto.CurriSubDTO"%>
<%@ page import = "com.edutrack.curritop.dao.CurriCategoryDAO, com.edutrack.curritop.dto.CurriCategoryTotalDTO" %>
<%@ page import = "com.edutrack.curridate.action.CurriDateAction"%>
<% String CONTEXTPATH = request.getContextPath(); %>
<%
	CommMemDTO COMMINFO = null;
	Map model = (Map)request.getAttribute("MODEL");

	String PMODE = "";
	String DATEYN = "";
	String MENUNO = "";

	if(model != null) {
		PMODE  = (String)model.get("pMode");
		DATEYN = (String)model.get("dateyn");
		MENUNO = (String)model.get("MENUNO");
	}

	String SYSTEMCODE = UserBroker.getSystemCode(request);
	String USERID     = UserBroker.getUserId(request);
	String USERTYPE   = UserBroker.getUserType(request);
	String SCHOOLYEAR = UserBroker.getSchoolYear(request);

	//로그인 하지 않은 메인
	int 	UnReadCnt 	= 	0;
	String 	popup_link 	=	"";
	int 	popup_cnt 	= 	0;

	ArrayList juoList = (ArrayList)model.get("popupnoticeList");
	PopupNoticeDTO popNotice = null;
		
	if(juoList != null) {
		popup_cnt	=	juoList.size();
		for(int i=0; i < popup_cnt; i++){
			popNotice = (PopupNoticeDTO)juoList.get(i);
			// 공지를 레이어로 띄우기 위해 아래 링크는 제외 시킴
			popup_link	+=	"Popup_Notice('"+CONTEXTPATH+"/PopupNotice.cmd?cmd=popupNoticeShow&pSEQ_NO="+popNotice.getSeqNo()+"','"+popNotice.getWinWidth()+"','"+popNotice.getWinHeight()+"','"+popNotice.getWinXposition()+"','"+popNotice.getWinYposition()+"');\n";
		}
	}

	if(USERID==null || ("").equals(USERID) || ("null").equals(USERID) ){
		if(StringUtil.nvl(model.get("UnReadCnt")) != "" ){
			UnReadCnt	=	StringUtil.nvl(model.get("UnReadCnt"), 0);
		}
		
		if(MENUNO==null || ("").equals(MENUNO) || ("null").equals(MENUNO)){
			MENUNO	=	StringUtil.nvl(request.getParameter("MEUNO"));
		}
	}

	String	myEdaLink		=	"javascript:alert('로그인을 하세요.')";
	String	USERDEPTDAECODE	=	UserBroker.getDeptDaeCode(request);
	String	USERDEPTSOCODE	=	UserBroker.getDeptSoCode(request);
	boolean	CHKDEPTMANAGER	=	UserBroker.chkDeptManager(request);
	if(!USERID.equals("")){		

		MessageDAO messageDao = new MessageDAO();
		UnReadCnt	=	StringUtil.nvl( String.valueOf(messageDao.getUnReadCnt(SYSTEMCODE,USERID)), 0);

		if(USERTYPE.equals("M"))
			myEdaLink = "/CurriSub.cmd?cmd=currentMypageList&MENUNO=0";
		else if(USERTYPE.equals("P") || USERTYPE.equals("J"))
			myEdaLink = "/Main.cmd?cmd=profmangerCurriList&pGubun=1&MENUNO=0";
		else if(USERTYPE.equals("S"))
			myEdaLink = "/Main.cmd?cmd=stuCurriList&MENUNO=0";
		else
			myEdaLink = "/Message.cmd?cmd=receiveMessagePagingList&MENUNO=0";
	}
	
	String	MainMenu	=	StringUtil.nvl(request.getParameter("MainMenu"));

%>
<html>
<head>
<title>::: 전태일을 따르는 사이버 노동대학 :::</title>
<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/style.css">
<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/form.css">
<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/form_community.css">
<script language=javascript src='<%=CONTEXTPATH%>/js/imagesover.js'></script>
<script language=javascript src='<%=CONTEXTPATH%>/js/flash.js'></script>

<Script Language="JavaScript" src = "<%=CONTEXTPATH%>/js/common1.js"></script>
<script language=javascript src="<%=CONTEXTPATH%>/js/common_util.js"></script>
<script language=javascript src="<%=CONTEXTPATH%>/js/common_button.js"></script>
<script language=javascript src="<%=CONTEXTPATH%>/js/selectbox.js"></script>

<script type="text/javascript" src="<%=CONTEXTPATH%>/js/scriptaculous/lib/prototype.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/scriptaculous/src/effects.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/scriptaculous/src/scriptaculous.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/util.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/ajaxCommon.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/imageView.js"></script>
<!-- Main_home.jsp용 스크립트 시작 -->
<script language=javascript>
<!--
	function SetCookie (name, value) {
	   var argv = SetCookie.arguments;
	   var argc = SetCookie.arguments.length;
	   var expires = (2 < argc) ? argv[2] : null;
	   var path = (3 < argc) ? argv[3] : null;
	   var domain = (4 < argc) ? argv[4] : null;
	   var secure = (5 < argc) ? argv[5] : false;
	   document.cookie = name + "=" + escape (value) +
		 ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
		 ((path == null) ? "" : ("; path=" + path)) +
		 ((domain == null) ? "" : ("; domain=" + domain)) +
		 ((secure == true) ? "; secure" : "");
	}
	
	function getCookie(name)
	{ 
		var Found = false
		var start, end 
		var i = 0 

		// cookie 문자열 전체를 검색 
		while(i <= document.cookie.length)
		{ 
			start = i 
			end = start + name.length

			// name과 동일한 문자가 있다면
			if(document.cookie.substring(start, end) == name)
			{ 
				Found = true
				break
			}
			i++
		} 

		// name 문자열을 cookie에서 찾았다면 
		if(Found == true)
		{ 
			start = end + 1 
			end = document.cookie.indexOf(";", start) 
			// 마지막 부분이라는 것을 의미
			if(end < start) 
			end = document.cookie.length 
			// name에 해당하는 value값을 추출하여 리턴한다. 
			return document.cookie.substring(start, end) 
		} 
		// 찾지 못했다면 
		return "" 
	} 
	
	function Popup_Notice(url, width, height, top, left) {
		if ( getCookie("popup") != "noshow" )
		{
			window.open(url, "event", "toolbar=no,location=no, width="+width+", height="+height+",top="+top+", left="+left+", directories=no,status=no,menubar=no,scrollbars=yes,resizable=no");
		}
	}

	function openPopupNotice()
	{
		<%=popup_link%>
	}

	function form_chk(){
		if (document.Login.userId.value == "" ) {
			alert("아이디를 입력하세요.");
			return false;
		}

		if (document.Login.userPw.value == "") {
			alert("패스워드를 입력하세요." );
			return false;
		}
	}

	function course_popup(curricode, curriyear, curriterm) {
		window.open("<%=CONTEXTPATH%>/Main.cmd?cmd=popupOrdinaryContents&pCurriCode="+curricode+"&pCurriYear="+curriyear+"&pCurriTerm="+curriterm+" ", "ordinaryContents", "");
	}

//-->
</script>
<!-- Main_home.jsp용 스크립트 끝 -->
<script language="javascript">

	//플래쉬메뉴 링크
	function goPotalPage (str1, str2) {
		var urlStr	=	"";
		var param	=	"&pInfoNum="+str2;
		
		if(str1 == '1') {
			urlStr	=	"<%=CONTEXTPATH%>/Main.cmd?cmd=infoShow&pMode=Info";	
		}
		else if(str1 == '2' || str1 == '100') {
			urlStr	=	"<%=CONTEXTPATH%>/Main.cmd?cmd=enterShow&pMode=Enter";
		}
		else if(str1 == '11') {
			urlStr	=	"<%=CONTEXTPATH%>/Main.cmd?cmd=StudentCenterShow&pMode=StudentCenter";
		}
		else if(str1 == '3') {
			if(str2 == '0' || str2 == '1') {
				urlStr	=	"<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=17&pMode=News&MENUNO=0";
			} else if(str2 == '2') {
				urlStr	=	"<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=18&pMode=News&MENUNO=0";
			} else if(str2 == '3') {
				urlStr	=	"<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=19&pMode=News&MENUNO=0";
			}
		}
		// 이용안내
		else if(str1 == '4') {
			urlStr	=	"<%=CONTEXTPATH%>/Main.cmd?cmd=infoShow&pMode=Help";
		}
		else if(str1 == '12') {
			urlStr	=	"<%=myEdaLink%>";
		}
		else if(str1 == '13') {
			urlStr	=	"<%=CONTEXTPATH%>/PublishCurriSub.cmd?cmd=publishCurriSubPageList&pPareCode1=99999&pPareCode2=00001&pGubun=1&MENUNO=0&pMode=Home&MainMenu=Y";
		}
		else if(str1 == '14') {
			urlStr	=	"<%=CONTEXTPATH%>/Main.cmd?cmd=communityShow&pMode=Community&MENUNO=0";
		}
		
		
		
		document.location = urlStr + param;
	}

	for (var k=1; k<=3; k++)
	{
		eval('sMenu'+k+'A = new Image();');
		eval('sMenu'+k+'B = new Image();');
		
		eval('sMenu'+k+'A.src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tab0'+k+'.gif";');
		eval('sMenu'+k+'B.src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tab0'+k+'_on.gif";');
	
	}

	for (var k=1; k<=3; k++)
	{
		eval('s1Menu'+k+'A = new Image();');
		eval('s1Menu'+k+'B = new Image();');
		
		eval('s1Menu'+k+'A.src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tab1'+k+'.gif";');
		eval('s1Menu'+k+'B.src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/bbs_tab1'+k+'_on.gif";');
	
	}

   //로그인 폼 아이디/비밀번호 표시 나타내고 사라지기
   function loginform_clearbg(type) {
    if (type == "id") {
     document.Login.userId.style.backgroundImage = '';
    } else if (type == "pass") {
     document.Login.userPw.style.backgroundImage = '';
    }
   }
	
	var oldMenu1;
	var oldMenu2;
	function toggleMenu(currMenu) {
		// MODIFIED 2021-01-18 13:45
		// if (document.all) {
			// obj = eval("document.all." + currMenu )
			// if(obj==null)  return true;

			// thisMenu = eval("document.all." + currMenu + ".style")
			// if (thisMenu.display == "block") {
				// thisMenu.display = "none";
			// } else {
				// thisMenu.display = "block";
				// if(currMenu.substring(0,4)=='menu') {
					// if(oldMenu1!=null && oldMenu1!=thisMenu) 
						// oldMenu1.display = "none";
					// oldMenu1 = thisMenu;
				// } else if(currMenu.substring(0,5)=='smenu') {
					// if(oldMenu2!=null && oldMenu2!=thisMenu) 
						// oldMenu2.display = "none";
					// oldMenu2 = thisMenu;
				// }
			// }
			// return false;
		// } else {
			// return true;
		// }
		var menu = document.querySelector('#' + currMenu);
		if(menu==null)  return true;
		//console.log('menu:', menu);
		if (menu.style.display == "") {
			  menu.style.display = "none";
		} else {
			  menu.style.display = "";
		}
		return true;
	}
	
	function MM_preloadImages() { //v3.0
	  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
	    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
	    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
	}
	
	function MM_swapImgRestore() { //v3.0
	  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
	}
	
	function MM_swapImage() { //v3.0
	  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
	   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
	}
</SCRIPT>
<!-- // left 메뉴 -->
</head>
<%
	String	bodyTagStr	=	"";
	if(PMODE.equals("Info")) {
		bodyTagStr	=	"onLoad=\"MM_preloadImages('../img/1/homepage/tab_ci03_on.gif','../img/1/homepage/tab_ci01_on.gif','../img/1/homepage/tab13_01_on.gif','../img/1/homepage/tab13_02_on.gif')\" ";
	}
%>
<body <%=bodyTagStr%> onLoad="openPopupNotice();">
<%
// 달력 Layer

	if (DATEYN != null && (DATEYN.toUpperCase().equals("Y")) ) {
%>
<div id="CalendarLayer" style="display:none; width:172px; height:176px;z-index:10;">
    <iframe name="CalendarFrame" src = "<%=CONTEXTPATH%>/html/lib.calendar.js.html" width="172" height="176" border="0" frameborder="0" scrolling="no"></iframe>
</div>
<%
	} // end if

%>
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="background-image:url(<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bg.gif); background-repeat:repeat-x;">
	<!-- logo, TOP메뉴 -->
	<tr valign="top">
		<td width="880" height="50"> 
			<table width="880" height="50" border="0" cellpadding="0" cellspacing="0">
				<tr> 
          			<td height="38" align="right"><a 
          				href="<%=CONTEXTPATH%>" onfocus='blur()';><img 
          				src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/hoem.gif" width="38" height="9" border="0" align="absmiddle"></a>&nbsp;&nbsp;<a 
          				href="<%=CONTEXTPATH%>/Main.cmd?cmd=siteMapShow&pMode=SiteMap&MENUNO=0" onfocus='blur()';><img 
          				src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/sitmap.gif" width="50" height="9" border="0" align="absmiddle"></a></td>
        		</tr>
      		</table>
		</td>
	</tr>
	<!-- left이미지, 로그인박스, 컨텐츠, 퀵메뉴 -->
	<tr valign="top">
		<td width="880">
			<table width="880" cellpadding="0" cellspacing="0">
				<!-- 좌측부분 (로고, 로그인, 입학신청, 메뉴, 배너) -->
				<tr valign="top"> 
					<td width="200">
						<!-- 로고 -->
						<table width="153" align="center" cellpadding="0" cellspacing="0">
							<tr><!--http://www.junnodae.org/main.asp-->
								<td style="padding:0 0 28 0"><a href="http://www.junnodae.org" onfocus='blur()';><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/logo.gif" border="0"></a></td>
							</tr>
						</table>
						<!-- 로그인 -->
<form name="Login" method="post" action="<%=CONTEXTPATH%>/Main.cmd?cmd=setLogin" onsubmit="return form_chk();">
<%
	if (USERID.equals("") || USERID.equals("null")) {
%>
						<table width="153" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td width="110" height="18"><input type="text" name="userId" class="id" tabindex="1" onmousedown="loginform_clearbg('id');" onkeydown="loginform_clearbg('id');" style="BACKGROUND: url(<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/loginbox_idbg.gif) #CCCCCC no-repeat; WIDTH: 110px"/></td>
								<td width="5"></td>
								<td width="38" rowspan="3" align="right"><input type="image" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/btn_login.gif" width="38" height="40" border="0"></a></td>
							</tr>
							<tr> 
								<td colspan="2" height="3"></td>
							</tr>
							<tr>
								<td width="110" height="18"><input type="password" name="userPw" class="pwd" tabindex="2" onmousedown="loginform_clearbg('pass');" onkeydown="loginform_clearbg('pass');" style="BACKGROUND: url(<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/loginbox_pwdbg.gif) #CCCCCC no-repeat; WIDTH: 110px"/></td>
								<td width="5"></td>
							</tr>
							<tr> 
								<td height="30" colspan="3" align="right" class="idsearch"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><a href="<%=CONTEXTPATH%>/User.cmd?cmd=searchIdPwShow&pMode=Home&MENUNO=0" onfocus='blur()';><u>아이디/비밀번호찾기</u></a></td>
							</tr>
						</table>
						<table width="153" align="center" cellpadding="0" cellspacing="0">
							<tr> 
								<td style="padding:5 0 20 0"><a href="<%=CONTEXTPATH%>/User.cmd?cmd=userWrite&pMode=Home&MENUNO=0&pGUBUN=write" onfocus='blur()';><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/btn_01.gif" border="0"></a></td>
							</tr>
						</table>
<%
	} else {
%>					
						<!-- 로그인 -->
						<table width="153" align="center" cellpadding="0" cellspacing="0">
							<tr> 
								<td height="30" align="left" class="idsearch"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" width="16" height="15" align="absmiddle"><font class="lg_name"> <%= UserBroker.getUserName(request)%>님</font> <a href="<%=CONTEXTPATH%>/Message.cmd?cmd=receiveMessagePagingList&MENUNO=0" onfocus='blur()';><u>쪽지(<%=UnReadCnt%>)통</u></a></td>
							</tr>
							<tr>
								<td height="18" align="center"><a href="<%=CONTEXTPATH%>/Main.cmd?cmd=userLogOff"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_quit.gif"></a> <a href="<%=CONTEXTPATH%>/User.cmd?cmd=userWrite&pGUBUN=edit&MENUNO=0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_infor.gif"></a></td>
							</tr>
							<tr> 
								<td height="3"></td>
							</tr>
							<tr>
						</table>
						<table width="153" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td height="31" style="padding:10 0 20 0">
<%	//if(CurriDateAction.getDateFlag("1", "enroll_start", "enroll_end", CommonUtil.getCurrentDate()).equals("Y")){	%><a 
	href="<%=CONTEXTPATH%>/CurriSub.cmd?cmd=currentList&MENUNO=0&pMode=MyPage" onfocus='blur()';><img 
	src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_04.gif" width="153" height="31" border="0"></a>
<%	//}	%>
								</td>
							</tr>
						</table>
<%	}	%>
</form>
						<table width="153" align="center" cellpadding="0" cellspacing="0">
							<tr> 
								<td>
									<!-- LEFT 메뉴 시작 -->
									<%@include file="left.jsp" %>
									<!-- LEFT 메뉴 끝 -->
								</td>
							</tr>
						</table>
					</td>
					<!-- // 좌측부분 (로고, 로그인, 입학신청, 메뉴, 배너 -->
					<!-- 컨텐츠 -->
					<td width="680">
						<!-- 네비게이션, 비쥬얼이미지 -->

<%		//-- 로그인 하기 전의 HEADER
	if (PMODE.equals("Home") || PMODE.equals("Info") || PMODE.equals("Enter") || PMODE.equals("News") || PMODE.equals("Help") || PMODE.equals("SiteMap") || (USERID.equals("") || USERID.equals("null"))) {	%>
						<table width="680" height="180" cellpadding="0" cellspacing="0">
							<tr valign="top">
								<td>
<%
		if ("222.112.16.114@@@@".equals(request.getRemoteAddr()) || request.getParameter("test") != null) {
%>								
								<script>var ff = lg("<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/swf/login_mv4.swf", "Mv", 680, 180, '');documentwrite(ff);</script>
<%
		} else {
%>		
<div class="navbar">
  <div class="dropdown">
    <button class="dropbtn">대학소개 
      <i class="fa fa-caret-down"></i>
    </button>
    <div class="dropdown-content">
      <a href="http://www.junnodae.org/Main.cmd?cmd=infoShow&pMode=Info&pInfoNum=1">인사말</a>
      <a href="http://www.junnodae.org/Main.cmd?cmd=infoShow&pMode=Info&pInfoNum=2">설립취지연혁</a>
      <a href="http://www.junnodae.org/Main.cmd?cmd=infoShow&pMode=Info&pInfoNum=3">교육과정내용</a>
      <a href="http://www.junnodae.org/Main.cmd?cmd=infoShow&pMode=Info&pInfoNum=4">교수진소개</a>
      <a href="http://www.junnodae.org/Main.cmd?cmd=infoShow&pMode=Info&pInfoNum=5">대학의 조직과 기구</a>
      <a href="http://www.junnodae.org/Main.cmd?cmd=infoShow&pMode=Info&pInfoNum=6">찾아오는 길</a>
    </div>
  </div> 
  <div class="dropdown">
    <button class="dropbtn">입학안내 
      <i class="fa fa-caret-down"></i>
    </button>
    <div class="dropdown-content">
      <a href="http://www.junnodae.org/Main.cmd?cmd=enterShow&pMode=Enter&pInfoNum=1">입학안내</a>
      <a href="http://www.junnodae.org/Main.cmd?cmd=enterShow&pMode=Enter&pInfoNum=2">수강안내</a>
      <a href="http://www.junnodae.org/Main.cmd?cmd=enterShow&pMode=Enter&pInfoNum=3">학사일정</a>
    </div>
  </div> 
  <div class="dropdown">
    <button class="dropbtn">학교소식 
      <i class="fa fa-caret-down"></i>
    </button>
    <div class="dropdown-content">
      <a href="http://www.junnodae.org/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=17&pMode=News&MENUNO=0&pInfoNum=1">학사공지</a>
      <a href="http://www.junnodae.org/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=18&pMode=News&MENUNO=0&pInfoNum=2">일반공지</a>
      <a href="http://www.junnodae.org/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=19&pMode=News&MENUNO=0&pInfoNum=3">소식</a>
    </div>
  </div> 
  <a class="onemenu" href="http://www.junnodae.org/Main.cmd?cmd=infoShow&pMode=Help&pInfoNum=0">이용안내</a>
</div>
<img src="http://www.junnodae.org/img/login_mv4_0_0_2.jpg">
<%
		}
%>		


								</td>
							</tr>
						</table>

<%
	}	//-- 로그인 하기 전의 HEADER 끝
	else {
%>

						<table width="680" height="35" align="center"cellpadding="0" cellspacing="0">
							<tr valign="top">
								<td class="flash_pd">
<%
		if ("222.112.16.114@@@@".equals(request.getRemoteAddr()) || request.getParameter("test") != null) {
%>							
								<script>var ff = lg("<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/swf/login01_mv.swf", "Mv", 680, 37, '');documentwrite(ff);</script>
<%
		} else {
%>
<div class="navbar">
  <a class="onemenu" href="http://www.junnodae.org/Main.cmd?cmd=StudentCenterShow&pMode=StudentCenter&pInfoNum=0">학생지원센터</a>
  <a class="onemenu" href="http://www.junnodae.org/Main.cmd?cmd=stuCurriList&MENUNO=0&pInfoNum=0">나의 학습실</a>
  <a class="onemenu" href="http://www.junnodae.org/PublishCurriSub.cmd?cmd=publishCurriSubPageList&pPareCode1=99999&pPareCode2=00001&pGubun=1&MENUNO=0&pMode=Home&MainMenu=Y&pInfoNum=0">열린마당</a>
  <a class="onemenu" href="http://www.junnodae.org/Main.cmd?cmd=communityShow&pMode=Community&MENUNO=0&pInfoNum=0">동아리</a>
</div>

<%
		}
%>	
								</td>
							</tr>
						</table>

<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CommonUtilWork.js"></script>
<Script>
<%-- /* 권한 체크를 위하여 세팅함  */ --%>
CommonUtilWork.getAuthorCheckAjax(callbackMethodAuthor);

var c_right = "false";
var r_right = "false";
var u_right = "false";
var d_right = "false";

function callbackMethodAuthor(arr){

	if(arr.length==4){
		c_right = arr[0];
		r_right = arr[1];
		u_right = arr[2];
		d_right = arr[3];
	}
}
</Script>

<%	}	%>

<%	//-- 타이틀 & 네비게이션
	//if(PMODE.equals("Home") || PMODE.equals("MyPage") || PMODE.equals("News") || PMODE.equals("SiteMap")) {

		String NAVIGATION = "";
		if (model != null) NAVIGATION = (String)model.get("site_navigation");

 		if( NAVIGATION != "" && (NAVIGATION.indexOf("<img") >-1) ){

%>
						<table width="680" height="100%" align="center">
								<tr valign="top"> 
									<!-- sub title -->
									
                					<td height="34" width="302" class="stit_title"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon07.gif" align="absmiddle"><font face='돋움' size="3"><b> <%=NAVIGATION.substring(NAVIGATION.lastIndexOf(">")+2,NAVIGATION.length())%></b></font></td>
									<!-- // sub title -->
									<!-- history -->
									<td class="stit_history" valign="bottom" align="right" width="378"> 
<% 
		if (NAVIGATION != "") {
			out.println(NAVIGATION) ;
		} // end if
%>
									</td>
									<!-- // history -->
								</tr>
								<tr valign="top"> 
									<td colspan="2" class="content_top" valign="top">
<%		} 
	//}	%>
<%
//out.print("<p>Remote Addr: " + request.getRemoteAddr() + "</p>");
%>