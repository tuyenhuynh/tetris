/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model;

import tetris.model.shape.SFigure;
import tetris.model.shape.JFigure;
import tetris.model.shape.Figure;
import tetris.model.shape.LFigure;
import tetris.model.shape.ZFigure;
import tetris.model.shape.IFigure;
import tetris.model.shape.TFigure;
import tetris.model.shape.OFigure;
import java.awt.Point;
import java.util.Random;
/**
 *
 * @author tuyenhm
 */
public class FigureFactory {
    
    private static final  Random random = new Random(); 
    
    public Figure createRandomFigure() {
        int num  = Math.abs(random.nextInt()%7);
        num = 6; 
        switch(num){
            case 0:
                return new IFigure(); 
            case 1:
                return new JFigure(); 
            case 2:
                return new LFigure(); 
            case 3:
                return new OFigure(); 
            case 4:
                return new SFigure(); 
            case 5:
                return new TFigure(); 
            default:
                return new ZFigure(); 
        }
    }
}
