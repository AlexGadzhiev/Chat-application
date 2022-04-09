package com.example.demo;

import java.util.Objects;

public class UserHistory {
    private String username;
    private String history;

    public UserHistory() {}

    public UserHistory(String username, String history) {
        this.username = username;
        this.history = history;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserHistory user = (UserHistory) o;
        return Objects.equals(username, user.username) && Objects.equals(history, user.history);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, history);
    }
}
