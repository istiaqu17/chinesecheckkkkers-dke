/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FrameWork;

import FrameWork.Board;
import FrameWork.Move;
import FrameWork.Node;
import FrameWork.Position;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Stefan
 */
public class Tree {
    private Node root;
    
    public Tree(Board b, int depth, Color color){
        root = new Node(b);
        int currentDepth = 0;
        boolean won = false;
        boolean lost = false;
        ArrayList<Move> moves = new ArrayList<Move>();
        ArrayList<Node> nodesAtCurrentDepth = new ArrayList<Node>();
        ArrayList<Node> nodesAtNextDepth = new ArrayList<Node>();
        nodesAtCurrentDepth.add(root);

        //make the tree until a certion depth or until someone wins
        while(currentDepth < depth && !won && !lost){
            //do things for all nodes at this depth
            for(Node n: nodesAtCurrentDepth){
                //get all possible next moves for this player
                for(Position p: n.getGameState().findPieces(color))
                    moves.addAll(n.getGameState().determineValidMoves(p));

                //make the new nodes for every possible resulting board
                for(Move m: moves){
                    Node kid = new Node(n.getGameState().doMove(m), n);
                    n.addChild(kid);
                    //check if this player or another as won
                    if(kid.getGameState().getWinner().getColor() != null){
                        if(kid.getGameState().getWinner().getColor() == color)
                            won = true;
                        else
                            lost = true;
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
