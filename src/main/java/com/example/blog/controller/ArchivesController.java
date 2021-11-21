package com.example.blog.controller;

import com.example.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ArchivesController {

    @Autowired
    BlogService blogService;

    @RequestMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archiveMap", blogService.archivesBlog());
        System.out.println(blogService.archivesBlog().size());
        model.addAttribute("blogCount", blogService.countBlog());
        return "archives";
    }
}
