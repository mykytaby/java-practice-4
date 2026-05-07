package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    private String url;
    private String user;
    private String password;

    public DatabaseManager(String configPath) throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(configPath)) {
            props.load(fis);
        }
        this.url = props.getProperty("db.url");
        this.user = props.getProperty("db.user");
        this.password = props.getProperty("db.password");
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void saveClothes(Clothes clothes) {
        String sql = "INSERT INTO clothes_inventory (class_type, item_type, brand, item_size, price, extra_boolean) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, clothes.getClass().getSimpleName());
            pstmt.setString(2, clothes.getType());
            pstmt.setString(3, clothes.getBrand());
            pstmt.setString(4, clothes.getSize().toString());
            pstmt.setDouble(5, clothes.getPrice());

            // Обробка специфічних полів через instanceof
            if (clothes instanceof Pants) {
                pstmt.setBoolean(6, ((Pants) clothes).isHasBelt());
            } else if (clothes instanceof Shirts) {
                pstmt.setBoolean(6, ((Shirts) clothes).isShortSleeves());
            } else if (clothes instanceof Outerwear) {
                pstmt.setBoolean(6, ((Outerwear) clothes).isWaterproof());
            } else if (clothes instanceof Dress) {
                pstmt.setBoolean(6, ((Dress) clothes).isEvening());
            } else {
                pstmt.setNull(6, java.sql.Types.BOOLEAN);
            }

            pstmt.executeUpdate();
            System.out.println("[DB] Об'єкт успішно збережено в базу даних.");
            
        } catch (SQLException e) {
            System.err.println("[DB Помилка] Не вдалося зберегти в БД: " + e.getMessage());
        }
    }
}