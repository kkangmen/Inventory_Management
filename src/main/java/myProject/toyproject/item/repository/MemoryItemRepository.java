package myProject.toyproject.item.repository;

import myProject.toyproject.item.entity.Item;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Primary
public class MemoryItemRepository implements ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>();
    private static Long sequence = 0L;

    /***
     * Item 객체를 저장합니다.
     * @param item
     * @return Item 객체
     */
    @Override
    public Item save(Item item) {
        item.setItemId(++sequence);
        store.put(item.getItemId(),item);
        return item;
    }

    /***
     * itemId 값을 통해 Item 객체를 반환합니다.
     * @param itemId
     * @return Item 객체
     */
    @Override
    public Item findById(Long itemId) {
        return store.get(itemId);
    }

    /***
     * 모든 Item 객체를 반환합니다.
     * @return List<Item></Item>
     */
    @Override
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    /***
     * Item 객체의 가격, 수량을 수정합니다.
     * @param itemId
     * @param updateItem
     */
    @Override
    public void update(Long itemId, Item updateItem) {
        Item item = store.get(itemId);
        item.setItemName(updateItem.getItemName());
        item.setPrice(updateItem.getPrice());
        item.setQuantity(updateItem.getQuantity());
    }

    /***
     * Item 객체를 제거합니다.
     * @param itemId
     * @return 삭제된 Item 객체를 반환합니다.
     */
    @Override
    public Item delete(Long itemId) {
        Item item = store.get(itemId);
        store.remove(itemId);
        return item;
    }

    /***
     * 저장소에 있는 모든 Item 객체를 삭제합니다.
     */
    @Override
    public void clearStore() {
        store.clear();
    }
}
