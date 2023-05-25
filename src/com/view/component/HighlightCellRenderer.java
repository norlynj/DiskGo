package view.component;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class HighlightCellRenderer extends DefaultTableCellRenderer {
    private int highlightRow;
    private int highlightColumn;
    private int totalRows;
    Color color;

    public HighlightCellRenderer(int highlightRow, int highlightColumn, int totalRows, boolean hit) {
        this.highlightRow = highlightRow;
        this.highlightColumn = highlightColumn;
        this.totalRows = totalRows;
        if (hit) {
            color = new Color(218, 247, 166);
        } else {
            color = new Color(255, 87, 51);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Calculate the reversed row index
        int reversedRow = totalRows - row - 1;

        // Check if the current cell matches the highlight cell
        if (reversedRow == highlightRow && column == highlightColumn) {
            cellComponent.setBackground(color); // Set the background color to yellow for the highlighted cell
        } else {
            cellComponent.setBackground(table.getBackground()); // Reset the background color for other cells
        }
        setHorizontalAlignment(SwingConstants.CENTER);

        return cellComponent;
    }
}
