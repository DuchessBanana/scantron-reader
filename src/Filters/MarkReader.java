package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class MarkReader implements PixelFilter {

    private int startR = 109;
    private int startC = 105;
    private int colDistBetween = 6;
    private int height = 19;
    private int width = 19;
    private int rowDistBetween = 30;
    private int rowSpaces = 11;
    private int colSpaces = 4;
    public MarkReader() {
        System.out.println("Filter running...");
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();

        grid = crop(grid, 0, 0, 700, 700);

        System.out.println("Image is " + grid.length + " by "+ grid[0].length);
        int question1 = darknessPerQuestion(grid, 1);
        int question2 = darknessPerQuestion(grid, 2);
        System.out.println(getLetterAnswer(question1));
        System.out.println(getLetterAnswer(question2));

        /*

         box1 = getAverageDarkness( r, c, w, h, grid );
         box2 = getAveragfeDarknes( r, c + a bit, w, h, grid);
         etc...

         find which box is darest
         print the answer.
         */


        img.setPixels(grid);

        return img;
    }

    private int darknessPerQuestion(short[][] grid, int question) {
        double answer = Double.MAX_VALUE;
        int choice = 0;
        double choice1 = getAverageDarkness(startR, startC, width, height, grid);
        int colSpacing = 0;
            for (int j = 0; j < 5; j++) {
                double circleDarkness = getAverageDarkness(startR +((height + rowDistBetween)*(question-1)), startC + ((colDistBetween * colSpacing) + (width * colSpacing)), width, height, grid);
                colSpacing++;
                if (circleDarkness < choice1) {
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

