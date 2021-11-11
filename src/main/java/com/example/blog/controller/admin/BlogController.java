package com.example.blog.controller.admin;


import com.example.blog.po.Blog;
import com.example.blog.po.User;
import com.example.blog.service.BlogService;
import com.example.blog.service.TagService;
import com.example.blog.service.TypeService;
import com.example.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/blogs", method = RequestMethod.GET)
    public String blogs(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model){

        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        return "admin/blogs";
    }


    @RequestMapping(value = "/blogs/search", method = RequestMethod.POST)
    public String search(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model){
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        return "admin/blogs :: blogList";
    }

    @RequestMapping(value = "/blogs/input", method = RequestMethod.GET)
    public String input(Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("blog", new Blog());
        return "admin/blogs-input";
    }

    @RequestMapping(value = "/blogs", method = RequestMethod.POST)
    public String post(Blog blog, HttpSession httpSession, RedirectAttributes attributes){
        blog.setUser((User)httpSession.getAttribute("user"));
        Blog b = blogService.saveBlog(blog);
        if(null == b){
            attributes.addFlashAttribute("message", "发布失败");
        }else{
            attributes.addFlashAttribute("message", "发布成功");
        }
        return "redirect:admin/blogs";
    }
}
