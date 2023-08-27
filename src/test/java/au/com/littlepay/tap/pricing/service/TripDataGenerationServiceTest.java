package au.com.littlepay.tap.pricing.service;

import au.com.littlepay.tap.pricing.enums.TripStatus;
import au.com.littlepay.tap.pricing.model.Trip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
class TripDataGenerationServiceTest {

    @Spy
    private TripDataGenerationService tripDataGenerationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(tripDataGenerationService, "outputFileName", "trips.csv");
    }

    @Test
    void generateTripData() {
        List<Trip> trips = Arrays.asList(Trip.builder().status(TripStatus.COMPLETED.toString()).fromStopId("Stop1").toStopId("Stop2").build(),
                Trip.builder().status(TripStatus.CANCELLED.toString()).fromStopId("Stop3").toStopId("Stop3").build());
        tripDataGenerationService.generateTripData(trips);
        verify(tripDataGenerationService, times(1)).convertTripData(trips);
    }
}