package FrameWork;

/**
 *
 * @author Stefan
 */
public class Tree {

    private Node root;
    private int size = 0;

    public Tree(Board board){
        root = new Node(board);
    }

    public Node getRoot(){
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void calculateSize(){
        System.out.println("+++++++++++++++++++ Tree size: " + root.calculateSize());
    }

    public void printTree(){
        root.printNode();
        calculateSize();
    }
}