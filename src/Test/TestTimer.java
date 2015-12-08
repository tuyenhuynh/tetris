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
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

/**
 *
 * @author tuyenhm
 */
public class TestTimer extends JFrame {
    
    private MyPanel panel = new MyPanel(); 
    
    public TestTimer() {
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Box box = Box.createVerticalBox();
        box.add(panel);
        setContentPane(box);
        pack(); 
        setLocationRelativeTo(null);
    }
    
    
    class MyPanel extends JPanel{
        
        boolean[][] isFilled; 
        
        private static final int CELL_SIZE  = 30; 
        
        private Timer timer; 
        private int width = 10; 
        private int height = 10; 
        
        private int currentX; 
        private int currentY; 
        
        private  final Logger logger = Logger.getLogger(MyPanel.class);
        
        public MyPanel() {
            
            isFilled = new boolean[height][width];
            for(int i = 0 ;i  < height;  ++i ) {
                for(int j = 0 ;  j < width ; ++j ) {
                    isFilled[j][i] = false; 
                }
            }
            
            currentX = 5; 
            currentY = 0; 
            
            timer = new Timer(800, new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(canMoveDown()){
                        currentY++;
                    }else {
                        isFilled[currentY][currentX] = true;
                        currentY = 0;
                        currentX = 5 ; 
                    }
                    repaint();    
                }
            }); 
            
            
            setLayout(new GridLayout(10, 10));
            setPreferredSize(new Dimension(width*CELL_SIZE, height*CELL_SIZE));
            setMaximumSize(new Dimension(width*CELL_SIZE, height*CELL_SIZE));
            setMinimumSize(new Dimension(width*CELL_SIZE, height*CELL_SIZE));
            
            timer.start();
            
            addKeyListener(new KeyObserver()); 
            
            setFocusable(true);
            
        }
        
        private boolean canMoveDown() {
            return currentY+1 < height && !isFilled[currentY+1][currentX];
        }
        
        private boolean canMoveLeft() {
            return currentX-1 >=0 && !isFilled[currentY][currentX-1];
        }
        
        private boolean canMoveRight() {
            return currentX+1<width && !isFilled[currentY][currentX+1];
        }
        
        private boolean inPanel(int x, int y){
            return x >= 0 && x <width && y >= 0  && y < height; 
        }
        
        
        
        @Override 
        public void paint(Graphics g){
            paintBoard(g);
            paintFallingPiece(g); 
        }
        
        private void paintBoard(Graphics g ) {
            for(int i = 0 ; i < height ; ++i ){
                for(int j = 0 ;j < width ; ++j) {
                    if(isFilled[i][j]){
                        paintSquare(g, j, i, Color.CYAN);
                    }else {
                        paintSquare(g, j, i, Color.darkGray);
                    }
                }
            }
        }
        
        private void paintSquare(Graphics g, int x, int y, Color color) {
            g.setColor(color);
            g.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE - 2 , CELL_SIZE - 2);
        }
        
        private void  paintFallingPiece(Graphics g) {
            g.setColor(Color.CYAN);
            g.fillRect(currentX * CELL_SIZE, currentY * CELL_SIZE, CELL_SIZE-2, CELL_SIZE-2);
        }
        
        class KeyObserver extends KeyAdapter {
            
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode() ; 
                switch (keyCode) {
                    case KeyEvent.VK_DOWN :
                        logger.info("down");
                        if(canMoveDown()){
                            currentY++;
                            repaint(); 
                        }
                        break; 
                    case KeyEvent.VK_LEFT :
                        logger.info("left");
                        if(canMoveLeft()) {
                            currentX--;
                            repaint(); 
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        logger.info("right");
                        if(canMoveRight()) {
                            currentX++;
                            repaint(); 
                        }
                        break;
                    default:
                        return ; 
                }
            }        
        }
    }
    
}
