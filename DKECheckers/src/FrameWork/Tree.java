package FrameWork;

import java.awt.Color;
import java.util.ArrayList;

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
}