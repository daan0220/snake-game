import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPanel extends JPanel {
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginPanel() {
        // レイアウトを設定
        setLayout(new GridLayout(3, 2)); // グリッドレイアウトを使用

        // ユーザ名のラベルとテキストフィールド
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(usernameLabel);
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        add(usernameField);

        // パスワードのラベルとテキストフィールド
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(passwordLabel);
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        add(passwordField);

        // ログインボタン
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        add(loginButton);

        // ログインボタンのアクションリスナー
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = usernameField.getText();
                char[] enteredPassword = passwordField.getPassword();

                Connection connection = DatabaseConnector.connect();

                if (connection != null) {
                    try {
                        String query = "SELECT * FROM users WHERE username=? AND password=?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, enteredUsername);
                        statement.setString(2, String.valueOf(enteredPassword));
                        ResultSet result = statement.executeQuery();

                        if (result.next()) {
                            JOptionPane.showMessageDialog(null, "Login successful!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Login failed. Invalid username or password.", "エラー", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } finally {
                        DatabaseConnector.closeConnection(connection);
                    }
                }
            }
        });
    }
}
