package view;

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
    private int STRING_LEN_MIN = 10, STRING_LEN_MAX = 40, STRING_VAL_MIN = 0, STRING_VAL_MAX = 20, FRAME_SIZE_MIN = 3, FRAME_SIZE_MAX = 10;
    private ImageButton musicOnButton, musicOffButton, homeButton;
    private JComboBox algorithmChoice;
    private ImageButton frameNumPlus, frameNumMinus;
    private JTextField requestQueueField, headField;
    private ImageButton importButton, randomizeButton, runButton, pauseButton, saveButton;
    private JSlider slider;
    private JLabel timerLabel;

    public InputPanel() {
        super("bg/input-panel.png");
        initializeButtons();
        initializeAlgorithmComboBox();
        initializeTextFields();
        initializeLabels();
        initializeTables();
        initializeSlider();
        setListeners();
        addComponentsToFrame();
    }

    private void initializeButtons() {
        musicOnButton = createImageButton("buttons/volume-on.png", 956, 35, 47, 47);
        musicOffButton = createImageButton("buttons/volume-off.png", 956, 25, 47, 47);
        homeButton = createImageButton("buttons/home.png", 1017, 35, 47, 47);
        musicOffButton.setVisible(false);
        frameNumPlus = createImageButton("buttons/add.png", 469, 212, 44, 40);
        frameNumMinus = createImageButton("buttons/minus.png", 354, 212, 44, 40);
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
        algorithmChoice = new JComboBox(new String[]{"Simulate all", "FIFO", "LRU", "Optimal", "Second Chance(SC)", "Enhanced SC", "LFU", "MFU"});
        algorithmChoice.setRenderer(new CustomComboBoxRenderer());
        algorithmChoice.setBackground(new Color(77,58,104));
        algorithmChoice.setForeground(Color.white);
        algorithmChoice.setFont(new Font("Montserrat", Font.BOLD, 18));
        algorithmChoice.setBounds(218, 133, 150, 44);
    }

    private void initializeTextFields() {
        requestQueueField = createTextField("", 2, "requestQueueField", 218, 85, 664, 40);
        headField = createTextField("4", 2, "headField", 734, 136, 148, 41);
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

    }

    private void initializeTables() {

    }

    private void showAllTables() {



    }


    private void showOneTable(){

    }

    public void resetTables() {


    }


    private void enableOutputButtons() {
        runButton.setEnabled(true);
        saveButton.setEnabled(true);
    }
    private void disableOutputButtons() {
        runButton.setEnabled(false);
        saveButton.setEnabled(false);
    }

    private void setListeners() {
        //hover state
        musicOnButton.hover("buttons/volume-off-hover.png", "buttons/volume-on.png");
        musicOffButton.hover("buttons/volume-on-hover.png", "buttons/volume-off.png");
        homeButton.hover("buttons/home-hover.png", "buttons/home.png");
        frameNumMinus.hover("buttons/minus-hover.png", "buttons/minus.png");
        frameNumPlus.hover("buttons/add-hover.png", "buttons/add.png");
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

        frameNumPlus.addActionListener(e -> {
            try {
                headField.setText(String.valueOf(Integer.parseInt(headField.getText()) + 1));
            } catch (NumberFormatException ex) {
                headField.setText("4");
            }
        });

        frameNumMinus.addActionListener(e -> {
            try {
                headField.setText(String.valueOf(Integer.parseInt(headField.getText()) - 1));
            } catch (NumberFormatException ex) {
                headField.setText("4");
            }
        });

        algorithmChoice.addActionListener(e -> {

        });

        importButton.addActionListener(e -> {
            FileReader fr = new FileReader();
            try {
                if (fr.readInputFromFile()) {

                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        randomizeButton.addActionListener(e -> {

        });

        runButton.addActionListener(e -> {

            pauseButton.setVisible(true);
            runButton.setVisible(false);
        });

        pauseButton.addActionListener(e -> {

        });

        saveButton.addActionListener(e -> {
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

            }
        });
    }

    private void initializeSlider() {
        // slider and timer
        slider = new JSlider();
        slider.setMajorTickSpacing(25);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        Hashtable position = new Hashtable();
        position.put(0, new JLabel("0s"));
        position.put(25, new JLabel("1s"));
        position.put(50, new JLabel("2s"));
        position.put(75, new JLabel("3s"));
        position.put(100, new JLabel("4s"));
        slider.setLabelTable(position);
        slider.setBackground(new Color(231, 205, 194));
        slider.setBounds(426, 200, 246, 45);
    }

    private void addComponentsToFrame() {
        this.add(slider);
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
    }

    private static class CustomComboBoxRenderer extends BasicComboBoxRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setHorizontalAlignment(SwingConstants.CENTER);
            if (isSelected) {
                setBackground(new Color(232, 160, 221)); // set selected item background color
                setForeground(new Color(77,58,104)); // set item text color
            } else {
                setBackground(new Color(77,58,104)); // set unselected item background color
                setForeground(Color.WHITE); // set item text color
            }
            setFont(new Font("Montserrat", Font.BOLD, 14));
            return this;
        }
    }

    private void saveResults(JPanel panel) {
        String[] fileFormats = {"PDF", "JPEG"};
        JComboBox<String> formatComboBox = new JComboBox<>(fileFormats);

        int result = JOptionPane.showOptionDialog(null, formatComboBox, "Save As", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (result == JOptionPane.OK_OPTION) {
            String selectedFormat = (String) formatComboBox.getSelectedItem();

            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = null;
            String defaultExtension = null;

            if (selectedFormat.equals("PDF")) {
                filter = new FileNameExtensionFilter("PDF Files", "pdf");
                defaultExtension = "pdf";
            } else if (selectedFormat.equals("JPEG")) {
                filter = new FileNameExtensionFilter("JPEG Files", "jpg", "jpeg");
                defaultExtension = "jpg";
            }

            fileChooser.setFileFilter(filter);
            fileChooser.setApproveButtonText("Save");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            // Generate the file name using the desired format
            String formattedDate = new SimpleDateFormat("MMddyy_HHmmss").format(new Date());
            String fileName = String.format("%s_PG.%s", formattedDate, defaultExtension);
            fileChooser.setSelectedFile(new File(fileName));

            int option = fileChooser.showSaveDialog(null);

            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String extension = getFileExtension(file);

                switch (selectedFormat) {
                    case "PDF":
                        if (!extension.equalsIgnoreCase("pdf")) {
                            file = new File(file.getAbsolutePath() + ".pdf");
                        }

//                        String[] labelStrings = Arrays.stream(titleLabels)
//                                .map(JLabel::getText)
//                                .toArray(String[]::new);
//                        new Export().saveAsPDF(tables, labelStrings, file);
                        break;
                    case "JPEG":
                        if (!extension.equalsIgnoreCase("jpeg") && !extension.equalsIgnoreCase("jpg")) {
                            file = new File(file.getAbsolutePath() + ".jpg");
                        }
                        new Export().saveAsJPEG(panel, file);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private String getFileExtension(File file) {
        String extension = "";
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex + 1).toLowerCase();
        }
        return extension;
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
}
