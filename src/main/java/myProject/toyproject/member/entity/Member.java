package myProject.toyproject.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true, nullable = false)
    private String loginId; // 통합 ID (일반: 입력값, 소셜: "google EJGek.."

    private String password; // (일반: 암호화된 비번, 소셜: "N/A")

    private String username; // 사용자 실명

    private String email;

    private String role;

    private String provider; // google, naver (일반 회원은 null)
    private String providerId; // sub, id (일반 회원은 null)

    @Builder
    public Member(String loginId, String password, String username, String email, String role, String provider, String providerId) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}
