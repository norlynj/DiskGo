package view;

import model.*;
import view.component.*;
import view.component.Frame;
import view.component.Label;
import view.component.HighlightCellRenderer;
import view.component.Panel;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

public class InputPanel extends Panel {
    Color bgColor = new Color(248, 241, 226);
    private ImageButton musicOnButton, musicOffButton, homeButton;
    private CustomDropDown algorithmChoice;
    private ImageButton importButton, randomizeButton, runButton, pauseButton, saveButton;
    private JTextField requestQueueField, headField;
    private JSlider slider;
    private JLabel timerLabel, totalSeekTimeLabel;
    private JPanel graphsPanel;
    private DiskScheduler diskScheduler;
    private SeekTimeGraph graph;
    private boolean validQueue = false, validHead = false;

    public InputPanel() {
        super("bg/input-panel.png");
        diskScheduler = new FCFS();
        graph = new SeekTimeGraph();
        graphsPanel = graph;

        initializeButtons();
        initializeAlgorithmComboBox();
        initializeTextFields();
        initializeLabels();
        initializeOutput();
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
        algorithmChoice = new CustomDropDown(new String[]{"FCFS", "SSTF", "SCAN", "C-SCAN", "LOOK", "C-LOOK"});
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
        timerLabel = new Label("00:00");
        timerLabel.setBounds(325, 205, 93, 32);
        totalSeekTimeLabel = new Label("0");
        totalSeekTimeLabel.setBounds(427, 255, 93, 32);
    }

    private void initializeOutput() {
        graphsPanel.setBounds(100, 347, 879, 381);
        graphsPanel.setBackground(bgColor);
    }

    private void showAllTables() {



    }


    private void showOneTable(){

    }

    public void resetTables() {


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

        algorithmChoice.addActionListener(e -> {
            String s = (String) Objects.requireNonNull(algorithmChoice.getSelectedItem());
            int[] existingQueue = diskScheduler.getRequestQueue();
            int existingHead = diskScheduler.getHead();
            if (s.equals("FCFS")) {
                diskScheduler = new FCFS();
            } else if (s.equals("SSTF")) {
                diskScheduler = new SSTF();
            } else if (s.equals("SCAN")) {
                diskScheduler = new SCAN();
            } else if (s.equals("C-SCAN")) {
                diskScheduler = new CSCAN();
            } else if (s.equals("LOOK")) {
                diskScheduler = new LOOK();
            } else if (s.equals("C-LOOK")) {
                diskScheduler = new CLOOK();
            }
            diskScheduler.setRequestQueue(existingQueue);
            diskScheduler.setHead(existingHead);
        });

        importButton.addActionListener(e -> {
            FileReader fr = new FileReader();
            try {
                if (fr.readInputFromFile()) {
                    diskScheduler.setHead(fr.getHeadStartsAt());
                    diskScheduler.setRequestQueue(fr.getQueue());

                    requestQueueField.setText(diskScheduler.getQueueAsString());
                    headField.setText(String.valueOf(diskScheduler.getHead()));
                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        randomizeButton.addActionListener(e -> {
            diskScheduler.randomizeQueue();
            diskScheduler.getRandomizeHead();
            requestQueueField.setText(diskScheduler.getQueueAsString());
            headField.setText(String.valueOf(diskScheduler.getHead()));
        });

        runButton.addActionListener(e -> {
            if (validHead && validQueue) {
                pauseButton.setVisible(true);
                runButton.setVisible(false);
                graph.setInitialPointer(diskScheduler.getHead());
                graph.setCylinders(diskScheduler.getCylinder());
                graph.setQueue(diskScheduler.simulate());
                graph.simulateGraph(slider.getValue(), this);
                if (!graph.timer.isRunning()) {

                }
            } else {
                JOptionPane.showMessageDialog(null, "Cannot run the program. Input is invalid.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        pauseButton.addActionListener(e -> {
            graph.stopSimulation(this);
            runButton.setVisible(true);
            pauseButton.setVisible(false);
        });

        saveButton.addActionListener(e -> {
            if (validHead && validQueue) {
                //save the table panels here
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
                        if (value < 0 || value > diskScheduler.getCylinder() - 1) {
                            // If the value is out of range, highlight the text field
                            invalidate(true);
                        } else {
                            input.setBackground(UIManager.getColor("TextField.background"));
                            diskScheduler.setHead(value);
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
                                    if (value < 0 || value > diskScheduler.getCylinder() - 1) {
                                        invalidate(false);
                                    } else {
                                        input.setBackground(UIManager.getColor("TextField.background"));
                                        numList[i] = value;
                                    }
                                }
                                diskScheduler.setRequestQueue(numList);
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
        this.add(graphsPanel);
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
}
