package com.jdar.geolocalizacionips.rest.dto.countrydto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryInfoRsDto {

    private Object languages;
    private Object currencies;
    private List<String> timezones;

}
