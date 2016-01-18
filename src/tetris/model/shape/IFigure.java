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
public class IFigure extends Figure{
    
    private static final int [][][] cellsConfigs = 
        {{
            {1,1,1,1}
         }, 
         {
             {1}, {1}, {1}, {1}
         }     
        };
    
    public IFigure() {
       state = 0; 
       cells = cellsConfigs[state];
       setColor(new Color(218, 10, 252));
    }

    @Override
    public int getWidth(){
        return state == 0 ? 4 : 1;
    }
    
    @Override
    public int getHeight() {
        return state == 0 ? 1: 4;
    }
    
    
    @Override
    public void rotateByClockWise() {
        switch (state){
            case 1:
                state = 0; 
                position.x-=2;
                position.y-=1;
                break;
            case 0:
                state = 1; 
                position.x+=2; 
                position.y+=1;
                break;
            default: 
                return;
        }
        cells = cellsConfigs[state];
    }

    @Override
    public void rotateAntiClockWise() {
        switch (state){
            case 0 :
                state =1; 
                position.x+=2;
                position.y+=1;
                break;
            case 1:
                state =0;
                position.x-=2;
                position.y-=1;
                break;
            default: 
                return;
        }
        cells = cellsConfigs[state];
    }
}

