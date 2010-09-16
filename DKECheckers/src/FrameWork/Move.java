/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FrameWork;

/**
 *
 * @author Suzanne
 */
public class Move {
    public void makeMove(Position[] positions) {

        Piece piece = null;
        for(int i = 0; i < positions.length-1; i++) {
            piece = positions[i].getPiece();
            positions[i+1].addPiece(piece);
        }
    }
}
