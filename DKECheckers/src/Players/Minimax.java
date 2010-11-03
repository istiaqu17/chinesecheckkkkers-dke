package Players;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import FrameWork.Board;
import FrameWork.Move;
import FrameWork.Node;
import FrameWork.Position;
import FrameWork.Tree;
import java.awt.Color;

/**
 *
 * @author Rik Claessens, Sara van de Moosdijk
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
//        throw new UnsupportedOperationException("Not supported yet.");
        int treeDepth = 1;
        Tree gameTree = new Tree(b, treeDepth);
        return getMinimaxNode(gameTree.getRoot()).getMove();
    }

    public Player copy() {
        Position[] newGoalPositions = new Position[this.getGoal().length];
        for (int i = 0; i < this.getGoal().length; i++){
            newGoalPositions[i] = this.getGoal()[i].copy();
        }
        return new HumanPlayer(this.getName(), this.getColor(), newGoalPositions);
    }
    
    public int evaluate(Node node) 
    {
    	int value = 0;
    	//1. Check if the move is a winning move.
    	if (node.getGameState().getWinner() != null) 
    	{
    		//Add a lot of points if the winner is the player.
    		if(node.getGameState().getWinner() == this) {value += 1000;}
    		//Subtract a lot of points if the winner is the opponent.
    		else {value -= 1000;}
    	}
    	
    	Position[] positions = node.getGameState().findPieces(color);
    	
    	//2. Check the total distance from the goal.
    	int totalDistanceGoal = 0;
    	Position checkpoint = goalPositions[0];
    	for(Position p: positions) 
    	{
    		totalDistanceGoal += p.distanceTo(checkpoint);
    	}
    	value += totalDistanceGoal/2;
    	
    	//3. Check the total distance between the player's pieces (grouping factor).
    	int totalDistancePieces = 0;
    	for(Position p: positions) 
    	{
    		for(Position p2: positions) 
    		{
    			totalDistancePieces += p.distanceTo(p2);
    		}
    	}
    	value += totalDistancePieces/2;
    	
    	//4. Check how close the player's pieces are to the centerline.
    	
    	//5. Check how close the player's pieces are to the opponent's pieces. 
    	
    	return value;
    	}

    public Node getMinimaxNode(Node n){
        Player[] players = n.getGameState().getPlayers();
        int turn = n.getGameState().getTurn();

        if(n.getChildren() == null){
            n.setValue(evaluate(n));
            return n;
        }
        else{
            //only 2 players, I'll change that later, first make this working
            if(players[turn] == this){
                Node max = null;
                for(Node kid: n.getChildren()){
                    if(max == null)
                        max = kid;
                    else if(getMinimaxNode(kid).getValue() > max.getValue())
                        max = kid;
                }
                return max;
            }
            else{
                Node min = null;
                for(Node kid: n.getChildren()){
                    if(min == null)
                        min = kid;
                    else if(getMinimaxNode(kid).getValue() < min.getValue())
                        min = kid;
                }
                return min;
            }
        }

    }
}
