package ggudock.domain.item.api;

import ggudock.domain.item.application.ItemService;
import ggudock.domain.item.dto.ItemDetailResponse;
import ggudock.domain.item.strategy.OrderByStrategy;
import ggudock.domain.item.entity.orderby.Review;
import ggudock.domain.item.entity.orderby.View;
import ggudock.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ggudock.global.exception.constant.ErrorCode.BAD_REQUEST;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    // 상품 상세보기
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDetailResponse> getDetail(@PathVariable("itemId") Long itemId) {
        return new ResponseEntity<>(itemService.getDetail(itemId), HttpStatusCode.valueOf(200));
    }

    // 상품 상세보기(토큰 有)
    @GetMapping("/token/{itemId}")
    public ResponseEntity<ItemDetailResponse> getDetailWithToken(@RequestHeader(value = "Authorization") String token, @PathVariable("itemId") Long itemId) {
        return new ResponseEntity<>(itemService.getDetailWithToken(token, itemId), HttpStatusCode.valueOf(200));
    }

    // 상품 전체 리스트 가져오기
    @GetMapping("/list")
    public ResponseEntity<List<ItemDetailResponse>> getList() {
        return new ResponseEntity<>(itemService.getList(), HttpStatusCode.valueOf(200));
    }

    // 상품 카테고리별 리스트 가져오기
    @GetMapping("/category")
    public ResponseEntity<List<ItemDetailResponse>> getCategoryList(@RequestParam("category") String category) {
        return new ResponseEntity<>(itemService.getCategoryList(category), HttpStatusCode.valueOf(200));
    }

    // 상품 카테고리별 리스트 가져오기
    @GetMapping("/order-by")
    public ResponseEntity<List<ItemDetailResponse>> getListOrderBy(@RequestParam("order-by") String orderBy) {
        return new ResponseEntity<>(itemService.getListOrderBy(getStrategy(orderBy)), HttpStatusCode.valueOf(200));
    }

    private OrderByStrategy getStrategy(String orderBy) {
        return switch (orderBy) {
            case "view" -> new View();
            case "review" -> new Review();
            default -> throw new BusinessException(BAD_REQUEST);
        };
    }
}
