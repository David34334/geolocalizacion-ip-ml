package com.jdar.geolocalizacionips.rest.impl;

import com.jdar.geolocalizacionips.exceptions.ServiceException;
import com.jdar.geolocalizacionips.rest.ICountryInfoApiService;
import com.jdar.geolocalizacionips.rest.dto.countrydto.CountryInfoRsDto;
import com.jdar.geolocalizacionips.utils.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class CountryInfoApiServiceImpl implements ICountryInfoApiService {

    private final WebClient webClient;

    @Override
    public CountryInfoRsDto getCountryInfo(String countryCode) {
        try {
            return webClient.get()
                    .uri(String.format("https://restcountries.com/v3.1/alpha/%s?fields=timezones,currencies,languages", countryCode))
                    .retrieve()
                    .bodyToMono(CountryInfoRsDto.class)
                    .block();
        } catch (Exception exception) {
            throw new ServiceException("500", "Error consultando la información de los países", DateUtil.getActualDate());
        }
    }

}
