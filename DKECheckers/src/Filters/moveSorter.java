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
public class moveSorter implements Filter{

    public ArrayList<Move> filter(ArrayList<Move> moves, Player player) {
        if(moves.size() <= 1)
            return moves;
        else{
            Move pivot = moves.get(moves.size()-1);
            moves.remove(moves.size()-1);
            ArrayList<Move> bigger = new ArrayList<Move>();
            ArrayList<Move> smaller = new ArrayList<Move>();
            for (Move move: moves){
                if(move.getPositions().length >= pivot.getPositions().length)
                    bigger.add(move);
                else
                    smaller.add(move);
            }
            ArrayList<Move> sorted = new ArrayList<Move>();
            sorted.addAll(filter(bigger, player));
            sorted.add(pivot);
            sorted.addAll(filter(smaller, player));
            return sorted;
        }
    }

}
