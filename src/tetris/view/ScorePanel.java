/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author tuyenhm
 */
public class ScorePanel extends JPanel{
    private int score = 0; 
    public ScorePanel() {
        Dimension size = new Dimension(160, 100);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(22, 37, 103));
        g.setColor(new Color(51, 99, 207));
        g.drawRect(0, 0, getWidth()-1, getHeight()-1);
        g.fillRect(0, 0, getWidth(), 30);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);
        Font font = new Font("Impact", Font.TRUETYPE_FONT, 26);
        g2.setFont(font);
        g2.drawString("SCORE", 40, 25);
        g2.drawString(Integer.toString(score), 25, 70);
    }
    
    public void setScore(int score){
        this.score = score; 
        repaint(); 
    }
}
