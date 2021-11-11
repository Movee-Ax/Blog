package com.example.blog.controller.admin;

import com.example.blog.po.Type;
import com.example.blog.service.TypeService;
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
public class TypeController {

    @Autowired
    private TypeService typeService;

    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public String lists(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @RequestMapping(value = "/types/input", method = RequestMethod.GET)
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @RequestMapping(value = "/types/{id}/input", method = RequestMethod.GET)
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    @RequestMapping(value = "/types", method = RequestMethod.POST)
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type t = typeService.getTypeByName(type.getName());
        if(null != t){
            result.rejectValue("name", "nameError", "该分类已存在");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type type1 = typeService.saveType(type);
        if(null == type1){
            attributes.addFlashAttribute("message", "新增失败");
        }else{
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    @RequestMapping(value = "/types/{id}", method = RequestMethod.POST)
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        Type t = typeService.getTypeByName(type.getName());
        if(null != t){
            result.rejectValue("name", "nameError", "该分类已存在");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type type1 = typeService.updateType(id, type);
        if(null == type1){
            attributes.addFlashAttribute("message", "更新失败");
        }else{
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    @RequestMapping(value = "/types/{id}/delete",method = RequestMethod.GET)
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
