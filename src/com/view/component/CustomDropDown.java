package view.component;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

        private boolean isButtonHovered = false;

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

            // Add a hover effect when the mouse is over the button
            if (isButtonHovered) {
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fill(shape);
            }

            g2d.dispose();
        }

        @Override
        protected JButton createArrowButton() {
            JButton arrowButton = super.createArrowButton();
            arrowButton.setContentAreaFilled(false); // Remove the button background
            arrowButton.setBorder(BorderFactory.createEmptyBorder()); // Remove the button border
            arrowButton.setIcon(new ArrowIcon()); // Set a custom icon for the arrow button
            arrowButton.setPreferredSize(new Dimension(20, arrowButton.getPreferredSize().height)); // Adjust the button width
            arrowButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isButtonHovered = true;
                    comboBox.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isButtonHovered = false;
                    comboBox.repaint();
                }
            });

            return arrowButton;
        }
    }

    private class ArrowIcon implements Icon {
        private static final int WIDTH = 8;
        private static final int HEIGHT = 4;

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.WHITE); // Set arrow color to white
            int startX = x + (getIconWidth() - WIDTH) / 2;
            int startY = y + (getIconHeight() - HEIGHT) / 2;
            int[] xPoints = {startX, startX + WIDTH, startX + WIDTH / 2};
            int[] yPoints = {startY, startY, startY + HEIGHT};
            g2d.fillPolygon(xPoints, yPoints, 3);
            g2d.dispose();
        }

        @Override
        public int getIconWidth() {
            return WIDTH;
        }

        @Override
        public int getIconHeight() {
            return HEIGHT;
        }
    }

    private class CustomDropDownRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component rendererComponent = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            ((JLabel) rendererComponent).setHorizontalAlignment(SwingConstants.CENTER);
            rendererComponent.setBackground(isSelected ? highlightColor : backgroundColor);
            rendererComponent.setFont(new Font("Montserrat", Font.BOLD, 14));
            return rendererComponent;
        }
    }
}
