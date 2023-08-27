package au.com.littlepay.tap.pricing.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Fare {

    private BigDecimal amount;
    private String currency;
}
