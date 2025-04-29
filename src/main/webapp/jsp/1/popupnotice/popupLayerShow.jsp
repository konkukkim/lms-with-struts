<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map,java.util.ArrayList"%> 
<%@ page import ="com.edutrack.common.UserBroker,com.edutrack.framework.util.DateTimeUtil,com.edutrack.framework.util.StringUtil"%>
<%@ page import ="com.edutrack.common.CommonUtil,com.edutrack.common.SiteNavigation,com.edutrack.message.dao.MessageDAO,com.edutrack.common.dto.CommMemDTO"%> 
<%@ page import ="javax.sql.RowSet,com.edutrack.popupnotice.dto.PopupNoticeDTO,com.edutrack.common.dto.EditorParam"%> 
<% 
	String CONTEXTPATH = request.getContextPath(); 
	String SYSTEMCODE = UserBroker.getSystemCode(request);
%>
<%
	Map data = (Map)request.getAttribute("MODEL"); 
	PopupNoticeDTO popupNoticeView = (PopupNoticeDTO)data.get("popupnoticeShow"); 

	String subject = popupNoticeView.getSubject();
	String contents = popupNoticeView.getContents();
%>
<html>
<head>
<title>Edutrack</title>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%=StringUtil.ReplaceAll(contents,"&quot;","\"")%>
</body>
</html>