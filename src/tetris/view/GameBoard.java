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
import java.io.File;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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
    private static final String NEW_GAME_TOOTIP = "New game"; 
    private static final String PLAY_PAUSE_TOOLTIP = "Play/Pause";
    
    private static final String GAMEOVER_SOUND = "src/Sound/gameover.wav";
    private static final String GAMESTART_SOUND = "src/Sound/gamestart.wav";
    private static final String FIGURE_ROTATED_SOUND = "src/Sound/figurerotated.wav";
    private static final String FIGURE_ROTATE_FAIL_SOUND = "src/Sound/figurerotatefail.wav";
    private static final String FIGURE_MOVELR_SOUND = "src/Sound/figuremoved.wav";
    private static final String FIGURE_MOVELR_FAIL_SOUND = "src/Sound/figuremovefail.wav";
    private static final String FIGURE_STOPPED_SOUND = "src/Sound/figurestopped.wav";
    private static final String FULL_ROWS_REMOVED_SOUND = "src/Sound/fullrowremoved.wav";
    
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
            e.printStackTrace();
        }
        
        Dimension frameSize = new Dimension(580, 690); 
        setPreferredSize(frameSize); 
        setMaximumSize(frameSize);
        setMinimumSize(frameSize); 
        setResizable(false);
        
        setFocusable(true);
        pack();
        
    }
    
    private void setupLayout() {
        GridBagLayout layout  = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        setLayout(layout); 
        
        gc.gridx = 0; 
        gc.gridy = 0; 
        gc.gridheight = 3; 
        add(gridPanel, gc);
        gc.insets = new Insets(30, 30, 30, 0);

        gc.gridheight = 1;
        gc.gridwidth = 2; 
        gc.gridx = 1;         
        gc.gridy = 0; 
        gc.insets = new Insets(0, 30, 60, 30);
        add(nextFigurePanel, gc);
        
        gc.gridx = 1;         
        gc.gridy = 1; 
        gc.insets = new Insets(0, 30, 0, 30);
        add(scorePanel, gc);
        
        gc.insets = new Insets(-20, 30, 0, 0);
        gc.gridwidth = 1; 
        gc.gridy = 2; 
        gc.gridx = 1;
        add(btnStart, gc);
        
        gc.gridx = 2;
        add(btnNewGame, gc);
        
        btnNewGame.setToolTipText(NEW_GAME_TOOTIP);
        btnStart.setToolTipText(PLAY_PAUSE_TOOLTIP);
       
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
        playSound(GAMESTART_SOUND); 
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
                    boolean isRotated = activeFigure.rotate();
                    if(!isRotated) {
                        playSound(FIGURE_ROTATE_FAIL_SOUND);
                    }
                } else {
                    boolean isMoved = activeFigure.move(direction);
                    if(!isMoved) {
                        playSound(FIGURE_MOVELR_FAIL_SOUND);
                    }
                }
            }
        } 
    }
    
    private Clip createClip(String path) throws Exception{
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
    }

    private void playSound(String soundPath) {
        try{
            Clip clip = createClip(soundPath);
            clip.start();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    class GameObserver implements GameListener {

        @Override
        public void figureMoved(GameFieldEvent event) {
            gridPanel.updateGridStatus(event);
            playSound(FIGURE_MOVELR_SOUND);
        }

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
            playSound(GAMEOVER_SOUND);
            JOptionPane.showMessageDialog(null, "Game Over!!!");
        }

        @Override
        public void figureRotated(GameFieldEvent event) {
            gridPanel.updateGridStatus(event);
            playSound(FIGURE_ROTATED_SOUND);
        }

        @Override
        public void figureStopped(GameFieldEvent event) {
            gridPanel.updateGridStatus(event);
            playSound(FIGURE_STOPPED_SOUND);
        }

        @Override
        public void fullRowRemoved(GameFieldEvent event) {
            playSound(FULL_ROWS_REMOVED_SOUND);
        }
    }
    
}
