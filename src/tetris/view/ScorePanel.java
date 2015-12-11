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

/**
 *
 * @author tuyenhm
 */
public class ScorePanel extends JPanel{
    
    JLabel headerLabel = new JLabel("SCORE");
    JLabel scoreLabel = new JLabel("0");
    
    public ScorePanel() {
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
    }
    
    @Override
    public void repaint() {
        
    }
    
    public void setScore(int score){
        scoreLabel.setText(Integer.toString(score));
    }
}
