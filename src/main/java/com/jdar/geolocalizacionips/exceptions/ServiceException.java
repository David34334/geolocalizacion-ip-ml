package com.jdar.geolocalizacionips.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceException extends RuntimeException {

    private final String code;
    private final String description;
    private final String issuedDate;

}
