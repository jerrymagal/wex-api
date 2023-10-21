package br.com.wex.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PurchaseTransactionResponse(String description,
                                          UUID uuid,
                                          @JsonProperty("transaction_date") LocalDate transactionDate,
                                          @JsonProperty("usd_amount")  BigDecimal usdAmount) {}