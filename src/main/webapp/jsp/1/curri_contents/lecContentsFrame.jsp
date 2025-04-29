<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map,java.util.ArrayList"%> 
<%@ page import ="com.edutrack.common.UserBroker,com.edutrack.framework.util.DateTimeUtil,com.edutrack.framework.util.StringUtil"%>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO,com.edutrack.currisub.dto.CurriContentsDTO"%>
<%@ page import ="com.edutrack.common.CommonUtil,com.edutrack.common.SiteNavigation"%> 
<% String CONTEXTPATH = request.getContextPath(); %>
<%
    Map model = (Map)request.getAttribute("MODEL");
    String PMODE = "";
    String DATEYN = "";
    if(model != null) {
    	PMODE = (String)model.get("pMode");
    	DATEYN = (String)model.get("dateyn");
    }
    String SYSTEMCODE = UserBroker.getSystemCode(request); 
    String USERID = UserBroker.getUserId(request);
    String USERTYPE = UserBroker.getUserType(request);
    String pContentsType = (String)model.get("pContentsType");
%>

<html>
<head>
	<title>교안보기... </title>

</head>	
<%if(pContentsType.equals("S")){%>
	<script language ="javascript" >
        var API = null;
        initAPI();
        function initAPI() {
            if (opener.wwws.document.applets["APIAdapter"]) {
                API = opener.wwws.document.applets["APIAdapter"];
            }
            else if (opener.wwws.document.APIAdapter) {
                API = opener.wwws.document.APIAdapter;
            }
            else {
                alert("Unable to find an API adapter");
            }
        }
    </script>
<%}%>
	<frameset rows="0,*,0" frameborder=0 border=0 framespacing=0>
		<frame name="contents_title" src = "<%=CONTEXTPATH%>/CurriContents.cmd?cmd=lecBookmarkStart&pCourseId=<%=model.get("pCourseId")%>&pContentsId=<%=model.get("pContentsId")%>" marginwidth=0 marginheight=0">
		<frame name="contents_show" src = "<%=CONTEXTPATH%>/CurriContents.cmd?cmd=lecContentsShow&pCourseId=<%=model.get("pCourseId")%>&pContentsId=<%=model.get("pContentsId")%>" scrolling=auto marginwidth=0 marginheight=0>
	</frameset>
</html>
