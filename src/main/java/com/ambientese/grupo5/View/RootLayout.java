package com.ambientese.grupo5.View;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RootLayout {
    @GetMapping("/")
    public ModelAndView root() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("root");
        return modelAndView;
    }
}
