package ggudock.domain.item.application;

import ggudock.domain.item.entity.Item;
import ggudock.domain.item.entity.ItemImage;
import ggudock.domain.item.repository.ItemImageReopsitory;
import ggudock.s3.application.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemImageService {

    private final ItemImageReopsitory itemImageReopsitory;
    private final S3UploadService s3UploadService;

    @Transactional
    public void save(List<MultipartFile> images, Item item) throws IOException {
        for (MultipartFile file : images) {
            String fileUrl = s3UploadService.upload(file, "items/");
            ItemImage itemImage = new ItemImage(fileUrl, item);
            itemImageReopsitory.save(itemImage);
        }

    }

    @Transactional
    public void deleteByItemId(Long itemId) {
        itemImageReopsitory.deleteByItem_Id(itemId);
    }

    @Transactional
    public void deletAllByItemId(Long itemId) {
        itemImageReopsitory.deleteAllByItem_Id(itemId);
    }

    public List<ItemImage> getItemImages(Long itemId) {
        return itemImageReopsitory.findAllByItem_Id(itemId);
    }

}
