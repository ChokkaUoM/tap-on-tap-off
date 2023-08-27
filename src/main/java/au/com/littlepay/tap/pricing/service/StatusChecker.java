package au.com.littlepay.tap.pricing.service;

import au.com.littlepay.tap.pricing.enums.TripStatus;
import au.com.littlepay.tap.pricing.model.TapDataInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class StatusChecker {

    public String getStatus(List<TapDataInput> individualTrips) {
        TripStatus status;

        List<String> tapTypes = individualTrips.stream().map(TapDataInput::getTapType).distinct().collect(Collectors.toList());
        List<String> stopIds = individualTrips.stream().map(TapDataInput::getStopId).distinct().collect(Collectors.toList());

        if(tapTypes.size() == 2 && stopIds.size() == 2) {
            status = TripStatus.COMPLETED;
        } else if(tapTypes.size() == 2 && stopIds.size() == 1) {
            status = TripStatus.CANCELLED;
        } else {
            status = TripStatus.INCOMPLETE;
        }

        return status.getName();
    }
}
