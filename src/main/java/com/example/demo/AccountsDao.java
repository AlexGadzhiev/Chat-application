package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountsDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private int cur_sum = 0;

    public String findHistory(String username){
        List<UserHistory> histories = this.selectHistory();
        for(UserHistory element : histories){
            if(element.getUsername().equals(username)){
                return element.getHistory();
            }
        }
        return "";
    }

    private String makeString(int a, int b, String op) {
        switch (op) {
            case "plus": {
                cur_sum = a + b;
                return a + " + " + b + " = " + (a + b);
            }
            case "minus": {
                cur_sum = a - b;
                return a + " - " + b + " = " + (a - b);
            }
            case "div": {
                cur_sum = a / b;
                return a + " / " + b + " = " + (a / b);
            }
            case "times": {
                cur_sum = a * b;
                return a + " * " + b + " = " + (a * b);
            }
        }
        return "";
    }

    public List<User> selectUser() {
        return jdbcTemplate.query("SELECT * from accounts", BeanPropertyRowMapper.newInstance(User.class));
    }

    public List<UserHistory> selectHistory() {
        return jdbcTemplate.query("SELECT * from histories", BeanPropertyRowMapper.newInstance(UserHistory.class));
    }

    public int update(String name, String current_history, int a, int b, String op) {
        if (name != null)
            this.jdbcTemplate.update("UPDATE histories SET history=? WHERE username=?",
                    current_history + makeString(a, b, op) + ", ", name);
        return cur_sum;
    }

    public void insertHistory(String username) {
        this.jdbcTemplate.update("INSERT INTO histories (username, history) VALUES(?,?)", username, " ");
    }

    public void insertAccount(String username, String add) {
        this.jdbcTemplate.update("INSERT INTO accounts (username, password) VALUES(?,?)", username, add);
    }
}
