<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/course_header.jsp" %>
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
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b>메시지</b></font></td>
									<!-- // classroom title -->
									<!-- history -->
									<td class="c_stit_history" valign="bottom" align="right" width="327">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (PMODE.equals("Search")) NAVIGATION = "홈 > 통합검색";
	if (NAVIGATION != "") {
		out.println(NAVIGATION) ;
	} // end if
%>
									<!-- // history -->
								</tr>
								<tr valign="top">
									<td colspan="2" class="content_top" align="center" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr class="s_tab01">
												<td colspan="3"></td>
											</tr>
											<tr>
												<td width="25%" align="center" valign="middle" style="padding:10 10 10 10"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/msg_bg01.gif" width="126" height="123" style="padding:10 10 10 10"></td>
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
<!-- <a href ="<%=url%>"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/bttn_ok.gif" align="absmiddle" border="0"></a>&nbsp; -->
<script language=javascript>Button3("확인", "goUrl1()", "");</script>
<%
		}
		else {
%>
<script language="javascript">
	function goHistory1() {
		history.back();
	}
</script>
<!-- <a href ="javascript:history.back()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/bttn_ok.gif" align="absmiddle" border="0"></a>&nbsp; -->
<script language=javascript>Button3("확인", "goHistory1()", "");</script>
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
<!-- <a href ="javascript:reloadAndClose();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/bttn_ok.gif" align="absmiddle" border="0"></a>&nbsp; -->
<script language=javascript>Button3("확인", "reloadAndClose()", "");</script>
<%
	}	// else
%>



                </td>
              </tr>
            </table>

<% } %>
<%@include file="../common/course_footer.jsp" %>