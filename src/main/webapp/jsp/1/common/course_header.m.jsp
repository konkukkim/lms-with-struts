<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
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
<title>mobilefor 전태일을 따르는 사이버 노동대학 :::</title>
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
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
<!--FILEINFO: course_header.m.jsp -->
<!-- left 메뉴 -->
<!-- 2012-11 BLOCK
<SCRIPT language=javascript>
</SCRIPT>
-->
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
<!--BLOCK
<table width="100%" height="90%"border="0" cellpadding="0" cellspacing="0" style="background-image:url(<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bg.gif); background-repeat:repeat-x;">
//BLOCK -->

<table>
<!---------
	<tr>
	<td>
	ABCDE
	</td>
	</tr>
---------->

	<!-- SELECT BOX for SUBJECT NAVIGATION -->
	<tr valign="top">
	<td width="880" height="60"> 
<%
	if(CURRITYPE.equals("R") && USERTYPE.equals("S")){//-- 정규과정의 경우
%>
		<table>
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

	</td>
	</tr>
	
	<!-- logo, TOP메뉴 -->
	<tr valign="top">
		<td width="880" height="60"> 
<!-- HOEM.gif
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
-->
		</td>
	</tr>
	<!-- left이미지, 로그인박스, 컨텐츠, 퀵메뉴 -->
	<tr valign="top">
		<td width="880">
			<table width="880" cellpadding="0" cellspacing="0">
				<!-- 좌측부분 (로고, 로그인, 입학신청, 메뉴, 배너) -->
				<tr valign="top"> 
					<!--<td width="200">-->
					<td width="0">
						<!-- 로고 -->
						<!-- 로그인 -->
<!-- BLOCK
						<table width="153" align="center" cellpadding="0" cellspacing="0">
// BLOCK -->
						<table width="153" align="left" cellpadding="0" cellspacing="0">
<form name="LoginTop" method="post" action="/Main.cmd?cmd=setLogin" onsubmit="return form_check();">
<%
	if (!USERID.equals("")) {
%>
							<tr> 
								<td height="30" align="left" class="idsearch"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" width="16" height="15" align="absmiddle"><a href="<%=CONTEXTPATH%>/User.cmd?cmd=userWrite&pGUBUN=edit&MENUNO=0"><font class="lg_name"> <%= UserBroker.getUserName(request)%>님</font></a> <!-- BLOCK <a href="<%=CONTEXTPATH%>/Message.cmd?cmd=receiveMessagePagingList&MENUNO=0" onfocus="blur();"><u>쪽지(<%=UnReadCnt%>)통</u></a> --> </td>
							</tr>
							<tr>
								<td height="18" align="center"><a href="<%=CONTEXTPATH%>/Main.cmd?cmd=userLogOff"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_quit.gif"></a> <!-- BLOCK 2013-04-05 <a href="<%=CONTEXTPATH%>/User.cmd?cmd=userWrite&pGUBUN=edit&MENUNO=0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_infor.gif"></a> --></td>
							</tr>
							<tr> 
								<td height="3"></td>
							</tr>
<%
	}
%>
</form>
						</table>

<!-- BLOCK
						<table width="153" align="center" cellpadding="0" cellspacing="0">
							<tr> 
								<td>
//BLOCK -->
									<!-- left menu 시작 -->
									<%//@include file="course_left.jsp" %>
									<!-- left menu 끝 -->
<!-- BLOCK
								</td>
							</tr>
							<tr valign="top">
								<td height="36" class="c_left_exit"><a href="<%=myEdaLink%>"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_classroom_exit.gif" width="153" height="39" border="0" alt="강의실 나가기"></a></td>
							</tr>
						</table>
//BLOCK -->
									<!-- left menu 시작 -->
					</td>
<!--ADD 2012-11 (BEGIN) -->
</tr>
<tr>
<!--ADD 2012-11 (END) -->
					<!-- // 좌측부분 (로고, 로그인, 입학신청, 메뉴, 배너 -->
					<!-- 컨텐츠 -->
					<td width="680"> 
					<!-- 과정명 -->
<!-- BLOCK
						<table width="680" height="35" align="center"cellpadding="0" cellspacing="0">
							<tr valign="top">
								<td class="flash_pd"><script>var ff=lg("<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/swf/login01_mv.swf", "Mv", 680, 37, '');documentwrite(ff);</script></td>
							</tr>
						</table>
//BLOCK -->

						<table width="680" height="100%" align="center">
