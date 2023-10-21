package br.com.wex.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PurchaseTransactionRequest(
        @NotNull @Size(max = 50) String description,
        @NotNull @JsonProperty("transaction_date") LocalDate transactionDate,
        @NotNull @Positive(message = "Purchase amount must be a positive value.") @JsonProperty("usd_amount") BigDecimal usdAmount
) {}