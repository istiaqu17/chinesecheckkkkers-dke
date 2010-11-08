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
    private int alpha = -1000000000;
    private int beta = 1000000000;

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
    
    public void setValue(int v){
    	value = v;
        alpha = v;
        beta = v;
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
        int i = 0;
        for (Node child: this.children){
            i++;
            System.out.print(i + " ");
            child.printNode();
        }
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public int getAlpha(){
        return alpha;
    }

    public void setAlpha(int a){
        alpha = a;
    }

    public int getBeta(){
        return beta;
    }

    public void setBeta(int b){
        beta = b;
    }
}
