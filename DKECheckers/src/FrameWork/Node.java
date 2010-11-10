//package FrameWork;
//
///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//import Players.Player;
//import java.util.ArrayList;
//
///**
// *
// * @author Stefan, Sara van de Moosdijk
// */
//public class Node {
//
//    private Node parent;
//    private ArrayList<Node> children;
//    private Board gameState;
//    private int value = 0;
//    private Move move;
//    private int alpha = Integer.MIN_VALUE;
//    private int beta = Integer.MAX_VALUE;
//    private boolean valueSet = false;
//    private static int index = 0;
//
//    public Node(Board b) {
//        gameState = b;
//        children = new ArrayList<Node>();
//        parent = null;
//        move = null;
////        System.out.println(index++);
//    }
//
//    public Node(Board b, Node p, Move m) {
//        gameState = b;
//        children = new ArrayList<Node>();
//        parent = p;
//        move = m;
////        System.out.println(index++);
//    }
//
//    public void addChild(Node c) {
//        children.add(c);
//    }
//
//    public Node getParent() {
//        return parent;
//    }
//
//    public ArrayList<Node> getChildren() {
//        return children;
//    }
//
//    public Board getGameState() {
//        return gameState;
//    }
//
//    public boolean isRoot() {
//        return parent == null;
//    }
//
//    public void setValue(int v) {
//        value = v;
//        valueSet = true;
//    }
//
//    public boolean isValueSet() {
//        return valueSet;
//    }
//
//    public int getValue() {
//        return value;
//    }
//
//    public Move getMove() {
//        return move;
//    }
//
//    public int getDepth() {
//        int depth = 0;
//        Node node = this;
//        while (node.getParent() != null) {
//            node = node.getParent();
//            depth++;
//        }
//        return depth;
//    }
//
//    public int calculateSize() {
//        int size = this.children.size();
//        for (Node child : this.getChildren()) {
//            size += child.calculateSize();
//        }
//        return size;
//    }
//
//    public void printNode() {
//        for (int i = 0; i < this.getDepth(); i++) {
//            System.out.print("\t");
//        }
//        System.out.println("Depth: " + this.getDepth()
//                + ", value: " + this.getValue()
//                + ", alpha: " + this.getAlpha()
//                + ", beta: " + this.getBeta()
//                + ", children: " + this.getChildren().size());
//        int i = 0;
//        for (Node child : this.children) {
//            i++;
//            if (i < 10) {
//                System.out.print(" ");
//            }
//            System.out.print(i + " ");
//            child.printNode();
//        }
//    }

//    public void setChildren(ArrayList<Node> children) {
//        this.children = children;
//    }
//
//    public int getAlpha() {
//        return alpha;
//    }
//
//    public void setAlpha(int a) {
//        alpha = a;
////        if (this.getParent() != null) {
////            for (Node node : this.getParent().getChildren()) {
////                node.setAlpha(a);
////            }
////        }
//    }
//
//    public int getBeta() {
//        return beta;
//    }
//
//    public void setBeta(int b) {
//        beta = b;
////        if (this.getParent() != null) {
////            for (Node node : this.getParent().getChildren()) {
////                node.setBeta(b);
////            }
////        }
//    }
//
//    public void setAlphaOrBeta(Player turn, int value, Player minimax) {
//        if (parent != null) {
//            if (turn.getColor() == minimax.getColor()) {
//                if (value < parent.getBeta()) {
//                    parent.setBeta(value);
//                }
//            } else {
//                if (value > parent.getAlpha()) {
//                    parent.setAlpha(value);
//                }
//            }
//        }
//    }
//}


package FrameWork;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import Players.Player;
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
    private int alpha = Integer.MIN_VALUE;
    private int beta = Integer.MAX_VALUE;
    boolean valueSet = false;

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
        valueSet = true;
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

    public void printNode() {
        for (int i = 0; i < this.getDepth(); i++) {
            System.out.print("\t");
        }
        System.out.println("Depth: " + this.getDepth()
                + ", value: " + this.getValue()
                + ", alpha: " + this.getAlpha()
                + ", beta: " + this.getBeta()
                + ", children: " + this.getChildren().size());
        int i = 0;
        for (Node child : this.children) {
            i++;
            if (i < 10) {
                System.out.print(" ");
            }
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

    public void setAlphaOrBeta(Player turn, int value, Player minimax){
        if (parent != null){
            if(turn.getColor() == minimax.getColor()){
                if(value < parent.getBeta())
                    parent.setBeta(value);
            } else {
                if(value > parent.getAlpha())
                    parent.setAlpha(value);
            }
        }
    }

     public boolean isValueSet() {
        return valueSet;
    }
}