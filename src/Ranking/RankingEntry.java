package Ranking;

public class RankingEntry {
    private String username;
    private int score;

    public RankingEntry(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
}
