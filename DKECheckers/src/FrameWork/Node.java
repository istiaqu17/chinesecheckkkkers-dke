package FrameWork;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.ArrayList;

/**
 *
 * @author Stefan, Sara van de Moosdijk
 */
public class Node {
    private Node parent;
    private ArrayList<Node> children;
    private Board gameState;
    private int value = 0;
    private Move move;

    public Node(Board b){
        gameState = b;
        children = new ArrayList<Node>();
        parent = null;
        move = null;
    }

    public Node(Board b, Node p, Move m){
        gameState = b;
        children = new ArrayList<Node>();
        parent = p;
        move = m;
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
    
    public void setValue(int aValue){
    	value = aValue;
    }
    
    public int getValue(){
    	return value;
    }

    public Move getMove(){
        return move;
    }

    public int getDepth(){
        int depth = 0;
        Node node = this;
        while (node.getParent() != null){
            node = node.getParent();
            depth++;
        }
        return depth;
    }

    public int calculateSize(){
        int size = this.children.size();
        for (Node child: this.getChildren()){
            size += child.calculateSize();
        }
        return size;
    }

    public void printNode(){
        System.out.println("Depth: " + this.getDepth() + ", value: " + this.getValue() + ", children: " + this.getChildren().size());
        for (Node child: this.children){
            child.printNode();
        }
    }
}
