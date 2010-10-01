/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FrameWork;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author Stefan
 */
public class RandomAIPlayer implements Player{
    private String name;
    private Color color;
    private Position[] goalPositions;

    public RandomAIPlayer(){
    }

    public RandomAIPlayer(String aName, Color aColor, Position[] goal){
        color = aColor;
        name = aName;
        goalPositions = goal;

    }


    public boolean isHuman(){
        return false;
    }

    public void makeMove(Board board){
        Position[] currentPositions = getCurrentPositions(board);
        boolean moved = false;
        Random r = new Random();
        int random = -1;
        Position p = null;

        while(!moved){
            // take a random piece of your own color
            random = r.nextInt(10);
            p = currentPositions[random];
            board.determineValidMoves(p);
            int numberValidMoves = board.getValidMoves().size();
            // if the piece has valid moves
            if(numberValidMoves > 0){
                // take a random valid move
                int random2 = r.nextInt(numberValidMoves);
                // move the piece
                Position[] beginAndEnd = new Position[2];
                // begin position
                beginAndEnd[0] = p;
                // end position
                beginAndEnd[1] = board.getValidMoves().get(random2);
                board.movePiece(beginAndEnd);
                moved = true;
                //update the currentpositions list
                currentPositions[random] = beginAndEnd[1];
            }
        }
    }

    //this makes an array with all positions currently occupied by the pieces of this player
    private Position[] getCurrentPositions(Board board){
        int i = 0;
        Position[] currentPositions = new Position[10];
        Position[][] array = board.getBoardArray();
        for(Position[] pos: array){
            for(Position p: pos){
                if(p != null && p.getPiece() != null && p.getPiece().getColor() == color){
                    currentPositions[i] = p;
                    i++;
                }
            }
        }
        return currentPositions;
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
