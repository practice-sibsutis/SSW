package com.sibsutis.MVCWithDynamicView.controllers.pathvariables;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerWithPathVariable {
    @RequestMapping("/with_path_variable/{color}")
    public String home(@PathVariable String color,
                       Model page) {
        page.addAttribute("username", "Anton");
        page.addAttribute("color", color);
        return "home.html";
    }
}
