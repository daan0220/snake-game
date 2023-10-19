package main;
import game.GamePanel;
import welcome.WelcomePanel;

import login.LoginPanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // スネークゲームのフレームを作成
            JFrame frame = new JFrame("Snake Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // GamePanelを作成
            GamePanel gamePanel = new GamePanel();
            frame.getContentPane().add(gamePanel);

            // WelcomePanelを作成
            WelcomePanel welcomePanel = new WelcomePanel(frame);
            frame.getContentPane().add(welcomePanel);

            // LoginPanelを作成
            LoginPanel loginPanel = new LoginPanel(frame);
            frame.getContentPane().add(loginPanel);

            // フレームのサイズを設定
            frame.setSize(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);

            // フレームを表示
            frame.setVisible(true);
        });
    }
}
