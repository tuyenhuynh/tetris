/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model;

import java.awt.Point;
import java.util.List;
import org.apache.log4j.Logger;
import tetris.model.event.FieldBottomEvent;
import tetris.model.event.FieldBottomListener;
import tetris.model.event.FigureActionListener;
import tetris.model.shape.Figure;
import tetris.model.event.GameFieldEvent;
import tetris.model.event.GameListener;
import tetris.model.event.RemoveShapesEvent;
import tetris.model.shape.Shape;

/**
 *
 * @author tuyenhm
 */
public class GameModel {
    private static final Logger logger = Logger.getLogger(GameModel.class);
    private int score; 
    private FigureFactory figureFactory; 
    private Figure activeFigure; 
    private Figure nextFigure; 
    private GameListener gameListener; 
    private GameField gameField; 
    private BonusCalculator bonusCalculator;
    private static final int WIDTH = 10; 
    private static final int HEIGHT = 20;
    
    public GameModel() {
        figureFactory = new FigureFactory(); 
        bonusCalculator  = new BonusCalculator();
        gameField = new GameField(10, 20);
        gameField.addFieldBottomListener(new FieldBottomObserver());
    }
    
    public void startGame() {
        gameField.activateFigure();
    }
    
    public void stopGame() {
        gameField.deactivateFigure();
    }
    
    public void createNewGame() {
        score = 0; 
        activeFigure = figureFactory.createRandomFigure();
        activeFigure.setPosition(new Point(WIDTH/2-1, HEIGHT));
        activeFigure.setGameField(gameField);
        nextFigure = figureFactory.createRandomFigure();
        activeFigure.addActionListener(new FigureActionObserver());
        gameListener.nextFigureChanged(nextFigure);
        gameField.setActiveFigure(activeFigure);
        gameField.clearBottom();
        startGame();
    }
    
    public Figure  getActiveFigure() {
        return this.activeFigure;
    }
    
     public void addGameListener(GameListener gameListener) {
        this.gameListener = gameListener;
    }
    
    class FieldBottomObserver implements FieldBottomListener {

        @Override
        public void figureStopped(FieldBottomEvent event) {
            //generate next figure
            Figure newFigure = figureFactory.createRandomFigure();
            //change active figure
            activeFigure = nextFigure;
            //configure active figure
            activeFigure.setPosition(new Point(WIDTH/2-1, HEIGHT));
            activeFigure.setGameField(gameField);
            activeFigure.addActionListener(new FigureActionObserver());
            //set active figure for game field
            gameField.setActiveFigure(activeFigure);
            
            //next figure
            nextFigure = newFigure;
            //render next figure
            gameListener.nextFigureChanged(nextFigure);
            
            
            GameFieldEvent gameFieldEvent = new GameFieldEvent(this);
            gameFieldEvent.setActiveFigure(activeFigure);
            gameFieldEvent.setShapes(event.getShapes());
            //TODO:
            gameListener.gridBoardChanged(gameFieldEvent);
            logger.info("Figure stopped. Rerendering grid");
        }

        @Override
        public void fullRowsRemoved(RemoveShapesEvent event) {
            int bonus = bonusCalculator.calculateBonus(event.getRemovedShape());
            score += bonus; 
            gameListener.scoreChanged(score);
            logger.info("Score changed");
        }

        @Override
        public void bottomOverload(FieldBottomEvent event) {
            gameField.deactivateFigure();
            gameListener.gameOver();
            logger.info("Game over");
        }
    }
    
    class FigureActionObserver implements FigureActionListener{
        
        @Override
        public void figureRotated(GameFieldEvent event) {
            gameListener.gridBoardChanged(event);
            logger.info("Figure rotated. Rerendering grid");
        }
        
        @Override
        public void figureMoved(GameFieldEvent event) {
            gameListener.gridBoardChanged(event);
            logger.info("Figure moved. Rerendering grid");
        }
    }
    
}
