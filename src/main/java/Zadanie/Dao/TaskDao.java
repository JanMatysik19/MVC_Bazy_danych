package Zadanie.Dao;

import Zadanie.Config.Database;
import Zadanie.Models.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class TaskDao {
    public static void save(TaskModel task) {
        var sql = """
            INSERT INTO %s(Content, Status) VALUES(?, ?);
        """.formatted(Database.TABLE);

        try (var conn = Database.getConnection(); var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getContent());
            stmt.setString(2, task.getStatus());

            Thread.sleep(4000);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Could not save the task.");
        }

    }

    public static void delete(int id) {
        var sql = """
            DELETE FROM %s WHERE Id = ?;
        """.formatted(Database.TABLE);

        try (var conn = Database.getConnection(); var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            Thread.sleep(4000);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Could not delete the task.");
        }

    }

    public static List<TaskModel> getAllTasks() {
        var sql = """
            SELECT Id, Content, Status FROM %s;
        """.formatted(Database.TABLE);

        try (var conn = Database.getConnection(); var stmt = conn.createStatement()) {
            List<TaskModel> out = new ArrayList<>();
            var rs = stmt.executeQuery(sql);

            while (rs.next()) out.add(new TaskModel(
                    rs.getInt("Id"), rs.getString("Content"), TaskModel.StatusType.fromString(rs.getString("Status"))
            ));

            Thread.sleep(4000);
            return out;
        } catch (Exception e) {
            System.out.println("Could not get all the task.");
        }

        return null;
    }

    public static String[] getColumnNames() {
        var sql = """
            PRAGMA table_info(%s);
        """.formatted(Database.TABLE);

        try (var conn = Database.getConnection(); var stmt = conn.createStatement()) {
            List<String> out = new ArrayList<>();
            var rs = stmt.executeQuery(sql);

            while (rs.next()) out.add(rs.getString("name"));

            Thread.sleep(4000);
            return out.toArray(String[]::new);
        } catch (Exception e) {
            System.out.println("Could not get all column names.");
        }

        return new String[0];
    }
}
