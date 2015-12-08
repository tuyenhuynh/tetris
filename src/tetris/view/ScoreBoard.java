/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import tetris.model.event.ScoreBoardListener;

/**
 *
 * @author tuyenhm
 */
public class ScoreBoard extends JPanel{
    
    private double score;
    
    JLabel headerLabel = new JLabel("SCORE");
    JLabel scoreLabel = new JLabel("0");
    
    ScoreBoardListener listener; 
    
    public ScoreBoard() {
        setForeground(Color.WHITE);
        
        Dimension size = new Dimension(100, 70);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
        headerLabel.setBackground(new Color(2, 77, 150));
        setBackground(Color.cyan);
        BorderLayout layout = new BorderLayout(); 
        setLayout(layout);
        add(headerLabel, BorderLayout.NORTH);
        add(scoreLabel, BorderLayout.CENTER);
        score = 0 ; 
    }
    
    public void updateScore(double score) {
        scoreLabel.setText(Double.toString(score));
    }
    
    @Override
    public void repaint() {
        
    }
    
    class ScoreBoardObserver implements ScoreBoardListener {

        @Override
        public void scoreChanged(double delta) {
            score+=delta;
            updateScore(score);
        }
        
    }
    
}
