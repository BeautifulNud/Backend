package ggudock.domain.item.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ggudock.domain.cart.entity.Cart;
import ggudock.domain.category.entity.Category;
import ggudock.domain.company.dto.CompanyDto;
import ggudock.domain.company.entity.Company;
import ggudock.domain.item.dto.ItemDetailResponse;
import ggudock.domain.item.entity.Item;
import ggudock.domain.item.repository.ItemRepository;
import ggudock.domain.item.strategy.OrderByStrategy;
import ggudock.domain.user.entity.KakaoProfile;
import ggudock.domain.user.entity.User;
import ggudock.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static ggudock.domain.cart.model.Category.DIB;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public List<ItemDetailResponse> getList(String token) {
        return itemRepository.findAll().stream()
                .map(Item::getId)
                .map(id -> getDetail(token,id))
                .toList();
    }

    public List<ItemDetailResponse> getCategoryList(String token, String category) {
        return itemRepository.findAll().stream()
                .filter(item -> item.isSameCategory(category))
                .map(Item::getId)
                .map(id -> getDetail(token,id))
                .toList();
    }

    public List<ItemDetailResponse> getListOrderBy(String token, OrderByStrategy orderByStrategy) {
        return orderByStrategy.findList(itemRepository).stream()
                .map(Item::getId)
                .map(id -> getDetail(token,id))
                .toList();
    }

    public ItemDetailResponse getDetail(String token, Long itemId) {
        Cart cart = getCart(itemId);
        User user = null;
        if(!isTokenEmpty(token)){
            String email = getEmailByToken(token);
            user = getUser(email);
        }
        Item item = getItem(itemId);
        Company company = getCompany(itemId);
        Category category = getCategory(itemId);
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
                .wish(user != null && user.hasWish(cart))
                .companyDto(CompanyDto.EntityToDto(company))
                .build();
    }

    private Item getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 상품을 찾을 수 없습니다."));
    }

    // TODO repo 개발 시 연결
    private Cart getCart(Long id) {
        return Cart.builder().category(DIB).build();
    }

    // TODO repo 개발 시 연결
    private Company getCompany(Long id) {
        return Company.builder().build();
    }

    // TODO repo 개발 시 연결
    private Category getCategory(Long id) {
        return Category.builder().build();
    }
    private User getUser(String email) {
        return userRepository.findByEmail(email);
    }
    private String getEmailByToken(String accessToken) {

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        ResponseEntity<String> response = null;

        response = rt.exchange(
                "url",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(response.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile.getKakaoAccount().getEmail();
    }
    private boolean isTokenEmpty(String token) {
        return StringUtils.hasText(token);
    }
}
