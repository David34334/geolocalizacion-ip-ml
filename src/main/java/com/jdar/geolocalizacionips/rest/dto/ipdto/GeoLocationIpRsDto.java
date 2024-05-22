package com.jdar.geolocalizacionips.rest.dto.ipdto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeoLocationIpRsDto {

    private String country;
    private String countryCode;
    private String query;
    private Double lat;
    private Double lon;

}
