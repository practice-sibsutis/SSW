package com.sibsutis.MVCWithDynamicView.controllers.beanscope;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final LoginProcessor loginProcessor;

    public LoginController(LoginProcessor loginProcessor) {
        this.loginProcessor = loginProcessor;
    }
    @GetMapping("/login")
    public String loginGet() {
        return "login.html";
    }

    @PostMapping("/login")
    public String loginPost(
            @RequestParam String username,
            @RequestParam String password,
            Model model
    ) {
        loginProcessor.setPassword(password);
        loginProcessor.setUsername(username);
        boolean loggedIn = loginProcessor.login();

        if (loggedIn) {
            return "redirect:/main";
        }

        model.addAttribute("message", "Login Failed!");
        return "login.html";
    }
}
