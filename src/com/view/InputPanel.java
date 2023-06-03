package view;

import model.*;
import view.component.*;
import view.component.Frame;
import view.component.Label;
import view.component.ScrollBar;
import view.component.Panel;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;

public class InputPanel extends Panel {
    Color bgColor = new Color(248, 241, 226);
    private ImageButton musicOnButton, musicOffButton, homeButton;
    private CustomDropDown algorithmChoice;
    private ImageButton importButton, randomizeButton, runButton, pauseButton, saveButton;
    private JTextField requestQueueField, headField;
    private JSlider slider;
    private JLabel timerLabel, totalSeekTimeLabel;
    private JPanel  resultsPanel;
    private JScrollPane  resultsPane;
    private JScrollPane[]  scrollPanes;
    private DiskScheduler[] diskScheduler;
    private String[] graphTitles;
    private JLabel[] graphLabels;
    private SeekTimeGraph[] graphs;
    private RequestQueue requestQueue = new RequestQueue();
    private boolean validQueue = false, validHead = false;

    public InputPanel() {
        super("bg/input-panel.png");
        initializeButtons();
        initializeAlgorithmComboBox();
        initializeTextFields();
        initializeLabels();
        initializeSchedulers();
        initializeGraphs();
        initializeSlider();
        setListeners();
        addComponentsToFrame();
    }

    private void initializeButtons() {
        musicOnButton = createImageButton("buttons/volume-on.png", 956, 35, 47, 47);
        musicOffButton = createImageButton("buttons/volume-off.png", 956, 35, 47, 47);
        homeButton = createImageButton("buttons/home.png", 1017, 35, 47, 47);
        musicOffButton.setVisible(false);
        importButton = createImageButton("buttons/from-text.png", 18, 129, 58, 58);
        randomizeButton = createImageButton("buttons/randomize.png", 31, 209, 58, 58);
        runButton = createImageButton("buttons/run.png", 1013, 141, 58, 58);
        pauseButton = createImageButton("buttons/pause.png", 1013, 141, 58, 58);
        saveButton = createImageButton("buttons/save.png", 1029, 222, 58, 58);
        pauseButton.setVisible(false);
    }

    private ImageButton createImageButton(String imagePath, int x, int y, int width, int height) {
        ImageButton button = new ImageButton(imagePath);
        button.setBounds(x, y, width, height);
        return button;
    }

    private void initializeAlgorithmComboBox() {
        algorithmChoice = new CustomDropDown(new String[]{"Simulate all", "FCFS", "SSTF", "SCAN", "C-SCAN", "LOOK", "C-LOOK"});
        algorithmChoice.setBounds(218, 133, 150, 44);
    }

    private void initializeTextFields() {
        requestQueueField = createTextField("", 2, "requestQueueField", 218, 85, 664, 40);
        headField = createTextField("", 2, "headField", 734, 136, 148, 41);
    }

    private JTextField createTextField(String text, int columns, String name, int x, int y, int width, int height) {
        JTextField textField = new JTextField(text, columns);
        textField.setName(name);
        textField.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setFont(new Font("Montserrat", Font.BOLD, 20));
        textField.setBounds(x, y, width, height);
        textField.setToolTipText("Length must be 10-40. Value must be between 0 and 20");
        return textField;
    }

    private void initializeLabels() {
        graphTitles = new String[]{"FCFS", "SSTF", "SCAN", "C-SCAN", "LOOK", "C-LOOK"};
        graphLabels = new Label[graphTitles.length];
        timerLabel = new Label("00:00");
        timerLabel.setBounds(325, 205, 93, 32);
        totalSeekTimeLabel = new Label("");
        totalSeekTimeLabel.setBounds(427, 255, 93, 32);
    }

    private void initializeSchedulers() {
        diskScheduler = new DiskScheduler[6];
        diskScheduler[0] = new FCFS();
        diskScheduler[1] = new SSTF();
        diskScheduler[2] = new SCAN();
        diskScheduler[3] = new CSCAN();
        diskScheduler[4] = new LOOK();
        diskScheduler[5] = new CLOOK();
        for (int i = 0; i < diskScheduler.length; i++) {
            diskScheduler[i].setRequestQueue(requestQueue);
        }
    }

    private void initializeGraphs() {
        graphs = new SeekTimeGraph[6];
        graphLabels = new Label[6];
        scrollPanes = new JScrollPane[6];
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        
        for (int i = 0; i < graphs.length; i++) {
            graphLabels[i] = new Label(graphTitles[i] + " | Total Seek Time: ");
            graphLabels[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            graphLabels[i].setBackground(bgColor);
            resultsPanel.add(graphLabels[i]);
            graphs[i] = new SeekTimeGraph();
            graphs[i].setBackground(bgColor);

            scrollPanes[i] = new JScrollPane(graphs[i]);

            resultsPanel.add(scrollPanes[i]);

            // Add some vertical space between tables
            scrollPanes[i].setPreferredSize(new Dimension(scrollPanes[i].getPreferredSize().width, 385));
            scrollPanes[i].setBorder(BorderFactory.createEmptyBorder());
        }
        resultsPane = new JScrollPane(resultsPanel);
        ScrollBar sbV = new ScrollBar();
        ScrollBar sbH = new ScrollBar();
        sbH.setOrientation(JScrollBar.HORIZONTAL);

        resultsPane.setVerticalScrollBar(sbV);
        resultsPane.setHorizontalScrollBar(sbH);
        resultsPane.setBorder(BorderFactory.createEmptyBorder());
        resultsPane.setBounds(70, 340, 1000, 400);
        resultsPanel.setBackground(bgColor);
    }

    private void setListeners() {
        //hover state
        musicOnButton.hover("buttons/volume-off-hover.png", "buttons/volume-on.png");
        musicOffButton.hover("buttons/volume-on-hover.png", "buttons/volume-off.png");
        homeButton.hover("buttons/home-hover.png", "buttons/home.png");
        importButton.hover("buttons/from-text-hover.png", "buttons/from-text.png");
        randomizeButton.hover("buttons/randomize-hover.png", "buttons/randomize.png");
        runButton.hover("buttons/run-hover.png", "buttons/run.png");
        saveButton.hover("buttons/save-hover.png", "buttons/save.png");

        musicOnButton.addActionListener(e -> {
            musicOnButton.setVisible(false);
            musicOffButton.setVisible(true);
        });

        musicOffButton.addActionListener(e -> {
            musicOffButton.setVisible(false);
            musicOnButton.setVisible(true);
            // Handle music off button click
        });

        // associate each algo with its corresponding index and make the pane and label visible accordingly
        Map<String, Integer> algorithmMap = new HashMap<>();
        algorithmMap.put("Simulate all", 0);
        algorithmMap.put("FCFS", 1);
        algorithmMap.put("SSTF", 2);
        algorithmMap.put("SCAN", 3);
        algorithmMap.put("C-SCAN", 4);
        algorithmMap.put("LOOK", 5);
        algorithmMap.put("C-LOOK", 6);

        algorithmChoice.addActionListener(e -> {
            String selectedAlgorithm = (String) Objects.requireNonNull(algorithmChoice.getSelectedItem());
            int selectedIndex = algorithmMap.getOrDefault(selectedAlgorithm, -1);

            for (int i = 0; i < graphs.length; i++) {
                if (selectedIndex == 0) {
                    totalSeekTimeLabel.setText("");
                    scrollPanes[i].setVisible(true);
                    graphLabels[i].setVisible(true);
                } else {
                    scrollPanes[i].setVisible(i == (selectedIndex - 1));
                    graphLabels[i].setVisible(false);
                }
                if (diskScheduler[i] != null) {
                    diskScheduler[i].setRequestQueue(requestQueue);
                }
            }
        });

        importButton.addActionListener(e -> {
            FileReader fr = new FileReader();
            try {
                if (fr.readInputFromFile()) {
                    requestQueue.setRequestQueue(fr.getQueue());
                    requestQueue.setHead(fr.getHeadStartsAt());
                    headField.setText(String.valueOf(fr.getHeadStartsAt()));
                    requestQueueField.setText(fr.getQueueAsString());
                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        randomizeButton.addActionListener(e -> {
            requestQueue.randomizeQueue();
            requestQueue.getRandomizeHead();
            requestQueueField.setText(requestQueue.getQueueAsString());
            headField.setText(String.valueOf(requestQueue.getHead()));
            validQueue = true;
            validHead = true;
        });

        runButton.addActionListener(e -> {
            int selectedIndex = algorithmChoice.getSelectedIndex();

            if (validHead && validQueue) {
                pauseButton.setVisible(true);
                runButton.setVisible(false);
                for (int i = 0; i < diskScheduler.length; i++) {
                    graphs[i].setInitialPointer(requestQueue.getHead());
                    graphs[i].setCylinders(requestQueue.getCylinder());
                    int[] queueCopy = Arrays.copyOf(diskScheduler[i].simulate(), diskScheduler[i].simulate().length);
                    graphs[i].setQueue(queueCopy);
                    graphs[i].simulateGraph(slider.getValue(), this, i, selectedIndex == 0, (selectedIndex - 1) == i);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cannot run the program. Input is invalid.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        pauseButton.addActionListener(e -> {
            for (int i = 0; i < graphTitles.length; i++) {
                if (graphs[i].timer != null && graphs[i].timer.isRunning()) {
                    graphs[i].timer.stop();
                }
            }
            runButton.setVisible(true);
            pauseButton.setVisible(false);
        });

        saveButton.addActionListener(e -> {
            if (validHead && validQueue) {
                new Export().saveResults(resultsPanel, graphs, graphLabels, graphs[0].getQueue(), requestQueue.getHead());
            } else {
                JOptionPane.showMessageDialog(null, "Cannot save the results since the program is not yet ran", "No results yet", JOptionPane.ERROR_MESSAGE);
            }
        });

        inputValidator(requestQueueField);
        inputValidator(headField);
    }

    private void inputValidator(JTextField input) {
        input.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateInput();
                adjustPaneSize();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                validateInput();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                validateInput();
            }

            private void validateInput() {
                try {
                    String str = input.getText();
                    if (input.getName().equals("headField")) {
                        int value = Integer.parseInt(str);
                        if (value < 0 || value > requestQueue.getCylinder() - 1) {
                            // If the value is out of range, highlight the text field
                            invalidate(true);
                        } else {
                            input.setBackground(UIManager.getColor("TextField.background"));
                            requestQueue.setHead(value);
                            validHead = true;
                        }
                    } else if(input.getName().equals("requestQueueField")) {
                        // check 3 things here: input is a comma-separated list of integers with a space after each comma,  length must be bet 0-40, string value must be between 0-199
                        if (str.matches("\\d+(,\\s\\d+)*")) {
                            String[] parts = str.split(",\\s");
                            if (parts.length >= 0 &&
                                    parts.length <= 40 &&
                                    parts[0].matches("\\d+") &&
                                    parts[parts.length-1].matches("\\d+")) {
                                // Split the input into an array of integers
                                String[] nums = str.split(",\\s");
                                int[] numList = new int[nums.length];
                                for (int i = 0; i < nums.length; i++) {
                                    String num = nums[i];
                                    int value = Integer.parseInt(num);
                                    // Check that each integer value in the input is between 0 and 199
                                    if (value < 0 || value > requestQueue.getCylinder() - 1) {
                                        invalidate(false);
                                    } else {
                                        input.setBackground(UIManager.getColor("TextField.background"));
                                        numList[i] = value;
                                    }
                                }
                                requestQueue.setRequestQueue(numList);
                                validQueue = true;
                            } else {
                                invalidate(false);
                            }
                        } else {
                            invalidate(false);
                        }
                    }
                } catch (NumberFormatException ex) {
                    invalidate(false);
                }
            }
            private void invalidate(boolean head) {
                input.setBackground(new Color(255, 202, 202));
                if (head) {
                    validHead = false;
                } else {
                    validQueue = false;
                }
            }

            private void adjustPaneSize() {
                for (int i = 0; i < scrollPanes.length; i++){
                    if (requestQueue.getRequestQueue() != null && requestQueue.getRequestQueue().length <= 10) {
                        scrollPanes[i].setPreferredSize(new Dimension(800, 385));
                    } else if (requestQueue.getRequestQueue() != null && requestQueue.getRequestQueue().length <= 20) {
                        scrollPanes[i].setPreferredSize(new Dimension(1000, 500));
                    } else if (requestQueue.getRequestQueue() != null && requestQueue.getRequestQueue().length <= 30) {
                        scrollPanes[i].setPreferredSize(new Dimension(1300, 800));
                    } else if (requestQueue.getRequestQueue() != null && requestQueue.getRequestQueue().length <= 40) {
                        scrollPanes[i].setPreferredSize(new Dimension(1500, 800));
                    }
                }
            }
        });
    }

    private void initializeSlider() {
        // slider and timer
        slider = new JSlider();
        slider.setUI(new CircleSliderUI(slider));
        slider.setBounds(420, 209, 246, 21);
    }

    private void addComponentsToFrame() {
        this.add(musicOnButton);
        this.add(musicOffButton);
        this.add(homeButton);
        this.add(requestQueueField);
        this.add(headField);
        this.add(algorithmChoice);
        this.add(importButton);
        this.add(randomizeButton);
        this.add(runButton);
        this.add(pauseButton);
        this.add(saveButton);
        this.add(slider);
        this.add(timerLabel);
        this.add(totalSeekTimeLabel);
        this.add(resultsPane);
    }

    private void simulate() {

    }

    public static void main(String[] args) {

        InputPanel m = new InputPanel();
        Frame frame = new Frame("Input Panel");
        frame.add(m);
        frame.setVisible(true);
    }

    public void musicClick() {
        if (musicOffButton.isVisible()){
            musicOnButton.setVisible(true);
            musicOffButton.setVisible(false);
        } else {
            musicOnButton.setVisible(false);
            musicOffButton.setVisible(true);
        }
    }

    public ImageButton getMusicOnButton() {
        return musicOnButton;
    }
    public ImageButton getMusicOffButton() {
        return musicOffButton;
    }
    public ImageButton getHomeButton() {
        return homeButton;
    }

    public ImageButton getRunButton() {
        return runButton;
    }

    public ImageButton getPauseButton() {
        return pauseButton;
    }

    public JLabel getTimerLabel() {
        return timerLabel;
    }

    public JLabel getTotalSeekTimeLabel() {
        return totalSeekTimeLabel;
    }

    public JLabel[] getGraphLabels() {
        return graphLabels;
    }

    public String[] getGraphTitles() {
        return graphTitles;
    }
}
