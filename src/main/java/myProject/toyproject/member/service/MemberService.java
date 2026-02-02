package myProject.toyproject.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import myProject.toyproject.member.dto.MemberCreateRequest;
import myProject.toyproject.member.entity.Member;
import myProject.toyproject.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member save(MemberCreateRequest request){
        Member member = new Member(request.getLoginId(), request.getPassword(), request.getUsername());
        memberRepository.save(member);
        return member;
    }

    public boolean existsByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).isPresent();
    }
}
