package game;

import session.UserSession;
import javax.swing.JFrame;

public class GameFrame extends JFrame {
    GameFrame(UserSession userSession) {
        this.add(new GamePanel(userSession, this));
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
