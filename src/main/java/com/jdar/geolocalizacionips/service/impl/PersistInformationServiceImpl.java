package com.jdar.geolocalizacionips.service.impl;

import com.jdar.geolocalizacionips.exceptions.ServiceException;
import com.jdar.geolocalizacionips.models.dto.ServiceRsDto;
import com.jdar.geolocalizacionips.models.entity.CountryEntity;
import com.jdar.geolocalizacionips.models.repository.ICountryRepository;
import com.jdar.geolocalizacionips.service.IPersistInformationService;
import com.jdar.geolocalizacionips.utils.mapper.CountryInfoMapper;
import com.jdar.geolocalizacionips.utils.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jdar.geolocalizacionips.utils.constants.Constants.NO_INFORMATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersistInformationServiceImpl implements IPersistInformationService {

    private final ICountryRepository countryRepository;

    /**
     * Consulta si la IP está registrada en base de datos, en caso de que no,
     * se devuele null.
     * @param ip - IP a consultar (PrimaryKey en base de datos).
     * @return - Objeto con la información de la IP en caso de encontrar información,
     * caso contrario, null.
     */
    @Override
    public ServiceRsDto getCountryInformation(String ip) {
        ServiceRsDto serviceRsDto = null;
        CountryEntity countryEntity = countryRepository.findById(ip).orElse(null);

        if (Objects.nonNull(countryEntity)) {
            serviceRsDto = buildServiceRsDto(countryEntity);
            countryEntity.setCountryCalls(countryEntity.getCountryCalls() + 1);
            countryRepository.save(countryEntity);
        }

        return serviceRsDto;
    }

    /**
     * Guarda el registro en base de datos cuando es consultado por primera vez.
     * @param serviceRsDto - Información obtenida de las API.
     * @param countryTimezones - Zonas horarias asociadas la país en cuestión.
     */
    @Override
    public void saveCountryInformation(ServiceRsDto serviceRsDto, List<String> countryTimezones) {
        try {
            countryRepository.save(buildCountryEntity(serviceRsDto, countryTimezones));
        } catch (Exception exception) {
            throw new ServiceException("500", "Error guardando información en la base de datos", DateUtil.getActualDate());
        }
    }

    /**
     * Método que consulta la distancia más lejana a Buenos Aires de acuerdo
     * con la información registrada en base de datos.
     */
    @Override
    public String getFurthestDistance() {
        List<CountryEntity> countries = countryRepository.findAll();
        if (countries.isEmpty()) {
            throw new ServiceException("210", NO_INFORMATION, DateUtil.getActualDate());
        } else {
            AtomicInteger furthestDistance = new AtomicInteger(0);
            countries.forEach(item -> {
                int distance = Integer.parseInt(item.getCountryDistance().split(" ")[0]);
                if (distance > furthestDistance.get()) {
                    furthestDistance.set(distance);
                }
            });
            return ">>>La distancia más lejana es: " + furthestDistance + " km";
        }
    }

    /**
     * Método que consulta la distancia más corta a Buenos Aires de acuerdo
     * con la información registrada en base de datos.
     */
    @Override
    public String getClosestDistance() {
        List<CountryEntity> countries = countryRepository.findAll();
        if (countries.isEmpty()) {
            throw new ServiceException("210", NO_INFORMATION, DateUtil.getActualDate());
        } else {
            AtomicInteger closestDistance = new AtomicInteger(1000000000);
            countries.forEach(item -> {
                int distance = Integer.parseInt(item.getCountryDistance().split(" ")[0]);
                if (distance < closestDistance.get()) {
                    closestDistance.set(distance);
                }
            });
            return ">>>La distancia más cercana es: " + closestDistance + " km";
        }
    }

    /**
     * Método que consulta la distancia promedio de acuerdo con todos los registros
     * presentes en base de datos.
     */
    @Override
    public String getAverageDistance() {
        List<CountryEntity> countries = countryRepository.findAll();
        if (countries.isEmpty()) {
            throw new ServiceException("210", NO_INFORMATION, DateUtil.getActualDate());
        } else {
            AtomicInteger totalServiceCalls = new AtomicInteger(0);
            AtomicInteger accumulateDistance = new AtomicInteger(0);
            countries.forEach(country -> {
                int distance = Integer.parseInt(country.getCountryDistance().split(" ")[0]);
                accumulateDistance.set(accumulateDistance.get() + (distance * country.getCountryCalls()));
                totalServiceCalls.set(totalServiceCalls.get() + country.getCountryCalls());
            });
            return ">>>La distancia promedio es de: " + (accumulateDistance.get() / totalServiceCalls.get()) + " km";
        }
    }

    private CountryEntity buildCountryEntity(ServiceRsDto serviceRsDto, List<String> countryTimezones) {
        return CountryEntity.builder()
                .ipCountry(serviceRsDto.getIp())
                .countryName(serviceRsDto.getCountry())
                .countryIsoCode(serviceRsDto.getIsoCode())
                .countryLanguages(serviceRsDto.getLanguages())
                .countryCurrency(serviceRsDto.getCurrency())
                .countryTimezones(getTimezonesAsString(countryTimezones))
                .countryDistance(serviceRsDto.getEstimateDistance())
                .countryCalls(1)
                .build();
    }

    private String getTimezonesAsString(List<String> timezones) {
        StringBuilder stringBuilder = new StringBuilder();
        timezones.forEach(timezone ->
            stringBuilder.append(timezone).append(","));
        return stringBuilder.toString();
    }

    private ServiceRsDto buildServiceRsDto(CountryEntity countryEntity) {
        return ServiceRsDto.builder()
                .ip(countryEntity.getIpCountry())
                .actualDate(DateUtil.getActualDate())
                .country(countryEntity.getCountryName())
                .isoCode(countryEntity.getCountryIsoCode())
                .languages(countryEntity.getCountryLanguages())
                .currency(countryEntity.getCountryCurrency())
                .countryDate(CountryInfoMapper.getCountryTimezones(getTimezonesList(countryEntity.getCountryTimezones())))
                .estimateDistance(countryEntity.getCountryDistance())
                .build();
    }

    private List<String> getTimezonesList(String timezones) {
        return Arrays.asList(timezones.split(","));
    }

}
