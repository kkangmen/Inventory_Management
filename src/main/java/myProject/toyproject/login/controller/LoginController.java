package myProject.toyproject.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myProject.toyproject.login.dto.LoginForm;
import myProject.toyproject.login.service.LoginService;
import myProject.toyproject.member.entity.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class LoginController {

    // private final LoginService loginService;

    @GetMapping("/api/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exeption,
                            Model model){

        model.addAttribute("loginForm", new LoginForm());

        if (error != null){
            model.addAttribute("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
        }

        return "home/loginForm";
    }

    /* Spring Security가 ?error 파라미터를 붙여서 리다이렉트 시켜준다.
    @PostMapping
    public String login(@Valid @ModelAttribute("loginForm") LoginForm form,
                        BindingResult bindingResult, HttpServletRequest request,
                        @RequestParam(defaultValue = "/") String redirectURL){

        if (bindingResult.hasErrors()){
            log.info("error = {}", bindingResult);
            return "home/loginForm";
        }

        Member loginMember = loginService.login(form);
        if (loginMember == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");

            return "home/loginForm";
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("loginMember", loginMember);

        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session != null){
            session.invalidate();;
        }

        return "redirect:/";
    }
     */
}
