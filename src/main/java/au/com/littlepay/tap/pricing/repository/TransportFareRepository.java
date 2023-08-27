package au.com.littlepay.tap.pricing.repository;

import au.com.littlepay.tap.pricing.model.Fare;

import java.util.Map;

public interface TransportFareRepository {

    Map<String, Fare> getCharges();
}
