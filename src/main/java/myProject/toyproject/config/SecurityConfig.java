package myProject.toyproject.config;

import lombok.RequiredArgsConstructor;
import myProject.toyproject.security.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.formLogin(form -> form
                .loginPage("/api/login")
                .loginProcessingUrl("/api/login/process")
                .usernameParameter("loginId")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .failureUrl("/api/login?error=true"));

        http.oauth2Login(oauth2 -> oauth2
                .loginPage("/api/login") // customlogin page
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(customOAuth2UserService))); // OAuth2 로그인 활성화

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/logo/**", "/login/**", "/oauth2/**",
                        "/api/members/add", "/api/login", "/css/**","/error").permitAll()
                .anyRequest().authenticated());

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
