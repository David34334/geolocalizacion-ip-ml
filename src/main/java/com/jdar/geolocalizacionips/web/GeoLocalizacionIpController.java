package com.jdar.geolocalizacionips.web;

import com.jdar.geolocalizacionips.models.dto.ServiceRsDto;
import com.jdar.geolocalizacionips.service.IGeoLocationIpService;
import com.jdar.geolocalizacionips.service.IPersistInformationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/geo/ip")
@RequiredArgsConstructor
public class GeoLocalizacionIpController {

    private final IGeoLocationIpService geoLocationIpService;
    private final IPersistInformationService persistInformationService;

    @Operation(summary = "Localiza una IP a partir del parámetro en la URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "IP Localizada",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = ServiceRsDto.class))}),
    })
    @GetMapping("/localize")
    public ResponseEntity<ServiceRsDto> localizeIp(@Parameter(description = "IP que se va a localizar", required = true)
                                                       @RequestParam("ip") String ip) {
        return ResponseEntity.ok(geoLocationIpService.getIpInfo(ip));
    }

    @Operation(summary = "Devuelve la distancia más larga a Buenos Aires de las IP's consultadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información encontrada",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class))}),
    })
    @GetMapping("/larger-distance")
    public ResponseEntity<String> furthestDistance() {
        return ResponseEntity.ok(persistInformationService.getFurthestDistance());
    }

    @Operation(summary = "Devuelve la distancia más corta a Buenos Aires de las IP's consultadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información encontrada",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class))}),
    })
    @GetMapping("/closest-distance")
    public ResponseEntity<String> closestDistance() {
        return ResponseEntity.ok(persistInformationService.getClosestDistance());
    }

    @Operation(summary = "Devuelve la distancia promedio a Buenos Aires de todas las IP's consultadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información encontrada",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class))}),
    })
    @GetMapping("/average-distance")
    public ResponseEntity<String> averageDistance() {
        return ResponseEntity.ok(persistInformationService.getAverageDistance());
    }

}
