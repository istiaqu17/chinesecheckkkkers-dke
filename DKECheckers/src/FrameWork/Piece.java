package FrameWork;

import java.awt.Color;

/**
 *
 * @author Stefan
 */
public class Piece {

    private Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Piece copy() {
        if (this == null) {
            return null;
        } else {
            return new Piece(this.getColor());
        }
    }
}
