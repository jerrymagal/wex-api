package br.com.wex.controller.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ConvertedTransaction(
        UUID identifier,
        String description,
        LocalDate transactionDate,
        BigDecimal usdAmount,
        BigDecimal exchangeRate,
        BigDecimal convertedAmount
) {}