/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import tetris.model.GameModel;
import tetris.navigation.Direction;
import tetris.view.GameBoard.GameBoardObserver;

/**
 *
 * @author tuyenhm
 */
public class GamePanel extends JFrame{
    
    private static final int width = 10; 
    private static final int height = 20; 
    private GameBoard gameBoard = new GameBoard(width, height); 
    private ScoreBoard scoreBoard= new ScoreBoard();
    private NextFigureBoard nextFigureBoard = new NextFigureBoard(); 
    private GameModel model = new GameModel();  
    private JButton btnStart = new JButton("Start");
    private JButton btnPause = new JButton("Pause"); 
    
    
    public GamePanel (){
        getContentPane().setBackground(new Color(3,46,119));
        setupLayout();
        model.addGameBoardListener(gameBoard.new GameBoardObserver());
        model.addNextFigureBoardListener(nextFigureBoard.new NextFigureBoardObserver());
        model.addScoreBoardListener(scoreBoard.new ScoreBoardObserver());
        gameBoard.addKeyListener(new KeyObserver());
        addKeyListener(new KeyObserver());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        btnStart.addActionListener( new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                model.start();
                btnStart.setEnabled(false);
            }
        });
        //important
        setFocusable(true);
        pack();
        
    }
    
    private void setupLayout() {
        GridBagLayout layout  = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        setLayout(layout); 
        
        gc.weightx= 1.0; 
        gc.weighty =1.0;
        gc.gridx = 0; 
        gc.gridy = 0; 
        gc.gridheight = 3;
        gc.gridwidth  = 1; 
        add(gameBoard, gc);
        
        
        gc.gridheight = 1; 
        
        gc.gridx = 1;         
        gc.gridy = 0; 
        add(btnStart, gc);
        
        gc.gridy = 1; 
        gc.insets = new Insets(10, 10, 10, 10);
        
        add(scoreBoard, gc);
        gc.gridy = 2; 
        add(new ScoreBoard(), gc);
        
        Dimension size = new Dimension(500, 700);
        setMinimumSize(size);
        setMaximumSize(size);
    }
    
    class KeyObserver extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode() ; 
            Direction direction = Direction.NONE;
            switch (keyCode) {
                case KeyEvent.VK_DOWN :
                    direction = Direction.DOWN;
                    break; 
                case KeyEvent.VK_LEFT :
                    direction = Direction.LEFT;
                    break;
                case KeyEvent.VK_RIGHT:
                    direction = Direction.RIGHT;
                    break;
                case KeyEvent.VK_UP :
                    direction = Direction.UP;
                    break;
                default:
                    return ; 
            }
            if(direction != Direction.NONE) {
                if(direction == Direction.UP) {
                    model.getActiveFigure().rotateByClockWise();
                } else {
                    System.out.println("Moved by user"); 
                    model.getActiveFigure().move(direction);
                }
            }
        } 
    }
}
