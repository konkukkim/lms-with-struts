<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/popup_header.jsp" %>
<%
	int retVal = Integer.parseInt((String)model.get("retVal"));
%>
<script language=javascript>
    function cancel_page() {
	    top.opener.document.location.reload();
		top.window.close();
    }
</script>
<center>
<table width="450" border="0" cellspacing="0" cellpadding="0" height="442">
  <tr align="center"> 
    <td colspan="5" height="61"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img140.gif" width="401" height="31"></td>
  </tr>
<tr>
	<td align=right>Result: &nbsp;</td>
   <%if(retVal > 0){%>
		<td align=left> 정상 업로드 되었습니다.</td>
	<%}else{%>
		<td align=left>업로드에 문제가 발생하였습니다. <br> 처음부터 다시 진행해 주세요 <br> 지속적인 문제 발생시 관리자에게 문의해 주세요.</td>
	<%}%>
	
</tr>
  <tr>
    <td valign="middle" height="40" align="center" colspan="5"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/ok01.gif" width="56" height="21" border="0" hspace="5"  onClick="cancel_page()" style="cursor:hand"></td>
  </tr>
  <tr> 
    <td colspan="5" bgcolor="AAD5AD" height="2"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
  </tr>
</table>