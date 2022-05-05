package com.example.demo.userOperations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
public class HtmlController {
    @Autowired
    private AccountsDao obj;

    @PostMapping(value = "/login")
    @ResponseBody
    public String login(@RequestBody User user) {
        List<User> userList = obj.selectUser();
        for(User curUser : userList){
            if(curUser.getUsername().equals(user.getUsername()) && curUser.getPassword().equals(user.getPassword()) && curUser.getIs_logged() == 0){
                obj.updateUser(user.getUsername(), 1);
                return "You successfully logged in!";
            }
        }
        return "Wrong username or password";
    }

    @PostMapping(value = "/logout")
    @ResponseBody
    public void logout(@RequestBody User user) {
        obj.updateUser(user.getUsername(), 0);
    }

    @PostMapping(value = "/signup")
    @ResponseBody
    public String signup(@RequestBody User user) {
        if (!obj.selectUser().isEmpty() && obj.selectUser().contains(user)) {
            return "This username already exists";
        }
        obj.insertAccount(user.getUsername(), user.getPassword(), 0);
        return "You successfully signed up!";
    }

//    CREATE TABLE accounts (
//            username varchar(255) NOT NULL UNIQUE,
//            password varchar(255) NOT NULL,
//                is_logged int NOT NULL
//    );
}
