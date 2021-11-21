package com.example.blog.controller;
import com.example.blog.NotFountException;
import com.example.blog.po.Tag;
import com.example.blog.po.Type;
import com.example.blog.service.BlogService;
import com.example.blog.service.CommentService;
import com.example.blog.service.TagService;
import com.example.blog.service.TypeService;
import com.example.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
public class IndexController {

    private static final int SEARCHSIZE = 10000;

    private static final int BlOGSIZE = 5;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(@PageableDefault(size = BlOGSIZE, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page", blogService.listBlog(pageable));
        List<Type> types = typeService.listTypeTop(6);
        for(Type type : types){
            type.getBlogs().removeIf(blog -> !blog.isPublished());
        }
        model.addAttribute("types", types);
        List<Tag> tags = tagService.listTagTop(10000);
        for(Tag tag : tags){
            tag.getBlogs().removeIf(blog -> !blog.isPublished());
        }
        model.addAttribute("tags", tags);
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }

    @RequestMapping(value = "/background")
    public String back(){
        return "background";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@PageableDefault(size = SEARCHSIZE, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model, @RequestParam String query){
        model.addAttribute("page", blogService.listBlog("%" + query + "%", pageable));
        model.addAttribute("query", query);
        return "search";
    }
    @RequestMapping(value = "/search/{query}", method = RequestMethod.GET)
    public String search1(@PathVariable String query, @PageableDefault(size = 10, sort = {"createTime"} ,direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page", blogService.listBlog("%" + query + "%", pageable));
        model.addAttribute("query", query);
        return "search";
    }
    @RequestMapping(value = "/blog/{id}", method = RequestMethod.GET)
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

}
