/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model.event;

import java.util.List;
import tetris.model.figure.Shape;

/**
 *
 * @author tuyenhm
 */
public interface GameFieldListener {
    void figureStopped();
    void figureMoved();
    void figureRotated();
    void fullRowsRemoved(List<Shape> shapes);
    
}
