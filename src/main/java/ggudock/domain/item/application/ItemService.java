package ggudock.domain.item.application;

import ggudock.domain.cart.entity.Cart;
import ggudock.domain.category.entity.Category;
import ggudock.domain.company.dto.CompanyDto;
import ggudock.domain.company.entity.Company;
import ggudock.domain.item.dto.ItemDetailResponse;
import ggudock.domain.item.entity.Item;
import ggudock.domain.item.repository.ItemRepository;
import ggudock.domain.item.strategy.OrderByStrategy;
import ggudock.domain.user.application.UserService;
import ggudock.domain.user.entity.User;
import ggudock.domain.user.repository.UserRepository;
import ggudock.global.exception.BusinessException;
import ggudock.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

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

    public ItemDetailResponse getDetail(Long itemId) {
        return createResponse(itemId);
    }

    public ItemDetailResponse getDetailWithToken(String email, Long itemId) {
        Cart cart = getCart(email, itemId);
        // 유저 찾고 아이템 아이디로 찾은 Cart 객체랑 맞으면 찜 추가
        return createResponse(itemId);
    }

    public List<ItemDetailResponse> getListWithToken(String token) {
        return itemRepository.findAll().stream()
                .map(Item::getId)
                .map(this::getDetail)
                .toList();
    }

    private ItemDetailResponse createResponse(Long id) {
        Item item = getItem(id);
        Company company = getCompany(id);
        Category category = getCategory(id);
        return ItemDetailResponse.builder()
                .name(item.getName())
                .category(category.getName())
                .price(item.getPrice())
                .salePercent(item.getSalePercent())
                .salePrice(item.getSalePrice())
                .thumbnail(item.getThumbnail())
                .description(item.getDescription())
                .view(item.getViews())
                .rating(item.getRating())
                .companyDto(CompanyDto.EntityToDto(company))
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

    // TODO repo 개발 시 연결
    private Cart getCart(String email, Long itemId) {
        User user = getUser(email);
        Item item = getItem(itemId);

        return Cart.builder()
                .user(user)
                .item(item)
                .build();
    }

    // TODO repo 개발 시 연결
    private Company getCompany(Long id) {
        return Company.builder().build();
    }

    // TODO repo 개발 시 연결
    private Category getCategory(Long id) {
        return Category.builder().build();
    }

}
