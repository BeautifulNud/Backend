package ggudock.domain.cart.api;

import ggudock.auth.utils.SecurityUtil;
import ggudock.domain.cart.application.CartService;
import ggudock.domain.cart.application.dto.CartResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "찜", description = "찜 api")
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "찜 생성", description = "찜을 저장한다")
    @PostMapping("/{itemId}")
    public ResponseEntity<?> saveCart(@PathVariable("itemId") Long itemId, Authentication authentication) {
        cartService.saveCart(SecurityUtil.getCurrentName(authentication),itemId);
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(204));
    }

    @Operation(summary = "찜 삭제", description = "찜을 삭제한다")
    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable("cartId") Long cartId) {
        cartService.deleteCart(cartId);
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(204));
    }

    @Operation(summary = "찜 한개 보기", description = "찜을 한개 확인한다")
    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getDetail(@PathVariable("cartId") Long cartId) {
        return new ResponseEntity<>(cartService.getDetail(cartId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "찜 전체 보기", description = "찜 전체를 확인한다")
    @GetMapping("/all")
    public ResponseEntity<List<CartResponse>> getAll(Authentication authentication) {
        return new ResponseEntity<>(cartService.getAll(SecurityUtil.getCurrentName(authentication)), HttpStatusCode.valueOf(200));
    }
}