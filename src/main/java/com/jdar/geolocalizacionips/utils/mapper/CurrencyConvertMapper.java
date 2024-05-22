package com.jdar.geolocalizacionips.utils.mapper;

import com.jdar.geolocalizacionips.rest.dto.currencyconvert.CurrencyConvertRsDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyConvertMapper {

    public static String getCurrencyConverted(CurrencyConvertRsDto currencyConvertRsDto, String currencyCode) {
        StringBuilder sb = new StringBuilder("Currency: ");
        LinkedHashMap<String, Object> currencyConverted = (LinkedHashMap<String, Object>) currencyConvertRsDto.getData();
        currencyConverted.forEach((k, v) -> {
            if (Objects.equals(k, currencyCode)) {
                sb.append(currencyCode).append(" (").append("1 ").append(currencyCode).append(" = ").append(v).append(" $ USD)");
            }
        });

        return Objects.equals("Currency: ", sb.toString()) ? sb.append(currencyCode).append(" - Dollar information not available").toString()
                : sb.toString();
    }

}
