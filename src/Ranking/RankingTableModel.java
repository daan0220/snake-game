package Ranking; // クラスのパッケージ名に合わせて調整

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class RankingTableModel extends AbstractTableModel {
    private List<RankingEntry> data;
    private String[] columnNames = {"Rank", "Username", "Score"};

    public RankingTableModel(List<RankingEntry> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RankingEntry entry = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1; // Rank starts from 1
            case 1:
                return entry.getUsername();
            case 2:
                return entry.getScore();
            default:
                return null;
        }
    }
}
