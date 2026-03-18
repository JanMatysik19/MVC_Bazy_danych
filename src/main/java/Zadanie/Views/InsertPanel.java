package Zadanie.Views;

import Zadanie.Config.AppInitials;
import Zadanie.Config.Styles;
import Zadanie.Models.TaskModel;
import Zadanie.Utils.Stylised;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InsertPanel extends JPanel {
    private JTextArea contentTxa;
    private JComboBox<String> statusCmb;
    private JButton insertBtn;

    public InsertPanel() {
        initialize();
    }

    private void initialize() {
        // Components initialisation >--------------------
        var contentStl = new Stylised<>(new JTextArea()); // Content
        contentTxa = contentStl.stylised(Styles.CONTENT_TEXTAREA).create();
        contentTxa.setLineWrap(true);
        contentTxa.setWrapStyleWord(true);

        var contentContainerStl = new Stylised<>(new JScrollPane(contentTxa)); // Content scrollable container
        var contentContainerScp = contentContainerStl.stylised(Styles.INSERT_SCROLL_PANE).create();
        var contentContainerBarStl = new Stylised<>(new JScrollBar());
        var contentContainerBarScb = contentContainerBarStl.stylised(Styles.INSERT_SCROLL_BAR).create();
        contentContainerScp.setVerticalScrollBar(contentContainerBarScb);

        var statusStl = new Stylised<>(new JComboBox<>(TaskModel.StatusType.TYPES)); // Status combo box
        statusCmb = statusStl.stylised(Styles.STATUS_COMBO).sized(120, 30).create();

        var insertStl = new Stylised<>(new JButton("Dodaj")); // Insert button
        insertBtn = insertStl.stylised(Styles.INSERT_BUTTON).sized(120, 30).create();
        insertBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // View initialisation >--------------------
        Stylised.sized(this, AppInitials.INSERT_PANEL_SIZE);
        Stylised.stylised(this, Styles.INSERT_PANEL);
        setLayout(new GridBagLayout());
        var c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 2;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        add(new Stylised<>(
                new JLabel("Nowe zadanie", JLabel.CENTER)
        ).stylised(Styles.PANEL_HEADER).create(), c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.weightx = 2;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(20, 0, 0, 0);
        add(new Stylised<>(
                new JLabel("Treść", JLabel.LEFT)
        ).stylised(Styles.PANEL_LABEL).create(), c);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.weightx = 2;
        c.weighty = 2;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 0, 0, 0);
        add(contentContainerScp, c);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.weightx = 2;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(15, 0, 0, 0);
        add(new Stylised<>(
                new JLabel("Status", JLabel.LEFT)
        ).stylised(Styles.PANEL_LABEL).create(), c);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.SOUTHWEST;
        add(statusCmb, c);
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.SOUTHEAST;
        add(insertBtn, c);
    }

    public void setInsertHandler(ActionListener l) {
        insertBtn.addActionListener(l);
    }

    public String getContent() {
        return contentTxa.getText();
    }

    public String getStatus() {
        return (String) statusCmb.getSelectedItem();
    }

    public void setInsertBtnEnabled(boolean enabled) {
        insertBtn.setEnabled(enabled);
    }

    public void displayValidationError(String error) {
        JOptionPane.showMessageDialog(null, "Podczas dodawania nowego zadania popełniono następujące błędy:\n%s".formatted(error),
                "Walidacja", JOptionPane.ERROR_MESSAGE);
    }

    public void displayInsertionSuccess() {
        JOptionPane.showMessageDialog(null, "Zakończono proces dodawania nowego zadania to tabeli.",
            "Dodawanie zadań", JOptionPane.INFORMATION_MESSAGE);
    }

    public void clearFields() {
        contentTxa.setText("");
        statusCmb.setSelectedIndex(0);
    }
}
