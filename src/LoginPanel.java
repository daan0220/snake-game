import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginPanel() {
        setPreferredSize(new Dimension(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT));
        setLayout(new GridBagLayout());

        // タイトルラベル
        JLabel titleLabel = new JLabel("Snake Game Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Username Label and Text Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));

        // Password Label and Password Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ユーザー名とパスワードを取得
                String enteredUsername = usernameField.getText();
                char[] enteredPassword = passwordField.getPassword();

                // ユーザー名とパスワードの一致を確認（シンプルな認証）
                if (enteredUsername.equals("daiki") && String.valueOf(enteredPassword).equals("daikipass")) {
                    // 認証成功したら新しい JFrame にスネークゲームを表示
                    JFrame gameFrame = new JFrame("Snake Game");
                    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    gameFrame.add(new GamePanel());
                    gameFrame.pack();
                    gameFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "ログインに失敗しました。", "エラー", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // コンポーネントを配置
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10); // コンポーネント間のスペースを設定

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        add(titleLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        add(usernameLabel, c);

        c.gridx = 1;
        c.gridwidth = 1;
        add(usernameField, c);

        c.gridx = 0;
        c.gridy = 2;
        add(passwordLabel, c);

        c.gridx = 1;
        add(passwordField, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        add(loginButton, c);
    }
}
