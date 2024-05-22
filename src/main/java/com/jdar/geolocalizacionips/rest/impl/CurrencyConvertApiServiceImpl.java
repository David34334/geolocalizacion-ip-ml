package com.jdar.geolocalizacionips.rest.impl;

import com.jdar.geolocalizacionips.exceptions.ServiceException;
import com.jdar.geolocalizacionips.rest.ICurrencyConvertApiService;
import com.jdar.geolocalizacionips.rest.dto.currencyconvert.CurrencyConvertRsDto;
import com.jdar.geolocalizacionips.utils.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class CurrencyConvertApiServiceImpl implements ICurrencyConvertApiService {

    private final WebClient webClient;

    @Value("${currency.service.api-key}")
    private String apiKey;

    @Override
    public CurrencyConvertRsDto getCurrencyExchange() {
        try {
            return webClient.get()
                    .uri(String.format("https://api.freecurrencyapi.com/v1/latest?apikey=%s", apiKey))
                    .retrieve()
                    .bodyToMono(CurrencyConvertRsDto.class)
                    .block();
        } catch (Exception exception) {
            throw new ServiceException("500", "Error consultando la información de las monedas", DateUtil.getActualDate());
        }
    }

}
