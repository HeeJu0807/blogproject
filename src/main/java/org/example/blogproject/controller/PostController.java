package org.example.blogproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.blogproject.entity.User;
import org.example.blogproject.service.PostService;
import org.example.blogproject.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("/write")
    public String write() {
        return "posting";
    }

    @PostMapping("/write")
    public String PostForm(@RequestParam("title") String title, @RequestParam("content") String content,
                           @RequestParam("images") MultipartFile[] images) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();

        User user = userService.findByUsername(username);

        postService.savePost(title, content, images, user);
        return "redirect:/";
    }

}
