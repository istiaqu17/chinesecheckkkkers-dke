/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Evaluator;

import FrameWork.Node;
import Players.Player;

/**
 *
 * @author Rik Claessens
 */
public interface Evaluator {
    public int evaluate(Node node, Player player);
}
