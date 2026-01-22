package myProject.toyproject.item.service;

import lombok.RequiredArgsConstructor;
import myProject.toyproject.item.dto.ItemCreateRequest;
import myProject.toyproject.item.dto.ItemUpdateRequest;
import myProject.toyproject.item.entity.Item;
import myProject.toyproject.item.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;

    @Override
    public Long createItem(ItemCreateRequest request) {
        Item item = new Item(request.getItemName(), request.getPrice(), request.getQuantity());
        Item savedItem = itemRepository.save(item);
        return savedItem.getItemId();
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItem(Long itemId) {
        Item item = itemRepository.findById(itemId);
        if (item == null){
            throw new RuntimeException("아이템이 존재하지 않습니다.");
        }
        return item;
    }

    @Override
    public void updateItem(Long itemId, ItemUpdateRequest request) {
        Item item = itemRepository.findById(itemId);

        if (item == null){
            throw new RuntimeException("아이템이 존재하지 않습니다.");
        }

        Item updateItem = new Item(request.getItemName(), request.getPrice(), request.getQuantity());
        itemRepository.update(itemId, updateItem);
    }

    @Override
    public Item deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId);
        if (item == null){
           throw new RuntimeException("아이템이 존재하지 않습니다.");
        }

        itemRepository.delete(itemId);
        return item;
    }
}
