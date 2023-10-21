package br.com.wex.service;

import br.com.wex.controller.request.PurchaseTransactionRequest;
import br.com.wex.controller.response.ConvertedTransaction;
import br.com.wex.controller.response.PurchaseTransactionResponse;
import br.com.wex.entity.PurchaseTransaction;
import br.com.wex.feign.Data;
import br.com.wex.feign.ExchangeRateResponse;
import br.com.wex.feign.ExchangeRatesClient;
import br.com.wex.repository.PurchaseTransactionRepository;
import br.com.wex.service.mapper.PurchaseTransactionMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PurchaseTransactionServiceTest {

    @InjectMocks
    private PurchaseTransactionService service;

    @Mock
    private PurchaseTransactionRepository repository;

    @Mock
    private PurchaseTransactionMapper mapper;

    @Mock
    private ExchangeRatesClient exchangeRatesClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        PurchaseTransactionRequest request = Instancio.create(PurchaseTransactionRequest.class);
        PurchaseTransaction entity = Instancio.create(PurchaseTransaction.class);
        PurchaseTransactionResponse response = Instancio.create(PurchaseTransactionResponse.class);

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        PurchaseTransactionResponse purchaseTransactionResponse = service.save(request);

        assertEquals(purchaseTransactionResponse, response);

        verify(mapper).toEntity(request);
        verify(repository).save(entity);
        verify(mapper).toResponse(entity);
    }

    @Test
    public void testGetWithConversion() {
        UUID uuid = UUID.randomUUID();
        String currency = "Euro";
        PurchaseTransaction transaction = Instancio.create(PurchaseTransaction.class);
        transaction.setPurchaseAmount(BigDecimal.TEN);
        transaction.setTransactionDate(LocalDate.now());
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();

        Data data = new Data();
        data.setExchange_rate("0.864");
        data.setRecord_date("2023-09-30");

        BigDecimal exchangeRate = new BigDecimal(data.getExchange_rate());

        BigDecimal convertedAmount = transaction.getPurchaseAmount().multiply(new BigDecimal(data.getExchange_rate()));

        exchangeRateResponse.setData(List.of(data));

        ConvertedTransaction convertedTransaction = new ConvertedTransaction(transaction.getUuid(),
                transaction.getDescription(),
                transaction.getTransactionDate(),
                transaction.getPurchaseAmount(),
                exchangeRate,convertedAmount);

        when(repository.findByUuid(uuid)).thenReturn(Optional.of(transaction));
        when(exchangeRatesClient.getExchangeRate(any(), any(), any())).thenReturn(exchangeRateResponse);
        when(mapper.toConvertedTransaction(any(), eq(transaction))).thenReturn(convertedTransaction);

        ConvertedTransaction serviceWithConversion = service.getWithConversion(uuid, currency);

        assertEquals(serviceWithConversion, convertedTransaction);

        verify(repository).findByUuid(uuid);
        verify(exchangeRatesClient).getExchangeRate(any(), any(), any());
        verify(mapper).toConvertedTransaction(any(), eq(transaction));
    }
}