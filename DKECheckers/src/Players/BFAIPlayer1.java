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
public class BFAIPlayer1 implements Player {
private String name;
    private Color color;
    private Position[] goalPositions;

    public BFAIPlayer1(){
    }

    public BFAIPlayer1(String aName, Color aColor, Position[] goal){
        color = aColor;
        name = aName;
        goalPositions = goal;

    }

    public boolean isHuman(){
        return false;
    }

    public void makeMove(Board board){

        Position[] currentPositions = board.findPieces(color);
        int maxMoved = 0;
        Move toBeMade = null;
        boolean moved = false;

        
        for (int i = 0; i < currentPositions.length ; i++) {
            ArrayList<Move> validMoves = board.determineValidMoves(currentPositions[i]);
            
            for (int j = 0; j < validMoves.size(); j++) {
                   Move moveSimulated = validMoves.get(j);
                   int number_of_hops = countHops(moveSimulated);

                   if (number_of_hops >= maxMoved) {
                       maxMoved = number_of_hops;
                       toBeMade = moveSimulated;
                   }
            }
        }

        board.movePiece(toBeMade);
        moved = true;
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

}
