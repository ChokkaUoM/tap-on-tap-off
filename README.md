# Tap-on Tap-off 
Tap on Tap off system

## Prerequisites

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Run application locally

There are mainly two ways you can run the application.

- You can execute the `main` method of the `au.com.littlepay.tap.pricing.PricingModuleApplication` class.
- You can run the following command in the Terminal using Maven

```shell
mvn spring-boot:run
```

### Assumptions

- Maximum of two stopIds available for any traveller
- Traveller will travel only on a single bus. That means same bus ID will be available for a PAN.
- Traveller can have only one trip. There is one to one mapping between trip to PAN.

### Input

taps.csv file will be inside the resource directory.

### Output

trips.csv will be created after successful run in the tap-on-tap-off directory.



