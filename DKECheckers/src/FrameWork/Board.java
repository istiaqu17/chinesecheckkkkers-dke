package FrameWork;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Rik Claessens
 */
public class Board extends JPanel {

    private Position[][] positions = new Position[17][17];
    private int[] mouseClickLocation = new int[]{-1, -1};
    private int[] mouseHoverLocation = new int[]{-1, -1};
    private int[][] rowLengths = new int[][]{{4, 1}, {4, 2}, {4, 3}, {4, 4}, {0, 13}, {1, 12}, {2, 11}, {3, 10}, {4, 9}, {4, 10}, {4, 11}, {4, 12}, {4, 13}, {9, 4}, {10, 3}, {11, 2}, {12, 1}};
    private Position[][] playerBases;
    private Color[] colors = new Color[]{Color.YELLOW, Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA};
    private Piece selectedPiece;
    private Position selectedPosition;
    private Piece movingPiece;
    private Position[] movingPiecePositions;
    private double[] movingPieceCoordinates;
    private Timer timer;
    private int moveRepeats = 0;
    private boolean allowMouseInput;
    private ArrayList<Position> validMoves = new ArrayList<Position>();
    /*
     * Constructor
     */

    public Board(int size) {
        this.setSize(size, size);
        createBoard();
        resetPlayerBase(0);
        resetPlayerBase(1);
        resetPlayerBase(2);
        resetPlayerBase(3);
        resetPlayerBase(4);
        resetPlayerBase(5);
        allowMouseInput = true;
    }

    /*
     * Resets the starting base of a player by placing all 10 pieces in the starting triangle
     */
    public void resetPlayerBase(int x) {
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
                int x = (positions.length - i - 13) * (this.getWidth() / (2 * positions.length)) + j * (this.getWidth() / (positions.length));
                int y = i * (this.getWidth() / (positions.length));
                positions[i][j] = new Position(x, y, null);
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
//                movingPiecePositions = new Position[]{positions[3][4], positions[4][5], positions[4][6], positions[4][5], positions[3][4]};
//                movePiece(movingPiecePositions);
                if (allowMouseInput) {
                    mouseClickLocation = getPosition(e.getX(), e.getY());
                    if (mouseClickLocation[0] != -1 && positions[mouseClickLocation[1]][mouseClickLocation[0]] != null) {
                        if (positions[mouseClickLocation[1]][mouseClickLocation[0]].getPiece() != null) {
                            selectedPiece = positions[mouseClickLocation[1]][mouseClickLocation[0]].getPiece();
                            movingPiecePositions = new Position[]{positions[mouseClickLocation[1]][mouseClickLocation[0]], null};
                            validMoves.clear();
                            determineValidMoves(positions[mouseClickLocation[1]][mouseClickLocation[0]]);
                        } else if (selectedPiece != null) {
                            selectedPosition = positions[mouseClickLocation[1]][mouseClickLocation[0]];
                            movingPiecePositions[1] = positions[mouseClickLocation[1]][mouseClickLocation[0]];
                            if (isValidMove(movingPiecePositions[1])) {
                                movePiece(movingPiecePositions);
                            }
                            resetMouseInput();
                        } else {
                            selectedPiece = null;
                            selectedPosition = null;
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

    public void determineValidMoves(Position position) {
        int[] positionCoordinates = getPosition(position.getX(), position.getY());
        int i = positionCoordinates[1] + 1;
        int j = positionCoordinates[0] + 1;
        System.out.println("Determine: " + position.getX() + " " + position.getY() + " " + i + " " + j);
        int[][] positionsToCheck = new int[][]{{-1, -1}, {-1, 0}, {0, 1}, {1, 1}, {1, 0}, {0, -1}};
        for (int k = 0; k < positionsToCheck.length; k++) {
            if (i + positionsToCheck[k][0] >= 0 && i + positionsToCheck[k][0] <= 16 && j + positionsToCheck[k][1] >= 0 && j + positionsToCheck[k][1] <= 16) {
                // Checks for valid moves of 1 position
                if (positions[i + positionsToCheck[k][0]][j + positionsToCheck[k][1]] != null && positions[i + positionsToCheck[k][0]][j + positionsToCheck[k][1]].getPiece() == null) {
                    validMoves.add(positions[i + positionsToCheck[k][0]][j + positionsToCheck[k][1]]);
                }
            }
            if (i + 2 * positionsToCheck[k][0] >= 0 && i + 2 * positionsToCheck[k][0] <= 16 && j + 2 * positionsToCheck[k][1] >= 0 && j + 2 * positionsToCheck[k][1] <= 16) {
                // Checks for hops
                if (positions[i + 2 * positionsToCheck[k][0]][j + 2 * positionsToCheck[k][1]] != null && positions[i + positionsToCheck[k][0]][j + positionsToCheck[k][1]].getPiece() != null
                        && positions[i + 2 * positionsToCheck[k][0]][j + 2 * positionsToCheck[k][1]].getPiece() == null) {
                    validMoves.add(positions[i + 2 * positionsToCheck[k][0]][j + 2 * positionsToCheck[k][1]]);
                }

            }
        }
    }

    private boolean isValidMove(Position position) {
        for (Position validMove : validMoves) {
            if (validMove == position) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Position> getValidMoves(){
        return validMoves;
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
                    for (Position validMove : validMoves) {
                        if (positions[i][j] == validMove) {
                            g2.setStroke(new BasicStroke(10f));
                            g2.setColor(new Color(0, 150, 175));
                            g2.drawOval((int) (positions[i][j].getX() + (0.05 * width)), (int) (positions[i][j].getY() + (0.05 * width)), (int) (0.9 * width), (int) (0.9 * width));
                        }
                    }
                    // Displays where the mouse is pointing to if the cursor is on the board
                    if (i == mouseHoverLocation[1] && j == mouseHoverLocation[0]) {
                        g2.setStroke(new BasicStroke(6f));
                        g2.setColor(Color.DARK_GRAY);
                        g2.drawOval((int) (positions[i][j].getX() + (0.05 * width)), (int) (positions[i][j].getY() + (0.05 * width)), (int) (0.9 * width), (int) (0.9 * width));
                    }
                    // Displays which position the user has clicked
                    if (i == mouseClickLocation[1] && j == mouseClickLocation[0]) {
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
    public void movePiece(Position[] position) {
        validMoves.clear();
        Piece piece = position[0].getPiece();
        position[0].removePiece();
        movingPiece = piece;
        movingPieceCoordinates = new double[]{position[0].getX(), position[0].getY()};
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
                movePiece(movingPiecePositions);
            }
            allowMouseInput = true;
        }
        repaint();
        notify();
    }

    /*
     * Resets the moving piece so that the moving piece is placed at it's destination and is no longer shown on screen.
     */
    private void resetMovingPiece() {
        movingPiecePositions[1].addPiece(movingPiece);
        movingPiece = null;
        repaint();
    }

    private void resetMouseInput() {
        validMoves.clear();
        mouseHoverLocation = new int[]{-1, -1};
        mouseClickLocation = new int[]{-1, -1};
    }
}
