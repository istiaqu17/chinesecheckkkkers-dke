package FrameWork;

import Players.Player;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Rik Claessens
 */
public class Board extends JPanel {

    private Position[][] positions = new Position[17][17];
    private Position[][] playerBases;
    private Position[] movingPiecePositions = new Position[]{null, null};
    private Position selectedPosition;
    private int turn = -1;
    private int moveRepeats = 0;
    private int[] mouseClickLocation = new int[]{-1, -1};
    private int[] mouseHoverLocation = new int[]{-1, -1};
    private int[][] rowLengths = new int[][]{{4, 1}, {4, 2}, {4, 3}, {4, 4}, {0, 13}, {1, 12}, {2, 11}, {3, 10}, {4, 9}, {4, 10}, {4, 11}, {4, 12}, {4, 13}, {9, 4}, {10, 3}, {11, 2}, {12, 1}};
    private double[] movingPieceCoordinates;
    private boolean allowMouseInput;
    private boolean allowPieceSelection = true;
    private Color[] colors = new Color[]{Color.YELLOW, Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA};
    private Piece movingPiece;
    private Timer timer;
    private ArrayList<Position> validMovePositions = new ArrayList<Position>();
    private ArrayList<Position> positionsChecked = new ArrayList<Position>();
    private ArrayList<Move> validMoves = new ArrayList<Move>();
    private Player[] players;
    private JPanel turnPanel;

    /*
     * Constructor
     */
    public Board(int size, Player[] players) {
        this.setSize(size, size);
        this.players = players;
        createBoard();
        createTurnPanel();
        nextTurn();
    }

    /*
     * Resets the starting base of a player by placing all 10 pieces in the starting triangle
     */
    public void setPlayerBase(int x, Player player) {
        for (int i = 0; i < playerBases[x].length; i++) {
            playerBases[x][i].addPiece(new Piece(colors[x]));
        }
        player.setGoal(playerBases[(x + 3) % 6]);
        player.setColor(colors[x]);
    }

    /*
     * Sets the goal for a player
     */
    public void setPlayerGoal(int x) {
        for (int i = 0; i < playerBases[x].length; i++) {
            playerBases[x][i].addPiece(new Piece(colors[x]));
        }
    }

    /*
     * Creates all the positions on the board. Position in the 17-17 array which are not on the board will be null.
     */
    private void createBoard() {
        /*
         * See the paper with the 17x17 array for explanation of the way the board is saved
         */
        for (int i = 0; i < rowLengths.length; i++) {
            for (int j = 0; j < rowLengths[i][0]; j++) {
                positions[i][j] = null;
            }
            for (int j = rowLengths[i][0]; j < rowLengths[i][0] + rowLengths[i][1]; j++) {
                int x = (positions.length - i - 12) * (this.getWidth() / (2 * positions.length)) + j * (this.getWidth() / (positions.length));
                int y = i * (this.getHeight() / (positions.length));
                positions[i][j] = new Position(x, y, i, j, null);
            }
            for (int j = rowLengths[i][0] + rowLengths[i][1]; j < rowLengths.length; j++) {
                positions[i][j] = null;
            }
        }

        /*
         * Handles when the mouse is moved over the board
         */
        class boardHovered implements MouseMotionListener {

            public void mouseDragged(MouseEvent e) {
            }

            public void mouseMoved(MouseEvent e) {
                if (allowMouseInput) {
                    mouseHoverLocation = getPosition(e.getX(), e.getY());
                    repaint();
                }
            }
        }

        /*
         * Handles when the board is clicked
         */
        class boardClicked implements MouseListener {

            public void mouseClicked(MouseEvent e) {
                // If there is a piece moving, or an AI player is working, the user should not be able to input anything
                if (allowMouseInput) {
                    // calculates the position the user clicked
                    mouseClickLocation = getPosition(e.getX(), e.getY());
                    // checks if the position is on the board, if there is a piece and if the piece is that player's color
                    if (mouseClickLocation[0] != -1 && positions[mouseClickLocation[1]][mouseClickLocation[0]] != null) {
                        if (positions[mouseClickLocation[1]][mouseClickLocation[0]].getPiece() != null
                                && positions[mouseClickLocation[1]][mouseClickLocation[0]].getPiece().getColor() == players[turn].getColor()
                                && allowPieceSelection) {
                            selectedPosition = positions[mouseClickLocation[1]][mouseClickLocation[0]];
                            validMovePositions.clear();
                            validMoves = determineValidMoves(positions[mouseClickLocation[1]][mouseClickLocation[0]]);
                            for (Move move : validMoves) {
                                for (int i = 1; i < move.getPositions().length; i++) {
                                    validMovePositions.add(move.getPositions()[i]);
                                }
                            }
                        } // if the user already selected a piece, check ik the position he clicked now is empty to check if the user is able to move the piece to this position
                        else if (positions[mouseClickLocation[1]][mouseClickLocation[0]].getPiece() == null && selectedPosition != null) {
                            Position targetPosition = positions[mouseClickLocation[1]][mouseClickLocation[0]];
                            Move move = new Move(null);
                            A:
                            for (int i = 0; i < validMoves.size(); i++) {
                                for (int j = 1; j < validMoves.get(i).getPositions().length; j++) {
                                    if (validMoves.get(i).getPositions()[j] == targetPosition) {
                                        Position[] newMove = new Position[j + 1];
                                        for (int k = 0; k < j + 1; k++) {
                                            newMove[k] = validMoves.get(i).getPositions()[k];
                                        }
                                        move = new Move(newMove);
                                        break A;
                                    }
                                }
                            }
                            if (move.getPositions() != null && isValidMove(move)) {
                                movePiece(move);
                            }
                            resetMouseInput();
                        }
                    }
                    repaint();
                }
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        }
        addMouseMotionListener(new boardHovered());
        addMouseListener(new boardClicked());
        /*
         * Starting bases for each of the six players
         */
        playerBases = new Position[][]{{positions[0][4], positions[1][4], positions[1][5], positions[2][4], positions[2][5],
                        positions[2][6], positions[3][4], positions[3][5], positions[3][6], positions[3][7]},
                    {positions[4][0], positions[4][1], positions[4][2], positions[4][3], positions[5][1],
                        positions[5][2], positions[5][3], positions[6][2], positions[6][3], positions[7][3]},
                    {positions[9][4], positions[10][4], positions[10][5], positions[11][4], positions[11][5],
                        positions[11][6], positions[12][4], positions[12][5], positions[12][6], positions[12][7]},
                    {positions[13][9], positions[13][10], positions[13][11], positions[13][12], positions[14][10],
                        positions[14][11], positions[14][12], positions[15][11], positions[15][12], positions[16][12]},
                    {positions[9][13], positions[10][13], positions[10][14], positions[11][13], positions[11][14],
                        positions[11][15], positions[12][13], positions[12][14], positions[12][15], positions[12][16]},
                    {positions[4][9], positions[4][10], positions[4][11], positions[4][12], positions[5][10],
                        positions[5][11], positions[5][12], positions[6][11], positions[6][12], positions[7][12]}
                };
        setupBoard();
    }

    /*
     * Calculates which position the mouse is pointing to
     */
    public int[] getPosition(double x, double y) {
        int[] position = new int[2];
        double width = this.getWidth();
        int j = (int) (y / (width / positions.length));
        A:
        for (int i = 0; i < positions[j].length; i++) {
            if (positions[j][i] != null) {
                double[] center = new double[]{positions[j][i].getX() + (width / (2 * positions.length)), positions[j][i].getY() + (width / (2 * positions.length))};
                if (Math.sqrt(Math.pow(x - center[0], 2) + Math.pow(y - center[1], 2)) <= width / (2 * positions.length)) {
                    position = new int[]{i, j};
                    break A;
                } else {
                    position = new int[]{-1, -1};
                }
            }
        }
        return position;
    }

    // Determines all the valid Moves for a position, including hopmoves
    public ArrayList<Move> determineValidMoves(Position position) {
        // validMoves will store ALL valid moves
        ArrayList<Move> validMoves = new ArrayList<Move>();
        // validHopMoves will store the hopmoves which have been found in one check
        ArrayList<Move> validHopMoves = new ArrayList<Move>();
        // At the start no positions have been checked
        positionsChecked.clear();
        // Calculate the index of the position which needs to be checked for valid moves
        int i = position.getI();
        int j = position.getJ();
        // Relative surroundings positions
        int[][] positionsToCheck = new int[][]{{-1, -1}, {-1, 0}, {0, 1}, {1, 1}, {1, 0}, {0, -1}};
        for (int k = 0; k < positionsToCheck.length; k++) {
            if (i + positionsToCheck[k][0] >= 0 && i + positionsToCheck[k][0] <= 16 && j + positionsToCheck[k][1] >= 0 && j + positionsToCheck[k][1] <= 16) {
                // Checks for valid moves of 1 position
                if (positions[i + positionsToCheck[k][0]][j + positionsToCheck[k][1]] != null && positions[i + positionsToCheck[k][0]][j + positionsToCheck[k][1]].getPiece() == null) {
                    validMoves.add(new Move(new Position[]{position, positions[i + positionsToCheck[k][0]][j + positionsToCheck[k][1]]}));
                }
            }
            if ((i + (2 * positionsToCheck[k][0])) >= 0 && i + (2 * positionsToCheck[k][0]) <= 16 && j + (2 * positionsToCheck[k][1]) >= 0 && j + (2 * positionsToCheck[k][1]) <= 16) {
                // Checks for hops
                if (positions[i + (2 * positionsToCheck[k][0])][j + (2 * positionsToCheck[k][1])] != null
                        && positions[i + positionsToCheck[k][0]][j + positionsToCheck[k][1]].getPiece() != null
                        && positions[i + (2 * positionsToCheck[k][0])][j + (2 * positionsToCheck[k][1])].getPiece() == null) {
                    // Make a new move with starting position and endposition
                    Move newValidMove = new Move(new Position[]{position, positions[i + (2 * positionsToCheck[k][0])][j + (2 * positionsToCheck[k][1])]});
                    // Add the move to the list
                    validHopMoves.add(newValidMove);
                }
            }
        }
        // You only need to check for further hops if the player is able to make a hop from the starting position
        boolean needToCheck = validHopMoves.size() > 0;
        // Add all the hopmoves to the valid Move list
        validMoves.addAll(validHopMoves);
        for (Move validHopMove : validHopMoves) {
            positionsChecked.add(validHopMove.getPositions()[validHopMove.getPositions().length - 1]);
        }
        // newValidHopMoves will store all the newly found hop moves from the positions which are already involved in a hop move
        ArrayList<Move> newValidHopMoves = new ArrayList<Move>();
        // As long as you find new valid positions to which a piece can hop, keep checking for new ones
        while (needToCheck) {
            // For all the hop moves found in the previous checked position, check for further hop moves
            for (Move validHopMove : validHopMoves) {
                // Add the newly found hopmoves to the temporary list
                newValidHopMoves.addAll(determineValidHopMoves(validHopMove));
            }
            for (Move newValidHopMove : newValidHopMoves) {
                positionsChecked.add(newValidHopMove.getPositions()[newValidHopMove.getPositions().length - 1]);
            }
            // Add all the newly found valid hop moves to the valid moves list
            validMoves.addAll(newValidHopMoves);
            // Now clear the hopmoves found in the previous check
            validHopMoves.clear();
            // Add all the newly found valid hop moves to the validHopMoves so that you can check these new position in the next run of the while-loop
            validHopMoves.addAll(newValidHopMoves);
            // Clear the newValidHopMoves list so that if you find new ones during the next run of the while-loop, the program doesn't check the same things twice
            newValidHopMoves.clear();
            // Only check again when you just found new positions to hop
            needToCheck = validHopMoves.size() > 0;
        }
        return validMoves;
    }

    public ArrayList<Move> determineValidHopMoves(Move move) {
        ArrayList<Move> validHopMoves = new ArrayList<Move>();
        Position position = move.getPositions()[move.getPositions().length - 1];
        int i = position.getI();
        int j = position.getJ();
        int[][] positionsToCheck = new int[][]{{-1, -1}, {-1, 0}, {0, 1}, {1, 1}, {1, 0}, {0, -1}};
        for (int k = 0; k < positionsToCheck.length; k++) {
            if ((i + (2 * positionsToCheck[k][0])) >= 0 && i + (2 * positionsToCheck[k][0]) <= 16 && j + (2 * positionsToCheck[k][1]) >= 0 && j + (2 * positionsToCheck[k][1]) <= 16) {
                // Checks for hops
                Position positionToCheck = positions[i + (2 * positionsToCheck[k][0])][j + (2 * positionsToCheck[k][1])];
                if (positions[i + (2 * positionsToCheck[k][0])][j + (2 * positionsToCheck[k][1])] != null
                        && positions[i + positionsToCheck[k][0]][j + positionsToCheck[k][1]].getPiece() != null
                        && positions[i + (2 * positionsToCheck[k][0])][j + (2 * positionsToCheck[k][1])].getPiece() == null) {

                    // Checks if the position found, has already been found or not, only add the position to the list if it hasn't been found already
                    int[] validMoveCoordinates = new int[]{positionToCheck.getI(), positionToCheck.getJ()};
                    boolean positionAlreadyFound = false;
                    for (int m = 0; m < positionsChecked.size(); m++) {
                        int[] positionCheckedCoordinates = new int[]{positionsChecked.get(m).getI(), positionsChecked.get(m).getJ()};
                        if (positionCheckedCoordinates[0] == validMoveCoordinates[0] && positionCheckedCoordinates[1] == validMoveCoordinates[1]) {
                            positionAlreadyFound = true;
                            break;
                        }
                    }
                    // If the position has not been found already, then add is to the list of valid moves and add it to the list of positions which already have been checked
                    if (!positionAlreadyFound) {
                        // copies the positions the piece has to be moved to, to perform the multiple-hop move
                        Position[] movePositions = new Position[move.getPositions().length + 1];
                        for (int n = 0; n < move.getPositions().length; n++) {
                            movePositions[n] = move.getPositions()[n];
                        }
                        // add the newly found position last
                        movePositions[movePositions.length - 1] = positions[i + (2 * positionsToCheck[k][0])][j + (2 * positionsToCheck[k][1])];
                        Move newValidMove = new Move(movePositions);
                        validHopMoves.add(newValidMove);
                        positionsChecked.add(positionToCheck);
                    }
                }
            }
        }
        return validHopMoves;
    }

    // Checks if a move is valid
    public boolean isValidMove(Move move) {
        for (int i = 1; i < move.getPositions().length; i++){
            if (!(move.getPositions()[i] != null && move.getPositions()[i].getPiece() == null)){
                return false;
            }
        }
        return true;
    }

    /*
     * Displays the board, the pieces, the mouselocation, the valid moves etc.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[0].length; j++) {
                if (positions[i][j] != null) {
                    int width = (int) (this.getWidth() / (positions.length));
                    // All positions
                    g2.setColor(Color.LIGHT_GRAY);
                    g2.fillOval((int) positions[i][j].getX() + (int) (0.05 * width), (int) positions[i][j].getY() + (int) (0.05 * width), (int) (0.9 * width), (int) (0.9 * width));

                    // All pieces
                    if (positions[i][j].getPiece() != null) {
                        g2.setColor(positions[i][j].getPiece().getColor());
                        g2.fillOval((int) (positions[i][j].getX() + (0.1 * width)), (int) (positions[i][j].getY() + (0.1 * width)), (int) (0.8 * width), (int) (0.8 * width));

                    }
                    // Displays all the valid moves
                    for (Position validPosition : validMovePositions) {
                        if (positions[i][j] == validPosition) {
                            g2.setStroke(new BasicStroke(10f));
                            g2.setColor(new Color(0, 150, 175));
                            g2.drawOval((int) (positions[i][j].getX() + (0.05 * width)), (int) (positions[i][j].getY() + (0.05 * width)), (int) (0.9 * width), (int) (0.9 * width));
                            break;
                        }
                    }
                    // Displays where the mouse is pointing to if the cursor is on the board
                    if (i == mouseHoverLocation[1] && j == mouseHoverLocation[0]) {
                        g2.setStroke(new BasicStroke(6f));
                        g2.setColor(Color.DARK_GRAY);
                        g2.drawOval((int) (positions[i][j].getX() + (0.05 * width)), (int) (positions[i][j].getY() + (0.05 * width)), (int) (0.9 * width), (int) (0.9 * width));
                    }
                    // Displays which position the user has clicked
                    if (positions[i][j] == selectedPosition) {
                        g2.setStroke(new BasicStroke(6f));
                        g2.setColor(Color.BLACK);
                        g2.drawOval((int) (positions[i][j].getX() + (0.05 * width)), (int) (positions[i][j].getY() + (0.05 * width)), (int) (0.9 * width), (int) (0.9 * width));
                    }
                    // Displays a moving piece
                    if (movingPiece != null) {
                        g2.setColor(movingPiece.getColor());
                        g2.fillOval((int) (movingPieceCoordinates[0] + (0.1 * width)), (int) (movingPieceCoordinates[1] + (0.1 * width)), (int) (0.8 * width), (int) (0.8 * width));
                    }
                }
            }
        }
    }

    /*
     * Moves a piece from it's starting position to it's end position while traversing all the positions in between.
     */
    private void movePiece(Move move) {
        validMovePositions.clear();
        Piece piece = move.getPositions()[0].getPiece();
        move.getPositions()[0].removePiece();
        movingPiece = piece;
        movingPieceCoordinates = new double[]{move.getPositions()[0].getX(), move.getPositions()[0].getY()};
        movingPiecePositions = move.getPositions();
        class TimerListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                advanceMovingPiece();
            }
        }
        timer = new Timer(10, new TimerListener());
        timer.start();
        repaint();
    }

    /*
     * Moves a piece towards the destination
     */
    private void advanceMovingPiece() {
        int speed = 50;
        // Direction in which a piece has to move
        double dX = movingPiecePositions[1].getX() - movingPiecePositions[0].getX();
        double dY = movingPiecePositions[1].getY() - movingPiecePositions[0].getY();
        // Lets the piece move in small steps
        movingPieceCoordinates[0] += dX / speed;
        movingPieceCoordinates[1] += dY / speed;
        moveRepeats++;
        // If the number op steps taken is equal to the speed, the piece is on the new position
        if (moveRepeats == speed) {
            moveRepeats = 0;
            timer.stop();
            resetMovingPiece();
            // Checks if there are more positions to which the piece has to be moved in case the piece could hop over another
            if (movingPiecePositions.length > 2) {
                // Copies the positions
                Position[] copy = new Position[movingPiecePositions.length];
                for (int i = 0; i < movingPiecePositions.length; i++) {
                    copy[i] = movingPiecePositions[i];
                }
                movingPiecePositions = new Position[movingPiecePositions.length - 1];
                // Copies everything back except the old starting location, at this point the piece is at movingPiecePositions[1]
                for (int i = 0; i < copy.length - 1; i++) {
                    movingPiecePositions[i] = copy[i + 1];
                }
                movePiece(new Move(movingPiecePositions));
            } else {
                nextTurn();
                if (players[turn].isHuman()) {
                    allowMouseInput = true;
                }
            }
        }
        repaint();
    }

    /*
     * Resets the moving piece so that the moving piece is placed at it's destination and is no longer shown on screen.
     */
    private void resetMovingPiece() {
        movingPiecePositions[1].addPiece(movingPiece);
        movingPiece = null;
        repaint();
    }

    /*
     * Resets the selected pieces and positions
     */
    private void resetMouseInput() {
        validMovePositions.clear();
        mouseHoverLocation = new int[]{-1, -1};
        mouseClickLocation = new int[]{-1, -1};
        selectedPosition = null;
    }

    /*
     * Fills the board with the pieces
     */
    private void setupBoard() {
        switch (players.length) {
            case 0:
            case 1:
            case 5:
                System.out.println("Wrong player number");
                break;
            case 2:
                setPlayerBase(0, players[0]);
                setPlayerBase(3, players[1]);
                break;
            case 3:
                setPlayerBase(0, players[0]);
                setPlayerBase(2, players[1]);
                setPlayerBase(4, players[2]);
                break;
            case 4:
                setPlayerBase(1, players[0]);
                setPlayerBase(2, players[1]);
                setPlayerBase(4, players[2]);
                setPlayerBase(5, players[3]);
                break;
            case 6:
                setPlayerBase(0, players[0]);
                setPlayerBase(1, players[1]);
                setPlayerBase(2, players[2]);
                setPlayerBase(3, players[3]);
                setPlayerBase(4, players[4]);
                setPlayerBase(5, players[5]);
                break;

        }
    }

    /*
     * Sets the turn to the next player
     */
    private void nextTurn() {
        if (!checkForWinner()) {
            turn = (turn + 1) % players.length;
            turnPanel.setBackground(players[turn].getColor());
            allowPieceSelection = true;
            if (players[turn].isHuman()) {
                allowMouseInput = true;
            } else {
                movePiece(players[turn].makeMove(this));
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Player " + (turn + 1) + " is the winner",
                    "We have a winner",
                    JOptionPane.PLAIN_MESSAGE);
            allowMouseInput = false;
        }
    }

    /*
     * Shows who's turn it is
     */
    private void createTurnPanel() {
        turnPanel = new JPanel();
        JLabel label = new JLabel("Turn: ");
        label.setForeground(Color.DARK_GRAY);
        turnPanel.add(label);
        this.setLayout(null);
        turnPanel.setFocusable(false);
        add(turnPanel);
        turnPanel.setBounds(400, 10, 80, 40);
    }

    public Position[][] getBoardPositions() {
        return this.positions;
    }

    //this makes an array with all positions currently occupied by the pieces of this player
    public Position[] findPieces(Color color) {
        int i = 0;
        Position[] currentPositions = new Position[10];
        for (Position[] pos : positions) {
            for (Position p : pos) {
                if (p != null && p.getPiece() != null && p.getPiece().getColor() == color) {
                    currentPositions[i] = p;
                    i++;
                }
            }
        }
        return currentPositions;
    }

    //this makes an array with all positions currently occupied by the pieces of this player opponents
    public Position[] findOpponentsPieces(Color color) {
        int i = 0;
        Position[] opponentsPositions = new Position[players.length - 1];
        for (Position[] pos : positions) {
            for (Position p : pos) {
                if (p != null && p.getPiece() != null && p.getPiece().getColor() != color) {
                    opponentsPositions[i] = p;
                    i++;
                }
            }
        }
        return opponentsPositions;
    }

    private boolean checkForWinner() {
        if (turn < 0) {
            return false;
        }
        boolean winner = true;
        int goalPieces = 10;
        for (Position goalPosition : players[turn].getGoal()) {
            if (goalPosition.getPiece() == null || goalPosition.getPiece().getColor() != players[turn].getColor()) {
                winner = false;
                goalPieces--;
            }
        }
        return winner;
    }

    public Player getWinner() {
        for (Player p : players) {
            boolean winner = true;
            for (Position goalPosition : p.getGoal()) {
                if (goalPosition.getPiece() == null || goalPosition.getPiece().getColor() != p.getColor()) {
                    winner = false;
                }
            }
            if (winner) {
                return p;
            }
        }
        return null;
    }

    // Simulates a move 
    public void simulateMove(Move move) {
        if (isValidMove(move)) {
            Piece piece = move.getPositions()[0].getPiece();
            move.getPositions()[move.getPositions().length - 1].addPiece(piece);
        }
    }
}
