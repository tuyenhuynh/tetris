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
 *
 * @author tuyenhm
 */
public class GameModel {
    private static final Logger logger = Logger.getLogger(GameModel.class);
    //score
    private int score; 
    //figure factory
    private FigureFactory figureFactory; 
    //active figure
    private Figure activeFigure; 
    //next figure
    private Figure nextFigure; 
    //listener
    private GameListener gameListener; 
    //game field
    private GameField gameField; 
    //bonus calculator
    private BonusCalculator bonusCalculator;
    //standard size of game tetris
    private Dimension gridDimension;
    
    public GameModel() {
        figureFactory = new FigureFactory(); 
        bonusCalculator  = new BonusCalculator();
        gridDimension  = new  Dimension(10, 20);
        gameField = new GameField(gridDimension);
        gameField.addFieldBottomListener(new FieldBottomObserver());
    }
    
    public void setGridSize(Dimension dimension){
        this.gridDimension = dimension; 
        gameField.updateDimension(gridDimension); 
    }
    
    public Dimension getGridSize() {
        return this.gridDimension; 
    }
    
    //start game
    public void startGame() {
        gameField.activateFigure();
    }
    
    //stop game
    public void stopGame() {
        gameField.deactivateFigure();
    }
    
    //create new game
    public void createNewGame() {
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
    
    class FigureActionObserver implements FigureActionListener{
        
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
