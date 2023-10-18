import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // スネークゲームのフレームを作成
                JFrame frame = new JFrame("Snake Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // ログイン画面を作成
                LoginPanel loginPanel = new LoginPanel();

                // ログイン画面をフレームに追加
                frame.getContentPane().add(loginPanel);

                // フレームを表示
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
