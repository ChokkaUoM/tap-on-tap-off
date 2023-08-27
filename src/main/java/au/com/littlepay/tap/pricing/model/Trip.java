package au.com.littlepay.tap.pricing.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Trip {

    private String startDateTime;
    private String finishDateTime;
    private long durationInSeconds;
    private String fromStopId;
    private String toStopId;
    private BigDecimal chargeAmount;
    private String companyId;
    private String busId;
    private String pan;
    private String status;

}
