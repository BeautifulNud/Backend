package ggudock.domain.item.repository;

import ggudock.domain.item.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    List<ItemImage> findAllByItem_Id(Long itemId);

    void deleteAllByItem_Id(Long itemId);
}
