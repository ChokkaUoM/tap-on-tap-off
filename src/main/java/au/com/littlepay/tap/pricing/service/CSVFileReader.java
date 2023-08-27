package au.com.littlepay.tap.pricing.service;

import au.com.littlepay.tap.pricing.exceptions.InvalidInputException;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@Service
@AllArgsConstructor
public class CSVFileReader implements FileReaderService {


    private final TapDataProcessingService tapDataProcessingService;

    @Override
    public void readFile(String filePath) throws FileNotFoundException {
        FileReader fileReader = new FileReader(filePath);
        CSVReader csvReader = new CSVReaderBuilder(fileReader)
                .withSkipLines(1)
                .build();

        try {
            String[] nextLine;

            while ((nextLine = csvReader.readNext()) != null) {
                tapDataProcessingService.processTapData(nextLine);
            }

        } catch (IOException e) {
            log.error("Ëxception occurred while reading file {}", filePath, e);
            throw new InvalidInputException("Invalid file is provided");
        }


    }
}
