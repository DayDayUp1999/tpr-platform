package com.zyz.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class IndexController {

    @RequestMapping("/")
    public String gologin(){
        return "login";
    }



}
