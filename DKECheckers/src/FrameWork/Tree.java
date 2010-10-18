package FrameWork;

import java.awt.Color;
import java.util.ArrayList;

import Players.Player;

/**
 *
 * @author Stefan, Sara van de Moosdijk
 */
public class Tree {

    private Node root;

    public Tree(Board b, int depth, Color color) {
        root = new Node(b);
        int currentDepth = 0;
        boolean won = false;
        boolean lost = false;
        ArrayList<Move> moves = new ArrayList<Move>();
        ArrayList<Node> nodesAtCurrentDepth = new ArrayList<Node>();
        ArrayList<Node> nodesAtNextDepth = new ArrayList<Node>();
        nodesAtCurrentDepth.add(root);

        //make the tree until a certain depth or until someone wins
        while (currentDepth < depth && !won && !lost) {
            //do things for all nodes at this depth
            for (Node n : nodesAtCurrentDepth) {
                //get all possible next moves for this player or opponent (only in 2 player mode, more players: change the 2)
                if (depth % 2 == 0) {
                    for (Position p : n.getGameState().findPieces(color)) {
                        moves.addAll(n.getGameState().determineValidMoves(p));
                    }
                } else {
                    for (Position p : n.getGameState().findOpponentsPieces(color)) //you can only do this in 2 player games
                    {
                        moves.addAll(n.getGameState().determineValidMoves(p));
                    }
                }

                //make the new nodes for every possible resulting board
                for (Move m : moves) {
                    Node kid = new Node(n.getGameState().simulateMove(m), n);
                    n.addChild(kid);
                    //check if this player or another as won
                    if (kid.getGameState().getWinner().getColor() != null) {
                        if (kid.getGameState().getWinner().getColor() == color) {
                            won = true;
                        } else {
                            lost = true;
                        }
                    }
                }
                nodesAtNextDepth.addAll(n.getChildren());
            }
            //prepare for going to the next depth
            nodesAtCurrentDepth = nodesAtNextDepth;
            depth++;
        }

    }
    
    public void evaluate(Node node, Player player) 
    {
    	//1. Check if the move is a winning move.
    	if (node.getGameState().getWinner() != null) 
    	{
    		//Add a lot of points if the winner is the player.
    		if(node.getGameState().getWinner() == player) {node.addToValue(1000);}
    		//Subtract a lot of points if the winner is the opponent.
    		else {node.addToValue(-1000);}
    	}
    	
    	Position[] positions = node.getGameState().findPieces(player.getColor());
    	
    	//2. Check the total distance from the goal.
    	int totalDistanceGoal = 0;
    	Position[] goal = player.getGoal();
    	Position checkpoint = goal[1];
    	for(Position p: positions) 
    	{
    		totalDistanceGoal += p.distanceTo(checkpoint);
    	}
    	node.addToValue(totalDistanceGoal/2);
    	
    	//3. Check the total distance between the player's pieces (grouping factor).
    	int totalDistancePieces = 0;
    	for(Position p: positions) 
    	{
    		for(Position p2: positions) 
    		{
    			totalDistancePieces += p.distanceTo(p2);
    		}
    	}
    	node.addToValue(totalDistancePieces/2);
    	
    	//4. Check how close the player's pieces are to the centerline.
    	}

    }
