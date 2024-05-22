package com.jdar.geolocalizacionips.rest;

import com.jdar.geolocalizacionips.rest.dto.countrydto.CountryInfoRsDto;

public interface ICountryInfoApiService {

    CountryInfoRsDto getCountryInfo(String countryCode);

}
