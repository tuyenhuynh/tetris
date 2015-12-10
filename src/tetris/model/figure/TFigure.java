/*
 * To change this license header, choose Ticense Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model.figure;

import java.awt.Point;

/**
 *
 * @author tuyenhm
 */
public class TFigure extends Figure{

    private static final int [][][] cellsConfigs = 
        {
         {
            {1,1,1},
            {0,1,0}
         }, 
         {
             {0,1},
             {1,1},
             {0,1}
         }, 
         {
             {0,1,0},
             {1,1,1}
         },
         {
             {1,0},
             {1,1},
             {1,0}
         }      
        };
    
    public TFigure() {
       state = 0; 
       cells = cellsConfigs[state];
    }

    @Override
    public int getWidth(){
        return (state == 0 || state == 2) ? 3:2;
    }
    
    @Override
    public int getHeight() {
        return (state == 0 || state == 2) ? 2:3;
    }
    
    @Override
    public void rotateByClockWise() {
        switch (state){
            case 3:
                state = 0; 
                position.x-=1;
                position.y-=1;
                break;
            case 0:
                state = 1;  
                position.y+=1;
                break;
            case 1:
                state = 2; 
                break;
            case 2:
                state =3;
                position.x+=1;
                break;
        }
        cells = cellsConfigs[state];
    }

    @Override
    public void rotateAntiClockWise() {
        switch (state){
            case 0:
                state =3; 
                position.x+=1;
                position.y+=1;
                break;
            case 1:
                state =0;
                position.y-=1;
                break;
            case 2:
                state = 1; 
                break;
            case 3:
                state = 2;
                position.x -=1;
                break;
        }
        cells = cellsConfigs[state];
    }

    @Override 
    public int[][] getCells() {
        return cellsConfigs[state];
    }
}
