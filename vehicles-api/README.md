# Vehicles API

A REST API to maintain vehicle data and to provide a complete
view of vehicle details including price and address.

## Features

- REST API exploring the main HTTP verbs and features
- Hateoas
- Custom API Error handling using `ControllerAdvice`
- Swagger API docs
- HTTP WebClient
- MVC Test
- Automatic model mapping

## Instructions

#### TODOs

- Implement the `TODOs` within the `CarService.java` and `CarController.java`  files
- Add additional tests to the `CarControllerTest.java` file based on the `TODOs`
- Implement API documentation using Swagger

#### Run the Code

To properly run this application you need to start the Orders API and
the Service API first.


```
$ mvn clean package
```

```
$ java -jar target/vehicles-api-0.0.1-SNAPSHOT.jar
```

Import it in your favorite IDE as a Maven Project.

## Operations

Swagger UI: http://localhost:8080/swagger-ui.html

### Create a Vehicle

`POST` `/cars`
```json
{
   "condition":"USED",
   "details":{
      "body":"sedan",
      "model":"Impala",
      "manufacturer":{
         "code":101,
         "name":"Chevrolet"
      },
      "numberOfDoors":4,
      "fuelType":"Gasoline",
      "engine":"3.6L V6",
      "mileage":32280,
      "modelYear":2018,
      "productionYear":2018,
      "externalColor":"white"
   },
   "location":{
      "lat":40.73061,
      "lon":-73.935242
   }
}
```

### Retrieve a Vehicle

`GET` `/cars/{id}`

This feature retrieves the Vehicle data from the database
and access the Pricing Service and Boogle Maps to enrich 
the Vehicle information to be presented

### Update a Vehicle

`PUT` `/cars/{id}`

```json
{
   "condition":"USED",
   "details":{
      "body":"sedan",
      "model":"Impala",
      "manufacturer":{
         "code":101,
         "name":"Chevrolet"
      },
      "numberOfDoors":4,
      "fuelType":"Gasoline",
      "engine":"3.6L V6",
      "mileage":32280,
      "modelYear":2018,
      "productionYear":2018,
      "externalColor":"white"
   },
   "location":{
      "lat":40.73061,
      "lon":-73.935242
   }
}
```

### Delete a Vehicle

`DELETE` `/cars/{id}`


### VehiclesApiApplication `vehicles`

This launches the Vehicles API as a Spring Boot application. Additionally, it initializes a few car manufacturers to place in the ManufacturerRepository, as well as creating the web clients to connect to the Maps and Pricing services.

### API Error `vehicles.api`

Declares a few quick methods to return errors and other messages from the Vehicles API.

### CarController

This is our actual REST controller for the application. This implements what happens when GET, POST, PUT and DELETE requests are received (using methods in the CarService), and how they are responded to (including formatting with CarResourceAssembler). You will implement these methods in your code.

### CarResourceAssembler

This helps mapping the CarController to the Car class to help return the API response.

### ErrorController

This helps to handle any invalid arguments fed to the API.

### Address `vehicles.client.maps`

Very similar to the Address file for boogle-maps, this declares a class for use with the MapsClient.

### MapsClient

Handles the format of a GET request to the boogle-maps WebClient to get location data.

### Price `vehicles.client.prices`

Very similar to the Price file for pricing-service, this declares a class for use with the PriceClient.

### PriceClient

Handles the format of a GET request to the pricing-service WebClient to get pricing data.

### Condition `vehicles.domain`

This enumerates the available values for the condition of a car (New or Used).

### Location

This declares information about the location of a vehicle. This is not the exact same as the Address class used by boogle-maps - it's primary use is related to the storage of latitude and longitude values. Because the data, such as address, gathered from boogle-maps is annotated as @Transient, this data is not stored until the next time boogle-maps is called.

### Car `vehicles.domain.car`

This declares certain information about a given vehicle, mostly that more about the car entry itself (such as CreatedAt). Note that a separate class, Details, also stores additional details about the car that is more specific to things like make, color and model. Note that similar to Location with data like address, this uses a @Transient tag with price, meaning the Pricing Service must be called each time a price is desired.

### CarRepository

This repository provide a type of data persistence while the web service runs, primarily related to vehicle information received in the CarService.

### Details

Declares additional vehicle details, primarily about the car build itself, such as fuelType and mileage.

### Manufacturer `vehicles.domain.manufacturer`

This declares the Manufacturer class, primarily just made of a ID code and name of manufacturer.

### ManufacturerRepository

This repository provide a type of data persistence while the web service runs, primarily to store manufacturer information like that initialized in VehiclesApiApplication.

### CarNotFoundException `vehicles.domain`

This creates a CarNotFoundException that can be thrown when an issue arises in the CarService.

### CarService

The Car Service does a lot of the legwork of the code. It can gather either the entire list of vehicles or just a single vehicle by ID (including calls to the maps and pricing web clients). It can also save updated vehicle information. Lastly, it can delete an existing car. All of these are called by the CarController based on queries to the REST API. You will implement most of these methods yourself.

### CarControllerTest `test/../vehicles.api`

Here, the various methods performed by the CarController are performed by creating mock calls to the Vehicles API. You will implement some of these methods yourself for great practice in building your own tests.

