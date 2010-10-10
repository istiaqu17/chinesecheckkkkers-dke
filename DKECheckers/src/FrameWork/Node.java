/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FrameWork;

import java.util.ArrayList;

/**
 *
 * @author Stefan
 */
public class Node {
    private Node parent;
    private ArrayList<Node> children;
    private Board gameState;

    public Node(Board b){
        gameState = b;
        children = new ArrayList<Node>();
    }

    public Node(Board b, Node p){
        gameState = b;
        children = new ArrayList<Node>();
        parent = p;
    }

    public void addChild(Node c){
        children.add(c);
    }

    public Node getParent(){
        return parent;
    }

    public ArrayList<Node> getChildren(){
        return children;
    }

    public Board getGameState(){
        return gameState;
    }

    public boolean isRoot(){
        return parent == null;
    }
}
