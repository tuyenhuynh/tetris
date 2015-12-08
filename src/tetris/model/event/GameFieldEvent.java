/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model.event;

import java.util.List;
import tetris.model.figure.Figure;
import tetris.model.figure.Shape;

/**
 *
 * @author tuyenhm
 */
public class GameFieldEvent {
    private List<Shape> shapes; 
    private Figure activeFigure;
    
    public GameFieldEvent(List<Shape> shapes, Figure activeFigure) {
        this.shapes = shapes; 
        this.activeFigure = activeFigure; 
    }
    
    public List<Shape> getShapes() {
        return this.shapes;
    }
    
    public Figure getActiveFigure() {
        return this.activeFigure;
    }
    
}
