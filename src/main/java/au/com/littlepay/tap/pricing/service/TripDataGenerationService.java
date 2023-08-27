package au.com.littlepay.tap.pricing.service;

import au.com.littlepay.tap.pricing.model.Trip;
import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static au.com.littlepay.tap.pricing.util.Constants.HEADER;
import static au.com.littlepay.tap.pricing.util.Constants.SEPARATOR;

@Slf4j
@Component
public class TripDataGenerationService {

    @Value("${taps.output.file_name}")
    private String outputFileName;

    public void generateTripData(List<Trip> trips) {

        FileWriter outputFile;
        try {
            outputFile = new FileWriter(outputFileName);
            CSVWriter csvWriter = new CSVWriter(outputFile);
            List<String[]> convertedData = convertTripData(trips);
            csvWriter.writeAll(convertedData);

            csvWriter.close();
        } catch (IOException e) {
            log.error("Exception occurred while writing trip data", e);
            throw new RuntimeException(e);
        }

    }

    protected List<String[]> convertTripData(List<Trip> trips) {
        List<String[]> data = new ArrayList<>();

        data.add(HEADER);

        trips.stream().forEach(trip -> {
            StringBuilder sb = new StringBuilder();
            sb.append(trip.getStartDateTime())
                    .append(SEPARATOR)
                    .append(trip.getFinishDateTime())
                    .append(SEPARATOR)
                    .append(trip.getDurationInSeconds())
                    .append(SEPARATOR)
                    .append(trip.getFromStopId())
                    .append(SEPARATOR)
                    .append(trip.getToStopId())
                    .append(SEPARATOR)
                    .append(trip.getChargeAmount())
                    .append(SEPARATOR)
                    .append(trip.getCompanyId())
                    .append(SEPARATOR)
                    .append(trip.getBusId())
                    .append(SEPARATOR)
                    .append(trip.getPan())
                    .append(SEPARATOR)
                    .append(trip.getStatus());
            data.add(sb.toString().split(SEPARATOR));
        });

        return data;

    }


}
