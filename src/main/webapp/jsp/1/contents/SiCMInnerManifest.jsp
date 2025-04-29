<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%> 
<%@include file="../common/popup_header.jsp" %>
<link href="<%=CONTEXTPATH%>/css/SiCM.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript">

var innerHTML = " <table style='position:relative; top:0; left:0; width:500;'  border='0' cellpadding='0' cellspacing='0'> "	  
	innerHTML += " <colgroup> "  
	innerHTML += "	<col width = '60' >  "  
	innerHTML += "	<col width = '1' >  "  
	innerHTML += "	<col width = '320'>  "  
	innerHTML += "	<col width = '1' >  "  
 innerHTML += "	<col> "  
 innerHTML += "	</colgroup> " 
 <%
int No = 0;
RowSet list= (RowSet)model.get("rsManiList");
  
while(list.next()){
%>
 
 innerHTML += "	 <TR height='25' bgcolor='#FFFFFF'> "  
 innerHTML += "	<TD  align= 'center'>  "  
 innerHTML += "  <INPUT type = checkbox  class = 'rcCls' name = 'chb1'    onclick= itemSearch('<%=list.getString("mani_identifier")%>','<%=No%>');  > " 
 innerHTML += " </TD><td></td>  "  
 innerHTML += "  <TD   align= 'left' style='padding 0 0 0 5'><a href=\"javascript:deleteManifest('<%=list.getString("mani_identifier")%>')\"><%=list.getString("title_value")%></a></TD><td></td>  "  
 innerHTML += " <TD  align= 'center'><%=list.getString("user_name")%></TD> "  
 innerHTML += " </TR><tr><td colspan=5 background='http://test.cbedunet.or.kr/images/cyber/bg_td.gif'></td></tr> " 
 <%
	No++;
}
list.close();
 %>
 innerHTML += "  </TABLE> " 
 parent.document.all.manifestList.innerHTML = innerHTML ; 

</SCRIPT>
</body>
</html>