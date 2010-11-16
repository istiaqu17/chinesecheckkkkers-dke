/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Sorting;

import FrameWork.Move;
import java.util.ArrayList;

/**
 *
 * @author Stefan
 */
public class MoveSorter {
    public static ArrayList<Move> sortMovesOnHops(ArrayList<Move> moves){
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
            sorted.addAll(sortMovesOnHops(bigger));
            sorted.add(pivot);
            sorted.addAll(sortMovesOnHops(smaller));
            return sorted;
        }
    }

}
