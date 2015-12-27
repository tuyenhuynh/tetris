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
import javax.swing.JPanel;
import tetris.model.shape.Figure;

/**
 *
 * @author tuyenhm
 */
public class NextFigurePanel extends JPanel{
    
    //next figure
    private Figure figure; 
    
    //margins
    private static final int margin_left = 20; 
    private static final int margin_top = 40 ; 
    //cell's size
    private static final int CELL_SIZE = 30 ; 
    
    public NextFigurePanel() {
        Dimension d = new Dimension(160, 110); 
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
    }
    
    //set next figure
    public void setFigure(Figure figure) {
        this.figure = figure; 
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // set background
        setBackground(new Color(22, 37, 103));
        //draw border
        g.setColor(new Color(51, 99, 207));
        g.drawRect(0, 0, getWidth()-1, getHeight()-1);
        //draw title of panel
        g.fillRect(0, 0, getWidth(), 30);
        Font font = new Font("Impact", Font.TRUETYPE_FONT, 26);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString("NEXT", 40 ,25 );
        //draw next figure
        if(figure!= null) {
            DrawUtility.drawShape(g, margin_left,  margin_top, CELL_SIZE,2, figure);
        }
    }
}