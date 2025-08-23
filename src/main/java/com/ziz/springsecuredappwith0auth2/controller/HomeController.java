package com.ziz.springsecuredappwith0auth2.controller;


import com.ziz.springsecuredappwith0auth2.model.User;
import com.ziz.springsecuredappwith0auth2.repository.UserRepository;
import com.ziz.springsecuredappwith0auth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class HomeController
{
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        System.out.println("Login page requested");
        return "login";
    }


    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User()); // assuming your model class is named 'user'
        return "register"; // matches register.html
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/login";
    }


    @RequestMapping("/logout")
    public String logoutPage(){
        return "logout";
    }
}

