/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import tetris.view.GameBoard;

/**
 *
 * @author tuyenhm
 */
public class Tetris {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame app = new GameBoard();
                app.setVisible(true);
                app.setLocationRelativeTo(null);
                app.setTitle("TETRIS");
            }
        });
    }
    
}
