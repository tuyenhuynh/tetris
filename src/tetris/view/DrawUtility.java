/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.view;

import java.awt.Color;
import java.awt.Graphics;
import tetris.model.shape.Shape;

/**
 *
 * @author tuyenhm
 */
public class DrawUtility {
    
    public static void drawShape(Graphics g, int paddingX, int paddingY, int cellSize, int rowCount, Shape shape) {
        for(int i = 0 ; i < shape.getHeight() ; ++i) {
            for( int j = 0 ; j < shape.getWidth() ; ++j) {
                if(shape.getCellValue(i, j) == 1) {
                    g.setColor(shape.getColor());
                    int x = shape.getPosition().x + j; 
                    int y = rowCount - (shape.getPosition().y - i) -1; 
                    int realX = cellSize * x + paddingX; 
                    int realY = cellSize * y + paddingY;
                    g.fillRect( realX+1, realY , cellSize -1, cellSize -1);
                    g.setColor(new Color(234, 238, 239));
                    g.drawRect(realX+5, realY +4,  cellSize- 10, cellSize - 11);
                    Color c = shape.getColor(); 
                    for( int t = 0 ; t < 20 ; ++ t ) {
                       c.darker();
                    }
                    g.setColor(c);
                    g.fillRect( realX+6, realY+5, cellSize-11, cellSize-12);
                }
            }
        }
    }
    
}
