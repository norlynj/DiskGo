package com.model;

import javax.swing.table.DefaultTableModel;
import java.util.HashSet;
import java.util.Set;

public class CustomTableModel extends DefaultTableModel {

    private Set<Integer> priorityNumbers = new HashSet<>();
    private int maxBurstTime = 30;
    private int maxArrivalTime = 30;
    private int maxPriorityNumber = 20;
    private int maxTimeQuantum = 10;

    public CustomTableModel(String[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Integer.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column != 0;
    }

    @Override
    public Object getValueAt(int row, int col) {
        // Set value of column 0 to row number
        if (col == 0) {
            return "P" + (row + 1);
        }
        return super.getValueAt(row, col);
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        if (value == null || value.toString().isEmpty()) {
            super.setValueAt(null, row, column);
            return;
        }

        try {
            int intValue = Integer.parseInt(value.toString());

            if (intValue < 1 || (column == 1 && intValue > maxBurstTime) ||
                    (column == 2 && intValue > maxArrivalTime) ||
                    (column == 3 && (intValue < 1 || intValue > maxPriorityNumber || priorityNumbers.contains(intValue))) ||
                    (column == 4 && intValue > maxTimeQuantum)) {
                fireTableCellUpdated(row, column);
                return;
            }

            if (column == 3) {
                priorityNumbers.remove(getValueAt(row, column));
                priorityNumbers.add(intValue);
            }

            super.setValueAt(intValue, row, column);

        } catch (NumberFormatException e) {
            fireTableCellUpdated(row, column);
        }
    }

    public void resetTable() {
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 1; j < getColumnCount(); j++) {
                setValueAt(null, i, j);
            }
        }
        priorityNumbers.clear();
    }

}