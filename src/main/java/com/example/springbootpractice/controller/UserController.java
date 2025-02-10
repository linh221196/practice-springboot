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

import com.example.springbootpractice.domain.Users;
import com.example.springbootpractice.repository.UserRepository;
import com.example.springbootpractice.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
      
    }


    @RequestMapping("/")
    public String userPage(Model model,@ModelAttribute("User") Users user) {
        String text = this.userService.handleUserPage();
        model.addAttribute("text", text);
        model.addAttribute("subText", "text no1");
        List<Users> test = this.userService.handleGetAllUser();
        List<Users> test1 = this.userService.handleGetAllUserByEmailAndPhone(test.get(0).getEmail(), test.get(0).getPhone());
        System.out.println(test);
        System.out.println("\n test1 " + test1);
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String adminUserPage(Model model) {
        List<Users> users = this.userService.handleGetAllUser();
        System.out.println(users);
        model.addAttribute("User", users);
        return "/admin/user/usersList";
    }

    @RequestMapping("/admin/user/create")
    public String adminCreateUserPage(Model model) {
        model.addAttribute("User", new Users());
        return "/admin/user/createUser";
    }

    @RequestMapping(value = "/admin/user/create-result", method = RequestMethod.POST)
    public String createUserResultPage(Model model, @ModelAttribute("User") Users user) {
        System.out.println("create user page runned " + user);
        userService.handleSaveCreatedUser(user);
        List<Users> test = new ArrayList<Users>();
        test = userService.handleGetAllUser();
        System.out.println(test);
        return "/admin/user/createUserResult";
    }

    @RequestMapping("/admin/user/edit/{id}")
    public String adminEditUserPage(Model model,@PathVariable long id,@ModelAttribute("User") Users editUser) {
       editUser = this.userService.findById(id).get();
       System.out.println(editUser);
      model.addAttribute("editUser", editUser);
        return "/admin/user/editUser";
    }

    @RequestMapping(value="/admin/user/edit/{id}", method=RequestMethod.POST)
    public String adminEditUserResultPage(Model model,@PathVariable long id,@ModelAttribute("User") Users editUser) {
       Users updatedUser = this.userService.findById(editUser.getId()).get();
       if(updatedUser != null){
        updatedUser.setName(editUser.getName());
        updatedUser.setPhone(editUser.getPhone());
       }
       this.userService.handleSaveCreatedUser(updatedUser);
       System.out.println("adminEditUserResultPage" + editUser);
      model.addAttribute("editUser", editUser);
        return "redirect:/admin/user";
    }

    @RequestMapping(value="/admin/user/delete/{id}", method=RequestMethod.POST)
    public String handleDeleteUserById(@PathVariable long id) {
         this.userService.handleDeleteUserById(id);
         return "redirect:/admin/user";
    }
    
}
