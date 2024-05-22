package com.jdar.geolocalizacionips.service;

import com.jdar.geolocalizacionips.models.dto.ServiceRsDto;

import java.util.List;

public interface IPersistInformationService {

    ServiceRsDto getCountryInformation(String ip);
    void saveCountryInformation(ServiceRsDto serviceRsDto, List<String> countryTimezones);
    String getFurthestDistance();
    String getClosestDistance();
    String getAverageDistance();

}
