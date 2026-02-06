package myProject.toyproject.ultraviolet.dto;

import lombok.Data;

@Data
public class UltravioletDto {
    private String ultIndex;

    public void setUltIndex(String ultStage){
        int num = Integer.parseInt(ultStage);

        if (0 <= num && num <= 2){
            this.ultIndex = "자외선 단계 낮음";
        }
        else if (3 <= num && num <= 5){
            this.ultIndex = "자외선 단계 보통";
        }
        else if (6 <= num && num <= 7){
            this.ultIndex = "자외선 단계 높음";
        }
        else if (8 <= num && num <= 10){
            this.ultIndex = "자외선 단계 매우 높음";
        }
        else {
            this.ultIndex = "자외선 단계 위험";
        }
    }
}
