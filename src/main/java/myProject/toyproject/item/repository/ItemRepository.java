package myProject.toyproject.item.repository;

import myProject.toyproject.item.dto.ItemSearchCond;
import myProject.toyproject.item.entity.Item;

import java.util.List;

public interface ItemRepository {
    Item save(Item item);
    Item findById(Long itemId);
    List<Item> findAll(ItemSearchCond cond);
    void update(Long itemId, Item updateItem);
    Item delete(Long itemId);
    void clearStore();
}
