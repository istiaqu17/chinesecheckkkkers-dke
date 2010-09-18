package FrameWork;

/**
 *
 * @author Stefan
 */
public class Position {
    private double x,y;
    private Piece piece;

    public Position(double initX, double initY, Piece p){
        x = initX;
        y = initY;
        piece = p;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public Piece getPiece(){
        return piece;
    }

    public void addPiece(Piece p){
        if(!occupied())
            piece = p;
        else
            System.out.println("Already a piece on position: " + this.x + " " + this.y);
    }

    public void removePiece(){
        piece = null;
    }

    public boolean occupied(){
        return piece != null;
    }
}
