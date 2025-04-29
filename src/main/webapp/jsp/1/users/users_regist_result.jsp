<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr"    %>
<%@ page import="java.io.*, java.lang.*, java.util.*, java.net.*" %>
<%@ page import ="com.edutrack.common.UserBroker, com.edutrack.framework.util.DateTimeUtil, com.edutrack.framework.util.StringUtil"%>
<%!
   String a2k(String s){
		String s2=null;
		try{
			if(s==null) 
				s2=null;
			else
				s2 = new String(s.getBytes("8859_1"),"euc-kr");
		}catch(Exception e){
		
		}
		return s2;
	}
%>
<%@include file="../common/header.jsp" %>
<%
	String pMemberGb	= (String)model.get("pMemberGb");
	String pUserId		= (String)model.get("pUserId");
	String pUserName	= (String)model.get("pUserName");
	String pUserPass 	= (String)model.get("pUserPass");
	String pUserEMail 	= (String)model.get("pUserEMail");
	String pRecvSms 	= (String)model.get("pRecvSms");
	String pPhoneHand 	= (String)model.get("pPhoneHand");
%>
<style type="text/css">
<!--
td.line {font-size:1pt; font-family: 굴림;line-height:1px;}
font {font-size:9pt; font-family: 굴림;}
.line {font-size:1px;line-height:1px;}
-->
</style>
<Script>
function goHome(){
	location.href="<%=CONTEXTPATH%>/Main.cmd?cmd=mainShow&pMode=Home";
}

function goLogin(){
	location.href="<%=CONTEXTPATH%>/User.cmd?cmd=usersLoginShow&pMode=Home";
}
</Script>

							
							<table border=0 cellpadding=0 cellspacing=0 width=500 align="center">
							<tr class=line bgcolor=#E6E6E6>
								<td width=1 class=line>&nbsp;</td>
								<td width=25 class=line>&nbsp;</td>
								<td width=13 class=line>&nbsp;</td>
								<td width=7   class=line>&nbsp;</td>
								<td width=400 class=line>&nbsp;</td>
								<td width=13 class=line>&nbsp;</td>
								<td width=24 class=line>&nbsp;</td>
								<td width=1 class=line>&nbsp;</td>
							</tr>
							<tr height=30>
								<td width=1 class=line bgcolor=#E6E6E6 rowspan=12>&nbsp;</td>
								<td colspan=5 align=right></td>
								<td class=line>&nbsp;</td>
								<td width=1 class=line bgcolor=#E6E6E6 rowspan=12>&nbsp;</td>
							</tr>
							<tr>
								<td rowspan=5 class=line>&nbsp;</td>
								<td colspan=4 class=line height=10>&nbsp;</td>
								<td rowspan=5 class=line>&nbsp;</td>
							</tr>
							<tr height=46>
								<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/newmail_smallleft_1.gif"></td>
								<td colspan=2 align=center bgcolor=#F1F1F1><b>"입학신청 승인 결과입니다."</b></td>
								<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login//newmail_smallright_1.gif"></td>
							</tr>
							<tr height=20>
								<td rowspan=2 colspan=2 class=line>&nbsp;</td>
								<td colspan=2 class=line>&nbsp;</td>
							</tr>
							<tr>
								<td colspan=2 align=left>
									<center><font color=#585858><b>아이디 :	<strong><font color="#0066CC"><%=pUserId%></font></strong><br>
									패스워드:<strong><font color="#FF3300"><%=pUserPass%></font></strong></b></font></center>
									<br><br><center><font color=#585858><b>입학신청이 승인되었습니다.<br>감사합니다.</b></font></center>
								</td>
							</tr>
							<tr><td colspan=4 height=30>&nbsp;</td></tr>
							<tr><td class=line colspan=6 height=1 bgcolor=#E6E6E6>&nbsp;</td></tr>
							<tr><td class=line colspan=6 height=10 bgcolor=#F1F1F1>&nbsp;</td></tr>
							<tr><td class=line colspan=6 height=1 bgcolor=#E6E6E6>&nbsp;</td></tr>
						</table>


            			<table width="100%" height="70" border="0" cellpadding="0" cellspacing="0">
              				<tr>
                				<td align="center" valign="middle">
                					<script language=javascript>Button3("홈으로", "goHome()", "");</script>&nbsp;
               				
                				</td>
              				</tr>
            			</table>


									</td>
								</tr>
							</table>
<%@include file="../common/footer.jsp" %>