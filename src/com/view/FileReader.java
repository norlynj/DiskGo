package view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

    private ArrayList<Integer> pageRefString;
    int frameNumber;

    public boolean readInputFromFile() throws FileNotFoundException {
        String resourcePath = "/resources/text/";
        URL resourceUrl = InputPanel.class.getResource(resourcePath);
        boolean invalid = false;

        // Convert the URL to a file object
        assert resourceUrl != null;
        File resourceFile = new File(resourceUrl.getPath());
        JFileChooser fileChooser = new JFileChooser(resourceFile);
        fileChooser.setDialogTitle("Select text file");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File inputFile = fileChooser.getSelectedFile();

            try {
                ArrayList<Integer> inputList = new ArrayList<>();
                Scanner scanner = new Scanner(inputFile);

                // Read page reference string
                String pageReferenceStringLine = scanner.nextLine();
                String[] pageReferenceStringArray = pageReferenceStringLine.split(": ")[1].split(", ");
                if (pageReferenceStringArray.length < 10 || pageReferenceStringArray.length > 40) {
                    invalid = true;
                }
                for (String pageReferenceString : pageReferenceStringArray) {
                    int value = Integer.parseInt(pageReferenceString);
                    if (value < 0 || value > 20) {
                        invalid = true;
                    }
                    inputList.add(value);
                }


                // Read number of frames
                String numberOfFramesLine = scanner.nextLine();
                int numberOfFrames = Integer.parseInt(numberOfFramesLine.split(": ")[1]);
                if (numberOfFrames < 3 || numberOfFrames > 10) {
                    invalid = true;
                }

                scanner.close();

                this.pageRefString = inputList;
                this.frameNumber = numberOfFrames;

            } catch (FileNotFoundException | ArrayIndexOutOfBoundsException ex) {
                System.out.println("Error reading the file");
                return false;
            }
        } else {
            return false;
        }
        if (invalid) {
            JOptionPane.showMessageDialog(null, "Please recheck inputs to follow the defined bounds");
            return false;
        }
        return true;
    }

    public ArrayList getPageRefString() {
        return pageRefString;
    }

    public int getFrameNumber() {
        return frameNumber;
    }
}