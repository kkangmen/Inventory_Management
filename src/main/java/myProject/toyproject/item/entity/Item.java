package myProject.toyproject.item.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import myProject.toyproject.member.entity.Member;

@Data
@Entity
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String itemName;
    private Integer price;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Item(String itemName, Integer price, Integer quantity, Member member) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.member = member;
    }
}
