package br.com.wex.service;

import br.com.wex.controller.response.ConvertedTransaction;
import br.com.wex.exception.ExchangeRateNotFoundException;
import br.com.wex.exception.ResourceNotFoundException;
import br.com.wex.feign.ExchangeRateResponse;
import br.com.wex.feign.ExchangeRatesClient;
import br.com.wex.controller.request.PurchaseTransactionRequest;
import br.com.wex.controller.response.PurchaseTransactionResponse;
import br.com.wex.entity.PurchaseTransaction;
import br.com.wex.repository.PurchaseTransactionRepository;
import br.com.wex.service.mapper.PurchaseTransactionMapper;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseTransactionService {

    @NonNull
    private PurchaseTransactionRepository repository;

    @NonNull
    private PurchaseTransactionMapper mapper;

    @NonNull
    private ExchangeRatesClient  exchangeRatesClient;

    @Transactional
    public PurchaseTransactionResponse save(PurchaseTransactionRequest transactionRequest) {
        PurchaseTransaction purchaseTransaction = mapper.toEntity(transactionRequest);
        return mapper.toResponse(repository.save(purchaseTransaction));
    }

    public ConvertedTransaction getWithConversion(UUID uuid, String currency) {
        PurchaseTransaction purchaseTransaction = repository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Transaction with id %s not found", uuid)));

        String filters = createFilters(purchaseTransaction.getTransactionDate(), currency);
        String fields = "exchange_rate, record_date";
        String sort = "-record_date";
        ExchangeRateResponse exchangeRate = exchangeRatesClient.getExchangeRate(filters, fields, sort);

        return exchangeRate.getData().stream().findFirst()
                .map(data -> mapper.toConvertedTransaction(data, purchaseTransaction))
                .orElseThrow(ExchangeRateNotFoundException::new);
    }

    private String createFilters(LocalDate transactionDate, String targetCurrency) {
        LocalDate startDate = transactionDate.minusMonths(6);

        return new StringBuilder()
            .append("record_date:gte:").append(startDate.toString())
               .append(",")
            .append("record_date:lte:").append(transactionDate.toString())
               .append(",")
           .append("currency:eq:").append(targetCurrency)
           .toString();
    }
}
