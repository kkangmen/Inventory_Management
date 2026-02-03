package myProject.toyproject.weather.dto;

import lombok.Data;

@Data
public class WeatherDto {
    private String temperature; // 온도 (T1H)
    private String condition; // 날씨 상태 (맑음, 비 등 - PTY)

    public void setConditionByCode(String ptyCode){
        switch(ptyCode){
            case "0":
                this.condition = "맑음☀️/흐림☁️";
                break;
            case "1":
                this.condition = "비🌧️";
                break;
            case "2":
                this.condition = "비/눈";
                break;
            case "3":
                this.condition = "눈";
                break;
            case "5":
                this.condition = "빗방울";
                break;
            default:
                this.condition = "알 수 없음";
                break;
        }
    }
}
