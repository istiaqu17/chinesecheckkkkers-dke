/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Filters;

import FrameWork.Move;
import Players.Player;
import java.util.ArrayList;

/**
 *
 * @author Rik Claessens
 */
public class backwardsFilter implements Filter {

    public ArrayList<Move> filter(ArrayList<Move> moves, Player player) {
        ArrayList<Move> filteredMoves = new ArrayList<Move>();
        for (Move move : moves){
            // don't add a move which moves a piece backwards
            if (move.getPositions()[0].distanceTo(player.getGoal()[0]) > move.getPositions()[move.getPositions().length - 1].distanceTo(player.getGoal()[0])){
                filteredMoves.add(move);
            }
        }
        return filteredMoves;
    }

}
