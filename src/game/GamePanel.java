package game;
import database.DatabaseConnector;
import database.UserDatabaseManager;
import login.LoginPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import session.UserSession;
import welcome.WelcomePanel;

public class GamePanel extends JPanel implements ActionListener {
    private JButton returnToWelcomeButton;
    private JFrame parentFrame;

    private UserSession userSession; // UserSession クラスのインスタンスを保持

    public void gameEnded(int score, String username) {
        // スコアとユーザー名を取得し、ゲーム結果を `rankings` テーブルに格納する
        try {
            Connection connection = DatabaseConnector.connect();
            if (connection != null) {
                String insertQuery = "INSERT INTO rankings (user_id, username, score) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

                // パラメータを設定
                UserDatabaseManager userDatabaseManager = new UserDatabaseManager(); // UserDatabaseManager クラスの新しいインスタンスを作成
                int userId = userDatabaseManager.getUserIdByUsername(username); // インスタンスを使用してユーザーIDを取得
                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, username);
                preparedStatement.setInt(3, score);

                // クエリを実行
                preparedStatement.executeUpdate();

                // 接続をクローズ
                preparedStatement.close();
                DatabaseConnector.closeConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private LoginPanel loginPanel; // LoginPanelの参照を保持するための変数

    public static final int SCREEN_WIDTH = 1300;
    public static final int SCREEN_HEIGHT = 750;
    static final int UNIT_SIZE = 50;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    static final int DELAY = 175;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    public GamePanel(UserSession userSession, JFrame parentFrame) {
        this.parentFrame = parentFrame; // 親の JFrame を保持
        this.userSession = userSession;
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
        returnToWelcomeButton = new JButton("Welcome に戻る");
        returnToWelcomeButton.setFont(new Font("Arial", Font.BOLD, 18));
        returnToWelcomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToWelcome(); // "Welcome に戻る" ボタンがクリックされたときのアクション
            }
        });
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(returnToWelcomeButton, constraints);

        // ボタンを最初は非表示にする
        returnToWelcomeButton.setVisible(false);
    }


    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
        drawObstacles(g); // 障害物を描画
    }

    public void draw(Graphics g) {

        if (running) {
            /*
             * for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
             * g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
             * g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
             * }
             */
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    // g.setColor(new
                    // Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
                    g.getFont().getSize());
        } else {
            gameOver(g);
        }

    }

    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
            createObstacleIfNecessary(); // リンゴが取られた後に障害物を生成するかチェック

        }
    }

    public void createObstacleIfNecessary() {
        if (applesEaten > 2 && applesEaten % 3 == 0) {
            if (applesEaten % 6 == 3) {
                for (int i = 0; i < 3; i++) {
                    createObstacle();
                }
            }
        }
    }

    public void checkCollisions() {
        // checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }

        // Check if head collides with obstacles
        for (Obstacle obstacle : obstacles) {
            if (x[0] == obstacle.getX() && y[0] == obstacle.getY()) {
                running = false;
            }
        }

        // check if head touches left border
        if (x[0] < 0) {
            running = false;
        }
        // check if head touches right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        // check if head touches top border
        if (y[0] < 0) {
            running = false;
        }
        // check if head touches bottom border
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }
    private List<Obstacle> obstacles = new ArrayList<>();

    private void createObstacle() {
        int obstacleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        int obstacleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
        int obstacleWidth = UNIT_SIZE;
        int obstacleHeight = UNIT_SIZE;
        Obstacle obstacle = new Obstacle(obstacleX, obstacleY, obstacleWidth, obstacleHeight);
        obstacles.add(obstacle);
    }


    private void drawObstacles(Graphics g) {
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g);
        }
    }

    // returnToWelcome メソッドを次のように変更
    private void returnToWelcome() {
        parentFrame.getContentPane().removeAll();
        WelcomePanel welcomePanel = new WelcomePanel(parentFrame, userSession);
        parentFrame.getContentPane().add(welcomePanel);
        parentFrame.setSize(WelcomePanel.SCREEN_WIDTH, WelcomePanel.SCREEN_HEIGHT);
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    public void gameOver(Graphics g) {
        // Score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2,
                g.getFont().getSize());

        // Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
        returnToWelcomeButton.setVisible(true);

        // ゲームが終了したときに gameEnded メソッドを呼び出す
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}