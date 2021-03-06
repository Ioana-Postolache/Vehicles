package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;
    private final MapsClient mapsClient;
    private final PriceClient priceClient;

    public CarService(CarRepository repository, MapsClient mapsClient, PriceClient priceClient) {
        this.repository = repository;
        this.mapsClient = mapsClient;
        this.priceClient = priceClient;
    }

    /**
     * Gathers a list of all vehicles
     *
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        List<Car> allCars  = repository.findAll();
        // Loop through all cars to get their price and their address
        for (Car car:allCars) {
            Long id = car.getId();
            //  Uses the Pricing Web client you create in `VehiclesApiApplication`
            //    to get the price based on the `id` input'
            String price = priceClient.getPrice(id);
            // Set the price of the car
            car.setPrice(price);

            // Uses the Maps Web client you create in `VehiclesApiApplication`
            //   to get the address for the vehicle. You should access the location
            //   from the car object and feed it to the Maps service.

            Location newCarLocation = mapsClient.getAddress(car.getLocation());
            // Set the location of the vehicle, including the address information
            car.setLocation(newCarLocation);

            repository.save(car);
        }
        return allCars;
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     *
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {

        Optional<Car> optionalCar = repository.findById(id);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();

            //  Uses the Pricing Web client you create in `VehiclesApiApplication`
            //    to get the price based on the `id` input'
            String price = priceClient.getPrice(id);
            // Set the price of the car
            car.setPrice(price);

            // Uses the Maps Web client you create in `VehiclesApiApplication`
            //   to get the address for the vehicle. You should access the location
            //   from the car object and feed it to the Maps service.

            Location newCarLocation = mapsClient.getAddress(car.getLocation());
            // Set the location of the vehicle, including the address information
            car.setLocation(newCarLocation);

            repository.save(car);
            return car;
        } else {
            throw new CarNotFoundException("Car " + id + " Not Found");
        }
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     *
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        Long id = car.getId();
        if ( id != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        carToBeUpdated.setPrice(car.getPrice());
                        carToBeUpdated.setCondition(car.getCondition());
                        carToBeUpdated.setModifiedAt(LocalDateTime.now());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }
        return repository.save(car);
    }

    /**
     * Deletes a given car by ID
     *
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {

        Optional<Car> optionalCar = repository.findById(id);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            repository.delete(car);
        } else {
            throw new CarNotFoundException("Car " + id + " Not Found");
        }
    }
}
