package ChineseCheckers;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stefan
 */
public class Position {
    private int x;
    private int y;
    private Piece piece;

    public Position(int initX, int initY, Piece p){
        x = initX;
        y = initY;
        piece = p;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Piece getPiece(){
        return piece;
    }

    public void addPiece(Piece p){
        if(!occupied())
            piece = p;
        else
            System.out.println("Already a piece on this position");
    }

    public void removePiece(){
        piece = null;
    }

    public boolean occupied(){
        return piece != null;
    }
}
