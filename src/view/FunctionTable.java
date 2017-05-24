package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by anonymous on 10.05.2017.
 */
public class FunctionTable  {
    private JPanel tablePanel = new JPanel(new BorderLayout());
    DefaultTableModel tableModel;
    JTable table;
    JScrollPane scrollPane;
    public FunctionTable(Controller controller)
    {
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        String header[] = new String[]{"Number", "Time"};
        tableModel.setColumnIdentifiers(header);
        scrollPane.setPreferredSize(new Dimension(200, 80));
        reloadTable(controller);
        tablePanel.add(scrollPane);
    }

    public JPanel getTablePanel(){return tablePanel;}

    public void reloadTable(Controller controller) {
        while (tableModel.getRowCount()>0)
            tableModel.removeRow(0);
        tableModel.addRow(new Double[]{0.0, 0.0});
        int number = 1;
        for (Double d: controller.getDataBase().getArrayList()){
            tableModel.addRow(new Double[]{Double.valueOf(number), d});
            number++;
            }
        table.updateUI();
        scrollPane.updateUI();
    }
}
