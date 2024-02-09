import FileIO.PDFHelper;
import Filters.MarkReader;
import core.DImage;
import processing.core.PImage;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

// Author: David Dobervich (this is my edit)
// ANOTHER EDIT.
public class OpticalMarkReaderMain {
    public static void main(String[] args) {
        String pathToPdf = fileChooser();
        System.out.println("Loading pdf at " + pathToPdf);
        System.out.println("Loading pdf....");
        ArrayList<PImage> pages = PDFHelper.getPImagesFromPdf("assets/OfficialOMRSampleDoc.pdf");
        for (int i = 0; i < pages.size(); i++) {
            PImage in = PDFHelper.getPageImage("assets/OfficialOMRSampleDoc.pdf",i+1);
            DImage img = new DImage(in);
            System.out.println("Running filter on page " + (i+1) + " ....");
            MarkReader filter = new MarkReader(12, 5);
            filter.processImage(img);
//            displayPage(i+1);
            try {
                writeDataToFile("scores.csv", "Student " + i + "'s Answers" + "" +
                        "\n");
                writeDataToFile("scores.csv", filter.getStudentAnswers());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private static String fileChooser() {
        String userDirLocation = System.getProperty("user.dir");
        File userDir = new File(userDirLocation);
        JFileChooser fc = new JFileChooser(userDir);
        int returnVal = fc.showOpenDialog(null);
        File file = fc.getSelectedFile();
        return file.getAbsolutePath();
    }
    public static void writeDataToFile(String filePath, String data) throws IOException {
        try (FileWriter f = new FileWriter(filePath, true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter writer = new PrintWriter(b);) {
            writer.println(data);
        } catch (IOException error) {
            System.err.println("There was a problem writing to the file: " + filePath);
            error.printStackTrace();
        }
    }
}
