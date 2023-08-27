package au.com.littlepay.tap.pricing.service;

import java.io.FileNotFoundException;

public interface FileReaderService {

    void readFile(String filePath) throws FileNotFoundException;
}
