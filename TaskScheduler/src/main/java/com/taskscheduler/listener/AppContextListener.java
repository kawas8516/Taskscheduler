package com.taskscheduler.listener;

import com.taskscheduler.util.ReminderThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {
    private ReminderThread reminderThread;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialize the ReminderThread and start it
        reminderThread = new ReminderThread();
        reminderThread.start();

        // Set the reminderThread as a context attribute, making it available for the whole app
        sce.getServletContext().setAttribute("reminderThread", reminderThread);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Gracefully shut down the reminder thread when the context is destroyed
        if (reminderThread != null) {
            reminderThread.shutdown();
        }
    }
}
