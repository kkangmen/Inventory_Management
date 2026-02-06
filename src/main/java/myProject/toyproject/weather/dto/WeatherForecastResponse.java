package myProject.toyproject.weather.dto;

import lombok.Data;

@Data
public class WeatherForecastResponse {
    private String fcstDate;
    private String fcstTime;
    private String temperature; // T1H 기온
    private String skyCondition; // SKY 하늘 상태
    private String humidity; // REH 습도

    public void setSkyCondition(String code){
        switch (code){
            case "1":
                this.skyCondition = "맑음☀️";
                break;
            case "3":
                this.skyCondition = "구름 많음🌥️";
                break;
            case "4":
                this.skyCondition = "흐림☁️";
                break;
            default:
                this.skyCondition = "알 수 없음";
                break;
        }
    }

    public String getFormattedDateTime(){
        if (fcstDate == null || fcstTime == null){
            return "";
        }

        String month = fcstDate.substring(4, 6);
        String day = fcstDate.substring(6, 8);
        String hour = fcstTime.substring(0, 2);

        return month + "월 " + day + "일 " + hour + "시 기준";
    }
}
