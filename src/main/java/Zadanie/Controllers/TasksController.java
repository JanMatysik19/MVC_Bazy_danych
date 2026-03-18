package Zadanie.Controllers;

import Zadanie.Config.AppInitials;
import Zadanie.Models.TableModel;
import Zadanie.Models.TaskModel;
import Zadanie.Utils.AsyncHandler;
import Zadanie.Views.ControlPanel;
import Zadanie.Views.DataPanel;
import Zadanie.Views.InsertPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class TasksController {
    private final TableModel tableModel;
    private final ControlPanel controlPanel;
    private final InsertPanel insertPanel;
    private final DataPanel dataPanel;
    private final Consumer<Cursor> changeAppCursor;

    public TasksController(TableModel tableModel, ControlPanel controlPanel, InsertPanel insertPanel, DataPanel dataPanel, Consumer<Cursor> changeAppCursor) {
        this.tableModel = tableModel;
        this.controlPanel = controlPanel;
        this.insertPanel = insertPanel;
        this.dataPanel = dataPanel;
        this.changeAppCursor = changeAppCursor;
        initialise();
    }

    private void initialise() {
        controlPanel.setDeleteHandler(this::handleDelete);
        controlPanel.setRefreshHandler(this::handleRefresh);
        insertPanel.setInsertHandler(this::handleInsert);
        dataPanel.setTableModel(tableModel);

        handleRefresh(null);
    }

    private void handleRefresh(ActionEvent actionEvent) {
        changeAppCursor.accept(new Cursor(Cursor.WAIT_CURSOR));
        controlPanel.setRefreshBtnEnabled(false);
        new AsyncHandler<>(TaskModel::_fetchAll, (data) -> {
            tableModel.setData(data);
            controlPanel.displayRefreshSuccess();
            changeAppCursor.accept(new Cursor(Cursor.DEFAULT_CURSOR));
            controlPanel.setRefreshBtnEnabled(true);
        }).execute();
    }

    private void handleDelete(ActionEvent actionEvent) {
        changeAppCursor.accept(new Cursor(Cursor.WAIT_CURSOR));
        controlPanel.setDeleteBtnEnabled(false);
        var selectedRow = dataPanel.getSelectedRow();

        new AsyncHandler<>(() -> {
            // Validation
            StringBuilder errors = new StringBuilder();
            if(selectedRow >= tableModel.getRowCount() || selectedRow < 0)
                errors.append("- Brak wybranego zadania do usunięcia");

            if(!errors.isEmpty()) {
                controlPanel.displayValidationError(errors.toString());
                return false;
            }

            return true;
        }, (result) -> {
            try {
                if(!result) {
                    changeAppCursor.accept(new Cursor(Cursor.DEFAULT_CURSOR));
                    controlPanel.setDeleteBtnEnabled(true);
                    return;
                }
            } catch (Exception _) { }

            var model = tableModel.getTaskModel(selectedRow);
            new AsyncHandler<>(() -> {
                model._delete();
                return null;
            }, (_) -> {
                handleRefresh(null);
                changeAppCursor.accept(new Cursor(Cursor.DEFAULT_CURSOR));
                controlPanel.setDeleteBtnEnabled(true);
                controlPanel.displayDeletionSuccess();
            }).execute();
        }).execute();
    }

    private void handleInsert(ActionEvent actionEvent) {
        changeAppCursor.accept(new Cursor(Cursor.WAIT_CURSOR));
        insertPanel.setInsertBtnEnabled(false);
        var content = insertPanel.getContent().trim();
        var model = new TaskModel(content, TaskModel.StatusType.fromString(insertPanel.getStatus()));

        new AsyncHandler<>(() -> {
            // Validation
            StringBuilder errors = new StringBuilder();
            if(content.isBlank() || content.length() < AppInitials.MINIMUM_TASK_CONTENT_LENGTH)
                errors.append("- Treść musi mieć co najmniej %d znaków".formatted(AppInitials.MINIMUM_TASK_CONTENT_LENGTH));

            if(!errors.isEmpty()) {
                insertPanel.displayValidationError(errors.toString());
                return false;
            }

            return true;
        }, (result) -> {
            try {
                if(!result) {
                    changeAppCursor.accept(new Cursor(Cursor.DEFAULT_CURSOR));
                    insertPanel.setInsertBtnEnabled(true);
                    return;
                }
            } catch (Exception _) { }

            new AsyncHandler<>(() -> {
                model._insert();
                return null;
            }, (_) -> {
                handleRefresh(null);
                insertPanel.clearFields();
                changeAppCursor.accept(new Cursor(Cursor.DEFAULT_CURSOR));
                insertPanel.setInsertBtnEnabled(true);
                insertPanel.displayInsertionSuccess();
            }).execute();
        }).execute();

    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public DataPanel getDataPanel() {
        return dataPanel;
    }

    public InsertPanel getInsertPanel() {
        return insertPanel;
    }
}
