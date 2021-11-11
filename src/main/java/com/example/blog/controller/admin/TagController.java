package com.example.blog.controller.admin;

import com.example.blog.po.Tag;
import com.example.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    public String lists(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    @RequestMapping(value = "/tags/input", method = RequestMethod.GET)
    public String input(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @RequestMapping(value = "/tags/{id}/input", method = RequestMethod.GET)
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    @RequestMapping(value = "/tags", method = RequestMethod.POST)
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag t = tagService.getTagByName(tag.getName());
        if(null != t){
            result.rejectValue("name", "nameError", "该标签已存在");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag tag1 = tagService.saveTag(tag);
        if(null == tag1){
            attributes.addFlashAttribute("message", "新增失败");
        }else{
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }

    @RequestMapping(value = "/tags/{id}", method = RequestMethod.POST)
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        Tag t = tagService.getTagByName(tag.getName());
        if(null != t){
            result.rejectValue("name", "nameError", "该标签已存在");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag tag1 = tagService.updateTag(id, tag);
        if(null == tag1){
            attributes.addFlashAttribute("message", "更新失败");
        }else{
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }

    @RequestMapping(value = "/tags/{id}/delete",method = RequestMethod.GET)
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }
}
