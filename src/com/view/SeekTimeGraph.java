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
    public Timer timer;  // Timer to control the drawing interval

    public void simulateGraph(int delay, InputPanel panel) {
        currentIndex = 0;  // Reset the current index
        timer = new Timer(delay, new ActionListener() {
            long startTime = System.currentTimeMillis();
            @Override
            public void actionPerformed(ActionEvent e) {
                // Increment the current index
                currentIndex++;

                // Repaint the graph to update the drawing
                repaint();

                long elapsedTime = System.currentTimeMillis() - startTime;
                long seconds = (elapsedTime / 1000) % 60;
                String time = String.format("%02d:%02d", seconds / 60, seconds % 60);
                panel.getTimerLabel().setText(time);

                // Check if the simulation has reached the end
                if (currentIndex >= queue.length) {
                    timer.stop();
                    panel.getRunButton().setVisible(true);
                    panel.getPauseButton().setVisible(false);
                }
            }
        });

        timer.start();  // Start the timer
    }

    public void stopSimulation(InputPanel panel) {
        if (timer != null && timer.isRunning()) {
            timer.stop();
            panel.getRunButton().setVisible(true);
            panel.getPauseButton().setVisible(false);
        }
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

        // Enable anti-aliasing
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

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
        int arrowSize = 10;

        // Enable anti-aliasing
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Save the original stroke
        Stroke originalStroke = g2d.getStroke();

        // Draw the line
        g.drawLine(startX, startY, endX, endY);

        // Calculate the angle of the arrow
        double angle = Math.atan2(endY - startY, endX - startX);

        // Calculate the coordinates of the arrowhead points
        int x1 = (int) (endX - arrowSize * Math.cos(angle - Math.PI / 6));
        int y1 = (int) (endY - arrowSize * Math.sin(angle - Math.PI / 6));
        int x2 = (int) (endX - arrowSize * Math.cos(angle + Math.PI / 6));
        int y2 = (int) (endY - arrowSize * Math.sin(angle + Math.PI / 6));

        // Adjust the thickness of the arrow
        float arrowThickness = 2.0f;
        g2d.setStroke(new BasicStroke(arrowThickness));

        // Draw the arrowhead
        g.drawLine(endX, endY, x1, y1);
        g.drawLine(endX, endY, x2, y2);

        // Restore the original stroke
        g2d.setStroke(originalStroke);

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
