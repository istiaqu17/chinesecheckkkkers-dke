/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stefan
 */
public class Piece {
    private int x;
    private int y;
    private int colour;
    public static final int BLUE = 0, YELLOW = 1, GREEN = 2, RED = 3, BLACK = 4, WHITE = 5, PURPLE = 6;

    public Piece(int initX, int initY, int team){
        x = initX;
        y = initY;
        colour = team;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getColour(){
        return colour;
    }

}
