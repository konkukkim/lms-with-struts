<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map,java.util.ArrayList"%>
<%@ page import ="com.edutrack.common.UserBroker,com.edutrack.framework.util.DateTimeUtil,com.edutrack.framework.util.StringUtil"%>
<%@ page import ="com.edutrack.common.CommonUtil,com.edutrack.common.SiteNavigation"%>
<%
	String CONTEXTPATH = request.getContextPath();

    Map model = (Map)request.getAttribute("MODEL");
    String PMODE = "";
    String DATEYN = "";

    if(model != null) {
    	PMODE   = (String)model.get("pMode");
    	DATEYN = (String)model.get("dateyn");
    }
    String SYSTEMCODE   = UserBroker.getSystemCode(request);
    String USERID       = UserBroker.getUserId(request);
    String USERTYPE     = UserBroker.getUserType(request);
%>
<html>
<head>
<title>::: 전태일을 따르는 사이버 노동대학 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/style.css">
<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/form.css">
<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/date_select1.css">
<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/form_classroom.css">

<script language=JavaScript src="<%=CONTEXTPATH%>/js/ajaxCommon.js"></script>
<Script language=JavaScript src="<%=CONTEXTPATH%>/js/common1.js"></script>
<script language=javascript src="<%=CONTEXTPATH%>/js/common_util.js"></script>
<script language=javascript src="<%=CONTEXTPATH%>/js/common_button.js"></script>
<SCRIPT language=javascript src="<%=CONTEXTPATH%>/js/flash.js"></SCRIPT>
</head>

<body leftmargin="3" topmargin="3" marginwidth="3" marginheight="3">
<center>