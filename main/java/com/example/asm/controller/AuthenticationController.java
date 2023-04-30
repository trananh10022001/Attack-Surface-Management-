package com.example.asm.controller;

import com.example.asm.model.UserLoginModel;
import com.example.asm.model.UserRegisterModel;
import com.example.asm.service.IUserService;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthenticationController {
    @Autowired
    IUserService userService;
    @GetMapping(value = "/sign-in")
    public String showSignInForm(Model model){
        model.addAttribute("login", new UserLoginModel());
        return "signin";
    }

    @PostMapping(value = "/sign-in")
    public String showSignInForm(@ModelAttribute UserLoginModel model, HttpSession session, Model tmodel){
        if(userService.login(model.getUsername(), model.getPassword())!=null){
            session.setAttribute("account", model.getUsername());
            return "redirect:domain";
        }else{
            tmodel.addAttribute("errorMessage","Wrong username or password!!!");
            tmodel.addAttribute("login", new UserLoginModel());
            return "signin";
        }
    }
    @GetMapping(value = "/sign-up")
    public String showSignUpForm(Model model){
        model.addAttribute("register", new UserRegisterModel());
        return "signup";
    }
    @PostMapping(value = "/sign-up")
    public String signUp(@ModelAttribute UserRegisterModel umodel, Model model, RedirectAttributes attributes){
        if(umodel.getPassword().equals(umodel.getRePassword())) {
            if (!this.userService.checkUsername(umodel.getUsername())) {
                model.addAttribute("errorMessage", "Username already exists.");
                model.addAttribute("register", new UserRegisterModel());
                return "signup";
            }
            this.userService.registerUser(umodel.getUsername(), umodel.getPassword());
        }else {
            model.addAttribute("errorMessage", "Password unmatch!!!");
        }
        model.addAttribute("login", new UserLoginModel());
        model.addAttribute("successMessage","Register Success");
        return "signin";
    }
    @GetMapping(value = "/sign-out")
    public String signOut(HttpSession session){
        session.removeAttribute("account");
        return "redirect:sign-in";
    }
}
