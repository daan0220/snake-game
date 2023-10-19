package Ranking;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import welcome.WelcomePanel;
import database.DatabaseConnector;
import session.UserSession;

public class RankingPanel extends JPanel {
    private UserSession userSession;
    private JFrame frame;

    public RankingPanel(JFrame frame, UserSession userSession) {
        this.frame = frame;
        this.userSession = userSession;

        setLayout(new BorderLayout());

        // Create a table to display the ranking
        JTable rankingTable = new JTable();
        rankingTable.setGridColor(Color.LIGHT_GRAY);
        rankingTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        rankingTable.setFont(new Font("Arial", Font.PLAIN, 16));
        rankingTable.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(rankingTable);
        add(scrollPane, BorderLayout.CENTER);

        // Set the background color and foreground color
        rankingTable.setBackground(Color.BLACK);
        rankingTable.setForeground(Color.WHITE);
        rankingTable.setSelectionBackground(Color.DARK_GRAY);

        // Retrieve and display the ranking
        List<RankingEntry> rankingEntries = getRankingDataFromDatabase(userSession);
        RankingTableModel model = new RankingTableModel(rankingEntries);
        rankingTable.setModel(model);

        JButton backButton = new JButton("Back to Welcome");
        backButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            WelcomePanel welcomePanel = new WelcomePanel(frame, userSession); // UserSession を WelcomePanel に渡す
            frame.getContentPane().add(welcomePanel);
            frame.revalidate();
            frame.repaint();
        });
        add(backButton, BorderLayout.SOUTH);
    }

    private List<RankingEntry> getRankingDataFromDatabase(UserSession userSession) {
        List<RankingEntry> rankingEntries = new ArrayList<>();
        try {
            Connection connection = DatabaseConnector.connect();
            if (connection != null) {
                Statement statement = connection.createStatement();
                String query = "SELECT username, score FROM rankings " +
                        "ORDER BY score DESC LIMIT 10";
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    int score = resultSet.getInt("score");
                    RankingEntry entry = new RankingEntry(username, score);
                    rankingEntries.add(entry);
                }
                DatabaseConnector.closeConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rankingEntries;
    }
}
