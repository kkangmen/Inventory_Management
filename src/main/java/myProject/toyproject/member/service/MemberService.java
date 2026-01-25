package myProject.toyproject.member.service;

import lombok.RequiredArgsConstructor;
import myProject.toyproject.member.dto.MemberCreateRequest;
import myProject.toyproject.member.entity.Member;
import myProject.toyproject.member.repository.MemoryMemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemoryMemberRepository memberRepository;
    private final MemoryMemberRepository memoryMemberRepository;

    public Member save(MemberCreateRequest request){
        Member member = new Member(request.getLoginId(), request.getPassword(), request.getUsername());
        memberRepository.save(member);
        return member;
    }

    public Member findDuplicateId(String loginId){
        Optional<Member> member = memberRepository.findByLoginId(loginId);

        return member.orElse(null);
    }
}
