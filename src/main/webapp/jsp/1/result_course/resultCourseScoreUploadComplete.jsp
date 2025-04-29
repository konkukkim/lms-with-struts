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
<table border=0 cellpadding=5 cellspacing=1 width=96% align='center'>
	<tr>
        <td class=hd1 colspan=2 height='100'></td>
    </tr>
	<tr>
        <td align=right>Result:</td>
       <%if(retVal > 0){%>
			<td align=left>정상 처리 되었습니다.</td>
		<%}else{%>
			<td align=left>성적처리에 문제가 발생하였습니다. <br> 처음부터 다시 진행해 주세요 <br> 지속적인 문제 발생시 관리자에게 문의해 주세요.</td>
		<%}%>
        
    </tr>
    <tr>
        <td class=hd1 colspan=2 height='100'></td>
    </tr>
    <tr>
        <td class=hd1 colspan=2 align=center><input type=button class=bt1 value=" close " onClick="cancel_page()"></td>
    </tr>	
</table>