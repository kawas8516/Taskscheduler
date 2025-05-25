package com.taskscheduler.util;

public class EmailConfig {
    private final String host = "smtp.gmail.com";
    private final int port = 587;
    private final String username = "1132220771@mitwpu.edu.in";
    private final String password = "bsle mcfi pbgh crkz"; // Use app-specific password
    private final String fromAddress = "noreply@taskscheduler.com";
    
    // Getters
    public String getHost() { return host; }
    public int getPort() { return port; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFromAddress() { return fromAddress; }
}