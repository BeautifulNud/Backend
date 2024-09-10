package ggudock.domain.subscription.api;

import ggudock.auth.utils.SecurityUtil;
import ggudock.domain.subscription.api.dto.SubscriptionDayRequest;
import ggudock.domain.subscription.api.dto.SubscriptionPeriodRequest;
import ggudock.domain.subscription.application.SubscriptionService;
import ggudock.domain.subscription.application.dto.DateResponse;
import ggudock.domain.subscription.application.dto.SubscriptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "구독", description = "구독 api")
@RequestMapping("/api/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Operation(summary = "기간 구독 생성", description = "기간구독과 구독날짜들을 저장한다")
    @PostMapping("/{addressId}/{orderId}/period")
    public ResponseEntity<?> saveSubscriptionByPeriod(@Valid @RequestBody SubscriptionPeriodRequest subscriptionRequest,
                                                      @PathVariable("addressId") Long addressId,
                                                      @PathVariable("orderId") String orderId,
                                                      Authentication authentication) {
        subscriptionService.saveSubscriptionByPeriod(subscriptionRequest, SecurityUtil.getCurrentName(authentication), addressId, orderId);
        return new ResponseEntity<>(null,HttpStatusCode.valueOf(204));
    }

    @Operation(summary = "당일 구독 생성", description = "당일구독과 구독날짜들을 저장한다")
    @PostMapping("/{addressId}/{orderId}/day")
    public ResponseEntity<?> saveSubscriptionByDay(@Valid @RequestBody SubscriptionDayRequest subscriptionRequest,
                                                   @PathVariable("addressId") Long addressId,
                                                   @PathVariable("orderId") String orderId,
                                                   Authentication authentication) {
        subscriptionService.saveSubscriptionByDay(subscriptionRequest, SecurityUtil.getCurrentName(authentication), addressId, orderId);
        return new ResponseEntity<>(null,HttpStatusCode.valueOf(204));
    }

    @Operation(summary = "구독 삭제", description = "구독과 구독날짜들을 삭제한다")
    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<?> deleteSubscription(@PathVariable("subscriptionId") Long subscriptionId) {
        subscriptionService.deleteSubscription(subscriptionId);
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(204));
    }

    @Operation(summary = "구독 id로 구독 정보 불러오기", description = "구독 id를 통해서 구독하나에 대한 정보를 불러온다")
    @GetMapping()
    public ResponseEntity<SubscriptionResponse> getDetail(@RequestParam("subscriptionId") Long subscriptionId) {
        return new ResponseEntity<>(subscriptionService.getDetail(subscriptionId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "모든 구독 불러오기", description = "모든 구독정보들을 받아온다")
    @GetMapping("/all")
    public ResponseEntity<Page<SubscriptionResponse>> getSubscriptionPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                          Authentication authentication) {
        return new ResponseEntity<>(subscriptionService.getSubscriptionPage(SecurityUtil.getCurrentName(authentication), page), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "기간이 지나서 구독 종료하기", description = "기간이 지나서 구독이 종료")
    @PatchMapping("/terminate/{subscriptionId}")
    public ResponseEntity<SubscriptionResponse> terminateSubscription(@PathVariable("subscriptionId") Long subscriptionId) {
        return new ResponseEntity<>(subscriptionService.terminateSubscription(subscriptionId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "현재 진행중인 구독 보기", description = "진행중인 구독 정보들을 확인한다.")
    @GetMapping("/search-on")
    public ResponseEntity<Page<SubscriptionResponse>> getSubscriptionPageByOn(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                              Authentication authentication) {
        return new ResponseEntity<>(subscriptionService.getSubscriptionPageByOn(page, SecurityUtil.getCurrentName(authentication)), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "종료된 구독 보기", description = "종료된 구독 정보들을 확인한다.")
    @GetMapping("/search-off")
    public ResponseEntity<Page<SubscriptionResponse>> getSubscriptionPageByOff(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                               Authentication authentication) {
        return new ResponseEntity<>(subscriptionService.getSubscriptionPageByOff(page, SecurityUtil.getCurrentName(authentication)), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "기간으로 등록한 구독 보기", description = "기간으로 등록된 구독을 확인한다.")
    @GetMapping("/search-period")
    public ResponseEntity<Page<SubscriptionResponse>> getSubscriptionPageByPeriod(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                                  Authentication authentication) {
        return new ResponseEntity<>(subscriptionService.getSubscriptionPageByPeriod(page, SecurityUtil.getCurrentName(authentication)), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "당일로 등록한 구독 보기", description = "당일로 등록된 구독을 확인한다.")
    @GetMapping("/search-day")
    public ResponseEntity<Page<SubscriptionResponse>> getSubscriptionPageByDay(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                               Authentication authentication) {
        return new ResponseEntity<>(subscriptionService.getSubscriptionPageByDay(page, SecurityUtil.getCurrentName(authentication)), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "제목으로 구독 보기", description = "제목으로 구독을 확인한다.")
    @GetMapping("/search-name")
    public ResponseEntity<Page<SubscriptionResponse>> getDaySubscriptionPage(@RequestParam("name") String name,
                                                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                                                             Authentication authentication) {
        return new ResponseEntity<>(subscriptionService.getSubscriptionPageByName(name, SecurityUtil.getCurrentName(authentication), page), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "구독된 날짜 보기", description = "오늘부터 이후까지 자기가 이미 구독된 정보 받아오기")
    @GetMapping("/dates")
    public ResponseEntity<List<DateResponse>> getDaySubscriptionPage(Authentication authentication) {
        return new ResponseEntity<>(subscriptionService.getSubscriptionByEmail(SecurityUtil.getCurrentName(authentication)), HttpStatusCode.valueOf(200));
    }
}
