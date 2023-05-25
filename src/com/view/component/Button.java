package view.component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class Button extends JButton {
    public Button(String text) {
        //midnight purple 34,0,48
        //pantone light purple 138,105,212
        //somewhat orange but darker 209, 151, 16
        this(text, new Color(209, 151, 16), new Color(34,0,48)); //dark violet default button color
    }

    public Button(String text, final Color firstColor, final Color secondColor) {

        setText(text);
        setBackground(firstColor);
        setForeground(Color.WHITE);
        setPreferredSize(new Dimension(300, 70));
        setHorizontalAlignment(CENTER); //set the button to center
        setSize(250, 50); //button size
        setFont(new Font("Georgia", Font.BOLD, 20)); //font definition
        setVisible(true);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AudioPlayer("click.wav").play(); //click sound
            }
        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent me) {
                me.getComponent().setBackground(secondColor); //illuminates the button when the mouse hovers on it
            }

            @Override
            public void mouseExited(MouseEvent me) {
                me.getComponent().setBackground(firstColor);
            }
        });

        // Make button look classy, edits shape and features Link: http://stackoverflow.com/questions/23698092/design-button-in-java-like-in-css
        setUI(new BasicButtonUI() {

            @Override
            public void installUI(JComponent c) {
                super.installUI(c);
                AbstractButton button = (AbstractButton) c;
                button.setOpaque(false);
                button.setBorder(new EmptyBorder(5, 15, 5, 15)); //padding
            }

            @Override
            public void paint(Graphics g, JComponent c) {
                AbstractButton b = (AbstractButton) c;
                paintBackground(g, b, b.getModel().isPressed() ? 2 : 0); //color changes when pressed
                super.paint(g, c);
            }

            private void paintBackground(Graphics g, JComponent c, int yOffset) {

                //paints the button background based on its shape, size and color
                Dimension size = c.getSize();
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(c.getBackground().darker()); //Darker background overlay on the button
                g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 60, 60);
                g.setColor(c.getBackground()); //lighter bg on the button
                g.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 5, 60, 60);
            }
        });

    }
}
