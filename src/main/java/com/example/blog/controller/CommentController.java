package com.example.blog.controller;

import com.example.blog.po.Comment;
import com.example.blog.po.User;
import com.example.blog.service.BlogService;
import com.example.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/comments/{blogId}", method = RequestMethod.GET)
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @RequestMapping(value = "/comments", method = RequestMethod.POST)
    public String post(Comment comment, HttpSession session){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if(null != user){
            comment.setAvatar("/images/me.jpg");
            comment.setAdminComment(true);
            comment.setNickname(user.getNickname());
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
