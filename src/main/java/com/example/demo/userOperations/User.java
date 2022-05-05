package com.example.demo.userOperations;

import java.util.Objects;

public class User {
    private String username;
    private String password;

    private int is_logged;

    public User() {}

    public User(String username, String password, int is_logged) {
        this.username = username;
        this.password = password;
        this.is_logged = is_logged;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIs_logged() {
        return is_logged;
    }

    public void setIs_logged(int is_logged) {
        this.is_logged = is_logged;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(is_logged, user.is_logged);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, is_logged);
    }
}
