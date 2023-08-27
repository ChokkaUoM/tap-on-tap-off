package au.com.littlepay.tap.pricing.repository;

import au.com.littlepay.tap.pricing.model.Fare;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BusFareRepositoryTest {

    private BusFareRepository busFareRepository;

    @BeforeEach
    void setUp() {
        busFareRepository = new BusFareRepository();
    }

    @Test
    void getCharges() {
        Map<String, Fare> charges = busFareRepository.getCharges();
        Assertions.assertNotNull(charges);
        Assertions.assertEquals(charges.size(), 3);
        Assertions.assertEquals(charges.get("Stop2Stop3").getAmount(), new BigDecimal("5.50"));
    }
}