/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.view;

import com.sun.istack.internal.logging.Logger;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import tetris.model.GameModel;
import tetris.model.event.GameFieldEvent;
import tetris.model.event.GameListener;
import tetris.model.event.GridSizeEvent;
import tetris.model.event.GridSizeListener;
import tetris.model.shape.Figure;
import tetris.navigation.Direction;

/**
 *
 * @author tuyenhm
 */
public class GameBoard extends JFrame{
    
    private static final Logger logger = Logger.getLogger(GameBoard.class);
    
    private int gridColumnCount; 
    private int gridRowCount; 
    
    private GridPanel gridPanel;
    private final ScorePanel scorePanel= new ScorePanel();
    private final NextFigurePanel nextFigurePanel = new NextFigurePanel(); 
    private final GameModel model = new GameModel();  
    private final JButton btnStart = new JButton("");
    private final JButton btnNewGame = new JButton(""); 
    private JMenuBar menu; 
    private final GridSizeDialog gridSizeDialog; 
    
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
    
    private final String[] fileItems = new String[] {"Size","Exit"}; 
    
    private Icon iconPlay; 
    private Icon iconPause; 
    private Icon iconRestart; 
    private boolean isRunning = false; 
    
    /**
     *
     */
    public GameBoard (){
        getContentPane().setBackground(new Color(22, 37, 103));
        configureButtons();
        createMenu();
        setJMenuBar(menu);
        gridSizeDialog  = new GridSizeDialog(this); 
        gridSizeDialog.addGridSizeListener(new GridSizeObserver());
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
        repaintBoard();
        setFocusable(true);
        pack();
    }
    
    private void repaintBoard() {
        getContentPane().removeAll();
        GridBagLayout layout  = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        gridPanel  = new GridPanel(model.getGridSize()); 
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
       
        Dimension frameSize = getFrameSize(); 
        setMinimumSize(frameSize); 
        setMaximumSize(frameSize); 
        setResizable(false);
        validate(); 
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
    
    /**
     *
     * @return
     */
    public Dimension getFrameSize() {
        int width = gridPanel.getWidth() + nextFigurePanel.getWidth() + 90;
        logger.info("width = " + width);
        int height = gridPanel.getHeight() + 80;
        return new Dimension(width, height);
    }
    
    private void configureButtons(){
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
    }
    
    private void createMenu() {
        menu = new JMenuBar(); 
        JMenu fileMenu = new JMenu("File");
        for(int i = 0 ; i < fileItems.length; ++i){
            JMenuItem item = new JMenuItem(fileItems[i]);
            item.setActionCommand(fileItems[i].toLowerCase());
            item.addActionListener( new FileMenuItemListener());
            fileMenu.add(item);
        }        
        fileMenu.insertSeparator(1);
        menu.add(fileMenu);
    }
    
    private class FileMenuItemListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if(command.equals("size")){
                gridSizeDialog.setVisible(true);
            }else if(command.equals("exit")){
                System.exit(0);
            }
        }
        
    }
    
    private class  GridSizeObserver implements GridSizeListener {

        @Override
        public void gridSizeChanged(GridSizeEvent evt) {
            gridColumnCount = evt.getColumnCount(); 
            gridRowCount = evt.getRowCount();
            Dimension dimension = new  Dimension(gridColumnCount, gridRowCount);
            model.setGridSize(dimension);
            gridSizeDialog.setVisible(false);
            repaintBoard();
            newGame(); 
            stopGame();
        }
        
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
