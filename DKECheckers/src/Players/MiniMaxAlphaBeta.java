package Players;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import Evaluator.Evaluator;
import Filters.Filter;
import FrameWork.Board;
import FrameWork.Move;
import FrameWork.Node;
import FrameWork.Position;
import FrameWork.Tree;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Stefan van der Horst
 */
public class MiniMaxAlphaBeta implements Player {

    private String name;
    private Color color;
    private Position[] goalPositions, basePositions;
    private int depth = 3;
    private Tree gameTree;
    private Evaluator evaluator;
    private Filter[] filters;
//    private int index = 0;        //for testing the MoveSorter

    public MiniMaxAlphaBeta() {
    }

    public MiniMaxAlphaBeta(String name, Color color, Position[] goal) {
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
        gameTree = new Tree(b);
        gameTree.setRoot(new Node(b));
        gameTree.setRoot(expandNode(gameTree.getRoot()));
//        gameTree.printTree();
        return getMove();
    }

    public Node expandNode(Node node) {
        ArrayList<Move> moves = new ArrayList<Move>();
        Player player = node.getGameState().getPlayers()[node.getGameState().getTurn()];
        for (Position position : node.getGameState().findPieces(player.getColor())) {
            moves.addAll(node.getGameState().determineValidMoves(position));
        }

        if (filters != null) {
            for (int i = 0; i < filters.length; i++) {
                moves = filters[i].filter(moves, player);
            }
        }
        for (Move move : moves) {
            if (node.getParent() != null) {
                if (node.getAlpha() >= node.getParent().getBeta() || node.getBeta() <= node.getParent().getAlpha()) {
                    break;
                }
            }

            Node child = new Node(node.getGameState().simulateMove(move), node, move);
            node.addChild(child);
            if (child.getDepth() < depth && child.getGameState().getWinner() == null) {
                child = expandNode(child);
            } else {
                int childValue = evaluator.evaluate(child, child.getGameState().getPlayers()[child.getGameState().getTurn()]);
                child.setValue(childValue);
                child.setAlphaOrBeta(child.getGameState().getPlayers()[child.getGameState().getTurn()], childValue, this);
            }
        }
        int firstChildValue = node.getChildren().get(0).getValue();
        node.setValue(firstChildValue);

        for (Node child : node.getChildren()) {
            if (player.getColor() == color) {
                node.setValue(Math.max(node.getValue(), child.getValue()));
            } else {
                node.setValue(Math.min(node.getValue(), child.getValue()));
            }
        }
        node.setAlphaOrBeta(player, node.getValue(), this);
        return node;
    }

    public Player copy() {
        Position[] newGoalPositions = new Position[this.getGoal().length];
        for (int i = 0; i < goalPositions.length; i++) {
            newGoalPositions[i] = goalPositions[i].copy();
        }
        return new MiniMaxAlphaBeta(this.getName(), this.getColor(), newGoalPositions);
    }

    public Move getMove() {
        Node node = null;
        for (Node child : gameTree.getRoot().getChildren()) {
            if (node == null) {
                node = child;
            } else if (child.getValue() > node.getValue()) {
                node = child;
            }
        }
        return node.getMove();
    }

    public void setEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    public void setBase(Position[] base) {
        basePositions = base;
    }

    public Position[] getBase() {
        return basePositions;
    }

    public void setFilters(Filter[] filters) {
        this.filters = filters;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
