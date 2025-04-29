<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.ArrayList" %>

<%
	String err_msg = "";
    ArrayList errors = (ArrayList)request.getAttribute("errors");
    if (errors != null) {
        for (int i = 0; i < errors.size(); i++) {
            err_msg = err_msg + errors.get(i) + "\\n";
        }
        if(err_msg.length() > 0) {
		System.out.println(err_msg);
%>
		<script>
        	alert('<%=err_msg%>');
        </script>
<%		}
    }
%>
