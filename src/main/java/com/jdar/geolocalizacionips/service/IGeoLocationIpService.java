package com.jdar.geolocalizacionips.service;

import com.jdar.geolocalizacionips.models.dto.ServiceRsDto;

public interface IGeoLocationIpService {

    ServiceRsDto getIpInfo(String ip);

}
