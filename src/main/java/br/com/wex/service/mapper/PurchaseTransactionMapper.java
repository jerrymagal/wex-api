package br.com.wex.service.mapper;

import br.com.wex.controller.request.PurchaseTransactionRequest;
import br.com.wex.controller.response.ConvertedTransaction;
import br.com.wex.controller.response.PurchaseTransactionResponse;
import br.com.wex.entity.PurchaseTransaction;
import br.com.wex.feign.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper(componentModel = "spring")
public interface PurchaseTransactionMapper {

    @Mapping(source = "usdAmount", target = "purchaseAmount", qualifiedByName = "roundedAmount")
    PurchaseTransaction toEntity(PurchaseTransactionRequest transactionRequest);

    @Named("roundedAmount")
    default BigDecimal roundedAmount(BigDecimal value) {
        return value.setScale(2,  RoundingMode.HALF_UP);
    }

    @Mapping(target = "usdAmount", source = "purchaseAmount")
    PurchaseTransactionResponse toResponse(PurchaseTransaction purchaseTransaction);


    @Mapping(target = "identifier", source = "purchaseTransaction.uuid")
    @Mapping(target = "usdAmount", source = "purchaseTransaction.purchaseAmount")
    @Mapping(target = "exchangeRate", source = "data.exchange_rate")
    @Mapping(target = "convertedAmount", expression = "java(convertAmount(data.getExchange_rate(), purchaseTransaction.getPurchaseAmount()))")
    ConvertedTransaction toConvertedTransaction(Data data, PurchaseTransaction purchaseTransaction);

    default BigDecimal convertAmount(String exchangeRate, BigDecimal purchaseAmount) {
        return new BigDecimal(exchangeRate).multiply(purchaseAmount).setScale(2, RoundingMode.HALF_UP);
    }
}
