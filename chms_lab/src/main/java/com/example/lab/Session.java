package com.example.lab;

public class Session {
    private static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Session.username = username;
    }
}