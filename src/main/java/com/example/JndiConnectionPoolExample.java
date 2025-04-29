package com.example;
// JNDI를 이용한 DB Connection Pool 사용 예제 (외부 의존성 없이 수정)
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JndiConnectionPoolExample {

    private DataSource dataSource;

    public JndiConnectionPoolExample() {
        try {
            // JNDI 초기화 속성 설정
            Properties properties = new Properties();
            properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
            properties.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");

            // JNDI 초기 컨텍스트 생성
            Context initContext = new InitialContext(properties);

            try {
                // 웹 애플리케이션 환경에서 시도
                Context envContext = (Context) initContext.lookup("java:comp/env");
                dataSource = (DataSource) envContext.lookup("jdbc/myDataSource");
            } catch (NamingException e) {
                // 웹 환경 검색 실패 시, 직접 루트에서 검색 시도
                try {
                    dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/myDataSource");
                } catch (NamingException e2) {
                    try {
                        // 서버 환경에 따라 경로가 다를 수 있음
                        dataSource = (DataSource) initContext.lookup("jdbc/myDataSource");
                    } catch (NamingException e3) {
                        // JNDI 검색 실패 시 간단한 커넥션 풀 생성
                        setupSimpleConnectionPool();
                    }
                }
            }

            System.out.println("DataSource 초기화 성공");
        } catch (NamingException e) {
            System.err.println("JNDI 초기화 실패, 간단한 커넥션 풀 생성: " + e.getMessage());
            setupSimpleConnectionPool();
        }
    }

    // 간단한 커넥션 풀 구현
    private void setupSimpleConnectionPool() {
        try {
            dataSource = new SimpleDataSource(
                    "com.mysql.jdbc.Driver",
                    "jdbc:mysql://localhost:3306/mydb?useSSL=false&serverTimezone=UTC",
                    "db_username",
                    "db_password",
                    5  // 초기 커넥션 수
            );
            System.out.println("간단한 커넥션 풀 초기화 성공");
        } catch (Exception e) {
            System.err.println("커넥션 풀 초기화 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Connection Pool에서 연결 가져오기
    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DataSource가 초기화되지 않았습니다.");
        }
        return dataSource.getConnection();
    }

    // 샘플 쿼리 실행
    public void executeQuery() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String sql = "SELECT * FROM users";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            System.out.println("쿼리 실행 결과:");
            boolean hasResults = false;

            while (rs.next()) {
                hasResults = true;
                String id = rs.getString("id");
                String name = rs.getString("name");
                System.out.println("User ID: " + id + ", Name: " + name);
            }

            if (!hasResults) {
                System.out.println("결과가 없습니다.");
            }
        } catch (SQLException e) {
            System.err.println("쿼리 실행 실패: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 리소스 해제
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();  // 실제로는 연결을 풀로 반환
            } catch (SQLException e) {
                System.err.println("리소스 해제 실패: " + e.getMessage());
            }
        }
    }

    // 간단한 DataSource 구현 (외부 라이브러리 없이)
    private static class SimpleDataSource implements DataSource {
        private String driverClassName;
        private String url;
        private String username;
        private String password;
        private ConcurrentLinkedQueue<Connection> connectionPool;
        private List<Connection> usedConnections = new ArrayList<>();
        private final int MAX_POOL_SIZE = 10;

        public SimpleDataSource(String driverClassName, String url, String username, String password, int initialPoolSize) throws Exception {
            this.driverClassName = driverClassName;
            this.url = url;
            this.username = username;
            this.password = password;

            // JDBC 드라이버 로드
            Class.forName(driverClassName);

            // 초기 커넥션 풀 생성
            connectionPool = new ConcurrentLinkedQueue<>();
            for (int i = 0; i < initialPoolSize; i++) {
                connectionPool.add(createConnection());
            }
        }

        private Connection createConnection() throws SQLException {
            url = "localhost";
            username = "root";
            password = "1234";
            return DriverManager.getConnection(url, username, password);
        }

        @Override
        public Connection getConnection() throws SQLException {
            Connection connection;

            if (connectionPool.isEmpty()) {
                if (usedConnections.size() < MAX_POOL_SIZE) {
                    connection = createConnection();
                } else {
                    throw new SQLException("최대 커넥션 수에 도달했습니다.");
                }
            } else {
                connection = connectionPool.poll();

                // 연결이 유효한지 확인
                if (connection == null || connection.isClosed()) {
                    connection = createConnection();
                }
            }

            // 실제 커넥션을 래핑하여 close() 호출 시 풀로 반환되도록 함
            Connection pooledConnection = new PooledConnection(connection, this);
            usedConnections.add(connection);
            return pooledConnection;
        }

        // 사용이 끝난 커넥션을 풀로 반환
        public boolean releaseConnection(Connection connection) {
            connectionPool.offer(connection);
            return usedConnections.remove(connection);
        }

        @Override
        public Connection getConnection(String username, String password) throws SQLException {
            return getConnection();  // 기본 구현에서는 동일하게 처리
        }

        // 나머지 DataSource 인터페이스 메서드들 (간단한 구현만 제공)
        @Override public java.io.PrintWriter getLogWriter() throws SQLException { return null; }
        @Override public void setLogWriter(java.io.PrintWriter out) throws SQLException {}
        @Override public void setLoginTimeout(int seconds) throws SQLException {}
        @Override public int getLoginTimeout() throws SQLException { return 0; }
        @Override public java.util.logging.Logger getParentLogger() { return null; }
        @Override public <T> T unwrap(Class<T> iface) throws SQLException { return null; }
        @Override public boolean isWrapperFor(Class<?> iface) throws SQLException { return false; }
    }

    // 커넥션 풀에서 관리되는 커넥션 래퍼 클래스
    private static class PooledConnection implements Connection {
        private Connection connection;
        private SimpleDataSource dataSource;

        public PooledConnection(Connection connection, SimpleDataSource dataSource) {
            this.connection = connection;
            this.dataSource = dataSource;
        }

        @Override
        public void close() throws SQLException {
            if (connection.isClosed()) {
                throw new SQLException("커넥션이 이미 닫혀있습니다.");
            }

            // 실제로 닫지 않고 풀로 반환
            dataSource.releaseConnection(connection);
        }

        // Connection 인터페이스의 모든 메서드를 실제 connection에 위임
        @Override public boolean isWrapperFor(Class<?> iface) throws SQLException { return connection.isWrapperFor(iface); }
        @Override public <T> T unwrap(Class<T> iface) throws SQLException { return connection.unwrap(iface); }
        @Override public void abort(java.util.concurrent.Executor executor) throws SQLException { connection.abort(executor); }
        @Override public void clearWarnings() throws SQLException { connection.clearWarnings(); }
        @Override public void commit() throws SQLException { connection.commit(); }
        @Override public java.sql.Array createArrayOf(String typeName, Object[] elements) throws SQLException { return connection.createArrayOf(typeName, elements); }
        @Override public java.sql.Blob createBlob() throws SQLException { return connection.createBlob(); }
        @Override public java.sql.Clob createClob() throws SQLException { return connection.createClob(); }
        @Override public java.sql.NClob createNClob() throws SQLException { return connection.createNClob(); }
        @Override public java.sql.SQLXML createSQLXML() throws SQLException { return connection.createSQLXML(); }
        @Override public java.sql.Statement createStatement() throws SQLException { return connection.createStatement(); }
        @Override public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException { return connection.createStatement(resultSetType, resultSetConcurrency); }
        @Override public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException { return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability); }
        @Override public java.sql.Struct createStruct(String typeName, Object[] attributes) throws SQLException { return connection.createStruct(typeName, attributes); }
        @Override public boolean getAutoCommit() throws SQLException { return connection.getAutoCommit(); }
        @Override public String getCatalog() throws SQLException { return connection.getCatalog(); }
        @Override public java.util.Properties getClientInfo() throws SQLException { return connection.getClientInfo(); }
        @Override public String getClientInfo(String name) throws SQLException { return connection.getClientInfo(name); }
        @Override public int getHoldability() throws SQLException { return connection.getHoldability(); }
        @Override public java.sql.DatabaseMetaData getMetaData() throws SQLException { return connection.getMetaData(); }
        @Override public int getNetworkTimeout() throws SQLException { return connection.getNetworkTimeout(); }
        @Override public String getSchema() throws SQLException { return connection.getSchema(); }
        @Override public int getTransactionIsolation() throws SQLException { return connection.getTransactionIsolation(); }
        @Override public java.util.Map<String, Class<?>> getTypeMap() throws SQLException { return connection.getTypeMap(); }
        @Override public java.sql.SQLWarning getWarnings() throws SQLException { return connection.getWarnings(); }
        @Override public boolean isClosed() throws SQLException { return connection.isClosed(); }
        @Override public boolean isReadOnly() throws SQLException { return connection.isReadOnly(); }
        @Override public boolean isValid(int timeout) throws SQLException { return connection.isValid(timeout); }
        @Override public String nativeSQL(String sql) throws SQLException { return connection.nativeSQL(sql); }
        @Override public java.sql.CallableStatement prepareCall(String sql) throws SQLException { return connection.prepareCall(sql); }
        @Override public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException { return connection.prepareCall(sql, resultSetType, resultSetConcurrency); }
        @Override public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException { return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability); }
        @Override public java.sql.PreparedStatement prepareStatement(String sql) throws SQLException { return connection.prepareStatement(sql); }
        @Override public java.sql.PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException { return connection.prepareStatement(sql, autoGeneratedKeys); }
        @Override public java.sql.PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException { return connection.prepareStatement(sql, columnIndexes); }
        @Override public java.sql.PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException { return connection.prepareStatement(sql, columnNames); }
        @Override public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException { return connection.prepareStatement(sql, resultSetType, resultSetConcurrency); }
        @Override public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException { return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability); }
        @Override public void releaseSavepoint(java.sql.Savepoint savepoint) throws SQLException { connection.releaseSavepoint(savepoint); }
        @Override public void rollback() throws SQLException { connection.rollback(); }
        @Override public void rollback(java.sql.Savepoint savepoint) throws SQLException { connection.rollback(savepoint); }
        @Override public void setAutoCommit(boolean autoCommit) throws SQLException { connection.setAutoCommit(autoCommit); }
        @Override public void setCatalog(String catalog) throws SQLException { connection.setCatalog(catalog); }
        @Override public void setClientInfo(java.util.Properties properties) throws java.sql.SQLClientInfoException { connection.setClientInfo(properties); }
        @Override public void setClientInfo(String name, String value) throws java.sql.SQLClientInfoException { connection.setClientInfo(name, value); }
        @Override public void setHoldability(int holdability) throws SQLException { connection.setHoldability(holdability); }
        @Override public void setNetworkTimeout(java.util.concurrent.Executor executor, int milliseconds) throws SQLException { connection.setNetworkTimeout(executor, milliseconds); }
        @Override public void setReadOnly(boolean readOnly) throws SQLException { connection.setReadOnly(readOnly); }
        @Override public java.sql.Savepoint setSavepoint() throws SQLException { return connection.setSavepoint(); }
        @Override public java.sql.Savepoint setSavepoint(String name) throws SQLException { return connection.setSavepoint(name); }
        @Override public void setSchema(String schema) throws SQLException { connection.setSchema(schema); }
        @Override public void setTransactionIsolation(int level) throws SQLException { connection.setTransactionIsolation(level); }
        @Override public void setTypeMap(java.util.Map<String, Class<?>> map) throws SQLException { connection.setTypeMap(map); }
    }

    public static void main(String[] args) {
        JndiConnectionPoolExample example = new JndiConnectionPoolExample();
        example.executeQuery();
    }
}