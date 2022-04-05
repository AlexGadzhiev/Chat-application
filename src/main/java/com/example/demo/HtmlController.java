package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@RestController
public class HtmlController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String lastUser;
    private String current_history;

    private String makeString(int a, int b, int total, String op) {
        return a + " " + op + " " + b + " = " + total;
    }

    @GetMapping(value = "/calculator")
    @ResponseBody
    public ResponseEntity<?> getInfo(@RequestParam("a") Integer a, @RequestParam("b") Integer b,
                                     @RequestParam("action") String action) throws IOException {
        List<UserHistory> histories = jdbcTemplate.query("SELECT * from histories", BeanPropertyRowMapper.newInstance(UserHistory.class));
        for(UserHistory user : histories){
            if(user.getUsername().equals(lastUser)){
                current_history = user.getHistory();
                break;
            }
        }
        com.example.demo.Operation obj = new com.example.demo.Operation(a, b);
        switch (action) {
            case "plus":
                if (lastUser != null)
                    jdbcTemplate.update("UPDATE histories SET history=? WHERE username=?",
                            current_history + makeString(a, b, a + b, "+") + ", ", lastUser);
                return ResponseEntity.ok().body(obj.plus());
            case "minus":
                if (lastUser != null)
                    jdbcTemplate.update("UPDATE histories SET history=? WHERE username=?",
                            current_history + makeString(a, b, a - b, "-") + ", ", lastUser);
                return ResponseEntity.ok().body(obj.minus());
            case "div":
                if (lastUser != null)
                    jdbcTemplate.update("UPDATE histories SET history=? WHERE username=?",
                            current_history + makeString(a, b, a / b, "/") + ", ", lastUser);
                return ResponseEntity.ok().body(obj.div());
            case "times":
                if (lastUser != null)
                    jdbcTemplate.update("UPDATE histories SET history=? WHERE username=?",
                            current_history + makeString(a, b, a * b, "*") + ", ", lastUser);
                return ResponseEntity.ok().body(obj.times());
        }
        return ResponseEntity.ok().body(null);
    }

    @GetMapping(value = "/history")
    @ResponseBody
    public ResponseEntity<?> getHistory() {
        if(lastUser == null){
            return ResponseEntity.ok().body("Sorry, don't have one");
        }
        List<UserHistory> histories = jdbcTemplate.query("SELECT * from histories", BeanPropertyRowMapper.newInstance(UserHistory.class));
        for(UserHistory user : histories){
            if(user.getUsername().equals(lastUser)){
                current_history = user.getHistory();
                break;
            }
        }
        if(current_history.isEmpty())
            return ResponseEntity.ok().body(null);
        return ResponseEntity.ok().body(current_history.substring(0, current_history.length() - 2));
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody User user) {
        List<User> userList = jdbcTemplate.query("SELECT * from accounts", BeanPropertyRowMapper.newInstance(User.class));
        if (lastUser != null) {
            if (lastUser.equals(user.getUsername())) {
                return ResponseEntity.ok().body("You're already logged in!");
            } else if (!userList.contains(user)) {
                return ResponseEntity.ok().body("Wrong username or password");
            }
            return ResponseEntity.ok().body("Try again");
        }
        for(User curUser : userList){
            if(curUser.getUsername().equals(user.getUsername()) && user.getPassword().equals(curUser.getPassword())){
                lastUser = user.getUsername();
                return ResponseEntity.ok().body("You have successfully logged in");
            }
        }
        return ResponseEntity.ok().body("Wrong login or password");
    }

    @GetMapping(value = "/logout")
    @ResponseBody
    public ResponseEntity<?> logout(@RequestParam("name") String name) {
        if (name == null || !name.equals(lastUser))
            return ResponseEntity.ok().body("Try again");
        else {
            lastUser = null;
            return ResponseEntity.ok().body("You have successfully logged out");
        }
    }

    @PostMapping(value = "/signup")
    @ResponseBody
    public ResponseEntity<?> signup(@RequestBody User user) {
        List<User> userList = jdbcTemplate.query("SELECT * from accounts", BeanPropertyRowMapper.newInstance(User.class));
        if (userList.contains(user)) {
            return ResponseEntity.ok().body("You're already signed up!");
        }
        jdbcTemplate.update("INSERT INTO histories (username, history) VALUES(?,?)",
                user.getUsername(), " ");
        jdbcTemplate.update("INSERT INTO accounts (username, password) VALUES(?,?)",
                user.getUsername(), user.getPassword());
        return ResponseEntity.ok().body("You have successfully signed up!");
    }

//    @GetMapping(value = "/run")
//    @ResponseBody
//    public ResponseEntity<?> run(){
//        List<User> userList = jdbcTemplate.query("SELECT * from accounts", BeanPropertyRowMapper.newInstance(User.class));
//        return ResponseEntity.ok().body(null);
//    }

//    CREATE TABLE accounts (
//            user_id serial PRIMARY KEY,
//            username VARCHAR ( 50 ) UNIQUE NOT NULL,
//            password VARCHAR ( 50 ) NOT NULL,
//            email VARCHAR ( 255 ) UNIQUE NOT NULL,
//            created_on TIMESTAMP NOT NULL,
//            last_login TIMESTAMP
//    );

//    CREATE TABLE histories (
//            user_id INT NULL,
//            username VARCHAR ( 50 ) UNIQUE NOT NULL,
//            history VARCHAR ( 50 ) NOT NULL
//    );
}
