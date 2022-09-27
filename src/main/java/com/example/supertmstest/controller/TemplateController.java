package com.example.supertmstest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("courses")
    public String getCourses(){
        return "courses";
    }
}
