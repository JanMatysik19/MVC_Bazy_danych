package Zadanie.Views;

import Zadanie.Config.AppInitials;
import Zadanie.Config.Styles;
import Zadanie.Controllers.TasksController;
import Zadanie.Utils.Stylised;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame() {
        initialize();
    }

    private void initialize() {
        setTitle(AppInitials.APP_TITLE);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void display() {
        setVisible(true);
    }

    public void setContent(Content content) {
        setContentPane(content);
        pack();
        setLocation(200, 200);
    }

    public void setAppCursor(Cursor cursor) {
        setCursor(cursor);
    }


    public static class Content extends JPanel {
        private final ControlPanel controlPanel;
        private final InsertPanel insertPanel;
        private final DataPanel dataPanel;

        public Content(TasksController controller) {
            this.controlPanel = controller.getControlPanel();
            this.insertPanel = controller.getInsertPanel();
            this.dataPanel = controller.getDataPanel();
            initialize();
        }

        private void initialize() {
            // View initialisation >--------------------
            Stylised.stylised(this, Styles.FRAME_CONTENT);
            setBorder(BorderFactory.createEmptyBorder(25, 50, 50, 50));
            setLayout(new GridBagLayout());
            var c = new GridBagConstraints();
            c.anchor = GridBagConstraints.NORTHWEST;

            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(0, 0, 0, 25);
            add(controlPanel, c);
            c.gridx = 0;
            c.gridy = 1;
            c.weighty = 1;
            c.insets = new Insets(25, 0, 0, 25);
            add(insertPanel, c);
            c.gridx = 1;
            c.gridy = 0;
            c.gridheight = 2;
            c.weighty = 1;
            c.insets = new Insets(0, 0, 0, 0);
            add(dataPanel, c);
        }
    }
}
