package com.taskscheduler.util;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.taskscheduler.dao.TaskDAO;
import com.taskscheduler.dao.UserDAO;
import com.taskscheduler.model.Task;
import com.taskscheduler.model.User;

public class ReminderThread {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final TaskDAO taskDAO = new TaskDAO();
    private final UserDAO userDAO = new UserDAO();
    private final EmailConfig emailConfig = new EmailConfig();

    public void start() {
        // Check every minute for tasks due soon
        scheduler.scheduleAtFixedRate(this::checkAndSendReminders, 0, 1, TimeUnit.MINUTES);
    }

    private void checkAndSendReminders() {
        try {
            List<Task> upcomingTasks = taskDAO.getTasksDueSoon();
            for (Task task : upcomingTasks) {
                if (shouldSendReminder(task)) {
                    sendReminder(task);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in reminder thread: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean shouldSendReminder(Task task) {
        return "PENDING".equals(task.getStatus()) && !task.isReminderSent();
    }

    private void sendReminder(Task task) throws SQLException {
        User user = userDAO.getUserById(task.getUserId());
        if (user != null) {
            sendEmailReminder(user.getEmail(), task);
            taskDAO.markReminderAsSent(task.getTaskId());
            
            // For future: Add in-app notification here
        }
    }

    private void sendEmailReminder(String toEmail, Task task) {
        try {
            Message message = createEmailMessage(toEmail, task);
            Transport.send(message);
            System.out.println("Reminder sent for task: " + task.getTitle());
        } catch (MessagingException e) {
            System.err.println("Failed to send email reminder: " + e.getMessage());
            throw new RuntimeException("Email sending failed", e);
        }
    }

    private Message createEmailMessage(String toEmail, Task task) throws MessagingException {
        Session session = createMailSession();
        Message message = new MimeMessage(session);
        
        message.setFrom(new InternetAddress(emailConfig.getFromAddress()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Task Reminder: " + task.getTitle());
        message.setContent(createEmailContent(task), "text/html");
        
        return message;
    }

    private Session createMailSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", emailConfig.getHost());
        props.put("mail.smtp.port", emailConfig.getPort());
        
        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    emailConfig.getUsername(), 
                    emailConfig.getPassword()
                );
            }
        });
    }

    private String createEmailContent(Task task) {
        return "<html><body>" +
               "<h2>Task Reminder</h2>" +
               "<p>This is a reminder for your upcoming task:</p>" +
               "<table border='1'>" +
               "<tr><th>Task</th><td>" + escapeHtml(task.getTitle()) + "</td></tr>" +
               "<tr><th>Description</th><td>" + escapeHtml(task.getDescription()) + "</td></tr>" +
               "<tr><th>Due Date</th><td>" + task.getFormattedDueDate() + "</td></tr>" +
               "<tr><th>Priority</th><td>" + escapeHtml(task.getPriority()) + "</td></tr>" +
               "</table>" +
               "<p>Please complete it on time.</p>" +
               "<p>Regards,<br>Task Scheduler Team</p>" +
               "</body></html>";
    }

    private String escapeHtml(String input) {
        return input == null ? "" : input
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;");
    }

    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}