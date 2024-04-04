package org.example.Database;

import java.sql.*;

public class DatabaseManager {
    private final Connection connection;
    private static final String DB_URL = "jdbc:postgresql://localhost:5555/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    public DatabaseManager() throws SQLException {
        if (!testConnection()) {
            throw new SQLException("Ошибка подключения! Проверьте данные");
        }

        this.connection = DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public boolean testConnection(){
        TestConnection testConnection = new TestConnection();
        return testConnection.apply_test(DB_URL, USER, PASS);
    }

    public void createDocumentTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS documents (id SERIAL PRIMARY KEY, number TEXT, content TEXT)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void insertDocument(String number, String content) throws SQLException {
        String sql = "INSERT INTO documents (number, content) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, content);
            pstmt.executeUpdate();
        }
    }

    // Другие методы для работы с базой данных, например, получение всех документов
}
