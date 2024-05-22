package com.jdar.geolocalizacionips.rest.impl;

import com.jdar.geolocalizacionips.exceptions.ServiceException;
import com.jdar.geolocalizacionips.rest.IGeoLocationIpApiService;
import com.jdar.geolocalizacionips.rest.dto.ipdto.GeoLocationIpRsDto;
import com.jdar.geolocalizacionips.utils.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GeoLocationIpApiServiceImpl implements IGeoLocationIpApiService {

    private final WebClient webClient;

    @Override
    public GeoLocationIpRsDto getGeolocationIp(String ip) {
        try {
            return webClient.get()
                    .uri(String.format("http://ip-api.com/json/%s?lang=en", ip))
                    .retrieve()
                    .bodyToMono(GeoLocationIpRsDto.class)
                    .block();
        } catch (Exception exception) {
            throw new ServiceException("500", "Error consultando la información de las IP", DateUtil.getActualDate());
        }
    }

}
