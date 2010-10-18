/*
 * GUI.java
 *
 * Created on 10-Oct-2010, 15:32:52
 */
package GUI;

import FrameWork.Board;
import Players.BruteForceAI;
import Players.HumanPlayer;
import Players.Player;
import Players.RandomAIPlayer;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Rik Claessens
 */
public class GUI extends javax.swing.JFrame {

    private String[] typeOfPlayers = new String[]{"Human Player", "Random AI", "Brute Force"};
    private JPanel board, newGamePanel, playerOptionPanel;
    private int selectedNumberOfPlayers = -1;
    private JComboBox[] playerOptions;
    private JTextField[] playerNames;
    private int framewidth = 800;

    /** Creates new form GUI */
    public GUI() {
        initComponents();
        newGamePanel = createNewGamePanel();
        add(newGamePanel);
        this.setSize((int) (0.9 * framewidth), (int) (1.1 * framewidth));
    }

    public JPanel createNewGamePanel() {
        final JPanel panel = new JPanel();
        final JRadioButton[] numberOfPlayerButtons = new JRadioButton[]{new JRadioButton("2"), new JRadioButton("3"), new JRadioButton("4"), new JRadioButton("6")};
        int[] numberOfPlayers = new int[]{2, 3, 4, 6};
        panel.add(new JLabel("Number of players: "));
        panel.setLayout(new BoxLayout(panel, WIDTH));
        class numberOfPlayerListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                if (playerOptionPanel != null) {
                    remove(playerOptionPanel);
                    validate();
                    repaint();
                }
                for (int i = 0; i < numberOfPlayerButtons.length; i++) {
                    if (e.getSource() != numberOfPlayerButtons[i]) {
                        numberOfPlayerButtons[i].setSelected(false);
                    } else {
                        numberOfPlayerButtons[i].setSelected(true);
                    }
                }
                createPlayerOptionPanel(e.getActionCommand());
                validate();
                repaint();
            }
        }

        for (int i = 0; i < numberOfPlayerButtons.length; i++) {
            numberOfPlayerButtons[i].setActionCommand("" + numberOfPlayers[i]);
            numberOfPlayerButtons[i].addActionListener(new numberOfPlayerListener());
            panel.add(numberOfPlayerButtons[i]);
        }
        panel.setBounds(10, 10, 300, 150);
        return panel;
    }

    public void createPlayerOptionPanel(String numberOfPlayersString) {
        int numberOfPlayers = Integer.parseInt(numberOfPlayersString);
        selectedNumberOfPlayers = numberOfPlayers;
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(numberOfPlayers + 1, 3, 5, 5));
        playerOptions = new JComboBox[numberOfPlayers];
        playerNames = new JTextField[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            panel.add(new JLabel("Player " + (i + 1) + ": "));
            playerOptions[i] = new JComboBox(typeOfPlayers);
            panel.add(playerOptions[i]);
            playerNames[i] = new JTextField("Player " + i, 15);
            panel.add(playerNames[i]);
        }
        panel.setBounds(10, newGamePanel.getHeight() + newGamePanel.getY(), 600, numberOfPlayers * 40);
        playerOptionPanel = panel;
        panel.add(new JLabel());
        panel.add(createNewGameButton());
        add(playerOptionPanel);
    }

    public JButton createNewGameButton() {
        final JButton button = new JButton("New Game");
        class newGameButtonListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                remove(newGamePanel);
                remove(playerOptionPanel);
                Player[] players = new Player[selectedNumberOfPlayers];
                String name;
                for (int i = 0; i < selectedNumberOfPlayers; i++) {
                    switch (playerOptions[i].getSelectedIndex()) {
                        case 0:
                            players[i] = new HumanPlayer();
                            name = playerNames[i].getText();
                            if (name.equalsIgnoreCase("")){
                                name = "Player " + (i + 1);
                            }
                            players[i].setName(name);
                            break;
                        case 1:
                            players[i] = new RandomAIPlayer();
                            name = playerNames[i].getText();
                            if (name.equalsIgnoreCase("")){
                                name = "Player " + (i + 1);
                            }
                            players[i].setName(name);
                            break;
                        case 2:
                            players[i] = new BruteForceAI();
                            name = playerNames[i].getText();
                            if (name.equalsIgnoreCase("")){
                                name = "Player " + (i + 1);
                            }
                            players[i].setName(name);
                            break;
                    }
                }
                board = new Board(framewidth, players);
                add(board);
                remove(button);
            }
        }
        button.addActionListener(new newGameButtonListener());
        return button;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("Game");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem1.setText("New Game");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Quit Game");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 674, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 444, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        if (board != null) {
            remove(board);
            newGamePanel = createNewGamePanel();
            add(newGamePanel);
            validate();
            repaint();
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    // End of variables declaration//GEN-END:variables
}
