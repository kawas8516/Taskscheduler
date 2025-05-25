package com.taskscheduler.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.taskscheduler.dao.TaskDAO;
import com.taskscheduler.model.Task;
import com.taskscheduler.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        TaskDAO taskDAO = new TaskDAO();
        try {
            List<Task> tasks = taskDAO.getTasksByUser(user.getUserId());
            request.setAttribute("tasks", tasks);
            request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error retrieving tasks");
            request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        TaskDAO taskDAO = new TaskDAO();
        
        try {
            if ("add".equals(action)) {
                Task task = new Task();
                task.setUserId(user.getUserId());
                task.setTitle(request.getParameter("title"));
                task.setDescription(request.getParameter("description"));
                task.setDueDate(LocalDateTime.parse(request.getParameter("dueDate")));
                task.setPriority(request.getParameter("priority"));
                task.setStatus("PENDING");
                task.setReminderSent(false);
                taskDAO.addTask(task);
            } else if ("update".equals(action)) {
                Task task = new Task();
                task.setTaskId(Integer.parseInt(request.getParameter("taskId")));
                task.setUserId(user.getUserId());
                task.setTitle(request.getParameter("title"));
                task.setDescription(request.getParameter("description"));
                task.setDueDate(LocalDateTime.parse(request.getParameter("dueDate")));
                task.setPriority(request.getParameter("priority"));
                task.setStatus(request.getParameter("status"));
                
                taskDAO.updateTask(task);
            } else if ("delete".equals(action)) {
                int taskId = Integer.parseInt(request.getParameter("taskId"));
                taskDAO.deleteTask(taskId);
            }
            
            response.sendRedirect("tasks");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        }
    }
}