<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.taskscheduler.model.User" %>
<%
    // Check if the user is logged in by verifying the session
    User user = (User) session.getAttribute("user");
    if (user == null) {
        // If not logged in, redirect to login page
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
<head>
    <title>Task Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; }
        .container { width: 80%; margin: 0 auto; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .priority-high { background-color: #ffcccc; }
        .priority-medium { background-color: #ffffcc; }
        .priority-low { background-color: #ccffcc; }
        .status-completed { text-decoration: line-through; color: #888; }
        .modal { display: none; position: fixed; z-index: 1; left: 0; top: 0; width: 100%; height: 100%; overflow: auto; background-color: rgba(0,0,0,0.4); }
        .modal-content { background-color: #fefefe; margin: 15% auto; padding: 20px; border: 1px solid #888; width: 50%; }
        .close { color: #aaa; float: right; font-size: 28px; font-weight: bold; }
        .close:hover { color: black; cursor: pointer; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Welcome, ${user.username}!</h2>
        <p><a href="logout">Logout</a></p>
        
        <h3>Your Tasks</h3>
        <button onclick="openAddTaskModal()">Add New Task</button>
        
        <table>
		    <tr>
		        <th>Title</th>
		        <th>Description</th>
		        <th>Due Date</th>
		        <th>Priority</th>
		        <th>Status</th>
		        <th>Actions</th>
		    </tr>
		    <c:forEach var="task" items="${tasks}">
			    <tr class="priority-${task.priority.toLowerCase()}">
			        <td class="${task.status eq 'COMPLETED' ? 'status-completed' : ''}">${fn:escapeXml(task.title)}</td>
			        <td class="${task.status eq 'COMPLETED' ? 'status-completed' : ''}">${fn:escapeXml(task.description)}</td>
			        <td class="${task.status eq 'COMPLETED' ? 'status-completed' : ''}">
			            ${task.formattedDueDate}
			        </td>
			        <td class="${task.status eq 'COMPLETED' ? 'status-completed' : ''}">${task.priority}</td>
			        <td class="${task.status eq 'COMPLETED' ? 'status-completed' : ''}">${task.status}</td>
			        <td>
			            <button 
			                onclick="openEditTaskModal(${task.taskId}, 
			                    '${fn:escapeXml(task.title)}', 
			                    '${fn:escapeXml(task.description)}', 
			                    '${task.dueDateForInput}', 
			                    '${task.priority}', 
			                    '${task.status}')">
			                Edit
			            </button>
			            <form action="tasks" method="post" style="display:inline;">
			                <input type="hidden" name="action" value="delete">
			                <input type="hidden" name="taskId" value="${task.taskId}">
			                <button type="submit">Delete</button>
			            </form>
			        </td>
			    </tr>
			</c:forEach>
		</table>
        
        <!-- Add Task Modal -->
        <div id="addTaskModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeAddTaskModal()">&times;</span>
                <h3>Add New Task</h3>
                <form action="tasks" method="post">
                    <input type="hidden" name="action" value="add">
                    <label for="title">Title:</label>
                    <input type="text" id="title" name="title" required><br>
                    <label for="description">Description:</label>
                    <textarea id="description" name="description"></textarea><br>
                    <label for="dueDate">Due Date:</label>
                    <input type="datetime-local" id="dueDate" name="dueDate" required><br>
                    <label for="priority">Priority:</label>
                    <select id="priority" name="priority" required>
                        <option value="HIGH">High</option>
                        <option value="MEDIUM" selected>Medium</option>
                        <option value="LOW">Low</option>
                    </select><br>
                    <button type="submit">Save Task</button>
                </form>
            </div>
        </div>
        
        <!-- Edit Task Modal -->
        <div id="editTaskModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeEditTaskModal()">&times;</span>
                <h3>Edit Task</h3>
                <form action="tasks" method="post">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" id="editTaskId" name="taskId">
                    <label for="editTitle">Title:</label>
                    <input type="text" id="editTitle" name="title" required><br>
                    <label for="editDescription">Description:</label>
                    <textarea id="editDescription" name="description"></textarea><br>
                    <label for="editDueDate">Due Date:</label>
                    <input type="datetime-local" id="editDueDate" name="dueDate" required><br>
                    <label for="editPriority">Priority:</label>
                    <select id="editPriority" name="priority" required>
                        <option value="HIGH">High</option>
                        <option value="MEDIUM">Medium</option>
                        <option value="LOW">Low</option>
                    </select><br>
                    <label for="editStatus">Status:</label>
                    <select id="editStatus" name="status" required>
                        <option value="PENDING">Pending</option>
                        <option value="COMPLETED">Completed</option>
                    </select><br>
                    <button type="submit">Update Task</button>
                </form>
            </div>
        </div>
    </div>
    
    <script>
        // Modal functions
        function openAddTaskModal() {
            document.getElementById('addTaskModal').style.display = 'block';
        }
        
        function closeAddTaskModal() {
            document.getElementById('addTaskModal').style.display = 'none';
        }
        
        function openEditTaskModal(taskId, title, description, dueDate, priority, status) {
            document.getElementById('editTaskId').value = taskId;
            document.getElementById('editTitle').value = title;
            document.getElementById('editDescription').value = description;
            
            // Convert dueDate to format compatible with datetime-local input
            const date = new Date(dueDate);
            const formattedDate = date.toISOString().slice(0, 16);
            document.getElementById('editDueDate').value = formattedDate;
            
            document.getElementById('editPriority').value = priority;
            document.getElementById('editStatus').value = status;
            
            document.getElementById('editTaskModal').style.display = 'block';
        }
        
        function closeEditTaskModal() {
            document.getElementById('editTaskModal').style.display = 'none';
        }
        
        // Close modals when clicking outside
        window.onclick = function(event) {
            if (event.target.className === 'modal') {
                event.target.style.display = 'none';
            }
        }
    </script>
</body>
</html>
