package com.jdar.geolocalizacionips.rest;

import com.jdar.geolocalizacionips.rest.dto.ipdto.GeoLocationIpRsDto;

public interface IGeoLocationIpApiService {

    GeoLocationIpRsDto getGeolocationIp(String ip);

}
