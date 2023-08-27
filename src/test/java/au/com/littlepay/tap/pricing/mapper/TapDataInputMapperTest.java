package au.com.littlepay.tap.pricing.mapper;

import au.com.littlepay.tap.pricing.exceptions.InvalidDataException;
import au.com.littlepay.tap.pricing.model.TapDataInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TapDataInputMapperTest {

    private TapDataInputMapper tapDataInputMapper;

    @BeforeEach
    void setUp() {
        tapDataInputMapper = new TapDataInputMapper();
        ReflectionTestUtils.setField(tapDataInputMapper, "FIELD_SIZE", 7);
        ReflectionTestUtils.setField(tapDataInputMapper, "ID_INDEX", 0);
        ReflectionTestUtils.setField(tapDataInputMapper, "DATE_TIME_INDEX", 1);
        ReflectionTestUtils.setField(tapDataInputMapper, "TAP_TYPE_INDEX", 2);
        ReflectionTestUtils.setField(tapDataInputMapper, "STOP_ID_INDEX", 3);
        ReflectionTestUtils.setField(tapDataInputMapper, "COMPANY_ID_INDEX", 4);
        ReflectionTestUtils.setField(tapDataInputMapper, "BUS_ID_INDEX", 5);
        ReflectionTestUtils.setField(tapDataInputMapper, "PAN_INDEX", 6);
    }

    @Test
    void mapTapData() {
        String[] fields = {"1", "22-01-2023 13:00:00", "ON", "Stop1", "Company1", "Bus37", "5500005555555559"};
        TapDataInput tapDataInput = tapDataInputMapper.mapTapData(fields);
        Assertions.assertNotNull(tapDataInput);
        Assertions.assertEquals(tapDataInput.getId(), "1");
        Assertions.assertEquals(tapDataInput.getPan(), "5500005555555559");
    }

    @Test
    void mapTapInvalidData() {
        String[] fields = {"1", "22-01-2023 13:00:00", "ON", "Stop1", "Company1", "Bus37"};
        Assertions.assertThrows(InvalidDataException.class, () -> tapDataInputMapper.mapTapData(fields));
    }
}