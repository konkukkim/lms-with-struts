<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/popup_header.jsp" %>
<%
String itemList = (String)model.get("itemList");
%>
<SCRIPT>
 var innerHTML = "<%=itemList%>" 
 parent.document.all.itemsList.innerHTML = innerHTML ; 
</script>
</body>
</html>