package view.component;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CustomDropDown extends JComboBox<String> {

    private Color backgroundColor = new Color(115, 200, 177);
    private Color highlightColor = new Color(187, 255, 255);
    private int cornerRadius = 15;

    public CustomDropDown(String[] items) {
        super(items);
        setUI(new CustomDropDownUI());
        setRenderer(new CustomDropDownRenderer());
        setOpaque(false);
        setFocusable(false);
        setBackground(backgroundColor);
        setFont(new Font("Montserrat", Font.BOLD, 18));
    }

    private class CustomDropDownUI extends BasicComboBoxUI {

        @Override
        protected JButton createArrowButton() {
            return new ArrowButton(BasicArrowButton.SOUTH);
        }

        @Override
        protected ComboPopup createPopup() {
            return new CustomComboPopup(comboBox);
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = c.getWidth();
            int height = c.getHeight();

            int arc = 2 * cornerRadius; // Calculate the arc diameter

            Shape shape = new RoundRectangle2D.Double(0, 0, width - 1, height - 1, arc, arc);
            g2d.setColor(backgroundColor);
            g2d.fill(shape);

            // Adjust the display rectangle
            Rectangle displayRect = rectangleForCurrentValue();
            displayRect.x += cornerRadius;
            displayRect.width -= cornerRadius + arrowButton.getWidth();

            // Paint the text
            g2d.setColor(comboBox.isEnabled() ? comboBox.getForeground() : Color.GRAY);
            FontMetrics fm = g2d.getFontMetrics();
            Object selectedItem = comboBox.getSelectedItem();
            if (selectedItem != null) {
                String displayText = selectedItem.toString();
                int textX = displayRect.x + 5;
                int textY = displayRect.y + ((displayRect.height - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(displayText, textX, textY);
            }

            g2d.dispose();
        }
    }

    private class CustomComboPopup extends BasicComboPopup {

        public CustomComboPopup(JComboBox combo) {
            super(combo);
            setOpaque(true);
            setBackground(backgroundColor);
        }

        @Override
        public void show() {
            setBackground(highlightColor);
            super.show();
        }

        @Override
        public void hide() {
            setBackground(backgroundColor);
            super.hide();
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());
        }
    }

    private class CustomDropDownRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setHorizontalAlignment(SwingConstants.CENTER);
            setBackground(isSelected ? highlightColor : backgroundColor);
            setFont(new Font("Montserrat", Font.BOLD, 14));
            return this;
        }
    }

    private class ArrowButton extends BasicArrowButton {
        public ArrowButton(int direction) {
            super(direction, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
        }

        @Override
        public void paint(Graphics g) {
            // Override the paint method to draw the arrow without a background
            Dimension size = getSize();
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.WHITE);
            int x = (size.width - 4) / 2;
            int y = (size.height - 8) / 2;
            int w = 10;
            int h = 10;
            g2d.fillPolygon(new int[]{x, x + w, x + (w / 2)}, new int[]{y, y, y + h}, 3);
            g2d.dispose();
        }
    }
}
