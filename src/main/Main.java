package main;

import game.GamePanel;
import session.UserSession;
import login.LoginPanel;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // スネークゲームのフレームを作成
            JFrame frame = new JFrame("Snake Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // UserSessionを作成
            UserSession userSession = new UserSession();

            // LoginPanelを作成
            LoginPanel loginPanel = new LoginPanel(frame, userSession);
            frame.getContentPane().add(loginPanel);

            // フレームのサイズを設定
            frame.setSize(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);

            // フレームを表示
            frame.setVisible(true);
        });
    }
}
