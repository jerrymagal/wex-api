package br.com.wex.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(name = "exchangeRatesClient", url = "${exchange-rates-api.base-url}")
public interface ExchangeRatesClient {

    @GetMapping("/v1/accounting/od/rates_of_exchange")
    ExchangeRateResponse getExchangeRate(
            @RequestParam("filter") String filter,
            @RequestParam("fields") String fields,
            @RequestParam("sort") String field);

}
