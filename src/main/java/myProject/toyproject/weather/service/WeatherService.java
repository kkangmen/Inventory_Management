package myProject.toyproject.weather.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myProject.toyproject.weather.dto.WeatherDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${weather.kma.url}") private String apiUrl;
    @Value("${weather.kma.service-key}") private String serviceKey;
    @Value("${weather.kma.nx}") private String nx;
    @Value("${weather.kma.ny}") private String ny;

    /***
     * json 형태로 응답 데이터를 받아온다.
     * @return
     */
    public String jpaGetKmaWeather(){
        try{
            LocalDateTime now = LocalDateTime.now();
            if (now.getMinute() < 40){
                now = now.minusHours(1);
            }
            String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String baseTime = now.format(DateTimeFormatter.ofPattern("HH00"));

            StringBuilder urlBuilder = new StringBuilder(apiUrl);
            urlBuilder.append("?serviceKey=").append(serviceKey);
            urlBuilder.append("&pageNo=1&numOfRows=1000&dataType=JSON");
            urlBuilder.append("&base_date=").append(baseDate);
            urlBuilder.append("&base_time=").append(baseTime);
            urlBuilder.append("&nx=").append(nx).append("&ny=").append(ny);

            URI uri = new URI(urlBuilder.toString());

            return restTemplate.getForObject(uri, String.class);

        }catch (Exception e){
            log.error("기상청 API 호출 실패", e);
            return "{\"error\": \"날씨 정보를 불러오는 데 실패했습니다.\"}";
        }
    }

    public WeatherDto getKmaWeather(){
        try{
            LocalDateTime now = LocalDateTime.now();
            if (now.getMinute() < 40){
                now = now.minusHours(1);
            }
            String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String baseTime = now.format(DateTimeFormatter.ofPattern("HH00"));

            StringBuilder urlBuilder = new StringBuilder(apiUrl);
            urlBuilder.append("?serviceKey=").append(serviceKey);
            urlBuilder.append("&numOfRows=10&pageNo=1&dataType=JSON");
            urlBuilder.append("&base_date=").append(baseDate);
            urlBuilder.append("&base_time=").append(baseTime);
            urlBuilder.append("&nx=").append(nx).append("&ny=").append(ny);

            URI uri = new URI((urlBuilder.toString()));

            String response = restTemplate.getForObject(uri, String.class);

            JsonNode items = objectMapper.readTree(response)
                    .path("response").path("body").path("items").path("item");

            WeatherDto weatherDto = new WeatherDto();
            for (JsonNode item : items){
                String category = item.path("category").asText();
                String obsrValue = item.path("obsrValue").asText();

                if ("T1H".equals(category)){
                    weatherDto.setTemperature(obsrValue);
                }
                else if ("PTY".equals(category)){
                    weatherDto.setConditionByCode(obsrValue);
                }
            }

            return weatherDto;

        } catch (Exception e){
            log.error("기상청 API 호출 실패", e);
            return null;
        }
    }
}
