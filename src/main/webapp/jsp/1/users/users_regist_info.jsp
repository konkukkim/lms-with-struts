<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp"%>

<Script Language="JavaScript">
	function Regist_Window() {
		var loc="<%=CONTEXTPATH%>/User.cmd?cmd=userWrite&pGUBUN=write&pMode=Member";
		document.location = loc;
	}
</Script>


						<table width="100%" height="35" border="0" cellpadding="0" cellspacing="0" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/membership/mem_cen_titlebg.gif">
							<tr>
								<td align="left" valign="middle" style="padding:0 0 0 8"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/membership/mem_centitle01.gif" width="134" height="19"></td>
								<td width="50%" align="right" valign="middle" style="padding:0 8 0 0">&nbsp;</td>
							</tr>
						</table>
						<table width="100%" height="120" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td height="315" align="center" valign="bottom"> <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/membership/mem01.gif" width="637" height="271"></td>
							</tr>
						</table>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="center" valign="top">
									<table width="637" border="0" cellpadding="0" cellspacing="0" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/membership/mem02.gif">
										<tr>
											<td align="center" valign="top" ><iframe src="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/users/article.htm" frameborder="0" width="617" height="230" scrolling="Auto"></iframe></td>
										</tr>
										<tr>
											<td height="10" bgcolor="#F4F4F1"> </td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<table width="100%" height="70" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="center" valign="middle">
									<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/membership/mem_agree.gif" width="124" height="36" hspace="5"  onClick="javascript:Regist_Window();" Style="cursor:hand">
									<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/membership/mem_disagree.gif" width="181" height="36" hspace="5" onClick="javascript:history.back();" Style="cursor:hand">
								</td>
							</tr>
						</table>
						



                

 
<%@include file="../common/footer.jsp" %>