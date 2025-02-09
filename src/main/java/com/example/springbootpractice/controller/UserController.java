package com.example.springbootpractice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.springbootpractice.domain.User;
import com.example.springbootpractice.repository.UserRepository;
import com.example.springbootpractice.service.UserService;

@Controller
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
      
    }


    @RequestMapping("/")
    public String userPage(Model model,@ModelAttribute("User") User user) {
        String text = this.userService.handleUserPage();
        model.addAttribute("text", text);
        model.addAttribute("subText", "text no1");
        List<User> test = this.userService.handleGetAllUser();
        List<User> test1 = this.userService.handleGetAllUserByEmailAndPhone(test.get(0).getEmail(), test.get(0).getPhone());
        System.out.println(test);
        System.out.println("\n test1 " + test1);
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String adminUserPage(Model model) {
        List<User> users = this.userService.handleGetAllUser();
        System.out.println(users);
        model.addAttribute("User", users);
        return "/admin/user/usersList";
    }

    @RequestMapping("/admin/user/create")
    public String adminCreateUserPage(Model model) {
        model.addAttribute("User", new User());
        return "/admin/user/createUser";
    }

    @RequestMapping(value = "/admin/user/create-result", method = RequestMethod.POST)
    public String createUserResultPage(Model model, @ModelAttribute("User") User user) {
        System.out.println("create user page runned " + user);
        userService.handleSaveCreatedUser(user);
        List<User> test = new ArrayList<User>();
        test = userService.handleGetAllUser();
        System.out.println(test);
        return "/admin/user/createUserResult";
    }

    @RequestMapping("/admin/user/edit/{id}")
    public String adminEditUserPage(Model model,@PathVariable long id,@ModelAttribute("User") User editUser) {
       editUser = this.userService.findById(id).get();
       System.out.println(editUser);
      model.addAttribute("editUser", editUser);
        return "/admin/user/editUser";
    }
}
