package welcome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import game.GamePanel;
import Ranking.RankingPanel;
import session.UserSession; // UserSession クラスをインポート

public class WelcomePanel extends JPanel {
    public static final int SCREEN_WIDTH = 800; // 800 は適切な幅に置き換えてください
    public static final int SCREEN_HEIGHT = 600; // 600 は適切な高さに置き換えてください
    private UserSession userSession; // UserSession クラスのインスタンスを保持

    private JFrame frame;

    public WelcomePanel(JFrame frame, UserSession userSession) {
        this.userSession = userSession;
        this.frame = frame;

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JButton startGameButton = new JButton("ゲームを始める");
        JButton viewRankingsButton = new JButton("ランキングを見る");

        startGameButton.setFont(new Font("Arial", Font.BOLD, 18));
        viewRankingsButton.setFont(new Font("Arial", Font.BOLD, 18));

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                GamePanel gamePanel = new GamePanel(userSession, frame); // frame を渡す
                frame.getContentPane().add(gamePanel);
                gamePanel.requestFocusInWindow(); // フォーカスを設定
                frame.setSize(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
                frame.revalidate();
                frame.repaint();
            }
        });

        viewRankingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                RankingPanel rankingPanel = new RankingPanel(frame, userSession);
                frame.getContentPane().add(rankingPanel);
                frame.setSize(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
                frame.revalidate();
                frame.repaint();
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(startGameButton, constraints);

        constraints.gridy = 1;
        add(viewRankingsButton, constraints);
    }
}
