package au.com.littlepay.tap.pricing.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TapDataInput {

    private String id;
    private LocalDateTime dateTime;
    private String tapType;
    private String stopId;
    private String companyId;
    private String busId;
    private String pan;
}
