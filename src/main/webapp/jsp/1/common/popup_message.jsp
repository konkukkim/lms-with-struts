<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/popup_header.jsp" %>
<%
	String mode=(String)model.get("ALERT_MODE");
	String type=(String)model.get("MSG_TYPE");
	String alert=(String)model.get("ALERT");
	String url=(String)model.get("URL");
	String param=(String)model.get("PARAM");
	String go=(String)model.get("GO");
	String close=(String)model.get("CLOSE");
	String message=(String)model.get("MSG");
	String reload=(String)model.get("RELOAD");
	String exception=(String)model.get("EXCEPTION");

	url = CONTEXTPATH+url;
%>
<% if( alert.equals("true") && !message.equals("")) { %>
<script language="javascript">
		var msg = "<%= message %>";
		alert(msg);
</script>
<% }%>

<% if( mode.equals("popup") && reload.equals("true")) { %>
<script language="javascript">
     <% if(param.equals("P")) { %>
          <% if(url.equals(CONTEXTPATH)){%>
	     	 parent.location.reload();
     	  <% }else{%>
			 parent.location.href ="<%=url%>";
		  <% } %>
	<%  }else if(param.equals("M")) { %>
          <% if(url.equals(CONTEXTPATH)){%>
	     	 top.dialogArguments.location.reload();
     	  <% }else{%>
			 top.dialogArguments.location.href ="<%=url%>";
		  <% } %>
	<% }else{ %>
          <% if(url.equals(CONTEXTPATH)){%>
	     	 opener.location.reload();
     	  <% }else{%>
			 opener.location.href ="<%=url%>";
		  <% } %>
     <% } %>
</script>
<% } %>

<% if( mode.equals("popup") && close.equals("true")) { %>
<script language="javascript">
	this.close();
</script>
<% } %>

<% if( mode.equals("confirm")) { %>
<script language="javascript">
	var truthBeTold = window.confirm("<%=message%>");
	if (truthBeTold) {
	   window.location.href ="<%=url%>";
	}else{
       window.location.href ="<%=param%>";
    }
</script>
<% } %>

<% if( mode.equals("history")) { %>
<script language="javascript">
  <% if(!url.equals(CONTEXTPATH)){%>
      window.location.href ="<%=url%>";
  <% }else{ %>
      history.go(-1);
  <% }%>
</script>
<% } %>

<% if( mode.equals("action") && url.equals("")) { %>
<form action=<%= url%> method="get">
</form>
<script language="javascript">
	document.forms[0].submit();
</script>
<% } %>



<% if( mode.equals("message")) {
   String msg2 = "";
   String msg = message;
%>




<table  height="100%" width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#DBDBDB">
  <tr>
    <td  align="center" bgcolor="#FFFFFF" valign="top" >
      <!--둘러싸고 있는 테이블과 안의 테이프 width값 차이 4px -->
      <table  width="98%" border="0" cellspacing="0" cellpadding="0" valign="top">
        <tr>
          <td width="100%" height="45" align="center"  valign="middle" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/com_bg.gif"><!-- Title --><font color="#FFFFFF" size="3"><strong>메세지</strong></font></td>
        </tr>
         <tr>
          <td align="right" height="10">&nbsp;</td>
        </tr>
        <tr>
          <td align="center">
            <!-- Contents Start-->
					<table width="85%" border="0" cellspacing="0" cellpadding="0" >
                  <tr>
                    <td align="center" valign="middle" height="80"><b>
				      <%=msg%><br>
				     <% if(!exception.equals("")){ %>
				       	Exception : <%=exception%>
				     <% } %>
                      </b></td>
                  </tr>
                </table>

				<table border=0 cellpadding=5 cellspacing=1 width=96% align='center'>
					<tr>
						<td align=center>
			<% if( param.equals("")) { %>
				<% if(!url.equals(CONTEXTPATH) ) { %>

					<script language=javascript>Button3("확인", "ForwardAndClose()", "");</script>
				<% }else{ %>

					<script language=javascript>Button3("확인", "history.back()", "");</script>
				<% } %>
				<script language="javascript">
			           function ForwardAndClose(){
							opener.location.href="<%=url%>";
                            window.close();
			           }
			     </script>
			<% }else{  %>
			     <script language="javascript">
			           function reloadAndClose(){
					     <% if(param.equals("P")) {%>
					     	parent.location.reload();
					     <% }else{ %>
							opener.location.reload();
						<% } %>
                            window.close();
			           }
			     </script>
			     <script language=javascript>Button3("확인", "reloadAndClose()", "");</script>

			<% } %>
						</td>
					</tr>
					<tr>
          			<td align="right" height="1"> </td>
        			</tr>
				</table>

          </td>
        </tr>
      </table>
      </td>
  </tr>
</table>



<% } %>
<%@include file="../common/popup_footer.jsp" %>