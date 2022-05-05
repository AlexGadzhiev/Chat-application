package com.example.demo.userOperations;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    @GetMapping("/index")
    public String index(@RequestParam(name="username", required=false, defaultValue="World") String username,
                        @RequestParam(name="numberOfChannel", required=false, defaultValue="0") int channel, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("numberOfChannel", channel);
        return "index";
    }
}
