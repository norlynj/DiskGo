package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeekTimeGraph extends JPanel {
    private Color bgColor = new Color(64, 64, 64);
    private static final int circleSize = 10;
    private int initialPointer;
    private int[] queue;
    private int cylinders;
    private int currentIndex;  // Current index to track the progress of simulation
    private Timer timer;  // Timer to control the drawing interval
    private int delay = 500;  // Delay between each update in milliseconds

    public void simulateGraph() {
        currentIndex = 0;  // Reset the current index
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Increment the current index
                currentIndex++;

                // Repaint the graph to update the drawing
                repaint();

                // Check if the simulation has reached the end
                if (currentIndex >= queue.length) {
                    timer.stop();
                }
            }
        });

        timer.start();  // Start the timer
    }
    public SeekTimeGraph() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (queue == null)
            return;

        int h = getHeight();
        int w = getWidth();

        float verticalStep = (float) h / (float) (queue.length + 2);
        float horizontalStep = (float)(w - circleSize) / (float) cylinders;

        g.setColor(bgColor);

        g.drawLine(0, 25, w, 25);

        g.drawString(String.valueOf(initialPointer), (int) (initialPointer * horizontalStep) + circleSize, 20);
        g.drawLine((int) (initialPointer * horizontalStep) + (circleSize / 2), 0, (int) (initialPointer * horizontalStep) + (circleSize / 2), 25);

        for (int i = 0; i < queue.length - 1; i++) {
            g.drawString(String.valueOf(queue[i]), (int) (queue[i] * horizontalStep) + circleSize, 20);
            g.drawLine((int) (queue[i] * horizontalStep) + (circleSize / 2), 0, (int) (queue[i] * horizontalStep) + (circleSize / 2), 25);
        }

        g.setColor(bgColor);

        g.fillOval((int) (initialPointer * horizontalStep), (int) verticalStep - (circleSize / 2), circleSize, circleSize);
        g.drawLine((int) (initialPointer * horizontalStep) + (circleSize / 2), (int) verticalStep, (int) (queue[0] * horizontalStep)  + (circleSize / 2), (int) (2 * verticalStep));

        for (int i = 0; i < currentIndex - 1; i++) {
            // Draw only up to the current index
            int startX = (int) (queue[i] * horizontalStep) + (circleSize / 2);
            int startY = (int) ((i + 2) * verticalStep);
            int endX = (int) (queue[i + 1] * horizontalStep) + (circleSize / 2);
            int endY = (int) ((i + 3) * verticalStep);

            drawArrow(g, startX, startY, endX, endY);
        }
    }

    private void drawArrow(Graphics g, int startX, int startY, int endX, int endY) {
        // Calculate the arrowhead size
        int arrowSize = 8;

        // Calculate the angle of the arrow
        double angle = Math.atan2(endY - startY, endX - startX);

        // Calculate the coordinates of the arrowhead points
        int x1 = (int) (endX - arrowSize * Math.cos(angle - Math.PI / 6));
        int y1 = (int) (endY - arrowSize * Math.sin(angle - Math.PI / 6));
        int x2 = (int) (endX - arrowSize * Math.cos(angle + Math.PI / 6));
        int y2 = (int) (endY - arrowSize * Math.sin(angle + Math.PI / 6));

        // Draw the line
        g.drawLine(startX, startY, endX, endY);

        // Draw the arrowhead
        g.drawLine(endX, endY, x1, y1);
        g.drawLine(endX, endY, x2, y2);
    }

    public int getInitialPointer() {
        return initialPointer;
    }

    public void setInitialPointer(int initialPointer) {
        this.initialPointer = initialPointer;
        repaint();
    }

    public int[] getQueue() {
        return queue;
    }

    public void setQueue(int[] queue) {
        this.queue = queue;
        repaint();
    }

    public int getCylinders() {
        return cylinders;
    }

    public void setCylinders(int cylinders) {
        this.cylinders = cylinders;
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(200, 100);
    }
}
