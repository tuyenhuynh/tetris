/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import tetris.model.shape.Figure;

/**
 *
 * @author tuyenhm
 */
public class NextFigurePanel extends JPanel{
    
    private Figure figure; 
    
    public NextFigurePanel() {
        Dimension d = new Dimension(200, 150); 
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
    }
    
    public void setFigure(Figure figure) {
        this.figure = figure; 
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int margin_left = 10; 
        int margin_top = 10 ; 
        int CELL_SIZE = 30 ; 
        g.setColor(Color.RED);
        if(figure != null){
            for(int i = 0 ; i < figure.getHeight(); ++i) {
                for(int j = 0 ; j < figure.getWidth() ; ++j) {
                    if(figure.getCellValue(i, j) != 0) {
                        g.fillRect(margin_left + j*CELL_SIZE,
                                margin_top + i *CELL_SIZE, 
                                CELL_SIZE,
                                CELL_SIZE);
                        
                    }
                }
            }
        }
    }
    
    private void drawSquare(Graphics g, int x, int y, int size) {
        int realX = size * x ; 
        int realY = size * y ;
        g.fillRect(realX, realY, size - 1, size - 1);
    }
    
}
