package Zadanie.Models;

import Zadanie.Dao.TaskDao;

import java.util.List;

public class TaskModel {
    private final int id;
    private String content;
    private StatusType status;

    public enum StatusType {
        PENDING("Oczekujące"), DONE("Zrobione");
        public static final String[] TYPES = new String[]{
                PENDING.value(), DONE.value()
        };

        private final String status;
        StatusType(String status) {
            this.status = status;
        }
        public String value() {
            return status;
        }
        public static StatusType fromString(String str) {
            for(var s : StatusType.values()) if(s.value().equals(str)) return s;
            return null;
        }
    }

    public TaskModel(int id, String content, StatusType status) {
        this.id = id;
        this.content = content;
        this.status = status;
    }
    public TaskModel(String content, StatusType status) {
        this.id = -1;
        this.content = content;
        this.status = status;
    }

    // DB methods >--------------------
    public void _insert() {
        TaskDao.save(this);
    }

    public void _delete() {
        TaskDao.delete(this.getId());
    }

    public static List<TaskModel> _fetchAll() {
        return TaskDao.getAllTasks();
    }

    public static String[] _fetchColumnNames() {
        return TaskDao.getColumnNames();
    }

    // Getters and setters >--------------------
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status.value();
    }
    public void setStatus(StatusType status) {
        this.status = status;
    }

    public Object getAt(int index) {
        return switch (index) {
            case 0 -> getId();
            case 1 -> getContent();
            case 2 -> getStatus();
            default -> null;
        };
    }
}
