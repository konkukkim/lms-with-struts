package com.example.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class DbJndiDataSource {

    private static DataSource dataSource;

    static {
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            dataSource = (DataSource)envContext.lookup("jdbc/mydb");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("JNDI DataSource 초기화 실패: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws Exception {
        if (dataSource == null) {
            throw new Exception("DataSource가 초기화되지 않았습니다.");
        }
        return dataSource.getConnection();
    }
}