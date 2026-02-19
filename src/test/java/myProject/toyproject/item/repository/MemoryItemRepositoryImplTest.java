package myProject.toyproject.item.repository;

import myProject.toyproject.item.entity.Item;
import myProject.toyproject.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class MemoryItemRepositoryImplTest {

    MemoryItemRepository itemRepository = new MemoryItemRepository();

    @Test
    void save() {
        // when
        Item itemA = new Item("ItemA", 10000, 100, new Member());

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
        Item itemA = new Item("ItemA", 10000, 100, new Member());
        Item itemB = new Item("ItemB", 20000, 200, new Member());

        // given
        itemRepository.save(itemA);
        itemRepository.save(itemB);

        // then
        List<Item> all = itemRepository.findAll(null);
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