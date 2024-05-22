package com.jdar.geolocalizacionips.service.impl;

import com.jdar.geolocalizacionips.exceptions.ServiceException;
import com.jdar.geolocalizacionips.models.dto.ServiceRsDto;
import com.jdar.geolocalizacionips.rest.ICountryInfoApiService;
import com.jdar.geolocalizacionips.rest.ICurrencyConvertApiService;
import com.jdar.geolocalizacionips.rest.IGeoLocationIpApiService;
import com.jdar.geolocalizacionips.rest.dto.countrydto.CountryInfoRsDto;
import com.jdar.geolocalizacionips.rest.dto.currencyconvert.CurrencyConvertRsDto;
import com.jdar.geolocalizacionips.rest.dto.ipdto.GeoLocationIpRsDto;
import com.jdar.geolocalizacionips.service.IGeoLocationIpService;
import com.jdar.geolocalizacionips.service.IPersistInformationService;
import com.jdar.geolocalizacionips.utils.mapper.CountryInfoMapper;
import com.jdar.geolocalizacionips.utils.mapper.CurrencyConvertMapper;
import com.jdar.geolocalizacionips.utils.util.CalculateDistanceUtil;
import com.jdar.geolocalizacionips.utils.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeoLocationIpServiceImpl implements IGeoLocationIpService {

    private final IGeoLocationIpApiService geoLocationIpApiService;
    private final ICountryInfoApiService countryInfoApiService;
    private final ICurrencyConvertApiService currencyConvertApiService;
    private final IPersistInformationService persistInformationService;

    /**
     * Método que retorna la información asociada a la IP
     * ingresada en el menú de la CLI.
     * @param ip - IP que ingresa por parámetro en el menú de la CLI.
     * @return Objeto con la información asociada a la IP.
     */
    @Override
    public ServiceRsDto getIpInfo(String ip) {
        //1. Valida si existe en base de datos
        ServiceRsDto countryInformation = persistInformationService.getCountryInformation(ip);
        try {
            // 2. Si no se encuentra información, se consulta las API's.
            if (Objects.isNull(countryInformation)) {
                countryInformation = ServiceRsDto.builder().build();
                GeoLocationIpRsDto ipInfo = geoLocationIpApiService.getGeolocationIp(ip);
                countryInformation.setActualDate(DateUtil.getActualDate());
                countryInformation.setIp(ipInfo.getQuery());
                countryInformation.setCountry(ipInfo.getCountry());
                countryInformation.setIsoCode(ipInfo.getCountryCode().toLowerCase());
                countryInformation.setEstimateDistance(CalculateDistanceUtil.calculateDistance(ipInfo));

                List<String> countryInfoTimezones = getCountryInfo(ipInfo.getCountryCode(), countryInformation);
                getCurrencyConvert(countryInformation.getCurrency(), countryInformation);

                //3. Una vez se construye la información de la IP, se guarda el registro en base de datos.
                persistInformationService.saveCountryInformation(countryInformation, countryInfoTimezones);
            }
        } catch (Exception exception) {
            throw new ServiceException("500", "Error generando la información de la dirección IP", DateUtil.getActualDate());
        }
        return countryInformation;
    }

    /**
     * Método que consume la API que devuelve los idiomas asociados,
     * la(s) moneda(s) locales y las diferentes zonas horarias del país a
     * partir de su countryCode (Ej: CO, US, ES).
     * @param countryCode - Código del país (ES, CO).
     * @param serviceRsDto - Objeto de respuesta que se va llenando de acuerdo a las respuestas.
     * @return Se retorna una lista con las zonas horarias del país (para almacenarlas en base
     * de datos).
     */
    private List<String> getCountryInfo(String countryCode, ServiceRsDto serviceRsDto) {
        List<String> timezones;
        try {
            CountryInfoRsDto countryInfoRsDto = countryInfoApiService.getCountryInfo(countryCode);
            serviceRsDto.setLanguages(CountryInfoMapper.getLanguagesInfo(countryInfoRsDto));
            serviceRsDto.setCurrency(CountryInfoMapper.getCurrenciesInfo(countryInfoRsDto));
            serviceRsDto.setCountryDate(CountryInfoMapper.getCountryTimezones(countryInfoRsDto.getTimezones()));
            timezones = countryInfoRsDto.getTimezones();
        } catch (Exception exception) {
            throw new ServiceException("500", "Error obteniendo información del país", DateUtil.getActualDate());
        }
        return timezones;
    }

    /**
     * Método que consume la API necesaria para encontrar la conversión de una moneda
     * a dólares estado unidenes. La API no contiene todas las monedas del mundo, por lo cual
     * es posible que no se encuentre información.
     * @param currency - Moneda del país asociado a la IP ingresada.
     * @param serviceRsDto - Objecto de respuesta que se va llenando de acuerdo a las respuestas.
     */
    private void getCurrencyConvert(String currency, ServiceRsDto serviceRsDto) {
        CurrencyConvertRsDto currencyConvertRsDto = currencyConvertApiService.getCurrencyExchange();
        String currencyConverted = CurrencyConvertMapper.getCurrencyConverted(currencyConvertRsDto, currency);
        serviceRsDto.setCurrency(currencyConverted);
    }

}
