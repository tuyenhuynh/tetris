/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import tetris.model.event.GameBoardListener;
import tetris.model.event.GameFieldEvent;
import tetris.model.shape.Figure;
import tetris.model.shape.Shape;

/**
 *
 * @author tuyenhm
 */
public class GameBoard  extends JPanel{
    
    private final int width; 
    private final int height; 
    
    private static final int CELL_SIZE =  30;
    
    private Figure activeFigure = null; 
    
    private List<Shape> shapes = new ArrayList<>(); 
    
    public GameBoard (int width, int height) {
        this.width = width; 
        this.height = height; 
        Dimension size = new Dimension(width*CELL_SIZE, height*CELL_SIZE);
        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        for(int i = 1 ; i < width; ++i ){
            g.drawLine(i*CELL_SIZE, 0, i*CELL_SIZE, height*CELL_SIZE);
        }
        for(int i = 1 ; i < height ; ++i) {
            g.drawLine(0, i*CELL_SIZE, width*CELL_SIZE, i*CELL_SIZE);
        }
        //paint border 
        g.setColor(new Color(1, 168, 213));
        g.drawLine(0, 0, 300, 0);
        g.drawLine(0, 0, 0, 600);
        g.drawLine(299, 0, 299, 600);
        g.drawLine(0, 599, 300, 599);
        //#002039
        Color c = new Color (47, 79, 79);
        setBackground(new Color(0, 28, 49));
        
        g.setColor(Color.red);
        
        if(activeFigure != null ) {
            drawShape(g, activeFigure);
        }
        
        for(Shape shape : shapes) {
            drawShape(g, shape);
        }
        
    }
    
    private void drawSquare(Graphics g, int x, int y, int size) {
        int realX = size * x ; 
        int realY = size * y ;
        g.fillRect(realX, realY, size - 1, size - 1);
    }
    
    private void drawShape(Graphics g, Shape shape) {
        for(int i = 0 ; i < shape.getHeight() ; ++i) {
            for( int j = 0 ; j < shape.getWidth() ; ++j) {
                if(shape.getCellValue(i, j) == 1) {
                    drawSquare(g, shape.getPosition().x + j, height - (shape.getPosition().y - i) -1, CELL_SIZE);
                }
            }
        }
    }
    
    class GameBoardObserver implements GameBoardListener {

        @Override
        public void boardStatusChanged(GameFieldEvent event) {
            activeFigure = event.getActiveFigure(); 
            shapes = event.getShapes();
            repaint();
        }

    }
    
}
