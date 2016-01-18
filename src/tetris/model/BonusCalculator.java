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
 * Calculate bonus
 */
public class BonusCalculator {
    
    /**
     * Cell's price
     */
    private static final double CELL_PRICE = 10;

    /**
     * Calculate bonus from list of shapes
     * @param shapes input shapes to calculate bonus
     * @return bonus
     */
    public int calculateBonus(List<Shape> shapes) {
        
        int bonus = 0 ; 
        
        for(Shape shape : shapes) {
            if(shape instanceof Figure) {
                //bonus+= 4*10*CELL_PRICE;
                bonus+= 4*10*CELL_PRICE;
            }else {
                bonus+= CELL_PRICE;
                System.out.println("Bonus :" + bonus); 
            }
        }
        return bonus; 
    }
}
