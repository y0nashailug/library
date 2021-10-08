package ui.panels;

import business.ControllerInterface;
import business.SystemController;
import ui.LTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewBookIds extends JPanel {

    public static ViewBookIds INSTANCE = new ViewBookIds();
    ControllerInterface ci = new SystemController();
    private JTable table;
    private JScrollPane scrollPane;
    private LTableModel model;
    private final String[] DEFAULT_COLUMN_HEADERS
            = {"Ids"};
    private static final int SCREEN_WIDTH = 320;
    private static final int SCREEN_HEIGHT = 320;
    private static final int TABLE_WIDTH = (int) (0.75 * SCREEN_WIDTH);
    private static final int DEFAULT_TABLE_HEIGHT = (int) (0.75 * SCREEN_HEIGHT);
    private final float [] COL_WIDTH_PROPORTIONS =
            {0.35f, 0.35f, 0.3f};

    private ViewBookIds() {}

    public void init() {
        createTableAndTablePane();
        this.add(scrollPane);
        setValues();
        table.updateUI();
    }

    private void createTableAndTablePane() {
        updateModel();
        table = new JTable(model);
        createCustomColumns(table, TABLE_WIDTH,
                COL_WIDTH_PROPORTIONS, DEFAULT_COLUMN_HEADERS);
        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(
                new Dimension(TABLE_WIDTH, DEFAULT_TABLE_HEIGHT));
        scrollPane.getViewport().add(table);
    }

    private void createCustomColumns(JTable table, int width,
                                     float[] proportions, String[] headers) {
        table.setAutoCreateColumnsFromModel(false);
        int num = headers.length;
        for(int i = 0; i < num; ++i) {
            TableColumn column = new TableColumn(i);
            column.setHeaderValue(headers[i]);
            column.setMinWidth(Math.round(proportions[i]*width));
            table.addColumn(column);
        }
    }

    private void setValues() {

        List<String[]> data = new ArrayList<>();

        List<String> ids = ci.allBookIds();
        Collections.sort(ids);

        for (String id: ids) {
            data.add(new String[] { id });
        }
        model.setTableValues(data);
    }

    public void updateModel(List<String[]> list){
        if(model == null) {
            model = new LTableModel();
        }
        model.setTableValues(list);
    }

    private void updateModel() {
        List<String[]> theData = new ArrayList<String[]>();
        updateModel(theData);
    }

    public void revalidateTable(String id) {
        model.addRow(new String[] { id });
        model.fireTableDataChanged();
    }
}
