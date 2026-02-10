package myProject.toyproject.config;

import myProject.toyproject.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /*
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns(("/**"))
                .excludePathPatterns("/", "/api/members/add", "/api/login", "/css/**", "/error",

                            // [Swagger 필수 제외 경로]
                            "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**",

                            // [API 테스트 예외]
                            "/api/items/weather", "/api/ult"
                        );
    }
     */
}
