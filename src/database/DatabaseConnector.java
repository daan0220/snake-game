package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnector {
    public static Connection connect() {

        String url = System.getenv("DB_URL"); // 最初に環境変数から値を取得
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");
        Connection connection = null; // Connection オブジェクトを宣言

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("データベースに接続しました");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("データベース接続をクローズしました");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
