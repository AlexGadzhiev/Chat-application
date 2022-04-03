package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
public class HtmlController {
    private HashMap<String, List<String>> mp = new HashMap<>();
    private String lastUser;

    private String makeString(int a, int b, int total, String op) {
        return a + " " + op + " " + b + " = " + total;
    }

    @GetMapping(value = "/calculator")
    @ResponseBody
    public ResponseEntity<?> getInfo(@RequestParam("a") Integer a, @RequestParam("b") Integer b,
                                     @RequestParam("action") String action) throws IOException {
        com.example.demo.Operation obj = new com.example.demo.Operation(a, b);
        switch (action) {
            case "plus":
                if (lastUser != null)
                    mp.get(lastUser).add(makeString(a, b, a + b, "+"));
                return ResponseEntity.ok().body(obj.plus());
            case "minus":
                if (lastUser != null)
                    mp.get(lastUser).add(makeString(a, b, a - b, "-"));
                return ResponseEntity.ok().body(obj.minus());
            case "div":
                if (lastUser != null)
                    mp.get(lastUser).add(makeString(a, b, a / b, "/"));
                return ResponseEntity.ok().body(obj.div());
            case "times":
                if (lastUser != null)
                    mp.get(lastUser).add(makeString(a, b, a * b, "*"));
                return ResponseEntity.ok().body(obj.times());
        }
        return ResponseEntity.ok().body(null);
    }

    @GetMapping(value = "/history")
    @ResponseBody
    public ResponseEntity<?> getHistory(@RequestParam("state") boolean state) {
        if (state && lastUser != null) {
            return ResponseEntity.ok().body("Username's name is: " + lastUser + '\n' +
                    "Username's history of operations: " + mp.get(lastUser));
        } else if (lastUser == null) {
            return ResponseEntity.ok().body("Sorry, don't have one");
        }
        return ResponseEntity.ok().body("Try again");
    }

    @GetMapping(value = "/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestParam("name") String name) {
        if(lastUser == null){
            lastUser = name;
        }
        return ResponseEntity.ok().body("Try again");
    }

    @GetMapping(value = "/logout")
    @ResponseBody
    public ResponseEntity<?> logout(@RequestParam("name") String name) {
        // спроси Магу про удалении истории при logout
        if(!name.equals(lastUser))
            return ResponseEntity.ok().body("Try again");
        else {
            mp.get(lastUser).clear();
            lastUser = null;
        }
        return ResponseEntity.ok().body(null);
    }
}
