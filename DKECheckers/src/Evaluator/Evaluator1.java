/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Evaluator;

import FrameWork.Node;
import FrameWork.Position;
import Players.Player;

/**
 *
 * @author Rik Claessens
 */
public class Evaluator1 implements Evaluator{
    private static int index = 0;

    public int evaluate(Node node, Player player) {
//        int value = 0;
//        //1. Check if the move is a winning move.
//        if (node.getGameState().getWinner() != null) {
//            //Add a lot of points if the winner is the player.
//            if (node.getGameState().getWinner() == this) {
//                value += 1000;
//            } //Subtract a lot of points if the winner is the opponent.
//            else {
//                value -= 1000;
//            }
//        }
//
//        Position[] positions = node.getGameState().findPieces(player.getColor());
//
//        //2. Check the total distance from the goal.
//        int totalDistanceGoal = 0;
//        Position checkpoint = player.getGoal()[0];
//        for (Position p : positions) {
//            totalDistanceGoal += p.distanceTo(checkpoint);
//        }
//        value += totalDistanceGoal / 2;
//
//        //3. Check the total distance between the player's pieces (grouping factor).
//        int totalDistancePieces = 0;
//        for (Position p : positions) {
//            for (Position p2 : positions) {
//                totalDistancePieces += p.distanceTo(p2);
//            }
//        }
//        value += totalDistancePieces / 2;
//
//        //4. Check how close the player's pieces are to the centerline.
//
//        //5. Check how close the player's pieces are to the opponent's pieces.

        return index++;
    }

}
