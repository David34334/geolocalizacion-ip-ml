package com.jdar.geolocalizacionips.models.repository;

import com.jdar.geolocalizacionips.models.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICountryRepository extends JpaRepository<CountryEntity, String> {
}
