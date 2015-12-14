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
public interface FigureActionListener extends EventListener {
    public void figureMoved(GameFieldEvent event);
    public void figureRotated(GameFieldEvent event);
}
