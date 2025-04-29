<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.poll.dto.InternetPollDTO"%>
<%@include file="../common/popup_header.jsp" %>
<%
	Map data = (Map)request.getAttribute("MODEL"); 
	String pPollId  = (String)model.get("pPollId");

	InternetPollDTO pollView = (InternetPollDTO)data.get("pollShow"); 
	String subject = pollView.getSubject();
	int hitcnt  = pollView.getHitCnt();

	String chk1				=	"";
	String chk2				=	"";
	String chk3				=	"checked";

	//실시중
	if(pollView.getStatus().equals("Y")) {
		chk1				=	"checked";
		chk2				=	"";
		chk3				=	"";	
	//대기중
	}else if(pollView.getStatus().equals("N")) {
		chk1				=	  "";
		chk2				=	"checked";
		chk3				=	"";
	}
%>
<table width="370" border="0" cellspacing="0" cellpadding="0" height="300">
<form name=Input method="post" action="<%=CONTEXTPATH%>/Poll.cmd?cmd=pollStatRegist">
<input type='hidden' name ='pPollId' value='<%=pPollId%>'>
  <tr align="center"> 
    <td height="60"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img320.gif" width="312" height="31"></td>
  </tr>
  <tr> 
    <td align="center" bgcolor="#FDFBF3" valign="middle" height="138"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr valign="top"> 
          <td width="10" height="20">&nbsp;</td>
          <td colspan="3" height="20">*인터넷 투표 상태를 변경합니다.</td>
        </tr>
        <tr> 
          <td height="2" colspan="4" bgcolor="#91C694"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="2"></td>
        </tr>
        <tr> 
          <td width="5" align="left" height="30">&nbsp;</td>
          <td width="45" align="left" height="30"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img303.gif" width="22" height="11" vspace="5"></td>
          <td width="5" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" vspace="5" hspace="5"></td>
          <td width="220"> <%=subject%></td>
        </tr>
        <tr> 
          <td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_bgimg.gif" colspan="4" height="2"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
        </tr>
        <tr> 
          <td align="left" height="30">&nbsp;</td>
          <td align="left" height="30"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img321.gif" width="21" height="12" vspace="5"></td>
          <td align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" vspace="5" hspace="5"></td>
          <td> 
            <input type="radio" name="pStatus" value="Y" <%=chk1%>>실시
				<%
				if (hitcnt == 0) {
				%>
							<input name="pStatus" type=radio value="N" <%=chk2%>><font color=red>대기</font>
				<%
				}
				else {
				%>
							<input name="pStatus" type=radio value="E" <%=chk3%>><font color=#339900>종료</font>
				<%
				}
				%>
        </tr>
        <tr> 
          <td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_bgimg.gif" colspan="4" height="2"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
        </tr>
        <tr align="center"> 
          <td height="30">&nbsp;</td>
          <td colspan="3" align="left" class="brown1">
				<%
				if (hitcnt == 0) {
				%>
							
							대기상태를 실시상태로 변경하면 이미 실시중이던 투표는 <br>중지됩니다.
				<%
				}
				else {
				%>
							인터넷투표를 종료하면 다시 변경할 수 없습니다.
				<%
				}
				%>
            </td>
        </tr>
        <tr align="center"> 
          <td height="42"><br>
          </td>
          <td colspan="3" height="32" align="center"><input type="image" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/ok01.gif" width="56" height="21"></a></td>
        </tr>
        <tr> 
          <td height="2" bgcolor="#91C694" colspan="4" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="2"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
    <td align="center" height="34"><a href="javascript:window.close();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/close.gif" width="68" height="18"></a></td>
  </tr>
  </form>
</table>


<%@include file="../common/popup_footer.jsp" %>