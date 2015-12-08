/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import com.sun.istack.internal.logging.Logger;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import tetris.model.GameModel;
import tetris.model.figure.Figure;

/**
 *
 * @author tuyenhm
 */
public class GameBoardTest extends JPanel{
    
    private static final int CELL_SIZE  = 30; 
        
    private Timer timer; 
    private int width = 10; 
    private int height = 20; 

    private int currentX; 
    private int currentY; 

    private  final Logger logger = Logger.getLogger(GameBoardTest.class);
    
    private GameModel model; 
    
    private Figure figure; 
    
    private JButton btnStart;
    
    private JButton btnPause; 
    
    public GameBoardTest() {
        model = new GameModel(); 
        //model.setBoardSize(width, height);
        
        
        addKeyListener(new KeyObserver()); 
        
        setLayout(new GridLayout(height, width));
        setPreferredSize(new Dimension(width*CELL_SIZE, height*CELL_SIZE));
        setMaximumSize(new Dimension(width*CELL_SIZE, height*CELL_SIZE));
        setMinimumSize(new Dimension(width*CELL_SIZE, height*CELL_SIZE));
        setFocusable(true);
        
        btnStart.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
    }
    
    public void start() {
        model.start();
        //figure = model.getRandomFigure();
        
        timer = new Timer(800, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
//                if(model.canMoveDown(currentX, currentY, figure)){
//                    currentY++;
//                }else {
//                    model.place(currentX, currentY, figure);
//                    //update currentX and currentY
//                    figure = model.getRandomFigure();
//                }
                repaint();
            }
        }); 
        timer.start();
    }
    
    @Override 
    public void paint(Graphics g){
        paintBoard(g);
        paintFallingFigure(g); 
    }

    private void paintBoard(Graphics g ) {
//        for(int i = 0 ; i < height ; ++i ){
//            for(int j = 0 ;j < width ; ++j) {
//                Color c = model.getColor(i, j); 
//                if(c== null){
//                    c = Color.darkGray;
//                }
//                paintSquare(g, j, i, c);
//            }
//        }
    }

    private void paintSquare(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE - 2 , CELL_SIZE - 2);
    }
        
    private void  paintFallingFigure(Graphics g) {
        //g.setColor(figure.getColor());
        //paint 4 piece of figures
        g.fillRect(currentX * CELL_SIZE, currentY * CELL_SIZE, CELL_SIZE-2, CELL_SIZE-2);
    }
    
    class KeyObserver extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode() ; 
            switch (keyCode) {
//                case KeyEvent.VK_DOWN :
//                    logger.info("down");
//                    if(model.canMoveDown(currentX, currentY, figure)){
//                        currentY++;
//                        repaint(); 
//                    }
//                    break; 
//                case KeyEvent.VK_LEFT :
//                    logger.info("left");
//                    if(model.canMoveToLeft(currentX, currentY, figure)) {
//                        currentX--;
//                        repaint(); 
//                    }
//                    break;
//                case KeyEvent.VK_RIGHT:
//                    logger.info("right");
//                    if(model.canMoveToRight(currentX, currentY, figure)) {
//                        currentX++;
//                        repaint(); 
//                    }
//                    break;
//                case KeyEvent.VK_UP :
//                    logger.info("Up");
//                    if(model.canRotate(currentX, currentY, figure)) {
//                        //figure = figure.rotate();
//                        //update currentX and currentY; 
//                        repaint();
//                    }
//                    break;
//                default:
//                    return ; 
            }
        } 
    }
    
}
