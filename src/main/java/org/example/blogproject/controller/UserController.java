package org.example.blogproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.blogproject.entity.User;
import org.example.blogproject.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
//
    private final UserService userService;


    @GetMapping("/user-info")
    public String userInfo(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.isAuthenticated()) {
            User user = userService.findByUsername(auth.getName());
            model.addAttribute("user", user);
            return "user-info";
        } else {
            return "redirect:/login";
        }
    }

}
