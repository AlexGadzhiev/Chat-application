package com.example.demo.userOperations;

import com.example.demo.userOperations.User;
import com.example.demo.userOperations.UserHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountsDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String findHistory(String username){
//        List<UserHistory> histories = this.selectHistory();
//        for(UserHistory element : histories){
//            if(element.getUsername().equals(username)){
//                return element.getHistory();
//            }
//        }
        return "";
    }

    public List<User> selectUser() {
        return jdbcTemplate.query("SELECT * from accounts", BeanPropertyRowMapper.newInstance(User.class));
    }

    public List<UserHistory> selectHistory() {
        return jdbcTemplate.query("SELECT * from histories", BeanPropertyRowMapper.newInstance(UserHistory.class));
    }

    public void updateUser(String username, int state) {
        if(username != null){
            this.jdbcTemplate.update("UPDATE accounts SET is_logged=? WHERE username=?", state, username);
        }
//        if (name != null)
//            this.jdbcTemplate.update("UPDATE histories SET history=? WHERE username=?",
//                    current_history + makeString(a, b, op) + ", ", name);
//        return cur_sum;
    }

    public void updateHistory(String username){}

    public void insertAccount(String username, String add, int state) {
        this.jdbcTemplate.update("INSERT INTO accounts (username, password, is_logged) VALUES(?,?,?)", username, add, state);
    }


}
