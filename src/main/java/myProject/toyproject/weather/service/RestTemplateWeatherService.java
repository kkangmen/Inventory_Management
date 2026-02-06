package myProject.toyproject.weather.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myProject.toyproject.weather.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestTemplateWeatherService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${weather.kma.base-url}") private String apiUrl;
    @Value("${weather.kma.service-key}") private String serviceKey;
    @Value("${weather.kma.nx}") private String nx;
    @Value("${weather.kma.ny}") private String ny;

    /***
     * 함수: 초단기실황 API를 호출/ JSON 형태의 응답데이터를 받는다.
     * @return JSON 형태로 응답데이터를 받는다.
     */
    public String jpaGetKmaWeather(){
        try{
            LocalDateTime now = LocalDateTime.now();
            if (now.getMinute() < 40){
                now = now.minusHours(1);
            }
            String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String baseTime = now.format(DateTimeFormatter.ofPattern("HH00"));

            URI uri = makeRequestUrl(baseDate, baseTime);

            return restTemplate.getForObject(uri, String.class);

        }catch (Exception e){
            log.error("기상청 API 호출 실패", e);
            return "{\"error\": \"날씨 정보를 불러오는 데 실패했습니다.\"}";
        }
    }

    /**
     * 함수: 초단기실황 API를 호출/ JSON 형태의 응답 데이터를 받고 DTO에 맞게 파싱한다.
     * @return WeatherResponse DTO
     */
    public WeatherResponse getKmaWeather(){
        try{
            LocalDateTime now = LocalDateTime.now();
            if (now.getMinute() < 40){
                now = now.minusHours(1);
            }
            String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String baseTime = now.format(DateTimeFormatter.ofPattern("HH00"));

            URI uri = makeRequestUrl(baseDate, baseTime);

            String response = restTemplate.getForObject(uri, String.class);

            JsonNode items = objectMapper.readTree(response)
                    .path("response").path("body").path("items").path("item");

            WeatherResponse weatherResponse = new WeatherResponse();
            for (JsonNode item : items){
                String category = item.path("category").asText();
                String obsrValue = item.path("obsrValue").asText();

                if ("T1H".equals(category)){
                    weatherResponse.setTemperature(obsrValue);
                }
                else if ("PTY".equals(category)){
                    weatherResponse.setConditionByCode(obsrValue);
                }
            }

            return weatherResponse;

        } catch (Exception e){
            log.error("기상청 API 호출 실패", e);
            return null;
        }
    }

    private URI makeRequestUrl(String baseDate, String baseTime) throws URISyntaxException {
        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?serviceKey=").append(serviceKey);
        urlBuilder.append("&numOfRows=10&pageNo=1&dataType=JSON");
        urlBuilder.append("&base_date=").append(baseDate);
        urlBuilder.append("&base_time=").append(baseTime);
        urlBuilder.append("&nx=").append(nx).append("&ny=").append(ny);

        return new URI(urlBuilder.toString());
    }
}
