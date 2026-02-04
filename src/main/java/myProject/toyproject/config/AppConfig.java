package myProject.toyproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    // HTTP Client 방법 중 restTemplate을 스프링 빈으로 등록
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    // HTTP Client 방법 중 webClient를 스프링 빈으로 등록
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
