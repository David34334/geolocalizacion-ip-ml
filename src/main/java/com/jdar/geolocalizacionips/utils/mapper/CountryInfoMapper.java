package com.jdar.geolocalizacionips.utils.mapper;

import com.jdar.geolocalizacionips.rest.dto.countrydto.CountryInfoRsDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CountryInfoMapper {

    public static String getLanguagesInfo(CountryInfoRsDto countryInfoRsDto) {
        StringBuilder sb = new StringBuilder("Languages: ");
        LinkedHashMap<String, String> countryLanguages = (LinkedHashMap<String, String>) countryInfoRsDto.getLanguages();
        countryLanguages.forEach((k, v) ->
            sb.append(v).append(" ").append("(").append(k).append(")").append(", "));
        return sb.toString();
    }

    public static String getCurrenciesInfo(CountryInfoRsDto countryInfoRsDto) {
        StringBuilder sb = new StringBuilder();
        LinkedHashMap<String, Object> countryCurrencies = (LinkedHashMap<String, Object>) countryInfoRsDto.getCurrencies();
        countryCurrencies.forEach((k, v) ->
                sb.append(k));
        return sb.toString();
    }

    public static String getCountryTimezones(List<String> timezones) {
        StringBuilder sb = new StringBuilder("Timezones: ");
        timezones.forEach(timezone -> {
            ZoneId zoneId = ZoneId.of(timezone);
            ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            sb.append(zonedDateTime.format(formatter)).append(" (").append(timezone).append(")").append(", ");
        });
        return sb.toString();
    }

}
