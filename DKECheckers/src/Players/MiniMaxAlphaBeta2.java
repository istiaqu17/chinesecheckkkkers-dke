/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Players;

import Evaluator.Evaluator;
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
    final private int DEPTH = 3;
    private Tree gameTree;
    private Evaluator evaluator;

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
//        gameTree.calculateSize();
//        gameTree.printTree();
        return determineMove();
    }

    private Move determineMove() {
        Node node = gameTree.getRoot().getChildren().get(0);
        for (Node child : gameTree.getRoot().getChildren()) {
            if (node.getValue() < child.getValue()) {
                node = child;
            }
        }
        return node.getMove();
    }

    private Node expandNode(Node node) {
        Position[] playerPositions = node.getGameState().findPieces(this.color);
        ArrayList<Move> moves = new ArrayList<Move>();

        Player player = node.getGameState().getPlayers()[node.getGameState().getTurn()];
        for (Position position : playerPositions) {
            moves.addAll(node.getGameState().determineValidMoves(position));
        }
        A:
        for (Move move : moves) {
            Node child = new Node(node.getGameState().simulateMove(move), node, move);
            node.addChild(child);
            if (child.getDepth() < DEPTH) {
                child = expandNode(child);
            } else {
                child.setValue(evaluator.evaluate(child, child.getGameState().getPlayers()[child.getGameState().getTurn()]));
            }

            if (player.getColor() == this.color) {
                if (!node.isValueSet()) {
                    node.setValue(Integer.MIN_VALUE);
                }
                int max = Math.max(node.getValue(), child.getValue());
                node.setValue(max);
                if (node.getParent() != null) {
                    if (node.getParent().getBeta() < max) {
                        break A;
                    }
                    node.getParent().setAlpha(max);
                }
            } else {
                if (!node.isValueSet()) {
                    node.setValue(Integer.MAX_VALUE);
                }
                int min = Math.min(node.getValue(), child.getValue());
                node.setValue(min);
                if (node.getParent() != null) {
                    if (node.getParent().getAlpha() > min) {
                        break A;
                    }
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

    public void setEvaluator(Evaluator evaluator){
        this.evaluator = evaluator;
    }

    public Player copy() {
        Position[] newGoalPositions = new Position[this.getGoal().length];
        for (int i = 0; i < goalPositions.length; i++) {
            newGoalPositions[i] = goalPositions[i].copy();
        }
        return new MiniMaxAlphaBeta2(this.getName(), this.getColor(), newGoalPositions);
    }
}
