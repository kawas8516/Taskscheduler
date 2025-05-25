package com.taskscheduler.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.taskscheduler.util.DateFormats;

public class Task {
    private Integer taskId;
    private Integer userId;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private String priority;
    private String status;
    private boolean reminderSent;

    // Constructors
    public Task() {}

    public Task(int taskId, int userId, String title, String description,
                LocalDateTime dueDate, String priority, String status, boolean reminderSent) {
        this.taskId = taskId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.reminderSent = reminderSent;
    }

    // Getters and Setters
    public Integer getTaskId() { return taskId; }
    public void setTaskId(Integer taskId) { this.taskId = taskId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isReminderSent() { return reminderSent; }
    public void setReminderSent(boolean reminderSent) { this.reminderSent = reminderSent; }

    // Formatting methods
    public String getFormattedDueDate() {
        return dueDate != null
            ? dueDate.format(DateTimeFormatter.ofPattern(DateFormats.DISPLAY_FORMAT))
            : "";
    }

    public String getDueDateForInput() {
        return dueDate != null
            ? dueDate.format(DateTimeFormatter.ofPattern(DateFormats.INPUT_FORMAT))
            : "";
    }

    public String formatDueDate(String pattern) {
        return dueDate != null
            ? dueDate.format(DateTimeFormatter.ofPattern(pattern))
            : "";
    }
}
