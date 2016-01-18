/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model.event;

import java.util.EventObject;

/**
 *
 * @author tuyenhm
 */
public class GridSizeEvent extends EventObject {
    
    private int columnCount; 
    private int rowCount;
    
    public GridSizeEvent(Object source){
        super(source);
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
    
    
}
