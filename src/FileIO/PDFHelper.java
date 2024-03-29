package FileIO;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import processing.core.PConstants;
import processing.core.PImage;

public class PDFHelper {

    /***
     * Load a pdf and convert each page to a PImage.  Return a List of PImages.
     * @param pathToPdf path to the pdf including full filename and file extension
     * @return a List of PImage objects corresponding to the pages of the pdf
     */
    public static ArrayList<PImage> getPImagesFromPdf(String pathToPdf) {
        // InputStream is = PDFHelper.class.getResourceAsStream(path);
        ArrayList<PImage> images = new ArrayList<PImage>();
        PDDocument pdf = null;

        try {
            InputStream is = new FileInputStream(pathToPdf);
            pdf = PDDocument.load(is);
        } catch (IOException e) {
            System.out.println("Couldn't load pdf");
            System.out.println("DID YOU ADD THE ASSETS FOLDER TO YOUR CLASS PATH IN ECLIPSE?");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        List<PDPage> pages = pdf.getDocumentCatalog().getAllPages();

        for (PDPage page : pages) {
            try {
                BufferedImage image = page.convertToImage();

                PImage img = new PImage(image.getWidth(), image.getHeight(), PConstants.ARGB);
                image.getRGB(0, 0, img.width, img.height, img.pixels, 0, img.width);
                img.updatePixels();

                images.add(img);
                System.out.println("Adding page " + images.size());
            } catch (IOException e) {
                System.out.println("problem converting to image");
                e.printStackTrace();
            }
        }

        try {
            pdf.close();
        } catch (IOException e) {
            // TODO: Auto-generated catch block
            e.printStackTrace();
        }

        return images;
    }
    public static void writeDataToFile(String filePath, String data) throws IOException{
        try(FileWriter f = new FileWriter(filePath)){
            BufferedWriter b = new BufferedWriter(f);
            PrintWriter writer = new PrintWriter(b);

            writer.println(data);

        } catch (IOException error){
            System.err.println("There was an error writing to the file");
            error.printStackTrace();
        }
    }
    public static String readFile(String fileName) throws IOException{
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }


    public static PImage getPageImage(String pathtoPdf, int pageNum) {
        //InputStream is = PDFHelper.class.getResourceAsStream(pathtoPdf);

        ArrayList<PImage> images = new ArrayList<PImage>();
        PDDocument pdf = null;

        try {
            InputStream is = new FileInputStream(pathtoPdf);
            pdf = PDDocument.load(is);
        } catch (IOException e) {
            System.out.println("Couldn't load pdf");
            System.out.println("DID YOU ADD THE ASSETS FOLDER TO YOUR CLASS PATH IN ECLIPSE?");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        List<PDPage> pages = pdf.getDocumentCatalog().getAllPages();

        if (pageNum >= 0 && pageNum <= pages.size()) {
            PDPage page = pages.get(pageNum-1);

            try {
                BufferedImage image = page.convertToImage();

                PImage img = new PImage(image.getWidth(), image.getHeight(), PConstants.ARGB);
                image.getRGB(0, 0, img.width, img.height, img.pixels, 0, img.width);
                img.updatePixels();
                return img;
            } catch (IOException e) {
                System.out.println("problem converting to image");
                e.printStackTrace();
            }

        } else {
            System.out.println("You requested page " + pageNum + " but there are only " + pages.size() + " pages");
            return null;
        }

        try {
            pdf.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
