/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Test;

/**
 *
 * @author Satu
 */

import FrameWork.Start;
import javax.swing.JFrame;

public class StartTester {

    public static void main(String[] args) {
        JFrame startPopUp = new Start();
        startPopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startPopUp.setVisible(true);
    }
}
