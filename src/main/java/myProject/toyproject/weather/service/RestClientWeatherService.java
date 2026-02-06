package myProject.toyproject.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myProject.toyproject.weather.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestClientWeatherService {

    private final RestClient weatherRestClient;
    private final ObjectMapper objectMapper;

    @Value("${weather.kma.path}") private String path;
    @Value("${weather.kma.service-key}") private String serviceKey;
    @Value("${weather.kma.nx}") private String nx;
    @Value("${weather.kma.ny}") private String ny;


    public WeatherResponse getCurrentWeather(){

        // 시간 계산 로직
        LocalDateTime now = LocalDateTime.now();
        if (now.getMinute() < 40){
            now = now.minusHours(1);
        }
        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = now.format(DateTimeFormatter.ofPattern("HH00"));

        String response = weatherRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("pageNo", "1")
                        .queryParam("numOfRows", "1000")
                        .queryParam("dataType", "JSON")
                        .queryParam("base_date", baseDate)
                        .queryParam("base_time", baseTime)
                        .queryParam("nx", nx)
                        .queryParam("ny", ny)
                        .build())
                .retrieve()
                .body(String.class);

        JsonNode root = objectMapper.readTree(response);
        JsonNode items = root.get("response")
                .get("body")
                .get("items")
                .get("item");

        WeatherResponse weatherResponse = new WeatherResponse();
        for (JsonNode item : items) {
            String category = item.get("category").asText();
            String obsrValue = item.get("obsrValue").asText();

            if (category.equals("T1H")){
                weatherResponse.setTemperature(obsrValue);
            }
            else if (category.equals("PTY")){
                weatherResponse.setConditionByCode(obsrValue);
            }
        }

        return weatherResponse;
    }
}
