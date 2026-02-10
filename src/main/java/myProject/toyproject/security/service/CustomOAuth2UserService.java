package myProject.toyproject.security.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myProject.toyproject.member.entity.Member;
import myProject.toyproject.member.repository.MemberRepository;
import myProject.toyproject.security.dto.*;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    /***
     *
     * @param userRequest AccessToken과 Provider 정보가 담겨있다.
     * @return
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest); // Resource Server가 보낸 사용자 정보
        log.info("oAuth2User = {}", oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response;
        if (registrationId.equals("naver")){
            oAuth2Response = new NaverResponse((Map<String, Object>) oAuth2User.getAttributes().get("response"));
        }
        else if(registrationId.equals("google")){
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else {
            oAuth2Response = null;
            return null;
        }

        String role = "ROLE_USER";
        String loginId = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        Member member = memberRepository.findByLoginId(loginId)
                .orElseGet(() -> {
                    return Member.builder()
                            .loginId(loginId)
                            .password("null")
                            .username(oAuth2Response.getName())
                            .email(oAuth2Response.getEmail())
                            .role(role)
                            .provider(oAuth2Response.getProvider())
                            .providerId(oAuth2Response.getProviderId())
                            .build();
                });
        memberRepository.save(member);

        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}
