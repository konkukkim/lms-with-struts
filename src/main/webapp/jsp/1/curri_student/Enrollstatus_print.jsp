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
String certImg = "cert3.jpg";
/*추가*/
String juminNo	=	"";
String price	=	"";


it = list.iterator();

while(it.hasNext())
{
    hjHm 			= (HashMap)it.next();
	curriType       = StringUtil.nvl( hjHm.get("CURRI_TYPE"      ).toString() );
    sUserNm         = StringUtil.nvl( hjHm.get("USER_NAME"      ).toString() );
    sProfNm         = StringUtil.nvl( hjHm.get("PROF_NAME"      ).toString() );
    sCurriNm        = StringUtil.nvl( hjHm.get("CURRI_NAME"     ).toString() );
    sServiceStart   = StringUtil.nvl( hjHm.get("SERVICESTART_DATE"  ).toString() );
    sServiceEnd     = StringUtil.nvl( hjHm.get("SERVICEEND_DATE"    ).toString() );
    juminNo			= StringUtil.nvl( hjHm.get("JUMIN_NO"    ).toString() );
    price			= StringUtil.nvl( hjHm.get("PRICE"    ).toString() );
}
int	priceA			= Integer.parseInt(price);

if(curriType.equals("R")){
	sCurriNm = sCurriNm + " ["+curriYear+"년 "+curriTerm+"기]";
	certImg = "cert3.jpg";
}
//if(sCompleteEnd.equals(""))
	sCompleteEnd = CommonUtil.getCurrentDate();

%>
<html>
<head>
<title>::: 수강신청확인서 :::</title>
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
        <input type=button Style="border:1px solid black;" value="수강신청확인서출력" onClick="javascipt:SetPrint()">
        <input type=button Style="border:1px solid black;" value="닫기" onClick="javascipt:self.close()">
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
			<table width="100%" height="688" border="0" cellpadding="0" cellspacing="0"  background="/img/1/popup/<%=certImg%>">
              <tr>
                <td height="300"  class=title03 valign=top>
                  <br>
                  <br>
                  <br>
                  <table  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="30" colspan="3" class="name01">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="#000000" size="2" face="바탕, 궁서, AppleMyungjo"><strong></strong></font></td>
                    </tr>
                    <tr>
                      <td height="85" colspan="3" class="name01">&nbsp;</td>
                    </tr>
                    <tr>
                      <td height="25" colspan="3" class="name01">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="#000000" size="2" face="바탕, 궁서, AppleMyungjo"><strong><%= sUserNm %></strong></font></td>
                    </tr>
                    <tr>
                      <td height="25" colspan="3" class="name01">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="#000000" size="2" face="바탕, 궁서, AppleMyungjo"><strong><%=juminNo%></strong></font></td>
                    </tr>
                    <tr>
                      <td height="25" colspan="3" class="name01">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="#000000" size="2" face="바탕, 궁서, AppleMyungjo"><strong><%= sCurriNm %></strong></font></td>
                    </tr>
                    <tr>
                      <td height="25" colspan="3" class="name01">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="#000000" size="2" face="바탕, 궁서, AppleMyungjo"><strong><%= DateTimeUtil.getDateType(1, sServiceStart) %> ~ <%= DateTimeUtil.getDateType(1, sServiceEnd) %></strong></font></td>
                    </tr>
                    <tr>
                      <td height="25" colspan="3" class="name01">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="#000000" size="2" face="바탕, 궁서, AppleMyungjo"><strong><%=StringUtil.getMoneyType(priceA)%></strong></font></td>
                    </tr>

                  </table>
                </td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="87%" height="80" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                      <td height=210 align="center" valign="top" class="date01" colspan=8>&nbsp;</td>
                    </tr>
                    <tr>
                      <td width=162></td>
                      <td width=50><font color="#000000" size="2" face="바탕, 궁서, AppleMyungjo"><strong><%= DateTimeUtil.getDateType(1, sCompleteEnd).substring(0,4) %></strong></font></td>
                      <td width=32><font color="#000000" size="2" face="바탕, 궁서, AppleMyungjo"><strong><%= DateTimeUtil.getDateType(1, sCompleteEnd).substring(5,7) %></strong></font></td>
                      <td width=20><font color="#000000" size="2" face="바탕, 궁서, AppleMyungjo"><strong><%= DateTimeUtil.getDateType(1, sCompleteEnd).substring(8,10) %></strong></font></td>
                      <td valign="top" class="date01">
                      </td>
                    </tr>
                  </table>
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
