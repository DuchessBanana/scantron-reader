import FileIO.PDFHelper;
import Filters.MarkReader;
import core.DImage;
import core.DisplayWindow;
import processing.core.PImage;

import java.util.ArrayList;

public class FilterTest {
    public static String currentFolder = System.getProperty("user.dir") + "/";

    public static void main(String[] args) {
        SaveAndDisplayExample(1);

        RunTheFilter();
        //ArrayList<PImage> pages = PDFHelper.getPImagesFromPdf("assets/OfficialOMRSampleDoc.pdf");
        //System.out.println(pages.size());
    }

    private static void RunTheFilter() {
        System.out.println("Loading pdf....");
        PImage in = PDFHelper.getPageImage("assets/OfficialOMRSampleDoc.pdf",1);
        DImage img = new DImage(in);       // you can make a DImage from a PImage

        System.out.println("Running filter on page 1....");
        MarkReader filter = new MarkReader(12, 5);
        filter.processImage(img);  // if you want, you can make a different method
                                   // that does the image processing an returns a DTO with
                                   // the information you want
    }

    private static void SaveAndDisplayExample(int page) {
        PImage img = PDFHelper.getPageImage("assets/OfficialOMRSampleDoc.pdf",page);
        img.save(currentFolder +  "assets/page" + page + ".png");

        DisplayWindow.showFor("assets/page" + page + ".png");
    }
}