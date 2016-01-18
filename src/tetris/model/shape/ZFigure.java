/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model.shape;

import java.awt.Color;

/**
 *
 * @author tuyenhm
 */
public class ZFigure extends Figure{

    private static final int [][][] cellsConfigs=
        {
         {
            {1,1,0},
            {0,1,1}
         }, 
         {
             {0,1},
             {1,1},
             {1,0}
         }      
        };
    
    public ZFigure() {
       state = 0; 
       cells = cellsConfigs[state];
       setColor(new Color(255, 35, 41));
    }

    @Override
    public int getWidth(){
        return (state == 0) ? 3:2;
    }
    
    @Override
    public int getHeight() {
        return (state == 0) ? 2:3;
    }
    
    @Override
    public void rotateByClockWise() {
        
        switch (state){
            case 1:
                state = 0; 
                position.x-=1;
                position.y-=1;
                break;
            case 0:
                state = 1;  
                position.x+=1;
                position.y+=1;
                break;
        }
        cells = cellsConfigs[state];
    }

    @Override
    public void rotateAntiClockWise() {
        switch (state){
            case 1:
                state = 0; 
                position.x-=1;
                position.y-=1;
                break;
            case 0:
                state = 1;  
                position.x+=1;
                position.y+=1;
                break;
                
        }
        cells = cellsConfigs[state];
    }
    
    @Override
    public int[][] getCells() {
        return cellsConfigs[state];
    }
}
