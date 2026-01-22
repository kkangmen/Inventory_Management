package myProject.toyproject.item.dto;

import lombok.Data;

@Data
public class ItemUpdateRequest {
    private String itemName;
    private Integer price;
    private Integer quantity;
}
