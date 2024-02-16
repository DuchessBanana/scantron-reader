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
        int numQuestions = 12;
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
            try {
                if(i == 0){
                    String str = " ";
                    for (int j = 1; j <= numQuestions; j++){
                        str += ", q" + j;
                    }
                    writeDataToFile("scores.csv", "page, # right" + str);
                }
                else {
                    ArrayList<String> studentAnswers = filter.getStudentAnswers();
                    writeDataToFile("scores.csv", makeLine(i, getAnswers(), studentAnswers));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static String makeLine(int pageNum, ArrayList<String> answers, ArrayList<String> studentAnswers) {
        String line = " ";
        String str = " ";
        int counter = 0;
        for (int i = 0; i < 12; i++) {
            if (answers.get(i).equals(studentAnswers.get(i))){
                counter++;
                str += ", right";
            }
            else{
                str += ", wrong";
            }
        }
        line += pageNum + ", " + counter + str;
        return line;
    }

    public static ArrayList<String> getAnswers(){
        ArrayList<String> answers = new ArrayList<>();
        PImage in = PDFHelper.getPageImage("assets/OfficialOMRSampleDoc.pdf",1);
        DImage img = new DImage(in);
        MarkReader filter = new MarkReader(12, 5);
        filter.processImage(img);
        answers = filter.getStudentAnswers();
        return answers;
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
