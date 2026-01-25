package myProject.toyproject;

import lombok.RequiredArgsConstructor;
import myProject.toyproject.member.entity.Member;
import myProject.toyproject.member.repository.MemoryMemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private MemoryMemberRepository memberRepository;

    @GetMapping("/")
    public String home(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
                       Model model){
        if (loginMember != null){
            model.addAttribute("member", loginMember);
        }
        return "home/home";
    }
}
