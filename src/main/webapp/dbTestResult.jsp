<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %> <%-- List 클래스 import --%>
<html>
<head>
    <title>DB Test Result</title>
</head>
<body>
    <h1>DB Test Result</h1>
    <p>${requestScope.result}</p>

    <%
        List<String[]> results = ((com.example.form.DBTestForm) request.getAttribute("dbTestForm")).getResults();
        if (results != null && !results.isEmpty()) {
    %>
        <h2>Query Results:</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                </tr>
            </thead>
            <tbody>
                <% for (String[] row : results) { %>
                    <tr>
                        <td><%= row[0] %></td>
                        <td><%= row[1] %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <%
        }
    %>
</body>
</html>