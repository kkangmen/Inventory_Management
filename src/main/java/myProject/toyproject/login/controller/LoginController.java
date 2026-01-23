package myProject.toyproject.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myProject.toyproject.login.dto.LoginForm;
import myProject.toyproject.login.service.LoginService;
import myProject.toyproject.member.entity.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginController {

    private final LoginService loginService;

    @GetMapping
    public String loginForm(@ModelAttribute("loginForm") LoginForm form){
        return "home/loginForm";
    }

    @PostMapping
    public String login(@Valid @ModelAttribute("loginForm") LoginForm form,
                        BindingResult bindingResult, HttpServletRequest request){

        if (bindingResult.hasErrors()){
            return "home/loginForm";
        }

        Member loginMember = loginService.login(form);
        if (loginMember == null){
            return "home/loginForm";
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("loginMember", loginMember);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session != null){
            session.invalidate();;
        }

        return "redirect:/";
    }
}
