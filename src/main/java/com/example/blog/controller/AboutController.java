package com.example.blog.controller;


import com.example.blog.po.Tag;
import com.example.blog.po.Type;
import com.example.blog.service.TagService;
import com.example.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AboutController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @RequestMapping("/about")
    public String about(Model model) {

        List<Type> types = typeService.listType();
        for(Type type : types){
            type.getBlogs().removeIf(blog -> !blog.isPublished());
        }
        model.addAttribute("types", types);
        List<Tag> tags = tagService.listTagTop(10000);
        for(Tag tag : tags){
            tag.getBlogs().removeIf(blog -> !blog.isPublished());
        }
        model.addAttribute("tags", tags);
        return "about";
    }
}
