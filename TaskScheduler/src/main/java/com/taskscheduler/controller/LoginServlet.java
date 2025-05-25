package com.taskscheduler.controller;

import com.taskscheduler.dao.UserDAO;
import com.taskscheduler.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String LOGIN_JSP = "/views/login.jsp";
    private static final String TASKS_PAGE = "tasks";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();

        try {
            // Validate user using the method that checks hashed password
            User user = userDAO.getValidUser(username, password);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect(TASKS_PAGE);  // Redirect after successful login
            } else {
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred. Please try again.");
            request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Display login page
        request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
    }
}
