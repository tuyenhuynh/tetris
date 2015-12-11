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
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import tetris.model.GameModel;
import tetris.model.event.GameFieldEvent;
import tetris.model.event.GameListener;
import tetris.model.shape.Figure;
import tetris.navigation.Direction;

/**
 *
 * @author tuyenhm
 */
public class GameBoard extends JFrame{
    
    private static final int width = 10; 
    private static final int height = 20; 
    private GridPanel gridPanel = new GridPanel(width, height); 
    private ScorePanel scorePanel= new ScorePanel();
    private NextFigurePanel nextFigurePanel = new NextFigurePanel(); 
    private GameModel model = new GameModel();  
    private JButton btnStart = new JButton("Start");
    private JButton btnPause = new JButton("Pause"); 
    private JButton btnNewGame = new JButton("New Game"); 
    
    public GameBoard (){
        
        getContentPane().setBackground(new Color(3,46,119));
        setupLayout();
        model.addGameListener(new GameObserver());
        addKeyListener(new KeyObserver());
        btnStart.setEnabled(false);
        btnPause.setEnabled(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        btnStart.addActionListener( new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();     
            }
        });
        
        btnPause.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopGame(); 
            }
        });
        
        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame(); 
            }
        });
        
        //important
       // setFocusable(true);
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
        gc.gridheight = 5;
        gc.gridwidth  = 1; 
        add(gridPanel, gc);
        
        
        gc.gridheight = 1; 
        
        gc.gridx = 1;         
        gc.gridy = 0; 
        add(btnNewGame, gc);
        
        gc.gridx = 1;         
        gc.gridy = 1; 
        add(btnStart, gc);
        
        gc.gridx = 1;         
        gc.gridy = 2; 
        add(btnPause, gc);
        
        
        gc.gridy = 3; 
        gc.insets = new Insets(10, 10, 10, 10);
        
        add(scorePanel, gc);
        
        gc.gridy = 4; 
        add(nextFigurePanel, gc);
        
        Dimension size = new Dimension(500, 700);
        setMinimumSize(size);
        setMaximumSize(size);
    }
    
    private void startGame() {
        model.startGame();
        btnStart.setEnabled(false);
        btnPause.setEnabled(true);
        getFocus();
    }
    
    private void stopGame() {
        model.stopGame();
        btnPause.setEnabled(false);
        btnStart.setEnabled(true);
        getFocus();
    }
    
    private void newGame() {
        model.createNewGame();
        startGame(); 
        getFocus();
    }
    
    private void getFocus() {
        requestFocus();
        requestFocusInWindow();
    }
    
    class KeyObserver extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Key pressed"); 
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
                Figure activeFigure = model.getActiveFigure() ; 
                if(direction == Direction.UP) {
                    activeFigure.rotateByClockWise();
                } else {
                    
                    activeFigure.move(direction);
                }
            }
        } 
    }
    
    class GameObserver implements GameListener {

        @Override
        public void gridBoardChanged(GameFieldEvent event) {
            gridPanel.updateGridStatus(event);
        }

        @Override
        public void nextFigureChanged(Figure nextFigure) {
            nextFigurePanel.setFigure(nextFigure);
        }

        @Override
        public void scoreChanged(int newScore) {
            scorePanel.setScore(newScore);
        }

        @Override
        public void gameOver() {
            btnStart.setEnabled(false);
            btnPause.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Game Over!!!");
        }
        
    }
    
}
