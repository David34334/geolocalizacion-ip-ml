package com.jdar.geolocalizacionips.models.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorRsDto {

    private String code;
    private String message;
    private String issuedDate;

}
