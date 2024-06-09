package ggudock.domain.payment.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import ggudock.config.PaymentConfig;
import ggudock.domain.order.entity.CustomerOrder;
import ggudock.domain.order.model.OrderStatus;
import ggudock.domain.order.repository.OrderRepository;
import ggudock.domain.payment.api.dto.PaymentRequest;
import ggudock.domain.payment.application.dto.CancelResponse;
import ggudock.domain.payment.application.dto.FailResponse;
import ggudock.domain.payment.application.dto.PaymentResponse;
import ggudock.domain.payment.application.dto.SuccessResponse;
import ggudock.domain.payment.entity.Payment;
import ggudock.domain.payment.repository.PaymentRepository;
import ggudock.domain.user.entity.User;
import ggudock.domain.user.repository.UserRepository;
import ggudock.global.exception.BusinessException;
import ggudock.global.exception.constant.ErrorCode;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;

@Service
@Transactional
public class PaymentService {
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentConfig paymentConfig;

    @Autowired
    public PaymentService(UserRepository userRepository, PaymentRepository paymentRepository, PaymentConfig paymentConfig,OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.paymentConfig = paymentConfig;
    }

    public PaymentResponse savePayment(PaymentRequest paymentRequest, String email,String orderId) {
        User user = getUser(email);
        CustomerOrder order = getOrder(orderId);
        Payment payment = createPayment(paymentRequest, user , order);
        Payment savePayment = save(payment);
        return createPaymentResponse(savePayment.getId());
    }

    @Transactional(readOnly = true)
    public Payment getPayment(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PAYMENT));
    }

    @Transactional(readOnly = true)
    public PaymentResponse getDetail(Long paymentId) {
        return createPaymentResponse(paymentId);
    }

    public SuccessResponse successPayment(String paymentKey, String orderId, int amount) throws Exception {
        checkOrder(paymentKey, orderId, amount);
        String result = finalPayment(paymentKey, orderId, amount);
        CustomerOrder order = getOrder(orderId);
        order.acceptStatus(OrderStatus.YES);
        return getSuccessResponseFromJson(result);
    }


    public SuccessResponse getSuccessResponseFromJson(String jsonResponse) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonResponse, SuccessResponse.class);
    }

    public void checkOrder(String paymentKey, String orderId, int amount) {
        Payment payment = getPaymentByOrderId(orderId);
        if (checkAmount(amount, payment))
            createPaymentKey(paymentKey, payment);
        else
            throw new BusinessException(ErrorCode.NOT_MATCH_AMOUNT);
    }

    public String finalPayment(String paymentKey, String orderId, int amount) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = createHeaders();
        JSONObject jsonObject = createSuccessObject(orderId, amount);
        return createHttpBody(restTemplate, PaymentConfig.PAYMENT_ACCEPT_URL + paymentKey, headers, jsonObject);
    }

    public FailResponse failPayment(String errorCode, String errorMessage, String orderId) {
        Payment payment = getPaymentByOrderId(orderId);
        CustomerOrder order = getOrder(orderId);
        order.acceptStatus(OrderStatus.NO);
        payment.failPayment();
        return createFailResponse(errorCode, errorMessage, orderId);
    }

    public CancelResponse cancelPayment(String paymentKey, String reason) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        cancelPayment(paymentKey);
        URI uri = URI.create(paymentConfig.getTossUrl() + paymentKey + "/cancel");
        HttpHeaders headers = createHeaders();
        JSONObject cancelObject = createFailObject(reason);
        String result = createHttpBody(restTemplate, uri + reason, headers, cancelObject);
        return getCancelResponseFromJson(result);
    }

    private void cancelPayment(String paymentKey) {
        Payment payment = getPaymentByPaymentKey(paymentKey);
        payment.cancelPayment();
    }

    private Payment getPaymentByPaymentKey(String paymentKey) {
        return paymentRepository.findByPaymentKey(paymentKey);
    }

    public CancelResponse getCancelResponseFromJson(String jsonResponse) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonResponse, CancelResponse.class);
    }

    private String createHttpBody(RestTemplate restTemplate, String uri, HttpHeaders headers, JSONObject jsonObject) {
        return restTemplate.postForEntity(
                uri,
                new HttpEntity<>(jsonObject, headers),
                String.class
        ).getBody();
    }
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String secretKey = paymentConfig.getSecretKey() + ":";
        String encodeAuth = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        setHeader(headers, encodeAuth);
        return headers;
    }

    private static void setHeader(HttpHeaders headers, String encodeAuth) {
        headers.setBasicAuth(encodeAuth);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    }

    private static JSONObject createSuccessObject(String orderId,int amount) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("orderId", orderId);
        hm.put("amount", String.valueOf(amount));
        return new JSONObject(hm);
    }

    private static JSONObject createFailObject(String reason) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("reason", reason);
        return new JSONObject(hm);
    }

    private CustomerOrder getOrder(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ORDER));
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    private Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }
    private static Payment createPayment(PaymentRequest paymentRequest, User user, CustomerOrder order) {
        return Payment.builder()
                .payType(paymentRequest.getPayType())
                .user(user)
                .order(order)
                .build();
    }

    private static FailResponse createFailResponse(String errorCode, String errorMessage, String orderId) {
        return FailResponse.builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .orderId(orderId)
                .build();
    }

    private PaymentResponse createPaymentResponse(Long paymentId) {
        Payment payment = getPayment(paymentId);
        return PaymentResponse.builder()
                .payType(payment.getPayType())
                .amount(payment.getOrder().getTotalPrice())
                .paymentKey(payment.getPaymentKey())
                .orderId(payment.getOrder().getOrderId())
                .orderName(payment.getOrder().getItem().getName())
                .customerEmail(payment.getUser().getEmail())
                .customerName(payment.getUser().getUsername())
                .payDate(payment.getPayDate())
                .payStatus(payment.getPayStatus())
                .build();
    }

    /**
     * 결제 검증하는거
     */
    private static void createPaymentKey(String paymentKey, Payment payment) {
        payment.createPaymentKey(paymentKey);
    }

    private static boolean checkAmount(int amount, Payment payment) {
        return payment.getOrder().getTotalPrice()==amount;
    }

    private Payment getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PAYMENT));
    }
}
