/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Players;


import FrameWork.*;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Suzanne
 */
public class BruteForceAI implements Player {
private String name;
    private Color color;
    private Position[] goalPositions, basePositions;

    public BruteForceAI(String aName, Color aColor, Position[] goal){
        color = aColor;
        name = aName;
        goalPositions = goal;
    }

    public BruteForceAI() {
    }

    public boolean isHuman(){
        return false;
    }

    public Move makeMove(Board board){

        Position[] currentPositions = board.findPieces(color);
        int maxMoved = -1;
        Move toBeMade = null;
        boolean moved = false;

        for (int i = 0; i < currentPositions.length ; i++) {
            ArrayList<Move> validMoves = board.determineValidMoves(currentPositions[i]);

            for (int j = 0; j < validMoves.size(); j++) {
                Move moveSimulated = validMoves.get(j);
                int number_of_hops = countHops(moveSimulated);
                Boolean forward = checkForward(moveSimulated);
                   
                if (number_of_hops > maxMoved) {
                    if (forward) {
                        maxMoved = number_of_hops;
                        toBeMade = moveSimulated;
                    }
                }
                else if(number_of_hops == maxMoved) {
                    toBeMade = checkBest(toBeMade, moveSimulated);
                }
            }
        }
        System.out.println("maxMoved: " + maxMoved);

        moved = true;
        return toBeMade;
    }


    public Move checkBest(Move moveCurrent, Move moveChallenging) {
        Position[] currentMovePositions = moveCurrent.getPositions();
        Position[] challengingMovePositions = moveChallenging.getPositions();
        Position[] goal = getGoal();
        int distanceCurrent1 = currentMovePositions[0].distanceTo(goal[0]);
        int distanceCurrent2 = currentMovePositions[currentMovePositions.length - 1].distanceTo(goal[0]);

        int distanceChallenging1 = challengingMovePositions[0].distanceTo(goal[0]);
        int distanceChallenging2 = challengingMovePositions[challengingMovePositions.length-1].distanceTo(goal[0]);

        if (distanceChallenging1-distanceChallenging2 >= distanceCurrent1 - distanceCurrent2) {
            return moveChallenging;
        }
        return moveCurrent;
    }

    public int countHops(Move move) {
        int length = move.getPositions().length;
        if (length == 2) {
            if (move.isHopMove()){
                return 1;
            }
            return 0;
        }
        return length;
    }

    public Boolean checkForward(Move move) {
        Position[] simulatedPositions = move.getPositions();
        Position[] goal = getGoal();
        int distance1 = simulatedPositions[0].distanceTo(goal[0]);
        int distance2 = simulatedPositions[simulatedPositions.length - 1].distanceTo(goal[0]);

        if(distance1 >= distance2) {
            return true;
        }
        return false;
    }

    public void setColor(Color aColor){
        color = aColor;
    }
    public Color getColor(){
        return color;
    }

    public void setName(String aName){
        name = aName;
    }
    public String getName(){
        return name;
    }

    public void setGoal(Position[] goal){
        goalPositions = goal;
    }
    public Position[] getGoal(){
        return goalPositions;
    }

    public Player copy() {
        Position[] newGoalPositions = new Position[this.getGoal().length];
        for (int i = 0; i < this.getGoal().length; i++){
            newGoalPositions[i] = this.getGoal()[i].copy();
        }
        return new BruteForceAI(this.getName(), this.getColor(), newGoalPositions);
    }

    public void setBase(Position[] base) {
        basePositions = base;
    }

    public Position[] getBase() {
        return basePositions;
    }
}
