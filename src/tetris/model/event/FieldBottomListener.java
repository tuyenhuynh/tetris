/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model.event;

import java.util.EventListener;

/**
 *
 * @author tuyenhm
 */
public interface FieldBottomListener extends EventListener {
    void figureStopped(FieldBottomEvent event);
    void fullRowsRemoved(RemoveShapesEvent event);
    void bottomOverload(FieldBottomEvent event);
}
