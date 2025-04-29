<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map"%> 
<% 
Map model = (Map)request.getAttribute("MODEL");
	String chkLogin = (String)model.get("chkLogin");
	String ftpInfo1 = (String)model.get("ftpInfo1");
	String ftpInfo2 = (String)model.get("ftpInfo2");
	String ftpInfo3 = (String)model.get("ftpInfo3");
	String ftpInfo4 = (String)model.get("ftpInfo4");
	String ftpInfo5 = (String)model.get("ftpInfo5");
	String ftpInfo6 = (String)model.get("ftpInfo6");
	String ftpInfo7 = (String)model.get("ftpInfo7");
	String ftpInfo8 = (String)model.get("ftpInfo8");
%>
<%=chkLogin%>
<%=ftpInfo1%>
<%=ftpInfo2%>
<%=ftpInfo3%>
<%=ftpInfo4%>
<%=ftpInfo5%>
<%=ftpInfo6%>
<%=ftpInfo7%>
<%=ftpInfo8%>