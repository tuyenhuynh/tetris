/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model;

import java.awt.Dimension;
import java.awt.Point;
import org.apache.log4j.Logger;
import tetris.model.event.FieldBottomEvent;
import tetris.model.event.FieldBottomListener;
import tetris.model.event.FigureActionListener;
import tetris.model.shape.Figure;
import tetris.model.event.GameFieldEvent;
import tetris.model.event.GameListener;
import tetris.model.event.RemoveShapesEvent;

/**
 * Game's model
 */
public class GameModel {
    private static final Logger logger = Logger.getLogger(GameModel.class);
    /**
     * Score
     */ 
    private int score; 
    /**
     * Figure factory
     */
    private FigureFactory figureFactory; 
    /**
     * Active figure
     */
    private Figure activeFigure; 
    
    /**
     * Next figure
     */
    private Figure nextFigure; 
    /**
     * Listener
     */
    private GameListener gameListener; 
    /**
     * Game field
     */
    private GameField gameField; 
    /**
     * bonus calculator
     */
    private BonusCalculator bonusCalculator;
    /**
     * Size of board
     */
    private Dimension gridDimension;
    
    public GameModel() {
        figureFactory = new FigureFactory(); 
        bonusCalculator  = new BonusCalculator();
        gridDimension  = new  Dimension(10, 20);
        gameField = new GameField(gridDimension);
        gameField.addFieldBottomListener(new FieldBottomObserver());
    }
    
    /**
     * Set size for game board
     * @param dimension size of game board
     */
    public void setGridSize(Dimension dimension){
        this.gridDimension = dimension; 
        gameField.updateDimension(gridDimension); 
    }
    
    /**
     * Get size of game board
     * @return size of game board
     * 
     */
    public Dimension getGridSize() {
        return this.gridDimension; 
    }
    
    /**
     * Start game
     */
    public void play() {
        gameField.activateFigure();
    }
    
    /**
     * Stop game
     */
    public void stopGame() {
        gameField.deactivateFigure();
    }
    
    /**
     * Create new game
     */
    public void newGame() {
        //set initial score
        score = 0; 
        //set active figure and it's properties
        activeFigure = figureFactory.createRandomFigure();
        activeFigure.setPosition(new Point(gridDimension.width/2-1, gridDimension.height));
        activeFigure.setGameField(gameField);
        activeFigure.addActionListener(new FigureActionObserver());
        //set next figure 
        nextFigure = figureFactory.createRandomFigure();
        nextFigure.setPosition(new Point(0, 1));
        //publish event
        gameListener.nextFigureChanged(nextFigure);
        //set active figure for game field
        gameField.setActiveFigure(activeFigure);
        gameField.clearBottom();
        //star game
        play();
    }
    
    /**
     * Get active figure
     * @return active figure
     */
    public Figure  getActiveFigure() {
        return this.activeFigure;
    }
    
    /**
     * Add GameListener
     * @param gameListener gameListener 
     */
    public void addGameListener(GameListener gameListener) {
        this.gameListener = gameListener;
    }
    
    private class FieldBottomObserver implements FieldBottomListener {

        @Override
        public void figureStopped(FieldBottomEvent event) {
            //generate next figure
            Figure newFigure = figureFactory.createRandomFigure();
            newFigure.setPosition(new Point(0, 1));
            //change active figure
            activeFigure = nextFigure;
            //configure active figure
            activeFigure.setPosition(new Point(((int)gridDimension.getWidth())/2-1, ((int)gridDimension.getHeight())));
            activeFigure.setGameField(gameField);
            activeFigure.addActionListener(new FigureActionObserver());
            //set active figure for game field
            gameField.setActiveFigure(activeFigure);
            //change next figure
            nextFigure = newFigure;
            //publish event about next figure changed
            gameListener.nextFigureChanged(nextFigure);
            //publish event about board status
            GameFieldEvent gameFieldEvent = new GameFieldEvent(this);
            gameFieldEvent.setActiveFigure(activeFigure);
            gameFieldEvent.setShapes(event.getShapes());
            gameListener.figureStopped(gameFieldEvent);
            logger.info("Figure stopped. Rerendering grid");
        }

        //process event about full rows removed
        @Override
        public void fullRowsRemoved(RemoveShapesEvent event) {
            //calculate bonus
            int bonus = bonusCalculator.calculateBonus(event.getRemovedShape());
            score += bonus; 
            //publish event after score changed
            gameListener.scoreChanged(score);
            logger.info("Score changed");
            //publish event about full row removed
            GameFieldEvent gameFieldEvent = new GameFieldEvent(this);
            gameFieldEvent.setShapes(event.getRemainedShapes());
            gameListener.fullRowRemoved(gameFieldEvent);
        }

        //process event about buttom overloaded
        @Override
        public void bottomOverload(FieldBottomEvent event) {
            gameField.deactivateFigure();
            gameListener.gameOver();
            logger.info("Game over");
        }
    }
    
    private class FigureActionObserver implements FigureActionListener{
        
        //process event about figure's rotation
        @Override
        public void figureRotated(GameFieldEvent event) {
            gameListener.figureRotated(event);
            logger.info("Figure rotated. Rerendering grid");
        }
        
        //process event about figure's movement
        @Override
        public void figureMoved(GameFieldEvent event) {
            gameListener.figureMoved(event);
            logger.info("Figure moved. Rerendering grid");
        }
    }
    
}
