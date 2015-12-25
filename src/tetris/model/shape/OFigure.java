/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model.shape;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author tuyenhm
 */
public class OFigure extends Figure{

    private int state; 
    
    private static final int[][][] cellsConfigs ={{ 
                                                    {1,1},
                                                    {1,1}}};
    
    public OFigure() {
        state = 0; 
        cells = cellsConfigs[state];
        setColor(new Color(3, 244, 255));
    }

    @Override
    public int getWidth() {
        return 2; 
    }    
    
    @Override
    public int getHeight() {
        return 2; 
    }
    
    @Override
    public int[][] getCells() {
        return cellsConfigs[0];
    }

    @Override
    public void rotateByClockWise() {
        
    }

    @Override
    public void rotateAntiClockWise() {
        
    }
    
}
