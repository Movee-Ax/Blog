package com.example.blog.controller;

import com.example.blog.po.Blog;
import com.example.blog.po.Tag;
import com.example.blog.po.Type;
import com.example.blog.service.BlogService;
import com.example.blog.service.TypeService;
import com.example.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class TypeShowController {

    private static final int PAGESIZE = 1000;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @RequestMapping("/types/{id}")
    public String types(@PageableDefault(size = PAGESIZE, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model){
        List<Type> types = typeService.listTypeTop(10000);
        if(id == -1){
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        for(Type type : types){
            type.getBlogs().removeIf(blog -> !blog.isPublished());
        }
        model.addAttribute("types", types);
        Page<Blog> pages = blogService.listPageBlog(pageable, blogQuery);
        model.addAttribute("page", pages);
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
