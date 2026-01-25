package myProject.toyproject.member.repository;

import myProject.toyproject.member.dto.MemberCreateRequest;
import myProject.toyproject.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Member findById(Long memberId);
    Optional<Member> findByLoginId(String loginId);
    List<Member> findAll();
    void clearStore();
}
