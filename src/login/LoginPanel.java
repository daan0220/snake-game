package login;

import database.DatabaseConnector;
import game.GamePanel;
import welcome.WelcomePanel;
import session.UserSession;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginPanel extends JPanel {
    private UserSession userSession; // UserSession クラスのインスタンスを保持

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private JFrame frame;

    public LoginPanel(JFrame frame, UserSession userSession) {
        this.frame = frame;
        this.userSession = userSession; // UserSession クラスのインスタンスを初期化
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(usernameLabel, constraints);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(usernameField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 18));
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(passwordLabel, constraints);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(passwordField, constraints);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(loginButton, constraints);

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
                            userSession.login(enteredUsername); // ログイン情報を UserSession に設定
                            JOptionPane.showMessageDialog(null, "Login successful!");

                            // ログイン成功時に WelcomePanel に遷移
                            frame.getContentPane().removeAll();
                            WelcomePanel welcomePanel = new WelcomePanel(frame, userSession);
                            frame.getContentPane().add(welcomePanel);
                            frame.setSize(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
                            frame.revalidate();
                            frame.repaint();
                        } else {
                            JOptionPane.showMessageDialog(null, "Login failed. Invalid username or password.", "エラー", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(LoginPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        DatabaseConnector.closeConnection(connection);
                    }
                }
            }
        });

    }
}
