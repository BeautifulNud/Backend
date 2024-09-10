package ggudock.domain.order.api;

import ggudock.auth.utils.SecurityUtil;
import ggudock.domain.order.api.dto.OrderDayRequest;
import ggudock.domain.order.api.dto.OrderPeriodRequest;
import ggudock.domain.order.application.OrderService;
import ggudock.domain.order.application.dto.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "주문", description = "주문 api")
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "기간 주문", description = "시작날짜와 끝나는 날짜를 받고 요일을 고르면 그에맞게 개수를 세서 가격을 받음")
    @PostMapping("/{itemId}/period")
    public ResponseEntity<?> saveByPeriod(@Valid @RequestBody OrderPeriodRequest orderPeriodRequest,
                                          @PathVariable("itemId") Long itemId,
                                          Authentication authentication) {
        orderService.saveOrderByPeriod(orderPeriodRequest, SecurityUtil.getCurrentName(authentication), itemId);
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(204));
    }

    @Operation(summary = "당일 주문", description = "날짜를 고르고 그에맞게 개수를 세서 가격을 받음")
    @PostMapping("/{itemId}/day")
    public ResponseEntity<?> saveByDay(@Valid @RequestBody OrderDayRequest orderDayRequest,
                                       @PathVariable("itemId") Long itemId,
                                       Authentication authentication) {
        orderService.saveOrderByDay(orderDayRequest, SecurityUtil.getCurrentName(authentication), itemId);
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(204));
    }

    @Operation(summary = "주문 삭제", description = "주문을 삭제한다.")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable("orderId") String orderId) {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(204));
    }

    @Operation(summary = "주문 받기", description = "한개의 주문을 받기")
    @GetMapping()
    public ResponseEntity<OrderResponse> getDetail(@RequestParam("orderId") String orderId) {
        return new ResponseEntity<>(orderService.getDetail(orderId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "내가 그동안 했던 주문 보기", description = "본인이 로그인된 아이디로 주문 페이지로 받기")
    @GetMapping("/all")
    public ResponseEntity<Page<OrderResponse>> getDetail(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         Authentication authentication) {
        return new ResponseEntity<>(orderService.getOrderPage(SecurityUtil.getCurrentName(authentication), page), HttpStatusCode.valueOf(200));
    }
}