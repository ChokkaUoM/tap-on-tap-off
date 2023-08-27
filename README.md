# Tap-on Tap-off 
Tap on Tap off system

## Prerequisites

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Build the application

```shell
mvn clean install
```

## Run the application locally

There are mainly two ways you can run the application.

- You can execute the `main` method of the `au.com.littlepay.tap.pricing.PricingModuleApplication` class.
- You can run the following command in the Terminal using Maven

```shell
mvn spring-boot:run
```

### Assumptions

- Maximum of two stopIds available for any trip on the same bus.
- Traveller can travel in any number of buses. There will be a separate trip entry for each bus travel.
- Traveller can have multiple trips. There is many to one mapping between trip to PAN.
- If the traveller is using same PAN and travel in the same bus, assume company Id is also same.

### Input

taps.csv file will be inside the resource directory.

### Output

trips.csv will be created after successful run in the tap-on-tap-off directory.



