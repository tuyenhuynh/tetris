/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model;

import java.util.List;
import tetris.model.shape.Shape;
import tetris.model.shape.Figure;

/**
 *
 * @author tuyenhm
 */
public class BonusCalculator {
    
    private static final double CELL_PRICE = 10;
    
    public int calculateBonus(List<Shape> shapes) {
        
        int bonus = 0 ; 
        
        for(Shape shape : shapes) {
            if(shape instanceof Figure) {
                bonus+= 4*10*CELL_PRICE;
            }else {
                bonus+= CELL_PRICE;
            }
        }
        return bonus; 
    }
    
}
