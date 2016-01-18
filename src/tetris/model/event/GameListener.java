/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model.event;

import tetris.model.shape.Figure;

/**
 *
 * @author tuyenhm
 */
public interface GameListener {
    void figureRotated(GameFieldEvent event);
    void figureMoved(GameFieldEvent event);
    void figureStopped(GameFieldEvent event);
    void fullRowRemoved(GameFieldEvent event);
    void nextFigureChanged(Figure nextFigure);
    void scoreChanged(int newScore);
    void gameOver();
}
