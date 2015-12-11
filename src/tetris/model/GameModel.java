/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model;

import java.awt.Point;
import java.util.List;
import tetris.model.event.FieldBottomListener;
import tetris.model.event.FigureActionListener;
import tetris.model.shape.Figure;
import tetris.model.event.GameFieldEvent;
import tetris.model.event.GameListener;
import tetris.model.shape.Shape;

/**
 *
 * @author tuyenhm
 */
public class GameModel {
    
    private int score; 
    private FigureFactory figureFactory; 
    private Figure activeFigure; 
    private Figure nextFigure; 
    private GameListener gameListener; 
    private GameField gameField; 
    private BonusCalculator bonusCalculator;
    private FigureActionListener figureActionListener;
    
    
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
    
    boolean isGameOver() {
        return false; 
    }
    
     public void addGameListener(GameListener gameListener) {
        this.gameListener = gameListener;
    }
    
    class FieldBottomObserver implements FieldBottomListener {

        @Override
        public void figureStopped() {
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
            
            //TODO: how to client know about new figure ????
            GameFieldEvent event = new GameFieldEvent (gameField.getFieldBottom().getShapes(), activeFigure);
            gameListener.gridBoardChanged(event);
        }

        @Override
        public void fullRowsRemoved(List<Shape> shapes) {
            int bonus = bonusCalculator.calculateBonus(shapes);
            score += bonus; 
            gameListener.scoreChanged(score);
        }

        @Override
        public void bottomOverload() {
            gameField.deactivateFigure();
            gameListener.gameOver();
        }
    }
    
    class FigureActionObserver implements FigureActionListener{

        @Override
        public void figureRotated() {
            GameFieldEvent event = new GameFieldEvent (gameField.getFieldBottom().getShapes(), activeFigure);
            gameListener.gridBoardChanged(event);
        }
        
        @Override
        public void figureMoved() {
            GameFieldEvent event = new GameFieldEvent(gameField.getFieldBottom().getShapes(), activeFigure);
            gameListener.gridBoardChanged(event);
        }
    }
    
}
