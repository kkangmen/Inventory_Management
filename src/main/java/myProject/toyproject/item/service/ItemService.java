package myProject.toyproject.item.service;

import lombok.RequiredArgsConstructor;
import myProject.toyproject.item.dto.ItemCreateRequest;
import myProject.toyproject.item.dto.ItemSearchCond;
import myProject.toyproject.item.dto.ItemUpdateRequest;
import myProject.toyproject.item.entity.Item;
import myProject.toyproject.item.repository.ItemRepository;
import myProject.toyproject.member.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ItemService {
    Long createItem(ItemCreateRequest request, Member member);
    List<Item> getAllItems(ItemSearchCond cond);
    Item getItem(Long itemId);
    void updateItem(Long itemId, ItemUpdateRequest request);
    Item deleteItem(Long itemId);
}
