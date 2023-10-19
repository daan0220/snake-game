package welcome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import game.GamePanel;
import Ranking.RankingPanel;
public class WelcomePanel extends JPanel {
    private JFrame frame;

    public WelcomePanel(JFrame frame) {
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
                GamePanel gamePanel = new GamePanel();
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
                RankingPanel rankingPanel = new RankingPanel(frame); // RankingPanelを新たに作成
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
