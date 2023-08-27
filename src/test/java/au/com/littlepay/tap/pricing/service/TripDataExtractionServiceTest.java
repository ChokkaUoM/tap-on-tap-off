package au.com.littlepay.tap.pricing.service;

import au.com.littlepay.tap.pricing.enums.TripStatus;
import au.com.littlepay.tap.pricing.model.TapDataInput;
import au.com.littlepay.tap.pricing.model.Trip;
import au.com.littlepay.tap.pricing.repository.TapDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class TripDataExtractionServiceTest {

    private TripDataExtractionService tripDataExtractionService;

    @Mock
    private TapDataRepository tapDataRepository;

    @Mock
    private PriceCalculator priceCalculator;

    @Mock
    private StatusChecker statusChecker;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        tripDataExtractionService = new TripDataExtractionService(tapDataRepository, priceCalculator, statusChecker);
    }

    @Test
    void extractTripData() {
        String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        Map<String, List<TapDataInput>> mapByPAN = new HashMap<>();
        mapByPAN.put("5500005555555559" , Arrays.asList(TapDataInput.builder().id("1").tapType("ON").stopId("Stop1")
                        .pan("5500005555555559").dateTime(LocalDateTime.parse("22-01-2023 13:00:00", dateTimeFormatter)).build(),
                TapDataInput.builder().id("2").tapType("OFF").stopId("Stop3").pan("5500005555555559")
                        .dateTime(LocalDateTime.parse("22-01-2023 13:05:00", dateTimeFormatter)).build()));

        when(tapDataRepository.getMapByPAN()).thenReturn(mapByPAN);
        when(priceCalculator.calculateFare(any())).thenReturn(new BigDecimal("13"));
        when(statusChecker.getStatus(any())).thenReturn(TripStatus.COMPLETED.toString());

        List<Trip> trips = tripDataExtractionService.extractTripData();

        Assertions.assertNotNull(trips);
        Assertions.assertEquals(trips.size(), 1);
        Assertions.assertEquals(trips.get(0).getPan(), "5500005555555559");
        Assertions.assertEquals(trips.get(0).getChargeAmount(), new BigDecimal("13"));
        Assertions.assertEquals(trips.get(0).getStatus(), TripStatus.COMPLETED.toString());

    }
}