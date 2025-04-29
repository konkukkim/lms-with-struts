<%@ page import="java.sql.*, javax.naming.*, javax.sql.DataSource" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page errorPage="error.jsp" %>

<%
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
        // JNDI context 조회
        Context context = new InitialContext();

        // 데이터소스를 조회 (JNDI Name 사용)
        DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/mydb");

        // 커넥션을 얻기
        conn = ds.getConnection();

        // 쿼리 실행
        String query = " select user_id id, user_name as name from users where user_id > 1000 limit 10";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(query);

        // 조회된 데이터 출력
        while (rs.next()) {
            // int id = rs.getInt("id");
             String id = rs.getString("id");
             String name = rs.getString("name");
            out.println("<p>ID: " + id + " - Name: " + name + "</p>");
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("<p>Error: " + e.getMessage() + "</p>");
    } finally {
        // 자원 정리
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
%>
