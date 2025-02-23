package com.sibsutis.MVCWithDynamicView.controllers.getpostmethods;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ControllerGetPost {
    private final ProductService productService;

    public ControllerGetPost(ProductService productService) {
        this.productService = productService;
    }

    //@GetMapping("/products")
    @RequestMapping("/products")
    public String viewProducts(Model model) {
        var products = productService.findAll();
        model.addAttribute("products", products);
        return "products.html";
    }

    //@PostMapping("/products")
    @RequestMapping(path = "/products", method = RequestMethod.POST)
    public String addProduct(Product p, Model model) {
        productService.addProduct(p);
        var products = productService.findAll();
        model.addAttribute("products", products);
        return "products.html";
    }
}
