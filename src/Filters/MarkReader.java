package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class MarkReader implements PixelFilter {

    private int startR = 120;
    private int startC = 105;
    private int distBetween = 6;
    public MarkReader() {
        System.out.println("Filter running...");
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();

        grid = crop(grid, 0, 0, 500, 500);

        System.out.println("Image is " + grid.length + " by "+ grid[0].length);
        int question1 = darknessPerQuestion(grid);
        System.out.println(question1);

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

    private int darknessPerQuestion(short[][] grid) {
        double answer = Double.MAX_VALUE;
        int choice = 0;
        for (int i = 0; i < 5; i++) {
            double circleDarkness = getAverageDarkness(startR, startC, 19, 20, grid);
            if (circleDarkness < answer) {
                choice = i;
            }
        }
        return choice;
    }

    private double getAverageDarkness(int startR, int startC, int width, int height, short[][] grid) {
        double darkness = 0;
        int spacing = 0;
        for (int i = startR; i < startR+(distBetween*spacing)+height; i++) {
            for (int j = startC; j < startC + width; j++) {
                darkness += grid[i][j];
                spacing++;
            }
        }
        return darkness/(height*width);
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

