package com.example.blog.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @RequestMapping(value = "/blogs", method = RequestMethod.GET)
    public String blogs(){
        return "admin/blogs";
    }
}
