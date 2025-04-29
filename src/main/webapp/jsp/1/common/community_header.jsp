<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map,java.util.ArrayList"%>
<%@ page import ="com.edutrack.common.UserBroker,com.edutrack.framework.util.DateTimeUtil,com.edutrack.framework.util.StringUtil"%>
<%@ page import ="com.edutrack.common.CommonUtil,com.edutrack.common.SiteNavigation,com.edutrack.message.dao.MessageDAO,com.edutrack.common.dto.CommMemDTO"%>
<%@ page import ="com.edutrack.curritop.dto.CurriTopDTO,com.edutrack.common.dto.CodeParam,com.edutrack.common.dto.EditorParam"%>
<%@ page import = "com.edutrack.curritop.dao.CurriCategoryDAO, com.edutrack.curritop.dto.CurriCategoryTotalDTO" %>
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
	String USERDEPTDAECODE   = UserBroker.getDeptDaeCode(request);
	String USERDEPTSOCODE   = UserBroker.getDeptSoCode(request);
	boolean CHKDEPTMANAGER   = UserBroker.chkDeptManager(request);

	int UnReadCnt = 0;
	String myEdaLink = "javascript:alert('로그인 후 접근 하실 수 있습니다.');document.location.href='"+CONTEXTPATH+"/User.cmd?cmd=usersLoginShow&pMode=MEMBER';";

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

%>
<html>
<head>
<title>::: 전태일을 따르는 사이버 노동대학 :::</title>
<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/style.css">
<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/form.css">
<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/date_select1.css">
<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/form_community.css">

<Script Language="JavaScript" src = "<%=CONTEXTPATH%>/js/common1.js"></script>
<script language=javascript src="<%=CONTEXTPATH%>/js/common_util.js"></script>
<script language=javascript src="<%=CONTEXTPATH%>/js/common_button.js"></script>
<SCRIPT language=javascript src="<%=CONTEXTPATH%>/js/flash.js"></SCRIPT>

<script type="text/javascript" src="<%=CONTEXTPATH%>/js/scriptaculous/lib/prototype.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/scriptaculous/src/effects.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/scriptaculous/src/scriptaculous.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/util.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/ajaxCommon.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/imageView.js"></script>
<!-- left 메뉴 -->
<script language=Javascript>
<!--
for (var k=1; k<=3; k++)
{
	eval('sMenu'+k+'A = new Image();');
	eval('sMenu'+k+'B = new Image();');
	
	eval('sMenu'+k+'A.src = "../img/1/login/bbs_tab0'+k+'.gif";');
	eval('sMenu'+k+'B.src = "../img/1/login/bbs_tab0'+k+'_on.gif";');

}

//-->
</script>

<script language=Javascript>
<!--
for (var k=1; k<=3; k++)
{
	eval('s1Menu'+k+'A = new Image();');
	eval('s1Menu'+k+'B = new Image();');
	
	eval('s1Menu'+k+'A.src = "../img/1/login/bbs_tab1'+k+'.gif";');
	eval('s1Menu'+k+'B.src = "../img/1/login/bbs_tab1'+k+'_on.gif";');

}

//-->
</script>

<script language="JavaScript"> 

   //로그인 폼 아이디/비밀번호 표시 나타내고 사라지기
   function loginform_clearbg(type) {
    if (type == "id") {
     document.login.id.style.backgroundImage = '';
    } else if (type == "pass") {
     document.login.password.style.backgroundImage = '';
    }
   }
</script>
<!-- left 메뉴 -->
<SCRIPT language=javascript>
<!--
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
			return false;
		} else {
			return true;
		}
	}

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
			return false;
		} else {
			return true;
		}
	}

	//플래쉬메뉴 링크
	function goPotalPage (str1, str2) {
		var urlStr	=	"";
		var param	=	"&pInfoNum="+str2;
		
		if(str1 == '1') {
			if(str2 == '0' || str2 == '1' || str2 == '1' || str2 == '1') {
				urlStr	=	"";
				alert('준비중입니다.');
			} else {
				urlStr	=	"<%=CONTEXTPATH%>/Main.cmd?cmd=infoShow&pMode=Info";	
			}
		}
		else if(str1 == '2') {
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
		else if(str1 == '4') {
			//urlStr	=	"<%=CONTEXTPATH%>/Main.cmd?cmd=infoShow&pMode=Help";
			alert("준비중입니다.");
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
//-->
</SCRIPT>
<!-- // left 메뉴 -->

</head>

<body>
<%
// 달력 Layer

	if (DATEYN != null && (DATEYN.toUpperCase().equals("Y")) ) {
%>
<div id="CalendarLayer" style="display:none; width:172px; height:176px; ">
    <iframe name="CalendarFrame" src = "<%=CONTEXTPATH%>/html/lib.calendar.js.html" width="172" height="176" border="0" frameborder="0" scrolling="no"></iframe>
</div>
<%
	} // end if
%>
<table width="100%" height="90%"border="0" cellpadding="0" cellspacing="0" style="background-image:url(../img/1/common/bg.gif); background-repeat:repeat-x;">
	<!-- logo, TOP메뉴 -->
	<tr valign="top">
		<td width="880" height="50"> 
			<table width="880" height="50" border="0" cellpadding="0" cellspacing="0">
				<tr> 
          			<td height="38" align="right"><a href="<%=CONTEXTPATH%>" onfocus='blur()';><img 
          				src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/hoem.gif" width="38" height="9" border="0" align="absmiddle"></a>&nbsp;&nbsp;<a 
          				href="<%=CONTEXTPATH%>/Main.cmd?cmd=siteMapShow&pMode=SiteMap&pMENUNO=0" onfocus='blur()';><img 
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
							<tr>
								<td style="padding:0 0 20 0"><a href="<%=CONTEXTPATH%>" onfocus='blur()';><img 
									src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/logo.gif" border="0"></a></td>
							</tr>
						</table>
						<!-- 로그인 -->
						<table width="153" align="center" cellpadding="0" cellspacing="0">
							<tr> 
								<td height="30" align="left" class="idsearch">
<%
	if (!USERID.equals("")) {
%>
									<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" width="16" height="15" 
									align="absmiddle"><a 
									href="<%=CONTEXTPATH%>/User.cmd?cmd=userWrite&pGUBUN=edit&MENUNO=0" class="login_memo"><font 
									class="lg_name"> <%= UserBroker.getUserName(request)%>님</font></a> <a 
									href="<%=CONTEXTPATH%>/Message.cmd?cmd=receiveMessagePagingList&MENUNO=0" 
									onfocus='blur()';><u>쪽지(<%=UnReadCnt%>)통</u></a>
<%
	}
%>
								</td>
							</tr>
							<tr>
								<td height="18" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_quit.gif"> <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_infor.gif"></td>
							</tr>
							<tr> 
								<td height="3"></td>
							</tr>
							<tr>
						</table>
				<!-- Left Menu Start -->
					<%@include file="./left.jsp" %>
				<!-- Left Menu End -->
					</td>
					<!-- // 좌측부분 (로고, 로그인, 입학신청, 메뉴, 배너 -->
					<!-- 컨텐츠 -->
					<td width="680">
						<!-- 네비게이션, 비쥬얼이미지 -->
						<table width="680" height="35" align="center"cellpadding="0" cellspacing="0">
							<tr valign="top">
								<td class="flash_pd"><script>var ff = lg("<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/swf/login01_mv.swf", "Mv", 680, 37, '');documentwrite(ff);</script></td>
							</tr>
						</table>
						<table width="680" align="right">
								<tr valign="top"> 
									<!--img -->
									<td colspan="2" class="com_img"></td>
								</tr>