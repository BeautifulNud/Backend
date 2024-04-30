package ggudock.domain.item.strategy;

import ggudock.domain.item.entity.Item;
import ggudock.domain.item.repository.ItemRepository;

import java.util.List;
public interface OrderByStrategy {
    List<Item> findList(ItemRepository itemRepository);
}
