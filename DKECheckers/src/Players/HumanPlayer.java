package Players;

import FrameWork.Board;
import FrameWork.Board;
import FrameWork.Board;
import FrameWork.Move;
import FrameWork.Move;
import FrameWork.Move;
import FrameWork.Position;
import FrameWork.Position;
import FrameWork.Position;
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

    public Move makeMove(Board b) {
        return null;
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
