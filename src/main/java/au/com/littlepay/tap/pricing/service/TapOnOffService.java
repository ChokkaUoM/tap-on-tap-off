package au.com.littlepay.tap.pricing.service;

import au.com.littlepay.tap.pricing.model.Trip;
import au.com.littlepay.tap.pricing.repository.TapDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TapOnOffService {

    private final FileReaderService fileReaderService;

    private final ResourceLoader resourceLoader;

    private final TripDataExtractionService tripDataExtractionService;

    private final TripDataGenerationService tripDataGenerationService;

    private final TapDataRepository tapDataRepository;

    @Value("${taps.debug.enabled}")
    private boolean isDebugEnabled;

    @Value("${taps.input.file_name}")
    String fileName;

    public void start() {
        try {
            Resource resource = resourceLoader.getResource(String.format("classpath:%s", fileName));
            fileReaderService.readFile(resource.getFile().getAbsolutePath());

            if (isDebugEnabled) {
                tapDataRepository.findAll().stream().forEach(System.out::println);
            }

            List<Trip> trips = tripDataExtractionService.extractTripData();

            if (isDebugEnabled) {
                trips.stream().forEach(System.out::println);
            }

            tripDataGenerationService.generateTripData(trips);

        } catch (Exception e) {
            log.error("Exception occurred", e);
            throw new RuntimeException("Exception occurred while processing the request");
        }
    }
}
