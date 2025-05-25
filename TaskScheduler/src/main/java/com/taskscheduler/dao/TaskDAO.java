package com.taskscheduler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.taskscheduler.model.Task;
import com.taskscheduler.util.DBUtil;

public class TaskDAO {

    public boolean addTask(Task task) throws SQLException {
        String sql = "INSERT INTO tasks (user_id, title, description, due_date, priority, status, reminder_sent) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, task.getUserId());
            stmt.setString(2, task.getTitle());
            stmt.setString(3, task.getDescription());
            stmt.setTimestamp(4, Timestamp.valueOf(task.getDueDate()));
            stmt.setString(5, task.getPriority());
            stmt.setString(6, task.getStatus());
            stmt.setBoolean(7, task.isReminderSent());
            return stmt.executeUpdate() > 0;
        }
    }
    
    public List<Task> getTasksByUser(int userId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE user_id = ? ORDER BY due_date";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Task task = new Task(
                        rs.getInt("task_id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getTimestamp("due_date").toLocalDateTime(),
                        rs.getString("priority"),
                        rs.getString("status"),
                        rs.getBoolean("reminder_sent")
                    );
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }
    
    public boolean updateTask(Task task) throws SQLException {
        String sql = "UPDATE tasks SET title = ?, description = ?, due_date = ?, " +
                     "priority = ?, status = ?, reminder_sent = ? WHERE task_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setTimestamp(3, Timestamp.valueOf(task.getDueDate()));
            stmt.setString(4, task.getPriority());
            stmt.setString(5, task.getStatus());
            stmt.setBoolean(6, task.isReminderSent());
            stmt.setInt(7, task.getTaskId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean deleteTask(int taskId) throws SQLException {
        String sql = "DELETE FROM tasks WHERE task_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, taskId);
            return stmt.executeUpdate() > 0;
        }
    }

    // New method to get tasks that are due soon (within 1 hour)
    public List<Task> getTasksDueSoon() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE status = 'PENDING' " +
                     "AND due_date BETWEEN NOW() AND NOW() + INTERVAL '1 hour' " +
                     "AND reminder_sent = FALSE";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Task task = new Task(
                    rs.getInt("task_id"),
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getTimestamp("due_date").toLocalDateTime(),
                    rs.getString("priority"),
                    rs.getString("status"),
                    rs.getBoolean("reminder_sent")
                );
                tasks.add(task);
            }
        }
        return tasks;
    }

    // New method to mark a task's reminder as sent
    public boolean markReminderAsSent(int taskId) throws SQLException {
        String sql = "UPDATE tasks SET reminder_sent = TRUE WHERE task_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, taskId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Existing method to get upcoming tasks (within 1 hour)
    public List<Task> getUpcomingTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE status = 'PENDING' AND due_date BETWEEN NOW() AND NOW() + INTERVAL '1 hour'";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Task task = new Task(
                    rs.getInt("task_id"),
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getTimestamp("due_date").toLocalDateTime(),
                    rs.getString("priority"),
                    rs.getString("status"),
                    rs.getBoolean("reminder_sent")
                );
                tasks.add(task);
            }
        }
        return tasks;
    }
}
