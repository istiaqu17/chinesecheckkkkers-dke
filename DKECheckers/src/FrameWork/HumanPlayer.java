package FrameWork;

import java.awt.Color;

/**
 *  
 * @author Sara van de Moosdijk
 *
 */

public class HumanPlayer implements Player
{
	private String name;
	private Color color;
	
	public HumanPlayer() 
	{
		name = " ";
		color = null;
	}
	
	public HumanPlayer(String aName, Color aColor) 
	{
		name = aName;
		color = aColor;
	}

	public void makeMove() 
	{
		
	}
	
	public Color getColor() {return color;}
	public String getName() {return name;}
	public void setColor(Color aColor) {color = aColor;}
	public void setName(String aName) {name = aName;}

}
