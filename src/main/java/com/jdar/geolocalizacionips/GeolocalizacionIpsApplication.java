package com.jdar.geolocalizacionips;

import com.jdar.geolocalizacionips.service.IGeoLocationIpService;
import com.jdar.geolocalizacionips.service.IPersistInformationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class GeolocalizacionIpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeolocalizacionIpsApplication.class, args);
    }

}
