<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet" %>
<%@ page import ="com.edutrack.framework.util.DateTimeUtil"%>
<%@ page import ="com.edutrack.framework.util.StringUtil" %>
<%@ page import ="com.edutrack.common.CommonUtil" %>
<%@ page import ="java.lang.*,java.util.*,java.util.ArrayList"%>
<%
String CONTEXTPATH = "http://"+request.getServerName()+request.getContextPath();
%>

<%
	Map model = (Map)request.getAttribute("MODEL");
	ArrayList list = (ArrayList)model.get("cerificationPrn");
	Iterator    it      = null;
	HashMap     hjHm    = null;

	String curriYear = (String)model.get("pCurriYear");
	String curriTerm = (String)model.get("pCurriTerm");

	String sCertNo		  = "";
	String sUserNm        = "" ;
	String sProfNm        = "" ;
	String sCurriNm       = "" ;
	String sCompleteEnd   = "" ;
	String sServiceStart  = "" ;
	String sServiceEnd    = "" ;
	String curriType 	  = "";
	String juminNo		  = "";
	String certStr = "이수";
	String certImg = "cert_medi.jpg" ; //"cert1.jpg";

	it = list.iterator();

	while(it.hasNext())
	{
	    hjHm = (HashMap)it.next();
		curriType         = StringUtil.nvl( hjHm.get("CURRI_TYPE"));
	//	sCertNo         = StringUtil.nvl( hjHm.get("CERT_NO"));
	    sUserNm         = StringUtil.nvl( hjHm.get("user_name"));
	    sProfNm         = StringUtil.nvl( hjHm.get("prof_name"));
	    sCurriNm        = StringUtil.nvl( hjHm.get("CURRI_NAME"));
	    sServiceStart   = StringUtil.nvl( hjHm.get("SERVICESTART_DATE"));
	    sServiceEnd     = StringUtil.nvl( hjHm.get("SERVICEEND_DATE"));
	    juminNo		     = StringUtil.nvl( hjHm.get("jumin_no"));
	}
/*
int tmpCertNoLength = sCertNo.length();
for(int i=0;i<(4-tmpCertNoLength);i++)
	sCertNo="0"+sCertNo;
sCertNo = "KIDP-"+curriYear.substring(2,4)+"-"+sCertNo;
*/
if(curriType.equals("R")){
	certStr = "수료";
	sCurriNm = sCurriNm + " ["+curriYear+"년 "+curriTerm+"기]";
	//certImg = "cert.jpg";
}
//if(sCompleteEnd.equals(""))
	sCompleteEnd = CommonUtil.getCurrentDate();
%>

<html>
<head>
<title>::: <%=certStr%>증 :::</title>
<Link Rel="stylesheet" Href="./css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<Style media="screen">
.name01 { font-size : 15px; }
.body01 { font-size : 16px; }
.title03{ font-size : 20px; }
.date01 { font-size : 15px; }
table.tb { display:; }
</Style>
<Style media="print">
.name01 { font-size : 15px; }
.body01 { font-size : 16px; }
.title03{ font-size : 20px; }
.date01 { font-size : 15px; }
table.tb { display:none; }
</Style>
<Script Language="JavaScript" src = "<%=CONTEXTPATH%>/js/common1.js"></script>
<script language=javascript src="<%=CONTEXTPATH%>/js/common_util.js"></script>
<script language=javascript src="<%=CONTEXTPATH%>/js/common_button.js"></script>
<Script>

function SetPrint( )
{
  IEPrint.left        = 0 ;
  IEPrint.right       = 0 ;
  IEPrint.top         = 30 ;
  IEPrint.bottom      = 0 ;
  IEPrint.header      = '';
  IEPrint.footer      = '';
  IEPrint.printbg     = true;
  IEPrint.landscape   = false ;
  IEPrint.paper       = 'A4';
  IEPrint.Preview() ;
}


</Script>
</head>

<!-- 프린트용 오브젝트(body tag 안에서 삽입시킴)  -->
<OBJECT ID="IEPrint" WIDTH=1 HEIGHT=1 CLASSID="CLSID:F290B058-CB26-460E-B3D4-8F36AEEDBE44"
codebase="<%=CONTEXTPATH%>/data/printX/IEPrint.cab#version=1,0,1,1">
</OBJECT>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<BR>
<table width="100%" class=tb border="0" cellpadding="0" cellspacing="1" align=center >
<tr>
    <Td>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <Script>Button5("<%=certStr%>증출력" ,"SetPrint()","")</Script>&nbsp;
        <Script>Button5("닫기" ,"self.close()","")</Script>
    </td>
</tr>
</table>
<BR>

<table width="100%" border="0" cellspacing="0" cellpadding="10"  align=center valign=center>
  <tr>
    <td align="center" valign="middle">
    <table width="500" border="0" cellpadding="0" cellspacing="1" bgcolor="#000000"  >
        <tr>
          <td valign="top" bgcolor="#FFFFFF">
			<table width="100%" height="688" border="0" cellpadding="0" cellspacing="0" >
              <tr>
                <td height="300"  class=title03 valign=top>
                <img src="/img/1/pop/<%=certImg%>" width="540" height="750">
                  <!--table><tr><td height=33></td></tr></table>
                  
                  <table  border="0" cellspacing="0" cellpadding="0" align="left">
                    <tr>
                      <td height="25" colspan="3" class="name01">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="#000000" face="바탕, 궁서, AppleMyungjo" style="font-size=9pt;"><strong>2007-01-00001</strong></font></td>
                    </tr>
                   </table>
                   <table><tr><td height=15></td></tr></table>
                  <table  border="0" cellspacing="0" cellpadding="0" align="right">
                    <tr>
                      <td height="107" colspan="3" class="name01">&nbsp;</td>
                    </tr>
                    <tr>
                      <td height="25" colspan="3" class="name01"><font color="#000000" size="2" face="바탕, 궁서, AppleMyungjo">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: <strong> <%= sUserNm %></strong></font></td>
                    </tr>
                    <tr>
                      <td height="25" colspan="3" class="name01"><font color="#000000" size="2" face="바탕, 궁서, AppleMyungjo"><strong>주민번호 : <%= juminNo %></strong></font></td>
                    </tr>
                    <tr>
                      <td height="25" colspan="3" class="name01"><font color="#000000" size="2" face="바탕, 궁서, AppleMyungjo"><strong>과 정 명 : <%= sCurriNm %></strong></font>&nbsp;&nbsp;</td>
                    </tr>
                    <tr>
                      <td height="25" colspan="3" class="name01"><font color="#000000" size="2" face="바탕, 궁서, AppleMyungjo"><strong>수강기간 : <%= DateTimeUtil.getDateType(1, sServiceStart) %> ~ <%= DateTimeUtil.getDateType(1, sServiceEnd) %></strong></font></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="87%" height="80" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                      <td height=210 align="center" valign="top" class="date01">&nbsp;</td>
                    </tr>
                    <tr>
                      <td valign="top" align="center"><font size="4" color="black"><strong><%= DateTimeUtil.getDateType(1, sCompleteEnd)%></strong></font></td>
                    </tr>
                    <tr>
                      <td height="80" valign="bottom" align="center"><font size="6" color="black" face="고딕, 바탕, 궁서, AppleMyungjo"><strong>(주) 메 디 오 피 아 테 크</font></td>
                    </tr>
                  </table-->
                  </td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
<Script>//SetPrint();</Script>