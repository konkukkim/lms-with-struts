<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
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
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
<title>:: mobile for 전태일을 따르는 사이버 노동대학 :::</title>
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
		if (document.all) {
			obj = eval("document.all." + currMenu )
			if(obj==null)  return true;

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
			return false;
		} else {
			return true;
		}
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
		<td width="0" height="0"> 
<!-- 
			<table width="680" height="50" border="0" cellpadding="0" cellspacing="0">
				<tr> 
          			<td height="38" align="right">
                                       <a 
          				href="<%=CONTEXTPATH%>" onfocus='blur()';><img 
          				src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/hoem.gif" width="38" height="9" border="0" align="absmiddle"></a>&nbsp;&nbsp;<a 
          				href="<%=CONTEXTPATH%>/Main.cmd?cmd=siteMapShow&pMode=SiteMap&MENUNO=0" onfocus='blur()';><img 
          				src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/sitmap.gif" width="50" height="9" border="0" align="absmiddle"></a>
				</td>
        		</tr>
      		</table>
-->
<!-- TEMP-ONE -->
	</td>
	</tr>
	<!-- left이미지, 로그인박스, 컨텐츠, 퀵메뉴 -->
	<tr valign="top">
		<!--<td width="880">-->
		<td width="0">
<!-- TEMP-TWO -->
			<table width="" cellpadding="0" cellspacing="0">
				<!-- 좌측부분 (로고, 로그인, 입학신청, 메뉴, 배너) -->
				<tr valign="top"> 
					<!--<td width="200"> -->
					<td width="0">
<!-- TEMP-THREE -->
						<!-- 로그인 -->
<form name="Login" method="post" action="<%=CONTEXTPATH%>/Main.cmd?cmd=setLogin" onsubmit="return form_chk();">
</form>
						<table width="0" align="center" cellpadding="0" cellspacing="0">
							<tr> 
								<td>
									<!-- LEFT 메뉴 시작 -->
									<%//@include file="left.jsp" %>
									<!-- LEFT 메뉴 끝 -->
								</td>
							</tr>
						</table>
					</td>
					<!-- // 좌측부분 (로고, 로그인, 입학신청, 메뉴, 배너 -->
					<!-- 컨텐츠 -->
					<td width="680">
						<!-- 네비게이션, 비쥬얼이미지 -->


<%	//-- 타이틀 & 네비게이션
	//if(PMODE.equals("Home") || PMODE.equals("MyPage") || PMODE.equals("News") || PMODE.equals("SiteMap")) {
	if(1==2) {

		String NAVIGATION = "";
		if (model != null) NAVIGATION = (String)model.get("site_navigation");

 		if( NAVIGATION != "" && (NAVIGATION.indexOf("<img") >-1) ){

%>
						<table width="680" height="100%" align="center">
							<tr valign="top"> 
							<!-- sub title -->
									
<!--
                					<td height="34" width="302" class="stit_title">ABC<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon07.gif" align="absmiddle"><font face='돋움' size="3"><b> <%=NAVIGATION.substring(NAVIGATION.lastIndexOf(">")+2,NAVIGATION.length())%></b></font></td>
-->
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
	//}	
	}
%>
<!-- end of header.m.jsp -->
