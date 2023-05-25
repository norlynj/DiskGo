package view.component;

import javax.swing.table.DefaultTableModel;
import java.util.HashSet;
import java.util.Set;

public class CustomTableModel extends DefaultTableModel {

    public CustomTableModel() {
        this(10, 4); // 2 extra rows for page and status
    }
    public CustomTableModel(int columnCount, int rowCount) {
        super(rowCount+2, columnCount); // 2 extra rows for page and status
    }

    @Override
    public void setNumRows(int rowCount) {
        super.setNumRows(rowCount+2);
    }

    public void resetTable() {
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                setValueAt(null, i, j);
            }
        }
    }

}