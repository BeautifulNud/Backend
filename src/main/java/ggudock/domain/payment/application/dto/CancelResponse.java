package ggudock.domain.payment.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CancelResponse {

    @JsonProperty("cancels")
    private Cancel cancel;

    @Data
    @Builder
    @AllArgsConstructor
    public static class Cancel {
        @JsonProperty("transactionKey")
        private String transactionKey;

        @JsonProperty("cancelReason")
        private String cancelReason;

        @JsonProperty("taxExemptionAmount")
        private Long taxExemptionAmount;

        @JsonProperty("canceledAt")
        private String canceledAt;

        @JsonProperty("receiptKey")
        private String receiptKey;

        @JsonProperty("cancelAmount")
        private Long cancelAmount;

        @JsonProperty("taxFreeAmount")
        private Long taxFreeAmount;

        @JsonProperty("refundableAmount")
        private Long refundableAmount;

        @JsonProperty("cancelStatus")
        private String cancelStatus;

        @JsonProperty("cancelRequestId")
        private String cancelRequestId;
    }
}
