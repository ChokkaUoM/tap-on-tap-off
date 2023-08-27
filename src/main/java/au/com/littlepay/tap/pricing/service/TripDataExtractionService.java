package au.com.littlepay.tap.pricing.service;

import au.com.littlepay.tap.pricing.exceptions.InvalidDataException;
import au.com.littlepay.tap.pricing.model.TapDataInput;
import au.com.littlepay.tap.pricing.model.Trip;
import au.com.littlepay.tap.pricing.repository.TapDataRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static au.com.littlepay.tap.pricing.util.Constants.*;

@Slf4j
@Component
@AllArgsConstructor
public class TripDataExtractionService {

    private final TapDataRepository tapDataRepository;

    private final PriceCalculator priceCalculator;

    private final StatusChecker statusChecker;

    //Assume a user can have maximum of two stopIds and user will use the same bus for travel

    private final Comparator<TapDataInput> compareByDate = Comparator.comparing(TapDataInput::getDateTime);


    public List<Trip> extractTripData() {

        Map<String, List<TapDataInput>> mapByBusIDAndPAN = tapDataRepository.getMapByBusIDAndPAN();
        List<Trip> trips = new ArrayList<>();

        mapByBusIDAndPAN.entrySet().forEach(entry -> {
            Trip trip = populateTrip(entry.getValue(), entry.getKey());
            trips.add(trip);

        });

        return trips;
    }

    private Trip populateTrip(List<TapDataInput> tapDataInputs, String compositeKey) {

        String busId = compositeKey.split(COMPOSITE_KEY_SEPARATOR)[0];
        String pan = compositeKey.split(COMPOSITE_KEY_SEPARATOR)[1];
        //Already we are getting same bus same PAN trips. No need to use PAN and Bus id for sorting.
        List<TapDataInput> individualTrips = tapDataInputs.stream().sorted(compareByDate).collect(Collectors.toList());
        List<String> sortedStopIds = individualTrips.stream().map(TapDataInput::getStopId).sorted().collect(Collectors.toList());

        if (CollectionUtils.isEmpty(individualTrips)) {
            log.error("No individual Trip details found for PAN {} busId {}", pan, busId);
            throw new InvalidDataException("Invalid Trip details. Please check the input file");
        }

        return Trip.builder()
                .startDateTime(individualTrips.get(FIRST_STOP_INDEX).getDateTime().toString())
                .finishDateTime(individualTrips.size() == MAX_STOPS_PER_BUS_PER_PAN ? individualTrips.get(SECOND_STOP_INDEX).getDateTime().toString() : EMPTY_STRING)
                .durationInSeconds(individualTrips.size() == MAX_STOPS_PER_BUS_PER_PAN ? Duration.between(individualTrips.get(FIRST_STOP_INDEX).getDateTime(),
                        individualTrips.get(SECOND_STOP_INDEX).getDateTime()).getSeconds() : 0L)
                .fromStopId(individualTrips.get(FIRST_STOP_INDEX).getStopId())
                .toStopId(individualTrips.size() == MAX_STOPS_PER_BUS_PER_PAN ? individualTrips.get(SECOND_STOP_INDEX).getStopId() : EMPTY_STRING)
                .chargeAmount(priceCalculator.calculateFare(sortedStopIds))
                .companyId(individualTrips.get(FIRST_STOP_INDEX).getCompanyId()) // Assume both company Ids are same. No need to validate that
                .busId(busId)
                .pan(pan)
                .status(statusChecker.getStatus(individualTrips))
                .build();
    }


}
