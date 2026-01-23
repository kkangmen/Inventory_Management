package myProject.toyproject.member.controller;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myProject.toyproject.member.dto.MemberCreateRequest;
import myProject.toyproject.member.entity.Member;
import myProject.toyproject.member.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("form", new MemberCreateRequest());
        return "member/addForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute("form") MemberCreateRequest form, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "member/addForm";
        }
        memberRepository.save(form);
        return "redirect:/";
    }
}
