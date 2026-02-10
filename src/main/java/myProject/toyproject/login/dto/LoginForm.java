package myProject.toyproject.login.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginForm {

    // 스프링 시큐리티는 DTO 객체가 아니라, HTML의 <input name="..."> 값을 직접 읽어가서
    // 필요 없다.
    @NotBlank(message = "로그인 아이디를 입력해주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
