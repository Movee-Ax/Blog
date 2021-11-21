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
import org.springframework.web.bind.annotation.PathVariable;
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
    public String blogs(@PageableDefault(size = 8, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        return "admin/blogs";
    }


    @RequestMapping(value = "/blogs/search", method = RequestMethod.POST)
    public String search(@PageableDefault(size = 8, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model){
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

    @RequestMapping(value = "/blogs/{id}/input", method = RequestMethod.GET)
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return "admin/blogs-input";
    }

    @RequestMapping(value = "/blogs", method = RequestMethod.POST)
    public String post(Blog blog, HttpSession httpSession, RedirectAttributes attributes){
        blog.setUser((User)httpSession.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if(null == blog.getId()){
            b = blogService.saveBlog(blog);
        }else{
            b = blogService.updateBlog(blog.getId(), blog);
        }
        if(null == b){
            attributes.addFlashAttribute("message", "发布失败");
        }else{
            attributes.addFlashAttribute("message", "发布成功");
        }
        return "redirect:/admin/blogs";
    }

    @RequestMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }
}
