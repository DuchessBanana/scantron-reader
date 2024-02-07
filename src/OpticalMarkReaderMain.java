import FileIO.PDFHelper;

import FileIO.PDFHelper;
import Filters.MarkReader;
import core.DImage;
import processing.core.PImage;

import FileIO.PDFHelper;
import core.DImage;
import processing.core.PImage;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.ArrayList;

// Author: David Dobervich (this is my edit)
// ANOTHER EDIT.
public class OpticalMarkReaderMain {
    public static void main(String[] args) {
        String pathToPdf = fileChooser();
        System.out.println("Loading pdf at " + pathToPdf);

        /*
        Your code here to...
        (1).  Load the pdf
        (2).  Loop over its pages
        (3).  Create a DImage from each page and process its pixels
        (4).  Output 2 csv files
         */

        ArrayList<PImage> pages = PDFHelper.getPImagesFromPdf("assets/OfficialOMRSampleDoc.pdf");
        for (int i = 0; i < pages.size(); i++) {
            DImage img = new DImage(pages.get(i));
            MarkReader filter = new MarkReader();
            filter.processImage(img);
            findBubbled(img);
        }



        //csv file stuff

        /*METHODS-PSEUDOCODE*/

        //findBubbled() -
        //getAnswerFromKey() ? -
        //getScore() - int
        //getRightQuestions() - ArrayList
        //getWrongQuestions() - ArrayList
        //
    }

    private static void findBubbled(DImage img) {
        //TODO: plug in darkness methods
    }

    private static String fileChooser() {
        String userDirLocation = System.getProperty("user.dir");
        File userDir = new File(userDirLocation);
        JFileChooser fc = new JFileChooser(userDir);
        int returnVal = fc.showOpenDialog(null);
        File file = fc.getSelectedFile();
        return file.getAbsolutePath();
    }
}
