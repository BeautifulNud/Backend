package ggudock.domain.payment.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CardResponse {
    @JsonProperty("issuerCode")
    private String issuerCode;
    @JsonProperty("acquirerCode")
    private String acquirerCode;
    @JsonProperty("number")
    private String number;
    @JsonProperty("installmentPlanMonths")
    private int installmentPlanMonths;
    @JsonProperty("isInterestFree")
    private boolean isInterestFree;
    @JsonProperty("approveNo")
    private String approveNo;
    @JsonProperty("useCardPoint")
    private boolean useCardPoint;
    @JsonProperty("cardType")
    private String cardType;
    @JsonProperty("ownerType")
    private String ownerType;
    @JsonProperty("acquireStatus")
    private String acquireStatus;
    @JsonProperty("amount")
    private int amount;
}