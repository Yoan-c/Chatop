package com.example.rentals.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DocummentationController {
    @RequestMapping("/documentation")
    public String getRedirectUrl() {
        return "redirect:swagger-ui.html";
    }
    @RequestMapping("/documentation-api")
    public String getRedirectJsonUrl() {
        return "redirect:/v3/api-docs";
    }
}
