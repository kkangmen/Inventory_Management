package myProject.toyproject.member.entity;

import lombok.Data;

@Data
public class Member {
    private Long memberId;
    private String loginId;
    private String password;
    private String username;

    public Member(String loginId, String password, String username) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
    }
}
