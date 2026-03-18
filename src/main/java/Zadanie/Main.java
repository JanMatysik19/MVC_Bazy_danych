package Zadanie;

import Zadanie.Config.Database;
import Zadanie.Controllers.TasksController;
import Zadanie.Models.TableModel;
import Zadanie.Views.ControlPanel;
import Zadanie.Views.DataPanel;
import Zadanie.Views.Frame;
import Zadanie.Views.InsertPanel;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        initFlatLaf();
        SwingUtilities.invokeLater(Main::initApp);
    }

    private static void bindMVC(Frame frame) {
        var controlPanel = new ControlPanel();
        var insertPanel = new InsertPanel();
        var dataPanel = new DataPanel();
        var tableModel = new TableModel();

        var controller = new TasksController(tableModel, controlPanel, insertPanel, dataPanel, frame::setAppCursor);

        var content = new Frame.Content(controller);
        frame.setContent(content);
    }

    private static void initApp() {
        Database.initialize();

        var frame = new Frame();
        bindMVC(frame);
        frame.display();
    }

    private static void initFlatLaf() {
        FlatDarkLaf.registerCustomDefaultsSource("themes");
        FlatMacDarkLaf.setup();
    }
}
