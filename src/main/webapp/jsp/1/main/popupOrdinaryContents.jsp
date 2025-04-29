<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map"%>
<%@ page import ="com.edutrack.framework.util.StringUtil"%>
<%
	String CONTEXTPATH = request.getContextPath();

	Map		model		=	(Map)request.getAttribute("MODEL");
	String	pCurriCode	=	StringUtil.nvl(model.get("pCurriCode"));
	String	pCurriYear	=	StringUtil.nvl(model.get("pCurriYear"));
	String	pCurriTerm	=	StringUtil.nvl(model.get("pCurriTerm"));
%>
<HTML>
<HEAD>
	<TITLE>공개강좌 강의입니다</TITLE>
</head>
<FRAMESET cols="20%,80%" FRAMEBORDER=0 BORDER=0 FRAMESPACING=0>
<!-- MODIFIED 2010-04-26
	<FRAME NAME="contents_left" SRC="<%=CONTEXTPATH%>/Main.cmd?cmd=popupOrdinaryMenu&pCurriCode=<%=pCurriCode%>&pCurriYear=<%=pCurriYear%>&pCurriTerm=<%=pCurriTerm%>" SCROLLING="auto" MARGINWIDTH=0 NORESIZE >
	<FRAME NAME="contents_main" SRC="<%=CONTEXTPATH%>/Main.cmd?cmd=popupOrdinaryMain" SCROLLING="AUTO" MARGINWIDTH=0 NORESIZE >
-->
	<FRAME NAME="contents_left" SRC="<%=CONTEXTPATH%>/Main.cmd?cmd=popupOrdinaryMenu&pCurriCode=<%=pCurriCode%>&pCurriYear=<%=pCurriYear%>&pCurriTerm=<%=pCurriTerm%>" SCROLLING="auto" MARGINWIDTH=0 >
	<FRAME NAME="contents_main" SRC="<%=CONTEXTPATH%>/Main.cmd?cmd=popupOrdinaryMain" SCROLLING="AUTO" MARGINWIDTH=0>
</FRAMESET>
</HTML>

