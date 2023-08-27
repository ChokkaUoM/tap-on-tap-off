package au.com.littlepay.tap.pricing.repository;

import au.com.littlepay.tap.pricing.model.Fare;

import java.math.BigDecimal;
import java.util.Map;

public interface TransportFareRepository {

    Map<String, Fare> getCharges();
}
