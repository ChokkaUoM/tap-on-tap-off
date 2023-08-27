package au.com.littlepay.tap.pricing.service;

import au.com.littlepay.tap.pricing.exceptions.InvalidDataException;
import au.com.littlepay.tap.pricing.repository.BusFareRepository;
import au.com.littlepay.tap.pricing.repository.TransportFareRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class PriceCalculatorTest {

    private PriceCalculator priceCalculator;
    private TransportFareRepository transportFareRepository;

    @BeforeEach
    void setUp() {
        transportFareRepository = new BusFareRepository();
        priceCalculator = new PriceCalculator(transportFareRepository);
    }

    @Test
    void testCalculateFareWithTwoStops() {
        List<String> stops = Arrays.asList("Stop1", "Stop2");
        BigDecimal fare = priceCalculator.calculateFare(stops);
        Assertions.assertEquals(fare, new BigDecimal(3.25));
    }

    @Test
    void testCalculateFareWithOneStop() {
        List<String> stops = Arrays.asList("Stop1");
        BigDecimal fare = priceCalculator.calculateFare(stops);
        Assertions.assertEquals(fare, new BigDecimal("7.30"));
    }

    @Test
    void testCalculateFareWithNoStop() {
        List<String> stops = new ArrayList<>();
        Assertions.assertThrows(InvalidDataException.class, ()-> priceCalculator.calculateFare(stops));
    }

    @Test
    void testCalculateFareWithInvalidStops() {
        List<String> stops = Arrays.asList("Stop1", "Stop2", "Stop3");
        Assertions.assertThrows(InvalidDataException.class, ()-> priceCalculator.calculateFare(stops));
    }
}