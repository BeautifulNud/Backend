package ggudock.domain.item.application;

import ggudock.domain.item.dao.ItemRepository;
import ggudock.domain.item.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    public List<Item> getList() {
        return itemRepository.findAll();
    }

    public Item getDetail(String itemId) {
        return itemRepository.findByItemId(itemId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 상품을 찾을 수 없습니다."));
    }
}
