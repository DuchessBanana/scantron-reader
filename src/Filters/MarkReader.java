package Filters;

import FileIO.PDFHelper;
import Interfaces.PixelFilter;
import core.DImage;
import processing.core.PImage;

import java.util.ArrayList;

public class MarkReader implements PixelFilter {

    private int startR, startC, colDistBetween, rowDistBetween, height, width;
    private int questions;
    private int choices;
    short[][] grid;
    public MarkReader(int numQuestions, int numChoices) {
        System.out.println("Filter running...");
        startR = 109;
        startC = 105;
        height = 19;
        width = 19;
        rowDistBetween = 30;
        colDistBetween = 6;
        this.questions = numQuestions;
        this.choices = numChoices;
    }

    @Override
    public DImage processImage(DImage img) {
        grid = img.getBWPixelGrid();
        grid = crop(grid, 0, 0, 700, 700);
        System.out.println("Image is " + grid.length + " by "+ grid[0].length);

        img.setPixels(grid);

        return img;
    }

    public ArrayList<String> getStudentAnswers() {
        ArrayList<String> answers = new ArrayList<>();
        String output = "";
        for (int i = 1; i < 13; i++) {
            int currQuestion = darknessPerQuestion(grid, i-1);
//            System.out.println(getLetterAnswer(currQuestion));
            output += "Question " + i + " answer: " + getLetterAnswer(currQuestion) + "\n";
            answers.add(getLetterAnswer(currQuestion));
        }
        return answers;
    }

    public DImage processPage(ArrayList<PImage> pages, int pageNum) {
        PImage in = pages.get(pageNum-1);
        DImage img = new DImage(in);
        System.out.println("Running filter on page " + pageNum + " ....");
        return img;
    }

    private int darknessPerQuestion(short[][] grid, int question) {
        double lowestSoFar = getAverageDarkness(startR, startC, width, height, grid);
        int choice = 0;
        double choice1 = getAverageDarkness(startR, startC, width, height, grid);
        int colSpacing = 0;
        for (int j = 0; j < choices; j++) {
            double circleDarkness = getAverageDarkness(startR +((height + rowDistBetween)*question), startC + ((colDistBetween * colSpacing) + (width * colSpacing)), width, height, grid);
            colSpacing++;
            if(circleDarkness < lowestSoFar){
                lowestSoFar = circleDarkness;
            }
            if (circleDarkness < choice1 && circleDarkness <= lowestSoFar) {
                choice = j;
            }
        }
        return choice;
    }



    private double getAverageDarkness(int startR, int startC, int width, int height, short[][] grid) {
        double darkness = 0;
        for (int i = startR; i < startR+height; i++) {
            for (int j = startC; j < startC+ width; j++) {
                darkness += grid[i][j];
            }
        }
        return darkness/(height*width);
    }

    private String getLetterAnswer(int question){
        if(question == 0){
            return "A";
        }
        else if(question == 1){
            return "B";
        }
        else if(question == 2){
            return "C";
        }
        else if(question == 3){
            return "D";
        }
        else if(question == 4){
            return "E";
        }
        return "NO ANSWER";
    }

    private short[][] crop(short[][] grid, int r1, int c1, int r2, int c2) {
        short[][] newGrid = new short[r2-r1][c2-c1];
        for (int r = 0; r < newGrid.length; r++) {
            for (int c = 0; c < newGrid[0].length; c++) {
                newGrid[r][c] = grid[r][c];
            }
        }
        return newGrid;
    }
}

