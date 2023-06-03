package view.component;
import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CircleSliderUI extends BasicSliderUI {

    private final Color thumbColor = new Color(115, 200, 177);

    public CircleSliderUI(JSlider slider) {
        super(slider);
        slider.setBackground(new Color(231, 205, 194));
        slider.setFocusable(false); // Disable focus painting

        slider.setMinimum(0);
        slider.setMaximum(5000);
        slider.setInverted(true);

    }

    protected Dimension getThumbSize() {
        return new Dimension(20, 20); // Adjust the size as needed
    }

    @Override
    public void paintThumb(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(thumbColor);
        g2.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
    }

    @Override
    public void paintTrack(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GRAY);
        if (slider.getOrientation() == JSlider.VERTICAL) {
            g2.fillRoundRect(slider.getWidth() / 2 - 2, 2, 4, slider.getHeight(), 1, 1);
        } else {
            g2.fillRoundRect(2, slider.getHeight() / 2 - 2, slider.getWidth() - 5, 4, 1, 1);
        }
    }
}