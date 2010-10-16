/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Players;

import FrameWork.Board;
import FrameWork.Move;
import FrameWork.Position;
import java.awt.Color;

/**
 *
 * @author Rik Claessens
 */
public class Minimax implements Player{
    private String name;
    private Color color;
    private Position[] goalPositions;

    public Minimax(String name, Color color, Position[] goal){
        this.name = name;
        this.color = color;
        this.goalPositions = goal;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setGoal(Position[] goal) {
        this.goalPositions = goal;
    }

    public Position[] getGoal() {
        return this.goalPositions;
    }

    public boolean isHuman() {
        return false;
    }

    public Move makeMove(Board b) {
        throw new UnsupportedOperationException("Not supported yet.");
//        int treeDepth = 1;
//        Tree gameTree = new Tree(b, treeDepth, color);

    }

    public Player copy() {
        Position[] newGoalPositions = new Position[this.getGoal().length];
        for (int i = 0; i < this.getGoal().length; i++){
            newGoalPositions[i] = this.getGoal()[i].copy();
        }
        return new HumanPlayer(this.getName(), this.getColor(), newGoalPositions);
    }

}
