package com.jdar.geolocalizacionips.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryEntity {

    @Id
    @Column(name = "IP_COUNTRY")
    private String ipCountry;

    @Column(name = "COUNTRY_NAME")
    private String countryName;

    @Column(name = "COUNTRY_ISO_CODE")
    private String countryIsoCode;

    @Column(name = "COUNTRY_LANGUAGES")
    private String countryLanguages;

    @Column(name = "COUNTRY_CURRENCY")
    private String countryCurrency;

    @Column(name = "COUNTRY_TIMEZONES")
    private String countryTimezones;

    @Column(name = "COUNTRY_DISTANCE")
    private String countryDistance;

    @Column(name = "COUNTRY_CALLS")
    private int countryCalls;


}
