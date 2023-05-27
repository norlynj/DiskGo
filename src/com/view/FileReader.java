package view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;
import java.util.StringJoiner;

public class FileReader {

    private int[] queue;
    private int headStartsAt;

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
                Scanner scanner = new Scanner(inputFile);

                // Read page reference string
                String requestQueueLine = scanner.nextLine();
                String[] requestQueue = requestQueueLine.split(": ")[1].split(", ");
                if (requestQueue.length <= 0 || requestQueue.length > 200) {
                    invalid = true;
                }
                queue = new int[requestQueue.length];
                for (int i = 0; i < requestQueue.length; i++) {
                    int value = Integer.parseInt(requestQueue[i].trim());
                    if (value < 0 || value > 199) {
                        invalid = true;
                    }
                    queue[i] = value;
                }

                // Read head starting position
                String headStartsAtLine = scanner.nextLine();
                headStartsAt = Integer.parseInt(headStartsAtLine.split(": ")[1]);
                if (headStartsAt < 0 || headStartsAt > 199) {
                    invalid = true;
                }

                scanner.close();

            } catch (FileNotFoundException | ArrayIndexOutOfBoundsException | NumberFormatException ex) {
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

    public int[] getQueue() {
        return queue;
    }

    public String getQueueAsString() {
        StringJoiner joiner = new StringJoiner(", ");
        for (int number : queue) {
            joiner.add(String.valueOf(number));
        }
        return joiner.toString();
    }

    public int getHeadStartsAt() {
        return headStartsAt;
    }
}
