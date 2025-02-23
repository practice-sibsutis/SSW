package com.sibsutis.MVCWithDynamicView.controllers.simpletemplate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SimpleController {
    @RequestMapping("/simple_controller")
    public String home(Model page) {
        page.addAttribute("username", "Anton");
        page.addAttribute("color", "red");
        return "home.html";
    }
}
