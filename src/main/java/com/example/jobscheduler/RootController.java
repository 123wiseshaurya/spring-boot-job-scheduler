package com.example.jobscheduler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String redirectToFrontend() {
        return "redirect:https://your-frontend-url.railway.app";
    }
}

