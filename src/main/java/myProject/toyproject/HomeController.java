package myProject.toyproject;

import lombok.RequiredArgsConstructor;
import myProject.toyproject.member.entity.Member;
import myProject.toyproject.member.repository.MemoryMemberRepository;
import myProject.toyproject.security.dto.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal PrincipalDetails principal, Model model){

        if (principal != null){
            model.addAttribute("member", principal.getMember());
        }
        return "home/home";
    }
}
