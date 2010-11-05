package FrameWork;

/**
 *
 * @author Stefan
 */
public class Position {

    private int x, y;
    private int i, j;
    private Piece piece;

    public Position(int initX, int initY, int i, int j, Piece p) {
        x = initX;
        y = initY;
        piece = p;
        this.i = i;
        this.j = j;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Piece getPiece() {
        return piece;
    }

    public void addPiece(Piece p) {
        if (!occupied()) {
            piece = p;
        } else {
            System.out.println("Already a piece on position: " + this.x + " " + this.y);
        }
    }

    public void removePiece() {
        piece = null;
    }

    public boolean occupied() {
        return piece != null;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    // Calculates the distance frpm one position to another
    public int distanceTo(Position position) {
        if (this.getI() == position.getI()) {
            return Math.abs(this.getJ() - position.getJ());
        } else if (this.getJ() == position.getJ()) {
            return Math.abs(this.getI() - position.getI());
        } else {
            int minI, minJ, maxI, maxJ;

            if (this.getI() < position.getI()) {
                minI = this.getI();
                minJ = this.getJ();
                maxI = position.getI();
                maxJ = position.getJ();
            } else {
                minI = position.getI();
                minJ = position.getJ();
                maxI = this.getI();
                maxJ = this.getJ();
            }
            if (maxJ - minJ < 0) {
                return Math.abs(maxI - minI) + Math.abs(maxJ - minJ);
            } else if (maxJ > minJ + (maxI - minI)) {
                return Math.abs(maxI - minI) + Math.abs(maxJ - minJ - Math.abs(maxI - minI));
            } else {
                return Math.abs(maxI - minI);
            }
        }
    }

    public Position copy() {
        if (this.getPiece() == null){
            System.out.println("bah");
        }
        return new Position(
                this.getX(),
                this.getY(),
                this.getI(),
                this.getJ(),
                this.getPiece().copy());
    }
}
