package com.jdar.geolocalizacionips.models.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ServiceRsDto {

    private String ip;
    private String actualDate;
    private String country;
    private String isoCode;
    private String languages;
    private String currency;
    private String countryDate;
    private String estimateDistance;

    @Override
    public String toString() {
        return "\n" +
                "===========================\n" +
                "=========TRACE IP==========\n" +
                "===========================\n" +
                "IP: " + ip + ", " + "actual date: " + actualDate + "\n" +
                "Country: " + country + "\n" +
                "ISO Code: " + isoCode + "\n" +
                languages + "\n" +
                currency + "\n" +
                countryDate + "\n" +
                "Estimate Distance: " + estimateDistance;
    }

}
