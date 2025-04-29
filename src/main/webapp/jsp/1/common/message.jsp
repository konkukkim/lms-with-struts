<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp" %>
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

	String 	messageTitleImg	=	"";

	if(PMODE.equals("Home")) {
		messageTitleImg = "message_title02.gif";
	}else if(PMODE.equals("Enroll")) {
		messageTitleImg = "message_title01.gif";
	}else if(PMODE.equals("MyPage")){
		messageTitleImg = "message_title02.gif";
	}else if(PMODE.equals("Help")){
		messageTitleImg = "message_title03.gif";
	}else if(PMODE.equals("Ebook")){
		messageTitleImg = "message_title04.gif";
	} else{
		messageTitleImg = "message_title02.gif";
	}
%>
<% if( alert.equals("true")) { %>
<script language="javascript">
		var msg = "<%= message %>";
		alert(msg);
</script>
<% }%>

<% if( mode.equals("popup") && reload.equals("true")) { %>
<script language="javascript">
     <% if(param.equals("P")) { %>
          <% if(url.equals("")){%>
	     	 parent.location.reload();
     	  <% }else{%>
			 parent.location.href ="<%=url%>";
		  <% } %>
	<%  }else if(param.equals("M")) { %>
          <% if(url.equals("")){%>
	     	 top.dialogArguments.location.reload();
     	  <% }else{%>
			 top.dialogArguments.location.href ="<%=url%>";
		  <% } %>
	<% }else{ %>
          <% if(url.equals("")){%>
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
  <% if(!url.equals("")){%>
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
				<!-- 작업완료후 바로이동 -->
				<!--META HTTP-EQUIV="refresh" Content="1; url=<%= url%>"--><!-- 디버깅을 위해서 우선 주석처리함...페이지 자동이동-->

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr class="s_tab01">
												<td colspan="3"></td>
											</tr>
											<tr>
												<td width="25%" align="center" valign="top" style="padding:10 10 10 10"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/msg_bg01.gif" width="126" height="123" style="padding:10 10 10 10"></td>
												<td width="75%" align="left" style="padding:0 10 0 0">
													<font color="#FF3300"><strong><%=msg%><br></strong></font>
													<font color="#333333">
<%
	if (!exception.equals("")) {
%>
		Exception : <%=exception%>
<%
	}
%>
													</font>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="3"></td>
											</tr>
										</table>

<br>
<%
	if ( param.equals("")) {
		if (!url.equals("")) {
%>
<script language="javascript">
	function goUrl1() {
		document.location.href = "<%=url%>";
	}
</script>
<center><script language=javascript>Button3("확인", "goUrl1()", "");</script></center>
<%
		}
		else {
%>
<script language="javascript">
	function goHistory1() {
		history.back();
	}
</script>
<center><script language=javascript>Button3("확인", "goHistory1()", "");</script></center>
<%
		}

	}
	else {
%>
<script language="javascript">
function reloadAndClose(){
<%
		if (param.equals("P")) {
%>
	parent.location.reload();
<%
		}
		else {
%>
	opener.location.reload();
<%
		}	// else
%>
	window.close();
}
</script>
<center><script language=javascript>Button3("확인", "reloadAndClose()", "");</script></center>
<%
	}	// else
%>



                </td>
              </tr>
            </table>





<% } %>
<%@include file="../common/footer.jsp" %>