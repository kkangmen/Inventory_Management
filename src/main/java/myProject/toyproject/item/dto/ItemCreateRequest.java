package myProject.toyproject.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ItemCreateRequest {

    @NotBlank(message = "상품명을 입력해주세요.")
    private String itemName;

    @NotNull(message = "가격을 입력해주세요.")
    private Integer price;

    @NotNull(message = "수량을 입력해주세요.")
    private Integer quantity;

    public ItemCreateRequest(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
