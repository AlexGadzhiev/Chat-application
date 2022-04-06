package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class HtmlController {
    @Autowired
    private AccountsDaoInterface obj;
    private String lastUser;
    private String current_history;

    @GetMapping(value = "/calculator")
    @ResponseBody
    public ResponseEntity<?> getInfo(@RequestParam("a") Integer a, @RequestParam("b") Integer b,
                                     @RequestParam("action") String action) throws IOException {
        current_history = obj.findHistory(lastUser);
        return ResponseEntity.ok().body(obj.update(lastUser, current_history, a, b, action));
    }

    @GetMapping(value = "/history")
    @ResponseBody
    public ResponseEntity<?> getHistory() {
        if(lastUser == null){
            return ResponseEntity.ok().body("Sorry, don't have one");
        }
        current_history = obj.findHistory(lastUser);
        if(current_history.isEmpty())
            return ResponseEntity.ok().body(null);
        return ResponseEntity.ok().body(current_history.substring(0, current_history.length() - 2));
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody User user) {
        List<User> userList = obj.selectUser();
        if (lastUser != null) {
            if (lastUser.equals(user.getUsername())) {
                return ResponseEntity.ok().body("You're already logged in!");
            } else if (!userList.contains(user)) {
                return ResponseEntity.ok().body("Wrong username or password");
            }
            return ResponseEntity.ok().body("Try again");
        }
        if(!userList.isEmpty() && userList.contains(user)){
            lastUser = user.getUsername();
            return ResponseEntity.ok().body("You have successfully logged in");
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
        if (!obj.selectUser().isEmpty() && obj.selectUser().contains(user)) {
            return ResponseEntity.ok().body("You're already signed up!");
        }
        obj.insertHistory(user.getUsername());
        obj.insertAccount(user.getUsername(), user.getPassword());
        return ResponseEntity.ok().body("You have successfully signed up!");
    }

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
