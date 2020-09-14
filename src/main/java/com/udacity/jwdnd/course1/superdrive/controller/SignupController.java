package com.udacity.jwdnd.course1.superdrive.controller;

import com.udacity.jwdnd.course1.superdrive.model.User;
import com.udacity.jwdnd.course1.superdrive.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private UserService userService;
    
    public SignupController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    public String signUpView() { return "/signup"; }
    
    @PostMapping
    public String userSignup(User user, Model model) {
        String signupError = null;
        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "Username is unavailable!";
        }
        if (signupError == null) {
            int userId = userService.createUser(user);
            if (userId < 0) {
                signupError = "Error occurred during sign up!";
            }
        }
        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
            return "login";
        } else {
            model.addAttribute("signupError", signupError);
        }
        return "signup";
    }
    
}
