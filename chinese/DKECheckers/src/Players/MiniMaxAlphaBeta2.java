/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Players;

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
 * @author Rik Claessens
 */
public class MiniMaxAlphaBeta2 implements Player {

    private String name;
    private Color color;
    private Position[] goalPositions, basePositions;
    private int depth = 3;
    private Tree gameTree;
    private Evaluator evaluator;
    private Filter[] filters;

    public MiniMaxAlphaBeta2() {
    }

    public MiniMaxAlphaBeta2(String name, Color color, Position[] goal) {
        this.name = name;
        this.color = color;
        this.goalPositions = goal;
    }

    public Move makeMove(Board board) {
        gameTree = new Tree(board);
        gameTree.setRoot(expandNode(gameTree.getRoot()));
        return determineMove();
    }

    private Move determineMove() {
        Node node = gameTree.getRoot().getChildren().get(0);
        // Pick the node at the first level with the highest value
        for (Node child : gameTree.getRoot().getChildren()) {
            if (node.getValue() < child.getValue()) {
                node = child;
            }
        }
        return node.getMove();
    }

    private Node expandNode(Node node) {
        ArrayList<Move> moves = new ArrayList<Move>();
        Player player = node.getGameState().getPlayers()[node.getGameState().getTurn()];
        // Determine the positions with a piece of the current player
        Position[] playerPositions = node.getGameState().findPieces(player.getColor());
        // Put all possible moves in a list
        for (Position position : playerPositions) {
            moves.addAll(node.getGameState().determineValidMoves(position));
        }
        // Possibly filter the set of moves
        if (filters != null) {
            for (int i = 0; i < filters.length; i++) {
                moves = filters[i].filter(moves, player);
            }
        }
        A:
            // If they are not pruned, adds all moves as a child to the current node
        for (Move move : moves) {
            // The child is the resulting board from the current move
            Node child = new Node(node.getGameState().simulateMove(move), node, move);
            // Add the child
            node.addChild(child);
            // If the child is not at the required depth, expand it (depth-first)
            if (child.getDepth() < depth) {
                child = expandNode(child);
            } else {
                // if it is on the required depth (it is a leave) evaluate it
                child.setValue(evaluator.evaluate(child, this));
            }

            // if this is a max turn
            if (player.getColor() == this.color) {
                // Set the value to the minimum value
                if (!node.isValueSet()) {
                    node.setValue(Integer.MIN_VALUE);
                }
                // determine the maximum value of the the children, if there is child with a bigger value than the till this moment found value, make this the value of the node
                int max = Math.max(node.getValue(), child.getValue());
                node.setValue(max);
                // If the node is not the root
                if (node.getParent() != null) {
                    // And the beta is smaller than the maximum value of the node at the min depth, PRUNE!
                    // At this point, the minimum player will never choose this node, since it has a higher value than a sibling of this node (the min player will choose the lowest value)
                    if (node.getParent().getBeta() < max) {
                        break A;
                    }
                    // else change the lower bound of the min level
                    node.getParent().setAlpha(max);
                }
            } else {
                // Set the value to the maximum value
                if (!node.isValueSet()) {
                    node.setValue(Integer.MAX_VALUE);
                }
                // determine the minimum value of the the children, if there is child with a lower value than the till this moment found value, make this the value of the node
                int min = Math.min(node.getValue(), child.getValue());
                node.setValue(min);
                // if the node is not the root
                if (node.getParent() != null) {
                    // And the alpha is bigger than the minimum value of the node at the max depth, PRUNE!
                    // At this point, the maximum player will never choose this node, since it has a lower value than a sibling of this node (the max player will choose the highest value)
                    if (node.getParent().getAlpha() > min) {
                        break A;
                    }
                    // else change the upper bound of the max level
                    node.getParent().setBeta(min);
                }
            }
        }
        return node;
    }

    public void setColor(Color aColor) {
        this.color = aColor;
    }

    public Color getColor() {
        return this.color;
    }

    public void setName(String aName) {
        this.name = aName;
    }

    public String getName() {
        return this.name;
    }

    public void setBase(Position[] base) {
        this.basePositions = base;
    }

    public Position[] getBase() {
        return this.basePositions;
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

    public void setEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    public void setFilters(Filter[] filters) {
        this.filters = filters;
    }

    public void setDepth(int depth){
        this.depth = depth;
    }

    public Player copy() {
        Position[] newGoalPositions = new Position[this.getGoal().length];
        for (int i = 0; i < goalPositions.length; i++) {
            newGoalPositions[i] = goalPositions[i].copy();
        }
        return new MiniMaxAlphaBeta2(this.getName(), this.getColor(), newGoalPositions);
    }
}
