<%@ page contentType="text/html; charset=euc-kr"%>
<%@ page import="mediopia.lms.common.Constants"%>

<%
	String	LMSConnector = "/connector";
	String	LCMSConnectorUrl = Constants.LCMS_CONNECTOR_URL;	
	String	LMSPage = request.getRequestURI();
	String	host = request.getServerName();
	int		port = request.getServerPort();
	String	contextPath = request.getContextPath();
	String	pSystemCode = request.getParameter("pSystemCode")==null?"":request.getParameter("pSystemCode");
	String	pCurriCode = request.getParameter("pCurriCode")==null?"":request.getParameter("pCurriCode");
	String	pCurriYear = request.getParameter("pCurriYear")==null?"":request.getParameter("pCurriYear");
	String	pCurriTerm = request.getParameter("pCurriTerm")==null?"":request.getParameter("pCurriTerm");
	String	pCourseId = request.getParameter("pCourseId")==null?"":request.getParameter("pCourseId");
	String	lesson = request.getParameter("lesson")==null?"":request.getParameter("lesson");
	String	pCONT_ID = request.getParameter("pCONT_ID")==null?"":request.getParameter("pCONT_ID");
	String	pNEW_ORDER = request.getParameter("pNEW_ORDER")==null?"":request.getParameter("pNEW_ORDER");
	String	pOrdinal = request.getParameter("pOrdinal")==null?"":request.getParameter("pOrdinal");
	String	pCONT_TYPE = request.getParameter("pCONT_TYPE")==null?"":request.getParameter("pCONT_TYPE");
	String	pCloseWin = request.getParameter("pCloseWin")==null?"":request.getParameter("pCloseWin");
	
	String	lmsParams = "pHost="+host
					+"&pPort="+port
					+"&pContextPath="+contextPath
					+"&pLmsConnector="+LMSConnector
					+"&pSystemCode="+pSystemCode
					+"&pCurriCode="+pCurriCode
					+"&pCurriYear="+pCurriYear
					+"&pCurriTerm="+pCurriTerm
					+"&pCourseId="+pCourseId
					+"&pLesson="+lesson
					+"&pContentsId="+pCONT_ID
					+"&pNewOrder="+pNEW_ORDER
					+"&pOrdinal="+pOrdinal
					+"&pContentsType="+pCONT_TYPE
					+"&pLmsPage="+LMSPage;
%>
<script language="JavaScript">
	if ("<%=pCloseWin%>" == "close") {
		top.refreshList();
		top.closeWin();
	}

	function refreshList() {
		top.opener.document.location.reload();
		//top.opener.parent.webtree_web_work.document.location = "webtree_web_work.jsp?pSERVER=0&pWORK_NO=8&pCOURSE_ID=<%=pCourseId%>";
	}

	function closeWin() {
		window.close();
	}
</script>

<html>
<head>
	<title>LCMS에서 콘텐츠 가져오기</title>
<%
	if (!pCloseWin.equals("close")) {
%>
		<frameset rows="100%,*" frameborder=0 border=0 framespacing=0>
			<frame name="lcmsFrame" scrolling="auto" marginwidth="0" marginheight="0" src="<%=LCMSConnectorUrl%>?<%=lmsParams%>" noresize>
			<frame name="blank" scrolling="no" marginwidth="0" marginheight="0" src="#" noresize>
		</frameset>
<%
	}
%>

</head>

</html>