/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FrameWork;

/**
 *
 * @author Satu
 */

/*
 * THIS CLASS SHOULD BE REMOVED AND REPLACED BY Satu
 */
import Players.Player;
import Players.RandomAIPlayer;
import Players.HumanPlayer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
//import javax.swing.GroupLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.util.ArrayList;

public class Start extends JFrame {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private JLabel header;
    private JButton startButton;
    private JButton okButton;
    private JPanel panel;
    private JPanel number;
    private JPanel basePanel;
    private JPanel pl1;
    private JPanel pl2;
    private JPanel pl3;
    private JPanel pl4;
    private JPanel pl5;
    private JPanel pl6;
    private ArrayList<JPanel> playerPanels;
    private int nPlayers;
    private Player[] players;

    public Start() {

        setSize(WIDTH, HEIGHT);
        setVisible(true);

        basePanel = new JPanel();
        add(basePanel);
        BorderLayout baseLayout = new BorderLayout();
        basePanel.setLayout(baseLayout);
        header = new JLabel("Players");
        basePanel.add(header, BorderLayout.PAGE_START);

        number = createNumber();
        basePanel.add(number, BorderLayout.CENTER);

        okButton = createOkButton();
        basePanel.add(okButton, BorderLayout.PAGE_END);

        playerPanels = new ArrayList<JPanel>();

//        pl1 = createPlayerLine("Player 1:");
//        pl2 = createPlayerLine("Player 2:");
//        pl3 = createPlayerLine("Player 3:");
//        pl4 = createPlayerLine("Player 4:");
//        pl5 = createPlayerLine("Player 5:");
//        pl6 = createPlayerLine("Player 6:");



    }

    private JPanel createNumber() {
        JPanel p = new JPanel();
        JLabel l = new JLabel("Number of Players:");

        //create the radio buttons for the number of players
        JRadioButton two = new JRadioButton("Two");
        two.setActionCommand("two");
        JRadioButton three = new JRadioButton("Three");
        three.setActionCommand("three");
        JRadioButton four = new JRadioButton("Four");
        four.setActionCommand("four");
        JRadioButton six = new JRadioButton("Six");
        six.setActionCommand("six");

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(two);
        group.add(three);
        group.add(four);
        group.add(six);

        class RadioListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("two")) {
                    nPlayers = 2;
                    System.out.println("2 players");
                }
                if (e.getActionCommand().equals("three")) {
                    nPlayers = 3;
                    System.out.println("3 players");
                }
                if (e.getActionCommand().equals("four")) {
                    nPlayers = 4;
                    System.out.println("4 players");
                }
                if (e.getActionCommand().equals("six")) {
                    nPlayers = 6;
                    System.out.println("6 players");
                }
            }
        }

        ActionListener listener = new RadioListener();

        //Register a listener for the radio buttons.
        two.addActionListener(listener);
        three.addActionListener(listener);
        four.addActionListener(listener);
        six.addActionListener(listener);

        //Put the label and the radio buttons in two columns in a panel.
        JPanel aux1 = new JPanel();
        aux1.add(two);
        aux1.add(four);

        JPanel aux2 = new JPanel();
        aux2.add(three);
        aux2.add(six);

        GridLayout layout = new GridLayout(3, 1);
        p.setLayout(layout);
        p.add(l);
        p.add(aux1);
        p.add(aux2);

        return p;
    }

    //creates ok-button for the beginning. it creates as many comboboxes as there are players
    private JButton createOkButton() {
        JButton ok = new JButton("OK");
        class StartListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                for (int i = 1; i <= nPlayers; i++) {
                    playerPanels.add(createPlayerLine("Player " + i + ":"));

                }

                players = new Player[nPlayers];
                for (int i = 0; i < nPlayers; i++) {
                    //create human players originally, will be
                    //overwritten later if a choice is made
                    players[i] = new HumanPlayer();
                }

                createStartButton();
                createPanel();

                number.setVisible(false);
                okButton.setVisible(false);
                repaint();
            }
        }
        ActionListener listener = new StartListener();
        ok.addActionListener(listener);
        return ok;
    }

    //this creates a label and a combobox to determine the type of player
    private JPanel createPlayerLine(String text) {
        final String s = text;
        JPanel p = new JPanel();
        JLabel t = new JLabel(text);
        String[] choices = {"Human Player", "Random AI"};
        JComboBox player = new JComboBox(choices);
        //player.setSelectedIndex(0);
        class PlayerListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String type = (String) cb.getSelectedItem();
                int n = Integer.parseInt(s.substring(7, 8)) - 1;
                System.out.println(s);
                System.out.println(n);
                if (type.equals("Human Player")) {
                    //First: prevent creation of unnecessary objects:
                    if (players[n] == null || players[n] instanceof RandomAIPlayer) {
                        HumanPlayer player = new HumanPlayer();
                        players[n] = player;
                        System.out.println("Human player was created");
                    }
                }
                if (type.equals("Random AI")) {
                    //if will prevent the creation of unnecessary objects
                    if (players[n] == null || players[n] instanceof HumanPlayer) {
                        RandomAIPlayer player = new RandomAIPlayer();
                        players[n] = player;
                        System.out.println("random aiPlayer");
                    }
                }
            }
        }
        ActionListener listener = new PlayerListener();
        player.addActionListener(listener);
        p.add(t);
        p.add(player);
        return p;
    }

    private void createStartButton() {
        startButton = new JButton("Start");
        class StartListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                java.awt.EventQueue.invokeLater(new Runnable() {

                    public void run() {
                        //final check to make sure everything is ok:
                        for (int i = 0; i < nPlayers; i++) {
                            if (players[i] == null) {
                                players[i] = new HumanPlayer();
                            }
                        }
                        //now we can start the game:
                        new BoardFrame(players).setVisible(true);
//                        JFrame bframe = new JFrame();
//                        bframe.add(board);
//                        bframe.setSize(1000,1000);
//                        bframe.setVisible(true);
//                        bframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    }
                });
            }
        }
        ActionListener listener = new StartListener();
        startButton.addActionListener(listener);
    }

    private void createPanel() {
        panel = new JPanel(new GridLayout(nPlayers, 1));

        for (int i = 0; i < nPlayers; i++) {
            panel.add(playerPanels.get(i));
        }

        basePanel.add(panel, BorderLayout.CENTER);
        basePanel.add(startButton, BorderLayout.PAGE_END);
    }
}
