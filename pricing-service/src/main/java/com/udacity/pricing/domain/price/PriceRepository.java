package com.udacity.pricing.domain.price;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;


public interface PriceRepository  extends CrudRepository<Price, Long> {
    List<Price> findByVehicleId(@Param("vehicleId") Long vehicleId);
}
