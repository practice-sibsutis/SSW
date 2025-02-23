package com.sibsutis.MVCWithDynamicView.controllers.withparams;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControllerWithParams {
    @RequestMapping("/controller_with_params")
    public String home(@RequestParam(required = false) String name,
                       @RequestParam(required = false) String color,
                       Model page) {
        page.addAttribute("username", name);
        page.addAttribute("color", color);
        return "home.html";
    }
}
