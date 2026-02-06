package myProject.toyproject.weather.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myProject.toyproject.weather.dto.WeatherForecastResponse;
import myProject.toyproject.weather.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebClientWeatherService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${weather.kma.base-url}") private String apiUrl;
    @Value("${weather.fma.url}") private String apiUrl2;
    @Value("${weather.kma.service-key}") private String serviceKey;
    @Value("${weather.kma.nx}") private String nx;
    @Value("${weather.kma.nx}") private String ny;

    /***
     * 함수명: 초단기실황 API 호출/응답 데이터 받기
     * @return WeatherResponse DTO
     */
    public WeatherResponse getCurrentWeather(){
        try {
            LocalDateTime now = LocalDateTime.now();
            if (now.getMinute() < 40){
                now = now.minusHours(1);
            }
            String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String baseTime = now.format(DateTimeFormatter.ofPattern("HH00"));

            StringBuilder urlBuilder = makeRequestURL(apiUrl, baseDate, baseTime);

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
     * 함수명: 초단기예보 API 호출/응답 데이터 받기
     * @return WeatherForecastResponse DTO
     */
    public WeatherForecastResponse getWeatherForecast() throws UnsupportedEncodingException, URISyntaxException {
        LocalDateTime now = LocalDateTime.now();
        if (now.getMinute() < 45){
            now = now.minusHours(1);
        }
        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = now.format(DateTimeFormatter.ofPattern("HH00"));

        LocalDateTime targetDateTime = now.plusHours(6);
        String targetDateStr = targetDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String targetTimeStr = targetDateTime.format(DateTimeFormatter.ofPattern("HH00"));

        StringBuilder urlBuilder = makeRequestURL(apiUrl2, baseDate, baseTime);

        URI uri = new URI(urlBuilder.toString());

        String response = webClient.get().uri(uri).retrieve().bodyToMono(String.class).block();
        JsonNode root = objectMapper.readTree(response);
        JsonNode items = root.path("response").path("body").path("items").path("item");

        WeatherForecastResponse weatherForecastResponse = new WeatherForecastResponse();
        for (JsonNode item : items) {
            String category = item.path("category").asText();
            String fcstValue = item.path("fcstValue").asText();

            String fcstDate = item.path("fcstDate").asText();
            String fcstTime = item.path("fcstTime").asText();

            if (fcstDate.equals(targetDateStr) && fcstTime.equals(targetTimeStr)) {

                // 날짜 정보는 한 번만 세팅
                if (weatherForecastResponse.getFcstDate() == null) {
                    weatherForecastResponse.setFcstDate(fcstDate);
                    weatherForecastResponse.setFcstTime(fcstTime);
                }

                if (category.equals("T1H")) {
                    weatherForecastResponse.setTemperature(fcstValue);
                } else if (category.equals("SKY")) {
                    weatherForecastResponse.setSkyCondition(fcstValue);
                } else if (category.equals("REH")) {
                    weatherForecastResponse.setHumidity(fcstValue);
                }
            }
        }

        return weatherForecastResponse;
    }

    /***
     * 함수: 기상청 단기예보 API 호출을 위한 requestURL 생성
     * @param apiUrl: "초단기예보", "초단기실황" 구분을 위한 URL
     * @param baseDate: "기준 날짜"
     * @param baseTime: "기준 시간"
     * @return API에 요청할 URL
     * @throws UnsupportedEncodingException 예외 처리
     */
    private StringBuilder makeRequestURL(String apiUrl, String baseDate, String baseTime) throws UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + URLEncoder.encode(serviceKey, "UTF-8")); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /*06시 발표(정시단위) */
        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점의 Y 좌표값*/
        return urlBuilder;
    }
}
