package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDatabaseManager {
    // 他のデータベース操作メソッドと共に、ユーザーIDをユーザー名から取得するメソッドを定義
    public int getUserIdByUsername(String username) {
        int userId = -1;

        try {
            Connection connection = DatabaseConnector.connect();
            if (connection != null) {
                String query = "SELECT user_id FROM users WHERE username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    userId = resultSet.getInt("user_id");
                }

                resultSet.close();
                preparedStatement.close();
                DatabaseConnector.closeConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }

    // 他のデータベース操作メソッドをここに追加
}

