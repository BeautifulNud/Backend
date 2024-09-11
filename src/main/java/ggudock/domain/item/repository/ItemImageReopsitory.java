package ggudock.domain.item.repository;

import ggudock.domain.item.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImageReopsitory extends JpaRepository<ItemImage, Long> {
    ItemImage findByItem_Id(Long itemId);

    List<ItemImage> findAllByItem_Id(Long itemId);

    void deleteAllByItem_Id(Long itemId);

    void deleteByItem_Id(Long itemId);
}
