package FrameWork;



import Players.Player;
import java.util.ArrayList;

/**
 *
 * @author Stefan
 */
public class Tree {

    private Node root;

    public Tree(Board board){
        root = new Node(board);
    }

//    public Tree(Board b, int depth) {
//        root = new Node(b);
//        int currentDepth = 0;
//        Player[] players = b.getPlayers();
//        int turn = b.getTurn();
//        ArrayList<Move> moves = new ArrayList<Move>();
//        ArrayList<Node> nodesAtCurrentDepth = new ArrayList<Node>();
//        ArrayList<Node> nodesAtNextDepth = new ArrayList<Node>();
//        nodesAtCurrentDepth.add(root);
//
//        //make the tree until a certain depth
//        while (currentDepth < depth) {
//            //do things for all nodes at this depth
//            for (Node n : nodesAtCurrentDepth) {
//                //only make children if there isn't a winner already
//                if(n.getGameState().getWinner() == null){
//                    //get all the valid moves
//                    for (Position p : n.getGameState().findPieces(players[turn].getColor())) {
//                        moves.addAll(n.getGameState().determineValidMoves(p));
//                    }
//
//                    //make the new nodes for every possible resulting board
//                    for (Move m : moves) {
//                        //make child and attach it to its parent (n) and attach the move that "made" this board to it
//                        Node kid = new Node(n.getGameState().simulateMove(m), n, m);
//                        n.addChild(kid);
//                    }
//                    nodesAtNextDepth.addAll(n.getChildren());
//                }
//            }
//            //prepare for going to the next depth
//            nodesAtCurrentDepth = nodesAtNextDepth;
//            nodesAtNextDepth.clear();
//            depth++;
//            turn++;
//        }
//    }
//
    public Node getRoot(){
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}