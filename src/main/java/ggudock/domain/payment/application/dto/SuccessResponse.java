package ggudock.domain.payment.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SuccessResponse {
    @JsonProperty("mId")
    private String mId;
    @JsonProperty("version")
    private String version;
    @JsonProperty("paymentKey")
    private String paymentKey;
    @JsonProperty("status")
    private String status;
    @JsonProperty("transactionKey")
    private String transactionKey;
    @JsonProperty("lastTransactionKey")
    private String lastTransactionKey;
    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("orderName")
    private String orderName;
    @JsonProperty("requestedAt")
    private String requestedAt;
    @JsonProperty("approvedAt")
    private String approvedAt;
    @JsonProperty("useEscrow")
    private boolean useEscrow;
    @JsonProperty("cultureExpense")
    private boolean cultureExpense;
    @JsonProperty("card")
    private CardResponse card;
    @JsonProperty("type")
    private String type;
    @JsonProperty("country")
    private String country;
    @JsonProperty("isPartialCancelable")
    private boolean isPartialCancelable;
    @JsonProperty("receipt")
    private Receipt receipt;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("totalAmount")
    private int totalAmount;
    @JsonProperty("balanceAmount")
    private int balanceAmount;
    @JsonProperty("suppliedAmount")
    private int suppliedAmount;
    @JsonProperty("vat")
    private int vat;
    @JsonProperty("taxFreeAmount")
    private int taxFreeAmount;
    @JsonProperty("method")
    private String payType;

    @Data
    @Builder
    @AllArgsConstructor
    public static class Receipt {
        @JsonProperty("url")
        private String url;
    }
}