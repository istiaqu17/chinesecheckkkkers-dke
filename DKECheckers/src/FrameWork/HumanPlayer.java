package FrameWork;

import java.awt.Color;

/**
 *  
 * @author Sara van de Moosdijk
 *
 */
public class HumanPlayer implements Player {

    private String name;
    private Color color;
    private Position[] goalPositions;

    public HumanPlayer() {
        name = " ";
        color = null;
    }

    public HumanPlayer(String aName, Color aColor, Position[] goal) {
        name = aName;
        color = aColor;
        goalPositions = goal;
    }

    public void makeMove(Board b) {
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public Position[] getGoal() {
        return goalPositions;
    }

    public void setColor(Color aColor) {
        color = aColor;
    }

    public void setName(String aName) {
        name = aName;
    }

    public void setGoal(Position[] goal) {
        goalPositions = goal;
    }

    public boolean isHuman() {
        return true;
    }
}
