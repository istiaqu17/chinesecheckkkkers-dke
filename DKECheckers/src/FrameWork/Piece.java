package FrameWork;

import java.awt.Color;

/**
 *
 * @author Stefan
 */
public class Piece {
    private int x;
    private int y;
    private Color color;

    public Piece(int initX, int initY, Color color){
        this.x = initX;
        this.y = initY;
        this.color = color;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Color getColor(){
        return color;
    }

}