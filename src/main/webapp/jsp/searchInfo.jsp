<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.framework.util.StringUtil"%>
<%
 String CONTEXTPATH = request.getContextPath();
 String target = StringUtil.nvl(request.getParameter("target"));
 
 String pCurriCode = StringUtil.nvl(request.getParameter("pCurriCode"));
 String pCurriYear = StringUtil.nvl(request.getParameter("pCurriYear"));
 String pCurriTerm = StringUtil.nvl(request.getParameter("pCurriTerm"));
 
 String pSaleIdx = StringUtil.nvl(request.getParameter("pSaleIdx"));
 String pInfoIdx = StringUtil.nvl(request.getParameter("pInfoIdx"));
 String pCateCode = StringUtil.nvl(request.getParameter("pCateCode"));

/**
target : SearchCourse  , SearchEbook
**/
%>
<HTML>
<HEAD>
	<TITLE>::: 한국영상자료원 - KOFA 영화학교 :::</TITLE>
</head>
<form name=search method=post action="<%=CONTEXTPATH%>/index.jsp">
<input type=hidden name="target" value="<%=target%>">
<input type=hidden name="pCurriCode" value="<%=pCurriCode%>">
<input type=hidden name="pCurriYear" value="<%=pCurriYear%>">
<input type=hidden name="pCurriTerm" value="<%=pCurriTerm%>">
<input type=hidden name="pSaleIdx" value="<%=pSaleIdx%>">
<input type=hidden name="pInfoIdx" value="<%=pInfoIdx%>">
<input type=hidden name="pCateCode" value="<%=pCateCode%>">
</form>
<SCRIPT>
		document.search.submit();
</SCRIPT>	
</HTML>