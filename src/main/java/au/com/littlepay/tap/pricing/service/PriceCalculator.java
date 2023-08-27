package au.com.littlepay.tap.pricing.service;

import au.com.littlepay.tap.pricing.exceptions.InvalidDataException;
import au.com.littlepay.tap.pricing.model.Fare;
import au.com.littlepay.tap.pricing.repository.TransportFareRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class PriceCalculator {

    private final TransportFareRepository transportFareRepository;

    public BigDecimal calculateFare(List<String> stopIds) {

        BigDecimal fare = BigDecimal.ZERO;

        if(CollectionUtils.isEmpty(stopIds) || stopIds.size() == 0) {
            log.error("No stopId found");
            throw new InvalidDataException("No stop Id found in the data");
        } else if(stopIds.size() == 2) {
            String stopIdKey = String.format("%s%s", stopIds.get(0), stopIds.get(1));
            fare = Optional.ofNullable(transportFareRepository.getCharges().get(stopIdKey))
                    .orElse(Fare.builder().amount(BigDecimal.ZERO).build()).getAmount();
        } else if (stopIds.size() == 1) {
            String stopId = stopIds.get(0);
            List<String> keys = transportFareRepository.getCharges().keySet().stream().filter(key -> key.contains(stopId)).collect(Collectors.toList());
            return calculateHighestFare(keys);
        } else {
            log.error("Invalid number of stops {} observed", stopIds.size());
            throw new InvalidDataException("Ïnvalid number of stops");
        }

        return fare;
    }

    private BigDecimal calculateHighestFare(List<String> keys) {
        List<BigDecimal> fares = new ArrayList<>();
        keys.stream().forEach(key-> {
            fares.add(transportFareRepository.getCharges().get(key).getAmount());
        });

        return Collections.max(fares);
    }

}
