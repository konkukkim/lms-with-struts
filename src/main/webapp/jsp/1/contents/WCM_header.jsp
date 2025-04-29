<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map,java.util.ArrayList"%> 
<%@ page import ="com.edutrack.common.UserBroker,com.edutrack.framework.util.DateTimeUtil,com.edutrack.framework.util.StringUtil"%>
<%@ page import ="com.edutrack.common.CommonUtil,com.edutrack.curritop.dto.ContentsFileDTO"%> 
<% String CONTEXTPATH = request.getContextPath(); %>
<%
    Map model = (Map)request.getAttribute("MODEL");
    
    String SYSTEMCODE = UserBroker.getSystemCode(request);
%>
<html>
<head>
<title>Edutrack</title>
<link href="<%=CONTEXTPATH%>/css/WCM.css"" rel="stylesheet" type="text/css">
<script language="JavaScript" src = "<%=CONTEXTPATH%>/js/common1.js"></script>
</head>
<body topmargin=0 leftmargin=0 marginwidth=0 marginheight=0>