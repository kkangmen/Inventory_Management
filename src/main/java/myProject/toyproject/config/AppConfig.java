package myProject.toyproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Value("${weather.kma.base-url}") private String weatherBaseUrl;
    @Value("${weather.ult.base-url}") private String ultBaseUrl;


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

    // 기상청 초단기실황 API
    // HTTP Client 방법 중 restClient를 스프링 빈으로 등록
    @Bean
    public RestClient weatherRestClient(){
        return RestClient.builder()
                .baseUrl(weatherBaseUrl)
                .build();
    }

    // 기상청 자외선지수조회 API
    @Bean
    public RestClient ultRestClient(){
        return RestClient.builder()
                .baseUrl(ultBaseUrl)
                .build();
    }
}
