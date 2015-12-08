/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model.event;

import tetris.model.figure.Figure;

/**
 *
 * @author tuyenhm
 */
public interface NextFigureBoardListener {
    void nextFigureChanged(Figure nextFigure);
}
