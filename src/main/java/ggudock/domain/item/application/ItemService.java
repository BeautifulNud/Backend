package ggudock.domain.item.application;

import ggudock.domain.cart.repository.CartRepository;
import ggudock.domain.category.application.response.CategoryResponse;
import ggudock.domain.category.entity.Category;
import ggudock.domain.category.repository.CategoryRepository;
import ggudock.domain.company.dto.CompanyResponse;
import ggudock.domain.company.entity.Company;
import ggudock.domain.company.repository.CompanyRepository;
import ggudock.domain.item.dto.ItemDetailResponse;
import ggudock.domain.item.dto.ItemRequest;
import ggudock.domain.item.entity.Item;
import ggudock.domain.item.repository.ItemRepository;
import ggudock.domain.item.strategy.OrderByStrategy;
import ggudock.domain.review.application.ReviewService;
import ggudock.domain.user.entity.User;
import ggudock.domain.user.repository.UserRepository;
import ggudock.global.exception.BusinessException;
import ggudock.global.exception.constant.ErrorCode;
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
public class ItemService {
    private final ItemImageService itemImageService;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final S3UploadService s3UploadService;
    private final ReviewService reviewService;

    @Transactional
    public ItemDetailResponse addItem(ItemRequest request, MultipartFile thumbnail, List<MultipartFile> images) throws IOException {
        validateItem(request);

        String thumbnailImage = s3UploadService.upload(thumbnail, "thumbnail/");
        Item item = createItem(request, thumbnailImage);

        itemImageService.save(images, item);
        Item save = saveItem(item);

        return ItemDetailResponse.of(save);
    }

    @Transactional
    public void deleteItem(Long itemId) {
        Item item = getItem(itemId);
        // S3 상품 썸네일 삭제
        s3UploadService.fileDelete(item.getThumbnail());
        // 상품 삭제
        itemRepository.deleteById(itemId);

        // 상품 이미지 리스트 삭제
        itemImageService.deletAllByItemId(itemId);

        //상품 리뷰 리스트 삭제
        reviewService.deleteListByItem(itemId);
    }

    public List<ItemDetailResponse> getList() {
        return itemRepository.findAll().stream()
                .map(Item::getId)
                .map(this::getDetail)
                .toList();
    }

    public List<ItemDetailResponse> getCategoryList(String category) {
        return itemRepository.findAll().stream()
                .filter(item -> item.isSameCategory(category))
                .map(Item::getId)
                .map(this::getDetail)
                .toList();
    }

    public List<ItemDetailResponse> getListOrderBy(OrderByStrategy orderByStrategy) {
        return orderByStrategy.findList(itemRepository).stream()
                .map(Item::getId)
                .map(this::getDetail)
                .toList();
    }

    @Transactional
    public ItemDetailResponse getDetail(Long itemId) {
        return createItemResponse(itemId);
    }

    @Transactional
    public ItemDetailResponse getDatailWithLogin(String email, Long itemId) {
        return createItemResponse(email, itemId);
    }

    private ItemDetailResponse createItemResponse(Long itemId) {
        Item item = getItem(itemId);
        addView(item);

        return ItemDetailResponse.builder()
                .name(item.getName())
                .price(item.getPrice())
                .salePercent(item.getSalePercent())
                .salePrice(item.getSalePrice())
                .thumbnail(item.getThumbnail())
                .description(item.getDescription())
                .view(item.getViews())
                .rating(item.getRating())
                .wish(false)
                .category(CategoryResponse.of(item.getCategory()))
                .company(CompanyResponse.of(item.getCompany()))
                .build();
    }

    private ItemDetailResponse createItemResponse(String email, Long itemId) {
        Item item = getItem(itemId);
        addView(item);
        boolean check = cartRepository.existsByUser_EmailAndItem_Id(email, itemId);

        return ItemDetailResponse.builder()
                .name(item.getName())
                .price(item.getPrice())
                .salePercent(item.getSalePercent())
                .salePrice(item.getSalePrice())
                .thumbnail(item.getThumbnail())
                .description(item.getDescription())
                .view(item.getViews())
                .rating(item.getRating())
                .wish(check)
                .category(CategoryResponse.of(item.getCategory()))
                .company(CompanyResponse.of(item.getCompany()))
                .build();
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
    }

    private Item getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));
    }

    private Company getCompany(String companyName) {
        return companyRepository.findByName(companyName)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMPANY));
    }

    private Category getCategory(String category) {
        return categoryRepository.findByName(category)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CATEGORY));
    }

    private Item createItem(ItemRequest request, String thumbnail) {
        return Item.builder()
                .name(request.getName())
                .category(getCategory(request.getName()))
                .company(getCompany(request.getName()))
                .price(request.getPrice())
                .salePercent(request.getSalePercent())
                .rating(0.0f)
                .plan(request.getPlan())
                .description(request.getDescription())
                .views(0)
                .thumbnail(thumbnail)
                .build();
    }

    private void validateItem(ItemRequest request) {
        if (itemRepository.existsAllByName(request.getName())) {
            throw new BusinessException(ErrorCode.DUPLICATED_ITEM);
        }
    }

    @Transactional
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Transactional
    public void addView(Item item) {
        item.raiseViews();
    }

}
