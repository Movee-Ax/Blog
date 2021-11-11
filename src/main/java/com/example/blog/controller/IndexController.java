package com.example.blog.controller;
import com.example.blog.NotFountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        System.out.println("-----------index----------------------");
        return "index";
    }

    @RequestMapping(value = "/1", method = RequestMethod.GET)
    public String blog(){
        return "admin/blogs-input";
    }
}
