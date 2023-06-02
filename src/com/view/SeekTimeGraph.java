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
    private int totalSeekTime;

    public SeekTimeGraph() {
    }

    public void simulateGraph(int delay, InputPanel panel, int simulatorNumber, boolean simulateAll) {
        currentIndex = 0;  // Reset the current index
        timer = new Timer(delay, new ActionListener() {
            long startTime = System.currentTimeMillis();
            @Override
            public void actionPerformed(ActionEvent e) {
                // Repaint the graph to update the drawing
                repaint();

                long elapsedTime = System.currentTimeMillis() - startTime;
                long seconds = (elapsedTime / 1000) % 60;
                String time = String.format("%02d:%02d", seconds / 60, seconds % 60);
                panel.getTimerLabel().setText(time);

                // Check if the simulation has reached the end
                if (currentIndex + 1 >= queue.length) {
                    timer.stop();
                    panel.getRunButton().setVisible(true);
                    panel.getPauseButton().setVisible(false);
                } else {
                    System.out.println(totalSeekTime);
                    totalSeekTime += Math.abs(queue[currentIndex] - queue[currentIndex+1]);
                    System.out.println(queue[currentIndex] + "-" + queue[currentIndex+1]);
                    if (!simulateAll) {
                        panel.getTotalSeekTimeLabel().setText(String.valueOf(totalSeekTime));
                    }
                    panel.getGraphLabels()[simulatorNumber].setText(panel.getGraphTitles()[simulatorNumber] + " | Total Seek Time: " + totalSeekTime);
                    // Increment the current index
                    currentIndex++;
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (queue == null)
            return;

        int h = getHeight();
        int w = getWidth();

        float verticalStep = (float) h / (float) (queue.length + 2);
        float horizontalStep = (float) (w - circleSize) / (float) cylinders;

        // Enable anti-aliasing
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < queue.length; i++) {

            // Draw the string above the line
            FontMetrics fontMetrics = g.getFontMetrics();
            int stringWidth = fontMetrics.stringWidth(String.valueOf(queue[i]));
            int stringX = (int) (queue[i] * horizontalStep) + (circleSize / 2) - (stringWidth / 2);
            int stringY = 10; // Adjust the vertical position of the string, e.g., 10 pixels above the line
            g.drawString(String.valueOf(queue[i]), stringX, stringY);
            g.drawLine((int) (queue[i] * horizontalStep) + (circleSize / 2), 15, (int) (queue[i] * horizontalStep) + (circleSize / 2), 25);
        }

        g.setColor(bgColor);
        g.drawLine(0, 25, w, 25);

        g.drawString(String.valueOf(initialPointer), (int) (initialPointer * horizontalStep) + circleSize, 20);

        // Draw arrow from initial pointer to first queue element
        int startX = (int) (initialPointer * horizontalStep) + (circleSize / 2);
        int startY = 25;
        int endX = (int) (queue[0] * horizontalStep) + (circleSize / 2);
        int endY = (int) (2 * verticalStep);

        g.drawLine(startX, startY, endX, endY);
        g.fillOval(startX - (circleSize / 2), startY - (circleSize / 2), circleSize, circleSize);

        for (int i = 0; i < currentIndex; i++) {
            // Draw only up to the current index
            startX = (int) (queue[i] * horizontalStep) + (circleSize / 2);
            startY = (int) ((i + 2) * verticalStep);
            endX = (int) (queue[i + 1] * horizontalStep) + (circleSize / 2);
            endY = (int) ((i + 3) * verticalStep);

            // Calculate the center point of the line
            int centerX = (startX + endX) / 2;
            int centerY = (startY + endY) / 2;

            // Calculate the angle of the line
            double angle = Math.atan2(endY - startY, endX - startX);

            // Coordinates
            g.drawString(String.valueOf(queue[i]), (int) (queue[i] * horizontalStep) + circleSize, (int) ((i + 1.5) * verticalStep));

            // Draw the line and circle
            g.drawLine(startX, startY, endX, endY);
            g.fillOval(startX - (circleSize / 2), startY - (circleSize / 2), circleSize, circleSize);
        }
        int lastIndex = currentIndex;
        int lastX = (int) (queue[lastIndex] * horizontalStep) + (circleSize / 2);
        int lastY = (int) ((lastIndex + 2) * verticalStep);
        // Coordinates
        g.drawString(String.valueOf(queue[lastIndex]), (int) (queue[lastIndex] * horizontalStep) + circleSize, (int) ((lastIndex + 1.5) * verticalStep));
        g.fillOval(lastX - (circleSize / 2), lastY - (circleSize / 2), circleSize, circleSize);
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
        totalSeekTime = Math.abs(initialPointer - queue[0]);
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
