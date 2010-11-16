/*
To change this template, choose Tools | Templates
and open the template in the editor.
*/

package FrameWork;

/**
 *
 * @author Suzanne, Rik
 */
public class Move {
    // The class move represents a move, so it does not handle the moment a player makes a move sorry :S
    private Position[] positions;
    private boolean hopMove;

    public Move(Position[] positions){
        this.positions = positions;
    }

    public Position[] getPositions(){
        return this.positions;
    }

    public boolean isHopMove(){
        return this.hopMove;
    }
}
