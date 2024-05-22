package com.jdar.geolocalizacionips.utils.util;

import com.jdar.geolocalizacionips.rest.dto.ipdto.GeoLocationIpRsDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

import static com.jdar.geolocalizacionips.utils.constants.Constants.LATITUDE_BA;
import static com.jdar.geolocalizacionips.utils.constants.Constants.LONGITUDE_BA;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculateDistanceUtil {

    public static String calculateDistance(GeoLocationIpRsDto geoLocationIpRsDto) {
        double distance = getDistance(geoLocationIpRsDto);

        distance = Math.pow(distance, 2);
        DecimalFormat decimalFormat = new DecimalFormat("#");

        return String.valueOf(decimalFormat.format(Math.sqrt(distance)))
                .concat(" kms ")
                .concat("(" + geoLocationIpRsDto.getLat() + ", " + geoLocationIpRsDto.getLon() + ") to ")
                .concat("(" + LATITUDE_BA + ", " + LONGITUDE_BA + ")");
    }

    private static double getDistance(GeoLocationIpRsDto geoLocationIpRsDto) {
        double x = Math.toRadians(geoLocationIpRsDto.getLat() - LATITUDE_BA);
        double y = Math.toRadians(geoLocationIpRsDto.getLon() - LONGITUDE_BA);

        final int R = 6371;

        double a = Math.sin(x / 2) * Math.sin(x / 2)
                + Math.cos(Math.toRadians(geoLocationIpRsDto.getLat())) * Math.cos(Math.toRadians(LATITUDE_BA))
                * Math.sin(y / 2) * Math.sin(y / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // convert to meters
        return distance;
    }

}
