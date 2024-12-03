package com.example.todoapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AnotherController {
    
    @RequestMapping("")
    public String method1() {
        return "html/index";
    }
}
