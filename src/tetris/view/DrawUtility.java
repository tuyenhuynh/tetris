/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import tetris.model.shape.Shape;

/**
 *
 * @author tuyenhm
 */
public class DrawUtility {
    
    
    
    
    private void drawShape(Graphics g, int x, int y, int cellSize, Shape shape) {
        for(int i = 0 ; i < shape.getHeight() ; ++i) {
            for( int j = 0 ; j < shape.getWidth() ; ++j) {
                if(shape.getCellValue(i, j) == 1) {
                    g.setColor(shape.getColor());
                    int dx = (shape.getPosition().x + j) * cellSize; 
                    int dy = ((shape.getPosition().y - i-1)) * cellSize;
                    Point cellPos =  new Point(x+dx, y-dy);
                    g.fillRect(cellPos.x, cellPos.y, cellSize-1, cellSize-1);
                    g.setColor(new Color(234, 238, 239));
                    g.drawRect(cellPos.x+5, cellPos.y +4,  cellSize - 9, cellSize - 9);
                    Color c = shape.getColor(); 
                    for( int t = 0 ; t < 10 ; ++ t ) {
                       c.darker();
                    }
                    g.setColor(c);
                    g.fillRect(cellPos.x + 5, cellPos.y+6, cellSize - 10, cellSize - 10);
                    
                }
            }
        }
    }
    
    
}
