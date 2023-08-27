package au.com.littlepay.tap.pricing.mapper;

import au.com.littlepay.tap.pricing.exceptions.InvalidDataException;
import au.com.littlepay.tap.pricing.model.TapDataInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

import static au.com.littlepay.tap.pricing.util.Constants.DATE_FORMAT;
import static au.com.littlepay.tap.pricing.util.Constants.EMPTY_STRING;


@Slf4j
@Component
public class TapDataInputMapper {

    @Value("${taps.input.field_size}")
    private int FIELD_SIZE;

    @Value("${taps.input.id.index}")
    private int ID_INDEX;

    @Value("${taps.input.date_time.index}")
    private int DATE_TIME_INDEX;

    @Value("${taps.input.tap_type.index}")
    private int TAP_TYPE_INDEX;

    @Value("${taps.input.stop_id.index}")
    private int STOP_ID_INDEX;

    @Value("${taps.input.company_id.index}")
    private int COMPANY_ID_INDEX;

    @Value("${taps.input.bus_id.index}")
    private int BUS_ID_INDEX;

    @Value("${taps.input.pan.index}")
    private int PAN_INDEX;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);


    public TapDataInput mapTapData(String[] fields) {

        if (Objects.isNull(fields) || fields.length != FIELD_SIZE) {
            log.error("Invalid data received in the file");
            throw new InvalidDataException("Invalid Data in the file");
        }

        return TapDataInput.builder()
                .id(Optional.ofNullable(fields[ID_INDEX]).orElse(EMPTY_STRING).trim())
                .dateTime(LocalDateTime.parse(Optional.ofNullable(fields[DATE_TIME_INDEX])
                        .orElse(LocalDateTime.now().toString()).trim(), dateTimeFormatter))
                .tapType(Optional.ofNullable(fields[TAP_TYPE_INDEX]).orElse(EMPTY_STRING).trim())
                .stopId(Optional.ofNullable(fields[STOP_ID_INDEX]).orElse(EMPTY_STRING).trim())
                .companyId(Optional.ofNullable(fields[COMPANY_ID_INDEX]).orElse(EMPTY_STRING).trim())
                .busId(Optional.ofNullable(fields[BUS_ID_INDEX]).orElse(EMPTY_STRING).trim())
                .pan(Optional.ofNullable(fields[PAN_INDEX]).orElse(EMPTY_STRING).trim())
                .build();
    }
}
