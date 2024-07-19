package org.example.blogproject.controller;


import org.example.blogproject.entity.Post;
import org.example.blogproject.entity.User;
import org.example.blogproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller

public class HomeController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String home(Model model) {

        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            // 인증된 사용자의 정보를 가져와서 모델에 추가
            String username = auth.getName();
            model.addAttribute("user", username);
        }
        return "home";
    }


}
