package br.com.wex.feign;

import java.util.List;

@lombok.Data
public class ExchangeRateResponse {
    private List<Data> data;
}
