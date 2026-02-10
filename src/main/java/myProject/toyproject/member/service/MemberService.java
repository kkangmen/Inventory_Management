package myProject.toyproject.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import myProject.toyproject.member.dto.MemberCreateRequest;
import myProject.toyproject.member.entity.Member;
import myProject.toyproject.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Long save(MemberCreateRequest memberCreateRequest){
        // 일반 회원은 provider, providerId 정보 null
        // 권한은 기본적으로 USER 부여
        Member member = Member.builder().loginId(memberCreateRequest.getLoginId())
                .password(passwordEncoder.encode(memberCreateRequest.getPassword()))
                .username(memberCreateRequest.getUsername())
                .email(memberCreateRequest.getEmail())
                .role("ROLE_USER")
                .provider(null)
                .providerId(null)
                .build();

        memberRepository.save(member);
        return member.getMemberId();
    }

    public boolean existsByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).isPresent();
    }
}
