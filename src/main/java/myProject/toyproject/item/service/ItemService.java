package myProject.toyproject.item.service;

import lombok.RequiredArgsConstructor;
import myProject.toyproject.item.dto.ItemCreateRequest;
import myProject.toyproject.item.dto.ItemUpdateRequest;
import myProject.toyproject.item.entity.Item;
import myProject.toyproject.item.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ItemService {
    Long createItem(ItemCreateRequest request);
    List<Item> getAllItems();
    Item getItem(Long itemId);
    void updateItem(Long itemId, ItemUpdateRequest request);
    Item deleteItem(Long itemId);
}
