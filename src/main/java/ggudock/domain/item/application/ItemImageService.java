package ggudock.domain.item.application;

import ggudock.domain.item.entity.Item;
import ggudock.domain.item.entity.ItemImage;
import ggudock.domain.item.repository.ItemImageRepository;
import ggudock.domain.picture.application.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemImageService  {

    private final ItemImageRepository itemImageRepository;
    private final S3UploadService s3UploadService;

    @Transactional
    public void save(MultipartFile image, Item item) throws IOException {
        String fileUrl = s3UploadService.upload(image, "images");
        ItemImage itemImage = new ItemImage(fileUrl, item);
        itemImageRepository.save(itemImage);
    }

    @Transactional
    public void delete(Long pictuerId) {
        itemImageRepository.deleteById(pictuerId);
    }

    @Transactional
    public void deleteAll(Long itemId) {
        itemImageRepository.deleteAllByItem_Id(itemId);
    }

    public List<ItemImage> findImages(Long itemId) {
        return itemImageRepository.findAllByItem_Id(itemId);
    }
}

