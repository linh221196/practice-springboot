package com.example.springbootpractice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.springbootpractice.domain.User;
import com.example.springbootpractice.service.UserService;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String userPage(Model model) {
        String text = this.userService.handleUserPage();
        model.addAttribute("text", text);
        model.addAttribute("subText", "text no1");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String adminUserPage(Model model) {
        model.addAttribute("User", new User());
        return "/admin/user/createUser";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("User") User user) {
        System.out.println("create user page runned " + user);
        return "/admin/user/hello";
    }
}
