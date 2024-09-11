package ggudock.domain.item.api;

import ggudock.auth.utils.SecurityUtil;
import ggudock.domain.item.application.ItemService;
import ggudock.domain.item.dto.ItemDetailResponse;
import ggudock.domain.item.dto.ItemRequest;
import ggudock.domain.item.strategy.OrderByStrategy;
import ggudock.domain.item.entity.orderby.Review;
import ggudock.domain.item.entity.orderby.View;
import ggudock.domain.review.dto.ReviewRequest;
import ggudock.global.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static ggudock.global.exception.constant.ErrorCode.BAD_REQUEST;

@RestController
@RequestMapping("/api/item")
@Tag(name = "상품", description = "상품 api")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    // 아이템 생성
    @Operation(summary = "상품 저장", description = "정보를 받아 상품을 저장한다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ItemDetailResponse> createItem(@Valid @RequestPart(value = "reviewRequest") ItemRequest request,
                                                         @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
                                                         @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
        return new ResponseEntity<>(itemService.addItem(request, thumbnail, images), HttpStatusCode.valueOf(200));
    }

    // 아이템 삭제
    @Operation(summary = "상품 삭제", description = "정보를 받아 상품을 저장한다.")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable(name = "itemId") Long itemId) {
        itemService.deleteItem(itemId);
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(200));
    }

    // 상품 상세보기
    @Operation(summary = "상품 상세보기", description = "상품의 개별 상세 페이지를 반환한다.")
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDetailResponse> getDetail(@PathVariable("itemId") Long itemId) {
        return new ResponseEntity<>(itemService.getDetail(itemId), HttpStatusCode.valueOf(200));
    }

    // 상품 상세보기(로그인후 찜 여부 표시)
    @Operation(summary = "상품 상세보기(로그인)", description = "상품의 개별 상세 페이지를 반환한다.(로그인시 찜 확인 가능)")
    @GetMapping("/token/{itemId}")
    public ResponseEntity<ItemDetailResponse> getDetailWithToken(Authentication authentication, @PathVariable("itemId") Long itemId) {
        return new ResponseEntity<>(itemService.getDatailWithLogin(SecurityUtil.getCurrentName(authentication), itemId), HttpStatusCode.valueOf(200));
    }

    // 상품 전체 리스트 가져오기
    @Operation(summary = "상품 전체 리스트", description = "상품의 전체 리스트를 반환한다.")
    @GetMapping("/list")
    public ResponseEntity<List<ItemDetailResponse>> getList() {
        return new ResponseEntity<>(itemService.getList(), HttpStatusCode.valueOf(200));
    }

    // 상품 카테고리별 리스트 가져오기
    @Operation(summary = "카테고리별 상품 리스트", description = "상품의 카테고리별 리스트를 반환한다.")
    @GetMapping("/category")
    public ResponseEntity<List<ItemDetailResponse>> getCategoryList(@RequestParam("category") String category) {
        return new ResponseEntity<>(itemService.getCategoryList(category), HttpStatusCode.valueOf(200));
    }

    // 상품 카테고리별 리스트 가져오기
    @Operation(summary = "카테고리별 상품 리스트 정렬", description = "정렬된 상품의 카테고리별 리스트를 반환한다.")
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
