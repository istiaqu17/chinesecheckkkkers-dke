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
 * Filters a collection of moves and returns a smaller list
 */
public interface Filter {
    public ArrayList<Move> filter(ArrayList<Move> moves, Player player);

}
