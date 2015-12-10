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
import tetris.model.event.NextFigureBoardListener;
import tetris.model.shape.Figure;

/**
 *
 * @author tuyenhm
 */
public class NextFigureBoard extends JPanel{
    
    private Figure figure; 
    
    public NextFigureBoard() {
        Dimension d = new Dimension(100, 150); 
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
    }
    
    public void setFigure(Figure figure) {
        this.figure = figure; 
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
                        g.drawRect(margin_left + j*CELL_SIZE,
                                margin_top + i *CELL_SIZE, 
                                margin_left + (j+1)*CELL_SIZE,
                                margin_top + (i+1)*CELL_SIZE);
                        
                    }
                }
            }
        }
    }
    
    class NextFigureBoardObserver implements NextFigureBoardListener{

        @Override
        public void nextFigureChanged(Figure nextFigure) {
            figure = nextFigure;
            repaint();
        }
        
    }
}
