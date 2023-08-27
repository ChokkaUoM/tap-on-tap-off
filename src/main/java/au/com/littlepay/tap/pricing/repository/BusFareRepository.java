package au.com.littlepay.tap.pricing.repository;

import au.com.littlepay.tap.pricing.model.Fare;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BusFareRepository implements TransportFareRepository {

    private static final String STOP1_STOP2 = "Stop1Stop2";
    private static final String STOP2_STOP3 = "Stop2Stop3";
    private static final String STOP1_STOP3 = "Stop1Stop3";

    private static final String STOP1_STOP2_FARE = "3.25";
    private static final String STOP2_STOP3_FARE = "5.50";
    private static final String STOP1_STOP3_FARE = "7.30";

    private static final String CURRENCY = "AUD";

    private static final Map<String, Fare> fareMap = new HashMap<>();

    static {
        fareMap.put(STOP1_STOP2, Fare.builder().amount(new BigDecimal(STOP1_STOP2_FARE)).currency(CURRENCY).build());
        fareMap.put(STOP2_STOP3, Fare.builder().amount(new BigDecimal(STOP2_STOP3_FARE)).currency(CURRENCY).build());
        fareMap.put(STOP1_STOP3, Fare.builder().amount(new BigDecimal(STOP1_STOP3_FARE)).currency(CURRENCY).build());
    }

    @Override
    public Map<String, Fare> getCharges() {

        return fareMap;
    }

}
