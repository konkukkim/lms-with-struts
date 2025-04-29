<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map"%> 
<%@ page import ="com.edutrack.common.CommonUtil"%> 
<% String CONTEXTPATH = request.getContextPath(); %>
<%
    Map model = (Map)request.getAttribute("MODEL");
    String systemCode = (String) model.get("systemCode");
    String pCourseId = (String) model.get("pCourseId");
%>
<html>
<head>
    <meta http-equiv="expires" content="0">
    <meta http-equiv="Pragma" content="no-cache">
</head>
<SCRIPT language=javascript src="<%=CONTEXTPATH%>/js/objEmbed.js"></script>
<script language ="javascript">
    var API = null;
    function initAPI() {
        if (document.applets["APIAdapter"]) {
            API = document.applets["APIAdapter"];
        }
        else if (document.APIAdapter) {
            API = document.APIAdapter;
        }
        else {
            alert("Unable to find an API adapter");
        }
    }
</script>   
<body>
<SCRIPT LANGUAGE="JavaScript">
KOFA_APPLET = new setEmbed();
KOFA_APPLET.init("applet" , "<%=CONTEXTPATH%>/jsp/<%=systemCode%>/curri_contents", "", "<%=CONTEXTPATH%>/jsp/<%=systemCode%>/curri_contents", "100", "100");
KOFA_APPLET.setHeaderAdd('code','org/adl/samplerte/client/APIAdapterApplet.class'); 
KOFA_APPLET.setHeaderAdd('archive','datamodel2.jar,lmsclientStr.jar,debug.jar');
KOFA_APPLET.setHeaderAdd('id','APIAdapter');
KOFA_APPLET.setHeaderAdd('name','APIAdapter'); 
KOFA_APPLET.setHeaderAdd('mayscript','true'); 
KOFA_APPLET.setParameter('PortStr',''); 
KOFA_APPLET.setParameter('URLStr','<%=CONTEXTPATH%>/ScormStudy.cmd?cmd=getScormRte&pCourseId=<%=pCourseId%>'); 
KOFA_APPLET.show(); 
</SCRIPT> 
</body>
</html>