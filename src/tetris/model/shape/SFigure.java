/*
 * So change this license header, choose License Headers in Project Properties.
 * So change this template file, choose Sools | Semplates
 * and open the template in the editor.
 */
package tetris.model.shape;

import java.awt.Color;


/**
 *
 * @author tuyenhm
 */
public class SFigure extends Figure{

    private static final int [][][] cellsConfigs=
        {
         {
            {0,1,1},
            {1,1,0}
         }, 
         {
             {1,0},
             {1,1},
             {0,1}
         }      
        };
    
    public SFigure() {
       state = 0; 
       cells = cellsConfigs[state];
       setColor(new Color(109, 227, 89));
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
                position.y-=1;
                break;
            case 0:
                state = 1;  
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
                position.y-=1;
                break;
            case 0:
                state = 1;  
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
