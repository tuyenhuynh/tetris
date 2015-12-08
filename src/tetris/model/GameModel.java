/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model;

import java.awt.Color;
import java.awt.Point;
import java.util.List;
import tetris.model.event.FigureActionListener;
import tetris.model.figure.Figure;
import tetris.model.event.GameBoardListener;
import tetris.model.event.GameFieldEvent;
import tetris.model.event.GameFieldListener;
import tetris.model.event.NextFigureBoardListener;
import tetris.model.event.ScoreBoardListener;
import tetris.model.figure.Shape;
import tetris.navigation.Direction;

/**
 *
 * @author tuyenhm
 */
public class GameModel {
    
    private int score; 
    private FigureFactory figureFactory; 
    private Figure activeFigure; 
    private Figure nextFigure; 
    private GameBoardListener gameBoardListener;
    private ScoreBoardListener scoreBoardListener;
    private NextFigureBoardListener nextFigureBoardListener;
    private GameField gameField; 
    private BonusCalculator bonusCalculator;
    private FigureActionListener figureActionListener;
    
    
    private static final int WIDTH = 10; 
    private static final int HEIGHT = 20;
    
    
    
    public GameModel() {
        figureFactory = new FigureFactory(); 
        bonusCalculator  = new BonusCalculator();
        gameField = new GameField(10, 20);
        gameField.addGameFieldListener(new GameFieldObserver());
    }
    
    public void start() {
        activeFigure = figureFactory.createRandomFigure();
        activeFigure.setPosition(new Point(WIDTH/2-1, HEIGHT));
        activeFigure.setGameField(gameField);
        nextFigure = figureFactory.createRandomFigure();
        activeFigure.addActionListener(new FigureActionObserver());
        nextFigureBoardListener.nextFigureChanged(nextFigure);
        gameField.setActiveFigure(activeFigure);
        gameField.activateFigure();
    }
    
    public Figure  getActiveFigure() {
        return this.activeFigure;
    }
    
    boolean isGameOver() {
        return false; 
    }
    
     public void addGameBoardListener(GameBoardListener gameBoardListener) {
        this.gameBoardListener = gameBoardListener;
    }

    public void addScoreBoardListener(ScoreBoardListener scoreBoardListener) {
        this.scoreBoardListener = scoreBoardListener;
    }

    public void addNextFigureBoardListener(NextFigureBoardListener nextFigureBoardListener) {
        this.nextFigureBoardListener = nextFigureBoardListener;
    }
    
    
    class GameFieldObserver implements GameFieldListener {

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
            nextFigureBoardListener.nextFigureChanged(nextFigure);
            
            //TODO: how to client know about new figure ????
            GameFieldEvent event = new GameFieldEvent (gameField.getFieldBottom().getShapes(), activeFigure);
            gameBoardListener.boardStatusChanged(event);
        }

        @Override
        public void figureMoved() {
            GameFieldEvent event = new GameFieldEvent (gameField.getFieldBottom().getShapes(), activeFigure);
            gameBoardListener.boardStatusChanged(event);
        }

        @Override
        public void figureRotated() {
            GameFieldEvent event = new GameFieldEvent (gameField.getFieldBottom().getShapes(), activeFigure);
            gameBoardListener.boardStatusChanged(event);
        }

        @Override
        public void fullRowsRemoved(List<Shape> shapes) {
            double bonus = bonusCalculator.calculateBonus(shapes);
            scoreBoardListener.scoreChanged(bonus);
        }
    }
    
    class FigureActionObserver implements FigureActionListener{

        @Override
        public void figureMoved() {
            GameFieldEvent event = new GameFieldEvent(gameField.getFieldBottom().getShapes(), activeFigure);
            gameBoardListener.boardStatusChanged(event);
        }
    }
    
}
