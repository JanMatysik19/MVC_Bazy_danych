package Zadanie.Views;

import Zadanie.Config.AppInitials;
import Zadanie.Config.Styles;
import Zadanie.Models.TableModel;
import Zadanie.Utils.Stylised;

import javax.swing.*;
import java.awt.*;

public class DataPanel extends JPanel {
    private JTable tasksTbl;
    private TableModel tableModel;

    public DataPanel() {
        initialize();
    }

    public void initialize() {
        // Components initialisation >--------------------
        var tasksStl = new Stylised<>(new JTable()); // Table
        tasksTbl = tasksStl.stylised(Styles.TASKS_TABLE).create();
        tasksTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tasksTbl.setColumnSelectionAllowed(false);

        var tasksHeaderStl = new Stylised<>(tasksTbl.getTableHeader()); // Table header
        var tasksHeaderTbh = tasksHeaderStl.stylised(Styles.TASKS_TABLE_HEADER).create();
        tasksHeaderTbh.setReorderingAllowed(false);
        tasksTbl.setTableHeader(tasksHeaderTbh);

        var tasksContainerStl = new Stylised<>(new JScrollPane(tasksTbl)); // Table scrollable container
        var tasksContainerScp = tasksContainerStl.stylised(Styles.DATA_SCROLL_PANE).create();
        var tasksContainerBarStl = new Stylised<>(new JScrollBar());
        var tasksContainerBarScb = tasksContainerBarStl.stylised(Styles.DATA_SCROLL_BAR).create();
        tasksContainerScp.setVerticalScrollBar(tasksContainerBarScb);

        // View initialisation >--------------------
        Stylised.sized(this, AppInitials.DATA_PANEL_SIZE);
        Stylised.stylised(this, Styles.DATA_PANEL);
        setLayout(new GridBagLayout());
        var c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        add(new Stylised<>(
                new JLabel("Zadania", JLabel.CENTER)
        ).stylised(Styles.PANEL_HEADER).create(), c);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;
        c.insets = new Insets(20, 0, 0, 0);
        add(tasksContainerScp, c);
    }

    // Getters and setters >--------------------
    public void setTableModel(TableModel model) {
        tableModel = model;
        tasksTbl.setModel(model);
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public int getSelectedRow() {
        return tasksTbl.getSelectedRow();
    }
}
