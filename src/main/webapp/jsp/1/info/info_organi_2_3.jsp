<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map,java.util.ArrayList"%>
<%@ page import ="com.edutrack.common.UserBroker,com.edutrack.framework.util.DateTimeUtil,com.edutrack.framework.util.StringUtil"%>
<%@ page import ="com.edutrack.common.CommonUtil,com.edutrack.common.SiteNavigation"%>
<%
	String CONTEXTPATH = request.getContextPath();

    Map model = (Map)request.getAttribute("MODEL");
    String PMODE = "";
    String DATEYN = "";

    if(model != null) {
    	PMODE   = (String)model.get("pMode");
    	DATEYN = (String)model.get("dateyn");
    }
    String SYSTEMCODE   = UserBroker.getSystemCode(request);
    String USERID       = UserBroker.getUserId(request);
    String USERTYPE     = UserBroker.getUserType(request);
%>
<HTML>
<HEAD>
	<TITLE>*개교기념식*</TITLE>
	<META http-equiv=Content-Type content="text/html; charset=ks_c_5601-1987">
<STYLE type=text/css>
	.unnamed1 {	FONT-SIZE: 9pt; COLOR: #000000; LINE-HEIGHT: 11pt; FONT-FAMILY: "돋움"}
</STYLE>

<SCRIPT language=javascript>
<!--//
	function openF(theURL,winName,features) {
		window.open(theURL,winName,features);
	}
//-->
</SCRIPT>
<script language="JavaScript">
	function closeWin()
	{ 
		var todayDate = new Date();
		todayDate.setDate( todayDate.getDate() + 1 );
		document.cookie = "popup=noshow; path=/; expires="+todayDate.toGMTString()+";";
		self.close();
	}
</script>

<META content="MSHTML 6.00.2900.3199" name=GENERATOR></HEAD>
<BODY bgColor=#ffffff leftMargin=0 topMargin=0>
<TABLE cellSpacing=0 cellPadding=0 width=355 align=center background=<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/pop_bg.gif border=0>
	<TBODY>
	<TR>
		<TD colSpan=4 height=117>
			<DIV align=center><IMG height=118 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/pop_1.gif"></DIV>
		</TD>
	</TR>
	<TR>
		<TD width=25 rowSpan=29>&nbsp;</TD>
		<TD class=unnamed1 colSpan=2 height=75>
			<P><B>어서 오십시오 !</B></P>
			<P>지금부터 전태일을 따르는 사이버 노동대학의 개교 기념식을 시작하겠습니다. 순서 옆에 있는 다운받기 단추를 누르면 동화상 및 텍스트를 보실 수 있습니다.</P>
			<DIV align=center><A href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/pop2/open.asf"></A></DIV>
		</TD>
		<TD width=25 rowSpan=29>
			<P>&nbsp;</P>
			<P>&nbsp;</P></TD></TR>
	<TR>
		<TD width=265 height=17><IMG height=12 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/1.gif" width=12><FONT size=2><B>개회선언 및 민중의례</B></FONT></TD>
		<TD class=unnamed1 width=50 height=17><A href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/pop2/open.asf"><IMG height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/i_lan.gif" width=49 border=0></A></TD>
	</TR>
	<TR>
		<TD class=unnamed1 width=265 height=20>&nbsp;&nbsp;&nbsp;사회 <FONT color=#3333cc>박찬식</FONT></TD>
		<TD class=unnamed1 width=50 height=20>
		<DIV align=center></DIV></TD>
	</TR>
	<TR>
		<TD width=265 height=20><IMG height=12 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/1.gif" width=12><FONT size=2><B>대학 설립 경과보고</B></FONT></TD>
		<TD class=unnamed1 width=50 height=20>
			<DIV align=center><A href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/pop2/progress.asf" target=_new><IMG height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/i_lan.gif" width=49 border=0></A></DIV></TD>
	</TR>
	<TR>
		<TD class=unnamed1 width=265 height=20>&nbsp;&nbsp;&nbsp;<FONT 	color=#3333cc>박찬식</FONT>(사이버 노동대학 설립추진위원회)</TD>
		<TD class=unnamed1 width=50 height=20>
		<DIV align=center></DIV></TD></TR>
	<TR>
		<TD width=265 height=20><IMG height=12 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/1.gif" width=12><FONT size=2><B>총장 인사말</B></FONT></TD>
		<TD class=unnamed1 width=50 height=20>
			<DIV align=center><A href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/pop2/intro.asf"><IMG height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/i_lan.gif" width=49 border=0></A></DIV></TD></TR>
	<TR>
		<TD class=unnamed1 width=265 height=20>&nbsp;&nbsp;&nbsp;<FONT color=#3333cc>김수행</FONT>(서울대 교수)</TD>
		<TD class=unnamed1 width=50 height=20>
			<DIV align=center></DIV></TD></TR>
	<TR>
		<TD width=265 height=20><B><IMG height=12 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/1.gif" width=12><FONT size=2>격려사</FONT></B></TD>
		<TD class=unnamed1 width=50 height=20>
			<DIV align=center><A href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/pop2/lee.asf"><IMG height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/i_lan.gif" width=49 border=0></A></DIV></TD></TR>
	<TR>
		<TD class=unnamed1 width=265 height=20>&nbsp;&nbsp;&nbsp;<FONT 
		color=#3333cc>이소선</FONT> 어머니</TD>
		<TD class=unnamed1 width=50 height=20>
			<DIV align=center></DIV></TD></TR>
	<TR>
		<TD width=265 height=20><B><IMG height=12 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/1.gif" 
		width=12><FONT size=2>축사</FONT></B></TD>
		<TD class=unnamed1 width=50 height=20>
			<DIV align=center></DIV></TD></TR>
	<TR>
		<TD class=unnamed1 width=265 height=20>&nbsp;&nbsp;&nbsp;<FONT 
		color=#3333cc>단병호</FONT>(민주노총 위원장)</TD>
		<TD class=unnamed1 width=50 height=20>
			<DIV align=center><A href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/pop2/con_dan.asf"><IMG height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/i_lan.gif" width=49 border=0></A></DIV></TD></TR>
	<TR>
		<TD class=unnamed1 width=265 height=20>&nbsp;&nbsp;&nbsp;<FONT 
		color=#3333cc>권영길</FONT>(민주노동당 대표)</TD>
		<TD class=unnamed1 width=50 height=20>
			<DIV align=center><A href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/pop2/con_geun.asf"><IMG height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/i_lan.gif" width=49 border=0></A></DIV></TD></TR>
	<TR>
		<TD class=unnamed1 width=265 height=20>&nbsp;&nbsp;&nbsp;<FONT 
		color=#3333cc>정광훈</FONT>(전농 의장)</TD>
		<TD class=unnamed1 width=50 height=20>
			<DIV align=center><A href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/pop2/con_jung.asf"><IMG height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/i_lan.gif" width=49 border=0></A></DIV></TD></TR>
	<TR>
		<TD class=unnamed1 width=265 height=20>&nbsp;&nbsp;&nbsp;<FONT 
		color=#3333cc>오종렬</FONT>(전국연합 의장)</TD>
		<TD class=unnamed1 width=50 height=20>
			<DIV align=center><A href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/pop2/con_oh.asf"><IMG height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/i_lan.gif" width=49 border=0></A></DIV></TD></TR>
	<TR>
		<TD class=unnamed1 width=265 height=20>&nbsp;&nbsp;&nbsp;<FONT 
		color=#3333cc>이종린</FONT>(범민련 의장)</TD>
		<TD class=unnamed1 width=50 height=20>
			<DIV align=center><A href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/pop2/con_lee.asf"><IMG height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/i_lan.gif" width=49 border=0></A></DIV></TD></TR>
	<TR>
		<TD class=unnamed1 width=265 height=20>&nbsp;&nbsp;&nbsp;<FONT 
		color=#3333cc>백기완</FONT> 선생</TD>
		<TD class=unnamed1 width=50 height=20>
			<DIV align=center><A href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/pop2/con_paek.asf"><IMG height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/i_lan.gif" width=49 border=0></A></DIV></TD></TR>
	<TR>
		<TD width=265 height=20><B><IMG height=12 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/1.gif" 
		width=12><FONT size=2>축시&nbsp;</FONT> <FONT class=unnamed1>-영원한 길에 
		대해</FONT></B></TD>
		<TD class=unnamed1 width=50 height=20>
			<DIV align=center><A href="javascript:openF('<%=CONTEXTPATH%>/data/migrationData/intro_movie/pop2/pop5.htm','poem','scrollbars=yes,width=620,height=600');"><IMG height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/i_lan.gif" width=49 border=0></A></DIV></TD></TR>
	<TR>
		<TD class=unnamed1 width=265 height=20>&nbsp;&nbsp;&nbsp;<FONT 
		color=#3333cc>오철수</FONT>(시인)</TD>
		<TD class=unnamed1 width=50 height=20>
			<DIV align=center></DIV></TD></TR>
	<TR>
		<TD width=265 height=20><B><IMG height=12 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/1.gif" 
		width=12><FONT size=2>축가&nbsp;</FONT>- <FONT 
		class=unnamed1>노동자라면</FONT></B></TD>
		<TD class=unnamed1 width=50 height=20>
			<DIV align=center><A href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/pop2/song.asf"><IMG height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/i_lan.gif" width=49 border=0></A></DIV></TD></TR>
	<TR>
		<TD class=unnamed1 width=265 height=20>&nbsp;&nbsp;&nbsp;<FONT 
		color=#3333cc>박준</FONT>(민중가수)</TD>
		<TD class=unnamed1 width=50 height=20>
			<DIV align=center></DIV></TD></TR>
	<TR>
		<TD width=265 height=20><B><IMG height=12 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/1.gif" 
		width=12><FONT size=2>학생대표 결의 발표</FONT></B></TD>
		<TD class=unnamed1 width=50 height=20>
			<DIV align=center></DIV></TD></TR>
	<TR>
		<TD class=unnamed1 width=265 height=20>&nbsp;&nbsp;&nbsp;<FONT 
		color=#3333cc>백생학</FONT>(서울지하철 노조 승무구로지회장) </TD>
		<TD class=unnamed1 width=50 height=20>
			<DIV align=center><A href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/pop2/student.asf"><IMG height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/i_lan.gif" width=49 border=0></A></DIV></TD></TR>
	<TR>
		<TD class=unnamed1 width=265 height=18>&nbsp;&nbsp;&nbsp;<FONT 
		color=#3333cc>김지찬</FONT>(부산지역 일반노동조합 조합원)</TD>
		<TD class=unnamed1 width=50 height=18>
			<DIV align=center><A href="javascript:openF('<%=CONTEXTPATH%>/data/migrationData/intro_movie/pop2/pop2.htm','answer','scrollbars=no,width=600,height=607');"><IMG height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/i_modem.gif" width=49 border=0></A></DIV></TD></TR>
	<TR>
		<TD class=unnamed1 height=18>&nbsp;&nbsp;&nbsp;<FONT 
		color=#3333cc>정병옥</FONT>(구미 한국합섬 노동조합 사무부장)</TD>
		<TD class=unnamed1 height=18>
			<DIV align=center><A href="javascript:openF('<%=CONTEXTPATH%>/data/migrationData/intro_movie/pop2/pop3.htm','answer1','scrollbars=no,width=600,height=410');"><IMG height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/i_modem.gif" width=49 border=0></A></DIV></TD></TR>
	<TR>
		<TD class=unnamed1 height=21>&nbsp;</TD>
		<TD class=unnamed1 height=21>&nbsp;</TD></TR>
	<TR>
		<TD class=unnamed1 height=20>
			<DIV align=right><FONT color=#006633>진보적 지식인 설립추진위원 명단</FONT>&nbsp;&nbsp;&nbsp;</DIV></TD>
		<TD class=unnamed1 height=20><A href="javascript:openF('<%=CONTEXTPATH%>/data/migrationData/intro_movie/pop2/pop4.htm','answer1','scrollbars=yes,width=620,height=600');"><IMG height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/i_modem.gif" width=49 border=0></A></TD></TR>
	<TR>
		<TD class=unnamed1 height=20>
			<DIV align=right><FONT color=#006600 size=2>감사의 글</FONT>&nbsp;&nbsp;&nbsp;<FONT color=#333300><B><FONT size=2> </FONT></B></FONT></DIV></TD>
		<TD class=unnamed1 height=20><A href="javascript:openF('<%=CONTEXTPATH%>/data/migrationData/intro_movie/pop2/pop6.htm','thank','scrollbars=no,width=600,height=137');"><IMG height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/i_modem.gif" width=49 border=0></A></TD></TR>
	<TR>
		<TD class=unnamed1 colSpan=2 height=20>&nbsp;</TD></TR>
	<TR>
		<TD colspan="2" class=unnamed1 align=right height=20><input type="checkbox" name="Notice" OnClick="javascript:closeWin();"  style="border:0"> <FONT color=red>오늘 하루 이 창 닫기 &nbsp;&nbsp;</FONT></TD>
	</TR>
	<TR>
		<TD colSpan=4>
			<DIV align=center><IMG height=38 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/pop_2.gif" useMap=#Map border=0><MAP name=Map><AREA shape=RECT coords=373,2,412,21 href="javascript:self.close();"></MAP></DIV>
		</TD>
	</TR>
	</TBODY>
</TABLE>

</BODY>
</HTML>
