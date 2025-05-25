package com.taskscheduler.controller;

import com.taskscheduler.dao.UserDAO;
import com.taskscheduler.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String REGISTER_JSP = "/views/register.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Server-side validation
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match");
            request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
            return;
        }

        UserDAO userDAO = new UserDAO();
        try {
            if (userDAO.getUserByUsername(username) != null) {
                request.setAttribute("error", "Username already exists");
                request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
                return;
            }

            if (userDAO.getUserByEmail(email) != null) {
                request.setAttribute("error", "Email already registered");
                request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
                return;
            }

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(password); // Hashing recommended in production

            if (userDAO.addUser(newUser)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", newUser);
                response.sendRedirect("tasks");
            } else {
                request.setAttribute("error", "Registration failed. Please try again.");
                request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred");
            request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
        }
    }
}
