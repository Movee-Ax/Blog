package com.example.blog.controller;

import com.example.blog.po.Blog;
import com.example.blog.po.Tag;
import com.example.blog.service.BlogService;
import com.example.blog.service.TagService;
import com.example.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.*;
import java.util.List;

@Controller
public class TagShowController {

    private static final int PAGESIZE = 1000;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @RequestMapping("/tags/{id}")
    public String tags(@PageableDefault(size = PAGESIZE, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model){
        List<Tag> tags = tagService.listTagTop(10000);
        if(id == -1){
            id = tags.get(0).getId();
        }
        for(Tag tag : tags){
            tag.getBlogs().removeIf(blog -> !blog.isPublished());
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(id, pageable));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
