package Players;

import FrameWork.Board;
import FrameWork.Move;
import FrameWork.Position;
import java.awt.Color;

/**
 *  
 * @author Sara van de Moosdijk
 *
 */

public interface Player 
{
	//For the player to move a piece.
	//The human player will make moves through mouse clicks.
	//The AI bot will calculate possible moves.
	//Should this return anything?
	Move makeMove(Board b);
	
	//A player will have an assigned color.
	void setColor(Color aColor);
	Color getColor();
	
	//It would be nice to let (human) players insert their name.
	//AI bots will of course get standard names.
	void setName(String aName);
	String getName();

        void setBase(Position[] base);
        Position[] getBase();

        void setGoal(Position[] goal);
        Position[] getGoal();

        boolean isHuman();

    public Player copy();

}

