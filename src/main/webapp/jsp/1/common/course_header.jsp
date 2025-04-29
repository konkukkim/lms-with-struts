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
<%@ page import ="java.lang.*, java.util.Map,java.util.ArrayList, javax.sql.RowSet"%>
<%@ page import ="com.edutrack.common.UserBroker, com.edutrack.framework.util.DateTimeUtil, com.edutrack.framework.util.StringUtil"%>
<%@ page import ="com.edutrack.common.CommonUtil, com.edutrack.common.SiteNavigation"%>
<%@ page import ="com.edutrack.common.dto.UserMemDTO, com.edutrack.common.dto.CurriMemDTO, com.edutrack.common.dto.CodeParam"%>
<%@ page import ="com.edutrack.currisub.dao.CurriSubCourseDAO, com.edutrack.currisub.dto.CurriCourseListDTO, com.edutrack.message.dao.MessageDAO"%>
<%@ page import = "com.edutrack.currisub.dao.CurriSubDAO" %>
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

	UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	CurriMemDTO curriMemDto = userMemDto.curriInfo;

	String SYSTEMCODE = userMemDto.systemCode;
	String USERID = userMemDto.userId;
	String USERNAME = userMemDto.userName;
	String USERTYPE = userMemDto.userType;
	String USERDEPTDAECODE   = userMemDto.deptDaeCode;
	String USERDEPTSOCODE   = userMemDto.deptSoCode;
	boolean CHKDEPTMANAGER   = userMemDto.deptManager;

	String CURRICODE = curriMemDto.curriCode;
	String CURRINAME = curriMemDto.curriName;
	String CURRITYPE = curriMemDto.curriType;

	int CURRIYEAR = StringUtil.nvl(curriMemDto.curriYear,0);
	int CURRITERM = StringUtil.nvl(curriMemDto.curriTerm,0);

	String classUserType = USERTYPE;

	if(USERTYPE.equals("M") || USERTYPE.equals("P") || USERTYPE.equals("C")) classUserType = "P";

	//-- 과정에 속한 과목의 리스트를 가져온다.
	CurriSubCourseDAO curriSubCoruseDao = new CurriSubCourseDAO();
	ArrayList COURSELIST = new ArrayList();
	CurriCourseListDTO CourseListDto = null;
	String PROFID = "";

	if(USERTYPE.equals("P")) PROFID = USERID;
		COURSELIST = curriSubCoruseDao.getCurriCourseList(SYSTEMCODE,CURRICODE,CURRIYEAR,CURRITERM,PROFID);

	//-- 과목 갯수는 COURSELIST.size()
	int COURSELISTSIZE = COURSELIST.size();
	String COURSEID = "";
	String COURSENAME = "";

	if(COURSELISTSIZE == 1){
		CourseListDto = (CurriCourseListDTO)COURSELIST.get(0);
		COURSEID = CourseListDto.getCourseId();
		COURSENAME = CourseListDto.getCourseName();
	}

	int UnReadCnt = 0;

	if(!USERID.equals("")){
		MessageDAO messageDao = new MessageDAO();
		UnReadCnt	=	StringUtil.nvl( String.valueOf(messageDao.getUnReadCnt(SYSTEMCODE,USERID)), 0);
	}

	String myEdaLink = "";

	if(USERTYPE.equals("M"))
		myEdaLink = "/CurriSub.cmd?cmd=currentMypageList&MENUNO=0";
	else if(USERTYPE.equals("P") || USERTYPE.equals("J"))
		myEdaLink = "/Main.cmd?cmd=profmangerCurriList&pGubun=1&MENUNO=0";
	else if(USERTYPE.equals("S"))
		myEdaLink = "/Main.cmd?cmd=stuCurriList&MENUNO=0";
	else
		myEdaLink = "/Message.cmd?cmd=receiveMessagePagingList&MENUNO=0";
%>
<html>
<head>
<title>::: 전태일을 따르는 사이버 노동대학 :::</title>

<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/style.css">
<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/form.css">
<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/form_classroom.css">
<script language=javascript src="<%=CONTEXTPATH%>/js/flash.js"></script>
<Script Language=JavaScript src="<%=CONTEXTPATH%>/js/common1.js"></script>
<script language=javascript src="<%=CONTEXTPATH%>/js/common_util.js"></script>
<script language=javascript src="<%=CONTEXTPATH%>/js/common_button.js"></script>
<script language=javascript src="<%=CONTEXTPATH%>/js/imagesover.js"></script>

<script type="text/javascript" src="<%=CONTEXTPATH%>/js/scriptaculous/lib/prototype.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/scriptaculous/src/effects.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/scriptaculous/src/scriptaculous.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/util.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/ajaxCommon.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/imageView.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CommonUtilWork.js"></script>
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

</head>
<body>
<%
	//  달력 Layer
	if (DATEYN != null && (DATEYN.equals("Y") || DATEYN.equals("y"))) {
%>
<!--<script language="JavaScript" src = "<%=CONTEXTPATH%>/js/date_select1.js"></script>-->
<div id="CalendarLayer" style="display:none; width:172px; height:176px;Z-index:1">
    <iframe name="CalendarFrame" src = "<%=CONTEXTPATH%>/html/lib.calendar.js.html" width="172" height="176" border="0" frameborder="0" scrolling="no"></iframe>
</div>
<%
	} // end if
%>
<table width="100%" height="90%"border="0" cellpadding="0" cellspacing="0" style="background-image:url(<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bg.gif); background-repeat:repeat-x;">
	<!-- logo, TOP메뉴 -->
	<tr valign="top">
		<td width="880" height="60"> 
			<table width="880" height="60" border="0" cellpadding="0" cellspacing="0">
				<tr> 
          			<td width="175">&nbsp;</td>
					<td width="*%"  height="39" align="left" class="c_title1" valign="middle"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/classroomtitle_blet.gif" width="13" height="13" hspace="5" align="absmiddle">과정명 : <%=CURRINAME %> <%-- [<%=CURRIYEAR%>년 <%=CURRITERM%>기] --%></td>
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
								<td style="padding:0 0 20 0"><a href="http://www.junnodae.org" onfocus='blur()';><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/logo.gif" border="0"></a></td>
							</tr>
						</table>
						<!-- 로그인 -->
						<table width="153" align="center" cellpadding="0" cellspacing="0">
<form name="LoginTop" method="post" action="/Main.cmd?cmd=setLogin" onsubmit="return form_check();">
<%
	if (!USERID.equals("")) {
%>
							<tr> 
								<td height="30" align="left" class="idsearch"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" width="16" height="15" align="absmiddle"><a href="<%=CONTEXTPATH%>/User.cmd?cmd=userWrite&pGUBUN=edit&MENUNO=0"><font class="lg_name"> <%= UserBroker.getUserName(request)%>님</font></a> <a href="<%=CONTEXTPATH%>/Message.cmd?cmd=receiveMessagePagingList&MENUNO=0" onfocus="blur();"><u>쪽지(<%=UnReadCnt%>)통</u></a></td>
							</tr>
							<tr>
								<td height="18" align="center"><a href="<%=CONTEXTPATH%>/Main.cmd?cmd=userLogOff"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_quit.gif"></a> <a href="<%=CONTEXTPATH%>/User.cmd?cmd=userWrite&pGUBUN=edit&MENUNO=0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_infor.gif"></a></td>
							</tr>
							<tr> 
								<td height="3"></td>
							</tr>
<%
	}
%>
</form>
						</table>
<%
	if(CURRITYPE.equals("R") && USERTYPE.equals("S")){//-- 정규과정의 경우
%>
						<table width="153" align="center" cellpadding="0" cellspacing="0">
							<tr> 
								<td style="padding:10 0 0 0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_classroom_01.gif" width="153" height="33"></td>
							</tr>
							<tr> 
								<td style="padding:5 15 0 18" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_classroom_02.gif">
<%
		/** 강의실 바로 가기	*/
		String property1 = StringUtil.nvl(request.getParameter("pProperty1"),"Cyber");
		CurriSubDAO curriSubDao = new CurriSubDAO();
		RowSet curriRList = null;
		
		if(("S").equals(USERTYPE)) curriRList = curriSubDao.getStCurriList(SYSTEMCODE, USERID,"S", 0, null);
		if(("M").equals(USERTYPE)) curriRList = curriSubDao.currentCurriList(SYSTEMCODE, "", "", "", property1, "R", "", 0);
		if(("P").equals(USERTYPE)) curriRList = curriSubDao.getProfCurriList(SYSTEMCODE, USERID,"ES", 0);
		
		String opValue = "";
%>
<Script Language=JavaScript>
function lectureChg(val){

    var curriInfo = val.split("!");

    if(curriInfo.length==5)
    lectureLink = "<%=CONTEXTPATH%>/LectureMain.cmd?cmd=goLecture&pMode=Lecture&MENUNO=0&pCurriCode="+curriInfo[0]+"&pCurriYear="+curriInfo[1]+"&pCurriTerm="+curriInfo[2]+"&pCurriType="+curriInfo[3]+"&pCurriName="+curriInfo[4];

	location.href = lectureLink ;

}
</Script>
<%
		if(curriRList!=null){
%>
								<select name="curriCode" onChange="javascript:lectureChg(this.value)" class="c_menu_selectbox">
									<option value="" selected>강의실 이동</option>
<%
			while(curriRList.next()){
			    opValue  = StringUtil.nvl(curriRList.getString("curri_code"))
			          +"!"+StringUtil.nvl(curriRList.getString("curri_year"))
			          +"!"+StringUtil.nvl(curriRList.getString("curri_term"))
			          +"!"+StringUtil.nvl(curriRList.getString("curri_property2"))
			          +"!"+StringUtil.nvl(curriRList.getString("curri_name"));
			
			    if(CURRICODE.equals(StringUtil.nvl(curriRList.getString("curri_code")))
			    && CURRIYEAR == curriRList.getInt("curri_year")
			    && CURRITERM == curriRList.getInt("curri_term") ) continue;
%>
									<option value="<%=opValue %>" ><%=StringUtil.setMaxLength(StringUtil.nvl(curriRList.getString("curri_name")),30)%></option>
<%
			}
			curriRList.close();
%>
								</select>
<%
		} // end if
%>
								</td>
							</tr>
							<tr> 
								<td style="padding:0 0 15 0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_classroom_03.gif" width="153" height="15"></td>
							</tr>
						</table>
<%
	}
%>

						<table width="153" align="center" cellpadding="0" cellspacing="0">
							<tr> 
								<td>
									<!-- left menu 시작 -->
									<%@include file="course_left.jsp" %>
									<!-- left menu 끝 -->
								</td>
							</tr>
							<tr valign="top">
								<td height="36" class="c_left_exit">
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
%>									</td>
							</tr>
						</table>
					</td>
					<!-- // 좌측부분 (로고, 로그인, 입학신청, 메뉴, 배너 -->
					<!-- 컨텐츠 -->
					<td width="680"> 
					<!-- 과정명 -->
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
%>									</td>
							</tr>
						</table>

						<table width="680" height="100%" align="center">