import FileIO.PDFHelper;
import Filters.MarkReader;
import core.DImage;
import processing.core.PImage;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

// Author: David Dobervich (this is my edit)
// ANOTHER EDIT.
public class OpticalMarkReaderMain {
    public static void main(String[] args) {
        String pathToPdf = fileChooser();
        System.out.println("Loading pdf at " + pathToPdf);
        System.out.println("Loading pdf....");
        ArrayList<PImage> pages = PDFHelper.getPImagesFromPdf("assets/OfficialOMRSampleDoc.pdf");
        MarkReader filter = new MarkReader(12, 5);
        DImage page1 = filter.processPage(pages,1);
        filter.processImage(page1);
        ArrayList<String> answers = filter.getStudentAnswers();
        try {
            writeDataToFile("scores.csv", answers.toString() + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        for (int i = 1; i < pages.size(); i++){
//            ArrayList<String> studentAnswers = filter.getStudentAnswers();
//            System.out.println(compareAnswers(answers, studentAnswers));
//        }
//        try {
//            writeDataToFile("scores.csv", filter.getStudentAnswers(1).toString() + "\n");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//            try {
//                if(i == 0){
//                    writeDataToFile("scores.csv", "Answers: ");
//                    writeDataToFile("scores.csv", filter.getStudentAnswers().toString() + "\n");
//                }
//                else {
//                    writeDataToFile("scores.csv", "Student " + (i) + "'s Answers" + "");
//                    writeDataToFile("scores.csv", filter.getStudentAnswers().toString() + "\n");
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }

    }
    public static String compareAnswers(ArrayList<String> answers, ArrayList<String> studentAnswers){
        String status = " ";
        for (int i = 0; i < 12; i++) {
            if (answers.get(i).equals(studentAnswers.get((i)))){
                status += ", right";
            }
            else {
                status += ", wrong";
            }
        }
        return status;
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
