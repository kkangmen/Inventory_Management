package myProject.toyproject.ultraviolet.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myProject.toyproject.ultraviolet.dto.UltravioletDto;
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
public class UltravioletService {

    private final RestClient ultRestClient;
    private final ObjectMapper objectMapper;

    @Value("${weather.ult.path}") private String path;
    @Value("${weather.ult.service-key}") private String serviceKey;
    @Value("${weather.ult.areaNo}") private String areaNo;

    public UltravioletDto getUltraviolet(){

        UltravioletDto ultravioletDto = new UltravioletDto();
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));

        String response = ultRestClient.get()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("pageNo", "1")
                        .queryParam("numOfRows", "10")
                        .queryParam("dataType", "JSON")
                        .queryParam("areaNo", areaNo)
                        .queryParam("time", time)
                        .build())
                .retrieve()
                .body(String.class);


        // 응답 데이터(JSON) 파싱
        JsonNode root = objectMapper.readTree(response);
        JsonNode items = root.get("response").get("body").get("items").get("item");

        for (JsonNode item : items) {
            String curUltIdx = item.get("h0").asText();

            ultravioletDto.setUltIndex(curUltIdx);
        }
        return ultravioletDto;
    }
}
