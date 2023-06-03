package view;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Export {
    public void saveResults(JPanel panel, JPanel[] panels, JLabel[] titles) {
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
                        String[] labelStrings = Arrays.stream(titles)
                                .map(JLabel::getText)
                                .toArray(String[]::new);
                        saveAsPDF(panels, labelStrings, file);
                        break;
                    case "JPEG":
                        if (!extension.equalsIgnoreCase("jpeg") && !extension.equalsIgnoreCase("jpg")) {
                            file = new File(file.getAbsolutePath() + ".jpg");
                        }
                        saveAsJPEG(panel, file);
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
    public void saveAsPDF(JPanel[] graphs, String[] paneTitles, File file) {
        try {
            PDDocument document = new PDDocument();
            float marginLeft = 30; // Left margin
            float marginRight = 30; // Right margin

            for (int i = 0; i < graphs.length; i++) {
                JPanel pane  = graphs[i];
                String paneTitle = paneTitles[i];


                // Create a new page for each pane
                PDPage page = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth())); // Set the page orientation to landscape
                document.addPage(page);

                // Load the pane as an image
                BufferedImage paneImage = new BufferedImage(pane.getWidth(), pane.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = paneImage.createGraphics();
                pane.print(g2d);
                g2d.dispose();

                // Convert the image to PDImageXObject
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(paneImage, "png", baos);
                PDImageXObject imageXObject = PDImageXObject.createFromByteArray(document, baos.toByteArray(), "png");

                // Start writing the content to the page
                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Calculate the scaling factor based on the pane width and page width
                float scaleFactor = (page.getMediaBox().getWidth() - marginLeft - marginRight) / (float) paneImage.getWidth();

                // Calculate the adjusted image width and height
                float adjustedImageWidth = paneImage.getWidth() * scaleFactor;
                float adjustedImageHeight = paneImage.getHeight() * scaleFactor;

                // Calculate the position to center the image horizontally
                float startX = marginLeft + (page.getMediaBox().getWidth() - marginLeft - marginRight - adjustedImageWidth) / 2;

                // Calculate the position to place the image vertically
                float startY = page.getMediaBox().getHeight() - adjustedImageHeight - 80;

                // Draw the pane image with adjusted size
                contentStream.drawImage(imageXObject, startX, startY, adjustedImageWidth, adjustedImageHeight);

                // Write the pane title
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 20);
                contentStream.newLineAtOffset(marginLeft, startY + adjustedImageHeight + 15);
                contentStream.showText(paneTitle);
                contentStream.endText();

                // Close the content stream
                contentStream.close();
            }

            // Save the PDF file
            document.save(file);
            JOptionPane.showMessageDialog(null, "Graphs saved as PDF successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error occurred while saving the graphs as PDF.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void saveAsJPEG(JPanel panel, File file) {
        try {
            BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = image.createGraphics();
            panel.print(graphics2D);
            graphics2D.dispose();
            ImageIO.write(image, "JPEG", file);
            JOptionPane.showMessageDialog(null, "Image saved successfully.");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while saving image.", "Error", JOptionPane.ERROR_MESSAGE);
        }    }
}
