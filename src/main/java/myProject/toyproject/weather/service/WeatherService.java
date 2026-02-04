package myProject.toyproject.weather.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myProject.toyproject.weather.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final WebClient webClient;

    @Value("${weather.kma.url}") private String apiUrl;
    @Value("${weather.kma.service-key}") private String serviceKey;
    @Value("${weather.kma.nx}") private String nx;
    @Value("${weather.kma.ny}") private String ny;

    public WeatherResponse getCurrentWeather(){
        try {
            LocalDateTime now = LocalDateTime.now();
            if (now.getMinute() < 40){
                now = now.minusHours(1);
            }
            String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String baseTime = now.format(DateTimeFormatter.ofPattern("HH00"));

            StringBuilder urlBuilder = new StringBuilder(apiUrl);
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(serviceKey, "UTF-8")); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
            urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /*‘21년 6월 28일 발표*/
            urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /*06시 발표(정시단위) */
            urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점의 X 좌표값*/
            urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점의 Y 좌표값*/

            URI uri = new URI(urlBuilder.toString());

            // API 호출 및 응답
            String response = webClient.get().uri(uri).retrieve().bodyToMono(String.class).block();

            log.info("response = {}", response);

            // JSON 파싱
            JsonNode root = objectMapper.readTree(response);
            JsonNode items = root.path("response").path("body").path("items").path("item");

            WeatherResponse weatherResponse = new WeatherResponse();
            for (JsonNode item: items){
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

        } catch (Exception e) {
            log.error("기상청 API 호출 실패", e);
            return null;
        }
    }



    /***
     * json 형태로 응답 데이터를 받아온다. (restTemplate 방식)
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

    /***
     * 기상 단기예보실황 API를 요청하여 json 형태로 응답데이터를(기온,강수량) 받아온다.
     * restTemplate 방법.
     * @return
     */
    public WeatherResponse getKmaWeather(){
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
}
