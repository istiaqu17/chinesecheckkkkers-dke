package ChineseCheckers;

import java.awt.Color;

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
    private Color colour;

    public Piece(int initX, int initY, Color team){
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

    public Color getColour(){
        return colour;
    }

}
