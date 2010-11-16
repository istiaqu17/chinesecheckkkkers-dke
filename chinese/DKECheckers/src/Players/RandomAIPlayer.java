/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Players;

import FrameWork.Board;
import FrameWork.Move;
import FrameWork.Position;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Stefan
 */
public class RandomAIPlayer implements Player {

    private String name;
    private Color color;
    private Position[] goalPositions, basePositions;

    public RandomAIPlayer() {
    }

    public RandomAIPlayer(String aName, Color aColor, Position[] goal) {
        color = aColor;
        name = aName;
        goalPositions = goal;
    }

    public boolean isHuman() {
        return false;
    }

    public Move makeMove(Board board) {
        Position[] currentPositions = board.findPieces(color);
        boolean moved = false;
        Random r = new Random();
        int random = -1;
        Position p = null;
        Move move = null;
        while (!moved) {
            // take a random piece of your own color
            random = r.nextInt(10);
            p = currentPositions[random];
            ArrayList<Move> moves = board.determineValidMoves(p);
            int numberValidMoves = moves.size();
            // if the piece has valid moves
            if (numberValidMoves > 0) {
                // take a random valid move
                int random2 = r.nextInt(numberValidMoves);
                // move the piece
                move = moves.get(random2);
                moved = true;
            }
        }
        return move;
    }

    public void setColor(Color aColor) {
        color = aColor;
    }

    public Color getColor() {
        return color;
    }

    public void setName(String aName) {
        name = aName;
    }

    public String getName() {
        return name;
    }

    public void setGoal(Position[] goal) {
        goalPositions = goal;
    }

    public Position[] getGoal() {
        return goalPositions;
    }

    public Player copy() {
        Position[] newGoalPositions = new Position[this.getGoal().length];
        for (int i = 0; i < this.getGoal().length; i++){
            newGoalPositions[i] = this.getGoal()[i].copy();
        }
        return new RandomAIPlayer(this.getName(), this.getColor(), newGoalPositions);
    }

    public void setBase(Position[] base) {
        basePositions = base;
    }

    public Position[] getBase() {
        return basePositions;
    }
}
