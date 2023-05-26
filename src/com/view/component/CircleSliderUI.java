package view.component;
import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CircleSliderUI extends BasicSliderUI {

    private Color thumbColor = new Color(219, 220, 222);
    private Color borderColor = Color.black;

    public CircleSliderUI(JSlider slider) {
        super(slider);
        this.thumbColor = thumbColor;
        this.borderColor = borderColor;
        slider.setBackground(new Color(231, 205, 194));
        slider.setFocusable(false); // Disable focus painting

        slider.setMinimum(0);
        slider.setMaximum(5000);
        slider.setInverted(true);

    }

    protected Dimension getThumbSize() {
        return new Dimension(16, 16); // Adjust the size as needed
    }

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = thumbRect.x;
        int y = thumbRect.y;
        int width = thumbRect.width;
        int height = thumbRect.height;
        int borderSize = 2; // Adjust the border size as needed

        // Draw the border
        g2d.setColor(borderColor);
        g2d.fillOval(x - borderSize, y - borderSize, width + borderSize * 2, height + borderSize * 2);

        // Draw the circle thumb
        g2d.setColor(thumbColor);
        g2d.fillOval(x, y, width, height);
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.GRAY); // Customize track color
        g2d.setStroke(new BasicStroke(4)); // Customize track thickness
        g2d.drawLine(trackRect.x, trackRect.y + trackRect.height / 2,
                trackRect.x + trackRect.width, trackRect.y + trackRect.height / 2);
    }
}