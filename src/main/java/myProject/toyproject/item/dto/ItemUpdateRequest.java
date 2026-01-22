package myProject.toyproject.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemUpdateRequest {
    private String itemName;
    private Integer price;
    private Integer quantity;
}