package FrameWork;

import java.awt.*;
import java.awt.event.*;
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
    private Piece movingPiece;
    private Position[] movingPiecePositions;
    private double[] movingPieceCoordinates;
    private Timer timer;
    private int moveRepeats = 0;

    public Board(int size) {
        this.setSize(size, size);
        createBoard();
        resetPlayerBase(0);
        resetPlayerBase(1);
        resetPlayerBase(2);
        resetPlayerBase(3);
        resetPlayerBase(4);
        resetPlayerBase(5);
    }

    public void resetPlayerBase(int x) {
        for (int i = 0; i < playerBases[x].length; i++) {
            playerBases[x][i].addPiece(new Piece(colors[x]));
        }
    }

    private void createBoard() {
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

        class boardHovered implements MouseMotionListener {

            public void mouseDragged(MouseEvent e) {
            }

            public void mouseMoved(MouseEvent e) {
                mouseHoverLocation = getPosition(e.getX(), e.getY());
                repaint();
            }
        }

        class boardClicked implements MouseListener {

            public void mouseClicked(MouseEvent e) {
                movingPiecePositions = new Position[]{positions[3][4], positions[4][5], positions[4][6], positions[4][5], positions[3][4]};
                movePiece(movingPiecePositions);
                mouseClickLocation = getPosition(e.getX(), e.getY());
                repaint();
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

    public int[] getPosition(int x, int y) {
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

    public boolean isValidMove(Position start, Position end) {
        boolean validMove = true;
        if (start != null || end != null) {
            validMove = false;
        } else if (start.equals(end)) {
            validMove = false;
        } else if (Math.abs(start.getY() - end.getY()) > 1) {
            validMove = false;
        } else if (Math.abs(start.getX() - end.getX()) > 1) {
            validMove = false;
        } else if ((start.getY() - end.getY() == 1) && start.getX() - end.getX() < 0) {
            validMove = false;
        } else if ((end.getY() - start.getY() == 1) && end.getX() - start.getX() < 0) {
            validMove = false;
        }
        return validMove;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[0].length; j++) {
                if (positions[i][j] != null) {
                    int width = (int) (this.getWidth() / (positions.length));
                    g2.setColor(Color.LIGHT_GRAY);
                    g2.fillOval((int) positions[i][j].getX() + (int) (0.05 * width), (int) positions[i][j].getY() + (int) (0.05 * width), (int) (0.9 * width), (int) (0.9 * width));
                    if (positions[i][j].getPiece() != null) {
                        g2.setColor(positions[i][j].getPiece().getColor());
                        g2.fillOval((int) (positions[i][j].getX() + (0.1 * width)), (int) (positions[i][j].getY() + (0.1 * width)), (int) (0.8 * width), (int) (0.8 * width));
                    }
                    if (i == mouseHoverLocation[1] && j == mouseHoverLocation[0]) {
                        g2.setStroke(new BasicStroke(5f));
                        g2.setColor(Color.DARK_GRAY);
                        g2.drawOval((int) (positions[i][j].getX() + (0.05 * width)), (int) (positions[i][j].getY() + (0.05 * width)), (int) (0.9 * width), (int) (0.9 * width));
                    }
                    if (i == mouseClickLocation[1] && j == mouseClickLocation[0]) {
                        g2.setStroke(new BasicStroke(5f));
                        g2.setColor(Color.BLACK);
                        g2.drawOval((int) (positions[i][j].getX() + (0.05 * width)), (int) (positions[i][j].getY() + (0.05 * width)), (int) (0.9 * width), (int) (0.9 * width));
                    }
                    if (movingPiece != null) {
                        g2.setColor(movingPiece.getColor());
                        g2.fillOval((int) (movingPieceCoordinates[0] + (0.1 * width)), (int) (movingPieceCoordinates[1] + (0.1 * width)), (int) (0.8 * width), (int) (0.8 * width));
                    }
                }
            }
        }
    }

    public void movePiece(Position[] position) {
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

    private void advanceMovingPiece() {
        int speed = 50;
        double dX = movingPiecePositions[1].getX() - movingPiecePositions[0].getX();
        double dY = movingPiecePositions[1].getY() - movingPiecePositions[0].getY();
        movingPieceCoordinates[0] += dX / speed;
        movingPieceCoordinates[1] += dY / speed;
        moveRepeats++;
        if (moveRepeats == speed) {
            moveRepeats = 0;
            timer.stop();
            resetMovingPiece();
            if (movingPiecePositions.length > 2) {
                Position[] copy = new Position[movingPiecePositions.length];
                for (int i = 0; i < movingPiecePositions.length; i++) {
                    copy[i] = movingPiecePositions[i];
                }
                movingPiecePositions = new Position[movingPiecePositions.length - 1];
                for (int i = 0; i < copy.length - 1; i++) {
                    movingPiecePositions[i] = copy[i + 1];
                }
                movePiece(movingPiecePositions);

            }
        }
        repaint();
    }

    private void resetMovingPiece() {
        movingPiecePositions[1].addPiece(movingPiece);
        movingPiece = null;
        repaint();
    }
}
