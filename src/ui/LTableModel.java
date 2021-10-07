package ui;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class LTableModel extends AbstractTableModel {
    //this is a List of Object arrays
    private List<Object[]> tableValues;

    public void setTableValues(Object [][] vals) {
        tableValues = Arrays.asList(vals);
    }

    public void setTableValues(java.util.List<String[]> vals) {
        if(vals == null) {
            return;
        }
        Iterator<String[]> it = vals.iterator();
        while(it.hasNext()){
            addRow(it.next());
        }
    }
    public Object[][] getTableValues() {
        return (Object[][])tableValues.toArray();
    }

    /** This convenience method allows addition of a row of type Object.
     * However, this can only be used if row is of type Object[].
     * If not, the method exits without performing any operation.
     * @param row
     */
    public void addRow(Object row){
        if(row instanceof Object[]){
            addRow((Object[])row);
        }

    }

    public void addRow(Object[] row){
        if(tableValues == null){
            tableValues = new ArrayList<Object[]>();
        }
        tableValues.add(row);
    }


    /**
     * Implementation of the table model interface. It returns the
     * the object stored in the model at the specified indices.
     */
    public Object getValueAt(int rowIndex, int colIndex) {
        Object[] row = (Object[])tableValues.get(rowIndex);
        return row[colIndex];
    }
    /**
     * This method is included because it is an abstract
     * method in AbstractTableModel. In this version of a table
     * model, the default method of setting values is used, so
     * the method here does not need a body.
     */
    public void setValueAt(Object val, int rowIndex, int colIndex) {
        //not used
    }
    public int getColumnCount() {
        return 0;
    }
    public int getRowCount() {
        if(tableValues==null) return 0;
        return tableValues.size();
    }

    private static final long serialVersionUID = 3257846584573376055L;

}
