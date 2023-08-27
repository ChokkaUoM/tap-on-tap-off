package au.com.littlepay.tap.pricing.service;

import au.com.littlepay.tap.pricing.mapper.TapDataInputMapper;
import au.com.littlepay.tap.pricing.model.TapDataInput;
import au.com.littlepay.tap.pricing.repository.TapDataRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class TapDataProcessingService {

    private final TapDataInputMapper tapDataInputMapper;

    private final TapDataRepository tapDataRepository;

    public void processTapData(String[] data) {
        TapDataInput tapDataInput = tapDataInputMapper.mapTapData(data);
        tapDataRepository.save(tapDataInput);
    }
}
