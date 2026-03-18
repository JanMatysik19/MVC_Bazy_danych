package Zadanie.Models;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TableModel extends AbstractTableModel {
    private List<TaskModel> data;
    private final String[] COLUMN_NAMES;

    public TableModel() {
        data = new ArrayList<>();
        COLUMN_NAMES = TaskModel._fetchColumnNames();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).getAt(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    public void setData(List<TaskModel> data) {
        this.data = data;
        fireTableDataChanged();
    }

    public TaskModel getTaskModel(int rowIndex) {
        return data.get(rowIndex);
    }
}
