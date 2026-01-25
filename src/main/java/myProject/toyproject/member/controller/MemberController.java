package myProject.toyproject.member.controller;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myProject.toyproject.member.dto.MemberCreateRequest;
import myProject.toyproject.member.entity.Member;
import myProject.toyproject.member.repository.MemberRepository;
import myProject.toyproject.member.service.MemberService;
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

    private final MemberService memberService;

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

        Member duplicateId = memberService.findDuplicateId(form.getLoginId());
        if (duplicateId != null){
            bindingResult.rejectValue("loginId", "duplicateId", "이미 존재하는 아이디 입니다.");
            return "member/addForm";
        }

        memberService.save(form);
        return "redirect:/";
    }
}
