<%@ page contentType="text/html; charset=KS_C_5601-1987" %>
<%@ page errorPage="/jsp/1/search/error/error_jsp.jsp" %>
<%
    String statusCode = request.getParameter("status");
%>

<%
    if(statusCode.equals("-109"))
    {
%>
    	<script language=javascript>
		alert('검색된 자료가 없습니다.');
		history.go(-1);
	</script>
<%
    }
    else if(statusCode.equals("-103"))
    {
%>
    	<script language=javascript>
		alert('서버에 연결할 수 없습니다. 잠시 후에 다시 이용해 주시기 바랍니다');
		history.go(-1);
	</script>
<%
    }
    else
    {
%>
    	<script language=javascript>
		alert('에러가 발생하였습니다..');
		history.go(-1);
	</script>
<%
     }
%>

