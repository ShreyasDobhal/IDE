/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepad;

/**
 *
 * @author Shreyas
 */

public class Notepad {

    public static int windowCount=0;
    /**
     * @param args the command line arguments
     */
    public static void showCount() {
        System.out.println(windowCount);
    }
    public static void main(String[] args) {
        // TODO code application logic here
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Editor().setVisible(true);
            }
        });
    }
    
}
