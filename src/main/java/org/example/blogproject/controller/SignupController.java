package org.example.blogproject.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.blogproject.entity.User;
import org.example.blogproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class SignupController {

    private final UserService userService;
    private final HttpSession httpSession;


    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }


    @PostMapping("/signup")
    public String signupForm(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        User existUser = userService.findByUsername(user.getUsername());

        if (existUser != null) {
            bindingResult.rejectValue("username", null, "이미 사용 중인 아이디입니다.");
            return "signup";
        }

        existUser = userService.findByEmail(user.getEmail());
        if (existUser != null) {
            bindingResult.rejectValue("email", null, "이미 사용 중인 이메일입니다.");
            return "signup";
        }

        User registUser = userService.registerUser(user);

        httpSession.setAttribute("username", registUser.getUsername());
        httpSession.setAttribute("showWelcome", true);

        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome(HttpSession session,Model model) {

        // welcome 페이지는 회원가입 후 딱 한번만 보여짐
        // 그 이후로는 접근하려고 해도 home 화면으로 이동

        Boolean showWelcome = (Boolean) httpSession.getAttribute("showWelcome");
        if (showWelcome != null && showWelcome) {
            String username = (String) session.getAttribute("username");
            httpSession.removeAttribute("showWelcome");
            model.addAttribute("username", username);
            return "welcome";
        }else {
            return "redirect:/";
        }
    }
}
