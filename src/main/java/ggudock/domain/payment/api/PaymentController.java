package ggudock.domain.payment.api;

import ggudock.config.oauth.utils.SecurityUtil;
import ggudock.domain.payment.api.dto.PaymentRequest;
import ggudock.domain.payment.application.PaymentService;
import ggudock.domain.payment.application.dto.CancelResponse;
import ggudock.domain.payment.application.dto.FailResponse;
import ggudock.domain.payment.application.dto.PaymentResponse;
import ggudock.domain.payment.application.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name="토스 결제",description = "토스 결제 api")
@RequestMapping("/api/toss-payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "토스 결제",description = "결제 정보 저장")
    @PostMapping("{orderId}")
    public ResponseEntity<PaymentResponse> savePayment(@RequestBody PaymentRequest paymentRequest,
                                                       @PathVariable("orderId") String orderId){
        return new ResponseEntity<>(paymentService.savePayment(paymentRequest, SecurityUtil.getCurrentName(),orderId),
                HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "토스 결제 성공",description = "성공후 토스페이먼츠에서 Json형식으로 데이터 저장")
    @GetMapping("/success")
    public ResponseEntity<SuccessResponse> successPayment(@RequestParam("paymentKey") String paymentKey,
                                                          @RequestParam("orderId") String orderId,
                                                          @RequestParam("amount") int amount) throws Exception {
        return new ResponseEntity<>(paymentService.successPayment(paymentKey,orderId,amount), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "토스 결제 실패",description = "실패한후 에러메세지 전달")
    @GetMapping("/fail")
    public ResponseEntity<FailResponse> failPayment(@RequestParam("errorCode")String errorCode,
                                                    @RequestParam("errorMessage")String errorMessage,
                                                    @RequestParam("orderId")String orderId){
        return new ResponseEntity<>(paymentService.failPayment(errorCode,errorMessage,orderId),HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "토스 결제취소",description = "결제 취소")
    @PostMapping("/cancel")
    public ResponseEntity<CancelResponse> cancelPayment(@RequestParam("paymentKey") String paymentKey,
                                                        @RequestParam("cancelReason") String cancelReason) throws Exception {
        return new ResponseEntity<>(paymentService.cancelPayment(paymentKey,cancelReason),HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "결제 한개 받기",description = "결제한거 받기")
    @GetMapping("")
    public ResponseEntity<PaymentResponse> getDetail(@RequestParam("paymentId") Long paymentId) throws Exception {
        return new ResponseEntity<>(paymentService.getDetail(paymentId),HttpStatusCode.valueOf(200));
    }
}
