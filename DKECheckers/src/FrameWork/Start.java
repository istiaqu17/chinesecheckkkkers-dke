/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FrameWork;

/**
 *
 * @author Satu
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.GroupLayout;

public class Start extends JFrame{

    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private JLabel header;
    private JButton button;
    private JPanel panel;
    private JPanel pl1;
    private JPanel pl2;
    private JPanel pl3;
    private JPanel pl4;
    private JPanel pl5;
    private JPanel pl6;

    public Start() {

        setSize(WIDTH, HEIGHT);
        setVisible(true);

        header = new JLabel("Players");

        pl1 = createPlayerLine("Player 1:");
        pl2 = createPlayerLine("Player 2:");
        pl3 = createPlayerLine("Player 3:");
        pl4 = createPlayerLine("Player 4:");
        pl5 = createPlayerLine("Player 5:");
        pl6 = createPlayerLine("Player 6:");

        createOKButton();

        createPanel();

    }

    private JPanel createPlayerLine(String text) {
        JPanel p = new JPanel();
        JLabel t = new JLabel(text);
        String[] choices = { "Human Player", "Computer Player", "Not playing"};
        JComboBox player = new JComboBox(choices);
        player.setSelectedIndex(0);
        class PlayerListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String type = (String)cb.getSelectedItem();
                if (type.equals("Human Player")) {
                    System.out.println("Human player");
                }
                //other options
            }
        }
        ActionListener listener = new PlayerListener();
        player.addActionListener(listener);
        p.add(t);
        p.add(player);
        return p;
    }


    private void createOKButton() {
        button = new JButton("Start");
        class StartListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        }
        ActionListener listener = new StartListener();
        button.addActionListener(listener);
    }

    private void createPanel() {
        panel = new JPanel();
//        panel.add(header);
//        panel.add(pl1);
//        panel.add(pl2);
//        panel.add(pl3);
//        panel.add(pl4);
//        panel.add(pl5);
//        panel.add(pl6);
//        panel.add(button);

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addComponent(header)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(pl1)
                    .addComponent(pl2)
                    .addComponent(pl3)
                    .addComponent(pl4)
                    .addComponent(pl5)
                    .addComponent(pl6))
                .addComponent(button)
        );
        layout.setVerticalGroup(
           layout.createSequentialGroup()
              .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                   .addComponent(header)
                   .addComponent(pl1)
                   .addComponent(button))
              .addComponent(pl2)
              .addComponent(pl3)
              .addComponent(pl4)
              .addComponent(pl5)
              .addComponent(pl6)
        );

        add(panel);
    }

}
