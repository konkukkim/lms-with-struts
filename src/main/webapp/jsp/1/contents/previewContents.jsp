<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page import ="com.edutrack.common.UserBroker, com.edutrack.framework.util.FileUtil"%>
<% String CONTEXTPATH = request.getContextPath(); %>
<%
	String systemCode = UserBroker.getSystemCode(request);
	String contentsBase = CONTEXTPATH+FileUtil.DATA_DIR;
	String serverPath = (String)request.getParameter("pServerPath");
	String contentsUrl = "";
	String tempUrl = "";

	// 컨텐츠의 경로가 LCMS로 부터 가져온 경로가 아니면
	if (serverPath.indexOf("/REP/") != 0 && serverPath.indexOf("mms://") != 0 ) {
		if(serverPath.length() > 6 )
			tempUrl = serverPath.substring(0,6);

		if(tempUrl.indexOf("/beuse") != 0)
			contentsUrl = contentsBase+serverPath;
		else
			contentsUrl = serverPath;
	}

	// 컨텐츠의 경로가 동영상 링크이면
	if (serverPath.indexOf("mms://") == 0)
		contentsUrl = CONTEXTPATH+"/jsp/"+systemCode+"/contents/previewContentsVideo.jsp?pFileUrl="+serverPath;

	// 컨텐츠의 경로가 LCMS 링크이면
	if (serverPath.indexOf("/REP/") == 0)
		contentsUrl = serverPath;

%>
<HTML>
<HEAD>
	<TITLE>컨텐츠 미리보기</TITLE>
</head>
<FRAMESET rows="100%,*" FRAMEBORDER=0 BORDER=0 FRAMESPACING=0>
	<FRAME NAME="preview_main" SRC="<%=contentsUrl%>" SCROLLING="AUTO" MARGINWIDTH=0 NORESIZE >
	<FRAME NAME="preview_hide" SRC="<%=CONTEXTPATH%>/blank.html" MARGINHEIGHT=0 MARGINWIDTH=0 >
</FRAMESET>
</HTML>
