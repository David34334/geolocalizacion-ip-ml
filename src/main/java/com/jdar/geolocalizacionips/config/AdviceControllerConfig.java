package com.jdar.geolocalizacionips.config;

import com.jdar.geolocalizacionips.exceptions.ServiceException;
import com.jdar.geolocalizacionips.models.dto.ErrorRsDto;
import com.jdar.geolocalizacionips.utils.util.DateUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceControllerConfig {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorRsDto> handleServiceException(ServiceException ex){
        ErrorRsDto errorRsDto = ErrorRsDto.builder()
                .code(ex.getCode())
                .message(ex.getDescription())
                .issuedDate(ex.getIssuedDate())
                .build();
        return new ResponseEntity<>(errorRsDto, HttpStatusCode.valueOf(Integer.parseInt(ex.getCode())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRsDto> handleExceptions(Exception ex){
        ErrorRsDto errorRsDto = ErrorRsDto.builder()
                .code("500")
                .message(ex.getMessage())
                .issuedDate(DateUtil.getActualDate())
                .build();
        return new ResponseEntity<>(errorRsDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
