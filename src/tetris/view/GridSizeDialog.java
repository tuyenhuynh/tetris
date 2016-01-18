/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import tetris.model.event.GridSizeEvent;
import tetris.model.event.GridSizeListener;

/**
 *
 * @author tuyenhm
 */
public class GridSizeDialog extends JDialog {
    private JPanel panel;
    private SpinnerNumberModel  columnModel ;
    private SpinnerNumberModel  rowModel ;
    private JSpinner columnSpinner; 
    private JSpinner rowSpinner; 
    private JButton btnOk = new JButton("OK");
    private JLabel columnLabel = new JLabel("Columns: ");
    private JLabel rowLabel  = new JLabel("Rows: ");
    private GridSizeListener listener; 
    
    public GridSizeDialog(final JFrame parent) {
        super(parent);
        columnModel = new SpinnerNumberModel(10, 10, 15, 1);
        rowModel = new SpinnerNumberModel(20, 20, 30, 1);
        columnSpinner = new JSpinner(columnModel);
        rowSpinner = new JSpinner(rowModel);
        setupLayout(); 
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GridSizeEvent evt = new GridSizeEvent(this);
                evt.setColumnCount(Integer.parseInt(columnSpinner.getValue().toString()));
                evt.setRowCount(Integer.parseInt(rowSpinner.getValue().toString()));
                listener.gridSizeChanged(evt);
            }
        });
        setMinimumSize(new Dimension(300, 120));
        setResizable(false);
        setLocationRelativeTo(null);
        
    }
    
    public void addGridSizeListener(GridSizeListener listener) {
        this.listener = listener; 
    }
    
    private void setupLayout(){
        panel = new JPanel(); 
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints(); 
        gc.gridx = 0; 
        gc.gridy = 0;
        panel.add(columnLabel, gc);
        
        gc.gridx = 1; 
        gc.gridy = 0;
        gc.insets = new Insets(5, 5, 5, 5);
        panel.add(columnSpinner, gc); 
        
        gc.gridx = 2; 
        gc.gridy = 0; 
        gc.insets = new Insets(5, 0, 5, 5);
        panel.add(rowLabel, gc); 
        
        gc.gridx = 3; 
        gc.gridy = 0; 
        gc.insets = new Insets(5, 0, 5, 5);
        panel.add(rowSpinner, gc); 
        
        gc.gridx = 3; 
        gc.gridy = 0; 
        gc.insets = new Insets(5, 0, 5, 5);
        panel.add(rowSpinner, gc); 
        
        gc.gridx = 1; 
        gc.gridy = 1; 
        gc.insets = new Insets(5, 5, 5, 5);
        panel.add(btnOk, gc);
        add(panel);
    }
}
