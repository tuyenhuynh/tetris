/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author tuyenhm
 */
public class ScorePanel extends JPanel{
    
    //JLabel headerLabel = new JLabel("SCORE");
    //JLabel scoreLabel = new JLabel("0");
    private int score = 0; 
    public ScorePanel() {
        setForeground(Color.WHITE);
        
        Dimension size = new Dimension(160, 100);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
        
        //headerLabel.setBackground(new Color(2, 77, 150));
        
//        BorderLayout layout = new BorderLayout(); 
//        setLayout(layout);
        //add(headerLabel, BorderLayout.NORTH);
        //add(scoreLabel, BorderLayout.CENTER);
        //repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.black);
        g2.setFont(new Font("Consolas", Font.BOLD, 40));
        g2.drawString("SCORE", 20, 50);
        g2.drawString(Integer.toString(score), 20, 90);
        setBackground(Color.BLACK);
        //setBackground(new Color(28, 53, 111));
    }
    
    @Override
    public void repaint() {
        
    }
    
    public void setScore(int score){
        this.score = score; 
        repaint(); 
        //scoreLabel.setText(Integer.toString(score));
    }
}
