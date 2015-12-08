package Test;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import tetris.model.GameModel;
import tetris.model.figure.Figure;
import tetris.model.figure.IFigure;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tuyenhm
 */
public class GameTest extends JFrame {
    
    private GameBoardTest gameBoard; 
    public GameTest() {
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Box box = Box.createVerticalBox();
        gameBoard = new GameBoardTest();
        box.add(gameBoard);
        setContentPane(box);
        pack(); 
        setLocationRelativeTo(null);
    }
  
  
}
