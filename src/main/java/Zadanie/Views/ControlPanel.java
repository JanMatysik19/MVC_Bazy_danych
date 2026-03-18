package Zadanie.Views;

import Zadanie.Config.AppInitials;
import Zadanie.Config.Styles;
import Zadanie.Utils.Stylised;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private JButton deleteBtn, refreshBtn;

    public ControlPanel() {
        initialize();
    }

    private void initialize() {
        // Components initialisation >--------------------
        var deleteStl = new Stylised<>(new JButton("Usuń wybrany")); // Delete button
        deleteBtn = deleteStl.stylised(Styles.DELETE_BUTTON).sized(120, 30).create();
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        var refreshStl = new Stylised<>(new JButton("Odśwież")); // Refresh button
        refreshBtn = refreshStl.stylised(Styles.REFRESH_BUTTON).sized(120, 30).create();
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // View initialisation >--------------------
        Stylised.sized(this, AppInitials.CONTROL_PANEL_SIZE);
        Stylised.stylised(this, Styles.CONTROL_PANEL);
        setLayout(new GridBagLayout());
        var c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.anchor = GridBagConstraints.WEST;
        add(deleteBtn, c);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        c.anchor = GridBagConstraints.EAST;
        add(refreshBtn, c);
    }

    // Setters and displays >--------------------
    public void setDeleteHandler(ActionListener l) {
        deleteBtn.addActionListener(l);
    }

    public void setRefreshHandler(ActionListener l) {
        refreshBtn.addActionListener(l);
    }

    public void setDeleteBtnEnabled(boolean enabled) {
        deleteBtn.setEnabled(enabled);
    }

    public void setRefreshBtnEnabled(boolean enabled) {
        refreshBtn.setEnabled(enabled);
    }

    public void displayValidationError(String error) {
        JOptionPane.showMessageDialog(null, "Podczas usuwania zadania popełniono następujące błędy:\n%s".formatted(error),
                "Walidacja", JOptionPane.ERROR_MESSAGE);
    }

    public void displayDeletionSuccess() {
        JOptionPane.showMessageDialog(null, "Zakończonoe proces usuwania zdania z tabeli.",
                "Usuwanie zadań", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayRefreshSuccess() {
        JOptionPane.showMessageDialog(null, "Zakończonoe proces odświerzania danych tabeli.",
                "Odświerzanie", JOptionPane.INFORMATION_MESSAGE);
    }
}
