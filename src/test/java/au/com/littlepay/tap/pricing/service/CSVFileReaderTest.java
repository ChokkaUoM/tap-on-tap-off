package au.com.littlepay.tap.pricing.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.FileNotFoundException;

class CSVFileReaderTest {

    private CSVFileReader csvFileReader;

    @Mock
    private TapDataProcessingService tapDataProcessingService;

    @BeforeEach
    void setUp() {
        csvFileReader = new CSVFileReader(tapDataProcessingService);
    }

    @Test
    void readFileInvalidPath() {

        String filePath = "invalidFilePath";
        Assertions.assertThrows(FileNotFoundException.class, () -> csvFileReader.readFile(filePath));
    }

}