package com.example;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class JndiExample {
    public static void main(String[] args) {
        try {
            System.out.println("aaa");
            // JNDI Context 객체 생성
            Context context = new InitialContext();

            // 데이터소스를 조회 (JNDI Name 사용)
            DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/mydb");
            System.out.println("ds" +  ds);
            // 커넥션을 얻기
            try (Connection conn = ds.getConnection()) {
                // 쿼리 실행
                String query = "SELECT * FROM test1234";
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(query)) {

                    while (rs.next()) {
                        System.out.println("ID: " + rs.getInt("id"));
                        System.out.println("Name: " + rs.getString("name"));
                    }
                }
            }

        } catch (Exception e) {
            //            e.printStackTrace();
            //
            System.out.println("jndi test error");
        }
    }
}

