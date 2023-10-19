package Ranking;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import welcome.WelcomePanel;
import database.DatabaseConnector;

public class RankingPanel extends JPanel {
    private JFrame frame;

    public RankingPanel(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        // Create a table to display the ranking
        JTable rankingTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(rankingTable);
        add(scrollPane, BorderLayout.CENTER);

        // Retrieve and display the ranking
        List<RankingEntry> rankingEntries = getRankingDataFromDatabase();
        RankingTableModel model = new RankingTableModel(rankingEntries);
        rankingTable.setModel(model);

        JButton backButton = new JButton("Back to Welcome");
        backButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            WelcomePanel welcomePanel = new WelcomePanel(frame);
            frame.getContentPane().add(welcomePanel);
            frame.revalidate();
            frame.repaint();
        });
        add(backButton, BorderLayout.SOUTH);
    }

    private List<RankingEntry> getRankingDataFromDatabase() {
        List<RankingEntry> rankingEntries = new ArrayList<>();
        try {
            Connection connection = DatabaseConnector.connect();
            if (connection != null) {
                Statement statement = connection.createStatement();
                String query = "SELECT username, score FROM game_logs " +
                        "INNER JOIN player_profiles ON game_logs.player_id = player_profiles.id " +
                        "ORDER BY score DESC LIMIT 10"; // Retrieve the top 10 scores
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
