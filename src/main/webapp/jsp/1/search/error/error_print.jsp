<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>

<%
    ArrayList errors = (ArrayList)request.getAttribute("errors");
    if (errors != null) {
        for (int i = 0; i < errors.size(); i++) {
%>
<li><font color="red"><%= errors.get(i) %></font>
<hr>
<%
        }
    }
%>
