/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model;

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
    private static final int WIDTH = 10; 
    private static final int HEIGHT = 20;
    
    public GameModel() {
        figureFactory = new FigureFactory(); 
        bonusCalculator  = new BonusCalculator();
        gameField = new GameField(10, 20);
        gameField.addFieldBottomListener(new FieldBottomObserver());
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
        activeFigure.setPosition(new Point(WIDTH/2-1, HEIGHT));
        activeFigure.setGameField(gameField);
        activeFigure.addActionListener(new FigureActionObserver());
        //set next figure 
        nextFigure = figureFactory.createRandomFigure();
        nextFigure.setPosition(new Point(0, 1));
        //emit event
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
