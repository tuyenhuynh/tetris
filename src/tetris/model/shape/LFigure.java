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
public class LFigure extends Figure{

    private static final int [][][] cellsConfigs = 
        {
         {
            {1,1,1},
            {1,0,0}
         }, 
         {
             {1,1},
             {0,1},
             {0,1}
         }, 
         {
             {0,0,1},
             {1,1,1}
         },
         {
             {1,0},
             {1,0},
             {1,1}
         }      
        };
    
    public LFigure() {
       state = 0; 
       cells = cellsConfigs[state];
       setColor(new Color(209, 159, 23));
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
                position.y-=1;
                break;
            case 2:
                state =3;
                position.x+=1;
                position.y+=1;
                break;
        }
        cells = cellsConfigs[state];
        validateRotation();
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
                position.y+=1;
                break;
            case 3:
                state = 2;
                position.x -=1;
                position.y -=1; 
                break;
        }
        cells = cellsConfigs[state];
    }

    @Override 
    public int[][] getCells() {
        return cellsConfigs[state];
    }
    
}
