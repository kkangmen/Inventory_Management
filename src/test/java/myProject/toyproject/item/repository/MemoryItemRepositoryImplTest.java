package myProject.toyproject.item.repository;

import myProject.toyproject.item.entity.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemoryItemRepositoryImplTest {

    MemoryItemRepositoryImpl itemRepository = new MemoryItemRepositoryImpl();

    @Test
    void save() {
        // when
        Item itemA = new Item("ItemA", 10000, 100);

        // given
        Item save = itemRepository.save(itemA);

        // then
        Item byId = itemRepository.findById(itemA.getItemId());
        Assertions.assertThat(byId.getItemName()).isEqualTo(save.getItemName());
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
        // when
        Item itemA = new Item("ItemA", 10000, 100);
        Item itemB = new Item("ItemB", 20000, 200);

        // given
        itemRepository.save(itemA);
        itemRepository.save(itemB);

        // then
        List<Item> all = itemRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(2);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void clearStore() {
    }
}