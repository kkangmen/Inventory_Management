package myProject.toyproject.login.service;

import lombok.RequiredArgsConstructor;
import myProject.toyproject.login.dto.LoginForm;
import myProject.toyproject.member.entity.Member;
import myProject.toyproject.member.repository.MemberRepository;
import myProject.toyproject.member.repository.MemoryMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class LoginService {

    private final MemberRepository memberRepository;

    // 스프링 시큐리티가 알아서 암호화된 비번을 비교해주어서
    // 더 이상 비밀번호를 직접 비교 할 필요가 없다.

    public Member login(LoginForm form){
        Optional<Member> findMember = memberRepository.findByLoginId(form.getLoginId());
        Member member = findMember.orElse(null);

        if (member == null){
            return null;
        }

        if (member.getPassword().equals(form.getPassword())){
            return member;
        }
        return null;
    }
}
