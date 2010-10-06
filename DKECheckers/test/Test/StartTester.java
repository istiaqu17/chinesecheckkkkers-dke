/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Test;

/**
 *
 * @author Satu
 */


import javax.swing.JFrame;
import FrameWork.Start;

public class StartTester {

    public static void main(String args[]) {

        Start startPopUp = new Start();
        startPopUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startPopUp.setVisible(true);

    }
}
