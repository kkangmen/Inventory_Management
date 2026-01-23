package myProject.toyproject.item.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import myProject.toyproject.item.entity.Item;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JpaItemRepository implements ItemRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    @Override
    public Item findById(Long itemId) {
        return em.find(Item.class, itemId);
    }

    @Override
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

    @Override
    public void update(Long itemId, Item updateItem) {
        Item item = em.find(Item.class, itemId);
        item.setItemName(updateItem.getItemName());
        item.setPrice(updateItem.getPrice());
        item.setQuantity(updateItem.getQuantity());
    }

    @Override
    public Item delete(Long itemId) {
        Item item = em.find(Item.class, itemId);
        em.remove(item);
        return item;
    }

    @Override
    public void clearStore() {
        // .executeUpdate()를 해야 DB에 반영이 된다.
        em.createQuery("delete from Item i").executeUpdate();
    }
}
