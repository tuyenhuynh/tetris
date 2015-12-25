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
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Icon;
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
    private JButton btnStart = new JButton("");
    private JButton btnNewGame = new JButton(""); 
    
    private static final String ICON_PLAY = "/tetris/view/images/play.png";
    private static final String ICON_PAUSE = "/tetris/view/images/pause.png";
    private static final String ICON_RESTART = "/tetris/view/images/restart.png";
    private Icon iconPlay; 
    private Icon iconPause; 
    private Icon iconRestart; 
    private boolean isRunning = false; 
    
    public GameBoard (){
        getContentPane().setBackground(new Color(22, 37, 103));
        setupLayout();
        model.addGameListener(new GameObserver());
        addKeyListener(new KeyObserver());
        btnStart.setEnabled(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        btnStart.addActionListener( new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isRunning){
                    stopGame();     
                }else {
                    startGame();     
                }
            }
        });
        
//        btnPause.addActionListener( new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                stopGame(); 
//            }
//        });
//        
        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame(); 
            }
        });
        
        try {
            Image imagePlay = ImageIO.read(getClass().getResourceAsStream(ICON_PLAY));
            iconPlay = new ImageIcon(imagePlay);
            Image imagePause = ImageIO.read(getClass().getResourceAsStream(ICON_PAUSE));
            iconPause = new ImageIcon(imagePause);
            Image imageRestart = ImageIO.read(getClass().getResourceAsStream(ICON_RESTART));
            iconRestart = new ImageIcon(imageRestart);
            btnStart.setIcon(iconPlay);
            btnStart.setPreferredSize(new Dimension(32, 32));
            btnNewGame.setIcon(iconRestart);
            btnNewGame.setPreferredSize(new Dimension(32, 32));
            btnStart.setEnabled(false);
        }catch (Exception e) {
            
        }
        
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
        
        
        gc.weighty = 0.5;
        
        gc.gridheight = 1; 
        gc.gridwidth = 2; 
        gc.gridx = 1;         
        gc.gridy = 0; 
        
        //gc.anchor = GridBagConstraints.NORTH;
        add(nextFigurePanel, gc);
        
        gc.gridx = 1;         
        gc.gridy = 1; 
        add(scorePanel, gc);
        
        gc.gridy = 2;
        gc.gridwidth = 1; 
        gc.gridx = 1;
        add(btnNewGame, gc);
        
        gc.gridx = 2;
        add(btnStart, gc);
        
        Dimension size = new Dimension(500, 700);
        setMinimumSize(size);
        setMaximumSize(size);
    }
    
    private void startGame() {
        model.startGame();
        isRunning = true;
        btnStart.setIcon(iconPause);
        getFocus();
    }
    
    private void stopGame() {
        model.stopGame();
        btnStart.setIcon(iconPlay);
        isRunning  = false; 
        getFocus();
    }
    
    private void newGame() {
        model.createNewGame();
        startGame(); 
        btnStart.setEnabled(true);
        getFocus();
    }
    
    private void getFocus() {
        requestFocus();
        requestFocusInWindow();
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
            JOptionPane.showMessageDialog(null, "Game Over!!!");
        }
        
    }
    
}
