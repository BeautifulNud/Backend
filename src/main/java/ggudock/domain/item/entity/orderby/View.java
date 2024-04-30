package ggudock.domain.item.entity.orderby;

import ggudock.domain.item.entity.Item;
import ggudock.domain.item.repository.ItemRepository;
import ggudock.domain.item.strategy.OrderByStrategy;

import java.util.List;

public class View implements OrderByStrategy {
    @Override
    public List<Item> findList(ItemRepository itemRepository) {
        return itemRepository.findAllByOrderByViewsDesc();
    }
}
