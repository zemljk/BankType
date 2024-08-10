package org.example.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
    static final String INDEX = "index";

    @GetMapping("/")
    public String showHomePage() {
        return INDEX;
    }
}
