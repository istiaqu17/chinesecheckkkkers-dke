package FrameWork;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

/**
 *
 * @author Rik Claessens
 */
public class Board extends JPanel {

    Position[][] positions = new Position[17][17];
    private int[] mouseClickLocation = new int[]{-1, -1};
    private int[] mouseHoverLocation = new int[]{-1, -1};
    private int[][] rowLengths = new int[][]{{4, 1}, {4, 2}, {4, 3}, {4, 4}, {0, 13}, {1, 12}, {2, 11}, {3, 10}, {4, 9}, {4, 10}, {4, 11}, {4, 12}, {4, 13}, {9, 4}, {10, 3}, {11, 2}, {12, 1}};

    public Board(int size) {
        this.setSize(size, size);
        createBoard();
    }

    private void createBoard() {
        for (int i = 0; i < rowLengths.length; i++) {
            for (int j = 0; j < rowLengths[i][0]; j++) {
                positions[i][j] = null;
            }
            for (int j = rowLengths[i][0]; j < rowLengths[i][0] + rowLengths[i][1]; j++) {
                int x = (positions.length - i - 13) * (this.getWidth() / (2 * positions.length)) + j * (this.getWidth() / (positions.length));
                int y =  i * (this.getWidth() / (positions.length));
                positions[i][j] = new Position(x, y, null);
            }
            System.out.println(positions[0][4].getX() + " " + positions[0][4].getY());
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
                    g2.fillOval((int) positions[i][j].getX() + (int) (0.1 * width), (int) positions[i][j].getY()  + (int) (0.1 * width), (int) (0.9 * width), (int) (0.9 * width));
                    if (positions[i][j].getPiece() != null) {
                        g2.setColor(positions[i][j].getPiece().getColor());
                        g2.fillOval((int) (positions[i][j].getX()  + (0.2 * width)), (int) (positions[i][j].getY()  + (0.2 * width)), (int) (0.8 * width), (int) (0.8 * width));
                    }
                    if (i == mouseHoverLocation[1] && j == mouseHoverLocation[0]) {
                        g2.setStroke(new BasicStroke(3f));
                        g2.setColor(Color.DARK_GRAY);
                        g2.drawOval((int) (positions[i][j].getX()  + (0.1 * width)), (int) (positions[i][j].getY()  + (0.1 * width)), (int) (0.9 * width), (int) (0.9 * width));
                    }
                    if (i == mouseClickLocation[1] && j == mouseClickLocation[0]) {
                        g2.setStroke(new BasicStroke(3f));
                        g2.setColor(Color.BLACK);
                        g2.drawOval((int) (positions[i][j].getX()  + (0.1 * width)), (int) (positions[i][j].getY()  + (0.1 * width)), (int) (0.9 * width), (int) (0.9 * width));
                    }
                }
            }
        }
    }
}
